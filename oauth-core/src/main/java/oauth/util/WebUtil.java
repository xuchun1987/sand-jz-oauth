package oauth.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

public class WebUtil {

    public static String parseFastJson(Object object){

        if(object!=null){
            return JSON.toJSONString(object);
        }
        return null;

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

}
