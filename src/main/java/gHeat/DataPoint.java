package gHeat;


public class DataPoint {

    private double x;
    private double y;
    private double weight;
    private Object data;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public DataPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }


}
