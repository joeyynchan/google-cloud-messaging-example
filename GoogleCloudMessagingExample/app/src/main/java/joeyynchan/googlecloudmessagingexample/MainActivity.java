package joeyynchan.googlecloudmessagingexample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.w3c.dom.Text;

import java.io.IOException;


public class MainActivity extends Activity implements View.OnClickListener{

    public static final String ACTION_SHOWMESSAGE = "joeyynchan.mainactivity.action.SHOWMESSAGE";
    public static final String SENDER_ID = "139712018020"; /* Project Number */

    private BroadcastReceiver receiver;
    private GoogleCloudMessaging gcm;

    private Button register;
    private TextView mDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new MainActivityBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SHOWMESSAGE);
        registerReceiver(receiver, intentFilter);

        mDisplay = (TextView) findViewById(R.id.textview_gcmregistration);

        register = (Button) findViewById(R.id.button_register);
        register.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_register:
                new GcmRegistrion().execute();
                break;
            default:
                break;
        }
    }

    private class MainActivityBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (MainActivity.ACTION_SHOWMESSAGE.equals(action)) {
                TextView textview = (TextView) findViewById(R.id.textview_message);
                String message = intent.getStringExtra(GcmIntentService.PARAM_MESSAAGE);
                textview.setText(message);
            }
        }
    }

    private class GcmRegistrion extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String msg = "";
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                }
                String regid = gcm.register(SENDER_ID);
                msg = "Device registered, registration ID=" + regid;

            } catch (IOException ex) {
                msg = "Error :" + ex.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            mDisplay.setText(msg + "\n");
        }
    }
}
