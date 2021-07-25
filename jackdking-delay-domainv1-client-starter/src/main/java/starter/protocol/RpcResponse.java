package starter.protocol;

import java.io.Serializable;

public class RpcResponse implements Serializable{

    private static final long serialVersionUID = -4546652381492921069L;

    private String id;
    private Object data;
    // 0=success -1=fail
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "RpcResponse{" + "id='" + id + '\'' + ", data=" + data + ", status=" + status + '}';
    }
}