package fun.flyee.sunshine4u.android.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

import fun.flyee.sunshine4u.android.cache.ConfigCache;

public class User extends Model {

    public final static int GENDER_MALE = 1;
    public final static int GENDER_FEMALE = 2;

    @Column(name = "id")
    public Long id;

    @Column(name = "login_name")
    public String loginName;

    @Column(name = "nick_name")
    public String nickName;

    @Column(name = "password")
    public String password;

    @Column(name = "avatar")
    public String avatar;

    @Column(name = "gender")
    public Integer gender = 0;

    @Column(name = "desc")
    public String desc;

    @Column(name = "zodiac")
    public Integer zodiac;

    @Column(name = "point")
    public Integer point = 0;

    @Column(name = "check_in_time")
    public Long checkInTime = 0l;

    @Column(name = "check_in_count")
    public Integer checkInCount = 0;

    @Override
    public Long getID() {
        return getDbId();
    }

    public static User getUserByName(String loginName) {
        User user = new Select().from(User.class).where("login_name=?", loginName).executeSingle();
        return user;
    }

    public static User getLoginUser() {
        if (ConfigCache.get().loginUserId != null && ConfigCache.get().loginUserId > 0) {
            return Model.load(User.class, ConfigCache.get().loginUserId);
        }
        return null;
    }
}
