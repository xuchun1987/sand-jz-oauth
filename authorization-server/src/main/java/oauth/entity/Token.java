package oauth.entity;

import org.hibernate.validator.constraints.NotEmpty;

public class Token {

    @NotEmpty(message="client_id不能为空")
    private String client_id;


    @NotEmpty(message="grant_type不能为空")
    private String grant_type;

    @NotEmpty(message="client_secret不能为空")
    private String client_secret;

    @NotEmpty(message="code不能为空")
    private String code;

    @NotEmpty(message="redirect_uri不能为空")
    private String redirect_uri;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }
}
