package fun.flyee.sunshine4u.android.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

import java.util.List;

import fun.flyee.sunshine4u.android.cache.ConfigCache;

public class Note extends Model {

    public final static int TYPE_NORMAL = 0;
    public final static int TYPE_HOPE = 1;
    public final static int TYPE_PLAN = 2;

    public final static int STATE_DELETE = -1;
    public final static int STATE_NORMAL = 0;
    public final static int STATE_COMPLETE = 1;

    public final static int NOTIFY_NONE = 0;
    public final static int NOTIFY_ALARM = 1;

    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    public Integer type;

    @Column(name = "state")
    public Integer state;

    @Column(name = "title")
    public String title;

    @Column(name = "icon")
    public String icon;

    @Column(name = "content")
    public String content;

    @Column(name = "user_id")
    public Long userId;

    @Column(name = "notify_time")
    public Long notifyTime;

    @Column(name = "notify_type")
    public Integer notifyType;

    @Column(name = "create_time")
    public Long createTime;

    @Column(name = "update_time")
    public Long updateTime;

    @Override
    public Long getID() {
        return getDbId();
    }

    public static List<Note> listNotes(int type, int state, int start, int size) {
        return new Select()
                .from(Note.class)
                .where("type=?", type)
                .where("state=?", state)
                .where("user_id=?", ConfigCache.get().loginUserId)
                .orderBy("update_time desc")
                .limit(size)
                .offset(start)
                .execute();
    }

    public static List<Note> listPlans(boolean isExpired, int start, int size) {
        From from = new Select()
                .from(Note.class)
                .where("type=?", TYPE_PLAN)
                .where("state=?", STATE_NORMAL)
                .where("user_id=?", ConfigCache.get().loginUserId);
        if (isExpired) {
            from.where("notify_time<?", System.currentTimeMillis())
                    .orderBy("notify_time desc");
        } else {
            from.where("notify_time>?", System.currentTimeMillis())
                    .orderBy("notify_time asc");
        }
        return from.limit(size)
                .offset(start)
                .execute();
    }

    public static List<Note> listHopes(boolean isComplete, int start, int size) {
        From from = new Select()
                .from(Note.class)
                .where("type=?", TYPE_HOPE)
                .where("user_id=?", ConfigCache.get().loginUserId);
        if (isComplete) {
            from.where("state=?", STATE_COMPLETE);
        } else {
            from.where("state=?", STATE_NORMAL);
        }
        return from.limit(size)
                .offset(start)
                .execute();
    }

    public static int countPlans() {
        From from = new Select()
                .from(Note.class)
                .where("type=?", TYPE_PLAN)
                .where("state=?", STATE_NORMAL)
                .where("user_id=?", ConfigCache.get().loginUserId)
                .where("notify_time>?", System.currentTimeMillis())
                .orderBy("notify_time asc");
        return from.count();
    }

    public static List<Note> listHopes(boolean isComplete) {
        From from = new Select()
                .from(Note.class)
                .where("type=?", TYPE_HOPE)
                .where("user_id=?", ConfigCache.get().loginUserId);
        if (isComplete) {
            from.where("state=?", STATE_COMPLETE);
        } else {
            from.where("state=?", STATE_NORMAL);
        }
        return from.execute();
    }

}
