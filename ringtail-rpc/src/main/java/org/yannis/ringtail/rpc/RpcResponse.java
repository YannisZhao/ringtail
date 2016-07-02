package org.yannis.ringtail.rpc;

/**
 * Created by dell on 2016/7/1.
 */
public class RpcResponse {
    private String requestId;
    private Object content;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
