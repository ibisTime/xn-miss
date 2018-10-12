package com.ogc.standard.dao;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.Comment;

/**
 * 评论
 * @author: silver 
 * @since: 2018年8月22日 下午7:48:45 
 * @history:
 */
public interface ICommentDAO extends IBaseDAO<Comment> {
    String NAMESPACE = ICommentDAO.class.getName().concat(".");

    // 审核评论
    public int updateApproveComment(Comment data);

}
