package fun.flyee.sunshine4u.android.activities;

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

public class SuggestiondActivity extends BaseActivity {

    @BindView(R.id.content_box)
    public TextView contentBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("意见反馈");
        setContentView(R.layout.activity_suggestion);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit_btn)
    public void doSubmit(View v) {
        if (TextUtils.isEmpty(contentBox.getText().toString())) {
            Toaster.show("不打算说点什么吗?");
            return;
        }
        Toaster.show("感谢您的宝贵意见, 我们会认真考虑~");
        finish();
    }

}
