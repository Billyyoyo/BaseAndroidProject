package fun.flyee.sunshine4u.android.modules.note;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.cache.ConfigCache;
import fun.flyee.sunshine4u.android.fragments.BaseFragment;
import fun.flyee.sunshine4u.android.models.Note;
import fun.flyee.sunshine4u.android.utils.Util;
import fun.flyee.sunshine4u.android.widgets.Toaster;
import me.iwf.photopicker.PhotoPicker;

public class WriteHopeFragment extends BaseFragment implements WriteFragment {

    @BindView(R.id.content_box)
    public TextView contentBox;
    @BindView(R.id.photo_grid_container)
    public GridLayout photoContainer;
    @BindView(R.id.add_photo_btn)
    public View addBtn;

    private List<String> imgPaths = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_write_hope;
    }

    public Note doPublish() {
        Note note = new Note();
        note.content = contentBox.getText().toString();
        note.icon = Util.joinString(imgPaths, ",");
        note.type = Note.TYPE_HOPE;
        note.state = Note.STATE_NORMAL;
        note.userId = ConfigCache.get().loginUserId;
        note.createTime = System.currentTimeMillis();
        note.updateTime = System.currentTimeMillis();
        note.save();
        return note;
    }

    @OnClick(R.id.add_photo_btn)
    public void onInsertImage(View v) {
        PhotoPicker.builder()
                .setPhotoCount(3 - imgPaths.size())
                .setShowCamera(true)
                .setShowGif(false)
                .setPreviewEnabled(true)
                .start(getActivity(), PhotoPicker.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PhotoPicker.REQUEST_CODE) {//拍照或者选择照片
                if (data != null) {
                    List<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    if (photos != null && !photos.isEmpty()) {
                        for (String path : photos) {
                            addPhotoView(path);
                        }
                        addBtn.setVisibility(imgPaths.size() >= 3 ? View.GONE : View.VISIBLE);
                    }
                }
            }
        }
    }

    private void addPhotoView(final String path) {
        imgPaths.add(path);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(Util.dip2px(getContext(), 80), Util.dip2px(getContext(), 80));
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(lp);
        layoutParams.leftMargin = Util.dip2px(getContext(), 1);
        layoutParams.topMargin = Util.dip2px(getContext(), 1);
        final ImageView photoView = new ImageView(getContext());
        photoContainer.addView(photoView, 0, layoutParams);
        imageViews.add(photoView);
        photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(getContext()).load(path).into(photoView);
        photoView.setOnLongClickListener((view) -> {
            imgPaths.remove(path);
            imageViews.remove(photoView);
            photoContainer.removeView(photoView);
            Toaster.show("已删除图片");
            addBtn.setVisibility(imgPaths.size() >= 3 ? View.GONE : View.VISIBLE);
            return true;
        });
    }

}
