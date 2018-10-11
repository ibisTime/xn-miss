/**
 * @Title EventAOImpl.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午12:56:55 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IEventAO;
import com.ogc.standard.bo.IEventBO;
import com.ogc.standard.bo.IReadBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Event;
import com.ogc.standard.dto.req.XN805300Req;
import com.ogc.standard.dto.req.XN805301Req;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.EEventStauts;
import com.ogc.standard.enums.EPublishType;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

/** 
 * @author: taojian 
 * @since: 2018年10月10日 下午12:56:55 
 * @history:
 */
@Service
public class EventAOImpl implements IEventAO {

    @Autowired
    private IEventBO eventBO;

    @Autowired
    private IReadBO readBO;

    @Override
    public String addEvent(XN805300Req req) {
        String code = null;
        if (EPublishType.SAVE.getCode().equals(req.getBizType())) {
            code = eventBO.saveEvent(req, EEventStauts.DRAFT.getCode());
        } else {
            code = eventBO.saveEvent(req, EEventStauts.TOAPPROVE.getCode());
        }
        return code;
    }

    @Override
    public void editEvent(XN805301Req req) {
        String status = eventBO.getEvent(req.getCode()).getStatus();
        // 状态判断
        if (!EEventStauts.DRAFT.getCode().equals(status)
                && !EEventStauts.FAILED.getCode().equals(status)
                && !EEventStauts.OFF.getCode().equals(status)) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "该赛事信息不能修改");
        }
        // 落地数据
        if (EPublishType.SAVE.getCode().equals(req.getBizType())) {
            eventBO.refreshEvent(req, EEventStauts.DRAFT.getCode());
        } else {
            eventBO.refreshEvent(req, EEventStauts.TOAPPROVE.getCode());
        }
    }

    @Override
    public void approveEvent(String code, String approver,
            String approveResult, String remark) {
        String status = eventBO.getEvent(code).getStatus();
        if (!EEventStauts.TOAPPROVE.getCode().equals(status)) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "该赛事信息不能审核");
        }

        if (EBoolean.YES.getCode().equals(approveResult)) {
            eventBO.refreshStatus(code, EEventStauts.PASS.getCode(), approver,
                remark);
        } else {
            eventBO.refreshStatus(code, EEventStauts.FAILED.getCode(),
                approver, remark);
        }
    }

    @Transactional
    @Override
    public void releaseEvent(String code, String updater, String remark) {
        String status = eventBO.getEvent(code).getStatus();
        if (!EEventStauts.PASS.getCode().equals(status)) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "该赛事信息不能上架");
        }

        eventBO.refreshStatus(code, EEventStauts.ON.getCode(), updater, remark);
        // 批量添加read数据
        readBO.saveToRead(code);

    }

    @Transactional
    @Override
    public void obtainEvent(String code, String updater, String remark) {
        String status = eventBO.getEvent(code).getStatus();
        if (!EEventStauts.ON.getCode().equals(status)) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "该赛事信息不能下架");
        }

        eventBO
            .refreshStatus(code, EEventStauts.OFF.getCode(), updater, remark);

        // 批量删除read数据
        readBO.refereshDelete(code);
    }

    @Override
    public Paginable<Event> queryEventPage(int start, int limit, Event condition) {
        return eventBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<Event> queryEventList(Event condition) {
        return eventBO.queryEventList(condition);
    }

    @Override
    public Event getEvent(String code) {
        return eventBO.getEvent(code);
    }

}
