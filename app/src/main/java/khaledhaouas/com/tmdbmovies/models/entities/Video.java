package khaledhaouas.com.tmdbmovies.models.entities;

public class Video {

    private String id;
    private String name;
    private String urlKey;
    private String type;

    public Video() {
    }

    public Video(String id, String name, String urlKey, String type) {
        this.id = id;
        this.name = name;
        this.urlKey = urlKey;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", urlKey='" + urlKey + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
