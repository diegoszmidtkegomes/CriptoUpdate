package ifrs.com.criptoupdate.model.response;

/**
 * Created by diego on 20/11/2017.
 */

public class EmailEnviado {


    private String msg;
    private String response;
    private Object err;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Object getErr() {
        return err;
    }

    public void setErr(Object err) {
        this.err = err;
    }
}
