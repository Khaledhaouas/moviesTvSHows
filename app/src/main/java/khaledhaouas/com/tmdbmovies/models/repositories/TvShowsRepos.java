package khaledhaouas.com.tmdbmovies.models.repositories;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.managers.ApiManager.ApiManager;
import khaledhaouas.com.tmdbmovies.managers.ApiManager.ApiServerCallback;
import khaledhaouas.com.tmdbmovies.managers.ConfigManager;
import khaledhaouas.com.tmdbmovies.models.entities.Company;
import khaledhaouas.com.tmdbmovies.models.entities.Episode;
import khaledhaouas.com.tmdbmovies.models.entities.Season;
import khaledhaouas.com.tmdbmovies.models.entities.TvShow;
import khaledhaouas.com.tmdbmovies.models.entities.Video;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnEpisodeListLoadedCallback;
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
            JSONArray networksArray = jsonShow.getJSONArray("networks");
            for (int j = 0; j < networksArray.length(); j++) {
                Company c = new Company();
                c.setId(((JSONObject) networksArray.get(j)).getInt("id"));
                c.setName(((JSONObject) networksArray.get(j)).getString("name"));
                c.setCountry(((JSONObject) networksArray.get(j)).getString("origin_country"));
                c.setLogoImage("https://image.tmdb.org/t/p/w92" + ((JSONObject) networksArray.get(j)).getString("logo_path"));
                resTvShow.getNetworks().add(c);
            }

            JSONArray seasonsArray = jsonShow.getJSONArray("seasons");
            for (int j = 0; j < seasonsArray.length(); j++) {
                Season s = new Season();
                s.setId(((JSONObject) seasonsArray.get(j)).getInt("id"));
                s.setName(((JSONObject) seasonsArray.get(j)).getString("name"));
                s.setAirDate(((JSONObject) seasonsArray.get(j)).getString("air_date"));
                s.setEpisodeCount(((JSONObject) seasonsArray.get(j)).getInt("episode_count"));
                s.setOverview(((JSONObject) seasonsArray.get(j)).getString("overview"));
                s.setNumber(((JSONObject) seasonsArray.get(j)).getInt("season_number"));
                s.setPosterImage("https://image.tmdb.org/t/p/w185" + ((JSONObject) seasonsArray.get(j)).getString("poster_path"));
                resTvShow.getSeasons().add(s);
            }

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

    public void getEpisodesBySeason(int showId, int seasonNbre, final OnEpisodeListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "tv/" + showId + "/season/" + seasonNbre + ConfigManager.getInstance().addApiKeyToRequest();
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                ArrayList<Episode> episodes = parseJsonToEpisodesList(result);

                callback.onSuccess(episodes);
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

    private ArrayList<Episode> parseJsonToEpisodesList(JSONObject jsonSeason) {
        ArrayList<Episode> episodes = new ArrayList<>();
        try {
            JSONArray episodesJsonArray = jsonSeason.getJSONArray("episodes");
            for (int i = 0; i < episodesJsonArray.length(); i++) {
                Episode resEpisode = new Episode();
                resEpisode.setId(episodesJsonArray.getJSONObject(i).getInt("id"));
                resEpisode.setPosterImage("https://image.tmdb.org/t/p/w185" + episodesJsonArray.getJSONObject(i).getString("still_path"));
                resEpisode.setAirDate(episodesJsonArray.getJSONObject(i).getString("air_date"));
                resEpisode.setOverview(episodesJsonArray.getJSONObject(i).getString("overview"));
                resEpisode.setName(episodesJsonArray.getJSONObject(i).getString("name"));
                resEpisode.setVoteCount(episodesJsonArray.getJSONObject(i).getInt("vote_count"));
                resEpisode.setRating(episodesJsonArray.getJSONObject(i).getDouble("vote_average"));
                resEpisode.setNumber(episodesJsonArray.getJSONObject(i).getInt("episode_number"));

                episodes.add(resEpisode);
                Log.e(TAG, episodes.toString());
            }

            return episodes;
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
                ArrayList<TvShow> tvShows = parseJsonToTvShowsList(result);

//                Log.e(TAG, TvShow.toString());
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, TvShow.toString());
                callback.onSuccess(tvShows, totalPages);
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
                ArrayList<TvShow> tvShows = parseJsonToTvShowsList(result);
//                Log.e(TAG, TvShow.toString());
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, TvShow.toString());
                callback.onSuccess(tvShows, totalPages);
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
                ArrayList<TvShow> tvShows = parseJsonToTvShowsList(result);
//                Log.e(TAG, TvShow.toString());
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, TvShow.toString());
                callback.onSuccess(tvShows, totalPages);
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
                ArrayList<TvShow> tvShows = parseJsonToTvShowsList(result);
//                Log.e(TAG, TvShow.toString());
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, TvShow.toString());
                callback.onSuccess(tvShows, totalPages);
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

                ArrayList<TvShow> tvShows = parseJsonToTvShowsList(result);
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, TvShow.toString());
                callback.onSuccess(tvShows, totalPages);
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

                ArrayList<TvShow> tvShows = parseJsonToTvShowsList(result);
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, TvShow.toString());
                callback.onSuccess(tvShows, totalPages);
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
            JSONArray tvShowsJsonArray = jsonTvShows.getJSONArray("results");
            for (int i = 0; i < tvShowsJsonArray.length(); i++) {
                TvShow resTvShow = new TvShow();
                resTvShow.setId(tvShowsJsonArray.getJSONObject(i).getInt("id"));
                resTvShow.setPosterImageUrl("https://image.tmdb.org/t/p/w185" + tvShowsJsonArray.getJSONObject(i).getString("poster_path"));
                resTvShow.setTitle(tvShowsJsonArray.getJSONObject(i).getString("name"));
                resTvShow.setFirstEpDate(tvShowsJsonArray.getJSONObject(i).getString("first_air_date"));
                resTvShow.setPlot(tvShowsJsonArray.getJSONObject(i).getString("overview"));
                resTvShow.setReviewNbrs(tvShowsJsonArray.getJSONObject(i).getInt("vote_count"));
                resTvShow.setRating(tvShowsJsonArray.getJSONObject(i).getDouble("vote_average"));
                resTvShow.setLanguage(tvShowsJsonArray.getJSONObject(i).getString("original_language"));
                StringBuilder genres = new StringBuilder();
//                JSONArray genresArray = tvShowsJsonArray.getJSONObject(i).getJSONArray("genre_ids");
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
