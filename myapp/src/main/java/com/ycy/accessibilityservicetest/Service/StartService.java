package com.ycy.accessibilityservicetest.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class StartService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
