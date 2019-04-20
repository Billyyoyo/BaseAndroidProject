package fun.flyee.sunshine4u.android.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.models.User;
import fun.flyee.sunshine4u.android.utils.Util;
import fun.flyee.sunshine4u.android.widgets.Toaster;

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.old_pwd_box)
    public TextView oldPwdBox;
    @BindView(R.id.new_pwd_box)
    public TextView newPwdBox;
    @BindView(R.id.cnf_pwd_box)
    public TextView cnfPwdBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("修改密码");
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit_btn)
    public void doSubmit(View v) {
        User user = User.getLoginUser();
        if (validateForm(user)) {
            user.password = newPwdBox.getText().toString();
            user.save();
            Toaster.show("密码修改成功");
            finish();
        }
    }

    private boolean validateForm(User user) {
        if (TextUtils.isEmpty(oldPwdBox.getText().toString())) {
            Toaster.show("请输入旧密码");
            return false;
        }
        if (TextUtils.isEmpty(newPwdBox.getText().toString())) {
            Toaster.show("请输入新密码");
            return false;
        }
        if (TextUtils.isEmpty(cnfPwdBox.getText().toString())) {
            Toaster.show("请再次输入密码");
            return false;
        }
        if (user == null) {
            return false;
        }
        if (!user.password.equals(oldPwdBox.getText().toString())) {
            Toaster.show("和旧密码不一致");
            return false;
        }
        if (!Util.isPassword(newPwdBox.getText().toString())) {
            Toaster.show("密码只能包含英文、数字、_.-@$!*%符号，长度6-16");
            return false;
        }
        if (!newPwdBox.getText().toString().equals(cnfPwdBox.getText().toString())) {
            Toaster.show("2次输入密码不一致");
            return false;
        }
        return true;
    }
}
