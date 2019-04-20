package fun.flyee.sunshine4u.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fun.flyee.sunshine4u.android.events.LoginEvent;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.utils.Util;
import fun.flyee.sunshine4u.android.widgets.Toaster;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {

    @BindView(R.id.user_name_box)
    EditText userNameBox;
    @BindView(R.id.password_box)
    EditText passwordBox;
    @BindView(R.id.show_hide_pwd_btn)
    ImageView showHidePwdBtn;

    public static Fragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {

    }

    @Override
    public int getLayoutId(){
        return R.layout.fragment_login;
    }

    private boolean validateAccountPwd() {
        String username = userNameBox.getText().toString().trim();
        String password = passwordBox.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toaster.show("请输入用户名");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toaster.show("密码不能为空");
            return false;
        }
        if (!Util.isUserName(username)) {
            Toaster.show("用户名只能包含英文、数字、._-@符号，长度4-20");
            return false;
        }
        if (!Util.isPassword(password)) {
            Toaster.show("密码只能包含英文、数字、_.-@$!*%符号，长度6-16");
            return false;
        }
        return true;
    }

    @OnClick(R.id.show_hide_pwd_btn)
    public void doShowHidePwd(View v) {
        showHidePwdBtn.setSelected(!showHidePwdBtn.isSelected());
        showHidePwdBtn.setImageResource(showHidePwdBtn.isSelected() ? R.drawable.pwd_show : R.drawable.pwd_hide);
        if (showHidePwdBtn.isSelected()) {
            //如果选中，显示密码
            passwordBox.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //否则隐藏密码
            passwordBox.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        passwordBox.setSelection(passwordBox.getText().toString().length());
    }

    @OnClick(R.id.login_btn)
    public void doLogin(View v) {
        if (!validateAccountPwd()) {
            return;
        }
        Bundle data = new Bundle();
        data.putString("name", userNameBox.getText().toString());
        data.putString("pwd", passwordBox.getText().toString());
        EventBus.getDefault().post(new LoginEvent(LoginEvent.ACTION_DO_LOGIN, data));
    }

    @OnClick(R.id.register_btn)
    public void goRegister(View v) {
        EventBus.getDefault().post(new LoginEvent(LoginEvent.ACTION_GO_REGISTER, null));
    }

}
