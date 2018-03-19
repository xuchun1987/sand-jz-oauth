package oauth.service.db1;

import oauth.entity.OauthClientToken;
import oauth.mapper.OauthClientTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OauthClientTokenService {

    @Autowired
    private OauthClientTokenMapper oauthClientTokenMapper;


    /**
     * 保存客户端token
     * @param oauthClientToken
     * @return
     */
    public int addOauthClientToken(OauthClientToken oauthClientToken){

        return oauthClientTokenMapper.insert(oauthClientToken);
    }
}
