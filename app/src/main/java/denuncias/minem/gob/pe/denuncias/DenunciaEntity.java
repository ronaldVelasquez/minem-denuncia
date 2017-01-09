package denuncias.minem.gob.pe.denuncias;

/**
 * Created by ronaldvelasquez on 7/01/17.
 */

public class DenunciaEntity {
    private String title, personName, description, photo;
    private Double laitude, longitude;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getLaitude() {
        return laitude;
    }

    public void setLaitude(Double laitude) {
        this.laitude = laitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
