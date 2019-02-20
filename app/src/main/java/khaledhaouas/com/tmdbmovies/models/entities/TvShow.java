package khaledhaouas.com.tmdbmovies.models.entities;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public class TvShow {
    private int id;
    private String backgroundImageUrl;
    private String posterImageUrl;
    private String title;
    private String genres;
    private double rating;
    private int reviewNbrs;
    private String firstEpDate;
    private int epRunTime;
    private String language;
    private String plot;
    private int seasonNbre;
    private int epNbre;
    private ArrayList<Season> seasons;
    private ArrayList<Company> networks;

    public TvShow() {
        seasons = new ArrayList<>();
        networks = new ArrayList<>();
    }

    public TvShow(int id, String backgroundImageUrl, String posterImageUrl, String title, String genres,
                  double rating, int reviewNbrs, String firstEpDate, int epRunTime, String language, String plot) {
        this();
        this.id = id;
        this.backgroundImageUrl = backgroundImageUrl;
        this.posterImageUrl = posterImageUrl;
        this.title = title;
        this.genres = genres;
        this.rating = rating;
        this.reviewNbrs = reviewNbrs;
        this.firstEpDate = firstEpDate;
        this.epRunTime = epRunTime;
        this.language = language;
        this.plot = plot;
    }

    public TvShow(String backgroundImageUrl, String posterImageUrl, String title, String genres,
                  double rating, int reviewNbrs, String firstEpDate, int epRunTime, String language, String plot) {
        this();
        this.backgroundImageUrl = backgroundImageUrl;
        this.posterImageUrl = posterImageUrl;
        this.title = title;
        this.genres = genres;
        this.rating = rating;
        this.reviewNbrs = reviewNbrs;
        this.firstEpDate = firstEpDate;
        this.epRunTime = epRunTime;
        this.language = language;
        this.plot = plot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public String getPosterImageUrl() {
        return posterImageUrl;
    }

    public void setPosterImageUrl(String posterImageUrl) {
        this.posterImageUrl = posterImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReviewNbrs() {
        return reviewNbrs;
    }

    public void setReviewNbrs(int reviewNbrs) {
        this.reviewNbrs = reviewNbrs;
    }

    public String getFirstEpDate() {
        return firstEpDate;
    }

    public void setFirstEpDate(String firstEpDate) {
        this.firstEpDate = firstEpDate;
    }

    public int getEpRunTime() {
        return epRunTime;
    }

    public void setEpRunTime(int epRunTime) {
        this.epRunTime = epRunTime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public int getSeasonNbre() {
        return seasonNbre;
    }

    public void setSeasonNbre(int seasonNbre) {
        this.seasonNbre = seasonNbre;
    }

    public int getEpNbre() {
        return epNbre;
    }

    public void setEpNbre(int epNbre) {
        this.epNbre = epNbre;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public ArrayList<Company> getNetworks() {
        return networks;
    }

    public void setNetworks(ArrayList<Company> networks) {
        this.networks = networks;
    }

    @NonNull
    @Override
    public String toString() {
        return "TvShow{" +
                "id=" + id +
                ", backgroundImageUrl='" + backgroundImageUrl + '\'' +
                ", posterImageUrl='" + posterImageUrl + '\'' +
                ", title='" + title + '\'' +
                ", genres='" + genres + '\'' +
                ", rating=" + rating +
                ", reviewNbrs=" + reviewNbrs +
                ", firstEpDate='" + firstEpDate + '\'' +
                ", epRunTime=" + epRunTime +
                ", language='" + language + '\'' +
                ", plot='" + plot + '\'' +
                ", seasonNbre=" + seasonNbre +
                ", epNbre=" + epNbre +
                ", seasons=" + seasons +
                ", networks=" + networks +
                '}';
    }
}
