package com.ogc.standard.dto.req;

public class XN001200Req {
    // tokenId(必填)
    private String tokenId;

    // 拥有者(必填)
    private String userId;

    // 发送短信内容(必填)
    private String content;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
