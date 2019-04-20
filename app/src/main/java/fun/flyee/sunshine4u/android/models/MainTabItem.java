package fun.flyee.sunshine4u.android.models;

import com.flyco.tablayout.listener.CustomTabEntity;

public class MainTabItem implements CustomTabEntity {

    private String title;
    private int icon;

    public MainTabItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return icon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return 0;
    }
}
