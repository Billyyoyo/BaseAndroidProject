package fun.flyee.sunshine4u.android.modules.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.activeandroid.Model;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.activities.WebActivity;
import fun.flyee.sunshine4u.android.models.News;
import fun.flyee.sunshine4u.android.widgets.Toaster;

public class ArticleActivity extends WebActivity {

    private Long id;

    private News news;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        id = getIntent().getLongExtra("id", 0l);
        news = Model.load(News.class, id);
        title = news.title;
        content = news.content;
        src = news.src;
        time = news.time;
    }

    @Override
    public void onPostCreate(Bundle b) {
        super.onPostCreate(b);
        news.saveRecent(news);
    }

    public boolean needJs() {
        return false;
    }

    public static void go(Context context, Long id) {
        Intent in = new Intent(context, ArticleActivity.class);
        in.putExtra("id", id);
        context.startActivity(in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_article, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Toaster.show("即将开放");
                break;
            case R.id.action_fav:
                news.saveFav(news);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
