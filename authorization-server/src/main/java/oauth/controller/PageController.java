package oauth.controller;

import oauth.common.ModelResult;
import oauth.common.ResultEntity;
import oauth.common.ValidResult;
import oauth.constant.Oauth;
import oauth.entity.Authorize;
import oauth.entity.OauthApprovals;
import oauth.entity.OauthClientInfo;
import oauth.mapper.OauthApprovalsMapper;
import oauth.mapper.OauthClientInfoMapper;
import oauth.service.db1.OauthApprovalsService;
import oauth.service.db1.OauthClientInfoService;
import oauth.util.HibernateValidatorUtil;
import oauth.util.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PageController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OauthApprovalsService oauthApprovalsService;

    @Autowired
    private OauthClientInfoService oauthClientInfoService;



    /**
     * xuchun
     * 2018-02-26
     * 用户授权页面
     * @param authorize
     * @return
     */
    @GetMapping("/authorization")
    public ModelAndView authorization(
            @ModelAttribute("authorize")
                    Authorize authorize ){
        logger.info("---------------recieve params:"+ WebUtil.parseFastJson(authorize));
        ModelAndView mv = new ModelAndView();
        //验证请求参数
        ValidResult result = HibernateValidatorUtil.validate(authorize);
        if (!result.getPass()) {
            return ModelResult.toMv(mv,"other/error.btl","error",result.getFirstMsg());
        }
        //查询clientId是否存在
        OauthClientInfo info=new OauthClientInfo();
        info.setClientId(authorize.getClient_id());
        info.setStatus("0");
        info=oauthClientInfoService.selectOne(info);
        if(info==null){
            return ModelResult.toMv(mv,"other/error.btl","error","client_id不存在或未激活！");
        }
        //1.判断clientId是否支持resonpseType
        if(StringUtils.isEmpty(info.getGrantType())){
            return ModelResult.toMv(mv,"other/error.btl","error","client_id未分配授权类型！");
        }
        //2.判断授权类型
        if(info.getGrantType().indexOf(",")!=-1){
            String[] grantTypes=info.getGrantType().split(",");
            //转成固定大小list，这个list添加元素会报错(因为是固定大小的)
            List<String> list=Arrays.asList(grantTypes);
            if(!list.contains(authorize.getResponse_type())){
                return ModelResult.toMv(mv,"other/error.btl","error","非法授权类型："+authorize.getResponse_type());
            }
        }else{
            if(!info.getGrantType().equals(authorize.getResponse_type())){
                return ModelResult.toMv(mv,"other/error.btl","error","与授予的授权类型不匹配："+authorize.getResponse_type());
            }
        }
        //3.判断回调redirect_uri
        if(StringUtils.isEmpty(info.getRedirectUri())){
            return ModelResult.toMv(mv,"other/error.btl","error","该client_id未设置回调地址！");
        }
        //设置了多个回调地址
        if(info.getRedirectUri().indexOf(",")!=-1){
            String[] uris=info.getRedirectUri().split(",");
            //转成固定大小list，这个list添加元素会报错(因为是固定大小的)
            List<String> list=Arrays.asList(uris);
            if(!list.contains(authorize.getRedirect_uri())){
                return ModelResult.toMv(mv,"other/error.btl","error","与配置的回调地址不一致！："+authorize.getRedirect_uri());
            }
        }else{//设置了单个地址
            if(!info.getRedirectUri().equals(authorize.getRedirect_uri())){
                return ModelResult.toMv(mv,"other/error.btl","error","与配置的回调地址不一致！："+authorize.getRedirect_uri());
            }
        }
        //取出clientId默认分配的权限scope范围集合
        OauthApprovals approvals=new OauthApprovals();
        approvals.setClientId(authorize.getClient_id());
        approvals.setUserId(0);
        approvals.setStatus("0");
        approvals=oauthApprovalsService.selectOne(approvals);
        if(approvals==null
                ||StringUtils.isEmpty(approvals.getScopeCodes())){
            return ModelResult.toMv(mv,"other/error.btl",
                    "error",
                    "未给clientId："+authorize.getClient_id()+"分配默认访问权限");
        }
        //默认配置的scope集合
        String [] a=null;
        if(approvals.getScopeCodes().indexOf(",")!=-1){
            a=approvals.getScopeCodes().split(",");
        }else{
            a=new String[1];
            a[0]=approvals.getScopeCodes();
        }

        //4.判断scope作用域（权限）
        if(info.getAutoApprove().equals(Oauth.用户授权.value())){
            //用户授权类型，对比提交的scope参数
            //验证提交的scope是否合法
            if(StringUtils.isNotEmpty(authorize.getScope())){

                String [] b=null;

                if(authorize.getScope().indexOf(",")!=-1){
                    b=authorize.getScope().split(",");
                }else{
                    b=new String[1];
                    b[0]=authorize.getScope();
                }
                //判断提交的scope参数是否是默认分配的权限scope的子集
                if(!WebUtil.isContain(a,b)){
                    return ModelResult.toMv(mv,"other/error.btl",
                            "error",
                            "提交申请的scope作用域不合法！");
                }
                return new ModelAndView("login/authorization.btl","scopes",b);

            }else{//否则就读取默认分配的所有scope范畴
                return new ModelAndView("login/authorization.btl","scopes",a);
            }

        }else if(info.getAutoApprove().equals(Oauth.自动授权.value())){
            //自动授权客户端提交的scope参数会被忽视，将读取数据库默认分配的scope，且不在授权页面列出授权范围列表
            Map<String,Object> paramsMap=new HashMap<>();
            paramsMap.put("scopes",a);
            paramsMap.put("autoApp",true);
            return new ModelAndView("login/authorization.btl",paramsMap);
        }else{
            return ModelResult.toMv(mv,"other/error.btl","error","客户端配置了不可识别授权类型，请联系系统管理员！");
        }



    }

    @GetMapping("/index")
    public String index(){
        return "/page/index.html";

    }
}
