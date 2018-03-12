package com.hy.multiprocess.backgroundprocess1;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.hy.androidlib.Logcat;
import com.hy.multiprocess.IToken;

import java.util.Random;

/**
 * 远程服务端，随机生成Token。
 */
public class TokenService extends Service {

    private static final String TAG = "@TokenService";

    private Random random;

    @Override
    public void onCreate() {
        super.onCreate();
        Logcat.i(TAG, "onCreate");
        random = new Random();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logcat.i(TAG, "onDestory");
    }

    @Override
    public IBinder onBind(Intent intent) {
//        String pkg = intent.getPackage();
//        if (pkg == null || !pkg.contains("MP")) {
//            return null;
//        }
        return binder;
    }

    private IBinder binder = new IToken.Stub() {

        @Override
        public String getToken() throws RemoteException {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                char c = (char) (random.nextInt(132 - 60) + 60);
                sb.append(c);
            }
            return sb.toString();
        }
    };

}
