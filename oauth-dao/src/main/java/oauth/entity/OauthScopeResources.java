package oauth.entity;

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
@TableName("oauth_scope_resources")
public class OauthScopeResources implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	private Integer id;
    /**
     * 权限作用域id
     */
	@TableField("scope_id")
	private Integer scopeId;
    /**
     * 资源id
     */
	@TableField("resource_id")
	private Integer resourceId;
    /**
     * 状态
     */
	private String status;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getScopeId() {
		return scopeId;
	}

	public void setScopeId(Integer scopeId) {
		this.scopeId = scopeId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OauthScopeResources{" +
			", id=" + id +
			", scopeId=" + scopeId +
			", resourceId=" + resourceId +
			", status=" + status +
			"}";
	}
}
