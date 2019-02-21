package khaledhaouas.com.tmdbmovies.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RatingBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static String formatDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
            return monthFormat.format(date) + " " + calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }
}
