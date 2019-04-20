package fun.flyee.sunshine4u.android.modules.mall;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fun.flyee.sunshine4u.android.R;

public class MallPanelView extends LinearLayout {

    @BindView(R.id.mall_banner)
    public Banner banner;

    public MallPanelView(Context context) {
        super(context);
    }

    public MallPanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MallPanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        ButterKnife.bind(this, this);
        banner.setImageLoader(new GlideImageLoader());
        List<String> images = Arrays.asList("https://m.360buyimg.com/babel/jfs/t1/30164/23/11562/42132/5cb5a039E43088360/3f1338f29921e89f.jpg"
                , "https://m.360buyimg.com/babel/jfs/t1/29405/28/13685/93103/5ca1ab7cEfa0ac9c0/6668b857ca2b8744.jpg"
                , "https://m.360buyimg.com/babel/jfs/t1/30840/28/12481/70601/5cb7d233Ea3189218/26a6eeeb66a6d32a.jpg");
        banner.setImages(images);
        banner.setDelayTime(10000);
        banner.start();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }
}
