package Collide;

import java.util.List;
import java.util.Vector;

public class CollisionCheck {

    /** main func
     * returns number for what to do next for each type of object:
     * 0 for no collisions
     * 1 for
     * 2 for
     * ...
     */
    public static int isHitAndInstruction(List<List<float[]>> objects, float[] currCoordinates){
//        check for each type of object if there was a collision
//        List<float[]> monkeys = objects.get(0);
//        List<float[]> coins = objects.get(1);
//        ...
        return 0;
    }

    /**
     * check collision between a ball/coin and a point
     */
    public static boolean isHit(BS bs, Vector<Float> point) {
        System.out.println("hit bs point");

        //calculate the distance from the center of the ball to the point
        float deltaX = (float)bs.getCenter().get(0) - (float)point.get(0);
        float deltaY = (float)bs.getCenter().get(1) - (float)point.get(1);
        float deltaZ = (float)bs.getCenter().get(2) - (float)point.get(2);
        float distance = (float) (Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));

        //if the distance shorter then the radius - then there is intersection
        if (distance < Math.pow(bs.getRadius(), 2)) {
            return true;
        }
        return false;
    }

    /**
     * check collision between 2 balls
     */
    public static boolean isHit(BS bs1, BS bs2) {
        System.out.println("hit bs bs");

        //calculate the distance between the 2 centers of the 2 balls
        float deltaX = (float)bs1.getCenter().get(0) - (float)bs2.getCenter().get(0);
        float deltaY = (float)bs1.getCenter().get(1) - (float)bs2.getCenter().get(1);
        float deltaZ = (float)bs1.getCenter().get(2) - (float)bs2.getCenter().get(2);
        float distance = (float) (Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));

        //if the distance shorter then the sum of the radius - then there is intersection
        if (distance < Math.pow(bs1.getRadius() + bs2.getRadius(), 2)) {
            return true;
        }
        return false;
    }

    /**
     * check collision between a box/arrow/laser/trophie/door and a point
     */
    public static boolean isHit(AABB box, Vector<Float> point) {

        //if the point inside the box, then there is intersection
        if (point.get(0) < box.getX_max() && point.get(0) > box.getX_min() &&
                point.get(1) < box.getY_max() && point.get(1) > box.getY_min() &&
                point.get(2) < box.getZ_max() && point.get(2) > box.getZ_min()) {
            System.out.println("hit box point");
            return true;
        }
        return false;
    }

    /**
     * check collision between a point and a sky/wall
     */
}



