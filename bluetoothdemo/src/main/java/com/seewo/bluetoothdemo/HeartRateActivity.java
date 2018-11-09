package com.seewo.bluetoothdemo;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HeartRateActivity extends AppCompatActivity {

    private static final String TAG = "@HR";

    // 心率带。
//    private static final String HR_ADDRESS = "F2:B5:01:6D:90:56";
    // 咕咚心率耳机。
//    private static final String HR_ADDRESS = "0C:73:EB:38:94:4B"; // CODOON Quiet
    private static final String HR_ADDRESS = "0C:73:EB:39:B8:0F"; // COD_HP18Q01
    private static final UUID HR_SERVICE_UUID = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb");// 0 1 d f a

    @BindView(R.id.button1)
    Button mButton1;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.button3)
    Button mButton3;
    @BindView(R.id.button4)
    Button mButton4;
    @BindView(R.id.text)
    TextView mMessage;
    @BindView(R.id.info)
    TextView mInfo;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.button5)
    Button mButton5;
    @BindView(R.id.button6)
    Button mButton6;

    private String mMyName;
    private String mMyAddress;
    private boolean mIsSupportBLE;
    private boolean mIsFoundHR;

    private int mBpm;
    private int mBatteryLevel;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private BluetoothDevice mHRDevice;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattService mBluetoothGattService;
    private BluetoothGattService mBluetoothBatteryGattService;
    private BluetoothGattCharacteristic mBluetoothGattCharacteristic;
    private BluetoothGattCharacteristic mBluetoothBatteryGattCharacteristic;
    private BluetoothGattDescriptor mBluetoothGattDescriptor;

    private ScanCallback mSCallback = new SC();
    private BluetoothGattCallback mBGCallback = new BGC();

    private Set<BluetoothDevice> mTotalDevice = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);
        ButterKnife.bind(this);

        mIsSupportBLE = getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
        if (!mIsSupportBLE) {
            mButton1.setClickable(false);
            mButton2.setClickable(false);
            mButton3.setClickable(false);
            mButton4.setClickable(false);
        }

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        mBluetoothAdapter.enable();

        mInfo.setText(generateInfo());
    }

    @SuppressLint("HardwareIds")
    private String generateInfo() {
        mMyName = mBluetoothAdapter.getName();
        mMyAddress = mBluetoothAdapter.getAddress();

        StringBuilder sb = new StringBuilder();
        sb.append(mMyName).append(" : ").append(mMyAddress);

        // 判断当前设备是否支持ble。
        if (mIsSupportBLE) {
            sb.append("  支持BLE");
        } else {
            sb.append("  不支持BLE");
        }

        if (mIsFoundHR) {
            sb.append("\n").append("找到心率设备 ")
                    .append(mHRDevice.getName()).append(" : ").append(HR_ADDRESS);
            if (mBluetoothGattCharacteristic != null) {
                sb.append("  心率: ").append(mBpm).append(" bpm");
            }
            if (mBluetoothGattDescriptor != null) {
                byte[] bs = mBluetoothGattDescriptor.getValue();
                sb.append("  描述: ").append(Arrays.toString(bs));
            }
            if (mBluetoothBatteryGattService != null) {
                sb.append("  电量: ").append(mBatteryLevel);
            }
        } else {
            sb.append("\n").append("未找到心率设备");
        }

        return sb.toString();
    }

    @OnClick(R.id.button1)
    public void click1(View v) {
        mBluetoothLeScanner.startScan(mSCallback);

//        mBluetoothAdapter.startDiscovery();

    }

    @OnClick(R.id.button2)
    public void click2(View v) {
        mBluetoothLeScanner.stopScan(mSCallback);

//        mBluetoothAdapter.cancelDiscovery();

        Log.d(TAG, "click2: " + Arrays.toString(mTotalDevice.toArray()));
        mTotalDevice.clear();
    }

    @OnClick(R.id.button3)
    public void click3(View v) {
        if (mHRDevice == null) {
            toast("还未找到设备");
            return;
        }
        mHRDevice.connectGatt(this, false, mBGCallback);
    }

    @OnClick(R.id.button4)
    public void click4(View v) {
        if (mHRDevice == null || mBluetoothGatt == null) {
            toast("还未找到设备或未连接上");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    @OnClick(R.id.button5)
    public void click5(View v) {
        if (mHRDevice == null || mBluetoothGatt == null) {
            toast("还未找到设备或未连接上");
            return;
        }
        mBluetoothGatt.discoverServices();
    }

    @OnClick(R.id.button6)
    public void click6(View v) {
        if (mBluetoothGattService == null) {
            toast("还未找到设备Service");
            return;
        }

        List<BluetoothGattCharacteristic> characteristics = mBluetoothGattService.getCharacteristics();
        mMessage.append("getCharacteristics:\n");
        for (BluetoothGattCharacteristic characteristic : characteristics) {
            mBluetoothGatt.readCharacteristic(characteristic);
            mMessage.append("value: " + Arrays.toString(characteristic.getValue()) +
                    ", uuid: " + characteristic.getUuid());
            mMessage.append("\n");
        }
        characteristics = mBluetoothBatteryGattService.getCharacteristics();
        mMessage.append("battery getCharacteristics:\n");
        for (BluetoothGattCharacteristic characteristic : characteristics) {
            mBluetoothGatt.readCharacteristic(characteristic);
            mMessage.append("value: " + Arrays.toString(characteristic.getValue()) +
                    ", uuid: " + characteristic.getUuid());
            mMessage.append("\n");
        }
        mScrollView.fullScroll(ScrollView.FOCUS_DOWN);

        mBluetoothGattCharacteristic = mBluetoothGattService.getCharacteristic(Constant.HEART_RATE_MEASUREMENT);
        if (mBluetoothGattCharacteristic != null) {
            boolean isSuccess = mBluetoothGatt.setCharacteristicNotification(mBluetoothGattCharacteristic, true);
            toast("设置Characteristic监听 " + (isSuccess ? "成功" : "失败"));

            mBluetoothGattDescriptor = mBluetoothGattCharacteristic.getDescriptor(Constant.CHAR_CLIENT_CONFIG);
            mBluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(mBluetoothGattDescriptor);
        }

        mBluetoothBatteryGattCharacteristic = mBluetoothBatteryGattService.getCharacteristic(Constant.BATTERY_LEVEL);
        if (mBluetoothBatteryGattCharacteristic != null) {
            boolean isSuccess = mBluetoothGatt.setCharacteristicNotification(mBluetoothBatteryGattCharacteristic, true);
            toast("设置Characteristic监听 " + (isSuccess ? "成功" : "失败"));

            mBluetoothGattDescriptor = mBluetoothBatteryGattCharacteristic.getDescriptor(Constant.CHAR_CLIENT_CONFIG);
            mBluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(mBluetoothGattDescriptor);

            mBluetoothGatt.readCharacteristic(mBluetoothBatteryGattCharacteristic);
            try {
                mBatteryLevel = mBluetoothBatteryGattCharacteristic.getIntValue(
                        BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                Log.d(TAG, "click6: " + mBatteryLevel);
                mInfo.setText(generateInfo());
            } catch (Exception e) {
                Log.e(TAG, "click6: ", e);
            }
        }
    }

    private class SC extends ScanCallback {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            runOnUiThread(() -> {
                mMessage.append("onScanResult: ");
                BluetoothDevice bd = result.getDevice();
                if (bd != null) {
                    mMessage.append(bd.getName() == null ? "null" : bd.getName());
                    mMessage.append(" : ");
                    mMessage.append(bd.getAddress() == null ? "null" : bd.getAddress());
                    mMessage.append("\n");

                    mTotalDevice.add(bd);
                } else {
                    mMessage.append("no device.\n");
                }
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                BluetoothDevice device = result.getDevice();
                if (HR_ADDRESS.equals(device.getAddress())) {
                    mHRDevice = device;
                    mIsFoundHR = true;
                    mInfo.setText(generateInfo());

                    BluetoothClass bluetoothClass = device.getBluetoothClass();
                    Log.d(TAG, "onScanResult: class: " + bluetoothClass.toString());
                }
            });
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            runOnUiThread(() -> {
                mMessage.append("onBatchScanResults:\n");
                for (ScanResult result : results) {
                    mMessage.append(result.toString());
                    mMessage.append("\n");
                }
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            });
        }

        @Override
        public void onScanFailed(int errorCode) {
            runOnUiThread(() -> {
                mMessage.append("scanFailed: " + errorCode);
                mMessage.append("\n");
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            });
        }
    }

    private class BGC extends BluetoothGattCallback {
        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            runOnUiThread(() -> {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    mBluetoothGatt = gatt;
                    gatt.discoverServices();
                    mMessage.append("设备成功连接\n");
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    mMessage.append("设备断开连接\n");
                    // clear.
                    mBluetoothGatt = null;
                    mBluetoothGattService = null;
                    mBluetoothGattCharacteristic = null;
                    mBluetoothBatteryGattService = null;
                }
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                toast("连接状态：" + newState);
            });
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            runOnUiThread(() -> {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    mMessage.append("onServicesDiscovered:\n");
                    List<BluetoothGattService> services = gatt.getServices();
                    for (BluetoothGattService service : services) {
                        mMessage.append("type: " + service.getType() + ", uuid: " + service.getUuid());
                        mMessage.append("\n");
                    }

                    mBluetoothGattService = gatt.getService(Constant.HEART_RATE);
                    mBluetoothBatteryGattService = gatt.getService(Constant.BATTERY_SERVICE);
                }
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                toast("设备服务发现状态: " + status);
            });
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            runOnUiThread(() -> {
                try {
                    if (Constant.HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
                        int flag = characteristic.getProperties();
                        int format;
                        if ((flag & 0x01) != 0) {
                            format = BluetoothGattCharacteristic.FORMAT_UINT16;
                        } else {
                            format = BluetoothGattCharacteristic.FORMAT_UINT8;
                        }
                        mBpm = characteristic.getIntValue(format, 1);
                        mInfo.setText(generateInfo());
                    } else if (Constant.BATTERY_LEVEL.equals(characteristic.getUuid())) {
                        mBatteryLevel = characteristic.getIntValue(
                                BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                        Log.d(TAG, "onCharacteristicChanged: " + mBatteryLevel);
                        mInfo.setText(generateInfo());
                    } else {
                        // For all other profiles, writes the data formatted in HEX.
                        final byte[] data = characteristic.getValue();
                        if (data != null && data.length > 0) {
                            final StringBuilder sb = new StringBuilder(data.length);
                            for (byte byteChar : data) {
                                sb.append(String.format("%02X ", byteChar));
                            }
                            mMessage.append("unknown onCharacteristicChanged: " + sb.toString());
                        }
                    }
                } catch (Exception e) {
                    toast("读取失败。" + e.getCause().toString());
                }
            });
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
        }
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}
