package fun.flyee.sunshine4u.android.modules.news;


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
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsTabFragment extends BaseFragment {

    @BindView(R.id.tab_bar)
    public SlidingTabLayout tabBar;
    @BindView(R.id.tab_pager)
    public ViewPager tabPager;

    private TabPagerAdapter mAdapter;

    private List<String> channels = new ArrayList<>();

    public NewsTabFragment() {
    }

    public static NewsTabFragment newInstance() {
        return new NewsTabFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_tab;
    }

    @Override
    public void onViewCreated(View v, Bundle b) {
        super.onViewCreated(v, b);
        tabBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int i) {
                tabPager.setCurrentItem(i);
            }

            @Override
            public void onTabReselect(int i) {

            }
        });
        mAdapter = new TabPagerAdapter(getChildFragmentManager());
        tabPager.setOffscreenPageLimit(7);
        tabPager.setAdapter(mAdapter);
        tabBar.setViewPager(tabPager);
        loadChannels();
    }

    private void loadChannels() {
//        ApiClient.jsApi().newsChannels(Constant.JISU_API_KEY)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<JisuResponse>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(JisuResponse resp) {
//                        if ("0".equals(resp.getStatus())) {
//                            JSONArray array = JSONArray.parseArray(resp.getResult());
//                            channels = array.toJavaList(String.class);
//                            mAdapter.notifyDataSetChanged();
//                            tabBar.notifyDataSetChanged();
//                            tabPager.setCurrentItem(0);
//                        } else {
//                            Toaster.show("频道加载失败");
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
        channels = Arrays.asList("星座","体育","NBA","娱乐","女性","健康","育儿");
        mAdapter.notifyDataSetChanged();
        tabBar.notifyDataSetChanged();
        tabPager.setCurrentItem(0);
    }

    private class TabPagerAdapter extends FragmentPagerAdapter {
        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return channels.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return channels.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return NewsListFragment.newInstance(channels.get(position));
        }
    }

}
