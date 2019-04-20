package fun.flyee.sunshine4u.android.modules.note;

import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.EventLog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.activities.PhotoActivity;
import fun.flyee.sunshine4u.android.events.NoteEvent;
import fun.flyee.sunshine4u.android.fragments.views.BaseHolder;
import fun.flyee.sunshine4u.android.models.Note;
import fun.flyee.sunshine4u.android.utils.DateUtil;
import fun.flyee.sunshine4u.android.utils.Util;

public class HopeHolder extends BaseHolder {

    @BindView(R.id.photo_grid_container)
    public GridLayout photoContainer;
    @BindView(R.id.content_view)
    public TextView contentView;
    @BindView(R.id.time_view)
    public TextView timeView;
    @BindView(R.id.finish_btn)
    public View finishBtn;

    public Note note;

    public HopeHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnLongClickListener((view) -> {
            EventBus.getDefault().post(new NoteEvent(NoteEvent.ACTION_DELETE, note));
            return true;
        });
    }

    public void bind(Note note) {
        this.note = note;
        contentView.setText(note.content);
        timeView.setText(DateUtil.getDateTimeAgo(note.updateTime));
        photoContainer.removeAllViews();
        finishBtn.setVisibility(note.state == Note.STATE_COMPLETE ? View.GONE : View.VISIBLE);
        if (!TextUtils.isEmpty(note.icon)) {
            String[] paths = note.icon.split(",");
            for (int i = 0; i < paths.length; i++) {
                final int index = i;
                String path = paths[i];
                showPhotos(path).setOnClickListener(v -> {
                    PhotoActivity.go(itemView.getContext(), note.getID(), index);
                });
            }
        }
    }

    @OnClick(R.id.finish_btn)
    public void doFinish(View v) {
        finishBtn.setVisibility(View.GONE);
        note.state = Note.STATE_COMPLETE;
        note.save();
        EventBus.getDefault().post(new NoteEvent(NoteEvent.ACTION_UPDATE, note));
    }

    private View showPhotos(String path) {
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(Util.dip2px(itemView.getContext(), 90), Util.dip2px(itemView.getContext(), 90));
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(lp);
        layoutParams.leftMargin = Util.dip2px(itemView.getContext(), 1);
        layoutParams.topMargin = Util.dip2px(itemView.getContext(), 1);
        final ImageView photoView = new ImageView(itemView.getContext());
        photoContainer.addView(photoView, layoutParams);
        photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(itemView.getContext()).load(path).into(photoView);
        return photoView;
    }

}
