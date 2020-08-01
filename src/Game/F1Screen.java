
package Game;

/** represent as object on center of the screen with visuality of Matrix "Green Rain"
 * will be not be considered room, but an object so it will be able to visualize on a running room.
 */

public class F1Screen {

    private static String instructions = "";

    public F1Screen(String roomName){
        if(roomName.equals("firstRoom")){
            instructions = "r1";
        }
        else if(roomName.equals("secondRoom")){
            instructions = "r2";
        }
        else if(roomName.equals("thirdRoom")){
            instructions = "r3";
        }
        else{//fourthRoom currently
            instructions = "r4";
        }
    }
    public static void show(){
        System.out.println(instructions);/*for now*/
    }

}