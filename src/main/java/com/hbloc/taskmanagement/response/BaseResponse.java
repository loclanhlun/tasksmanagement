package com.hbloc.taskmanagement.response;

import java.util.List;

public class BaseResponse {
    private String resultCode;
    private Object resultDescription;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public Object getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(Object resultDescription) {
        this.resultDescription = resultDescription;
    }
}
