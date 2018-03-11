package pl.hacknarok.positivedevs.runit.entity;

public class Image {
    private String name;
    private String data;
    private double latitude;
    private double longitude;

    public Image(){
    }

    public Image(String name, String data){
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        String info = String.format("Image name = %s, data = %s", name, data);
        return info;
    }

}