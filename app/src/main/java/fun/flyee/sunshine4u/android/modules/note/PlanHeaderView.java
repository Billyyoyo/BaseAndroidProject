package fun.flyee.sunshine4u.android.modules.note;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.models.Note;

public class PlanHeaderView extends LinearLayout {

    @BindView(R.id.text_view)
    public TextView textView;
    @BindView(R.id.count_view)
    public TextView countView;

    public PlanHeaderView(Context context) {
        super(context);
    }

    public PlanHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlanHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        ButterKnife.bind(this, this);
        setOnClickListener((view)->{
            NoteListActivity.go(getContext(), Note.TYPE_PLAN);
        });
        loadData();
    }

    public void loadData(){
        countView.setText("共" + Note.countPlans() + "个计划");
        List<Note> notes = Note.listPlans(false, 0, 1);
        if (notes!=null && notes.size()>0){
            textView.setText(notes.get(0).content);
        } else {
            textView.setText("快去添加计划吧");
        }
    }
}
