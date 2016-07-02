package org.yannis.ringtail.rpc;

import java.util.Map;

/**
 * Created by dell on 2016/7/1.
 */
public class RpcRequest {
    private String requestId;
    private String serviceName;
    private String methodName;
    private Map<Class, Object> parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Map<Class, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<Class, Object> parameters) {
        this.parameters = parameters;
    }
}
