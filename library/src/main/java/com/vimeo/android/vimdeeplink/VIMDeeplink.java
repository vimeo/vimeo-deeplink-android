package com.vimeo.android.vimdeeplink;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * This is a utility class to help facilitate deep linking into the Vimeo Android application
 * <p/>
 * Created by zetterstromk on 8/28/15.
 */
public class VIMDeeplink {

    private static final int VERSION_CODE_DEEP_LINK_CATEGORY = 48;
    private static final int VERSION_CODE_DEEP_LINK_VIDEO = 48;

    private static final String VIMEO_BASE_URI = "vimeo://app.vimeo.com";
    private static final String VIMEO_APP_PACKAGE = "com.vimeo.android.videoapp";
    private static final String PLAY_STORE_URI = "market://details?id=" + VIMEO_APP_PACKAGE;
    private static final String PLAY_STORE_WEB_URL =
            "http://play.google.com/store/apps/details?id=" + VIMEO_APP_PACKAGE;

    public static final String VIMEO_VIDEO_URI_PREFIX = "/videos/";
    public static final String VIMEO_CATEGORY_URI_PREFIX = "/categories/";


    /**
     * Determines if the Vimeo Android app is installed on the device
     *
     * @param context
     * @return true if the app is installed, false otherwise
     */
    public static boolean isVimeoAppInstalled(final Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(VIMEO_APP_PACKAGE, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * View the Vimeo app in the Google Play Store
     * First, it tries the offical app. The fallback is the website
     *
     * @param context
     * @return true if the Google Play Store app is launched to the Vimeo download page, or if the fallback
     * Google Play Store website is launched to it; false if neither the Google Play app or website
     * are launched
     */
    public static boolean viewVimeoAppInAppStore(final Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_URI));
        if (!startActivity(context, intent)) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_WEB_URL));
            return startActivity(context, intent);
        }
        return true;
    }

    /**
     * Opens the Vimeo app, if it is installed, to the default launch activity
     * Disclosure: if the user has never opened the Vimeo app before, they will go to the Launch Screen
     *
     * @param context
     * @return true if the app is installed, false otherwise
     */
    public static boolean openVimeoApp(final Context context) {
        if (isVimeoAppInstalled(context)) {
            Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(VIMEO_APP_PACKAGE);
            context.startActivity(LaunchIntent);
            return true;
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a video deep link
     *
     * @param context
     * @return true if the Vimeo app is installed and it can handle a video deep link
     */
    public static boolean canHandleVideoDeeplink(final Context context) {
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_VIDEO;
    }

    /**
     * Open the Vimeo app to the video player for the specified video uri
     *
     * @param context
     * @param videoUriPath this path should be in the format "/videos/{videoParam}" and should be taken from
     *                     the uri field of a video JSON object from the API
     * @return true if the param videoUriPath is correct and the Vimeo app can handle the video deep link;
     * false otherwise
     */
    public static boolean showVideoWithUri(final Context context, final String videoUriPath) {
        if (videoUriPath.startsWith(VIMEO_VIDEO_URI_PREFIX) &&
            (vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_VIDEO)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + videoUriPath));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a category deep link
     *
     * @param context
     * @return true if the Vimeo app is installed and it can handle a category deep link
     */
    public static boolean canHandleCategoryDeeplink(final Context context) {
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_CATEGORY;
    }

    /**
     * Open the Vimeo app to the category screen for the specified category uri
     *
     * @param context
     * @param categoryUriPath this path should be in the format "/categories/{categoryParam}" and should be
     *                        taken from the uri field of a category JSON object from the API
     * @return true if the param categoryUriPath is correct and the Vimeo app can handle the category deep link;
     * false otherwise
     */
    public static boolean showCategoryWithUri(final Context context, final String categoryUriPath) {
        if (categoryUriPath.startsWith(VIMEO_CATEGORY_URI_PREFIX) &&
            (vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_CATEGORY)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + categoryUriPath));
            return startActivity(context, intent);
        }
        return false;
    }

    private static int vimeoAppVersion(final Context context) {
        if (isVimeoAppInstalled(context)) {
            PackageManager packageManager = context.getPackageManager();
            try {
                PackageInfo packageInfo = packageManager
                        .getPackageInfo(VIMEO_APP_PACKAGE, PackageManager.GET_ACTIVITIES);
                return packageInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                return 0;
            }
        }
        return 0;
    }

    private static boolean startActivity(final Context context, final Intent intent) {
        if (intent != null && intent.resolveActivity(context.getPackageManager()) != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        }
        return false;
    }
}
