package khaledhaouas.com.tmdbmovies.models.entities;

public class Credit {
    private String creditId;
    private String actorName;
    private String characterName;
    private int actorGender;
    private String actorProfilePicture;
    private int actorId;

    public Credit() {
    }

    public Credit(String creditId, String actorName, String characterName, int actorGender, String actorProfilePicture, int actorId) {
        this.creditId = creditId;
        this.actorName = actorName;
        this.characterName = characterName;
        this.actorGender = actorGender;
        this.actorProfilePicture = actorProfilePicture;
        this.actorId = actorId;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getActorGender() {
        return actorGender;
    }

    public void setActorGender(int actorGender) {
        this.actorGender = actorGender;
    }

    public String getActorProfilePicture() {
        return actorProfilePicture;
    }

    public void setActorProfilePicture(String actorProfilePicture) {
        this.actorProfilePicture = actorProfilePicture;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "creditId='" + creditId + '\'' +
                ", actorName='" + actorName + '\'' +
                ", characterName='" + characterName + '\'' +
                ", actorGender=" + actorGender +
                ", actorProfilePicture='" + actorProfilePicture + '\'' +
                ", actorId=" + actorId +
                '}';
    }
}
