package fun.flyee.sunshine4u.android.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

public class UserOrder extends Model {

    public final static int STATE_INIT = 0;
    public final static int STATE_SENDING = 1;
    public final static int STATE_COMPLETE = 2;
    public final static int STATE_CANCEL = 3;

    @Column(name = "id")
    public Long id;
    @Column(name = "user_id")
    public Long userId;
    @Column(name = "status")
    public Integer status;
    @Column(name = "mer_id")
    public Long merId;
    @Column(name = "mer_name")
    public String merName;
    @Column(name = "mer_pic")
    public Float merPic;
    @Column(name = "mer_price")
    public Float merPrice;
    @Column(name = "count")
    public Integer count;
    @Column(name = "amount")
    public Float amount;
    @Column(name = "time")
    public Long time;

    @Override
    public Long getID() {
        return getDbId();
    }
}
