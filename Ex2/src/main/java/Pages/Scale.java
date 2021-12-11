package Pages;

import api.GeoLocation;
import main.Point3D;

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

        //32.343434343 -> 100
        //33.0001


    }

    public Point3D scalePoint3D(GeoLocation scalePoint, GeoLocation leftCorner, GeoLocation rightCorner){
//        if(leftCorner.x()==rightCorner.x() && leftCorner.y()==rightCorner.y()){
//            new Point3D(scale(scalePoint.x(),scalePoint.x()+5,width,75,width-75),scale(scalePoint.y(),scalePoint.y()+5,height,75,height-75),0);
//        }
        return new Point3D(scale(scalePoint.x(),leftCorner.x(),rightCorner.x(),75,width-75),scale(scalePoint.y(),leftCorner.y(),rightCorner.y(),75,height-75),0);
    }

}
