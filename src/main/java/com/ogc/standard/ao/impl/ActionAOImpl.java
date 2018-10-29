/**
 * @Title ActionAOImpl.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月12日 下午2:45:20 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IActionAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IActionBO;
import com.ogc.standard.bo.IPlayerBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.ITicketBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.AmountUtil;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.SysConstant;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.Action;
import com.ogc.standard.domain.Player;
import com.ogc.standard.domain.SYSConfig;
import com.ogc.standard.domain.Ticket;
import com.ogc.standard.domain.User;
import com.ogc.standard.enums.EActionType;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.EJourBizTypePlat;
import com.ogc.standard.enums.EJourBizTypeUser;
import com.ogc.standard.enums.EPlayerStatus;
import com.ogc.standard.enums.ESysUser;
import com.ogc.standard.enums.ETicketStatus;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

/** 
 * @author: taojian 
 * @since: 2018年10月12日 下午2:45:20 
 * @history:
 */
@Service
public class ActionAOImpl implements IActionAO {

    @Autowired
    private IActionBO actionBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IPlayerBO playerBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private ITicketBO ticketBO;

    @Transactional
    @Override
    public String addAction(String type, String toType, String toCode,
            String creater) {
        User user = userBO.getNormalUser(creater);
        Player player = playerBO.getPlayer(toCode);

        if (!EPlayerStatus.UP.getCode().equals(player.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "该选手无法进行此操作");
        }
        String code = null;
        String remark = user.getNickname() + "于"
                + DateUtil.getToday(DateUtil.DATA_TIME_PATTERN_7);
        if (EActionType.ATTENTION.getCode().equals(type)) {
            if (actionBO.isActionExist(creater, toCode, type)) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "已关注无需重复关注");
            }
            playerBO.addAttention(player);
            remark = remark + "关注了选手" + player.getCname();
            code = actionBO.saveAction(type, toType, toCode, creater, remark);
        } else if (EActionType.SHARE.getCode().equals(type)) {
            remark = remark + "分享了选手" + player.getCname();
            code = actionBO.saveAction(type, toType, toCode, creater, remark);
            if (!actionBO.isActionExist(creater, type)) {
                SYSConfig money = sysConfigBO
                    .getConfigValue(SysConstant.RETURN_MONEY);
                BigDecimal mulMoney = AmountUtil.mul(
                    StringValidater.toBigDecimal(money.getCvalue()), 1000L);
                accountBO.transAmountCZB(ESysUser.SYS_USER.getCode(),
                    ECurrency.CNY.getCode(), creater, ECurrency.CNY.getCode(),
                    mulMoney, EJourBizTypePlat.AJ_FX.getCode(),
                    EJourBizTypeUser.AJ_FX.getCode(),
                    EJourBizTypePlat.AJ_FX.getValue(),
                    EJourBizTypeUser.AJ_FX.getValue(), code);
            }
            playerBO.addShare(player);
        } else if (EActionType.FOOT.getCode().equals(type)) {
            remark = remark + "查看了选手" + player.getCname();
            code = actionBO.saveAction(type, toType, toCode, creater, remark);
            playerBO.addScan(player);
        } else {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "暂不支持此操作");
        }
        return code;
    }

    @Override
    @Transactional
    public void cancelAttation(String creater, String toCode) {
        Action data = actionBO.getActionByTypeToCodeCreater(
            EActionType.ATTENTION.getCode(), toCode, creater);
        actionBO.removeAction(data.getCode());
        Player player = playerBO.getPlayer(toCode);
        playerBO.subAttention(player);
    }

    @Override
    public Paginable<Action> queryActionPage(int start, int limit,
            Action condition) {
        Paginable<Action> paginable = actionBO.getPaginable(start, limit,
            condition);
        List<Action> list = paginable.getList();
        for (Action data : list) {
            init(data);
        }
        return paginable;
    }

    @Override
    public Action getAction(String code) {
        return actionBO.getAction(code);
    }

    public void init(Action data) {
        if (StringUtils.isNotBlank(data.getToCode())) {
            Player player = playerBO.getPlayer(data.getToCode());
            data.setPlayer(player);
        }
        if (StringUtils.isNotBlank(data.getCreater())) {
            User user = userBO.getUser(data.getCreater());
            data.setUser(user);
        }
        if (StringUtils.isNotBlank(data.getToCode())
                && StringUtils.isNotBlank(data.getCreater())) {
            Ticket condition = new Ticket();
            condition.setPlayerCode(data.getToCode());
            condition.setApplyUser(data.getCreater());
            condition.setStatus(ETicketStatus.PAYED.getCode());
            List<Ticket> queryTicketList = ticketBO.queryTicketList(condition);
            long myTicketSum = 0L;
            if (CollectionUtils.isNotEmpty(queryTicketList)) {
                for (Ticket ticket : queryTicketList) {
                    myTicketSum += ticket.getTicket();
                }
            }
            data.setMyTicketSum(myTicketSum);
        }
    }
}
