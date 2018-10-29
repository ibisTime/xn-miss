package com.ogc.standard.dto.req;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 取现回录
 * @author: xieyj 
 * @since: 2018年10月6日 下午1:32:59 
 * @history:
 */
public class XN803754Req {

    @NotBlank
    private String accountNumber;

    @NotBlank
    @Min(0)
    private String amount;

    @NotBlank
    private String withDate;

    @NotBlank
    private String channelOrder;

    private String withNote;

    @NotBlank
    private String updater;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getWithDate() {
        return withDate;
    }

    public void setWithDate(String withDate) {
        this.withDate = withDate;
    }

    public String getWithNote() {
        return withNote;
    }

    public void setWithNote(String withNote) {
        this.withNote = withNote;
    }

    public String getChannelOrder() {
        return channelOrder;
    }

    public void setChannelOrder(String channelOrder) {
        this.channelOrder = channelOrder;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }
}
