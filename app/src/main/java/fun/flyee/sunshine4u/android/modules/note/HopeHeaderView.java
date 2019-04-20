package fun.flyee.sunshine4u.android.modules.note;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.models.Note;
import fun.flyee.sunshine4u.android.utils.Util;

public class HopeHeaderView extends RelativeLayout {

    @BindView(R.id.info_view)
    public TextView infoView;
    @BindView(R.id.hope_container)
    public LinearLayout hopeContainer;

    public HopeHeaderView(Context context) {
        super(context);
    }

    public HopeHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HopeHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        ButterKnife.bind(this, this);
        setOnClickListener((view)->{
            NoteListActivity.go(getContext(), Note.TYPE_HOPE);
        });
        loadData();
    }

    public void loadData(){
        List<Note> compeleteList = Note.listHopes(true);
        List<Note> ingList = Note.listHopes(false);
        if (compeleteList.size() > 0 || ingList.size() > 0) {
            infoView.setText("你有 " + ingList.size() + " 个愿望   已经实现 " + compeleteList.size() + " 个愿望");
        } else {
            infoView.setText("你没有许愿, 快去许一个吧");
        }
        List<String> hopePhotos = new ArrayList<>();
        for (Note note : ingList) {
            if (!TextUtils.isEmpty(note.icon)) {
                String[] paths = note.icon.split(",");
                for (String path : paths) {
                    hopePhotos.add(path);
                    if (hopePhotos.size() > 5) {
                        break;
                    }
                }
            }
        }
        if (hopePhotos.size() == 0) {
            return;
        }
        Random random = new Random(System.currentTimeMillis());
        int max = Util.dip2px(getContext(), 64);
        for (String path : hopePhotos) {
            View tile = LayoutInflater.from(getContext()).inflate(R.layout.hope_tile, null, true);
            ImageView image = tile.findViewById(R.id.image_view);
            ((LinearLayout.LayoutParams) image.getLayoutParams()).topMargin = random.nextInt(max);
            Glide.with(getContext()).load(path).into(image);
            hopeContainer.addView(tile);
            ((LinearLayout.LayoutParams) tile.getLayoutParams()).weight = 1;
        }
    }
}
