package com.kubera.easyble.gatt.callback;


import com.kubera.easyble.BleDevice;

public interface BleWriteCallback extends BleCallback {
    void onWriteSuccess(byte[] data, BleDevice device);
}
