package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.ICommentDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.Comment;

/**
 * 评论表
 * @author: silver 
 * @since: 2018年8月22日 下午7:50:32 
 * @history:
 */
@Repository("commentDAOImpl")
public class CommentDAOImpl extends AMybatisTemplate implements ICommentDAO {

    @Override
    public int insert(Comment data) {
        return super.insert(NAMESPACE.concat("insert_comment"), data);
    }

    @Override
    public int delete(Comment data) {
        return super.update(NAMESPACE.concat("update_deleteComment"), data);
    }

    @Override
    public int updateApproveComment(Comment data) {
        return super.update(NAMESPACE.concat("update_approveComment"), data);
    }

    @Override
    public Comment select(Comment condition) {
        return super.select(NAMESPACE.concat("select_comment"), condition,
            Comment.class);
    }

    @Override
    public long selectTotalCount(Comment condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_comment_count"),
            condition);
    }

    @Override
    public List<Comment> selectList(Comment condition) {
        return super.selectList(NAMESPACE.concat("select_comment"), condition,
            Comment.class);
    }

    @Override
    public List<Comment> selectList(Comment condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_comment"), start,
            count, condition, Comment.class);
    }

}
