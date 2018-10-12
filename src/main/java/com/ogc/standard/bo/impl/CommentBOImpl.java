package com.ogc.standard.bo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ICommentBO;
import com.ogc.standard.bo.IInteractBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.ICommentDAO;
import com.ogc.standard.domain.Comment;
import com.ogc.standard.domain.User;
import com.ogc.standard.enums.ECommentStatus;
import com.ogc.standard.enums.ECommentType;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.exception.BizException;

/**
 * 评论表
 * @author: silver 
 * @since: 2018年8月22日 下午8:14:28 
 * @history:
 */
@Component
public class CommentBOImpl extends PaginableBOImpl<Comment> implements
        ICommentBO {

    @Autowired
    private ICommentDAO commentDAO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IInteractBO interactBO;

    @Override
    public boolean isCommentExist(String code) {
        Comment condition = new Comment();
        condition.setCode(code);
        if (commentDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String saveComment(String creater, String content,
            String playerCode, String status) {
        Comment data = new Comment();

        String code = OrderNoGenerater.generate(EGeneratePrefix.Comment
            .getCode());
        data.setCode(code);
        data.setParentCode(playerCode);
        data.setType(ECommentType.COMMENT.getCode());

        data.setCreater(creater);
        data.setContent(content);
        data.setToCode(playerCode);
        data.setStatus(status);
        data.setCreateDatetime(new Date());

        commentDAO.insert(data);
        return code;

    }

    @Override
    public void removeComment(String code) {
        if (StringUtils.isNotBlank(code)) {
            Comment data = new Comment();
            data.setCode(code);
            data.setStatus(ECommentStatus.DELETED.getCode());
            commentDAO.delete(data);
        }
    }

    @Override
    public void refreshApproveComment(String code, String status,
            String approver, String approveNote) {
        Comment data = new Comment();
        data.setCode(code);
        data.setStatus(status);
        data.setApprover(approver);
        data.setApproveDatetime(new Date());
        data.setRemark(approveNote);
        commentDAO.updateApproveComment(data);
    }

    @Override
    public List<Comment> queryCommentList(Comment condition) {
        return commentDAO.selectList(condition);
    }

    @Override
    public List<Comment> queryCommentListByObjectCode(String objectCode,
            String userId) {
        Comment condition = new Comment();
        List<String> statusList = new ArrayList<String>();

        statusList.add(ECommentStatus.APPROVED_YES.getCode());
        statusList.add(ECommentStatus.RELEASED.getCode());
        condition.setStatusList(statusList);
        condition.setToCode(objectCode);

        List<Comment> list = queryCommentList(condition);

        if (CollectionUtils.isNotEmpty(list)) {
            for (Comment comment : list) {
                initComment(userId, comment);
            }
        }
        return list;
    }

    @Override
    public Comment getComment(String code) {
        Comment data = null;
        if (StringUtils.isNotBlank(code)) {
            Comment condition = new Comment();
            condition.setCode(code);
            data = commentDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.comm_NOTEXIST.getCode());
            }
        }
        return data;
    }

    @Override
    public void searchCycleComment(String parentCode, List<Comment> list) {
        Comment condition = new Comment();
        condition.setParentCode(parentCode);
        List<String> statusList = new ArrayList<String>();
        statusList.add(ECommentStatus.RELEASED.getCode());
        statusList.add(ECommentStatus.APPROVED_YES.getCode());
        condition.setStatusList(statusList);
        condition.setOrder("comment_datetime", "asc");
        List<Comment> nextList = queryCommentList(condition);
        if (CollectionUtils.isNotEmpty(nextList)) {
            list.addAll(nextList);
            for (int i = 0; i < nextList.size(); i++) {
                searchCycleComment(nextList.get(i).getCode(), list);
            }
        }
    }

    @Override
    public void initComment(String userId, Comment comment) {
        User user = userBO.getUser(comment.getCreater());
        comment.setNickname(user.getNickname());
        comment.setPhoto(user.getPhoto());
    }

    @Override
    public void orderCommentList(List<Comment> commentList, String userId) {
        for (int i = 0; i < commentList.size(); i++) {
            for (int j = i + 1; j < commentList.size(); j++) {
                if (commentList.get(i).getCreateDatetime()
                    .after(commentList.get(j).getCreateDatetime())) {
                    Comment temp = new Comment();
                    temp = commentList.get(i);
                    commentList.set(i, commentList.get(j));
                    commentList.set(j, temp);
                }
            }
        }
        // 初始化参数
        for (Comment comment : commentList) {
            initComment(userId, comment);
        }
    }

}
