package oauth.entity;

import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("oauth_scope")
public class OauthScope implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="scope_id", type= IdType.AUTO)
	private Integer scopeId;
	@TableField("scope_code")
	private String scopeCode;
    /**
     * 权限名称
     */
	@TableField("scope_name")
	private String scopeName;
    /**
     * 状态
     */
	private String status;


	public Integer getScopeId() {
		return scopeId;
	}

	public void setScopeId(Integer scopeId) {
		this.scopeId = scopeId;
	}

	public String getScopeCode() {
		return scopeCode;
	}

	public void setScopeCode(String scopeCode) {
		this.scopeCode = scopeCode;
	}

	public String getScopeName() {
		return scopeName;
	}

	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OauthScope{" +
			", scopeId=" + scopeId +
			", scopeCode=" + scopeCode +
			", scopeName=" + scopeName +
			", status=" + status +
			"}";
	}
}
