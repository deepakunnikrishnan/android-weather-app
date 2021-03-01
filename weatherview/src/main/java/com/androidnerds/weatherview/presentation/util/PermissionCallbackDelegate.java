package com.androidnerds.weatherview.presentation.util;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.androidnerds.weatherview.framework.PermissionUtility;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class PermissionCallbackDelegate implements EasyPermissions.PermissionCallbacks {

    public interface PermissionCallback {
        void onPermissionGranted();
    }

    public static final int REQUEST_LOCATION_PERMISSION = 100;
    private final Fragment fragment;
    private PermissionCallback callback;

    public PermissionCallbackDelegate(Fragment fragment, PermissionCallback callback) {
        this.fragment = fragment;
        this.callback = callback;
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if(null != callback) {
            callback.onPermissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }


    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(PermissionUtility.hasPermanentlyDeniedLocationPermission(fragment)) {
            PermissionUtility.showAppSettings(fragment);
        }else {
            requestLocationPermission();
        }
    }

    public void requestLocationPermission() {
        if(PermissionUtility.hasLocationPermission(fragment.getContext())) {
            return;
        }
        PermissionUtility.requestPermission(fragment, REQUEST_LOCATION_PERMISSION);
    }
}
