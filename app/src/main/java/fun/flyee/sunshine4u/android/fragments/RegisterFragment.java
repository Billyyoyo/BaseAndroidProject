package fun.flyee.sunshine4u.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
public class RegisterFragment extends BaseFragment {

    @BindView(R.id.user_name_box)
    EditText userNameBox;
    @BindView(R.id.password_box)
    EditText passwordBox;
    @BindView(R.id.confirm_box)
    EditText confirmBox;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register;
    }

    private boolean validateAccountPwd() {
        String username = userNameBox.getText().toString().trim();
        String password = passwordBox.getText().toString().trim();
        String confirm = confirmBox.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toaster.show("请输入用户名");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toaster.show("密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(confirm)) {
            Toaster.show("请再次确认密码");
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
        if (!password.equals(confirm)) {
            Toaster.show("2次输入密码不一致");
            return false;
        }
        return true;
    }

    @OnClick(R.id.register_btn)
    public void doRegister(View v) {
        if (!validateAccountPwd()) return;
        Bundle data = new Bundle();
        data.putString("name", userNameBox.getText().toString());
        data.putString("pwd", passwordBox.getText().toString());
        EventBus.getDefault().post(new LoginEvent(LoginEvent.ACTION_DO_REGISTER, data));
    }

}
