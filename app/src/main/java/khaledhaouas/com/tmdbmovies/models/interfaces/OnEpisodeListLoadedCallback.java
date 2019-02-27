package khaledhaouas.com.tmdbmovies.models.interfaces;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.models.entities.Episode;

public interface OnEpisodeListLoadedCallback {
    void onSuccess(ArrayList<Episode> episodes);

    void onError();
}
