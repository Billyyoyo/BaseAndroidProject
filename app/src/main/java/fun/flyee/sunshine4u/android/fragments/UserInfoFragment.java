package fun.flyee.sunshine4u.android.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.modules.news.NewsListActivity;
import fun.flyee.sunshine4u.android.activities.SettingsActivity;
import fun.flyee.sunshine4u.android.events.EditProfileEvent;
import fun.flyee.sunshine4u.android.models.News;
import fun.flyee.sunshine4u.android.models.User;
import fun.flyee.sunshine4u.android.utils.DateUtil;
import fun.flyee.sunshine4u.android.utils.ImageLoader;
import fun.flyee.sunshine4u.android.widgets.Toaster;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment extends BaseFragment {

    @BindView(R.id.avatar_view)
    public ImageView avatarView;
    @BindView(R.id.gender_view)
    public ImageView genderView;
    @BindView(R.id.nickname_view)
    public TextView nicknameView;
    @BindView(R.id.point_view)
    public TextView pointView;

    private User host;

    public UserInfoFragment() {
    }

    public static UserInfoFragment newInstance() {
        return new UserInfoFragment();
    }

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        EventBus.getDefault().register(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    public void onViewCreated(View v, Bundle b) {
        super.onViewCreated(v, b);
        showUserInfo();
    }

    private void showUserInfo() {
        host = User.getLoginUser();
        nicknameView.setText(host.nickName);
        pointView.setText("" + host.point);
        ImageLoader.circleImage(avatarView, host.avatar, R.mipmap.ic_launcher);
    }

    @OnClick(R.id.setting_btn)
    public void goSetting() {
        getActivity().startActivity(new Intent(getActivity(), SettingsActivity.class));
    }

    @OnClick(R.id.checkin_btn)
    public void doCheckin(View v) {
        if (System.currentTimeMillis() - host.checkInTime > DateUtil.DAY) {
            host.checkInTime = System.currentTimeMillis();
            host.checkInCount++;
            host.point += 5;
            Toaster.show("获得5点积分, 总共签到" + host.checkInCount + "次");
            host.save();
            pointView.setText("" + host.point);
        } else {
            Toaster.show("还不能签到哦");
        }
    }

    @OnClick(R.id.point_btn)
    public void showPoint(View v) {
//        Dialog.showMessage(getContext(),
//                "我的积分",
//                "积分:" + host.point + "\n" +
//                        "积分:" + host.point + "\n");
    }

    @OnClick(R.id.recent_btn)
    public void goRecent(View v) {
        NewsListActivity.go(getContext(), News.TYPE_RECENT);
    }

    @OnClick(R.id.favorite_btn)
    public void goFav(View v) {
        NewsListActivity.go(getContext(), News.TYPE_FAV);
    }

    @Subscribe
    public void whenEditProfile(EditProfileEvent ev) {
        showUserInfo();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
