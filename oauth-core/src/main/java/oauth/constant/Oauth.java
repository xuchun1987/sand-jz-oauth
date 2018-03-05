package oauth.constant;

public enum Oauth {

	/**
	 * 表：oauth_client_info
	 * 字段：auto_approve
	 */
	用户授权("1"),
	自动授权("2");


	private final String value;


	private Oauth(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
	
	public String toString() {
		return this.value.toString();
	}
}
