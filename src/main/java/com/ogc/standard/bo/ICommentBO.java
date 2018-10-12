package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Comment;

/**
 * 评论表
 * @author: silver 
 * @since: 2018年8月22日 下午7:56:51 
 * @history:
 */
public interface ICommentBO extends IPaginableBO<Comment> {

    public boolean isCommentExist(String code);

    // 添加评论
    public String saveComment(String creater, String content,
            String playerCode, String status);

    // 删除评论
    public void removeComment(String code);

    // 审核评论
    public void refreshApproveComment(String code, String status,
            String approver, String approveNote);

    public List<Comment> queryCommentList(Comment condition);

    public List<Comment> queryCommentListByObjectCode(String objectCode,
            String userId);

    public Comment getComment(String code);

    public void searchCycleComment(String parentCode, List<Comment> list);

    public void initComment(String userId, Comment comment);

    public void orderCommentList(List<Comment> commentList, String userId);

}
