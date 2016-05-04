# vimeo-deeplink-android
A helper library to deep link into the official Vimeo Android App.


## Contents
 - [Getting Started](#getting-started)
  - [Gradle](#gradle)
  - [Submodule](#submodule)
 - [Reference](#reference)
  - [View app in the Google Play store](#open-app-in-the-google-play-store)
  - [Check if the Vimeo app is installed](#check-if-the-vimeo-app-is-installed)
  - [Open the Vimeo app](#open-the-vimeo-app)
  - [View a video in the Vimeo app](#view-a-video-in-the-vimeo-app)
  - [View a user profile in the Vimeo app](#view-a-user-in-the-vimeo-app)
  - [View a category in the Vimeo app](#view-a-category-in-the-vimeo-app)
  - [View a channel in the Vimeo app](#view-a-channel-in-the-vimeo-app)
  - [Open the All Categories page](#open-the-all-categories-page)
  - [Open the Explore page](#open-the-explore-page)
  - [Open the Feed](#open-the-feed)
  - [Open the current user's profile](#open-the-current-users-profile)
  - [Open the Playlists page](#open-the-playlists-page)
  - [Open the Offline playlist page](#open-the-offline-playlist-page)
  - [Open the Watch Later page](#open-the-watch-later-page)
  - [Open the Purchases page](#open-the-purchases-page)
  - [Open the Camera roll to start the upload flow](#open-the-camera-roll-to-start-the-upload-flow)

## Getting Started
For a more in depth look at the usage, refer to the [example Android app](example). The example project includes implementation of all of the below features.

### Gradle
Specify the dependency in your `build.gradle` file (make sure `jcenter()` is included as a repository)
```groovy
compile 'com.vimeo.android.deeplink:vimeo-deeplink:1.0.0'
```

### Submodule
We recommend using JCenter, but if you'd like to use the library as a submodule:
```
git submodule add git@github.com:vimeo/vimeo-deeplink-android.git
```
Then in your `build.gradle` use:
```groovy
compile project(':vimeo-deeplink-android:vimeo-deeplink')
```

## Reference

The Vimeo deeplink base URL is: `vimeo://app.vimeo.com`

Currently supported paths are:
* `vimeo://app.vimeo.com/videos/12345` where `/videos/12345` is a video URI
* `vimeo://app.vimeo.com/users/12345` where `/users/12345` is a user URI.
* `vimeo://app.vimeo.com/categories/12345` where `/categories/12345` is a category URI.
* `vimeo://app.vimeo.com/channels/12345` where `/channels/12345` is a channel URI.
* `vimeo://app.vimeo.com/categories`
* `vimeo://app.vimeo.com/explore`
* `vimeo://app.vimeo.com/feed`
* `vimeo://app.vimeo.com/me`
* `vimeo://app.vimeo.com/playlists`
* `vimeo://app.vimeo.com/upload`

### Open app in the Google Play store
`boolean playStoreOpened = VimeoDeeplink.viewVimeoAppInAppStore(Context context)`

### Check if the Vimeo app is installed
`boolean isInstalled = VimeoDeeplink.isVimeoAppInstalled(Context context)`

### Open the Vimeo app
`boolean opened = VimeoDeeplink.openVimeoApp(Context context)`

### View a video in the Vimeo app

You can check if this method is supported on the installed Vimeo version using the call:

`boolean supported = VimeoDeeplink.canHandleVideoDeeplink(Context context)`

You must provide a video uri to this method. You can find a video's uri by making an API call to our [video endpoints](https://developer.vimeo.com/api/endpoints/videos#/{video_id}).
`boolean handled = VimeoDeeplink.showVideoWithUri(Context context, String videoUri)`

### View a user in the Vimeo app

You can check if this method is supported on the installed Vimeo version using the call:

`boolean supported = VimeoDeeplink.canHandleUserDeeplink(Context context)`

You must provide a user uri to this method. You can find a user's uri by making an API call to our [user endpoints](https://developer.vimeo.com/api/endpoints/users#/{user_id}).

`boolean handled = VimeoDeeplink.showUserWithUri(Context context, String userUri)`

### View a category in the Vimeo app

You can check if this method is supported on the installed Vimeo version using the call:

`boolean supported = VimeoDeeplink.canHandleCategoryDeeplink(Context context)`

You must provide a category uri to this method. You can find a categories uri by making an API call to our [category endpoints](https://developer.vimeo.com/api/endpoints/categories#/{category_id}).

`boolean handled = VimeoDeeplink.showCategoryWithUri(Context context, String categoryUri)`

### View a channel in the Vimeo app

You can check if this method is supported on the installed Vimeo version using the call:

`boolean supported = VimeoDeeplink.canHandleChannelDeeplink(Context context)`

You must provide a channel uri to this method. You can find a channel's uri by making an API call to our [channel endpoints](https://developer.vimeo.com/api/endpoints/channels#/{channel_id}).

`boolean handled = VimeoDeeplink.showChannelWithUri(Context context, String channelUri)`

### Open the All Categories page
`boolean handled = VimeoDeeplink.showCategories(Context context)`

### Open the Explore page
`boolean handled = VimeoDeeplink.showExplore(Context context)`

### Open the Feed
`boolean handled = VimeoDeeplink.showFeed(Context context)`

### Open the current user's profile
`boolean handled = VimeoDeeplink.showMyProfile(Context context)`

### Open the Playlists page
`boolean handled = VimeoDeeplink.showPlaylists(Context context)`

### Open the Offline playlist page
`boolean handled = VimeoDeeplink.showOffline(Context context)`

### Open the Watch Later page
`boolean handled = VimeoDeeplink.showWatchLater(Context context)`

### Open the Purchases page
`boolean handled = VimeoDeeplink.showPurchases(Context context)`

### Open the Camera roll to start the upload flow
`boolean handled = VimeoDeeplink.showUpload(Context context)`

## Found an Issue?

Please file it in the git [issue tracker](https://github.com/vimeo/vimeo-deeplink-android/issues).

## Want to Contribute?

If you'd like to contribute, please follow our guidelines found in [CONTRIBUTING.md](CONTRIBUTING.md).

## License

`vimeo-deeplink-android` is available under the MIT license. See the [LICENSE](LICENSE) file for more info.

## Questions?

Tweet at us here: [@vimeoapi](https://twitter.com/vimeoapi).

Post on [Stackoverflow](http://stackoverflow.com/questions/tagged/vimeo-android) with the tag `vimeo-android`.

Get in touch [here](https://vimeo.com/help/contact).

Interested in working at Vimeo? We're [hiring](https://vimeo.com/jobs)!
