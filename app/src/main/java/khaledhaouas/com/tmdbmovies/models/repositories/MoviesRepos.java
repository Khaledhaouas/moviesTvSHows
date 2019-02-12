package khaledhaouas.com.tmdbmovies.models.repositories;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.managers.ApiManager.ApiManager;
import khaledhaouas.com.tmdbmovies.managers.ApiManager.ApiServerCallback;
import khaledhaouas.com.tmdbmovies.managers.ConfigManager;
import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.models.entities.Video;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMovieLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMoviesListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnVideoListLoadedCallback;

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
            resMovie.setPosterImageUrl("https://image.tmdb.org/t/p/w342" + jsonMovie.getString("poster_path"));
            resMovie.setTitle(jsonMovie.getString("title"));
            resMovie.setReleaseDate(jsonMovie.getString("release_date"));
            resMovie.setPlot(jsonMovie.getString("overview"));
            resMovie.setReviewNbrs(jsonMovie.getInt("vote_count"));
            resMovie.setRating(jsonMovie.getDouble("vote_average"));
            try {
                resMovie.setRunTime(jsonMovie.getInt("runtime"));
            } catch (Exception e) {
                e.printStackTrace();
            }

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


    public void getSimilarMoviesList(int id, final OnMoviesListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "movie/" + id + "/similar" + ConfigManager.getInstance().addApiKeyToRequest();
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                ArrayList<Movie> movies = parseJsonToMoviesList(result);

//                Log.e(TAG, movie.toString());
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, movie.toString());
                callback.onSuccess(movies, totalPages);
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

    public void getSimilarMoviesList(int id, int page, final OnMoviesListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "movie/" + id + "/similar" + ConfigManager.getInstance().addApiKeyToRequest() + "&page=" + page;
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                ArrayList<Movie> movies = parseJsonToMoviesList(result);
//                Log.e(TAG, movie.toString());
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, movie.toString());
                callback.onSuccess(movies, totalPages);
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

    public void getMoviesList(String movieListType, final OnMoviesListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "movie/" + movieListType + ConfigManager.getInstance().addApiKeyToRequest();
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                ArrayList<Movie> movies = parseJsonToMoviesList(result);
//                Log.e(TAG, movie.toString());
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, movie.toString());
                callback.onSuccess(movies, totalPages);
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

    public void getMoviesList(String movieListType, int page, final OnMoviesListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "movie/" + movieListType + ConfigManager.getInstance().addApiKeyToRequest() + "&page=" + page;
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                ArrayList<Movie> movies = parseJsonToMoviesList(result);
//                Log.e(TAG, movie.toString());
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, movie.toString());
                callback.onSuccess(movies, totalPages);
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

    public void getSearchResultMoviesList(String searchText, final OnMoviesListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "search/movie" + ConfigManager.getInstance().addApiKeyToRequest() + "&query=" + searchText;
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().CANCELALLPENDINGREQUESTS();
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {

                ArrayList<Movie> movies = parseJsonToMoviesList(result);
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, movie.toString());
                callback.onSuccess(movies, totalPages);
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

    public void getNextPageSearchResultMoviesList(String searchText, int page, final OnMoviesListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "search/movie" + ConfigManager.getInstance().addApiKeyToRequest() + "&query=" + searchText + "&page=" + page;
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().CANCELALLPENDINGREQUESTS();
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {

                ArrayList<Movie> movies = parseJsonToMoviesList(result);
                int totalPages = 0;
                try {
                    totalPages = result.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e(TAG, movie.toString());
                callback.onSuccess(movies, totalPages);
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

    private ArrayList<Movie> parseJsonToMoviesList(JSONObject jsonMovies) {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            JSONArray moviesJsonArray = jsonMovies.getJSONArray("results");
            for (int i = 0; i < moviesJsonArray.length(); i++) {
                Movie resMovie = new Movie();
                resMovie.setId(moviesJsonArray.getJSONObject(i).getInt("id"));
                resMovie.setPosterImageUrl("https://image.tmdb.org/t/p/w185" + moviesJsonArray.getJSONObject(i).getString("poster_path"));
                resMovie.setTitle(moviesJsonArray.getJSONObject(i).getString("title"));
                resMovie.setReleaseDate(moviesJsonArray.getJSONObject(i).getString("release_date"));
                resMovie.setPlot(moviesJsonArray.getJSONObject(i).getString("overview"));
                resMovie.setReviewNbrs(moviesJsonArray.getJSONObject(i).getInt("vote_count"));
                resMovie.setRating(moviesJsonArray.getJSONObject(i).getDouble("vote_average"));
                resMovie.setLanguage(moviesJsonArray.getJSONObject(i).getString("original_language"));
                StringBuilder genres = new StringBuilder();
//                JSONArray genresArray = moviesJsonArray.getJSONObject(i).getJSONArray("genre_ids");
//                for (int j = 0; j < genresArray.length(); j++) {
//                    genres.append(((JSONObject) genresArray.get(j)).getInt("name")).append("|");
//                }
//                genres.deleteCharAt(genres.toString().length() - 1);
                //                    genres.append(((JSONObject) genresArray.get(j)).getInt("name")).append("|");
                resMovie.setGenres(genres.toString());
                movies.add(resMovie);
                Log.e(TAG, movies.toString());
            }

            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void getMovieVideosList(int id, final OnVideoListLoadedCallback callback) {
        String url = ConfigManager.getInstance().getAppRoot() + "movie/" + id + "/videos" + ConfigManager.getInstance().addApiKeyToRequest();
        Log.e(TAG, "onCreate: " + url);
        ApiManager.getsInstance().GET(url, new ApiServerCallback() {
            @Override
            public boolean onSuccess(JSONObject result) {
                ArrayList<Video> videos = parseJsonToVideosList(result);
//                Log.e(TAG, movie.toString());
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
