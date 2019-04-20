package fun.flyee.sunshine4u.android.modules.note;

import android.support.v7.widget.GridLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.activities.PhotoActivity;
import fun.flyee.sunshine4u.android.events.NoteEvent;
import fun.flyee.sunshine4u.android.fragments.views.BaseHolder;
import fun.flyee.sunshine4u.android.models.Note;
import fun.flyee.sunshine4u.android.utils.DateUtil;
import fun.flyee.sunshine4u.android.utils.Util;

public class NoteHolder extends BaseHolder {

    @BindView(R.id.title_view)
    public TextView titleView;
    @BindView(R.id.photo_grid_container)
    public GridLayout photoContainer;
    @BindView(R.id.content_view)
    public TextView contentView;
    @BindView(R.id.time_view)
    public TextView timeView;

    public Note note;

    public NoteHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnLongClickListener((view) -> {
            EventBus.getDefault().post(new NoteEvent(NoteEvent.ACTION_DELETE, note));
            return true;
        });
    }

    public void bind(Note note) {
        this.note = note;
        titleView.setVisibility(TextUtils.isEmpty(note.title) ? View.GONE : View.VISIBLE);
        titleView.setText(note.title);
        titleView.setVisibility(TextUtils.isEmpty(note.content) ? View.GONE : View.VISIBLE);
        contentView.setText(note.content);
        timeView.setText(DateUtil.getDateTimeAgo(note.updateTime));
        photoContainer.removeAllViews();
        if (!TextUtils.isEmpty(note.icon)) {
            String[] paths = note.icon.split(",");
            for (int i = 0; i < paths.length; i++) {
                showPhotos(i, paths[i]);
            }
        }
    }

    private void showPhotos(int index, String path) {
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(Util.dip2px(itemView.getContext(), 90), Util.dip2px(itemView.getContext(), 90));
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(lp);
        layoutParams.leftMargin = Util.dip2px(itemView.getContext(), 1);
        layoutParams.topMargin = Util.dip2px(itemView.getContext(), 1);
        final ImageView photoView = new ImageView(itemView.getContext());
        photoContainer.addView(photoView, layoutParams);
        photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(itemView.getContext()).load(path).into(photoView);
        photoView.setOnClickListener(v -> {
            PhotoActivity.go(itemView.getContext(), note.getID(), index);
        });
    }

}
