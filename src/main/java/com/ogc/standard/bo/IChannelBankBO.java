package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.ChannelBank;

/**
 * 渠道银行
 * @author: xieyj 
 * @since: 2016年11月10日 下午8:30:39 
 * @history:
 */
public interface IChannelBankBO extends IPaginableBO<ChannelBank> {

    // 根据编号查询渠道银行是否存在
    public boolean isChannelBankExist(Long id);

    // 新增渠道银行
    public int saveChannelBank(ChannelBank data);

    // 删除
    public int removeChannelBank(Long id);

    // 列表查询
    public List<ChannelBank> queryChannelBankList(ChannelBank condition);

    // 详情查询
    public ChannelBank getChannelBank(Long id);

    // 根据银行编号
    public ChannelBank getChannelBank(String bankCode);

    // 更新
    public void refreshChannelBank(ChannelBank data, String bankCode,
            String bankName, String updater, String remark);

}
