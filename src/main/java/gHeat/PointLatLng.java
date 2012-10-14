package gHeat;


public class PointLatLng {

    private Object data;
    private double weight;
    private double longitude;
    private double latitude;



    public PointLatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PointLatLng(double latitude, double longitude, Object data, double weight) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.data = data;
        this.weight = weight;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
