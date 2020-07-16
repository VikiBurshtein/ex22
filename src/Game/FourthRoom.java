package Game;//names ids

import Collide.CollisionCheck;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.media.opengl.fixedfunc.GLMatrixFunc;

public class FourthRoom extends KeyAdapter implements GLEventListener {
    private String roomName = "fourthRoom";
    private Texture leftWallTexture, rightWallTexture, frontWallTexture, backWallTexture, ceilingTexture, floorTexture,tableTexture, gobletTexture;
    private WavefrontObjectLoader_DisplayList tableModel, gobletModel;
    private PlayerLogic player;
    private float stepQuanity = 0.2f;
    private float camAngle = 2;
    private static GLU glu = new GLU();
    private static GLCanvas canvas = new GLCanvas();
    private static Frame frame = new Frame("Fourth Room");
    private static Animator animator = new Animator(canvas);
    private boolean WIsPressed, SIsPressed, AIsPressed, DIsPressed, EIsPressed, QIsPressed,
            IIsPressed, KIsPressed, LIsPressed, JIsPressed, OIsPressed, UIsPressed;
    private float material[] = {0.8f, 0.8f, 0.8f, 1.0f};
    private float position0[] = {10f, 0f, -5f, 1.0f};    // red light on the cubes from the top
    private float roomWidth = 200.0f;
    private float roomHeight = 100.0f;
    private float roomDepth = 400.0f;
    private float gobletRotation = 0.0f;
    private float gobletElevation = -70.0f;
    private boolean gobletIsUP = false;
    private static List<List<float[]>> objectsInTheRoom = new ArrayList<>();
    private static boolean immune = false;

    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        player.setLookAtPoint();
        gl.glLoadIdentity();  // Reset The View
        glu.gluLookAt(player.pos[0], player.pos[1], player.pos[2],//Specifies the position of the eye point.
                player.look[0], player.look[1], player.look[2], //Specifies the position of the reference point.
                player.yAxis[0], player.yAxis[1], player.yAxis[2]); //Specifies the direction of the up vector.

        drawRoom(gl);
        drawObjects(gl);
    }

    public void drawObjects(GL2 gl) {
        drawTable(gl);
        drawGoblet(gl);
    }

    public void gobletUp(GL2 gl) {
        gobletRotation = gobletRotation + 0.5f;
        if (gobletElevation < -25) {
            gobletElevation = gobletElevation + 0.1f;
        }
    }

    public void drawRoom(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        gl.glScalef(roomWidth, roomHeight, roomDepth);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, material, 0);

        //Back wall
        backWallTexture.bind(gl);
        gl.glBegin(GL2.GL_QUADS);
        gl.glNormal3f(0, 0, 1);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glEnd();

        //Front wall
        frontWallTexture.bind(gl);
        gl.glBegin(GL2.GL_QUADS);
        gl.glNormal3f(0, 0, -1);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glEnd();

        //Right wall
        rightWallTexture.bind(gl);
        gl.glBegin(GL2.GL_QUADS);
        gl.glNormal3f(1, 0, 0);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glEnd();

        //Left wall
        leftWallTexture.bind(gl);
        gl.glBegin(GL2.GL_QUADS);
        gl.glNormal3f(-1, 0, 0);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();

        //ceiling
        ceilingTexture.bind(gl);
        gl.glBegin(GL2.GL_QUADS);
        gl.glNormal3f(0, 1, 0);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glEnd();

        //floor
        floorTexture.bind(gl);
        gl.glBegin(GL2.GL_QUADS);
        gl.glNormal3f(0, -1, 0);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glFlush();
    }

    public void drawTable(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0, -100, -200.0f);
        gl.glScalef(15, 15, 15);
        tableTexture.bind(gl);
        tableModel.drawModel(gl);
        gl.glPopMatrix();
    }

    public void drawGoblet(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0, gobletElevation, -200.0f);
        gl.glScalef(5, 5, 5);
        gl.glRotatef(gobletRotation, 0.0f, gobletRotation, 0.0f);
        gobletTexture.bind(gl);
        gobletModel.drawModel(gl);
        gl.glPopMatrix();
        if (gobletIsUP) {
            gobletUp(gl);
        }
        gobletIsUP = true;
    }

    public void displayChanged(GLAutoDrawable gLDrawable,
                               boolean modeChanged, boolean deviceChanged) {
    }

    public void init(GLAutoDrawable drawable) {
        player = new PlayerLogic(stepQuanity, camAngle);
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 2.0f, 0.0f);    // Background
        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL2.GL_DEPTH_TEST);              // Enables Depth Testing
        gl.glDepthFunc(GL2.GL_LEQUAL);               // The Type Of Depth Testing To Do
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

        setTextures(gl);
        loadObjects();

        if (drawable instanceof com.jogamp.newt.Window) {
            com.jogamp.newt.Window window = (Window) drawable;
            window.addKeyListener(this);
        } else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) drawable;
            new AWTKeyAdapter(this, drawable).addTo(comp);
        }
        // Light
//        float	ambient[] = {0.1f,0.1f,0.1f,1.0f};
//        float	diffuse0[] = {1f,0f,0f,1.0f};
//
//        gl.glShadeModel(GL2.GL_SMOOTH);
//        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
//        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse0, 0);
//        gl.glEnable(GL2.GL_LIGHT0);
//
//        gl.glEnable(GL2.GL_LIGHTING);
//        WIsPressed = false;
//        SIsPressed = false;
//        AIsPressed = false;
//        DIsPressed = false;
//        EIsPressed = false;
//        QIsPressed = false;
//        IIsPressed = false;
//        KIsPressed = false;
//        LIsPressed = false;
//        JIsPressed = false;
//        OIsPressed = false;
//        UIsPressed = false;
    }

    public void setTextures(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
            //objects texture
            String goblet = "resources/" + roomName + "/objectTextures/goblet.jpg";
            gobletTexture = TextureIO.newTexture(new File(goblet), true);
            String table = "resources/" + roomName + "/objectTextures/table.jpg";
            tableTexture = TextureIO.newTexture(new File(table), true);

            //room texture
            String leftWall = "resources/" + roomName + "/roomTextures/leftWall.jpg";
            leftWallTexture = TextureIO.newTexture(new File(leftWall), true);
            String rightWall = "resources/" + roomName + "/roomTextures/rightWall.jpg";
            rightWallTexture = TextureIO.newTexture(new File(rightWall), true);
            String backWall = "resources/" + roomName + "/roomTextures/backWall.jpg";
            backWallTexture = TextureIO.newTexture(new File(backWall), true);
            String frontWall = "resources/" + roomName + "/roomTextures/frontWall.jpg";
            frontWallTexture = TextureIO.newTexture(new File(frontWall), true);
            String ceiling = "resources/" + roomName + "/roomTextures/ceiling.jpg";
            ceilingTexture = TextureIO.newTexture(new File(ceiling), true);
            String floor = "resources/" + roomName + "/roomTextures/floor.jpg";
            floorTexture = TextureIO.newTexture(new File(floor), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void loadObjects() {
        tableModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/table.obj");
        gobletModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/goblet.obj");
    }

    public void reshape(GLAutoDrawable drawable, int x,
                        int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        if (height <= 0) {
            height = 1;
        }
        float h = (float) width / (float) height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void keyPressed(KeyEvent e) {
        int moveType = 0;//0 for normal move, 1 for camera move, 2 for none
        float[] move = new float[3];
        float angle = 0;
        String axis = "";
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                exit(true);
                break;
            //player movement:
            case KeyEvent.VK_W:
                WIsPressed = true;
                move[0] = 0;
                move[1] = 0;
                move[2] = 11;
                break;
            case KeyEvent.VK_S:
                SIsPressed = true;
                move[0] = 0;
                move[1] = 0;
                move[2] = -11;
                break;
            case KeyEvent.VK_D:
                DIsPressed = true;
                move[0] = 11;
                move[1] = 0;
                move[2] = 0;
                break;
            case KeyEvent.VK_A:
                AIsPressed = true;
                move[0] = -11;
                move[1] = 0;
                move[2] = 0;
                break;
            case KeyEvent.VK_E:
                EIsPressed = true;
                move[0] = 0;
                move[1] = 11;
                move[2] = 0;
                break;
            case KeyEvent.VK_Q:
                QIsPressed = true;
                move[0] = 0;
                move[1] = -11;
                move[2] = 0;
                break;
            //camera movement:
            case KeyEvent.VK_I:
                IIsPressed = true;
                angle = 1;
                axis = "X";
                moveType = 1;
                break;
            case KeyEvent.VK_K:
                KIsPressed = true;
                angle = -1;
                axis = "X";
                moveType = 1;
                break;
            case KeyEvent.VK_L:
                LIsPressed = true;
                angle = -1;
                axis = "Y";
                moveType = 1;
                break;
            case KeyEvent.VK_J:
                JIsPressed = true;
                angle = 1;
                axis = "Y";
                moveType = 1;
                break;
            case KeyEvent.VK_O:
                OIsPressed = true;
                angle = -1;
                axis = "Z";
                moveType = 1;
                break;
            case KeyEvent.VK_U:
                UIsPressed = true;
                angle = 1;
                axis = "Z";
                moveType = 1;
                break;
            //instructions:
            case KeyEvent.VK_F1:
                moveType = 2;
                break;
            default:
                break;
        }
        if(moveType == 0){
            float[] futurePlace;
            futurePlace = player.getFuturePlaceOfMove(move[0],move[1],move[2]);
            int whatToDo = CollisionCheck.isHitAndInstruction(objectsInTheRoom, futurePlace);
            switch (whatToDo){
                case 0:
                    //no collisions:
                    player.move(move[0],move[1],move[2]);
                    break;
                case 1:
                    //hurt
                    if(!immune){
                        //immune = true; - delete comment
                        //hurt - make immune for 1 second and make thread that makes that false back after 1 sec.
                        //show hurt and down one life:
                    }
                    break;
                case 2:
                    //

                    break;
                default:
                    break;
            }
        }
        else if(moveType == 1){
            player.camMove(angle,axis);
        }
        else{//2
            F1Screen.show();
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                WIsPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                SIsPressed = false;
                break;
            case KeyEvent.VK_LEFT:
                AIsPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                DIsPressed = false;
                break;
            //player movement:
            case KeyEvent.VK_W:
                WIsPressed = false;
                break;
            case KeyEvent.VK_S:
                SIsPressed = false;
                break;
            case KeyEvent.VK_D:
                DIsPressed = false;
                break;
            case KeyEvent.VK_A:
                AIsPressed = false;
                break;
            case KeyEvent.VK_E:
                EIsPressed = false;
                break;
            case KeyEvent.VK_Q:
                QIsPressed = false;
                break;
            //camera movement:
            case KeyEvent.VK_I:
                IIsPressed = false;
                break;
            case KeyEvent.VK_K:
                KIsPressed = false;
                break;
            case KeyEvent.VK_L:
                LIsPressed = false;
                break;
            case KeyEvent.VK_J:
                JIsPressed = false;
                break;
            case KeyEvent.VK_O:
                OIsPressed = false;
                break;
            case KeyEvent.VK_U:
                UIsPressed = false;
            case KeyEvent.VK_F3:
                exit(false);
                FirstRoomAndLoader f = new FirstRoomAndLoader();
                f.start();
                break;
            case KeyEvent.VK_F4:
                Coin.useCoin();
                break;
            default:
                break;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void exit(boolean system) {
        if (system) {
            System.exit(0);
        }
    }

    public static void start() {
        canvas.addGLEventListener(new FourthRoom());
        frame.add(canvas);
        frame.setSize(3000, 2000);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        frame.setVisible(true);
        animator.start();
        canvas.requestFocus();
    }

    public void dispose(GLAutoDrawable arg0) {
    }
}
