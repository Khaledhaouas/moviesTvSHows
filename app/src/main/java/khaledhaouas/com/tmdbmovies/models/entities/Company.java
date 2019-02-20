package khaledhaouas.com.tmdbmovies.models.entities;

import android.support.annotation.NonNull;

public class Company {
    private int id;
    private String name;
    private String logoImage;
    private String country;

    public Company() {
    }

    public Company(int id, String name, String logoImage, String country) {
        this.id = id;
        this.name = name;
        this.logoImage = logoImage;
        this.country = country;
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

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NonNull
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logoImage='" + logoImage + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
