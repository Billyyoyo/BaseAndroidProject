package fun.flyee.sunshine4u.android.modules.note;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.events.NoteEvent;
import fun.flyee.sunshine4u.android.fragments.BaseFragment;
import fun.flyee.sunshine4u.android.models.Note;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends BaseFragment {

    private int PAGE_SIZE = 10;

    @BindView(R.id.list_view)
    public XRecyclerView listView;

    private NoteAdapter adapter;

    private PlanHeaderView planHeader;
    private HopeHeaderView hopeHeader;

    public NoteFragment() {

    }

    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_note;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);

        listView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        listView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        listView.setArrowImageView(R.drawable.iconfont_downgrey);

        listView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        listView.getDefaultFootView().setLoadingHint("玩命加载中");
        listView.getDefaultFootView().setNoMoreHint("没有更多了");
        listView.setLimitNumberToCallLoadMore(2);

        planHeader = (PlanHeaderView) LayoutInflater.from(getContext()).inflate(R.layout.top_plan_header, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        planHeader.init();
        listView.addHeaderView(planHeader);

        hopeHeader = (HopeHeaderView) LayoutInflater.from(getContext()).inflate(R.layout.top_hope_header, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        hopeHeader.init();
        listView.addHeaderView(hopeHeader);

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

        refresh();
    }

    private void refresh() {
        List<Note> notes = Note.listNotes(Note.TYPE_NORMAL, Note.STATE_NORMAL, 0, PAGE_SIZE);
        adapter.setData(notes);
        listView.refreshComplete();
    }

    private void loadMore() {
        List<Note> notes = Note.listNotes(Note.TYPE_NORMAL, Note.STATE_NORMAL, adapter.getItemCount(), PAGE_SIZE);
        if (notes != null && notes.size() > 0) {
            adapter.appendData(notes);
            listView.loadMoreComplete();
            listView.refreshComplete();
        } else {
            listView.setNoMore(true);
        }
    }

    @Subscribe
    public void whenNoteEvent(NoteEvent ev) {
        if (ev.action == NoteEvent.ACTION_ADD) {
            if (ev.note.type == Note.TYPE_NORMAL) {
                adapter.insertData(ev.note);
            } else if (ev.note.type == Note.TYPE_HOPE) {
                hopeHeader.loadData();
            } else {
                planHeader.loadData();
            }
        } else if (ev.action == NoteEvent.ACTION_DELETE) {
            if (ev.note.type == Note.TYPE_NORMAL) {
                adapter.deleteData(ev.note);
            } else if (ev.note.type == Note.TYPE_HOPE) {
                hopeHeader.loadData();
            } else {
                planHeader.loadData();
            }
        } else if (ev.action == NoteEvent.ACTION_UPDATE) {
            if (ev.note.type == Note.TYPE_HOPE) {
                hopeHeader.loadData();
            } else if (ev.note.type == Note.TYPE_PLAN){
                planHeader.loadData();
            }
        }
    }

    @OnClick(R.id.add_btn)
    public void goAddNote(View v) {
        View sheetLayout = LayoutInflater.from(getContext()).inflate(R.layout.add_note_sheet, null, false);
        final PopupWindow popupWindow = new PopupWindow(sheetLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        sheetLayout.findViewById(R.id.add_note_btn).setOnClickListener((v1) -> {
            WriteActivity.write(getContext(), Note.TYPE_NORMAL);
            popupWindow.dismiss();
        });
        sheetLayout.findViewById(R.id.add_hope_btn).setOnClickListener((v2) -> {
            WriteActivity.write(getContext(), Note.TYPE_HOPE);
            popupWindow.dismiss();
        });
        sheetLayout.findViewById(R.id.add_plan_btn).setOnClickListener((v3) -> {
            WriteActivity.write(getContext(), Note.TYPE_PLAN);
            popupWindow.dismiss();
        });
    }


}
