package khaledhaouas.com.tmdbmovies.models.entities;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Season {
    private int id;
    private int episodeCount;
    private String name;
    private String airDate;
    private String posterImage;
    private String overview;
    private int number;
    private ArrayList<Episode> episodes;

    public Season() {
        episodes = new ArrayList<>();
    }

    public Season(int id, int episodeCount, String name, String airDate, String posterImage, String overview, int number) {
        this();
        this.id = id;
        this.episodeCount = episodeCount;
        this.name = name;
        this.airDate = airDate;
        this.posterImage = posterImage;
        this.overview = overview;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    @Override
    public String toString() {
        return "Season{" +
                "id=" + id +
                ", episodeCount=" + episodeCount +
                ", name='" + name + '\'' +
                ", airDate='" + airDate + '\'' +
                ", posterImage='" + posterImage + '\'' +
                ", overview='" + overview + '\'' +
                ", number=" + number +
                ", episodes=" + episodes +
                '}';
    }
}
