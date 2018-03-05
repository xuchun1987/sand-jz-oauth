package oauth.common;

import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public class ModelResult {


    /**
     * xuchun
     * 2018-02-26
     * 返回视图
     * @param mv
     * @param path
     * @param key
     * @param msg
     * @return
     */
    public static ModelAndView toMv(ModelAndView mv,String path,String key,String msg){
        mv.setViewName(path);
        mv.addObject(key,msg);
        return mv;
    }

    public static ModelAndView toMv(ModelAndView mv,Map<String,Object> params){
        mv.addAllObjects(params);
        return mv;
    }
}
