/**
 * @Title ActionAOImpl.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月12日 下午2:45:20 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IActionAO;
import com.ogc.standard.bo.IActionBO;
import com.ogc.standard.bo.IPlayerBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Action;
import com.ogc.standard.domain.Player;
import com.ogc.standard.enums.EActionType;
import com.ogc.standard.enums.EPlayerStatus;
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

    @Override
    public String addAction(String type, String toType, String toCode,
            String creater) {

        userBO.getUser(creater);
        Player player = playerBO.getPlayer(toCode);
        if (!EPlayerStatus.UP.getCode().equals(player.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "该选手无法进行此操作");
        }
        if (EActionType.ATTENTION.getCode().equals(type)) {
            playerBO.addAttention(player);
        } else if (EActionType.SHARE.getCode().equals(type)) {
            playerBO.addShare(player);
        } else if (EActionType.FOOT.getCode().equals(type)) {
            playerBO.addScan(player);
        } else {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "暂不支持此操作");
        }
        return actionBO.saveAction(type, toType, toCode, creater);
    }

    @Override
    public Paginable<Action> queryActionPage(int start, int limit,
            Action condition) {

        return actionBO.getPaginable(start, limit, condition);
    }

    @Override
    public Action getAction(String code) {
        return actionBO.getAction(code);
    }

}
