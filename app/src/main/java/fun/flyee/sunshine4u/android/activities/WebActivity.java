package fun.flyee.sunshine4u.android.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.fragments.WebFragment;

public abstract class WebActivity extends BaseActivity {

    protected String title, url, content, src, time;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_fragment);

    }

    @Override
    public void onPostCreate(Bundle b) {
        super.onPostCreate(b);
        url = getIntent().getStringExtra("url");
        Fragment fragment = null;
        if (!TextUtils.isEmpty(url)) {
            fragment = WebFragment.newWebFragment(needJs(), url);
        } else {
            fragment = WebFragment.newWebFragment(title, content, src, time);
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .show(fragment)
                .commit();
    }

    public abstract boolean needJs();
}
