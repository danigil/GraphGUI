package main;

import api.GeoLocation;

public class Point3D implements GeoLocation {
    private double x,y,z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double distance(GeoLocation g) {
        return Math.sqrt(Math.pow(x-g.x(),2)+Math.pow(y-g.y(),2)+Math.pow(z-g.z(),2));
    }

    public Point3D clone(){
        return new Point3D(x,y,z);
    }
}
