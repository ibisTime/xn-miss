package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 审核选手
 * @author: jiafr 
 * @since: 2018年10月11日 下午2:30:20 
 * @history:
 */
public class XN640001Req {

    // 编号
    @NotBlank
    private String code;

    // 审核结果
    @NotBlank
    private String approveResult;

    // 审核人
    @NotBlank
    private String approver;

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

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
