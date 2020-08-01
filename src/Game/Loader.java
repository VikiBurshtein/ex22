package Game;

public class Loader {

    public static BaseRoom room;

    public static void main(String[] args){
        room = new FirstRoom();
        room.start();
    }

    public static void runNewRoom(String roomName){
        room.exit(false);
        room = null;
        if(roomName.equals("secondRoom")){
            room = new SecondRoom();
        }
        else if(roomName.equals("thirdRoom")){
            room = new ThirdRoom();
        }
        else if(roomName.equals("fourthRoom")){
            room = new FourthRoom();
        }
        room.start();
    }
}
