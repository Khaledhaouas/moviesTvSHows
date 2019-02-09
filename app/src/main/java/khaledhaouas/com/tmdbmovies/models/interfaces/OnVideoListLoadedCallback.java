package khaledhaouas.com.tmdbmovies.models.interfaces;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.models.entities.Video;

public interface OnVideoListLoadedCallback {
    void onSuccess(ArrayList<Video> videos);

    void onError();
}
