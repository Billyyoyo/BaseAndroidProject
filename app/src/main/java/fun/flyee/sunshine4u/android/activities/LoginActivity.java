package fun.flyee.sunshine4u.android.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import fun.flyee.sunshine4u.android.events.LoginEvent;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.cache.ConfigCache;
import fun.flyee.sunshine4u.android.fragments.LoginFragment;
import fun.flyee.sunshine4u.android.fragments.ProfileFragment;
import fun.flyee.sunshine4u.android.fragments.RegisterFragment;
import fun.flyee.sunshine4u.android.models.User;
import fun.flyee.sunshine4u.android.widgets.Toaster;

public class LoginActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_fragment);
        if (ConfigCache.isLogined()) {
            Fragment fragment = ProfileFragment.newInstance();
            showFragment("编辑信息", fragment);
        } else {
            Fragment loginFragment = LoginFragment.newInstance();
            showFragment("登录", loginFragment);
        }
        EventBus.getDefault().register(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getSupportActionBar().getClass()
                    .getDeclaredMethod("setShowHideAnimationEnabled", boolean.class)
                    .invoke(getSupportActionBar(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
//            finish();
//            return;
//        }
        super.onBackPressed();
//        if (getSupportActionBar() != null) {
//            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
//                getSupportActionBar().hide();
//            } else {
//                getSupportActionBar().show();
//            }
//            getSupportActionBar().setTitle(getLastFragment().getTag());
//        }
    }

    private Fragment getLastFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (int i = 1; i <= fragments.size(); i++) {
                Fragment fragment = fragments.get(fragments.size() - i);
                if (fragment != null) {
                    return fragment;
                }
            }
        }
        return null;
    }

    @Subscribe
    public void goEvent(LoginEvent ev) {
        if (ev.action == LoginEvent.ACTION_GO_REGISTER) {
            RegisterFragment fragment = RegisterFragment.newInstance();
            showFragment("注册", fragment);
        } else if (ev.action == LoginEvent.ACTION_DO_REGISTER) {
            String name = ev.data.getString("name");
            String pwd = ev.data.getString("pwd");
            User user = User.getUserByName(name);
            if (user == null) {
                user = new User();
                user.loginName = name;
                user.password = pwd;
                user.save();
                ConfigCache.get().loginUserId = user.getID();
                ConfigCache.save();
                ProfileFragment fragment = ProfileFragment.newInstance();
                showFragment("编辑信息", fragment);
            } else {
                Toaster.show("该用户名已被注册");
            }
        } else if (ev.action == LoginEvent.ACTION_DO_LOGIN) {
            String name = ev.data.getString("name");
            String pwd = ev.data.getString("pwd");
            User user = User.getUserByName(name);
            if (user == null) {
                Toaster.show("用户未注册");
            } else {
                if (!user.password.equals(pwd)) {
                    Toaster.show("密码不正确");
                } else {
                    ConfigCache.get().loginUserId = user.getID();
                    ConfigCache.save();
                    if (user.gender != null && user.gender > 0) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        ProfileFragment fragment = ProfileFragment.newInstance();
                        showFragment("编辑信息", fragment);
                    }
                }
            }
        } else if (ev.action == LoginEvent.ACTION_DO_AVATAR) {
            if (ConfigCache.get().loginUserId == 0) {
                Toaster.show("没有登录");
                Fragment fragment = LoginFragment.newInstance();
                showFragment("登录", fragment);
                return;
            }
            User user = User.load(User.class, ConfigCache.get().loginUserId);
            if (user == null) {
                Toaster.show("找不到用户");
                return;
            }
            user.nickName = ev.data.getString("name");
            user.desc = ev.data.getString("desc");
            user.avatar = ev.data.getString("avatar");
            user.gender = ev.data.getInt("gender");
            user.save();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void showFragment(String tag, Fragment fragment) {
//        if (getSupportActionBar() != null) {
//            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//                getSupportActionBar().show();
//            } else {
//                getSupportActionBar().hide();
//            }
//        }
        getSupportActionBar().setTitle(tag);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, tag);
        transaction.show(fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        getLastFragment().onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
