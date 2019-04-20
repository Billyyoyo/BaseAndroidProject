package fun.flyee.sunshine4u.android.modules.mall;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.activities.BaseActivity;
import fun.flyee.sunshine4u.android.models.Merchandise;
import fun.flyee.sunshine4u.android.modules.news.NewsListFragment;
import fun.flyee.sunshine4u.android.modules.news.NewsTabFragment;

public class MerchandiseActivity extends BaseActivity {

    @BindView(R.id.tab_bar)
    public SlidingTabLayout tabBar;
    @BindView(R.id.tab_pager)
    public ViewPager tabPager;

    private TabPagerAdapter mAdapter;

    private List<String> titles = Arrays.asList("商品", "详情", "评价");

    private Merchandise merchandise;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchandise);
        ButterKnife.bind(this);
        Long id = getIntent().getLongExtra("id", 0l);
        merchandise = Merchandise.one();
    }

    @Override
    public void onPostCreate(Bundle b) {
        super.onPostCreate(b);
        tabBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int i) {
                tabPager.setCurrentItem(i);
            }

            @Override
            public void onTabReselect(int i) {

            }
        });
        mAdapter = new TabPagerAdapter(getSupportFragmentManager());
        tabPager.setOffscreenPageLimit(7);
        tabPager.setAdapter(mAdapter);
        tabBar.setViewPager(tabPager);
    }

    @OnClick(R.id.back_btn)
    public void goBack(View v) {
        this.onBackPressed();
    }

    @OnClick(R.id.cart_btn)
    public void goCart(View v) {

    }

    @OnClick(R.id.add_cart_btn)
    public void addToCart(View v) {

    }

    @OnClick(R.id.buy_btn)
    public void doBuy(View v) {

    }

    public static void go(Context context, Long id) {
        Intent intent = new Intent(context, MerchandiseActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    private class TabPagerAdapter extends FragmentPagerAdapter {
        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = MerchandiseBuyFragment.newInstance(merchandise);
            } else if (position == 1) {
                fragment = MerchandiseDetailFragment.newInstance(merchandise);
            } else {
                fragment = MerchandiseCommentFragment.newInstance(merchandise);
            }
            return fragment;
        }
    }
}
