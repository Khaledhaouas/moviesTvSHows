package khaledhaouas.com.tmdbmovies.models.repositories;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.managers.ApiManager.ApiManager;
import khaledhaouas.com.tmdbmovies.managers.ApiManager.ApiServerCallback;
import khaledhaouas.com.tmdbmovies.managers.ConfigManager;
import khaledhaouas.com.tmdbmovies.models.entities.Review;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnReviewListLoadedCallback;

public class ReviewsRepos {
    private static final String TAG = "ReviewsRepos";

    public void getMovieReviewsList(int id, final OnReviewListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "movie/" + id + "/reviews" + ConfigManager.getInstance().addApiKeyToRequest();
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                ArrayList<Review> reviews = parseJsonToReviewsList(result);
//                Log.e(TAG, movie.toString());
                callback.onSuccess(reviews);
                return false;
            }

            @Override
            public boolean onFailure(int statusCode) {
                Log.e(TAG, "onFailure: ");
                callback.onError();
                return false;
            }
        });
    }

    public void getTvReviewsList(int id, final OnReviewListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "tv/" + id + "/reviews" + ConfigManager.getInstance().addApiKeyToRequest();
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                ArrayList<Review> reviews = parseJsonToReviewsList(result);
//                Log.e(TAG, movie.toString());
                callback.onSuccess(reviews);
                return false;
            }

            @Override
            public boolean onFailure(int statusCode) {
                Log.e(TAG, "onFailure: ");
                callback.onError();
                return false;
            }
        });
    }

    private ArrayList<Review> parseJsonToReviewsList(JSONObject jsonReviews) {
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            JSONArray reviewsJsonArray = jsonReviews.getJSONArray("results");
            for (int i = 0; i < reviewsJsonArray.length(); i++) {
                Review r = new Review();
                r.setId(reviewsJsonArray.getJSONObject(i).getString("id"));
                r.setAuthor(reviewsJsonArray.getJSONObject(i).getString("author"));
                r.setContent(reviewsJsonArray.getJSONObject(i).getString("content"));
                reviews.add(r);
                Log.e(TAG, r.toString());
            }

            return reviews;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

}
