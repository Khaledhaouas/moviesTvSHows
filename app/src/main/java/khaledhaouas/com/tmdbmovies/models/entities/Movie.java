package khaledhaouas.com.tmdbmovies.models.entities;

public class Movie {
    private int id;
    private String backgroundImageUrl;
    private String posterImageUrl;
    private String title;
    private String genres;
    private double rating;
    private int reviewNbrs;
    private String releaseDate;
    private int runTime;
    private String language;
    private String plot;

    public Movie() {
    }

    public Movie(int id, String backgroundImageUrl, String posterImageUrl, String title, String genres,
                 double rating, int reviewNbrs, String releaseDate, int runTime, String language, String plot) {
        this.id = id;
        this.backgroundImageUrl = backgroundImageUrl;
        this.posterImageUrl = posterImageUrl;
        this.title = title;
        this.genres = genres;
        this.rating = rating;
        this.reviewNbrs = reviewNbrs;
        this.releaseDate = releaseDate;
        this.runTime = runTime;
        this.language = language;
        this.plot = plot;
    }

    public Movie(String backgroundImageUrl, String posterImageUrl, String title, String genres,
                 double rating, int reviewNbrs, String releaseDate, int runTime, String language, String plot) {
        this.backgroundImageUrl = backgroundImageUrl;
        this.posterImageUrl = posterImageUrl;
        this.title = title;
        this.genres = genres;
        this.rating = rating;
        this.reviewNbrs = reviewNbrs;
        this.releaseDate = releaseDate;
        this.runTime = runTime;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
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

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", backgroundImageUrl='" + backgroundImageUrl + '\'' +
                ", posterImageUrl='" + posterImageUrl + '\'' +
                ", title='" + title + '\'' +
                ", genres='" + genres + '\'' +
                ", rating=" + rating +
                ", reviewNbrs=" + reviewNbrs +
                ", releaseDate='" + releaseDate + '\'' +
                ", runTime=" + runTime +
                ", language='" + language + '\'' +
                ", plot='" + plot + '\'' +
                '}';
    }
}
