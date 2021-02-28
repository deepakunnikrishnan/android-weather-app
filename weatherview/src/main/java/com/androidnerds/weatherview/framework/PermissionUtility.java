package com.androidnerds.weatherview.framework;

import android.Manifest;
import android.content.Context;

import androidx.fragment.app.Fragment;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class PermissionUtility {

    public static boolean hasLocationPermission(Context context) {
        return EasyPermissions.hasPermissions(context, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public static void requestPermission(Fragment fragment, int requestCode) {
        EasyPermissions.requestPermissions(fragment,
                "Please allow to app to access the device location",
                requestCode,
                Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public static boolean hasPermanentlyDeniedLocationPermission(Fragment fragment) {
        return EasyPermissions.permissionPermanentlyDenied(fragment,
                Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public static void showAppSettings(Fragment fragment) {
        new AppSettingsDialog.Builder(fragment).build().show();
    }

    public static void onPermissionResult(int requestCode, String[] permissions, int[] grantResults, Object receiver) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, receiver);
    }
}
