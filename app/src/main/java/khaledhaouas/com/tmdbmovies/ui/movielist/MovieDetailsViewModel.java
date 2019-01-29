package khaledhaouas.com.tmdbmovies.ui.movielist;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import org.json.JSONObject;

import khaledhaouas.com.tmdbmovies.managers.ApiManager.ApiManager;
import khaledhaouas.com.tmdbmovies.managers.ApiManager.ApiServerCallback;
import khaledhaouas.com.tmdbmovies.managers.ConfigManager;

public class MovieDetailsViewModel extends ViewModel {
    private static final String TAG = "MovieDetailsViewModel";

    public void getPopular() {
        String url = ConfigManager.getInstance().getAppRoot() + "movie/popular" + ConfigManager.getInstance().addApiKeyToRequest();
        Log.e(TAG, "onCreate: "+url );
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                Log.e(TAG, "onSuccess: " + result.toString());
                return false;
            }

            @Override
            public boolean onFailure(int statusCode) {
                Log.e(TAG, "onFailure: " );
                return false;
            }
        });
    }
}
