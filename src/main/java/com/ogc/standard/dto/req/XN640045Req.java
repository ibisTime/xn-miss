/**
 * @Title XN640040Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年10月12日 下午3:28:19 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;


/** 
 * 分页查用户行为
 * @author: taojian 
 * @since: 2018年10月12日 下午3:28:19 
 * @history:
 */
public class XN640045Req extends APageReq {

    private static final long serialVersionUID = -5148706971362241471L;

    // 类型
    private String type;

    // 对象类型
    private String toType;

    // 对象编号
    private String toCode;

    // 操作人编号
    private String creater;

    private String createDatetimeStart;

    private String createDatetimeEnd;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
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
}
