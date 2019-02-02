package khaledhaouas.com.tmdbmovies.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.widget.RatingBar;

import java.util.Locale;

import khaledhaouas.com.tmdbmovies.R;

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

    public static void initRatingBar(Context context, RatingBar rating) {
        LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
        //Color filled stars
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        //Color half filled stars
        stars.getDrawable(1).setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        //Color empty stars
        stars.getDrawable(0).setColorFilter(ContextCompat.getColor(context, R.color.grey), PorterDuff.Mode.SRC_IN);
    }
}
