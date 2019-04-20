package fun.flyee.sunshine4u.android.modules.zodiac;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.activities.BaseActivity;
import fun.flyee.sunshine4u.android.models.Merchandise;
import fun.flyee.sunshine4u.android.modules.mall.MerchandiseBuyFragment;
import fun.flyee.sunshine4u.android.modules.mall.MerchandiseCommentFragment;
import fun.flyee.sunshine4u.android.modules.mall.MerchandiseDetailFragment;

public class ZodiacActivity extends BaseActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchandise);
        ButterKnife.bind(this);
        Long id = getIntent().getLongExtra("id", 0l);
    }

    @Override
    public void onPostCreate(Bundle b) {
        super.onPostCreate(b);
    }

    public static void go(Context context, Long id) {
        Intent intent = new Intent(context, ZodiacActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

}
