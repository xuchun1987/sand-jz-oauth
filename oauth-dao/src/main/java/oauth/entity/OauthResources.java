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
@TableName("oauth_resources")
public class OauthResources implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("resource_id")
	private Integer resourceId;
    /**
     * 编码
     */
	private String code;
    /**
     * 名称
     */
	private String name;
    /**
     * 是否节点
     */
	@TableField("is_leaf")
	private Integer isLeaf;
    /**
     * 父id
     */
	private Integer pid;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 状态
     */
	private String status;


	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OauthResources{" +
			", resourceId=" + resourceId +
			", code=" + code +
			", name=" + name +
			", isLeaf=" + isLeaf +
			", pid=" + pid +
			", sort=" + sort +
			", status=" + status +
			"}";
	}
}
