/*
 * Copyright (c) 2016 Vimeo (https://vimeo.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.vimeo.android.deeplink;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * This is a utility class to help facilitate deep linking into the Vimeo Android application
 * <p>
 * Created by zetterstromk on 8/28/15.
 */
public final class VimeoDeeplink {

    private static final int VERSION_CODE_DEBUG = 0;
    private static final int VERSION_CODE_DEEP_LINK_CATEGORY = 48;
    private static final int VERSION_CODE_DEEP_LINK_USER = 49;
    private static final int VERSION_CODE_DEEP_LINK_VIDEO = 48;
    private static final int VERSION_CODE_DEEP_LINK_CATEGORIES = 74;
    private static final int VERSION_CODE_DEEP_LINK_CHANNELS = 74;
    private static final int VERSION_CODE_DEEP_LINK_EXPLORE = 74;
    private static final int VERSION_CODE_DEEP_LINK_FEED = 74;
    private static final int VERSION_CODE_DEEP_LINK_ME = 74;
    private static final int VERSION_CODE_DEEP_LINK_NOTIFICATIONS = 1202;
    private static final int VERSION_CODE_DEEP_LINK_NOTIFICATION_SETTINGS = 1202;
    private static final int VERSION_CODE_DEEP_LINK_OFFLINE = 470;
    private static final int VERSION_CODE_DEEP_LINK_ONDEMAND = 470;
    private static final int VERSION_CODE_DEEP_LINK_PLAYLISTS = 74;
    private static final int VERSION_CODE_DEEP_LINK_PURCHASES = 470;
    private static final int VERSION_CODE_DEEP_LINK_UPGRADE = 2260;
    private static final int VERSION_CODE_DEEP_LINK_UPLOAD = 74;
    private static final int VERSION_CODE_DEEP_LINK_URL = 234;
    private static final int VERSION_CODE_DEEP_LINK_VIDEO_MANAGER = 2340;
    private static final int VERSION_CODE_DEEP_LINK_ALBUMS = 2340;
    private static final int VERSION_CODE_DEEP_LINK_WATCHLATER = 470;

    private static final String VIMEO_BASE_URL_HOST = "vimeo.com";
    private static final String VIMEO_BASE_URI = "vimeo://app.vimeo.com";
    private static final String VIMEO_APP_PACKAGE = "com.vimeo.android.videoapp";
    private static final String PLAY_STORE_URI = "market://details?id=" + VIMEO_APP_PACKAGE;
    private static final String PLAY_STORE_WEB_URL =
            "http://play.google.com/store/apps/details?id=" + VIMEO_APP_PACKAGE;

    private static final String CATEGORIES = "/categories";
    private static final String EXPLORE = "/explore";
    private static final String WATCH = "/watch";
    private static final String FEED = "/feed";
    private static final String ME = "/me";
    private static final String NOTIFICATIONS = "/notifications";
    private static final String NOTIFICATION_SETTINGS = "/settings/notifications";
    private static final String OFFLINE = "/offline";
    private static final String PLAYLISTS = "/playlists";
    private static final String PURCHASES = "/purchases";
    private static final String UPGRADE = "/upgrade";
    private static final String UPLOAD = "/upload";
    private static final String ACCOUNT = "/account";
    private static final String VIDEO_MANAGER = "/manage/videos";
    private static final String WATCH_LATER = "/watchlater";

    public static final String VIMEO_VIDEO_URI_PREFIX = "/videos/";
    public static final String VIMEO_USER_URI_PREFIX = "/users/";
    public static final String VIMEO_CATEGORY_URI_PREFIX = "/categories/";
    public static final String VIMEO_CHANNEL_URI_PREFIX = "/channels/";
    public static final String VIMEO_ONDEMAND_URI_PREFIX = "/ondemand/";
    public static final String VIMEO_ALBUMS_URI_POSTFIX = "/albums";
    public static final String VIMEO_ALBUM_URI_PREFIX = "/album";
    private static final String VIMEO_ALBUM_PATTERN = "^(" + VIMEO_ALBUM_URI_PREFIX + "/)[0-9]+$";


    /**
     * Determines if the Vimeo Android app is installed on the device
     *
     * @param context an Android {@link Context}
     * @return true if the app is installed, false otherwise
     */
    public static boolean isVimeoAppInstalled(@NonNull final Context context) {
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
     * @param context an Android {@link Context}
     * @return true if the Google Play Store app is launched to the Vimeo download page, or if the fallback
     * Google Play Store website is launched to it; false if neither the Google Play app or website
     * are launched
     */
    public static boolean viewVimeoAppInAppStore(@NonNull final Context context) {
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
     * @param context an Android {@link Context}
     * @return true if the app is installed, false otherwise
     */
    public static boolean openVimeoApp(@NonNull final Context context) {
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
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a video deep link
     */
    public static boolean canHandleVideoDeeplink(@NonNull final Context context) {
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_VIDEO ||
               vimeoAppVersion(context) == VERSION_CODE_DEBUG;
    }

    /**
     * Open the Vimeo app to the video player for the specified video uri
     *
     * @param context      an Android {@link Context}
     * @param videoUriPath this path should be in the format "/videos/{videoParam}" and should be taken from
     *                     the uri field of a video JSON object from the API
     * @return true if the param videoUriPath is correct and the Vimeo app can handle the video deep link;
     * false otherwise
     */
    public static boolean showVideoWithUri(@NonNull final Context context,
                                           @NonNull final String videoUriPath) {
        if (videoUriPath.startsWith(VIMEO_VIDEO_URI_PREFIX) && canHandleVideoDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + videoUriPath));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a category deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a category deep link
     */
    public static boolean canHandleCategoryDeeplink(@NonNull final Context context) {
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_CATEGORY ||
               vimeoAppVersion(context) == VERSION_CODE_DEBUG;
    }

    /**
     * Open the Vimeo app to the category screen for the specified category uri
     *
     * @param context         an Android {@link Context}
     * @param categoryUriPath this path should be in the format "/categories/{categoryParam}" and should be
     *                        taken from the uri field of a category JSON object from the API
     * @return true if the param categoryUriPath is correct and the Vimeo app can handle the category deep link;
     * false otherwise
     */
    public static boolean showCategoryWithUri(@NonNull final Context context,
                                              @NonNull final String categoryUriPath) {
        if (categoryUriPath.startsWith(VIMEO_CATEGORY_URI_PREFIX) && canHandleCategoryDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + categoryUriPath));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a channel deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a channel deep link
     */
    public static boolean canHandleChannelDeeplink(@NonNull final Context context) {
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_CHANNELS ||
               vimeoAppVersion(context) == VERSION_CODE_DEBUG;
    }

    /**
     * Open the Vimeo app to the channel screen for the specified channel uri
     *
     * @param context        an Android {@link Context}
     * @param channelUriPath this path should be in the format "/channels/{channelParam}" and should be
     *                       taken from the uri field of a channel JSON object from the API
     * @return true if the param channelUriPath is correct and the Vimeo app can handle the channel deep link;
     * false otherwise
     */
    public static boolean showChannelWithUri(@NonNull final Context context,
                                             @NonNull final String channelUriPath) {
        if (channelUriPath.startsWith(VIMEO_CHANNEL_URI_PREFIX) && canHandleChannelDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + channelUriPath));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a user deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a user deep link
     */
    public static boolean canHandleUserDeeplink(@NonNull final Context context) {
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_USER ||
               vimeoAppVersion(context) == VERSION_CODE_DEBUG;
    }

    /**
     * Open the Vimeo app to the user profile screen for the specified user uri
     *
     * @param context     an Android {@link Context}
     * @param userUriPath this path should be in the format "/users/{userId}" and should be
     *                    taken from the uri field of a user JSON object from the API
     * @return true if the param userUriPath is correct and the Vimeo app can handle the user deep link;
     * false otherwise
     */
    public static boolean showUserWithUri(@NonNull final Context context, @NonNull final String userUriPath) {
        if (userUriPath.startsWith(VIMEO_USER_URI_PREFIX) && canHandleUserDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + userUriPath));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle an ondemand deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle an ondemand deep link
     */
    public static boolean canHandleOnDemandDeeplink(@NonNull final Context context) {
        return vimeoAppVersion(context) >= VERSION_CODE_DEEP_LINK_ONDEMAND ||
               vimeoAppVersion(context) == VERSION_CODE_DEBUG;
    }

    /**
     * Open the Vimeo app for the specified ondemand uri, this may link to the video player or an ondemand
     * container, in the case for series
     *
     * @param context         an Android {@link Context}
     * @param ondemandUriPath this path should be in the format "/ondemand/{param}"
     * @return true if the param videoUriPath is correct and the Vimeo app can handle the ondemand deep link;
     * false otherwise
     */
    public static boolean showOnDemandTitleWithUri(@NonNull final Context context,
                                                   @NonNull final String ondemandUriPath) {
        if (ondemandUriPath.startsWith(VIMEO_ONDEMAND_URI_PREFIX) && canHandleOnDemandDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + ondemandUriPath));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Open the Vimeo App to the All Categories screen
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app opens the Categories deeplink
     */
    public static boolean showCategories(@NonNull final Context context) {
        if (canHandleCategoiesDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + CATEGORIES));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a categories deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a categories deep link
     */
    public static boolean canHandleCategoiesDeeplink(@NonNull final Context context) {
        return true;
    }

    /**
     * Open the Vimeo App to the Explore screen
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app opens the Explore deeplink
     */
    public static boolean showExplore(@NonNull final Context context) {
        if (canHandleExploreDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + EXPLORE));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Open the Vimeo App to the Account screen
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app opens the Account deeplink
     */
    public static boolean showAccount(@NonNull final Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + ACCOUNT));
        return startActivity(context, intent);
    }

    /**
     * Open the Vimeo App to the Watch screen
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app opens the Watch deeplink
     */
    public static boolean showWatch(@NonNull final Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + WATCH));
        return startActivity(context, intent);
    }

    /**
     * Determine if the user's Vimeo app can handle a explore deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a explore deep link
     */
    public static boolean canHandleExploreDeeplink(@NonNull final Context context) {
        return true;
    }

    /**
     * Open the Vimeo App to the Feed screen
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app opens the Feed deeplink
     */
    public static boolean showFeed(@NonNull final Context context) {
        if (canHandleFeedDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + FEED));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a feed deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a feed deep link
     */
    public static boolean canHandleFeedDeeplink(@NonNull final Context context) {
        return true;
    }

    /**
     * Open the Vimeo App to the Me screen
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app opens the Me/My Profile deeplink
     */
    public static boolean showMyProfile(@NonNull final Context context) {
        if (canHandleMeDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + ME));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a Me deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a Me deep link
     */
    public static boolean canHandleMeDeeplink(@NonNull final Context context) {
        return true;
    }

    /**
     * Open the Vimeo App to the Notifications screen
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app opens the Notifications deeplink
     */
    public static boolean showNotifications(@NonNull final Context context) {
        if (canHandleNotificationsDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + NOTIFICATIONS));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a Notifications deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a Notifications deep link
     */
    public static boolean canHandleNotificationsDeeplink(@NonNull final Context context) {
        return true;
    }

    /**
     * Open the Vimeo App to the Push Notifications Settings screen
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app opens the Push Notifications Settings deeplink
     */
    public static boolean showPushNotificationSettings(@NonNull final Context context) {
        if (canHandlePushNotificationSettingsDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + NOTIFICATION_SETTINGS));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a Push-Notification-Settings deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a Push-Notification-Settings deep link
     */
    public static boolean canHandlePushNotificationSettingsDeeplink(@NonNull final Context context) {
        return true;
    }

    /**
     * Open the Vimeo App to the Offline screen - this is where a users' videos saved for offline will show up.
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app opens the Offline deeplink
     */
    public static boolean showOffline(@NonNull final Context context) {
        if (canHandleOfflineDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + OFFLINE));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a offline deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a offline deep link
     */
    public static boolean canHandleOfflineDeeplink(@NonNull final Context context) {
        return true;
    }

    /**
     * Open the Vimeo App to the Playlists screen
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app opens the Playlists deeplink
     */
    public static boolean showPlaylists(@NonNull final Context context) {
        if (canHandlePlaylistDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + PLAYLISTS));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a playlist deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a playlist deep link
     */
    public static boolean canHandlePlaylistDeeplink(@NonNull final Context context) {
        return true;
    }

    /**
     * Open the Vimeo App to the Purchases screen
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app opens the Purchases deeplink
     */
    public static boolean showPurchases(@NonNull final Context context) {
        if (canHandlePlaylistDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + PURCHASES));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a purchase deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a purchase deep link
     */
    public static boolean canHandlePurchaseDeeplink(@NonNull final Context context) {
        return true;
    }

    /**
     * Open the Vimeo App to the Upgrade screen
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app opens the Upgrade deeplink
     */
    public static boolean showUpgrade(@NonNull final Context context) {
        if (canHandleUpgradeDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + UPGRADE));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a upgrade deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a upgrade deep link
     */
    public static boolean canHandleUpgradeDeeplink(@NonNull final Context context) {
        return true;
    }

    /**
     * Open the Vimeo App to the Upload screen
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app opens the Upload deeplink
     */
    public static boolean showUpload(@NonNull final Context context) {
        if (canHandleUploadDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + UPLOAD));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a upload deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a upload deep link
     */
    public static boolean canHandleUploadDeeplink(@NonNull final Context context) {
        return true;
    }

    /**
     * Open the Vimeo App to the Video Manager screen
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app opens the Video Manager deeplink
     */
    public static boolean showVideoManager(@NonNull final Context context) {
        if (canHandleVideoManagerDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + VIDEO_MANAGER));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a video manager deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a video manager deep link
     */
    public static boolean canHandleVideoManagerDeeplink(@NonNull final Context context) {
        return true;
    }

    /**
     * Determine if the user's Vimeo app can handle a album deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle an albums deep link
     */
    public static boolean canHandleAlbumsDeeplink(@NonNull final Context context, @NonNull String uri) {
        return true;
    }

    public static boolean isValidAlbumUri(@NonNull final String uri) {
        return uri.endsWith(VIMEO_ALBUMS_URI_POSTFIX) || uri.matches(VIMEO_ALBUM_PATTERN);
    }

    public static boolean showAlbums(@NonNull final Context context, String uri) {
        if (canHandleAlbumsDeeplink(context, uri)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + uri));
            return startActivity(context, intent);
        }
        return false;
    }


    /**
     * Open the Vimeo App to the Watch Later screen
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app opens the Watch Later deeplink
     */
    public static boolean showWatchLater(@NonNull final Context context) {
        if (canHandleOfflineDeeplink(context)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIMEO_BASE_URI + WATCH_LATER));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle a watchlater deep link
     *
     * @param context an Android {@link Context}
     * @return true if the Vimeo app is installed and it can handle a watchlater deep link
     */
    public static boolean canHandleWatchLaterDeeplink(@NonNull final Context context) {
        return true;
    }

    /**
     * Open the Vimeo App for the given url
     *
     * @param context an Android {@link Context}
     * @param url     a url
     * @return true if the Vimeo app opens the url
     */
    public static boolean openUrl(@NonNull final Context context, @NonNull final String url) {
        if (canHandleUrl(context, url)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            return startActivity(context, intent);
        }
        return false;
    }

    /**
     * Determine if the user's Vimeo app can handle the given url
     *
     * @param context an Android {@link Context}
     * @param url     a url
     * @return true if the Vimeo app is installed and the url contains the url host scheme
     */
    public static boolean canHandleUrl(@NonNull final Context context, @NonNull final String url) {
        return true;
    }

    private static int vimeoAppVersion(@NonNull final Context context) {
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

    private static boolean startActivity(@NonNull final Context context, @Nullable final Intent intent) {
        if (intent != null && intent.resolveActivity(context.getPackageManager()) != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    private VimeoDeeplink() {
    }
}
