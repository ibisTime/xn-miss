package com.ogc.standard.ao;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Comment;

/**
 * @author: silver 
 * @since: 2018年8月24日 下午2:33:06 
 * @history:
 */
public interface ICommentAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    // // 评论评论
    // public XN628271Res commentComment(String code, String content, String
    // userId);

    // 删除评论
    public void dropOssComment(String code, String updater);

    // 前端删除评论
    public void dropFrontComment(String code, String updater);

    // 审核评论
    public void approveComment(String code, String approveResult,
            String approver, String approveNote);

    public Paginable<Comment> queryCommentPage(int start, int limit,
            Comment condition);

    public Paginable<Comment> queryMyCommentPage(int start, int limit,
            Comment condition, String userId);

}
