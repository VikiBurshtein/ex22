package Collide;

import java.util.List;
import java.util.Vector;

public class CollisionCheck {

    /** main func
     * returns number for what to do next for each type of object:
     *  0 for no collisions(blank air)
     *  1 for door collision
     *  2 for left wall/right wall/ceilling/floor/back wall/front wall collision
     *  3 for coin collision
     *  4 for monkey collision
     *  5 for arrow collision
     *  6 for lasers collision
     *  7 for water collision(not on bridge)
     *  8 for square of spike collision
     *  9 for goblet table
     */
    public static int isHitAndInstruction(List<List<float[]>> objects, float[] whatsHere, String roomName){
        int whatToDo = 0;
        if(roomName.equals("firstRoom")){

        }
        else if(roomName.equals("secondRoom")){

        }
        else if(roomName.equals("thirdRoom")){

        }
        else if(roomName.equals("fourthRoom")){

        }
        return whatToDo;
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



