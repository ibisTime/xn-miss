package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ISimuMatchResultBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.ISimuMatchResultDAO;
import com.ogc.standard.domain.SimuMatchResult;
import com.ogc.standard.domain.SimuOrderDetail;

@Component
public class SimuMatchResultBOImpl extends PaginableBOImpl<SimuMatchResult>
        implements ISimuMatchResultBO {

    @Autowired
    private ISimuMatchResultDAO simuMatchResultDAO;

    public String saveSimuMatchResult(SimuMatchResult data) {
        String code = null;
        if (data != null) {
            simuMatchResultDAO.insert(data);
        }
        return code;
    }

    @Override
    public int removeSimuMatchResult(long id) {
        int count = 0;
        if (id > 0) {
            SimuMatchResult data = new SimuMatchResult();
            data.setId(id);
            count = simuMatchResultDAO.delete(data);
        }
        return count;
    }

    public int refreshSimuMatchResult(SimuMatchResult data) {
        int count = 0;
        count = simuMatchResultDAO.update(data);
        return count;
    }

    @Override
    public List<SimuMatchResult> querySimuMatchResultHistoryList(
            SimuMatchResult condition) {
        return simuMatchResultDAO.selectList(condition);
    }

    @Override
    public void doSimuMatchResult(SimuOrderDetail bidsOrderDetail,
            SimuOrderDetail asksOrderDetail) {
        SimuMatchResult data = null;
        if (null != bidsOrderDetail && null != asksOrderDetail) {

            // 根据买卖双方委托单编号搜索撮合结果，无则新增，有则更新
            SimuMatchResult condition = new SimuMatchResult();
            condition.setBuyOrderCode(bidsOrderDetail.getOrderCode());
            condition.setSellOrderCode(asksOrderDetail.getOrderCode());
            condition.setBuyOrderDetailCode(bidsOrderDetail.getCode());
            condition.setSellOrderDetailCode(asksOrderDetail.getCode());
            data = simuMatchResultDAO.select(condition);
            if (data == null) {

                data = new SimuMatchResult();
                data.setSymbol(bidsOrderDetail.getSymbol());
                data.setToSymbol(bidsOrderDetail.getToSymbol());
                data.setBuyOrderCode(bidsOrderDetail.getCode());
                data.setSellOrderCode(asksOrderDetail.getCode());
                data.setBuyUserId(bidsOrderDetail.getUserId());

                data.setSellUserId(asksOrderDetail.getUserId());
                // 交易数量（symbol数量）
                data.setSymbolCount(bidsOrderDetail.getTradedAmount());
                // 计价数量（toSymbol数量）
                data.setToSymbolCount(asksOrderDetail.getTradedCount());
                data.setBuyFee(bidsOrderDetail.getTradedFee());
                data.setSellFee(asksOrderDetail.getTradedFee());

                data.setCreateDatetime(new Date());

                saveSimuMatchResult(data);

            } else {

                data.setSymbolCount(bidsOrderDetail.getTradedAmount()
                    .add(data.getSymbolCount()));
                data.setToSymbolCount(asksOrderDetail.getTradedCount()
                    .add(data.getToSymbolCount()));
                data.setBuyFee(
                    bidsOrderDetail.getTradedFee().add(data.getBuyFee()));
                data.setSellFee(
                    asksOrderDetail.getTradedFee().add(data.getSellFee()));

                refreshSimuMatchResult(data);

            }
        }

    }

}
