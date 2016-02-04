# vimeo-deeplink-android
A helper library to deep link into the official Vimeo Android App.


# Contents
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
  - [Open the Playslists page](#open-the-playlists-page)
  - [Open the Camera roll to start the upload flow](#open-the-camera-roll-to-start-the-upload-flow)

# Reference

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

## Open app in the Google Play store
`boolean playStoreOpened = VimeoDeeplink.viewVimeoAppInAppStore(Context)`

## Check if the Vimeo app is installed
`boolean isInstalled = VimeoDeeplink.isVimeoAppInstalled(Context)`

## Open the Vimeo app
`boolean opened = VimeoDeeplink.openVimeoApp(Context)`

## View a video in the Vimeo app

You can check if this method is supported on the installed Vimeo version using the call:
`boolean supported = VimeoDeeplink.canHandleVideoDeeplink(Context)`

You must provide a video uri to this method. You can find a video's uri by making an API call to our [video endpoints](https://developer.vimeo.com/api/endpoints/videos#/{video_id}).

`boolean handled = VimeoDeeplink.showVideoWithUri(Context context, String videoUri)`

## View a user in the Vimeo app

You can check if this method is supported on the installed Vimeo version using the call:
`boolean supported = VimeoDeeplink.canHandleUserDeeplink(Context)`

You must provide a user uri to this method. You can find a user's uri by making an API call to our [user endpoints](https://developer.vimeo.com/api/endpoints/users#/{user_id}).

`boolean handled = VimeoDeeplink.showUserWithUri(Context context, String userUri)`

## View a category in the Vimeo app

You can check if this method is supported on the installed Vimeo version using the call:
`boolean supported = VimeoDeeplink.canHandleCategoryDeeplink(Context)`

You must provide a category uri to this method. You can find a categories uri by making an API call to our [category endpoints](https://developer.vimeo.com/api/endpoints/categories#/{category_id}).

`boolean handled = VimeoDeeplink.showCategoryWithUri(Context context, String categoryUri)`

## View a channel in the Vimeo app

You can check if this method is supported on the installed Vimeo version using the call:
`boolean supported = VimeoDeeplink.canHandleChannelDeeplink(Context)`

You must provide a channel uri to this method. You can find a channel's uri by making an API call to our [channel endpoints](https://developer.vimeo.com/api/endpoints/channels#/{channel_id}).

`boolean handled = VimeoDeeplink.showChannelWithUri(Context context, String channelUri)`

## Open the All Categories page
`boolean handled = VimeoDeeplink.showCategories(Context)`

## Open the Explore page
`boolean handled = VimeoDeeplink.showExplore(Context)`

## Open the Feed
`boolean handled = VimeoDeeplink.showFeed(Context)`

## Open the current user's profile
`boolean handled = VimeoDeeplink.showMyProfile(Context)`

## Open the Playlists page
`boolean handled = VimeoDeeplink.showPlaylists(Context)`

## Open the Camera roll to start the upload flow
`boolean handled = VimeoDeeplink.showUpload(Context)`

## Found an Issue?
Please file any and all issues found in this library to the git [issue tracker](https://github.com/vimeo/vimeo-deeplink-android/issues)

## Want to Contribute?
If you'd like to contribute, please follow our guidelines found in [CONTRIBUTING.md](CONTRIBUTING.md)

## Questions?

Tweet at us here: @vimeoapi

Post on [Stackoverflow](http://stackoverflow.com/questions/tagged/vimeo-android) with the tag `vimeo-android`

Get in touch [here](https://Vimeo.com/help/contact)

## License

`vimeo-deeplink-android` is available under the MIT license. See the LICENSE file for more info.
