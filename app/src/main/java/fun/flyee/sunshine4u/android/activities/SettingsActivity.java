package fun.flyee.sunshine4u.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.events.LogoutEvent;
import fun.flyee.sunshine4u.android.utils.CLog;
import fun.flyee.sunshine4u.android.widgets.Dialog;
import fun.flyee.sunshine4u.android.widgets.Toaster;

public class SettingsActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("设置");
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.change_userinfo_btn)
    public void changeUserInfo(View v) {
        startActivity(new Intent(this, EditProfileActivity.class));
    }

    @OnClick(R.id.change_password_btn)
    public void changePassword(View v) {
        startActivity(new Intent(this, ChangePasswordActivity.class));
    }

    @OnClick(R.id.suggestion_btn)
    public void writeSuggestion(View v) {
        startActivity(new Intent(this, SuggestiondActivity.class));
    }

    @OnClick(R.id.clear_memory_btn)
    public void clearMemory(View v) {
        Dialog.showConfirm(this, "清除缓存", "共有204k缓存"
                , "清除", "取消", () -> {
                    Toaster.show("已清除");
                },
                () -> {
                    CLog.i("haha");
                });
    }

    @OnClick(R.id.logout_btn)
    public void logout(View v) {
        Dialog.showConfirm(this, "注销", "您确定要退出登录吗?"
                , "退出", "取消", () -> {
                    EventBus.getDefault().post(new LogoutEvent());
                    finish();
                },
                () -> {
                    CLog.i("haha");
                });

    }

}
