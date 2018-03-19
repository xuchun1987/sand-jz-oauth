package oauth.bean;

public class JwtHead {

    /**
     *标注信息
     */
    private String typ="JWT";

    /**
     *算法  HMAC SHA256
     */
    private String alg="HS256";


    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }
}
