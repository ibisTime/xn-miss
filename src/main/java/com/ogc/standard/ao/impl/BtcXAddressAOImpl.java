package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IBtcXAddressAO;
import com.ogc.standard.bo.IBtcXAddressBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.BtcXAddress;
import com.ogc.standard.domain.User;

/** 
 * @author: taojian 
 * @since: 2018年9月11日 下午2:10:44 
 * @history:
 */
@Service
public class BtcXAddressAOImpl implements IBtcXAddressAO {

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IBtcXAddressBO btcXAddressBO;

    @Override
    public Paginable<BtcXAddress> queryBtcXAddressPage(int start, int limit,
            BtcXAddress condition) {

        Paginable<BtcXAddress> page = btcXAddressBO.getPaginable(start, limit,
            condition);

        if (null != page) {
            List<BtcXAddress> list = page.getList();
            for (BtcXAddress address : list) {
                User user = userBO.getUser(address.getUserId());
                address.setUserInfo(user);
            }
        }

        return page;
    }

}
