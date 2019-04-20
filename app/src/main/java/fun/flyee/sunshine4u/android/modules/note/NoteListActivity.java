package fun.flyee.sunshine4u.android.modules.note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RadioGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.activities.BaseActivity;
import fun.flyee.sunshine4u.android.events.NoteEvent;
import fun.flyee.sunshine4u.android.models.Note;

public class NoteListActivity extends BaseActivity {
    private int PAGE_SIZE = 10;

    private int type;

    private boolean flag = false;

    @BindView(R.id.plan_radio)
    public RadioGroup planRadio;
    @BindView(R.id.hope_radio)
    public RadioGroup hopeRadio;
    @BindView(R.id.list_view)
    public XRecyclerView listView;

    private NoteAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            finish();
            return;
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void whenNoteChanged(NoteEvent ev) {
        refresh();
    }

    @Override
    public void onPostCreate(Bundle b) {
        super.onPostCreate(b);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);

        listView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        listView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        listView.setArrowImageView(R.drawable.iconfont_downgrey);

        listView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        listView.getDefaultFootView().setLoadingHint("玩命加载中");
        listView.getDefaultFootView().setNoMoreHint("没有更多了");
        listView.setLimitNumberToCallLoadMore(2);

        adapter = new NoteAdapter();
        listView.setAdapter(adapter);
        listView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });

        hopeRadio.setOnCheckedChangeListener((radio, id) -> {
            flag = id == R.id.hope_radio_yes;
            refresh();
        });
        planRadio.setOnCheckedChangeListener((radio, id) -> {
            flag = id == R.id.plan_radio_yes;
            refresh();
        });

        refresh();

        if (type == Note.TYPE_HOPE) {
            getSupportActionBar().setTitle("愿望树");
            hopeRadio.setVisibility(View.VISIBLE);
            planRadio.setVisibility(View.GONE);
        } else {
            getSupportActionBar().setTitle("计划表");
            hopeRadio.setVisibility(View.GONE);
            planRadio.setVisibility(View.VISIBLE);
        }
    }

    private void refresh() {
        List<Note> notes = new ArrayList<>();
        if (type == Note.TYPE_HOPE) {
            notes = Note.listHopes(flag, 0, PAGE_SIZE);
        } else {
            notes = Note.listPlans(flag, 0, PAGE_SIZE);
        }
        adapter.setData(notes);
        listView.refreshComplete();
    }

    private void loadMore() {
        List<Note> notes = new ArrayList<>();
        if (type == Note.TYPE_HOPE) {
            notes = Note.listHopes(flag, adapter.getItemCount(), PAGE_SIZE);
        } else {
            notes = Note.listPlans(flag, adapter.getItemCount(), PAGE_SIZE);
        }
        if (notes != null && notes.size() > 0) {
            adapter.appendData(notes);
            listView.loadMoreComplete();
            listView.refreshComplete();
        } else {
            listView.setNoMore(true);
        }
    }

    public static void go(Context context, int noteType) {
        Intent intent = new Intent(context, NoteListActivity.class);
        intent.putExtra("type", noteType);
        context.startActivity(intent);
    }

}
