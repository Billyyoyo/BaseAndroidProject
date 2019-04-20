package fun.flyee.sunshine4u.android.modules.note;

import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import fun.flyee.sunshine4u.android.events.NoteEvent;
import fun.flyee.sunshine4u.android.fragments.views.BaseHolder;
import fun.flyee.sunshine4u.android.models.Note;
import fun.flyee.sunshine4u.android.utils.AlarmUtil;
import fun.flyee.sunshine4u.android.utils.DateUtil;
import fun.flyee.sunshine4u.android.utils.Util;

public class PlanHolder extends BaseHolder {

    @BindView(R.id.content_view)
    public TextView contentView;
    @BindView(R.id.time_view)
    public TextView timeView;
    @BindView(R.id.cancel_btn)
    public TextView cancelBtn;

    public Note note;

    public PlanHolder(View itemView) {
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
        timeView.setText(DateUtil.formatTime(note.notifyTime, "yyyy-MM-dd HH:mm"));
        cancelBtn.setVisibility(note.notifyTime > System.currentTimeMillis() ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.cancel_btn)
    public void doCancel(View v) {
        note.delete();
        AlarmUtil.cancelAlarm(itemView.getContext(), 1000 + note.getID().intValue(), note.content);
        EventBus.getDefault().post(new NoteEvent(NoteEvent.ACTION_DELETE, note));
    }

}
