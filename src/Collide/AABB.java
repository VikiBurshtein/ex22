//Viki Burshtein 328684642
//Tomer Paz 315311365
package Collide;

public class AABB extends Shape {
    private float x_max;
    private float y_max;
    private float z_max;
    private float x_min;
    private float y_min;
    private float z_min;

    public AABB(float x_max, float y_max, float z_max, float x_min, float y_min, float z_min) {
        this.setX_max(x_max);
        this.setY_max(y_max);
        this.setZ_max(z_max);
        this.setX_min(x_min);
        this.setY_min(y_min);
        this.setZ_min(z_min);
    }

    public float getZ_min() {
        return z_min;
    }

    public void setZ_min(float z_min) {
        this.z_min = z_min;
    }

    public float getY_min() {
        return y_min;
    }

    public void setY_min(float y_min) {
        this.y_min = y_min;
    }

    public float getX_min() {
        return x_min;
    }

    public void setX_min(float x_min) {
        this.x_min = x_min;
    }

    public float getZ_max() {
        return z_max;
    }

    public void setZ_max(float z_max) {
        this.z_max = z_max;
    }

    public float getY_max() {
        return y_max;
    }

    public void setY_max(float y_max) {
        this.y_max = y_max;
    }

    public float getX_max() {
        return x_max;
    }

    public void setX_max(float x_max) {
        this.x_max = x_max;
    }
}