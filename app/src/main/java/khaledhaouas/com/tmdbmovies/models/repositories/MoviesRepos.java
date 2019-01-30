package khaledhaouas.com.tmdbmovies.models.repositories;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import khaledhaouas.com.tmdbmovies.managers.ApiManager.ApiManager;
import khaledhaouas.com.tmdbmovies.managers.ApiManager.ApiServerCallback;
import khaledhaouas.com.tmdbmovies.managers.ConfigManager;
import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMovieLoadedCallback;

public class MoviesRepos {
    private static final String TAG = "MoviesRepos";

    public void getMovieDetails(int id, final OnMovieLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "movie/" + id + ConfigManager.getInstance().addApiKeyToRequest();
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                Movie movie = parseJsonToMovie(result);
                Log.e(TAG, movie.toString());
                callback.onSuccess(movie);
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

    private Movie parseJsonToMovie(JSONObject jsonMovie) {
        Movie resMovie = new Movie();
        try {
            resMovie.setId(jsonMovie.getInt("id"));
            resMovie.setBackgroundImageUrl("https://image.tmdb.org/t/p/w500" + jsonMovie.getString("backdrop_path"));
            resMovie.setPosterImageUrl("https://image.tmdb.org/t/p/w500" + jsonMovie.getString("poster_path"));
            resMovie.setTitle(jsonMovie.getString("title"));
            resMovie.setReleaseDate(jsonMovie.getString("release_date"));
            resMovie.setPlot(jsonMovie.getString("overview"));
            resMovie.setReviewNbrs(jsonMovie.getInt("vote_count"));
            resMovie.setRating(jsonMovie.getDouble("vote_average"));
            resMovie.setRunTime(jsonMovie.getInt("runtime"));
            resMovie.setLanguage(jsonMovie.getString("original_language"));
            StringBuilder genres = new StringBuilder();
            JSONArray genresArray = jsonMovie.getJSONArray("genres");
            for (int i = 0; i < genresArray.length(); i++) {
                genres.append(((JSONObject) genresArray.get(i)).getString("name")).append("|");
            }
            genres.deleteCharAt(genres.toString().length() - 1);
            resMovie.setGenres(genres.toString());

            return resMovie;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }


}
