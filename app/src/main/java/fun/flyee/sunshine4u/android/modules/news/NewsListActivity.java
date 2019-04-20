package fun.flyee.sunshine4u.android.modules.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.activities.BaseActivity;
import fun.flyee.sunshine4u.android.models.News;

public class NewsListActivity extends BaseActivity {
    private int PAGE_SIZE = 10;

    private int type;

    @BindView(R.id.list_view)
    public XRecyclerView listView;

    private NewsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    public void onPostCreate(Bundle b) {
        super.onPostCreate(b);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);

        listView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        listView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        listView.setArrowImageView(R.drawable.iconfont_downgrey);

        listView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        listView.getDefaultFootView().setLoadingHint("玩命加载中");
        listView.getDefaultFootView().setNoMoreHint("没有更多了");
        listView.setLimitNumberToCallLoadMore(2);

        adapter = new NewsAdapter();
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
        List<News> list = News.list(type, 0, PAGE_SIZE);
        adapter.setData(list);
        listView.refreshComplete();
    }

    private void loadMore() {
        List<News> list = News.list(type, adapter.getItemCount(), PAGE_SIZE);
        if (list != null && list.size() > 0) {
            adapter.appendData(list);
            listView.loadMoreComplete();
            listView.refreshComplete();
        } else {
            listView.setNoMore(true);
        }
    }

    public static void go(Context context, int noteType) {
        Intent intent = new Intent(context, NewsListActivity.class);
        intent.putExtra("type", noteType);
        context.startActivity(intent);
    }

}
