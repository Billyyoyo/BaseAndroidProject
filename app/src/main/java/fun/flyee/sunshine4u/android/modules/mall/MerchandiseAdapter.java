package fun.flyee.sunshine4u.android.modules.mall;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.models.Merchandise;
import fun.flyee.sunshine4u.android.models.Note;

public class MerchandiseAdapter extends RecyclerView.Adapter<MerchandiseHoder> {

    private List<Merchandise> data = new ArrayList<>();

    public void setData(List<Merchandise> d) {
        data.clear();
        data.addAll(d);
        notifyDataSetChanged();
    }

    public void appendData(List<Merchandise> d) {
        data.addAll(d);
        notifyDataSetChanged();
    }

    public void insertData(Merchandise d) {
        data.add(0, d);
        notifyDataSetChanged();
    }

    public void deleteData(Merchandise d) {
        data.remove(d);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MerchandiseHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View view;
        if (type == 0) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.merchandise_item_ver_layout, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.merchandise_item_hor_layout, viewGroup, false);
        }
        return new MerchandiseHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MerchandiseHoder hoder, int i) {
        hoder.bindData(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
