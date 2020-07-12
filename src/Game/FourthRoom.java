package Game;

import com.jogamp.newt.event.KeyEvent;

public class FourthRoom {

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                exit(true);
                break;
//            //player movement:
//            case KeyEvent.VK_W:
//                WIsPressed = true;
//                player.move(0,0,11);
//                break;
//            case KeyEvent.VK_S:
//                SIsPressed = true;
//                player.move(0,0,-11);
//                break;
//            case KeyEvent.VK_D:
//                DIsPressed = true;
//                player.move(11,0,0);
//                break;
//            case KeyEvent.VK_A:
//                AIsPressed = true;
//                player.move(-11,0,0);
//                break;
//            case KeyEvent.VK_E:
//                EIsPressed = true;
//                player.move(0,11,0);
//                break;
//            case KeyEvent.VK_Q:
//                QIsPressed = true;
//                player.move(0,-11,0);
//                break;
//            //camera movement:
//            case KeyEvent.VK_I:
//                IIsPressed = true;
//                player.camMove(1,"X");
//                break;
//            case KeyEvent.VK_K:
//                KIsPressed = true;
//                player.camMove(-1,"X");
//                break;
//            case KeyEvent.VK_L:
//                LIsPressed = true;
//                player.camMove(-1,"Y");
//                break;
//            case KeyEvent.VK_J:
//                JIsPressed = true;
//                player.camMove(1,"Y");
//                break;
//            case KeyEvent.VK_O:
//                OIsPressed = true;
//                player.camMove(-1,"Z");
//                break;
//            case KeyEvent.VK_U:
//                UIsPressed = true;
//                player.camMove(1,"Z");
//                break;
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
//            case KeyEvent.VK_UP:
//                WIsPressed = false;
//                break;
//            case KeyEvent.VK_DOWN:
//                SIsPressed = false;
//                break;
//            case KeyEvent.VK_LEFT:
//                AIsPressed = false;
//                break;
//            case KeyEvent.VK_RIGHT:
//                DIsPressed = false;
//                break;
//            //player movement:
//            case KeyEvent.VK_W:
//                WIsPressed = false;
//                break;
//            case KeyEvent.VK_S:
//                SIsPressed = false;
//                break;
//            case KeyEvent.VK_D:
//                DIsPressed = false;
//                break;
//            case KeyEvent.VK_A:
//                AIsPressed = false;
//                break;
//            case KeyEvent.VK_E:
//                EIsPressed = false;
//                break;
//            case KeyEvent.VK_Q:
//                QIsPressed = false;
//                break;
//            //camera movement:
//            case KeyEvent.VK_I:
//                IIsPressed = false;
//                break;
//            case KeyEvent.VK_K:
//                KIsPressed = false;
//                break;
//            case KeyEvent.VK_L:
//                LIsPressed = false;
//                break;
//            case KeyEvent.VK_J:
//                JIsPressed = false;
//                break;
//            case KeyEvent.VK_O:
//                OIsPressed = false;
//                break;
//            case KeyEvent.VK_U:
//                UIsPressed = false;
            case KeyEvent.VK_F3:
                exit(false);
                FirstRoomAndLoader f = new FirstRoomAndLoader();
                f.start();
                break;
            default:
                break;
        }
    }


    public static void start() {

    }


    public static void exit(boolean system) {
//        animator.stop();
//        frame.dispose();
        if(system){
            System.exit(0);
        }
    }
}
