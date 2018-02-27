package com.hy.androidlib;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;


/**
 * 权限申请器。实例化并调用申请。
 *
 * @author hy 2017/12/5
 */
public class PermissionManager {

    public static final String TAG = "PermissionManager";

    private String[] permissions;

    private OnPermissionStateCallback onPermissionStateCallback;

    private Activity context;

    private final static int PERMISSION_REQUEST_CODE = 0X111;

    public void setOnPermissionStateCallback(OnPermissionStateCallback onPermissionStateCallback) {
        this.onPermissionStateCallback = onPermissionStateCallback;
    }

    public PermissionManager(String[] permissions, Activity context) {
        this.permissions = permissions;
        this.context = context;
    }

    /**
     * Activity onResume中调用，检查权限是否被关闭。
     */
    public void onResume() {
        boolean versionRet = Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
        if (versionRet || checkPermission()) {
            onPermissionStateCallback.onSuccess();
        } else {
            requestPermissions(permissions);
        }
    }

    /**
     * 用于检查APP是否有权限，没有就申请。
     *
     * @param mPermissions
     */
    public void requestPermissions(String[] mPermissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : mPermissions) {
                //  检查是否有权限, 如果没有再申请
                int ret = context.checkSelfPermission(permission);
                if (ret != PackageManager.PERMISSION_GRANTED) {
                    context.requestPermissions(permissions, PERMISSION_REQUEST_CODE);
                    return;
                }
            }
        }
    }

    public void onRequestResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        // 权限申请失败
                        Logcat.e(TAG, permissions[i]);
                        onPermissionStatus(grantResults[i], permissions[i]);
                        return;
                    }
                }
                // 所有权限申请成功
                onPermissionStatus(PackageManager.PERMISSION_GRANTED, "");
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void onPermissionStatus(int ret, String permission) {
        if (ret != PackageManager.PERMISSION_GRANTED) {
            //finish();
            if (context.shouldShowRequestPermissionRationale(permission)) {
                Logcat.e(TAG, "shouldShowRequestPermissionRationale 1:" + permission);
                requestPermissions(new String[]{permission});
            } else {
                if (onPermissionStateCallback != null) {
                    onPermissionStateCallback.onFailed(permission, getIntentByPermission(permission));
                }
            }

        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean checkPermission() {
        for (String permission : permissions) {
            //  检查是否有权限, 如果没有再申请
            int ret = context.checkSelfPermission(permission);
            if (permission.equals(Manifest.permission.WRITE_SETTINGS)) {
                if (!Settings.System.canWrite(context)) {
                    return false;
                }
            } else if (ret != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private Intent getIntentByPermission(String permission) {
        Intent intent;
        if (permission.equals(Manifest.permission.WRITE_SETTINGS)) {
            intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + context.getApplication().getPackageName()));
        } else {
            intent = new Intent(Settings.ACTION_SETTINGS);// getIntentByBrand();
        }
        return intent;
    }

    public interface OnPermissionStateCallback {
        void onSuccess();

        void onFailed(String permission, Intent intent);
    }

    // 根据机型获取到进入设置的Intent。
    private Intent getIntentByBrand() {
        Intent intent = null;
        String manufacturer = Build.MANUFACTURER;
        Logcat.e(TAG, manufacturer);
        if (TextUtils.isEmpty(manufacturer)) {
            intent = new Intent(Settings.ACTION_SETTINGS);
        } else if (manufacturer.equalsIgnoreCase("Huawei")) {
            intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
        } else if (manufacturer.equalsIgnoreCase("Meizu")) {
            intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        } else if (manufacturer.equalsIgnoreCase("Xiaomi")) {
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.setComponent(componentName);
            intent.putExtra("extra_pkgname", BuildConfig.APPLICATION_ID);
        } else if (manufacturer.equalsIgnoreCase("Sony")) {
            intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
            ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
            intent.setComponent(comp);
        } else if (manufacturer.equalsIgnoreCase("OPPO")) {
            intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
            ComponentName comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
            intent.setComponent(comp);
        } else if (manufacturer.equalsIgnoreCase("LG")) {
            intent = new Intent("android.intent.action.MAIN");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
            ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
            intent.setComponent(comp);

        } else if (manufacturer.equalsIgnoreCase("Letv")) {
            intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
            ComponentName comp = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
            intent.setComponent(comp);
        } else {
            intent = new Intent(Settings.ACTION_SETTINGS);
        }
        return intent;
    }
}
