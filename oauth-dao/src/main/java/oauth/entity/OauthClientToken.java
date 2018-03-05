package oauth.entity;

import java.util.Date;
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
@TableName("oauth_client_token")
public class OauthClientToken implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	private Long id;
    /**
     * 客户端标识
     */
	@TableField("client_id")
	private String clientId;
    /**
     * 用户标识
     */
	@TableField("user_id")
	private Long userId;
    /**
     * access_token
     */
	@TableField("access_token")
	private String accessToken;
    /**
     * refresh_token
     */
	@TableField("refresh_token")
	private String refreshToken;
    /**
     * access过期时间
     */
	@TableField("access_expired")
	private Date accessExpired;
    /**
     * refresh过期时间
     */
	@TableField("refresh_expired")
	private Date refreshExpired;
    /**
     * 状态
     */
	private String status;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Date getAccessExpired() {
		return accessExpired;
	}

	public void setAccessExpired(Date accessExpired) {
		this.accessExpired = accessExpired;
	}

	public Date getRefreshExpired() {
		return refreshExpired;
	}

	public void setRefreshExpired(Date refreshExpired) {
		this.refreshExpired = refreshExpired;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OauthClientToken{" +
			", id=" + id +
			", clientId=" + clientId +
			", userId=" + userId +
			", accessToken=" + accessToken +
			", refreshToken=" + refreshToken +
			", accessExpired=" + accessExpired +
			", refreshExpired=" + refreshExpired +
			", status=" + status +
			"}";
	}
}
