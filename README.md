FacebookImageShareIntent
========================

An Android share intent that allows you to share and image with text aswell! 

Using the Facebook SDK 3.14.1 

This is a work around for this bug https://developers.facebook.com/bugs/332619626816423

How to Use
----------
1 Change the app_id in strings.xml (You will find this at https://developers.facebook.com/ under dashboard in your facebook app)
e.g. if your app_id was 123456789123456
```xml
<!--LIVE APP ID-->
<string name="app_id">123456789123456</string>
```


Adding to your own app
----------------------
1. Copy FacebookShareActivty to your project
 1. Import your R file from your project
   ```
   import com.YOURAPPPACKAGENAME.app.R;
   ```
 2. Copy activity_facebook_share.xml from res/layout into res/layout in your project.
2. Add the Facebook SDK
 1. The 'import module' does not work properly in Android Studio So this is the way I do it
  1. Copy the 'facebook' module from this project.
  2. Edit the settings.gradle in the root project
   1. add the facebook module in here like so
      ```xml
        include ':app', ':facebook'
      ```

 2. Go to file > Project Structure
  1. click on your app module > Dependecies tab > '+' button at the bottom > Module Dependecy > then click on ':facebook'
 
3. Add these strings to your strings.xml
   ```xml
   <!--Facebook share intent Strings-->
    <string name="title_activity_facebook_share">Facebook</string>
    <string name="facebook_default_text">Please enter your text here</string>
    <string name="title_post_text">Write Post</string>
    <string name="post_button_text">Post</string>
    <string name="title_facebook_login">Facebook Login</string>


    <!--LIVE APP ID-->
    <string name="app_id">ADD_YOUR_OWN_APP_ID_HERE</string>
    <string name="facebook_post_content_desc">Image to share</string>
    <string name="app_package_name">com.YOURAPPPACKAGENAME.app</string>
    ```
    
4. Add this activity and meta-data to your AndroidMaifest.xml 
   ```xml
   <activity
            android:name="com.CHANGETHISTOYOURAPP.app.FacebookShareActivity"
            android:label="@string/title_activity_facebook_share"
            android:icon="@drawable/icon_facebook_activity"
            android:theme="@style/FullscreenTheme">
            <!--Using icon from facebook app as of 23/05/14-->
            <intent-filter>
                <action android:name="android.intent.action.SEND"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <data android:mimeType="text/plain"/>
                <data android:mimeType="image/*"/>
            </intent-filter>
        </activity>
        <activity
            android:name="com.facebook.LoginActivity"
            android:label="@string/title_facebook_login" >
        </activity>
        <meta-data android:name="com.facebook.sdk.ApplicationId" android:value="@string/app_id"/>
   ```

5. Add these styles to styles.xml
   ```xml
   <style name="FullscreenTheme" parent="android:Theme.NoTitleBar">
        <item name="android:windowContentOverlay">@null</item>
        <item name="android:windowBackground">@null</item>
    </style>

    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
    </style>
   ```
   
6. Add the Facebook Icon under res/drawable/icon_facebook_activity.png to your res/drawable folder
7. Clean the build, then rebuild.
8. Change these settings in https://developers.facebook.com/ under settings 
 1. Package Name to your pack name of your app e.g. com.weeworld.facebooktestingimageandtext.app 
 2. Class Name to your main activity e.g. MainActivity
 3. Add the Key Hashes (you can get this by running the app with 'getHashKey()' uncommented and checking the log or on OS X, run: 
    
    ```
 keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore | openssl sha1 -binary | openssl base64
    ```

## To use the intent

The Intent supports the type "image/*" and "text/plain" so as long as you set the type of your intent when creating the chooser it will show. If you would rather have a set list of intents check out the demo app to see how that is done in 'shareButtonPressed' in 'MainActivity.java'

Make sure the extra stream is set to the uri of the image and extra text is the caption, for local images check the demo app.
```xml
intent.putExtra(Intent.EXTRA_TEXT, "picture caption #test");
intent.putExtra(Intent.EXTRA_STREAM, path);
```

If you get CLOSED_LOGIN_FAILED sessions check your setting in https://developers.facebook.com/
ScreenShots
-----------

![screenshot1](https://www.dropbox.com/s/vq65beea9ed6aii/Screenshot%202014-05-29%2013.23.29.png)
