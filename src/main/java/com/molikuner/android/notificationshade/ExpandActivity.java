package com.molikuner.android.notificationshade;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExpandActivity extends Activity {

    /** @noinspection JavaReflectionMemberAccess: IDEA doesn't know about the methods... */
    @Override
    @SuppressLint("WrongConstant") // the statusbar system service is an unofficial API
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Object sbservice = getSystemService("statusbar");
        try {
            Class<?> statusbarManager = Class.forName("android.app.StatusBarManager");
            Method showsb;
            if (Build.VERSION.SDK_INT >= 17) { // name changed > jelly bean
                showsb = statusbarManager.getMethod("expandNotificationsPanel");
            } else {
                showsb = statusbarManager.getMethod("expand");
            }
            boolean isAccessible = showsb.isAccessible();
            try {
                showsb.setAccessible(true);
            } catch (SecurityException ignored) {
                // yeah, whatever, we'll try to use it anyway
            }
            showsb.invoke(sbservice);
            try {
                showsb.setAccessible(isAccessible);
            } catch (SecurityException e) {
                // yeah, whatever it probably failed before anyway
            }
        } catch (NullPointerException e) {
            Toast.makeText(this, R.string.api_not_found, Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(this, R.string.api_not_found, Toast.LENGTH_SHORT).show();
        } catch (NoSuchMethodException e) {
            Toast.makeText(this, R.string.api_not_found, Toast.LENGTH_SHORT).show();
        } catch (InvocationTargetException e) {
            Toast.makeText(this, R.string.api_failed, Toast.LENGTH_SHORT).show();
        } catch (IllegalAccessException e) {
            Toast.makeText(this, R.string.api_failed, Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}