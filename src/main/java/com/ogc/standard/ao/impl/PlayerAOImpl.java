package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IPlayerAO;
import com.ogc.standard.bo.IActionBO;
import com.ogc.standard.bo.ICommentBO;
import com.ogc.standard.bo.IPlayerBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.domain.Comment;
import com.ogc.standard.domain.Player;
import com.ogc.standard.domain.User;
import com.ogc.standard.dto.req.XN640000Req;
import com.ogc.standard.dto.req.XN640002Req;
import com.ogc.standard.enums.EActionToType;
import com.ogc.standard.enums.EActionType;
import com.ogc.standard.enums.EPlayerStatus;
import com.ogc.standard.exception.BizException;

@Service
public class PlayerAOImpl implements IPlayerAO {

    @Autowired
    private IPlayerBO playerBO;

    @Autowired
    private ICommentBO commentBO;

    @Autowired
    private IActionBO actionBO;

    @Autowired
    private IUserBO userBO;

    @Override
    public String addPlayer(XN640000Req req) {
        return playerBO.savePlayer(req);
    }

    @Override
    public void approvePlayer(String code, String approveResult,
            String approver, String remark) {
        Player data = playerBO.getPlayer(code);
        if (!EPlayerStatus.TO_APPROVE.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "选手不是待审核状态");
        }
        playerBO.refreshApprove(data, approveResult, approver, remark);
    }

    @Override
    public int editPlayer(XN640002Req req) {
        Player data = playerBO.getPlayer(req.getCode());
        if (!EPlayerStatus.DRAFT.getCode().equals(data.getStatus())
                && !EPlayerStatus.NO_PASS.getCode().equals(data.getStatus())
                && !EPlayerStatus.DOWN.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "选手不是可修改状态");
        }
        return playerBO.refreshPlayer(data, req);
    }

    @Override
    public void upPlayer(String code, String location, String orderNo,
            String updater) {
        Player data = playerBO.getPlayer(code);
        if (!EPlayerStatus.TO_UP.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "选手不是待上架状态");
        }
        playerBO.refreshUpPlayer(data, location, orderNo, updater);
    }

    @Override
    public void downPlayer(String code, String updater) {
        Player data = playerBO.getPlayer(code);
        if (!EPlayerStatus.UP.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "选手不是可下架状态");
        }
        playerBO.refreshDownPlayer(data, updater);
    }

    @Override
    public Paginable<Player> queryPlayerPage(int start, int limit,
            Player condition) {
        return playerBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<Player> queryPlayerList(Player condition) {
        return playerBO.queryPlayerList(condition);
    }

    @Override
    public Player getPlayer(String code, String userId) {
        Player player = playerBO.getPlayer(code);
        User user = userBO.getUser(userId);
        actionBO.saveAction(EActionType.FOOT.getCode(),
            EActionToType.PLAYER.getCode(), code, userId, user.getNickname()
                    + "于" + DateUtil.getToday(DateUtil.DATA_TIME_PATTERN_7)
                    + "查看了选手" + player.getCname());
        playerBO.addScan(player);
        List<Comment> commentList = commentBO
            .queryCommentListByObjectCode(player.getCode());
        player.setCommentList(commentList);
        return player;
    }
}
