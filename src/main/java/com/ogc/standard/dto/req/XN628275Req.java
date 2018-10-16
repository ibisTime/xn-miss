/**
 * @Title XN628271Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年10月12日 上午11:18:05 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

/** 
 * 分页查评论
 * @author: taojian 
 * @since: 2018年10月12日 上午11:18:05 
 * @history:
 */
public class XN628275Req extends APageReq {

    private static final long serialVersionUID = 2322534099362456354L;

    // 类型
    private String type;

    // 评论内容
    private String content;

    // 状态
    private String status;

    // 对象编号
    private String toCode;

    // 评论时间起
    private String createDatetimeStart;

    // 评论时间止
    private String createDatetimeEnd;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDatetimeStart() {
        return createDatetimeStart;
    }

    public void setCreateDatetimeStart(String createDatetimeStart) {
        this.createDatetimeStart = createDatetimeStart;
    }

    public String getCreateDatetimeEnd() {
        return createDatetimeEnd;
    }

    public void setCreateDatetimeEnd(String createDatetimeEnd) {
        this.createDatetimeEnd = createDatetimeEnd;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

}
