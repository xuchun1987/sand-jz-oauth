package oauth.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
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
@TableName("oauth_users")
public class OauthUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户名
     */
	private String username;
    /**
     * 密码
     */
	private String password;
    /**
     * 姓名
     */
	private String name;
    /**
     * 身份证
     */
	private String idcard;
    /**
     * 邮箱
     */
	private String email;
    /**
     * 手机
     */
	private String phone;
    /**
     * 系统id 字典表id
     */
	private String sysid;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OauthUsers{" +
			", id=" + id +
			", username=" + username +
			", password=" + password +
			", name=" + name +
			", idcard=" + idcard +
			", email=" + email +
			", phone=" + phone +
			", sysid=" + sysid +
			", status=" + status +
			"}";
	}
}
