package com.seewo.bluetoothdemo;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HeartRateActivity extends AppCompatActivity {

    private static final String HR_ADDRESS = "F2:B5:01:6D:90:56";

    @BindView(R.id.button1)
    Button mButton1;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.text)
    TextView mMessage;
    @BindView(R.id.info)
    TextView mInfo;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;

    private String mMyName;
    private String mMyAddress;
    private boolean mIsSupportBLE;
    private boolean mIsFoundHR;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private BluetoothDevice mHRDevice;

    private ScanCallback mSCallback = new SC();
    private BluetoothGattCallback mBGCallback = new BGC();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);
        ButterKnife.bind(this);

        mIsSupportBLE = getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);

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
        } else {
            sb.append("\n").append("未找到心率设备");
        }

        return sb.toString();
    }

    @OnClick(R.id.button1)
    public void click1(View v) {
        mBluetoothLeScanner.startScan(mSCallback);

    }

    @OnClick(R.id.button2)
    public void click2(View v) {
        mBluetoothLeScanner.stopScan(mSCallback);

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
        if (mHRDevice == null) {
            toast("还未找到设备");
            return;
        }

    }

    private class SC extends ScanCallback {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            mMessage.append("onScanResult: ");
            BluetoothDevice bd = result.getDevice();
            if (bd != null) {
                mMessage.append(bd.getName() == null ? "null" : bd.getName());
                mMessage.append(" : ");
                mMessage.append(bd.getAddress() == null ? "null" : bd.getAddress());
                mMessage.append("\n");
            } else {
                mMessage.append("no device.\n");
            }
            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            BluetoothDevice device = result.getDevice();
            if (HR_ADDRESS.equals(device.getAddress())) {
                mHRDevice = device;
                mIsFoundHR = true;
                mInfo.setText(generateInfo());
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            mMessage.append("onBatchScanResults:\n");
            for (ScanResult result : results) {
                mMessage.append(result.toString());
                mMessage.append("\n");
            }
            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }

        @Override
        public void onScanFailed(int errorCode) {
            mMessage.append("scanFailed: " + errorCode);
            mMessage.append("\n");
            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
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
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                gatt.discoverServices();
            }
            toast("连接状态：" + newState);
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                List<BluetoothGattService> services = gatt.getServices();
                for (BluetoothGattService service : services) {
                    mMessage.append("type: " + service.getType() + ", uuid: " + service.getUuid());
                    mMessage.append("\n");
                }
            }
            toast("设备发现状态: " + status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}
