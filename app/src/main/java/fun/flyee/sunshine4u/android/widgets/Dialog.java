package fun.flyee.sunshine4u.android.widgets;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import fun.flyee.sunshine4u.android.R;

public class Dialog {

    public static void showMessage(Context context,
                                   String title,
                                   String message) {
        new AlertDialog.Builder(context, R.style.AppDialog)
                .setTitle(title)
                .setMessage(message)
                .show();
    }

    public static void showConfirm(Context context,
                                   String title,
                                   String message,
                                   String confirm,
                                   String cancel,
                                   Callback confirmCallback,
                                   Callback cancelCallback) {
        new AlertDialog.Builder(context, R.style.AppDialog)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(confirm, (dialog, which) -> {
                    dialog.dismiss();
                    confirmCallback.callback();
                })
                .setNegativeButton(cancel, (dialog, which) -> {
                    dialog.dismiss();
                    cancelCallback.callback();
                })
                .show();
    }

    public interface Callback {
        void callback();
    }

}
