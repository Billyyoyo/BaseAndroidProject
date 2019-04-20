package fun.flyee.sunshine4u.android.fragments.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import fun.flyee.sunshine4u.android.models.Note;

public abstract class BaseHolder extends RecyclerView.ViewHolder {
    public BaseHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(Note object);
}
