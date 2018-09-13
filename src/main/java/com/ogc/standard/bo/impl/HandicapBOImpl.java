package com.ogc.standard.bo.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IHandicapBO;
import com.ogc.standard.bo.ISimuOrderBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.IHandicapDAO;
import com.ogc.standard.domain.Handicap;
import com.ogc.standard.domain.HandicapGrade;
import com.ogc.standard.domain.SimuOrder;
import com.ogc.standard.enums.ESimuOrderDirection;

@Component
public class HandicapBOImpl extends PaginableBOImpl<Handicap>
        implements IHandicapBO {

    @Autowired
    private IHandicapDAO handicapDAO;

    @Autowired
    private ISimuOrderBO simuOrderBO;

    @Override
    public void stuffHandicap(String symbol, String toSymbol, String direction,
            int stuffHandicapQuantity) {

        // 查询当前盘口池内盘口数量
        Handicap condition = new Handicap();
        condition.setDirection(direction);
        condition.setSymbol(symbol);
        condition.setToSymbol(toSymbol);
        List<Handicap> handicaps = queryHandicapList(condition);

        // 查询数量
        int querryQuantity = 0;
        if (handicaps.size() < stuffHandicapQuantity) {
            querryQuantity = stuffHandicapQuantity - handicaps.size();
        } else {
            return;
        }

        if (querryQuantity != 0) {

            List<SimuOrder> simuOrders = new ArrayList<>();
            // 根据买卖方向 填充盘口
            if (ESimuOrderDirection.BUY.getCode().equals(direction)) {
                simuOrders = simuOrderBO.queryBidsHandicapList(querryQuantity,
                    symbol, toSymbol);
            } else {
                simuOrders = simuOrderBO.queryAsksHandicapList(querryQuantity,
                    symbol, toSymbol);
            }

            for (SimuOrder simuOrder : simuOrders) {

                // 重新获取盘口
                condition.setOrderCode(simuOrder.getCode());
                List<Handicap> currentHandicaps = queryHandicapList(condition);

                // 当前委托单不在盘口内时
                if (CollectionUtils.isEmpty(currentHandicaps)) {
                    saveHandicap(simuOrder);
                }

            }

        }

    }

    private void saveHandicap(SimuOrder simuOrder) {
        Handicap data = new Handicap();
        data.setOrderCode(simuOrder.getCode());
        data.setPrice(simuOrder.getPrice());
        data.setCount(simuOrder.getTotalCount());
        data.setDirection(simuOrder.getDirection());

        data.setSymbol(simuOrder.getSymbol());
        data.setToSymbol(simuOrder.getToSymbol());
        handicapDAO.insert(data);
    }

    @Override
    public int removeHandicap(String orderCode) {

        int count = 0;
        if (StringUtils.isNotBlank(orderCode)) {
            Handicap data = new Handicap();
            data.setOrderCode(orderCode);
            count = handicapDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshHandicap(Handicap data) {

        int count = 0;
        if (StringUtils.isNotBlank(data.getId() + "")) {
            count = handicapDAO.update(data);
        }
        return count;
    }

    @Override
    public List<HandicapGrade> queryHandicapList(String symbol, String toSymbol,
            String direction, int handicapGradeQuantity) {

        // 根据条件查找
        Handicap condition = new Handicap();
        condition.setDirection(direction);
        condition.setSymbol(symbol);
        condition.setToSymbol(toSymbol);
        condition.setOrder(ESimuOrderDirection.BUY.getCode().equals(direction)
                ? "price desc,id desc" : "price asc,id desc");
        List<Handicap> allHandicaps = queryHandicapList(condition);

        // 盘口档位
        List<HandicapGrade> handicapGrades = new ArrayList<>();

        // 获取前五档价格
        for (Handicap handicap : allHandicaps) {

            if (handicapGrades.size() == 0) {

                // 添加第一档位
                List<Handicap> handicaps = new ArrayList<>();
                handicaps.add(handicap);

                HandicapGrade handicapGrade = new HandicapGrade();
                handicapGrade.setPrice(handicap.getPrice());
                handicapGrade.setHandicapList(handicaps);
                handicapGrades.add(handicapGrade);
            } else {

                if (handicapGrades.get(handicapGrades.size() - 1).getPrice()
                    .compareTo(handicap.getPrice()) != 0) {

                    // 价格不等 新增档位
                    List<Handicap> handicaps = new ArrayList<>();
                    handicaps.add(handicap);

                    HandicapGrade handicapGrade = new HandicapGrade();
                    handicapGrade.setPrice(handicap.getPrice());
                    handicapGrade.setHandicapList(handicaps);
                    handicapGrades.add(handicapGrade);
                } else {

                    // 价格相等，往档位中增加盘口
                    handicapGrades.get(handicapGrades.size() - 1)
                        .getHandicapList().add(handicap);
                }
            }

            if (handicapGrades.size() >= handicapGradeQuantity) {
                // 档位限制
                break;
            }

        }

        return handicapGrades;
    }

    @Override
    public List<Handicap> queryHandicapList(Handicap condition) {
        return handicapDAO.selectList(condition);
    }

}
