package fun.flyee.sunshine4u.android.widgets;

import android.support.design.widget.Snackbar;
import android.widget.Toast;

import fun.flyee.sunshine4u.android.ApplicationContext;

public class Toaster {

    public static void show(String message) {
        Toast.makeText(ApplicationContext.getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
