package com.kubera.easyble.scan;


import com.kubera.easyble.BleDevice;

public interface BleScanCallback {
    void onLeScan(BleDevice device, int rssi, byte[] scanRecord);

    void onStart(boolean startScanSuccess, String info);

    void onFinish();
}
