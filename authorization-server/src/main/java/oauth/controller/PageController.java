package oauth.controller;

import oauth.bean.JwtHead;
import oauth.bean.JwtPlayLoad;
import oauth.common.ModelResult;
import oauth.common.ValidResult;
import oauth.constant.Oauth;
import oauth.entity.*;
import oauth.exception.JsontoObjException;
import oauth.service.db1.OauthApprovalsService;
import oauth.service.db1.OauthClientInfoService;
import oauth.service.db1.OauthClientTokenService;
import oauth.service.db1.OauthUsersService;
import oauth.service.redis.RedisService;
import oauth.util.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/page")
public class PageController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OauthApprovalsService oauthApprovalsService;

    @Autowired
    private OauthClientInfoService oauthClientInfoService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private OauthUsersService oauthUsersService;

    @Autowired
    private OauthClientTokenService oauthClientTokenService;



    /**
     * xuchun
     * 2018-02-26
     * 步骤一》》用户授权页面(授权场景一）
     * @param authorize
     * @return
     */
    @GetMapping("/authorize")
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
        String keys=redisService.get(authorize.getClient_id()+","+authorize.getState());
        if(StringUtils.isNotEmpty(keys)){
            return ModelResult.toMv(mv,"other/error.btl","error","state重复提交");
        }
        //查询clientId是否存在（可放入redis优化）
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
        //将请求的参数缓存在client_info中并放入redis，下次获取code时候使用(防止授权页面过期）
        String key=authorize.getClient_id()+","+authorize.getState();
        info.setCacheRedirectUrl(authorize.getRedirect_uri());
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
                info.setCacheScopes(b);
                redisService.set(key,WebUtil.parseFastJson(info),300000L);
                mv.setViewName("login/authorization.btl");
                mv.addObject("scopes",b);
                mv.addObject("clientId",authorize.getClient_id());
                mv.addObject("state",authorize.getState());
                return mv;

            }else{//否则就读取默认分配的所有scope范畴
                info.setCacheScopes(a);
                redisService.set(key,WebUtil.parseFastJson(info),300000L);
                mv.setViewName("login/authorization.btl");
                mv.addObject("scopes",a);
                mv.addObject("clientId",authorize.getClient_id());
                mv.addObject("state",authorize.getState());
                return mv;
            }

        }else if(info.getAutoApprove().equals(Oauth.自动授权.value())){
            //自动授权客户端提交的scope参数会被忽视，将读取数据库默认分配的scope，且不在授权页面列出授权范围列表
            Map<String,Object> paramsMap=new HashMap<>();
            info.setCacheScopes(a);
            redisService.set(key,WebUtil.parseFastJson(info),300000L);
            paramsMap.put("scopes",a);
            paramsMap.put("clientId",authorize.getClient_id());
            paramsMap.put("state",authorize.getState());
            paramsMap.put("autoApp",true);
            return new ModelAndView("login/authorization.btl",paramsMap);
        }else{
            return ModelResult.toMv(mv,"other/error.btl","error","客户端配置了不可识别授权类型，请联系系统管理员！");
        }



    }


    /**
     * xuchun
     * 2018-02-26
     * 步骤二》》授权页面提交获取code(授权场景一）
     * @param clientId
     * @param state
     * @return
     */
    @ResponseBody
    @RequestMapping("/code")
    public Map<String,Object> getCode(
            String clientId,
            String state,
            String[] scopes,
            String username,
            String password){
        logger.info("------------收到的params:"
                +"clientId="+clientId
                +"\tstate="+state
                +"\tscopes="+WebUtil.parseFastJson(scopes)
                +"\tusername="+username
                +"\tpassword="+password);
       Map<String,Object> resMap=new HashMap<>();
        try {
            if(StringUtils.isEmpty(clientId)
                    ||StringUtils.isEmpty(state)
                    || ArrayUtils.isEmpty(scopes)
                    ||StringUtils.isEmpty(username)
                    ||StringUtils.isEmpty(password)){
                resMap.put("success",false);
                resMap.put("msg","授权参数不完整，授权失败！");
                logger.error("========授权参数不能为空！");
                return resMap;
                //return ModelResult.toMv(mv,"other/error.btl","error","请求参数中不能包含空值！");
            }
            //获取redis中code回调地址
            String key=clientId+","+state;
            String clientInfo=redisService.get(key);
            if(StringUtils.isEmpty(clientInfo)){
                resMap.put("success",false);
                resMap.put("msg","授权流程已过期！");
                return resMap;
                //return ModelResult.toMv(mv,"other/error.btl","error","您在授权页面停留过久啦，授权流程已过期！");
            }
            //用户登录验证
            OauthUsers user=new OauthUsers();
            user.setUsername(username.trim());
            user.setStatus("0");
            user=oauthUsersService.getUser(user);
            if(user==null
                    ||!WebUtil.validPwd(password.trim(),user.getPassword())){
                resMap.put("success",false);
                resMap.put("msg","用户或者密码错误！");
                return resMap;
            }


            OauthClientInfo info=WebUtil.parseObject(clientInfo,OauthClientInfo.class);
            String redirectUrlCache=info.getCacheRedirectUrl();
            //获取上一步授权提交的授权列表
            String[] scopesCache=info.getCacheScopes();

            //判断用户提交的授权是否包含在，上一步的授权列表中
            if(!WebUtil.isContain(scopesCache,scopes)){
                resMap.put("success",false);
                resMap.put("msg","非法授权类型！");
                return resMap;
            }
            //授权入库
            OauthApprovals newOauthApprovals=new OauthApprovals();
            newOauthApprovals.setUserId(1);
            newOauthApprovals.setClientId(clientId);
            newOauthApprovals.setScopeCodes(StringUtils.join(scopes, ","));
            newOauthApprovals.setLastModifiedTime(new Date());
            newOauthApprovals.setExpiresTime(DateUtil.getDateAddMin(5));//测试5分钟，实际应该是30天左右
            newOauthApprovals.setStatus("0");
            OauthApprovals oddOauthApprovals=new OauthApprovals();
            oddOauthApprovals.setClientId(clientId);
            oddOauthApprovals.setUserId(1);
            int i=oauthApprovalsService.saveOrUpdate(newOauthApprovals,oddOauthApprovals);
            if(i<0){
                throw new RuntimeException("保存或者更新oauthApprovals表失败！");
            }
            info.setCacheScopes(scopes);//更新用户提交的授权
            //code码生成规则为客户端ID+状态+系统时间戳
            String code= Md5Util.MD5(clientId+state+System.currentTimeMillis());
            //code只能使用一次
            redisService.set(code,WebUtil.parseFastJson(info),60000L);//1分钟过期（实际应该10分钟）
            resMap.put("success",true);
            resMap.put("msg","授权成功！");
            resMap.put("redirectUrl",redirectUrlCache);
            resMap.put("code",code);
            resMap.put("state",state);
            return resMap;
        } catch (Exception e) {
            logger.error("error:"+e);
            resMap.put("success",false);
            resMap.put("msg","授权失败，请联系服务商！");
            return resMap;
        }
    }


    @RequestMapping("/token")
    public ResponseEntity<TokenResult> token(Token token){
        logger.info("---------------recieve params:"+ WebUtil.parseFastJson(token));
        Map<String,Object> res=new HashMap<>();
        TokenResult tokenResult=new TokenResult();
        try {
            //验证请求参数
            ValidResult result = HibernateValidatorUtil.validate(token);
            if (!result.getPass()) {
                tokenResult.setSuccess(false);
                tokenResult.setMsg(result.getFirstMsg());
                return new ResponseEntity<>(tokenResult, HttpStatus.OK);
            }
            //根据code获取缓存信息
            String clientInfoCache=redisService.get(token.getCode());
            if(StringUtils.isEmpty(clientInfoCache)){
                tokenResult.setSuccess(false);
                tokenResult.setMsg("无效code或授权已过期！");
                return new ResponseEntity<>(tokenResult, HttpStatus.OK);
            }

            OauthClientInfo info=WebUtil.parseObject(clientInfoCache,OauthClientInfo.class);
            //验证请求clientId
            if(StringUtils.equals(token.getClient_id(),info.getClientId())){
                tokenResult.setSuccess(false);
                tokenResult.setMsg("clientId与授权code步骤中的不匹配！");
                return new ResponseEntity<>(tokenResult, HttpStatus.OK);
            }
            //验证请求redirectUrl
            if(StringUtils.equals(token.getRedirect_uri(),info.getCacheRedirectUrl())){
                tokenResult.setSuccess(false);
                tokenResult.setMsg("redirect_uri与授权code步骤中的不匹配！");
                return new ResponseEntity<>(tokenResult, HttpStatus.OK);
            }
            //验证请求secret
            if(StringUtils.equals(token.getClient_secret(),info.getClientSecret())){
                tokenResult.setSuccess(false);
                tokenResult.setMsg("secret客户端秘钥与授权code步骤中的不匹配！");
                return new ResponseEntity<>(tokenResult, HttpStatus.OK);
            }
            //生成token
            JwtHead head=new JwtHead();
            JwtPlayLoad playLoad=new JwtPlayLoad();
            playLoad.setClientId(token.getClient_id());
            Date iatD=new Date();
            Date expD=DateUtil.getDateAddMin(60);
            Date refreshExpD=DateUtil.getDateAddMin(720);
            playLoad.setIat(DateUtil.dateFormat(iatD,DateUtil.patternDateTime));//签发时间
            playLoad.setExp(DateUtil.dateFormat(expD,DateUtil.patternDateTime));//过期时间
            playLoad.setNbf(DateUtil.dateFormat(iatD,DateUtil.patternDateTime));//生效时间
            playLoad.setAud(info.getClientName());
            playLoad.setScopes(StringUtils.join(info.getCacheScopes(), ","));
            String jwtToken=JwtTokenUtil.createToken(
                    iatD,
                    expD,
                    WebUtil.beanToMap(head),
                    WebUtil.beanToMapStr(playLoad),
                    info.getClientSecret());
            String refreshToken =Md5Util.MD5(jwtToken+System.currentTimeMillis());
            OauthClientToken oauthClientToken=new OauthClientToken();
            oauthClientToken.setClientId(info.getClientId());
            oauthClientToken.setAccessExpired(expD);
            oauthClientToken.setAccessToken(jwtToken);
            oauthClientToken.setRefreshToken(refreshToken);
            oauthClientToken.setRefreshExpired(refreshExpD);
            oauthClientToken.setUserId(1L);
            oauthClientToken.setStatus("0");
            int i=oauthClientTokenService.addOauthClientToken(oauthClientToken);
            return null;
        } catch (JsontoObjException e) {
            logger.error("error:"+e);
            tokenResult.setSuccess(false);
            tokenResult.setMsg("请求token发生异常！");
            return new ResponseEntity<>(tokenResult, HttpStatus.OK);
        }

    }

    @GetMapping("/index")
    public String index(){
        return "/page/index.html";

    }
}
