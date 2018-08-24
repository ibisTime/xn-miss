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
import com.ogc.standard.domain.Interact;
import com.ogc.standard.domain.User;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.ECommentStatus;
import com.ogc.standard.enums.ECommentType;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.EInteractType;
import com.ogc.standard.enums.EObjectType;
import com.ogc.standard.exception.BizException;

/**
 * 评论表
 * @author: silver 
 * @since: 2018年8月22日 下午8:14:28 
 * @history:
 */
@Component
public class CommentBOImpl extends PaginableBOImpl<Comment>
        implements ICommentBO {

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
    public String saveComment(String type, String parentCode,
            String parentUserId, String content, String status, String userId) {
        Comment data = new Comment();

        String code = OrderNoGenerater
            .generate(EGeneratePrefix.Comment.getCode());
        data.setCode(code);
        data.setType(type);
        data.setParentCode(parentCode);
        data.setParentUserId(parentUserId);

        data.setContent(content);
        data.setPointCount(0);
        data.setUserId(userId);
        data.setStatus(status);
        data.setCommentDatetime(new Date());

        if (ECommentType.POST.getCode().equals(type)) {
            data.setObjectCode(parentCode);
        }

        commentDAO.insert(data);
        return code;

    }

    @Override
    public void removeComment(String code, String updater) {
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
        condition.setObjectCode(objectCode);

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
                throw new BizException("xn0000", "评论记录不存在");
            }
        }
        return data;
    }

    public void initComment(String userId, Comment comment) {
        User user = userBO.getUser(comment.getUserId());
        comment.setNickname(user.getNickname());
        comment.setPhoto(user.getPhoto());
        if (StringUtils.isNotBlank(comment.getParentUserId())) {
            User parentUser = userBO.getUser(comment.getParentUserId());
            comment.setParentNickName(parentUser.getNickname());
            comment.setParentUserId(parentUser.getUserId());
            comment.setParentPhoto(parentUser.getPhoto());
        }

        if (comment.getParentCode().equals(comment.getObjectCode())) {
            comment.setIsTop(EBoolean.YES.getCode());
        } else {
            comment.setIsTop(EBoolean.NO.getCode());
        }

        comment.setIsPoint(EBoolean.NO.getCode());
        if (StringUtils.isNotBlank(userId)) {
            Interact data = interactBO.getInteract(
                EInteractType.POINT.getCode(), EObjectType.COMMENT.getCode(),
                comment.getCode(), userId);
            if (data != null) {
                comment.setIsPoint(EBoolean.YES.getCode());
            }
        }
    }
}
