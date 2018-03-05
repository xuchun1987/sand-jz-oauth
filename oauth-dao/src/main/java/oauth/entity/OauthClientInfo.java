package oauth.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xuchun
 * @since 2018-02-22
 */
@TableName("oauth_client_info")
public class OauthClientInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("client_id")
	private String clientId;
    /**
     * 客户端秘钥
     */
	@TableField("client_secret")
	private String clientSecret;
    /**
     * 客户端名称
     */
	@TableField("client_name")
	private String clientName;
    /**
     * 授权类型
     */
	@TableField("grant_type")
	private String grantType;
    /**
     * 重定向URI
     */
	@TableField("redirect_uri")
	private String redirectUri;
    /**
     * 过期时间
     */
	@TableField("access_token_validity")
	private Long accessTokenValidity;
    /**
     * 刷新过期时间
     */
	@TableField("refresh_token_validity")
	private Long refreshTokenValidity;
    /**
     * 自动授权
     */
	@TableField("auto_approve")
	private String autoApprove;
    /**
     * 状态
     */
	private String status;


	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public Long getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public void setAccessTokenValidity(Long accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	public Long getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	public void setRefreshTokenValidity(Long refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}

	public String getAutoApprove() {
		return autoApprove;
	}

	public void setAutoApprove(String autoApprove) {
		this.autoApprove = autoApprove;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OauthClientInfo{" +
			", clientId=" + clientId +
			", clientSecret=" + clientSecret +
			", clientName=" + clientName +
			", grantType=" + grantType +
			", redirectUri=" + redirectUri +
			", accessTokenValidity=" + accessTokenValidity +
			", refreshTokenValidity=" + refreshTokenValidity +
			", autoApprove=" + autoApprove +
			", status=" + status +
			"}";
	}
}
