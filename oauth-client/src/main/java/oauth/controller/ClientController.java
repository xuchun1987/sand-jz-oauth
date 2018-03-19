package oauth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/client")
@Controller
public class ClientController {
    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 授权服务器通知客户端的回调code地址
     * @param code
     * @param state
     * @return
     */
    @ResponseBody
    @RequestMapping("/code")
    public String getCode(String code,String state){
        logger.info("---------获取到的code："+code);
        logger.info("---------获取到的state："+state);

        //这边开始向授权服务器获取token操作,然后根据state存放返回的token

        return "success";
    }


    /**
     * 授权成功后的回跳页面
     * @param state
     * @return
     */
    @RequestMapping("/page")
    public ModelAndView authorPage(String state){
        //根据当前的登录用户userId获取对应的state值，然后根据
        return null;
    }
}
