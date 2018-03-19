package oauth.bean;

public class JwtPlayLoad {

    private String iss="sand";//jwt签发者

    private String sub="website";//jwt所面向的用户

    private String aud="client";//接收jwt的一方

    private String exp;//jwt的过期时间，这个过期时间必须要大于签发时间

    private String nbf;//定义在什么时间之前，该jwt都是不可用的

    private String iat;//jwt的签发时间

    private String jti;//jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击

    private String scopes;//授权范围

    private String clientId;//客户端标识

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getNbf() {
        return nbf;
    }

    public void setNbf(String nbf) {
        this.nbf = nbf;
    }

    public String getIat() {
        return iat;
    }

    public void setIat(String iat) {
        this.iat = iat;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
