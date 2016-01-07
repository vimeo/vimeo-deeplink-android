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

    private static final int VERSION_CODE_DEBUG = 0;
    private static final int VERSION_CODE_DEEP_LINK_CATEGORY = 48;
    private static final int VERSION_CODE_DEEP_LINK_USER = 49;
    private static final int VERSION_CODE_DEEP_LINK_VIDEO = 48;
    private static final int VERSION_CODE_DEEP_LINK_CATEGORIES = 74;
    private static final int VERSION_CODE_DEEP_LINK_CHANNELS = 74;
    private static final int VERSION_CODE_DEEP_LINK_EXPLORE = 74;
    private static final int VERSION_CODE_DEEP_LINK_FEED = 74;
    private static final int VERSION_CODE_DEEP_LINK_ME = 74;
    private static final int VERSION_CODE_DEEP_LINK_PLAYLISTS = 74;
    private static final int VERSION_CODE_DEEP_LINK_UPLOAD = 74;
    private static final int VERSION_CODE_DEEP_LINK_URL = 234;

    private static final String VIMEO_BASE_URL_HOST = "vimeo.com";
    private static final String VIMEO_BASE_URI = "vimeo://app.vimeo.com";
    private static final String VIMEO_APP_PACKAGE = "com.vimeo.android.videoapp";
    private static final String PLAY_STORE_URI = "market://details?id=" + VIMEO_APP_PACKAGE;
    private static final String PLAY_STORE_WEB_URL =
            "http://play.google.com/store/apps/details?id=" + VIMEO_APP_PACKAGE;

    private static final String CATEGORIES = "/categories";
    private static final String EXPLORE = "/explore";
    private static final String FEED = "/feed";
    private static final String ME = "/me";
    private static final String PLAYLISTS = "/playlists";
    private static final String UPLOAD = "/upload";

    public static final String VIMEO_VIDEO_URI_PREFIX = "/videos/";
    public static final String VIMEO_USER_URI_PREFIX = "/users/";
    public static final String VIMEO_CATEGORY_URI_PREFIX = "/categories/";
    public static final String VIMEO_CHANNEL_URI_PREFIX = "/channels/";


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
            e.printStackTrace();
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
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_VIDEO ||
               vimeoAppVersion(context) == VERSION_CODE_DEBUG;
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
        if (videoUriPath.startsWith(VIMEO_VIDEO_URI_PREFIX) && canHandleVideoDeeplink(context)) {
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
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_CATEGORY ||
               vimeoAppVersion(context) == VERSION_CODE_DEBUG;
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
        if (categoryUriPath.startsWith(VIMEO_CATEGORY_URI_PREFIX) && canHandleCategoryDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + categoryUriPath));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a channel deep link
     *
     * @param context
     * @return true if the Vimeo app is installed and it can handle a channel deep link
     */
    public static boolean canHandleChannelDeeplink(final Context context) {
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_CHANNELS ||
               vimeoAppVersion(context) == VERSION_CODE_DEBUG;
    }

    /**
     * Open the Vimeo app to the channel screen for the specified channel uri
     *
     * @param context
     * @param channelUriPath this path should be in the format "/channels/{channelParam}" and should be
     *                       taken from the uri field of a channel JSON object from the API
     * @return true if the param channelUriPath is correct and the Vimeo app can handle the channel deep link;
     * false otherwise
     */
    public static boolean showChannelWithUri(final Context context, final String channelUriPath) {
        if (channelUriPath.startsWith(VIMEO_CHANNEL_URI_PREFIX) && canHandleChannelDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + channelUriPath));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a user deep link
     *
     * @param context
     * @return true if the Vimeo app is installed and it can handle a user deep link
     */
    public static boolean canHandleUserDeeplink(final Context context) {
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_USER ||
               vimeoAppVersion(context) == VERSION_CODE_DEBUG;
    }

    /**
     * Open the Vimeo app to the user profile screen for the specified user uri
     *
     * @param context
     * @param userUriPath this path should be in the format "/users/{userId}" and should be
     *                    taken from the uri field of a user JSON object from the API
     * @return true if the param userUriPath is correct and the Vimeo app can handle the user deep link;
     * false otherwise
     */
    public static boolean showUserWithUri(final Context context, final String userUriPath) {
        if (userUriPath.startsWith(VIMEO_USER_URI_PREFIX) && canHandleUserDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + userUriPath));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Open the Vimeo App to the All Categories screen
     *
     * @param context
     * @return true if the Vimeo app opens the Categories deeplink
     */
    public static boolean showCategories(final Context context) {
        if (canHandleCategoiesDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + CATEGORIES));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a categories deep link
     *
     * @param context
     * @return true if the Vimeo app is installed and it can handle a categories deep link
     */
    public static boolean canHandleCategoiesDeeplink(final Context context) {
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_CATEGORIES ||
               vimeoAppVersion(context) == VERSION_CODE_DEBUG;
    }

    /**
     * Open the Vimeo App to the Explore screen
     *
     * @param context
     * @return true if the Vimeo app opens the Explore deeplink
     */
    public static boolean showExplore(final Context context) {
        if (canHandleExploreDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + EXPLORE));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a explore deep link
     *
     * @param context
     * @return true if the Vimeo app is installed and it can handle a explore deep link
     */
    public static boolean canHandleExploreDeeplink(final Context context) {
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_EXPLORE ||
               vimeoAppVersion(context) == VERSION_CODE_DEBUG;
    }

    /**
     * Open the Vimeo App to the Feed screen
     *
     * @param context
     * @return true if the Vimeo app opens the Feed deeplink
     */
    public static boolean showFeed(final Context context) {
        if (canHandleFeedDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + FEED));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a feed deep link
     *
     * @param context
     * @return true if the Vimeo app is installed and it can handle a feed deep link
     */
    public static boolean canHandleFeedDeeplink(final Context context) {
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_FEED ||
               vimeoAppVersion(context) == VERSION_CODE_DEBUG;
    }

    /**
     * Open the Vimeo App to the Me screen
     *
     * @param context
     * @return true if the Vimeo app opens the Me/My Profile deeplink
     */
    public static boolean showMyProfile(final Context context) {
        if (canHandleMeDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + ME));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a Me deep link
     *
     * @param context
     * @return true if the Vimeo app is installed and it can handle a Me deep link
     */
    public static boolean canHandleMeDeeplink(final Context context) {
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_ME ||
               vimeoAppVersion(context) == VERSION_CODE_DEBUG;
    }

    /**
     * Open the Vimeo App to the Playlists screen
     *
     * @param context
     * @return true if the Vimeo app opens the Playlists deeplink
     */
    public static boolean showPlaylists(final Context context) {
        if (canHandlePlaylistDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + PLAYLISTS));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a playlist deep link
     *
     * @param context
     * @return true if the Vimeo app is installed and it can handle a playlist deep link
     */
    public static boolean canHandlePlaylistDeeplink(final Context context) {
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_PLAYLISTS ||
               vimeoAppVersion(context) == VERSION_CODE_DEBUG;
    }

    /**
     * Open the Vimeo App to the Upload screen
     *
     * @param context
     * @return true if the Vimeo app opens the Upload deeplink
     */
    public static boolean showUpload(final Context context) {
        if (canHandleUploadDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + UPLOAD));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a upload deep link
     *
     * @param context
     * @return true if the Vimeo app is installed and it can handle a upload deep link
     */
    public static boolean canHandleUploadDeeplink(final Context context) {
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_UPLOAD ||
               vimeoAppVersion(context) == VERSION_CODE_DEBUG;
    }

    /**
     * Open the Vimeo App for the given url
     *
     * @param context
     * @param url
     * @return true if the Vimeo app opens the url
     */
    public static boolean openUrl(final Context context, final String url) {
        if (canHandleUrl(context, url)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle the given url
     *
     * @param context
     * @param url
     * @return true if the Vimeo app is installed and the url contains the url host scheme
     */
    public static boolean canHandleUrl(final Context context, final String url) {
        return (vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_URL ||
                vimeoAppVersion(context) == VERSION_CODE_DEBUG) &&
               url.toLowerCase().contains(VIMEO_BASE_URL_HOST);
    }

    private static int vimeoAppVersion(final Context context) {
        if (isVimeoAppInstalled(context)) {
            PackageManager packageManager = context.getPackageManager();
            try {
                PackageInfo packageInfo =
                        packageManager.getPackageInfo(VIMEO_APP_PACKAGE, PackageManager.GET_ACTIVITIES);
                return packageInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                return 0;
            }
        }
        return 0;
    }

    private static boolean startActivity(final Context context, final Intent intent) {
        if (intent != null && intent.resolveActivity(context.getPackageManager()) != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            return true;
        }
        return false;
    }
}
