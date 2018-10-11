package com.ogc.standard.ao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Player;
import com.ogc.standard.dto.req.XN640000Req;
import com.ogc.standard.dto.req.XN640002Req;

@Component
public interface IPlayerAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String addPlayer(XN640000Req req);

    public int editPlayer(XN640002Req data);

    public Paginable<Player> queryPlayerPage(int start, int limit,
            Player condition);

    public List<Player> queryPlayerList(Player condition);

    public Player getPlayer(String code);

    public void approve(String code, String approveResult, String approver,
            String remark);

    public void upPlayer(String code, String location, String orderNo,
            String updater);

    public void downPlayer(String code, String updater);

}
