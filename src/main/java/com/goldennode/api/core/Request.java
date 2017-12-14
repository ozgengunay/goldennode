package com.goldennode.api.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String method;
    private ArrayList<Object> params = new ArrayList<Object>();
    private RequestType requestType;
    private Server serverFrom;
    private int timeout;// seconds
    private String processId;

    Request() {
        id = java.util.UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getShortId() {
        return id.toString().substring(id.length() - 4, id.length());
    }

    public String getMethod() {
        return method;
    }

    void setMethod(String method) {
        this.method = method;
    }

    public Object[] getParams() {
        return params.toArray();
    }

    void addParams(Object param) {
        params.add(param);
    }

    void addParams(Object... params) {
        for (int i = 0; i < params.length; i++) {
            this.params.add(params[i]);
        }
    }

    public RequestType getRequestType() {
        return requestType;
    }

    void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public byte[] getBytes() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream gos;
            gos = new ObjectOutputStream(bos);
            gos.writeObject(this);
            gos.close();
            return bos.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    public Server getServerFrom() {
        return serverFrom;
    }

    void setServerFrom(Server serverFrom) {
        this.serverFrom = serverFrom;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    @Override
    public String toString() {
        return " > Request [id=" + getShortId() + ", method=" + method + ", params=" + params + ", requestType="
                + requestType + ", serverFrom=" + serverFrom + "] ";
    }
}
