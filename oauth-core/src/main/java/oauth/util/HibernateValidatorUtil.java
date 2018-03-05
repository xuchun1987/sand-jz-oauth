package oauth.util;


import oauth.common.ValidResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


/**
 * hibernateValidator验证工具类
 * @author xuchun
 * @date 2016-07-12
 */
public class HibernateValidatorUtil {
	private static Validator validator=null;
	
	static{
		 ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	     validator = factory.getValidator();
	}
	
	/**
	 * hibernateValidator验证
	 * xuchun
	 * 2017-07-12
	 * @param obj
	 * @return
	 */
	public  static ValidResult validate(Object obj){
		ValidResult vr=new ValidResult();
		if(obj==null){
			vr.setPass(false);
			vr.getListMsg().add("映射后的参数实体为空！");
			return vr;
		}

		vr.setObj(obj);
		 Set<ConstraintViolation<Object>> constraintViolations =validator.validate(obj);
		 if(constraintViolations!=null&&constraintViolations.size()>0){
			 vr.setPass(false);
		 }else{
			 vr.setPass(true);
			 return vr;
		 }
	     for(ConstraintViolation<Object> c:constraintViolations){
	        	//System.out.println(c.getPropertyPath()+c.getMessage());
	    	 vr.getListMsg().add(c.getMessage());
	     }
	     return vr;   
	}
	
}
