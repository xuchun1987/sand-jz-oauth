package oauth.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import oauth.common.BCryptPasswordEncoder;
import oauth.exception.JsontoObjException;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WebUtil {

    public static String parseFastJson(Object object){

        if(object!=null){
            return JSON.toJSONString(object);
        }
        return null;

    }

    public static <T> List<T> parseFastList(String str,Class<T> clazz){
        return JSON.parseArray(str, clazz);
    }


    /**
     * json字符转map
     * xuchun
     * 2016-07-07
     * @param json
     * @return
     * @throws Exception
     */
    public static Map<String,Object> parseObject(String json)  throws JsontoObjException{
        try {
            return (Map<String,Object>)JSON.parseObject(json);
        } catch (Exception e) {
            throw new JsontoObjException(e);
        }
    }

    /**
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public  static <T> T parseObject(String text, Class<T> clazz) throws JsontoObjException{
        try {
            return JSON.parseObject(text,clazz);
        } catch (Exception e) {
            throw new JsontoObjException(e);
        }
    }

    /**
     * 判断b中的元素是否都是a中所存在的，只要有一个不在a中，即返回false
     * @param a
     * @param b
     * @return
     */
    public static boolean isContain(String[] a,String[] b){

        if(a==null||b==null)return false;

        for(int i=0;i<b.length;i++){
            if(!ArrayUtils.contains(a,b[i])){
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param text
     * @param sign
     * @return
     */
    public static boolean validPwd(String text,String sign){
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        return encoder.matches(text,sign);
    }

    /**
     * 实体类属性拷贝到map；类属性必须有默认值
     * xuchun
     * 2017-09-20
     * @param obj
     * @return
     */
    public static Map<String, Object> beanToMap(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> mappedObject = objectMapper.convertValue(obj, Map.class);
        return mappedObject;
    }



    /**
     * 实体类属性拷贝到map；类属性必须有默认值
     * xuchun
     * 2017-09-20
     * @param obj
     * @return
     */
    public static Map<String, String> beanToMapStr(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> mappedObject = objectMapper.convertValue(obj, Map.class);
        return mappedObject;
    }

}
