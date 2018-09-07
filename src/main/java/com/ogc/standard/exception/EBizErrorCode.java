package com.ogc.standard.exception;

/**
 * @author: haiqingzheng 
 * @since: 2017年11月14日 下午1:05:03 
 * @history:
 */
public enum EBizErrorCode {

    DEFAULT("xn625000", "业务异常"),

    BLOCK_GET_ERROR("1400", "获取区块数据失败");
    // PUSH_STATUS_UPDATE_FAILURE("eth000001","地址状态更新失败");

    private String code;

    private String info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    EBizErrorCode(String code, String info) {
        this.code = code;
        this.info = info;
    }
}
