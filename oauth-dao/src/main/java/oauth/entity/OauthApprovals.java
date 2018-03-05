package oauth.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
@TableName("oauth_approvals")
public class OauthApprovals implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 客户端ID
     */
	@TableField("client_id")
	private String clientId;
    /**
     * 作用域ID
     */
	@TableField("scope_codes")
	private String scopeCodes;
    /**
     * 用户ID
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 预期时间
     */
	@TableField("expires_time")
	private Date expiresTime;
    /**
     * 最后修改时间
     */
	@TableField("last_modified_time")
	private Date lastModifiedTime;
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



	public String getScopeCodes() {
		return scopeCodes;
	}

	public void setScopeCodes(String scopeCodes) {
		this.scopeCodes = scopeCodes;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(Date expiresTime) {
		this.expiresTime = expiresTime;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OauthApprovals{" +
			", id=" + id +
			", clientId=" + clientId +
			", scopeCodes=" + scopeCodes +
			", userId=" + userId +
			", expiresTime=" + expiresTime +
			", lastModifiedTime=" + lastModifiedTime +
			", status=" + status +
			"}";
	}
}
