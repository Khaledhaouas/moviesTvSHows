package khaledhaouas.com.tmdbmovies.models.repositories;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.managers.ApiManager.ApiManager;
import khaledhaouas.com.tmdbmovies.managers.ApiManager.ApiServerCallback;
import khaledhaouas.com.tmdbmovies.managers.ConfigManager;
import khaledhaouas.com.tmdbmovies.models.entities.TvShow;
import khaledhaouas.com.tmdbmovies.models.entities.Video;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnTvShowListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnTvShowLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnVideoListLoadedCallback;

public class TvShowsRepos {

    private static final String TAG = "TvShowsRepos";

    public void getTvShowDetails(int id, final OnTvShowLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "tv/" + id + ConfigManager.getInstance().addApiKeyToRequest();
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                TvShow show = parseJsonToShow(result);
                Log.e(TAG, show.toString());
                callback.onSuccess(show);
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

    private TvShow parseJsonToShow(JSONObject jsonShow) {
        TvShow resTvShow = new TvShow();
        try {
            resTvShow.setId(jsonShow.getInt("id"));
            resTvShow.setBackgroundImageUrl("https://image.tmdb.org/t/p/w500" + jsonShow.getString("backdrop_path"));
            resTvShow.setPosterImageUrl("https://image.tmdb.org/t/p/w342" + jsonShow.getString("poster_path"));
            resTvShow.setTitle(jsonShow.getString("name"));
            resTvShow.setFirstEpDate(jsonShow.getString("first_air_date"));
            resTvShow.setPlot(jsonShow.getString("overview"));
            resTvShow.setReviewNbrs(jsonShow.getInt("vote_count"));
            resTvShow.setRating(jsonShow.getDouble("vote_average"));
            resTvShow.setEpNbre(jsonShow.getInt("number_of_episodes"));
            resTvShow.setSeasonNbre(jsonShow.getInt("number_of_seasons"));

            try {
//                resTvShow.setEpRunTime(jsonShow.getInt("episode_run_time"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            resTvShow.setLanguage(jsonShow.getString("original_language"));
            StringBuilder genres = new StringBuilder();
            JSONArray genresArray = jsonShow.getJSONArray("genres");
            for (int i = 0; i < genresArray.length(); i++) {
                genres.append(((JSONObject) genresArray.get(i)).getString("name")).append("|");
            }
            genres.deleteCharAt(genres.toString().length() - 1);
            resTvShow.setGenres(genres.toString());

            return resTvShow;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }


    public void getSimilarTvShowsList(int id, final OnTvShowListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "tv/" + id + "/similar" + ConfigManager.getInstance().addApiKeyToRequest();
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                ArrayList<TvShow> TvShows = parseJsonToTvShowsList(result);

//                Log.e(TAG, TvShow.toString());
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, TvShow.toString());
                callback.onSuccess(TvShows, totalPages);
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

    public void getSimilarTvShowsList(int id, int page, final OnTvShowListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "tv/" + id + "/similar" + ConfigManager.getInstance().addApiKeyToRequest() + "&page=" + page;
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                ArrayList<TvShow> TvShows = parseJsonToTvShowsList(result);
//                Log.e(TAG, TvShow.toString());
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, TvShow.toString());
                callback.onSuccess(TvShows, totalPages);
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

    public void getTvShowsList(String TvShowListType, final OnTvShowListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "tv/" + TvShowListType + ConfigManager.getInstance().addApiKeyToRequest();
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                ArrayList<TvShow> TvShows = parseJsonToTvShowsList(result);
//                Log.e(TAG, TvShow.toString());
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, TvShow.toString());
                callback.onSuccess(TvShows, totalPages);
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

    public void getTvShowsList(String TvShowListType, int page, final OnTvShowListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "tv/" + TvShowListType + ConfigManager.getInstance().addApiKeyToRequest() + "&page=" + page;
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                ArrayList<TvShow> TvShows = parseJsonToTvShowsList(result);
//                Log.e(TAG, TvShow.toString());
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, TvShow.toString());
                callback.onSuccess(TvShows, totalPages);
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

    public void getSearchResultTvShowsList(String searchText, final OnTvShowListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "search/tv" + ConfigManager.getInstance().addApiKeyToRequest() + "&query=" + searchText;
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().CANCELALLPENDINGREQUESTS();
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {

                ArrayList<TvShow> TvShows = parseJsonToTvShowsList(result);
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, TvShow.toString());
                callback.onSuccess(TvShows, totalPages);
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

    public void getNextPageSearchResultTvShowsList(String searchText, int page, final OnTvShowListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "search/tv" + ConfigManager.getInstance().addApiKeyToRequest() + "&query=" + searchText + "&page=" + page;
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().CANCELALLPENDINGREQUESTS();
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {

                ArrayList<TvShow> TvShows = parseJsonToTvShowsList(result);
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, TvShow.toString());
                callback.onSuccess(TvShows, totalPages);
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

    private ArrayList<TvShow> parseJsonToTvShowsList(JSONObject jsonTvShows) {
        ArrayList<TvShow> shows = new ArrayList<>();
        try {
            JSONArray TvShowsJsonArray = jsonTvShows.getJSONArray("results");
            for (int i = 0; i < TvShowsJsonArray.length(); i++) {
                TvShow resTvShow = new TvShow();
                resTvShow.setId(TvShowsJsonArray.getJSONObject(i).getInt("id"));
                resTvShow.setPosterImageUrl("https://image.tmdb.org/t/p/w185" + TvShowsJsonArray.getJSONObject(i).getString("poster_path"));
                resTvShow.setTitle(TvShowsJsonArray.getJSONObject(i).getString("name"));
                resTvShow.setFirstEpDate(TvShowsJsonArray.getJSONObject(i).getString("first_air_date"));
                resTvShow.setPlot(TvShowsJsonArray.getJSONObject(i).getString("overview"));
                resTvShow.setReviewNbrs(TvShowsJsonArray.getJSONObject(i).getInt("vote_count"));
                resTvShow.setRating(TvShowsJsonArray.getJSONObject(i).getDouble("vote_average"));
                resTvShow.setLanguage(TvShowsJsonArray.getJSONObject(i).getString("original_language"));
                StringBuilder genres = new StringBuilder();
//                JSONArray genresArray = TvShowsJsonArray.getJSONObject(i).getJSONArray("genre_ids");
//                for (int j = 0; j < genresArray.length(); j++) {
//                    genres.append(((JSONObject) genresArray.get(j)).getInt("name")).append("|");
//                }
//                genres.deleteCharAt(genres.toString().length() - 1);
                //                    genres.append(((JSONObject) genresArray.get(j)).getInt("name")).append("|");
                resTvShow.setGenres(genres.toString());
                shows.add(resTvShow);
                Log.e(TAG, shows.toString());
            }

            return shows;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void getTvShowVideosList(int id, final OnVideoListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "tv/" + id + "/videos" + ConfigManager.getInstance().addApiKeyToRequest();
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                ArrayList<Video> videos = parseJsonToVideosList(result);
//                Log.e(TAG, TvShow.toString());
                callback.onSuccess(videos);
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

    private ArrayList<Video> parseJsonToVideosList(JSONObject jsonVideos) {
        ArrayList<Video> videos = new ArrayList<>();
        try {
            JSONArray videosJsonArray = jsonVideos.getJSONArray("results");
            for (int i = 0; i < videosJsonArray.length(); i++) {
                Video resVideo = new Video();
                resVideo.setId(videosJsonArray.getJSONObject(i).getString("id"));
                resVideo.setName(videosJsonArray.getJSONObject(i).getString("name"));
                resVideo.setType(videosJsonArray.getJSONObject(i).getString("type"));
                resVideo.setUrlKey(videosJsonArray.getJSONObject(i).getString("key"));
                videos.add(resVideo);
                Log.e(TAG, videos.toString());
            }

            return videos;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }
}
