package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Player;
import com.ogc.standard.dto.req.XN640000Req;
import com.ogc.standard.dto.req.XN640002Req;

public interface IPlayerBO extends IPaginableBO<Player> {

    public boolean isPlayerExistByMatchPlayCode(String MatchPlayCode);

    public String savePlayer(XN640000Req req);

    public void refreshApprove(Player data, String approveResult,
            String approver, String remark);

    public int refreshPlayer(Player data, XN640002Req req);

    public void refreshUpPlayer(Player data, String location, String orderNo,
            String updater);

    public void refreshDownPlayer(Player data, String updater);

    public void addPlayerTicket(Player player, Long ticket);

    public void addAttention(Player data);

    public void addShare(Player data);

    public void addScan(Player data);

    public List<Player> queryPlayerList(Player condition);

    public Player getPlayer(String code);

    public Player getPlayerForUpdate(String code);

    public void subAttention(Player data);

}
