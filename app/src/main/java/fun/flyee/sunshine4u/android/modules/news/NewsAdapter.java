package fun.flyee.sunshine4u.android.modules.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.models.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {

    private List<News> data = new ArrayList<>();

    private int factor;

    public NewsAdapter() {
        factor = new Random(System.currentTimeMillis()).nextInt(100) % 5;
    }

    public void setData(List<News> d) {
        data.clear();
        data.addAll(d);
        notifyDataSetChanged();
    }

    public void appendData(List<News> d) {
        data.addAll(d);
        notifyDataSetChanged();
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view = null;
        if (type == 1) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item_ver_layout, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item_hor_layout, viewGroup, false);
        }
        return new NewsHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return (factor == position % 5) ? 1 : -1;
    }

    @Override
    public void onBindViewHolder(NewsHolder newsHolder, int i) {
        newsHolder.bind(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
