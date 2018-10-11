/**
 * @Title XN640110Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年10月11日 下午2:30:57 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * 审核回复模版
 * @author: taojian 
 * @since: 2018年10月11日 下午2:30:57 
 * @history:
 */
public class XN640111Req {

    // 编号
    @NotBlank
    private String code;

    // 审核结果
    @NotBlank
    private String approveResult;

    // 更新人
    @NotBlank
    private String updater;

    // 备注
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getApproveResult() {
        return approveResult;
    }

    public void setApproveResult(String approveResult) {
        this.approveResult = approveResult;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
