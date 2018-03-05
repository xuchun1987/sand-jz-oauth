package oauth.service.db1;

import oauth.entity.OauthUsers;
import oauth.mapper.OauthUsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OauthUsersService {

    @Autowired
    private OauthUsersMapper oauthUsersMapper;

    public OauthUsers getUser(OauthUsers users){
        return oauthUsersMapper.selectOne(users);
    }
}
