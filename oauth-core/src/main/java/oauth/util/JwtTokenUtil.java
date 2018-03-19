package oauth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class JwtTokenUtil {


    /**
     * xuchun
     * 2018-03-05
     * 生成jwt方式token
     * @param iatDate 签发时间
     * @param expDate 过期时间
     * @param header
     * @param payload
     * @param secret
     * @return
     * @throws Exception
     */
    public  static String createToken(Date iatDate,
                                      Date expDate,
                                      Map<String,Object> header,
                                      Map<String,String> payload,
                                      String secret){
        try {
            JWTCreator.Builder builder=JWT.create().withHeader(header);
            if(payload!=null){
                for(Iterator<String> it = payload.keySet().iterator();it.hasNext();){
                    String key=it.next();
                    builder.withClaim(key,payload.get(key));
                }
            }
            return builder.withExpiresAt(expDate).withIssuedAt(iatDate).sign(Algorithm.HMAC256(secret));
        } catch (UnsupportedEncodingException e) {
           return null;
        }

    }


    /**
     * xuchun
     * 2018-03-05
     * 验证token
     * @param token
     * @param secret
     * @return
     */
    public static Map<String,Claim>  verifyToken(String token,
                                                 String secret) {

        DecodedJWT jwt= null;
        try {
            JWTVerifier verifier=JWT.require(Algorithm.HMAC256(secret)).build();
            jwt = verifier.verify(token);
        } catch (JWTVerificationException e) {
            return null;
        }catch(UnsupportedEncodingException e){
            return null;
        }
        return jwt.getClaims();


    }
}
