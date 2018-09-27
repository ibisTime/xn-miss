package com.ogc.standard.ao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.ISimuMatchResultHistoryAO;
import com.ogc.standard.bo.ISimuMatchResultHistoryBO;
import com.ogc.standard.domain.SimuMatchResultHistory;

@Service
public class SimuMatchResultHistoryAOImpl implements ISimuMatchResultHistoryAO {

    @Autowired
    private ISimuMatchResultHistoryBO simuMatchResultHistoryBO;

    @Override
    public void querySimuMatchResultHistory(SimuMatchResultHistory condition) {

        simuMatchResultHistoryBO.querySimuMatchResultHistoryList(condition);
    }

}
