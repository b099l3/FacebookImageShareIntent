package com.weeworld.facebooktestingimageandtext.app;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void shareButtonPressed(View view)
    {
        // uri to the image you want to share
        Uri path = Uri.parse("android.resource://com.weeworld.facebooktestingimageandtext.app/" + R.drawable.ic_launcher);
        Resources resources = getResources();

        // create email intent first to remove bluetooth + others options
        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
        // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
        emailIntent.putExtra(Intent.EXTRA_TEXT, "blah");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "blah");
        emailIntent.setType("image/jpeg");
        // Create the chooser based on the email intent
        Intent openInChooser = Intent.createChooser(emailIntent, "blah");

        // Check for other packages that open our mime type
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/jpeg");

        PackageManager pm = getPackageManager();
        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        for(int i = 0; i < resInfo.size(); i++)
        {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if(packageName.contains("android.email"))
            {
                emailIntent.setPackage(packageName);
                // select packages to share here V
            }
            else if(packageName.contains("com.instagram.android") || packageName.contains("com.twitter.android") || packageName.contains("com.whatsapp") || packageName.contains("mms") || packageName.contains("android.gm"))
                  /* Removed facebook Intent here can add it back in if needed */
            //|| packageName.contains("com.facebook.katana"))
            {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, path);
                intent.setType("image/jpeg");

                if(packageName.contains("twitter"))
                {
                    intent.putExtra(Intent.EXTRA_TEXT, "blah");
                }
                else if(packageName.contains("mms"))
                {
                    intent.putExtra(Intent.EXTRA_TEXT, "blah");
                }
                    /* can add back in the original facebook intent here
                    else if(packageName.contains("facebook"))
                    {
                        // Facebook IGNORES our text. They say "These fields are intended for users to express themselves.
                        // Pre-filling these fields erodes the authenticity of the user voice."
                        // Putting it here anyway as they might change their minds
                        intent.putExtra(Intent.EXTRA_TEXT, "picture caption #test");
                    }*/
                else if(packageName.contains("android.gm"))
                {
                    intent.putExtra(Intent.EXTRA_TEXT, "blah");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "blah");
                }
                
                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
            }
        }


        // Get our custom intent put here as we only want your app to get it not others
        Intent customIntent = new Intent("facebooktestingimageandtext.intent.action.SEND");
        customIntent.setType("image/jpeg");
        customIntent.setAction("facebooktestingimageandtext.intent.action.SEND");

        resInfo = pm.queryIntentActivities(customIntent, 0);
        for(int i = 0; i < resInfo.size(); i++)
        {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if(packageName.contains("com.weeworld.slapsticker.app"))
            {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, path);
                intent.setType("image/jpeg");
                if(packageName.contains("com.weeworld.slapsticker.app"))
                {
                    // My custom facebook intent to do something very simple!
                    intent.putExtra(Intent.EXTRA_TEXT, "caption #testhashtag");
                }
                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
            }
        }

        // convert the list of intents(intentList) to array and add as extra intents
        LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);
        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);

        startActivity(openInChooser);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
