package oauth.service.db1;

import oauth.entity.OauthClientInfo;
import oauth.mapper.OauthClientInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OauthClientInfoService {
    @Autowired
    private OauthClientInfoMapper oauthClientInfoMapper;

    public OauthClientInfo selectOne(OauthClientInfo info){
        return oauthClientInfoMapper.selectOne(info);
    }
}
