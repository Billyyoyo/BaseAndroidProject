package fun.flyee.sunshine4u.android.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

public class Address extends Model {

    @Column(name = "id")
    public Long id;

    @Column(name = "province")
    public Long province;

    @Column(name = "city")
    public String city;

    @Column(name = "district")
    public String district;

    @Column(name = "street")
    public String street;

    @Column(name = "social")
    public String social;

    @Column(name = "area")
    public String area;

    @Column(name = "detail")
    public String detail;

    @Column(name = "phone")
    public String phone;

    @Column(name = "real_name")
    public String realName;

    @Override
    public Long getID() {
        return getDbId();
    }
}
