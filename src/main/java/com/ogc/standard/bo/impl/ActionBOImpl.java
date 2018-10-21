package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IActionBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IActionDAO;
import com.ogc.standard.domain.Action;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

@Component
public class ActionBOImpl extends PaginableBOImpl<Action> implements IActionBO {

    @Autowired
    private IActionDAO actionDAO;

    @Override
    public boolean isActionExist(String userId, String toCode, String actionType) {
        Action condition = new Action();
        condition.setCreater(userId);
        condition.setToCode(toCode);
        condition.setType(actionType);
        List<Action> dataList = actionDAO.selectList(condition);
        if (dataList.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public String saveAction(String type, String toType, String toCode,
            String creater, String remark) {
        Action data = new Action();
        String code = OrderNoGenerater.generate(EGeneratePrefix.ACTION
            .getCode());
        data.setCode(code);
        data.setType(type);
        data.setToType(toType);
        data.setToCode(toCode);
        data.setCreater(creater);
        data.setCreateDatetime(new Date());
        data.setRemark(remark);
        actionDAO.insert(data);
        return code;
    }

    @Override
    public Action getAction(String code) {
        Action condition = new Action();
        condition.setCode(code);
        Action data = actionDAO.select(condition);
        if (null == null) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "该用户行为不存在");
        }
        return data;
    }

    @Override
    public Action getActionByTypeToCodeCreater(String type, String toCode,
            String creater) {
        Action condition = new Action();
        condition.setType(type);
        condition.setToCode(toCode);
        condition.setCreater(creater);
        Action data = actionDAO.select(condition);
        if (null == data) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "该用户行为不存在");
        }
        return data;
    }

    @Override
    public void removeAction(String code) {
        if (StringUtils.isNotBlank(code)) {
            Action data = new Action();
            data.setCode(code);
            actionDAO.delete(data);
        }
    }

}
