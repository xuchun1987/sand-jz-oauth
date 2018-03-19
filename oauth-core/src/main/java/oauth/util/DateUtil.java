package oauth.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String patternDateTime="yyyy-MM-dd HH:mm:ss";

    /**
     * 获取多少分钟以后的时间
     * @param addMin
     * @return
     */
    public static Date getDateAddMin(int addMin){
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, addMin);
        return nowTime.getTime();
    }


    /**
     * 格式化日期
     * @param d
     * @param pattern
     * @return
     */
    public static String dateFormat(Date d,String pattern){
        DateFormat format=new SimpleDateFormat(pattern);
        return format.format(d);
    }

}
