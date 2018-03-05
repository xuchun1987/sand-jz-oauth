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
@TableName("oauth_dict_items")
public class OauthDictItems implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 字典text
     */
	private String text;
    /**
     * 字典value
     */
	private String value;
    /**
     * 字典类型id
     */
	@TableField("type_id")
	private Integer typeId;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OauthDictItems{" +
			", id=" + id +
			", text=" + text +
			", value=" + value +
			", typeId=" + typeId +
			", status=" + status +
			"}";
	}
}
