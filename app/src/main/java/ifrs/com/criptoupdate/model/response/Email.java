package ifrs.com.criptoupdate.model.response;

/**
 * Created by diego on 20/11/2017.
 */

public class Email {

    private User user;
    private Msg msg;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }


}
