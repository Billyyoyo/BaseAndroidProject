package fun.flyee.sunshine4u.android.models;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljy on 2018/3/16.
 */

public class NewsResponse implements Serializable{

    private String msg;
    private String status;
    private String result;
    private int total;
    private Object object;


    public String getMsg() {
        return msg == null ?"":msg;
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

    public Object getObject() {
        if(object == null){
            object = new Object();
        }
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    /**
     * 失败返回null
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getData(Class<T> clazz) {
        if (result != null) {
            try {
                return JSONObject.parseObject(JSONObject.toJSONString(result), clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 失败返回null
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getData(Class<T> clazz, T defaultValue) {
        if (result != null) {
            T t = null;
            try {
                t = JSONObject.parseObject(JSONObject.toJSONString(result), clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(t == null){
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * 如果data里是数组，直接这样获取
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getDatas(Class<T> clazz) {
        if (result == null) {
            return null;
        }
        String json = JSONArray.toJSONString(result);
        return JSONArray.parseArray(json, clazz);
    }
    /**
     * 如果data里是数组，直接这样获取
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> ArrayList<T> getDataList(Class<T> clazz) {
        ArrayList<T> newList = new ArrayList<>();
        newList.addAll(getDatas(clazz));
        return newList;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
