package fun.flyee.sunshine4u.android.events;

import android.os.Bundle;

public class LoginEvent {
    public final static int ACTION_GO_REGISTER = 0;
    public final static int ACTION_GO_AVATAR = 1;
    public final static int ACTION_GO_INTEREST = 2;
    public final static int ACTION_GO_LOGIN = 3;
    public final static int ACTION_DO_REGISTER = 4;
    public final static int ACTION_DO_LOGIN = 5;
    public final static int ACTION_DO_INTEREST = 6;
    public final static int ACTION_DO_AVATAR = 7;

    public int action;
    public Bundle data;

    public LoginEvent(int action, Bundle data) {
        this.action = action;
        this.data = data;
    }
}
