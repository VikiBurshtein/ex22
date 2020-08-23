//Viki Burshtein 328684642
//Tomer Paz 315311365
package Collide;

import java.util.Vector;

/**
 * this class represent bounding sphere
 * @author Inbar Demuth
 *
 */
public class BS extends Shape{

    private Vector<Float> center;
    private float radius;

    public BS(Vector<Float> ballCenter, float ballRadius) {
        setCenter(ballCenter);
        setRadius(ballRadius);
    }

    public Vector<Float> getCenter() {
        return center;
    }

    private void setCenter(Vector<Float> center) {
        this.center = center;
    }

    public float getRadius() {
        return radius;
    }

    private void setRadius(float radius) {
        this.radius = radius;
    }
}