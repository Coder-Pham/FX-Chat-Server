package Connection;

import java.io.Serializable;

public class Signal implements Serializable {
    private String action;
    private boolean status;
    private Object data;
    private String error;

    public Signal(String action, boolean status, Object data, String error) {
        this.action = action;
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
