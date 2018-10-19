package com.ogc.standard.ao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Bankcard;
import com.ogc.standard.dto.req.XN802020Req;
import com.ogc.standard.dto.req.XN802022Req;
import com.ogc.standard.dto.req.XN802023Req;

/**
 * 
 * @author: lei 
 * @since: 2018年9月11日 下午5:41:25 
 * @history:
 */
@Component
public interface IBankcardAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String addBankcard(XN802020Req req);

    public void dropBankcard(String code);

    public void editBankcard(XN802022Req req);

    public void editBankcard(XN802023Req req);

    public Paginable<Bankcard> queryBankcardPage(int start, int limit,
            Bankcard condition);

    public List<Bankcard> queryBankcardList(Bankcard condition);

    public Bankcard getBankcard(String code);

    public void onOffBankcard(String code);

}
