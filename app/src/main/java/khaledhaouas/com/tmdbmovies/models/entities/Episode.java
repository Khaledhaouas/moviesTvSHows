package khaledhaouas.com.tmdbmovies.models.entities;

public class Episode {
    private int id;
    private String name;
    private String airDate;
    private String posterImage;
    private String overview;
    private int number;
    private int voteCount;
    private double rating;

    public Episode() {

    }

    public Episode(int id, String name, String airDate, String posterImage, String overview, int number, int voteCount, double rating) {
        this.id = id;
        this.name = name;
        this.airDate = airDate;
        this.posterImage = posterImage;
        this.overview = overview;
        this.number = number;
        this.voteCount = voteCount;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", airDate='" + airDate + '\'' +
                ", posterImage='" + posterImage + '\'' +
                ", overview='" + overview + '\'' +
                ", number=" + number +
                ", voteCount=" + voteCount +
                ", rating=" + rating +
                '}';
    }
}
