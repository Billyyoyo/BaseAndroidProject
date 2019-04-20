package fun.flyee.sunshine4u.android.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;

import com.activeandroid.util.Log;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.cache.ConfigCache;
import fun.flyee.sunshine4u.android.events.LogoutEvent;
import fun.flyee.sunshine4u.android.modules.mall.MallFragment;
import fun.flyee.sunshine4u.android.modules.news.NewsTabFragment;
import fun.flyee.sunshine4u.android.modules.note.NoteFragment;
import fun.flyee.sunshine4u.android.fragments.UserInfoFragment;
import fun.flyee.sunshine4u.android.models.MainTabItem;
import fun.flyee.sunshine4u.android.widgets.Toaster;

public class MainActivity extends BaseActivity {

    private final static String TAG = "MainActivity";

    private long exitTime = 0l;

    @BindView(R.id.tab_bottom_bar)
    public CommonTabLayout tabBottomBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        EventBus.getDefault().register(this);
        configTab();
        configFragments(savedInstanceState);
    }

    private void configFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Fragment fragment0 = NewsTabFragment.newInstance();
            Fragment fragment1 = NoteFragment.newInstance();
            Fragment fragment2 = MallFragment.newInstance();
            Fragment fragment3 = UserInfoFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, fragment1, "fragment_tab_1").hide(fragment1);
            transaction.add(R.id.fragment_container, fragment2, "fragment_tab_2").hide(fragment2);
            transaction.add(R.id.fragment_container, fragment3, "fragment_tab_3").show(fragment3);
            transaction.add(R.id.fragment_container, fragment0, "fragment_tab_0").show(fragment0);
            transaction.commitAllowingStateLoss();
        } else {
            Fragment fragment0 = getSupportFragmentManager().findFragmentByTag("fragment_tab_0");
            Fragment fragment1 = getSupportFragmentManager().findFragmentByTag("fragment_tab_1");
            Fragment fragment2 = getSupportFragmentManager().findFragmentByTag("fragment_tab_2");
            Fragment fragment3 = getSupportFragmentManager().findFragmentByTag("fragment_tab_3");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(fragment1);
            transaction.hide(fragment2);
            transaction.hide(fragment3);
            transaction.show(fragment0);
            transaction.commitAllowingStateLoss();
        }
    }

    private void configTab() {
        ArrayList<CustomTabEntity> tabList = new ArrayList<CustomTabEntity>();
        tabList.add(new MainTabItem("推荐", R.drawable.ic_card_giftcard_white_48dp));
        tabList.add(new MainTabItem("笔记", R.drawable.ic_date_range_white_48dp));
        tabList.add(new MainTabItem("小店", R.drawable.ic_mall));
        tabList.add(new MainTabItem("我的", R.drawable.ic_face_white_48dp));
        tabBottomBar.setOnTabSelectListener(new OnTabSelectListener() {

            @Override
            public void onTabSelect(int position) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                for (int i = 0; i < 4; i++) {
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("fragment_tab_" + i);
                    if (i == position) {
                        Log.e(TAG, fragment.getClass().getSimpleName());
                        transaction.show(fragment);
                    } else {
                        transaction.hide(fragment);
                    }
                }
                transaction.commitAllowingStateLoss();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        tabBottomBar.setTabData(tabList);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 1000) {
                Toaster.show("再次点击将退出应用");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void whenLogout(LogoutEvent ev) {
        ConfigCache.get().loginUserId = 0l;
        ConfigCache.save();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
