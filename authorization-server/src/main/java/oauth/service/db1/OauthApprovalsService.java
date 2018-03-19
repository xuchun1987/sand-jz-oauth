package oauth.service.db1;

import oauth.entity.OauthApprovals;
import oauth.entity.OauthClientInfo;
import oauth.mapper.OauthApprovalsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OauthApprovalsService {

    @Autowired
    private OauthApprovalsMapper oauthApprovalsMapper;


    /**
     * 根据字段值，查询单条记录
     * @param approvals
     * @return
     */
    public OauthApprovals selectOne(OauthApprovals approvals){
        return oauthApprovalsMapper.selectOne(approvals);
    }

    /**
     * 插入或更新
     * update会忽略null值
     * @param newApprovals
     * @param oddApprovals
     * @return
     */
    public int saveOrUpdate(OauthApprovals newApprovals,OauthApprovals oddApprovals){
        oddApprovals=oauthApprovalsMapper.selectOne(oddApprovals);
        if(oddApprovals==null){//没有就插入
            return oauthApprovalsMapper.insert(newApprovals);
        }else{
            newApprovals.setId(oddApprovals.getId());
            return oauthApprovalsMapper.updateById(newApprovals);
        }
    }
}
