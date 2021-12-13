package Pages;

import api.GeoLocation;
import main.Point3D;

/** This class can take a certain double and scale it up or down according to bounds set by the developer.
 *  We use it to scale all the points in our graph in such a way that the whole graph will be visible on the screen at once.
 */
public class Scale {

    double width;
    double height;

    public Scale(double width,double height){
        this.width=width;
        this.height=height;
    }

    /**
     *
     * @param data denote some data to be scaled
     * @param r_min the minimum of the range of your data
     * @param r_max the maximum of the range of your data
     * @param t_min the minimum of the range of your desired target scaling
     * @param t_max the maximum of the range of your desired target scaling
     * @return
     */
    public double scale(double data, double r_min, double r_max,
                         double t_min, double t_max)
    {
        double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
        return res;
    }

    /**
     * This function takes a Point3d object and scales its 3 variables, according to limit-denoting points passed in the arguments.
     * @param scalePoint
     * @param leftCorner
     * @param rightCorner
     * @return
     */
    public Point3D scalePoint3D(GeoLocation scalePoint, GeoLocation leftCorner, GeoLocation rightCorner){
        return new Point3D(scale(scalePoint.x(),leftCorner.x(),rightCorner.x(),75,width-75),scale(scalePoint.y(),leftCorner.y(),rightCorner.y(),75,height-75),0);
    }

}
