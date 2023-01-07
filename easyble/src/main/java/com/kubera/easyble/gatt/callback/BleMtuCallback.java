package com.kubera.easyble.gatt.callback;


import com.kubera.easyble.BleDevice;


public interface BleMtuCallback extends BleCallback {
    void onMtuChanged(int mtu, BleDevice device);
}
