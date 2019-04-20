package fun.flyee.sunshine4u.android.modules.note;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.fragments.views.BaseHolder;
import fun.flyee.sunshine4u.android.models.Note;

public class NoteAdapter extends RecyclerView.Adapter<BaseHolder> {

    private List<Note> data = new ArrayList<>();

    public NoteAdapter() {
    }

    public void setData(List<Note> d) {
        data.clear();
        data.addAll(d);
        notifyDataSetChanged();
    }

    public void appendData(List<Note> d) {
        data.addAll(d);
        notifyDataSetChanged();
    }

    public void insertData(Note d) {
        data.add(0, d);
        notifyDataSetChanged();
    }

    public void deleteData(Note note){
        data.remove(note);
        notifyDataSetChanged();
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view = null;
        if( type == Note.TYPE_NORMAL) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item_layout, viewGroup, false);
            return new NoteHolder(view);
        }else if( type == Note.TYPE_HOPE) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hope_item_layout, viewGroup, false);
            return new HopeHolder(view);
        }else if( type == Note.TYPE_PLAN) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plan_item_layout, viewGroup, false);
            return new PlanHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int i) {
        holder.bind(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
