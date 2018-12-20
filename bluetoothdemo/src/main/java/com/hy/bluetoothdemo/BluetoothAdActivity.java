package com.hy.bluetoothdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * 接收小米手环3广播包demo。
 */
public class BluetoothAdActivity extends AppCompatActivity {

    private static final String TAG = "@BluetoothAd";

    Button mStartScanBtn;
    Button mStopScanBtn;

    TextView mText;

    BluetoothLeScanner mLeScanner;
    ScanCallback mSc = new SC();

    BluetoothLeAdvertiser mLeAdvertiser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_ad);

        mStartScanBtn = findViewById(R.id.startScanButton);
        mStopScanBtn = findViewById(R.id.stopScanButton);
        mText = findViewById(R.id.text);

        mLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
        mLeAdvertiser = BluetoothAdapter.getDefaultAdapter().getBluetoothLeAdvertiser();

        mStartScanBtn.setOnClickListener(v -> mLeScanner.startScan(mSc));
        mStopScanBtn.setOnClickListener(v -> mLeScanner.stopScan(mSc));

        LePackage lePackage = new LePackage(0, 0);
        byte[] serviceData = new byte[]{(byte) 0x8b, 8, 0, 0};
        lePackage.step = ((int) serviceData[0] & 0xff);
        lePackage.step |= ((int) serviceData[1] & 0xff) << 8;
        lePackage.step |= ((int) serviceData[2] & 0xff) << 16;
        lePackage.step |= ((int) serviceData[3] & 0xff) << 24;
        Log.d(TAG, "onCreate: " + lePackage.step);
    }

    class SC extends ScanCallback {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            ScanRecord scanRecord = result.getScanRecord();
            if (scanRecord != null) {
                String localName = scanRecord.getDeviceName();
                if (localName != null) {
                    runOnUiThread(() -> {
                        mText.setText(localName);
                        Log.i(TAG, "onScanResult: " + localName);
                    });
                }

                LePackage lePackage = LePackage.parse(result.getScanRecord());
                if (lePackage.step >= 0) {
                    Log.d(TAG, "onScanResult: le package: " + lePackage.step + " " + lePackage.heartRate);
                    runOnUiThread(() -> mText.setText("step: " + lePackage.step + ", heartRate: " + lePackage.heartRate));
                }
            } else {
                Log.d(TAG, "onScanResult: scan record is null. " + callbackType);
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
        }

        @Override
        public void onScanFailed(int errorCode) {
        }
    }

    static class LePackage {

        static LePackage sPackage = new LePackage(-1, -1);

        public static LePackage parse(ScanRecord scanRecord) {
            LePackage lePackage = sPackage;
            lePackage.step = lePackage.heartRate = -1;

            // advertisingData.
            byte[] data = scanRecord.getManufacturerSpecificData(0x0157);
            if (data != null && data.length == 26) {
                Log.d(TAG, "parse: user status: " + data[20]);
                String mac = getHexString(data, 21);
                Log.d(TAG, "parse: mac: " + mac);
            }

            // scan response.
            String localName = scanRecord.getDeviceName();
            if ("Mi Band 3".equals(localName)) {
                ParcelUuid uuid = ParcelUuid.fromString("0000fee0-0000-1000-8000-00805F9B34FB");
                byte[] serviceData = scanRecord.getServiceData(uuid);
                Log.d(TAG, "parse: uuid: " + uuid.toString() + " data: " + Arrays.toString(serviceData));

                if (serviceData != null && serviceData.length >= 4) {
                    lePackage.step = ((int) serviceData[0] & 0xff);
                    lePackage.step |= ((int) serviceData[1] & 0xff) << 8;
                    lePackage.step |= ((int) serviceData[2] & 0xff) << 16;
                    lePackage.step |= ((int) serviceData[3] & 0xff) << 24;

                    if (serviceData.length >= 5) {
                        lePackage.heartRate = serviceData[4];
                    }
                }
            }

            return lePackage;
        }

        public int step;

        public int heartRate;

        public LePackage(int step, int heartRate) {
            this.step = step;
            this.heartRate = heartRate;
        }

        public static String getHexString(byte[] b, int start) {
            StringBuilder a = new StringBuilder();
            for (int i = start; i < start + 6; i++) {
                String hex = Integer.toHexString(b[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                a.append(":").append(hex);
            }

            return a.toString();
        }
    }

}
