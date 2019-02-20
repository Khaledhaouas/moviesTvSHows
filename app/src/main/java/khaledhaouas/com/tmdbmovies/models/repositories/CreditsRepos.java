package khaledhaouas.com.tmdbmovies.models.repositories;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.managers.ApiManager.ApiManager;
import khaledhaouas.com.tmdbmovies.managers.ApiManager.ApiServerCallback;
import khaledhaouas.com.tmdbmovies.managers.ConfigManager;
import khaledhaouas.com.tmdbmovies.models.entities.Credit;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnCreditListLoadedCallback;

public class CreditsRepos {
    private static final String TAG = "CreditsRepos";

    public void getMovieCreditsList(int id, final OnCreditListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "movie/" + id + "/credits" + ConfigManager.getInstance().addApiKeyToRequest();
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                ArrayList<Credit> credits = parseJsonToCreditsList(result);
//                Log.e(TAG, movie.toString());
                callback.onSuccess(credits);
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

    public void getTvCreditsList(int id, final OnCreditListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "movie/" + id + "/credits" + ConfigManager.getInstance().addApiKeyToRequest();
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                ArrayList<Credit> credits = parseJsonToCreditsList(result);
//                Log.e(TAG, movie.toString());
                callback.onSuccess(credits);
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

    private ArrayList<Credit> parseJsonToCreditsList(JSONObject jsonCredits) {
        ArrayList<Credit> credits = new ArrayList<>();
        try {
            JSONArray castJsonArray = jsonCredits.getJSONArray("cast");
            for (int i = 0; i < castJsonArray.length(); i++) {
                Credit c = new Credit();
                c.setCreditId(castJsonArray.getJSONObject(i).getString("credit_id"));
                c.setActorId(castJsonArray.getJSONObject(i).getInt("id"));
                c.setActorName(castJsonArray.getJSONObject(i).getString("name"));
                c.setCharacterName(castJsonArray.getJSONObject(i).getString("character"));
                c.setActorProfilePicture("https://image.tmdb.org/t/p/w185" + castJsonArray.getJSONObject(i).getString("profile_path"));
                c.setActorGender(castJsonArray.getJSONObject(i).getInt("gender"));
                credits.add(c);
                Log.e(TAG, c.toString());
            }

            return credits;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

}
