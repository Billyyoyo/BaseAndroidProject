package fun.flyee.sunshine4u.android.modules.zodiac;


import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.ListenerMethod;
import fun.flyee.sunshine4u.android.ApplicationContext;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.fragments.BaseFragment;
import fun.flyee.sunshine4u.android.models.JisuResponse;
import fun.flyee.sunshine4u.android.models.Merchandise;
import fun.flyee.sunshine4u.android.models.News;
import fun.flyee.sunshine4u.android.models.User;
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
public class ZodiacHomeFragment extends BaseFragment {

    @BindView(R.id.no_zodiac_title)
    public View noZodiacTitleView;
    @BindView(R.id.no_zodiac_layout)
    public XRecyclerView noZodiacTitleLayout;
    public ZoddiacSelectAdapter adapter;

    @BindView(R.id.zodiac_info_layout)
    public View zodiacInfoLayout;

    private User host;

    public static ZodiacHomeFragment newInstance() {
        ZodiacHomeFragment fragment = new ZodiacHomeFragment();
        return fragment;
    }

    public ZodiacHomeFragment() {

    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_zodiac_home;
    }

    @Override
    public void onViewCreated(View v, Bundle b) {
        super.onViewCreated(v, b);
        host = User.getLoginUser();
        refreshView();
    }

    public void refreshView() {
        if (host.zodiac < 0) {
            showNoZodiac();
        } else {
            showZodiac();
        }
    }

    public void showNoZodiac() {
        noZodiacTitleLayout.setVisibility(View.VISIBLE);
        zodiacInfoLayout.setVisibility(View.GONE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        noZodiacTitleLayout.setLayoutManager(gridLayoutManager);

        noZodiacTitleLayout.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        noZodiacTitleLayout.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        noZodiacTitleLayout.setArrowImageView(R.drawable.iconfont_downgrey);

        noZodiacTitleLayout.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        noZodiacTitleLayout.getDefaultFootView().setLoadingHint("玩命加载中");
        noZodiacTitleLayout.getDefaultFootView().setNoMoreHint("没有更多了");
        noZodiacTitleLayout.setLimitNumberToCallLoadMore(2);

        ((ViewGroup)noZodiacTitleView.getParent()).removeView(noZodiacTitleView);
        noZodiacTitleLayout.addHeaderView(noZodiacTitleView);

        adapter = new ZoddiacSelectAdapter();
        noZodiacTitleLayout.setAdapter(adapter);
        loadAllZodiac();
    }

    public void showZodiac() {
        noZodiacTitleLayout.setVisibility(View.GONE);
        zodiacInfoLayout.setVisibility(View.VISIBLE);
    }

    public void loadAllZodiac() {
        ApplicationContext.getContext().getApiClient().jsApi().zodiacAll(Constant.JISU_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JisuResponse>(){

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JisuResponse resp) {
                        if ("0".equals(resp.getStatus())) {
                            JSONArray array = JSONObject.parseArray(resp.getResult());

                                List<Zodiac> list = new ArrayList<>();
                                Gson gson = Util.getGson();
                                for (int i = 0; i < array.size(); i++) {
                                    String json = array.getString(i);
                                    Zodiac zodiac = gson.fromJson(json, Zodiac.class);
                                    list.add(zodiac);
                                }
                                adapter.zodiacs.addAll(list);
                                adapter.notifyDataSetChanged();

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

    public void loadZodiacStatus() {

    }

    private class ZoddiacSelectAdapter extends RecyclerView.Adapter<ZodiacSelectItemHolder> {

        public List<Zodiac> zodiacs = new ArrayList<>();

        @NonNull
        @Override
        public ZodiacSelectItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ZodiacSelectItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.zodiac_select_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ZodiacSelectItemHolder holder, int i) {
            holder.bindData(zodiacs.get(i));
        }

        @Override
        public int getItemCount() {
            return zodiacs.size();
        }
    }

    public class ZodiacSelectItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_view)
        public ImageView imageView;
        @BindView(R.id.title_view)
        public TextView titleView;

        public Zodiac zodiac;

        public ZodiacSelectItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                new AlertDialog.Builder(getContext())
                        .setMessage("很高兴您有了选择～")
                        .setPositiveButton("就这么定了", (diag, which) -> {
                            host.zodiac = zodiac.astroid - 1;
                        })
                        .setNegativeButton("好像选错了", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            });
        }

        public void bindData(Zodiac zodiac) {
            this.zodiac = zodiac;
            titleView.setText(zodiac.astroname);
            Glide.with(getContext()).load(zodiac.pic).into(imageView);
        }

    }


}
