package fun.flyee.sunshine4u.android.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fun.flyee.sunshine4u.android.ApplicationContext;
import fun.flyee.sunshine4u.android.events.LoginEvent;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.cache.ConfigCache;
import fun.flyee.sunshine4u.android.models.User;
import fun.flyee.sunshine4u.android.utils.CLog;
import fun.flyee.sunshine4u.android.utils.FileUtil;
import fun.flyee.sunshine4u.android.utils.ImageLoader;
import fun.flyee.sunshine4u.android.widgets.Toaster;
import me.iwf.photopicker.PhotoPicker;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {

    @BindView(R.id.avatar_view)
    ImageView avaterView;
    @BindView(R.id.nickname_box)
    EditText nickNameBox;
    @BindView(R.id.desc_box)
    TextView descBox;
    @BindView(R.id.gender_box)
    RadioGroup genderRadio;

    private String newAvatar;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(View v, Bundle b) {
        super.onViewCreated(v, b);
        clearGlideCache();
        loadUser();
    }

    private void loadUser() {
        User user = User.getLoginUser();
        if (user == null) return;
        nickNameBox.setText(user.nickName);
        genderRadio.check(user.gender == 1 ? R.id.male_radio : R.id.female_radio);
        descBox.setText(user.desc);
        newAvatar = user.avatar;
        ImageLoader.circleImage(avaterView, user.avatar, R.mipmap.ic_launcher);
    }

    private void clearGlideCache() {
        new Thread() {
            public void run() {
                Glide.get(ApplicationContext.getContext()).clearDiskCache();
            }
        }.start();
        Glide.get(ApplicationContext.getContext()).clearMemory();
    }

    @OnClick(R.id.avatar_view)
    public void doSelectAvatar(View v) {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setShowGif(false)
                .setPreviewEnabled(true)
                .start(getActivity(), PhotoPicker.REQUEST_CODE);
    }

    @OnClick(R.id.next_btn)
    public void doSubmit(View v) {
        if (TextUtils.isEmpty(newAvatar)) {
            Toaster.show("选个酷酷的头像吧");
            return;
        }
        if (TextUtils.isEmpty(nickNameBox.getText().toString())) {
            Toaster.show("取个响亮的昵称吧");
        }
        String path = FileUtil.saveFile(getContext(), newAvatar, "avatar-" + ConfigCache.get().loginUserId);
        Bundle bundle = new Bundle();
        bundle.putString("name", nickNameBox.getText().toString());
        bundle.putString("desc", descBox.getText().toString());
        bundle.putString("avatar", path);
        bundle.putInt("gender", genderRadio.getCheckedRadioButtonId() == R.id.male_radio ? 1 : 2);
        EventBus.getDefault().post(new LoginEvent(LoginEvent.ACTION_DO_AVATAR, bundle));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            clearGlideCache();
            if (requestCode == PhotoPicker.REQUEST_CODE) {//拍照或者选择照片
                if (data != null) {
                    List<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    if (photos != null && !photos.isEmpty() && !TextUtils.isEmpty((newAvatar = photos.get(0)))) {
                        CLog.i("SelectPhoto", newAvatar);
                        UCrop.of(Uri.fromFile(new File(newAvatar)),
                                Uri.fromFile(new File(new File(newAvatar).getParentFile(), "temp.png")))
                                .withAspectRatio(1, 1)
                                .withMaxResultSize(500, 500)
                                .start(getActivity());
                        ImageLoader.circleImage(avaterView, newAvatar, R.mipmap.ic_launcher);
                    }
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {//裁剪
                Uri resultUri;
                if (data != null && (resultUri = UCrop.getOutput(data)) != null) {
                    String cropPath;
                    if (!TextUtils.isEmpty(cropPath = resultUri.getPath())) {
                        newAvatar = cropPath;
                        CLog.i("CropPhoto", newAvatar);
                        ImageLoader.circleImage(avaterView, newAvatar, R.mipmap.ic_launcher);
                    }

                }
            }
        }
    }

}
