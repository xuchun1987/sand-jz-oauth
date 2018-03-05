package oauth.test;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import oauth.DaoRun;
import oauth.entity.OauthDictItems;
import oauth.mapper.OauthDictItemsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes= DaoRun.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class DbTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OauthDictItemsMapper oauthDictItemsMapper;

    @Test
    public void test1(){

        List<OauthDictItems> list=oauthDictItemsMapper.selectPage(
                new Page<OauthDictItems>(1,10),
                new EntityWrapper<OauthDictItems>());
        for(OauthDictItems items:list){
            logger.info(items.getText());
        }

        logger.info("---------------test1");
    }
}
