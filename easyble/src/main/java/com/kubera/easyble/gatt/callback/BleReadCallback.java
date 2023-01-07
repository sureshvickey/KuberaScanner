package com.kubera.easyble.gatt.callback;


import com.kubera.easyble.BleDevice;

public interface BleReadCallback extends BleCallback {
    void onReadSuccess(byte[] data, BleDevice device);
}
