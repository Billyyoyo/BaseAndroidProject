package fun.flyee.sunshine4u.android.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

import java.util.ArrayList;
import java.util.List;

public class Merchandise extends Model {

    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "type")
    public String type;

    @Column(name = "description")
    public String description;

    @Column(name = "content")
    public String content;

    @Column(name = "pic")
    public String pic;

    @Column(name = "origin_price")
    public Float originPrice;

    @Column(name = "sales_price")
    public Float salesPrice;

    @Column(name = "saled")
    public Integer saled;

    @Override
    public Long getID() {
        return getDbId();
    }

    public static Merchandise one() {
        Merchandise mer = new Merchandise();
        mer.id = 1l;
        mer.name = "维达纸巾";
        mer.description = "维达(Vinda) 抽纸 超韧3层130抽软抽*6包(小规格)";
        mer.pic = "https://img20.360buyimg.com/vc/jfs/t19021/57/1975377722/254728/9b963661/5adf3719N155e33ac.jpg";
        mer.content = "content";
        mer.originPrice = 18f;
        mer.salesPrice = 15f;
        mer.saled = 31;
        return mer;
    }

    public static List<Merchandise> all() {
        List<Merchandise> list = new ArrayList<>();
        list.add(one());
        list.add(one());
        list.add(one());
        list.add(one());
        return list;
    }
}
