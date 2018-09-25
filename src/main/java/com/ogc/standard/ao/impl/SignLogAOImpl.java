package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.ISignLogAO;
import com.ogc.standard.bo.ISignLogBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.SignLog;
import com.ogc.standard.dto.req.XN805140Req;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.ESignLogClient;
import com.ogc.standard.enums.ESignLogType;
import com.ogc.standard.exception.BizException;

@Service
public class SignLogAOImpl implements ISignLogAO {

    @Autowired
    private ISignLogBO signLogBO;

    @Override
    public int addSignLog(XN805140Req req) {
        if (signLogBO.isCheckIn(req.getUserId())) {
            throw new BizException(
                EErrorCode_main.signlog_TODAYSIGNED.getCode());
        }
        SignLog data = new SignLog();
        data.setClient(req.getClient());
        data.setIp(req.getIp());
        data.setLocation(req.getLocation());
        data.setUserId(req.getUserId());
        data.setType(ESignLogType.SIGN_IN.getCode());
        data.setClient(ESignLogClient.ANDROID.getCode());
        return signLogBO.saveSignLog(data);
    }

    @Override
    public Paginable<SignLog> querySignLogPage(int start, int limit,
            SignLog condition) {
        return signLogBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<SignLog> querySignLogList(SignLog condition) {
        return signLogBO.querySignLogList(condition);
    }

    @Override
    public long keepCheckIn(String userId) {
        SignLog condition = new SignLog();
        condition.setUserId(userId);
        List<SignLog> signLogList = signLogBO.querySignLogList(condition);
        // 排序
        signLogBO.ListSort(signLogList);
        // 没有签到数据返回0
        if (signLogList.size() == 0) {
            return 0;
        }
        // 今天没签到返回0
        if (!signLogBO.isCheckIn(userId)) {
            return 0;
        } else {
            long count = 1;
            long dayNum = 0;
            for (int i = 0; i < signLogList.size() - 1; i++) {
                // 获取连续数据的天数差（24*60*60*1000=86400000ms）
                dayNum = signLogList.get(i).getCreateDatetime().getTime()
                        / 86400000
                        - signLogList.get(i + 1).getCreateDatetime().getTime()
                                / 86400000;
                if (dayNum == 1) {
                    count++;
                } else {
                    break;
                }
            }

            return count;
        }
    }

}
