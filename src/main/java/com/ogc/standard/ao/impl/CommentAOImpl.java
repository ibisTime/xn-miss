package com.ogc.standard.ao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.ICommentAO;
import com.ogc.standard.bo.ICommentBO;
import com.ogc.standard.bo.IInteractBO;
import com.ogc.standard.bo.IKeywordBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Comment;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.ECommentStatus;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

/**
 * @author: silver 
 * @since: 2018年8月22日 下午8:29:21 
 * @history:
 */
@Service
public class CommentAOImpl implements ICommentAO {

    @Autowired
    private ICommentBO commentBO;

    @Autowired
    private IKeywordBO keywordBO;

    @Autowired
    private IInteractBO interactBO;

    // @Override
    // public XN628271Res commentComment(String commentCode, String content,
    // String userId) {
    // // 关键字过滤
    // List<Keyword> keywordList = keywordBO.checkContent(content);
    // String status = ECommentStatus.RELEASED.getCode();
    // String filterFlag = null;
    //
    // if (CollectionUtils.isNotEmpty(keywordList)) {
    //
    // // 直接拦截
    // if (EKeyWordReaction.REFUSE.getCode().equals(
    // keywordList.get(0).getReaction())) {
    // throw new BizException(EErrorCode_main.comm_KEYWORD.getCode(),
    // (Object) keywordList.get(0).getWord());
    // }
    //
    // // 替换**
    // if (EKeyWordReaction.REPLACE.getCode().equals(
    // keywordList.get(0).getReaction())) {
    // for (Keyword keyword : keywordList) {
    // content = keywordBO.replaceKeyword(content,
    // keyword.getWord());
    // }
    //
    // filterFlag = EFilterFlag.REPLACED.getCode();
    // }
    //
    // // 审核
    // if (EKeyWordReaction.APPROVE.getCode().equals(
    // keywordList.get(0).getReaction())) {
    // status = ECommentStatus.TO_APPROVE.getCode();
    // }
    // }
    //
    // if (ECommentStatus.RELEASED.getCode().equals(status)
    // && null == filterFlag) {
    // filterFlag = EFilterFlag.NORMAN.getCode();
    // } else if (ECommentStatus.TO_APPROVE.getCode().equals(status)) {
    // filterFlag = EFilterFlag.TO_APPROVE.getCode();
    // }
    //
    // String code = commentBO.saveComment(ECommentType.COMMENT.getCode(),
    // commentCode, userId, content, status, userId);
    //
    // return new XN628271Res(code, filterFlag);
    // }

    @Override
    @Transactional
    public void approveComment(String code, String approveResult,
            String approver, String approveNote) {
        Comment comment = commentBO.getComment(code);
        if (!ECommentStatus.TO_APPROVE.getCode().equals(comment.getStatus())) {
            throw new BizException(EErrorCode_main.comm_STATUS.getCode());
        }

        String status = null;
        if (EBoolean.YES.getCode().equals(approveResult)) {
            status = ECommentStatus.APPROVED_YES.getCode();

        } else {
            status = ECommentStatus.APPROVED_NO.getCode();
        }

        commentBO.refreshApproveComment(code, status, approver, approveNote);
    }

    @Override
    public void dropOssComment(String code, String updater) {
        Comment comment = commentBO.getComment(code);
        if (!ECommentStatus.DELETED.getCode().equals(comment.getStatus())) {
            throw new BizException(EErrorCode_main.comm_STATUS.getCode());
        }

        commentBO.removeComment(code);
    }

    @Override
    public void dropFrontComment(String code, String userId) {
        Comment comment = commentBO.getComment(code);
        if (!ECommentStatus.DELETED.getCode().equals(comment.getStatus())) {
            throw new BizException(EErrorCode_main.comm_STATUS.getCode());
        }
        if (!userId.equals(comment.getUserId())) {
            throw new BizException(EErrorCode_main.comm_USERRIGHTS.getCode());
        }

        commentBO.removeComment(code);
    }

    @Override
    public Paginable<Comment> queryCommentPage(int start, int limit,
            Comment condition) {
        Paginable<Comment> page = commentBO.getPaginable(start, limit,
            condition);
        List<Comment> resultList = page.getList();
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (Comment comment : resultList) {
                commentBO.initComment(null, comment);
            }
        }
        return page;
    }

    @Override
    public Paginable<Comment> queryMyCommentPage(int start, int limit,
            Comment condition, String userId) {
        Paginable<Comment> page = commentBO.getPaginable(start, limit,
            condition);
        List<Comment> resultList = page.getList();
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (Comment comment : resultList) {

                commentBO.initComment(null, comment);

                List<Comment> commentList = new ArrayList<Comment>();
                commentBO.searchCycleComment(comment.getCode(), commentList);
                commentBO.orderCommentList(commentList, userId);
                comment.setNextCommentList(commentList);
            }
        }
        return page;
    }

}
