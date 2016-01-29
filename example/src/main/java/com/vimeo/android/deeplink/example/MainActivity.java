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

package com.vimeo.android.deeplink.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vimeo.android.vimdeeplink.VimeoDeeplink;

public class MainActivity extends AppCompatActivity {

    private static final String DEFAULT_CATEGORY_URI_PATH = "art";
    private static final String DEFAULT_CHANNEL_URI_PATH = "staffpicks";
    private static final String DEFAULT_USER_URI_PATH = "staff";
    private static final String DEFAULT_VIDEO_URI_PATH = "149058362";
    private static final String DEFAULT_VIDEO_URL = "http://www.vimeo.com/148943792";

    public enum DeepLinkType {
        NONE,
        CATEGORY,
        CHANNEL,
        URL,
        USER,
        VIDEO
    }

    private Button mGoButton;
    private EditText mUriEditText;
    private DeepLinkType mDeepLinkType = DeepLinkType.NONE;

    private RadioGroup.OnCheckedChangeListener mCheckedChangeListener =
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    mGoButton.setEnabled(true);
                    switch (checkedId) {
                        case R.id.activity_main_category_radiobutton:
                            mDeepLinkType = DeepLinkType.CATEGORY;
                            break;
                        case R.id.activity_main_channel_radiobutton:
                            mDeepLinkType = DeepLinkType.CHANNEL;
                            break;
                        case R.id.activity_main_url_radiobutton:
                            mDeepLinkType = DeepLinkType.URL;
                            break;
                        case R.id.activity_main_user_radiobutton:
                            mDeepLinkType = DeepLinkType.USER;
                            break;
                        case R.id.activity_main_video_radiobutton:
                            mDeepLinkType = DeepLinkType.VIDEO;
                            break;
                        default:
                            mDeepLinkType = DeepLinkType.NONE;
                            break;
                    }
                }
            };

    private View.OnClickListener mGoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mDeepLinkType == DeepLinkType.NONE) {
                Toast.makeText(getApplicationContext(), R.string.activity_main_select_error,
                               Toast.LENGTH_SHORT).show();
                return;
            }
            String uriPath = mUriEditText.getText().toString().trim();
            if (uriPath.isEmpty()) {
                uriPath = generatedUriPath();
            }

            performDeepLink(uriPath);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGoButton = (Button) findViewById(R.id.activity_main_go_button);
        mUriEditText = (EditText) findViewById(R.id.activity_main_edittext);

        RadioButton videoRadioButton = (RadioButton) findViewById(R.id.activity_main_video_radiobutton);
        videoRadioButton.setEnabled(VimeoDeeplink.canHandleVideoDeeplink(this));
        RadioButton userRadioButton = (RadioButton) findViewById(R.id.activity_main_user_radiobutton);
        userRadioButton.setEnabled(VimeoDeeplink.canHandleUserDeeplink(this));
        RadioButton categoryRadioButton = (RadioButton) findViewById(R.id.activity_main_category_radiobutton);
        categoryRadioButton.setEnabled(VimeoDeeplink.canHandleCategoryDeeplink(this));
        RadioButton channelRadioButton = (RadioButton) findViewById(R.id.activity_main_channel_radiobutton);
        channelRadioButton.setEnabled(VimeoDeeplink.canHandleChannelDeeplink(this));
        RadioButton urlRadioButton = (RadioButton) findViewById(R.id.activity_main_url_radiobutton);
        urlRadioButton.setEnabled(VimeoDeeplink.canHandleUrl(this, DEFAULT_VIDEO_URL));

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.activity_main_radiogroup);
        radioGroup.setOnCheckedChangeListener(mCheckedChangeListener);

        mGoButton.setEnabled(VimeoDeeplink.isVimeoAppInstalled(this));
        mGoButton.setOnClickListener(mGoClickListener);

        Button launchButton = (Button) findViewById(R.id.activity_main_launch_button);
        launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VimeoDeeplink.openVimeoApp(MainActivity.this);
            }
        });
        Button playstoreButton = (Button) findViewById(R.id.activity_main_playstore_button);
        playstoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VimeoDeeplink.viewVimeoAppInAppStore(MainActivity.this);
            }
        });
        Button categoriesButton = (Button) findViewById(R.id.activity_main_categories_button);
        categoriesButton.setEnabled(VimeoDeeplink.canHandleCategoiesDeeplink(MainActivity.this));
        categoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VimeoDeeplink.showCategories(MainActivity.this);
            }
        });
        Button exploreButton = (Button) findViewById(R.id.activity_main_explore_button);
        exploreButton.setEnabled(VimeoDeeplink.canHandleExploreDeeplink(MainActivity.this));
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VimeoDeeplink.showExplore(MainActivity.this);
            }
        });
        Button feedButton = (Button) findViewById(R.id.activity_main_feed_button);
        feedButton.setEnabled(VimeoDeeplink.canHandleFeedDeeplink(MainActivity.this));
        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VimeoDeeplink.showFeed(MainActivity.this);
            }
        });
        Button myProfileButton = (Button) findViewById(R.id.activity_main_me_button);
        myProfileButton.setEnabled(VimeoDeeplink.canHandleMeDeeplink(MainActivity.this));
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VimeoDeeplink.showMyProfile(MainActivity.this);
            }
        });
        Button playlistsButton = (Button) findViewById(R.id.activity_main_playlists_button);
        playlistsButton.setEnabled(VimeoDeeplink.canHandlePlaylistDeeplink(MainActivity.this));
        playlistsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VimeoDeeplink.showPlaylists(MainActivity.this);
            }
        });
        Button uploadButton = (Button) findViewById(R.id.activity_main_upload_button);
        uploadButton.setEnabled(VimeoDeeplink.canHandleUploadDeeplink(MainActivity.this));
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VimeoDeeplink.showUpload(MainActivity.this);
            }
        });
    }

    private String generatedUriPath() {
        switch (mDeepLinkType) {
            case CATEGORY:
                return DEFAULT_CATEGORY_URI_PATH;
            case CHANNEL:
                return DEFAULT_CHANNEL_URI_PATH;
            case URL:
                return DEFAULT_VIDEO_URL;
            case USER:
                return DEFAULT_USER_URI_PATH;
            case VIDEO:
                return DEFAULT_VIDEO_URI_PATH;
            case NONE:
                break;
        }
        return null;
    }

    private void performDeepLink(String uriPath) {
        String uri;
        boolean handled = false;
        switch (mDeepLinkType) {
            case CATEGORY:
                uri = VimeoDeeplink.VIMEO_CATEGORY_URI_PREFIX + uriPath;
                handled = VimeoDeeplink.showCategoryWithUri(this, uri);
                break;
            case CHANNEL:
                uri = VimeoDeeplink.VIMEO_CHANNEL_URI_PREFIX + uriPath;
                handled = VimeoDeeplink.showChannelWithUri(this, uri);
                break;
            case URL:
                handled = VimeoDeeplink.openUrl(this, uriPath);
                break;
            case USER:
                uri = VimeoDeeplink.VIMEO_USER_URI_PREFIX + uriPath;
                handled = VimeoDeeplink.showUserWithUri(this, uri);
                break;
            case VIDEO:
                uri = VimeoDeeplink.VIMEO_VIDEO_URI_PREFIX + uriPath;
                handled = VimeoDeeplink.showVideoWithUri(this, uri);
                break;
            case NONE:
                break;
        }
        if (!handled) {
            Toast.makeText(this, R.string.activity_main_deeplink_failure, Toast.LENGTH_SHORT).show();
        }
    }
}
