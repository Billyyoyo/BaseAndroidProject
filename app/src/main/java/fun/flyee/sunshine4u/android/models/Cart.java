package fun.flyee.sunshine4u.android.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

public class Cart extends Model {

    @Column(name = "id")
    public Long id;
    @Column(name = "user_id")
    public Long userId;
    @Column(name = "mer_id")
    public Long merId;
    @Column(name = "mer_name")
    public String merName;
    @Column(name = "mer_pic")
    public String merPic;
    @Column(name = "mer_price")
    public String merPrice;
    @Column(name = "count")
    public Long count;

    @Override
    public Long getID() {
        return getDbId();
    }
}
