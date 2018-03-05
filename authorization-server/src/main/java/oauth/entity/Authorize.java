package oauth.entity;

import org.hibernate.validator.constraints.NotEmpty;

public class Authorize {

    @NotEmpty(message="response_type不能为空")
    private String response_type;

    @NotEmpty(message="client_id不能为空")
    private String client_id;

    private String scope;

    private String state;

    @NotEmpty(message="redirect_uri不能为空")
    private String redirect_uri;

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
