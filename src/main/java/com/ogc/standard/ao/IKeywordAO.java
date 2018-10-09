package com.ogc.standard.ao;

import java.util.List;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Keyword;
import com.ogc.standard.dto.req.XN628000Req;

/**
 * @author: silver 
 * @since: 2018年8月22日 上午10:49:33 
 * @history:
 */
public interface IKeywordAO {
    static final String DEFAULT_ORDER_COLUMN = "id";

    // 批量增加关键字
    public void addKeywords(List<XN628000Req> reqList, String updater);

    // 添加关键字
    public void addKeyword(String word, String level, String reaction,
            String remark, String updater);

    // 删除关键字
    public void dropKeyword(Integer id);

    // 修改关键字
    public void editKeyword(Integer id, String word, String level,
            String reaction, String remark, String updater);

    public Paginable<Keyword> queryKeywordPage(int start, int limit,
            Keyword condition);

    public Keyword getKeyword(Integer id);

}
