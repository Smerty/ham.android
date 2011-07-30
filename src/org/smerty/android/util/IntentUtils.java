package org.smerty.android.util;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class IntentUtils {

  public static boolean isIntentAvailable(Context context, String target) {
    final PackageManager packageManager = context.getPackageManager();
    final Intent intent = new Intent(target);
    List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(
        intent, PackageManager.MATCH_DEFAULT_ONLY);
    if (resolveInfo.isEmpty()) {
      return false;
    }
    return true;
  }

}
