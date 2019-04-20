package fun.flyee.sunshine4u.android.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

import com.activeandroid.Model;

import java.util.Arrays;
import java.util.List;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.models.Note;
import me.iwf.photopicker.fragment.ImagePagerFragment;

public class PhotoActivity extends BaseActivity {

    private long id;
    private int index;
    private ImagePagerFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        id = getIntent().getLongExtra("id", 0);
        index = getIntent().getIntExtra("index", 1);

        Note note = Model.load(Note.class, id);
        getSupportActionBar().setTitle(note.content);
        String[] pathArray = note.icon.split(",");
        List<String> paths = Arrays.asList(pathArray);
//        Collections.reverse(paths);
//        index = paths.size() - index;
        fragment = ImagePagerFragment.newInstance(paths, index);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .show(fragment)
                .commitAllowingStateLoss();
        getSupportActionBar().setTitle((index + 1) + " / " + paths.size());

        new Handler() {
            public void handleMessage(Message msg) {
                if (fragment.getViewPager() == null) return;
                fragment.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        getSupportActionBar().setTitle((i + 1) + " / " + paths.size());
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });
            }
        }.sendEmptyMessageDelayed(0, 500);

    }

    public static void go(Context context, long id, int index) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

}
