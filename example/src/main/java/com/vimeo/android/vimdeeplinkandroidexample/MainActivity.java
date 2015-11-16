package com.vimeo.android.vimdeeplinkandroidexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vimeo.android.vimdeeplink.VIMDeeplink;

public class MainActivity extends AppCompatActivity {

    private static final String DEFAULT_CATEGORY_URI_PATH = "art";
    private static final String DEFAULT_CHANNEL_URI_PATH = "staffpicks";
    private static final String DEFAULT_USER_URI_PATH = "vimeomobile";
    private static final String DEFAULT_VIDEO_URI_PATH = "71994339";

    public enum DeepLinkType {
        NONE,
        CATEGORY,
        CHANNEL,
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
        videoRadioButton.setEnabled(VIMDeeplink.canHandleVideoDeeplink(this));
        RadioButton userRadioButton = (RadioButton) findViewById(R.id.activity_main_user_radiobutton);
        userRadioButton.setEnabled(VIMDeeplink.canHandleUserDeeplink(this));
        RadioButton categoryRadioButton = (RadioButton) findViewById(R.id.activity_main_category_radiobutton);
        categoryRadioButton.setEnabled(VIMDeeplink.canHandleCategoryDeeplink(this));
        RadioButton channelRadioButton = (RadioButton) findViewById(R.id.activity_main_channel_radiobutton);
        channelRadioButton.setEnabled(VIMDeeplink.canHandleChannelDeeplink(this));

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.activity_main_radiogroup);
        radioGroup.setOnCheckedChangeListener(mCheckedChangeListener);

        mGoButton.setEnabled(VIMDeeplink.isVimeoAppInstalled(this));
        mGoButton.setOnClickListener(mGoClickListener);

        Button launchButton = (Button) findViewById(R.id.activity_main_launch_button);
        launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VIMDeeplink.openVimeoApp(MainActivity.this);
            }
        });
        Button playstoreButton = (Button) findViewById(R.id.activity_main_playstore_button);
        playstoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VIMDeeplink.viewVimeoAppInAppStore(MainActivity.this);
            }
        });
        Button categoriesButton = (Button) findViewById(R.id.activity_main_categories_button);
        categoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VIMDeeplink.showCategories(MainActivity.this);
            }
        });
        Button exploreButton = (Button) findViewById(R.id.activity_main_explore_button);
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VIMDeeplink.showExplore(MainActivity.this);
            }
        });
        Button feedButton = (Button) findViewById(R.id.activity_main_feed_button);
        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VIMDeeplink.showFeed(MainActivity.this);
            }
        });
        Button myProfileButton = (Button) findViewById(R.id.activity_main_me_button);
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VIMDeeplink.showMyProfile(MainActivity.this);
            }
        });
        Button playlistsButton = (Button) findViewById(R.id.activity_main_playlists_button);
        playlistsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VIMDeeplink.showPlaylists(MainActivity.this);
            }
        });
        Button uploadButton = (Button) findViewById(R.id.activity_main_upload_button);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VIMDeeplink.showUpload(MainActivity.this);
            }
        });
    }

    private String generatedUriPath() {
        switch (mDeepLinkType) {
            case CATEGORY:
                return DEFAULT_CATEGORY_URI_PATH;
            case CHANNEL:
                return DEFAULT_CHANNEL_URI_PATH;
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
                uri = VIMDeeplink.VIMEO_CATEGORY_URI_PREFIX + uriPath;
                handled = VIMDeeplink.showCategoryWithUri(this, uri);
                break;
            case CHANNEL:
                uri = VIMDeeplink.VIMEO_CHANNEL_URI_PREFIX + uriPath;
                handled = VIMDeeplink.showChannelWithUri(this, uri);
                break;
            case USER:
                uri = VIMDeeplink.VIMEO_USER_URI_PREFIX + uriPath;
                handled = VIMDeeplink.showUserWithUri(this, uri);
                break;
            case VIDEO:
                uri = VIMDeeplink.VIMEO_VIDEO_URI_PREFIX + uriPath;
                handled = VIMDeeplink.showVideoWithUri(this, uri);
                break;
            case NONE:
                break;
        }
        if (!handled) {
            Toast.makeText(this, R.string.activity_main_deeplink_failure, Toast.LENGTH_SHORT).show();
        }
    }
}
