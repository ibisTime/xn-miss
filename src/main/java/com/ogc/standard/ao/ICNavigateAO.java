package com.ogc.standard.ao;

import java.util.List;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.CNavigate;

public interface ICNavigateAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    // 新增导航
    public String addCNavigate(CNavigate data);

    // 删除导航
    public void dropCNavigate(String code);

    // 修改导航
    public void editCNavigate(CNavigate data);

    // 总部/地方分页查询
    public Paginable<CNavigate> queryCNavigatePage(int start, int limit,
            CNavigate condition);

    // 列表查询
    public List<CNavigate> queryCNavigateList(CNavigate condition);

    // 详情查询
    public CNavigate getCNavigate(String code);

    public void Release(String code, String location, Integer orderNo,
            String remark);

    public void obtain(String code, String remark);
}
