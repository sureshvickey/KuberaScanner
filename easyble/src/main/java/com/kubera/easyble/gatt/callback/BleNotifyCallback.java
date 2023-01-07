package com.kubera.easyble.gatt.callback;


import com.kubera.easyble.BleDevice;

public interface BleNotifyCallback extends BleCallback {
    void onCharacteristicChanged(byte[] data, BleDevice device);

    void onNotifySuccess(String notifySuccessUuid, BleDevice device);
}
