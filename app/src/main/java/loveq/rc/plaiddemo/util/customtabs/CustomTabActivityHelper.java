package loveq.rc.plaiddemo.util.customtabs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

/**
 * Created by rc on 2017/12/25.
 * Description:
 */

public class CustomTabActivityHelper {


    /**
     * Opens the URL on a Custom Tab if possible; otherwise falls back to opening it via
     * {@code Intent.ACTION_VIEW}
     *
     * @param activity The host activity
     * @param customTabsIntent a CustomTabsIntent to be used if Custom Tabs is available
     * @param uri the Uri to be opened
     */
    public static void openCustomTab(Activity activity,
                                     CustomTabsIntent customTabsIntent,
                                     Uri uri) {
        String packageName = CustomTabsHelper.getPackageNameToUse(activity);

        // if we cant find a package name, it means there's no browser that supports
        // Custom Tabs installed. So, we fallback to a view intent
        if (packageName != null) {
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity, uri);
        } else {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
}
