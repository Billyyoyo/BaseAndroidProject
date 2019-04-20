package fun.flyee.sunshine4u.android;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import fun.flyee.sunshine4u.android.utils.AlarmUtil;
import fun.flyee.sunshine4u.android.utils.CLog;

import static android.app.AlarmManager.RTC_WAKEUP;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class AlarmService extends IntentService {

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
//            if ("CREATE_ALARM".equals(action)) {
//                Long requestCode = intent.getLongExtra("request_code", 0);
//                AlarmUtil.setAlarm(this,
//                        1000 + requestCode, RTC_WAKEUP,
//                        System.currentTimeMillis() + 10, note.content);
//            } else if("EXECUTE_ALARM".equals(action)){
//                String title = "提醒";
//                String content = intent.getStringExtra("content");
//                CLog.e("-------------------" + content + "-------------------");
//            } else if ("CANCEL_ALARM".equals(action)){
//
//            }
        }
    }

    public static Intent getIntent(Context context, String content) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction("FUN_ALARM");
        intent.putExtra("content", content);
        return intent;
    }

}
