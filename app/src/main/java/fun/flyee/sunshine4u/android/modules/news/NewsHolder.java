package fun.flyee.sunshine4u.android.modules.news;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import fun.flyee.sunshine4u.android.ApplicationContext;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.models.News;

public class NewsHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title_view)
    public TextView titleView;
    @BindView(R.id.pic_view)
    public ImageView picView;
    @BindView(R.id.src_view)
    public TextView srcView;
    @BindView(R.id.time_view)
    public TextView timeView;

    public News news;

    public NewsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener((v) -> {
            ArticleActivity.go(itemView.getContext(), news.getID());
        });
    }

    public void bind(News news) {
        this.news = news;
        titleView.setText(news.title);
        srcView.setText(news.src);
        timeView.setText(news.time);
        Glide.with(ApplicationContext.getContext())
                .load(news.pic)
                .into(picView);
    }


}
