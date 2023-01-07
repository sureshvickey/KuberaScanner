package com.kubera.easyble.gatt.callback;


import com.kubera.easyble.BleDevice;

public interface BleRssiCallback extends BleCallback {

    void onRssi(int rssi, BleDevice bleDevice);
}
