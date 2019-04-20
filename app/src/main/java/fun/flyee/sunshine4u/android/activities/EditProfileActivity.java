package fun.flyee.sunshine4u.android.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.cache.ConfigCache;
import fun.flyee.sunshine4u.android.events.EditProfileEvent;
import fun.flyee.sunshine4u.android.events.LoginEvent;
import fun.flyee.sunshine4u.android.fragments.LoginFragment;
import fun.flyee.sunshine4u.android.fragments.ProfileFragment;
import fun.flyee.sunshine4u.android.models.User;
import fun.flyee.sunshine4u.android.widgets.Toaster;

public class EditProfileActivity extends BaseActivity {

    private Fragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("编辑个人信息");
        setContentView(R.layout.activity_edit_profile);
        fragment = ProfileFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .show(fragment)
                .commitAllowingStateLoss();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void doEditProfile(LoginEvent ev) {
        if (ev.action == LoginEvent.ACTION_DO_AVATAR) {
            User user = User.getLoginUser();
            user.nickName = ev.data.getString("name");
            user.desc = ev.data.getString("desc");
            user.avatar = ev.data.getString("avatar");
            user.gender = ev.data.getInt("gender");
            user.save();
            EventBus.getDefault().post(new EditProfileEvent());
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}
