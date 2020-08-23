//Viki Burshtein 328684642
//Tomer Paz 315311365
package Game;

public class GameScore {

    public static int coins = 0;
    public static int life = 2;

    /* returns true if spent 3 coins */
    public static boolean changeScore(String command){
        if(command.equals("addCoin")){
            coins ++;
        }
        else if(command.equals("useCoins")){
            if(coins >= 3 && life < 2) {
                coins -= 3;
                return true;
            }
        }
        return false;
    }

    /*returns true iff game over*/
    public static boolean changeBar(String command){
        if(command.equals("addLife")){
            if(changeScore("useCoins")){
                life ++;
            }
        }
        else if(command.equals("damage")){
            if(life > 0){
                life --;
            }
            else{
                return true;
            }
        }
        return false;
    }

    public static void reset(){
        coins = 0;
        life = 2;
    }
}
