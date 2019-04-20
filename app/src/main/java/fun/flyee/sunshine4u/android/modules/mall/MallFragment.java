package fun.flyee.sunshine4u.android.modules.mall;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.fragments.BaseFragment;
import fun.flyee.sunshine4u.android.models.Merchandise;
import fun.flyee.sunshine4u.android.utils.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class MallFragment extends BaseFragment {

    private int PAGE_SIZE = 10;

    private MallPanelView mallPanelView;
    private MallCategoryView mallCategoryView;

    public static MallFragment newInstance() {
        return new MallFragment();
    }

    @BindView(R.id.list_view)
    public XRecyclerView listView;

    private MerchandiseAdapter adapter;

    public MallFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    public void onViewCreated(View v, Bundle b) {
        super.onViewCreated(v, b);
        int padding = Util.dip2px(getContext(), 6);
        listView.setPadding(padding, padding, padding, padding);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(gridLayoutManager);

        listView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        listView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        listView.setArrowImageView(R.drawable.iconfont_downgrey);

        listView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        listView.getDefaultFootView().setLoadingHint("玩命加载中");
        listView.getDefaultFootView().setNoMoreHint("没有更多了");
        listView.setLimitNumberToCallLoadMore(2);

        mallPanelView = (MallPanelView) LayoutInflater.from(getContext()).inflate(R.layout.mall_head_panel, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        mallPanelView.init();
        listView.addHeaderView(mallPanelView);

        mallCategoryView = (MallCategoryView) LayoutInflater.from(getContext()).inflate(R.layout.mall_category_panel, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        mallCategoryView.init();
        listView.addHeaderView(mallCategoryView);

        adapter = new MerchandiseAdapter();
        listView.setAdapter(adapter);
        listView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });

        refresh();
    }

    private void refresh() {
        List<Merchandise> mers = Merchandise.all();
        adapter.setData(mers);
        listView.refreshComplete();
    }

    private void loadMore() {
        List<Merchandise> mers = Merchandise.all();
        if (mers != null && mers.size() > 0) {
            adapter.appendData(mers);
            listView.loadMoreComplete();
            listView.refreshComplete();
        } else {
            listView.setNoMore(true);
        }
    }

}
