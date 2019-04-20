package fun.flyee.sunshine4u.android.modules.mall;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fun.flyee.sunshine4u.android.R;

public class MallCategoryView extends LinearLayout {

    @BindView(R.id.left_category_title)
    public TextView leftTitle;
    @BindView(R.id.left_category_image)
    public ImageView leftImage;
    @BindView(R.id.right_category_title)
    public TextView rightTitle;
    @BindView(R.id.right_category_image)
    public ImageView rightImage;

    public MallCategoryView(Context context) {
        super(context);
    }

    public MallCategoryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MallCategoryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        ButterKnife.bind(this, this);
        Glide.with(getContext()).load("https://img11.360buyimg.com/mobilecms/s110x110_jfs/t1/20320/4/8141/553511/5c73f32fEe85f1c10/cd55e069121f922c.png!q90!cc_110x110.webp").into(leftImage);
        Glide.with(getContext()).load("https://img11.360buyimg.com/mobilecms/s110x110_jfs/t1/11475/15/10227/82597/5c7df5b4E812478a0/b8502e0f4cc5971c.jpg!q90!cc_110x110.webp").into(rightImage);
    }

}
