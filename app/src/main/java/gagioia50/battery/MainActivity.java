package gagioia50.battery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvLevel, tvCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLevel = findViewById(R.id.tvLevel);
        tvCar = findViewById(R.id.tvCar);

        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        MyBroadCastReceiver myReceiver = new MyBroadCastReceiver();
        registerReceiver(myReceiver, filter);
    }

    private class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean isCharging = chargePlug == BatteryManager.BATTERY_PLUGGED_USB ||
                    chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            int batteryPct = Math.round(level*100 / (float) scale);
            String batteryPctString = batteryPct +" %";
            tvLevel.setText(batteryPctString);

            if (isCharging) {
                tvCar.setText(R.string.si);
            } else {
                tvCar.setText(R.string.no);
            }
       }
   }
    
}