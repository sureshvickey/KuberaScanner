package com.kubera.scanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.kubera.easyble.BleDevice;
import com.kubera.easyble.BleManager;
import com.kubera.easyble.Logger;
import com.kubera.easyble.gatt.callback.BleConnectCallback;
import com.kubera.easyble.gatt.callback.BleNotifyCallback;
import com.kubera.scanner.R;
import com.kubera.scanner.databinding.ActivityHomeScreenBinding;


public class HomeScreen extends AppCompatActivity {

    private ActivityHomeScreenBinding binding;
    FragmentCommunicator fragmentCommunicator;
    public static final String KEY_DEVICE_INFO = "keyDeviceInfo";

    private BleDevice device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

      //  BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_screen);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        device = getIntent().getParcelableExtra(KEY_DEVICE_INFO);
        BleManager.getInstance().connect(device.address, connectCallback);

    }

    private BleConnectCallback connectCallback = new BleConnectCallback() {
        @Override
        public void onStart(boolean startConnectSuccess, String info, BleDevice device) {
            Logger.e("start connecting:" + startConnectSuccess + "    info=" + info);
            HomeScreen.this.device = device;
            if (!startConnectSuccess) {
                Toast.makeText(HomeScreen.this, "start connecting fail:" + info, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onConnected(BleDevice device) {
            BleManager.getInstance().notify(device, "4fafc201-1fb5-459e-8fcc-c5c9c331914b", "beb5483e-36e1-4688-b7f5-ea07361b26a8", notifyCallback);
        }

        @Override
        public void onDisconnected(String info, int status, BleDevice device) {
            //Logger.e("disconnected!");
            onBackPressed();
        }

        @Override
        public void onFailure(int failCode, String info, BleDevice device) {
            Logger.e("connect fail:" + info);
            Toast.makeText(HomeScreen.this,
                    getResources().getString(failCode == BleConnectCallback.FAIL_CONNECT_TIMEOUT ?
                            R.string.tips_connect_timeout : R.string.tips_connect_fail), Toast.LENGTH_LONG).show();
        }
    };

    private BleNotifyCallback notifyCallback = new BleNotifyCallback() {
        @Override
        public void onCharacteristicChanged(byte[] data, BleDevice device) {
            //String s = ByteUtils.bytes2HexStr(data);
            Logger.e("onCharacteristicChanged:" + new String(data) );
            fragmentCommunicator.passData(new String(data));

        }

        @Override
        public void onNotifySuccess(String notifySuccessUuid, BleDevice device) {
            Logger.e("notify success uuid:" + notifySuccessUuid);
        }

        @Override
        public void onFailure(int failCode, String info, BleDevice device) {
            Logger.e("notify fail:" + info);
            Toast.makeText(HomeScreen.this, "notify fail:" + info, Toast.LENGTH_LONG).show();
        }
    };

    public void passVal(FragmentCommunicator fragmentCommunicator) {
        this.fragmentCommunicator = fragmentCommunicator;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BleManager.getInstance().disconnect(device.address);
    }
}