package com.kubera.easyble.gatt.callback;


import com.kubera.easyble.BleDevice;

public interface BleWriteByBatchCallback extends BleCallback {
    void writeByBatchSuccess(byte[] data, BleDevice device);
}
