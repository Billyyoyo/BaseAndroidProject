package fun.flyee.sunshine4u.android.modules.note;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;

import butterknife.BindView;
import butterknife.OnClick;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.cache.ConfigCache;
import fun.flyee.sunshine4u.android.fragments.BaseFragment;
import fun.flyee.sunshine4u.android.models.Note;
import fun.flyee.sunshine4u.android.utils.AlarmUtil;
import fun.flyee.sunshine4u.android.utils.DateUtil;

import static android.app.AlarmManager.RTC_WAKEUP;

public class WritePlanFragment extends BaseFragment implements WriteFragment {

    @BindView(R.id.content_box)
    public TextView contentBox;
    @BindView(R.id.timer_view)
    public TextView timerView;
    @BindView(R.id.notify_check)
    public CheckBox notifyCheckbox;

    private Long selectTime = 0l;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_write_plan;
    }

    @Override
    public Note doPublish() {
        Note note = new Note();
        note.content = contentBox.getText().toString();
        note.type = Note.TYPE_PLAN;
        note.state = Note.STATE_NORMAL;
        note.userId = ConfigCache.get().loginUserId;
        note.createTime = System.currentTimeMillis();
        note.updateTime = System.currentTimeMillis();
        note.notifyType = notifyCheckbox.isChecked() ? Note.NOTIFY_ALARM : Note.NOTIFY_NONE;
        note.notifyTime = selectTime;
        note.save();
        if(notifyCheckbox.isChecked()) {
            AlarmUtil.setAlarm(getContext(),
                    1000 + note.getID().intValue(), RTC_WAKEUP,
                    System.currentTimeMillis() + 10, note.content);
        }
        return note;
    }

    @OnClick(R.id.select_time_btn)
    public void doSelectTime(View v) {
        TimePickerView pvTime = new TimePickerBuilder(getContext(), (date, view) -> {
            selectTime = date.getTime();
            timerView.setText(DateUtil.formatTime(selectTime, "yyyy-MM-dd HH:mm"));
        }).setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示
                .setCancelText("取消")
                .setSubmitText("确定")
                .setSubmitColor(getResources().getColor(R.color.colorPrimary))
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }
}
