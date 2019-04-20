package fun.flyee.sunshine4u.android.modules.note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.activities.BaseActivity;
import fun.flyee.sunshine4u.android.events.NoteEvent;
import fun.flyee.sunshine4u.android.fragments.BaseFragment;
import fun.flyee.sunshine4u.android.models.Note;
import fun.flyee.sunshine4u.android.widgets.Toaster;

public class WriteActivity extends BaseActivity {

    private int type;
    private BaseFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        type = getIntent().getIntExtra("type", 0);

        if (type == Note.TYPE_NORMAL) {
            getSupportActionBar().setTitle("写笔记");
            fragment = new WriteNoteFragment();
        } else if (type == Note.TYPE_HOPE) {
            getSupportActionBar().setTitle("许个愿");
            fragment = new WriteHopeFragment();
        } else if (type == Note.TYPE_PLAN) {
            getSupportActionBar().setTitle("定计划");
            fragment = new WritePlanFragment();
        } else {
            finish();
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .show(fragment)
                .commitAllowingStateLoss();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_write, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_publish:
                Note note = ((WriteFragment) fragment).doPublish();
                Toaster.show("已发布");
                if (note != null) {
                    EventBus.getDefault().post(new NoteEvent(NoteEvent.ACTION_ADD, note));
                }
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int request, int resule, Intent intent) {
        super.onActivityResult(request, resule, intent);
        if (fragment != null) {
            fragment.onActivityResult(request, resule, intent);
        }
    }

    public static void write(Context context, int noteType) {
        Intent intent = new Intent(context, WriteActivity.class);
        intent.putExtra("type", noteType);
        context.startActivity(intent);
    }

}
