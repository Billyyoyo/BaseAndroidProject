package fun.flyee.sunshine4u.android.models;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class NewResponse implements Serializable {
    private String msg;
    private String status;
    private String result;

    public NewResponse(String msg, String status, String result) {
        this.msg = msg;
        this.status = status;
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
