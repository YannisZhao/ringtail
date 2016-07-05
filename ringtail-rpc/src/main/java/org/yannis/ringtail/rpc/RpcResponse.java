package org.yannis.ringtail.rpc;

/**
 * Created by dell on 2016/7/1.
 */
public class RpcResponse {

    public static final RpcResponse SUCCESS = new RpcResponse(200, "OK");
    public static final RpcResponse CACHED = new RpcResponse(304, "Not modified");
    public static final RpcResponse BAD_REQUEST = new RpcResponse(400, "Bad request");
    public static final RpcResponse SERVICE_NOT_FOUND = new RpcResponse(404, "Service not found");
    public static final RpcResponse METHOD_NOT_FOUND = new RpcResponse(405, "Method not found");
    public static final RpcResponse SERVER_ERROR = new RpcResponse(500, "Server inner error");

    private String requestId;
    private int code;
    private String message;
    private Throwable error;
    private Object data;

    public RpcResponse(){}

    public RpcResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public RpcResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess(){
        return (this.code == 200 || this.code == 304) ? true : false;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
