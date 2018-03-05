package oauth.service.db1;

import oauth.entity.OauthApprovals;
import oauth.mapper.OauthApprovalsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OauthApprovalsService {

    @Autowired
    private OauthApprovalsMapper oauthApprovalsMapper;

    public OauthApprovals selectOne(OauthApprovals approvals){
        return oauthApprovalsMapper.selectOne(approvals);
    }
}
