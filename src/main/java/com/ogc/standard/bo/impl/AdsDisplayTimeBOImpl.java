package com.ogc.standard.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IAdsDisplayTimeBO;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dao.IDisplayTimeDAO;
import com.ogc.standard.domain.AdsDisplayTime;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

/**
 * Created by tianlei on 2017/十一月/15.
 */
@Component
public class AdsDisplayTimeBOImpl implements IAdsDisplayTimeBO {

    @Autowired
    IDisplayTimeDAO displayTimeDAO;

    @Override
    public void insertDisplayTime(AdsDisplayTime adsDisplayTime) {

        // 校验参数不能为 空
        ObjValidater.validateReq(adsDisplayTime);

        if (adsDisplayTime.getEndTime()
            .compareTo(adsDisplayTime.getStartTime()) < 0) {

            throw new BizException(EErrorCode_main.page_STARTENDTIME.getCode());

        }

        this.displayTimeDAO.insert(adsDisplayTime);

    }

    @Override
    public void deleteAdsDisplayTimeByAdsCode(String adsCode) {

        this.displayTimeDAO.deleteByAdsCode(adsCode);

    }

    public List<AdsDisplayTime> queryList(String adsCode) {

        AdsDisplayTime condition = new AdsDisplayTime();
        condition.setAdsCode(adsCode);
        return this.displayTimeDAO.selectList(condition);

    }

}
