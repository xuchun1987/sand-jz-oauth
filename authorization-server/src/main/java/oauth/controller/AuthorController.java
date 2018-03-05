package oauth.controller;


import com.alibaba.fastjson.JSON;
import oauth.common.ResultEntity;
import oauth.entity.Authorize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @GetMapping(value="/authorize")
    public ResponseEntity<ResultEntity> authorize(
            @ModelAttribute("authorize")
                    Authorize authorize){
        logger.info("client_id："+authorize.getClient_id());
        logger.info("Redirect_uri："+authorize.getRedirect_uri());
        logger.info("Response_type："+authorize.getResponse_type());
        logger.info("State："+authorize.getState());
        ResultEntity entity=new ResultEntity();
        entity.setSuccess(true);
        entity.setCode("0000");
        entity.setMsg("成功");
        entity.setData(JSON.toJSONString(authorize));
        return new ResponseEntity<ResultEntity>(entity, HttpStatus.OK);
    }
}
