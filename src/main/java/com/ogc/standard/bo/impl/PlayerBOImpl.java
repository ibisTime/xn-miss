package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IPlayerBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IPlayerDAO;
import com.ogc.standard.domain.Player;
import com.ogc.standard.dto.req.XN640000Req;
import com.ogc.standard.dto.req.XN640002Req;
import com.ogc.standard.enums.EBizType;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.EPlayerStatus;
import com.ogc.standard.exception.BizException;

@Component
public class PlayerBOImpl extends PaginableBOImpl<Player> implements IPlayerBO {

    @Autowired
    private IPlayerDAO playerDAO;

    @Override
    public boolean isPlayerExist(String code) {
        Player condition = new Player();
        condition.setCode(code);
        if (playerDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isMatchPlayCodeExist(String code) {
        Player condition = new Player();
        condition.setMatchPlayCode(code);
        if (playerDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String savePlayer(XN640000Req req) {
        Player data = new Player();
        String code = OrderNoGenerater.generate(EGeneratePrefix.PLAYER
            .getCode());
        data.setCode(code);
        data.setMatch(req.getMatch());
        data.setMatchPlayCode(req.getMatchPlayCode());
        data.setCname(req.getCname());
        data.setEname(req.getEname());
        data.setNativePlace(req.getNativePlace());
        data.setHeight(req.getHeight());
        data.setWeight(req.getWeight());
        data.setXwei(req.getXwei());
        data.setYwei(req.getYwei());
        data.setTwei(req.getTwei());
        data.setDescription(req.getDescription());
        data.setListPic(req.getListPic());
        data.setBannerPics(req.getBannerPics());
        data.setPics(req.getPics());
        if (EBizType.SAVE.getCode().equals(req.getBizType())) {
            data.setStatus(EPlayerStatus.DRAFT.getCode());
        } else {
            data.setStatus(EPlayerStatus.TO_APPROVE.getCode());
        }
        data.setCreateDatetime(new Date());
        data.setUpdater(req.getUpdater());
        data.setUpdateDatetime(new Date());
        data.setRemark(req.getRemark());
        playerDAO.insert(data);
        return code;
    }

    @Override
    public void refreshApprove(Player data, String approveResult,
            String approver, String remark) {
        if (EBoolean.YES.getCode().equals(approveResult)) {
            data.setStatus(EPlayerStatus.TO_UP.getCode());
        } else {
            data.setStatus(EPlayerStatus.NO_PASS.getCode());
        }
        data.setUpdater(approver);
        data.setUpdateDatetime(new Date());
        data.setRemark(remark);
        playerDAO.updateApprove(data);
    }

    @Override
    public int refreshPlayer(Player data, XN640002Req req) {
        int count = 0;
        if (StringUtils.isNotBlank(data.getCode())) {
            data.setMatch(req.getMatch());
            data.setMatchPlayCode(req.getMatchPlayCode());
            data.setCname(req.getCname());
            data.setEname(req.getEname());
            data.setNativePlace(req.getNativePlace());
            data.setHeight(req.getHeight());
            data.setWeight(req.getWeight());
            data.setXwei(req.getXwei());
            data.setYwei(req.getYwei());
            data.setTwei(req.getTwei());
            data.setDescription(req.getDescription());
            data.setListPic(req.getListPic());
            data.setBannerPics(req.getBannerPics());
            data.setPics(req.getPics());
            if (EBizType.SAVE.getCode().equals(req.getBizType())) {
                data.setStatus(EPlayerStatus.DRAFT.getCode());
            } else {
                data.setStatus(EPlayerStatus.TO_APPROVE.getCode());
            }
            data.setUpdater(req.getUpdater());
            data.setUpdateDatetime(new Date());
            data.setRemark(req.getRemark());
            count = playerDAO.update(data);
        }
        return count;
    }

    @Override
    public void refreshUpPlayer(Player data, String location, String orderNo,
            String updater) {
        data.setStatus(EPlayerStatus.UP.getCode());
        data.setLocation(location);
        data.setOrderNo(orderNo);
        data.setUpdater(updater);
        data.setUpdateDatetime(new Date());
        playerDAO.updateUpPlayer(data);
    }

    @Override
    public void refreshDownPlayer(Player data, String updater) {
        data.setStatus(EPlayerStatus.DOWN.getCode());
        data.setUpdater(updater);
        data.setUpdateDatetime(new Date());
        playerDAO.updateDownPlayer(data);
    }

    @Override
    public List<Player> queryPlayerList(Player condition) {
        return playerDAO.selectList(condition);
    }

    @Override
    public Player getPlayer(String code) {
        Player data = null;
        if (StringUtils.isNotBlank(code)) {
            Player condition = new Player();
            condition.setCode(code);
            data = playerDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "选手不存在");
            }
        }
        return data;
    }

    @Override
    public void addAttention(Player data) {
        if (null == data.getAttentionSum()) {
            data.setAttentionSum(Long.valueOf(1));
        } else {
            data.setAttentionSum(data.getAttentionSum() + 1);
        }
    }

    @Override
    public void addShare(Player data) {
        if (null == data.getShareSum()) {
            data.setShareSum(Long.valueOf(1));
        } else {
            data.setShareSum(data.getShareSum() + 1);
        }
    }

    @Override
    public void addScan(Player data) {
        if (null == data.getScanSum()) {
            data.setScanSum(Long.valueOf(1));
        } else {
            data.setScanSum(data.getScanSum() + 1);
        }
    }

}
