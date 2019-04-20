package fun.flyee.sunshine4u.android.models;

import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

import java.util.List;

import fun.flyee.sunshine4u.android.cache.ConfigCache;
import fun.flyee.sunshine4u.android.widgets.Toaster;

public class News extends Model {

    public final static int TYPE_FAV = 1;
    public final static int TYPE_RECENT = 2;

    @Column(name = "id")
    public Long id;
    @Column(name = "title")
    public String title;
    @Column(name = "time")
    public String time;
    @Column(name = "src")
    public String src;
    @Column(name = "category")
    public String category;
    @Column(name = "pic")
    public String pic;
    @Column(name = "content")
    public String content;
    @Column(name = "url")
    public String url;
    @Column(name = "weburl")
    public String weburl;
    @Column(name = "type")
    public Integer type;
    @Column(name = "action_time")
    public Long actionTime;
    @Column(name = "user_id")
    public Long userId;

    public News() {
        super();
    }

    @Override
    public Long getID() {
        return getDbId();
    }

    public void saveRecent(News news) {
        News exist = getByUrl(TYPE_RECENT, news.weburl);
        if (exist != null) {
            return;
        }
        news.type = TYPE_RECENT;
        news.userId = ConfigCache.get().loginUserId;
        news.actionTime = System.currentTimeMillis();
        news.save();
    }

    public void saveFav(News news) {
        News exist = getByUrl(TYPE_FAV, news.weburl);
        if (exist != null) {
            return;
        }
        news.type = TYPE_FAV;
        news.userId = ConfigCache.get().loginUserId;
        news.actionTime = System.currentTimeMillis();
        news.save();
        Toaster.show("已收藏");
    }

    public static News getByUrl(int type, String url) {
        From from = new Select().from(News.class)
                .where("weburl=?", url)
                .where("user_id=?", ConfigCache.get().loginUserId);
        if (type > 0) {
            from.where("type=?", type);
        }
        return from.executeSingle();
    }

    public static List<News> list(int type, int start, int size) {
        return new Select()
                .from(News.class)
                .where("type=?", type)
                .where("user_id=?", ConfigCache.get().loginUserId)
                .orderBy("action_time desc")
                .limit(size)
                .offset(start)
                .execute();
    }

    public News saveByUrl() {
        News news = getByUrl(0, weburl);
        if (news == null){
            save();
            return this;
        }
        return news;
    }

}
