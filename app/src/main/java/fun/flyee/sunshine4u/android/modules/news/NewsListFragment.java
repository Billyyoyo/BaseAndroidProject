package fun.flyee.sunshine4u.android.modules.news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.api.ApiClient;
import fun.flyee.sunshine4u.android.fragments.BaseFragment;
import fun.flyee.sunshine4u.android.models.JisuResponse;
import fun.flyee.sunshine4u.android.models.News;
import fun.flyee.sunshine4u.android.utils.Constant;
import fun.flyee.sunshine4u.android.utils.Util;
import fun.flyee.sunshine4u.android.widgets.Toaster;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends BaseFragment {

    private String channel;

    private int PAGE_SIZE = 10;

    @BindView(R.id.list_view)
    public XRecyclerView listView;
    private NewsAdapter adapter;

    public static NewsListFragment newInstance(String channel) {
        NewsListFragment fragment = new NewsListFragment();
        fragment.channel = channel;
        return fragment;
    }

    public NewsListFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId(){
        return R.layout.fragment_list;
    }

    @Override
    public void onViewCreated(View v, Bundle b) {
        super.onViewCreated(v, b);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
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
        loadList(0, (list) -> {
            adapter.setData(list);
            listView.refreshComplete();
        });
    }

    private void loadMore() {
        loadList(adapter.getItemCount(), (list) -> {
            if (list != null && list.size() > 0) {
                adapter.appendData(list);
                listView.loadMoreComplete();
                listView.refreshComplete();
            } else {
                listView.setNoMore(true);
            }
        });
    }

    private void loadList(int start, DataCallback callback) {
        ApiClient.jsApi().newsList(Constant.JISU_API_KEY, channel, start, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JisuResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JisuResponse resp) {
                        if ("0".equals(resp.getStatus())) {
                            JSONObject respJson = JSONObject.parseObject(resp.getResult());
                            if (respJson.containsKey("list")) {
                                JSONArray array = respJson.getJSONArray("list");
                                List<News> list = new ArrayList<>();
                                Gson gson = Util.getGson();
                                for (int i = 0; i < array.size(); i++) {
                                    String json = array.getString(i);
                                    News news = gson.fromJson(json, News.class);
                                    news = news.saveByUrl();
                                    list.add(news);
                                }
                                callback.callback(list);
                            }
                        } else {
                            Toaster.show(resp.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface DataCallback {
        void callback(List<News> list);
    }

}
