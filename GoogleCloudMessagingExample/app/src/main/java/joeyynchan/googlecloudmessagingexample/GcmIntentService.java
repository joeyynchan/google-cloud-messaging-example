package joeyynchan.googlecloudmessagingexample;

import android.app.IntentService;
import android.content.Intent;
import android.widget.TextView;

public class GcmIntentService extends IntentService {

    /* List of performable actions */
    public static final String ACTION_FOO = "joeyynchan.googlecloudmessagingexample.action.FOO";

    /* List of usable parameters */
    public static final String PARAM_MESSAAGE = "joeyynchan.googlecloudmessagingexample.param.MESSAGE";

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(PARAM_MESSAAGE);
                handleActionFoo(param1);
            }
        }
    }

    private void handleActionFoo(String param1) {
        Intent intent = new Intent();
        intent.setAction(MainActivity.ACTION_SHOWMESSAGE);
        intent.putExtra(PARAM_MESSAAGE, param1);
    }

}
