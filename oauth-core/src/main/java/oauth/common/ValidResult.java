package oauth.common;


import java.util.ArrayList;
import java.util.List;

public class ValidResult {
	
	private boolean pass;//是否通过验证
	
	private String firstMsg;//不通过的第一个字段信息
	
	private Object obj;//保存验证的对象
	
	private List<String> listMsg=new ArrayList<String>();//所有不通过的所有字段信息
	
	
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public boolean getPass() {
		return pass;
	}
	public void setPass(boolean pass) {
		this.pass = pass;
	}
	public String getFirstMsg() {
		if(listMsg!=null&&listMsg.size()>0){
			return listMsg.get(0);
		}
		return firstMsg;
	}
	public void setFirstMsg(String firstMsg) {
		this.firstMsg = firstMsg;
	}
	public List<String> getListMsg() {
		return listMsg;
	}
	public void setListMsg(List<String> listMsg) {
		this.listMsg = listMsg;
	}
	
	

}
