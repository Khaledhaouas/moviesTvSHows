package khaledhaouas.com.tmdbmovies.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import java.util.Locale;

public class Utils {

    public static String getCountryFromCode(String code) {
        Locale loc = new Locale(code);
        return loc.getDisplayLanguage(loc);
    }

    public static String formatTimeFromMinutes(int minutes) {
        String resTime = "";
        if (minutes / 60 != 0) {
            resTime = resTime + (minutes / 60) + " hr " + resTime + (minutes % 60) + " min";
        } else {
            resTime = resTime + (minutes % 60) + " min";
        }
        return resTime;
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }
}
