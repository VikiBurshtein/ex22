//Viki Burshtein 328684642
//Tomer Paz 315311365
package Collide;

import Game.BaseRoom;
import jogamp.graph.font.typecast.ot.table.GposTable;

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
     *  5 for ball collision
     *  6 for lasers collision
     *  7 for water collision(not on bridge)
     *  8 for square of spike collision
     *  9 for goblet table
     */
    public static int isHitAndInstruction(List<List<float[]>> objects, float[] whatsHere, String roomName){
        int whatToDo;
        whatToDo = 0;
        if(roomName.equals("firstRoom")){
            //check walls:
            if(
                    objects.get(4).get(0)[0] > whatsHere[0] ||
                            objects.get(5).get(0)[0] < whatsHere[0] ||
                            objects.get(6).get(0)[0] < whatsHere[1] ||
                            objects.get(7).get(0)[0] > whatsHere[1] ||
                            objects.get(8).get(0)[0] < whatsHere[2] ||
                            objects.get(9).get(0)[0] > whatsHere[2]
            )
            {
                whatToDo = 2;
            }
            //door check:
            if(isHitBox(whatsHere,objects.get(3).get(0),40,100,40)){
                whatToDo = 1;
            }
            //ball from monkey check:
            for(int i = 0; i < objects.get(2).size(); i++){
                if(isHitSphere(whatsHere,objects.get(2).get(i),50)){
                    whatToDo = 5;
                }
            }
            //monkey check:
            for(int i = 0; i < objects.get(1).size(); i++){
                if(isHitBox(whatsHere,objects.get(1).get(i),20,17,25)){
                    whatToDo = 4;
                }
            }
            //coins check:
            for(int i = 0; i < objects.get(0).size(); i++){
                if(BaseRoom.checkIfCoinExists(i)){
                    if(isHitSphere(whatsHere,objects.get(0).get(i),15)){
                        BaseRoom.removeCoin(i);
                        whatToDo = 3;
                    }
                }
            }
        }
        else if(roomName.equals("secondRoom")){
            //check walls:
            if(
                    objects.get(3).get(0)[0] > whatsHere[0] ||
                            objects.get(4).get(0)[0] < whatsHere[0] ||
                            objects.get(5).get(0)[0] < whatsHere[1] ||
                            objects.get(6).get(0)[0] > whatsHere[1] ||
                            objects.get(7).get(0)[0] < whatsHere[2] ||
                            objects.get(8).get(0)[0] > whatsHere[2]
            )
            {
                whatToDo = 2;
            }
            //door check:
            if(isHitBox(whatsHere,objects.get(2).get(0),40,100,15)){
                whatToDo = 1;
            }
            //water check:
            if((whatsHere[2] <= 350 && whatsHere[2] >= 40 && whatsHere[0] >= -90 && whatsHere[0] <= 200) ||
                    (whatsHere[0] >= 173 && whatsHere[0] <= 200 && whatsHere[2] >= -380 && whatsHere[2] <= 70) ||
                    (whatsHere[0] <= -170 && whatsHere[0] >= -200 && whatsHere[2] <= 395 && whatsHere[2] >= 0) ||
                    (whatsHere[0] <= 90 && whatsHere[0] >= -200 && whatsHere[2] <= -7 && whatsHere[2] >= -355) ||
                    (whatsHere[0] <= - 170)){
                whatToDo = 7;
            }
            //coins check:
            for(int i = 0; i < objects.get(0).size(); i++){
                if(BaseRoom.checkIfCoinExists(i)){
                    if(isHitSphere(whatsHere,objects.get(0).get(i),15)){
                        BaseRoom.removeCoin(i);
                        whatToDo = 3;
                    }
                }
            }
        }
        else if(roomName.equals("thirdRoom")){
            //check walls:
            if(
                    objects.get(4).get(0)[0] > whatsHere[0] ||
                            objects.get(5).get(0)[0] < whatsHere[0] ||
                            objects.get(6).get(0)[0] < whatsHere[1] ||
                            objects.get(7).get(0)[0] > whatsHere[1] ||
                            objects.get(8).get(0)[0] < whatsHere[2] ||
                            objects.get(9).get(0)[0] > whatsHere[2]
            )
            {
                whatToDo = 2;
            }
            //door check:
            if(isHitBox(whatsHere,objects.get(3).get(0),40,100,40)){
                whatToDo = 1;
            }
            //lasers check:
            //horizontal:
            for(int i = 0; i < objects.get(1).size(); i++){
                if(isHitBox(whatsHere,objects.get(1).get(i),1000,20,15)){
                    whatToDo = 6;
                }
            }
            //vertical:
            for(int i = 0; i < objects.get(2).size(); i++){
                if(isHitBox(whatsHere,objects.get(2).get(i),20,1000,15)){
                    whatToDo = 6;
                }
            }
            //coins check:
            for(int i = 0; i < objects.get(0).size(); i++){
                if(BaseRoom.checkIfCoinExists(i)){
                    if(isHitSphere(whatsHere,objects.get(0).get(i),15)){
                        BaseRoom.removeCoin(i);
                        whatToDo = 3;
                    }
                }
            }
        }
        else if(roomName.equals("fourthRoom")){
            //check walls:
            if(
                    objects.get(3).get(0)[0] > whatsHere[0] ||
                            objects.get(4).get(0)[0] < whatsHere[0] ||
                            objects.get(5).get(0)[0] < whatsHere[1] ||
                            objects.get(6).get(0)[0] > whatsHere[1] ||
                            objects.get(7).get(0)[0] < whatsHere[2] ||
                            objects.get(8).get(0)[0] > whatsHere[2]
            )
            {
                whatToDo = 2;
            }
            //spikes check:
            for(int i = 0; i < objects.get(1).size(); i++){
                if(isHitBox(whatsHere,objects.get(1).get(i),15,1000,35)){
                    BaseRoom.getSpikesUp(i);
                    whatToDo = 8;
                }
            }
            //coins check:
            for(int i = 0; i < objects.get(0).size(); i++){
                if(BaseRoom.checkIfCoinExists(i)){
                    if(isHitSphere(whatsHere,objects.get(0).get(i),15)){
                        BaseRoom.removeCoin(i);
                        whatToDo = 3;
                    }
                }
            }
            //goblet check:
            if(whatsHere[0] >= -40 && whatsHere[0] <= 40 && whatsHere[1] <= -25 && whatsHere[1] >= -100 && whatsHere[2] <= -135){
                whatToDo = 9;
            }
        }
        return whatToDo;
    }



    /**
     * check collision between a ball/coin/etc ... and a point
     */
    public static boolean isHitSphere(float[] point, float[] center, float radius) {
        float distX = Math.abs(point[0] - center[0]);
        float distY = Math.abs(point[1] - center[1]);
        float distZ = Math.abs(point[2] - center[2]);
        if((Math.pow(distX,2) + Math.pow(distY,2) + Math.pow(distZ,2)) <= Math.pow(radius,2)){
            return true;
        }
        return false;
    }


    /**
     *
     * check collision between a box(door, laser, etc ...) and a point
     */
    public static boolean isHitBox(float[] point, float[] center, float distX, float distY, float distZ) {
        if(Math.abs(point[0] - center[0]) <= distX &&
                Math.abs(point[1] - center[1]) <= distY &&
                Math.abs(point[2] - center[2]) <= distZ) {
            return true;
        }
        return false;
    }

}



