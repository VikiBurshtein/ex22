package Collide;

import java.util.Vector;

import Mathematics.Calculations;

public class OBB extends Shape {

    private Vector<Float> center;

    //the box coordinates
    private Vector<Float> x;
    private Vector<Float> y;
    private Vector<Float> z;

    //

    public OBB (Vector<Float>[] vertices) {
        createCoordinate(vertices);
    }


    /**
     * create the coordinate of the box
     * @param vertices
     */
    public void createCoordinate(Vector<Float>[] vertices) {

        int len = vertices.length;
        float x_center = 0;
        float y_center = 0;
        float z_center = 0;
        Vector<Float> max_point_z = vertices[0];
        Vector<Float> max_point_y = vertices[0];
        Vector<Float> max_point_x = vertices[0];

        //go over the vertices
        for (int i = 0; i < len; i++) {

            //calculate the center of the box
            x_center += vertices[i].get(0);
            y_center += vertices[i].get(1);
            z_center += vertices[i].get(2);

            //calculate the max point of the box in the z value
            if (vertices[i].get(2) > max_point_z.get(2)) {
                max_point_z = vertices[i];
            }

            //calculate the max point of the box in the y value
            if (vertices[i].get(1) > max_point_y.get(1) &&
                    max_point_z != vertices[i]) {
                max_point_y = vertices[i];
            }

            //calculate the max point of the box in the x value
            if (vertices[i].get(0) > max_point_y.get(0) &&
                    max_point_z != vertices[i] && max_point_y != vertices[i]) {
                max_point_x = vertices[i];
            }
        }

        ///set the center
        x_center /= len;
        y_center /= len;
        z_center /= len;
        this.center.set(0, x_center);
        this.center.set(1, y_center);
        this.center.set(2, z_center);

        //find the first coordinate
        Vector<Float> first_coordinate = new Vector<Float>();
        first_coordinate.set(0, max_point_z.get(0) - x_center);
        first_coordinate.set(1, max_point_z.get(1) - y_center);
        first_coordinate.set(2, max_point_z.get(2) - z_center);

        //find the second coordinate
        Vector<Float> second_coordinate = new Vector<Float>();
        second_coordinate.set(0, max_point_y.get(0) - x_center);
        second_coordinate.set(1, max_point_y.get(1) - y_center);
        second_coordinate.set(2, max_point_y.get(2) - z_center);

        //find the third coordinate with cross product
        Vector<Float> third_coordinate = Calculations.crossProduct(first_coordinate, second_coordinate);

        //normalization the vectors
        first_coordinate = Calculations.normalization(first_coordinate);
        second_coordinate = Calculations.normalization(second_coordinate);
        third_coordinate = Calculations.normalization(third_coordinate);

        //set the box's coordinates
        x = third_coordinate;
        y = second_coordinate;
        z = first_coordinate;
    }


    public Vector<Float> getCenter() {
        return center;
    }


    public void setCenter(Vector<Float> center) {
        this.center = center;
    }

}