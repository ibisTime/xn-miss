package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IRankBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dao.IRankDAO;
import com.ogc.standard.domain.Rank;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.exception.BizException;

@Component
public class RankBOImpl extends PaginableBOImpl<Rank> implements IRankBO {

    @Autowired
    private IRankDAO rankDAO;

    @Override
    public void refreshManualAdjustment(Rank data, String fakeTicket,
            String updater, String remark) {
        data.setFakeTicketSum(data.getFakeTicketSum()
                + StringValidater.toInteger(fakeTicket));
        data.setUpdater(updater);
        data.setUpdateDatetime(new Date());
        data.setRemark(remark);
        rankDAO.updateManualAdjustment(data);
    }

    @Override
    public void refreshRanking(Rank data) {
        rankDAO.updateRanking(data);
    }

    @Override
    public boolean isRankExist(String code) {
        Rank condition = new Rank();
        condition.setCode(code);
        if (rankDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String saveRank(Rank data) {
        String code = null;
        if (data != null) {
            code = OrderNoGenerater.generate(EGeneratePrefix.RANK.getCode());
            data.setCode(code);
            rankDAO.insert(data);
        }
        return code;
    }

    @Override
    public int removeRank(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Rank data = new Rank();
            data.setCode(code);
            count = rankDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshRank(Rank data) {
        int count = 0;
        if (StringUtils.isNotBlank(data.getCode())) {
            count = rankDAO.update(data);
        }
        return count;
    }

    @Override
    public List<Rank> queryRankList(Rank condition) {
        return rankDAO.selectList(condition);
    }

    @Override
    public Rank getRank(String code) {
        Rank data = null;
        if (StringUtils.isNotBlank(code)) {
            Rank condition = new Rank();
            condition.setCode(code);
            data = rankDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "榜单不存在");
            }
        }
        return data;
    }

}
