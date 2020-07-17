package Game;

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
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

abstract public class BaseRoom extends KeyAdapter implements GLEventListener {
    public PlayerLogic player;
    public float stepQuanity = 0.2f;
    public float camAngle = 2;
    public HealthBar healthBar;
    public static GLU glu = new GLU();
    public Texture leftWallTexture, rightWallTexture, frontWallTexture,
            backWallTexture, ceilingTexture, floorTexture;
    public float roomWidth,roomHeight,roomDepth;
    public float material[] = {0.8f, 0.8f, 0.8f, 1.0f};
    public boolean WIsPressed, SIsPressed, AIsPressed, DIsPressed, EIsPressed, QIsPressed,
            IIsPressed, KIsPressed, LIsPressed, JIsPressed, OIsPressed, UIsPressed;
    public static Frame frame = new Frame("");
    public static GLCanvas canvas = new GLCanvas();
    public static Animator animator = new Animator(canvas);
    public String roomName;
    public static List<List<float[]>> objects = new ArrayList<>();

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        player = new PlayerLogic(stepQuanity, camAngle);
        final GL2 gl = glAutoDrawable.getGL().getGL2();
        healthBar = new HealthBar();
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

        if (glAutoDrawable instanceof com.jogamp.newt.Window) {
            com.jogamp.newt.Window window = (Window) glAutoDrawable;
            window.addKeyListener(this);
        } else if (GLProfile.isAWTAvailable() && glAutoDrawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) glAutoDrawable;
            new AWTKeyAdapter(this, glAutoDrawable).addTo(comp);
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

    public void setRoomTextures(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
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

    abstract public void setTextures(GL2 gl);

    abstract public void loadObjects();

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        final GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        player.setLookAtPoint();
        gl.glLoadIdentity();  // Reset The View
        glu.gluLookAt(player.pos[0], player.pos[1], player.pos[2],//Specifies the position of the eye point.
                player.look[0], player.look[1], player.look[2], //Specifies the position of the reference point.
                player.yAxis[0], player.yAxis[1], player.yAxis[2]); //Specifies the direction of the up vector.

        drawRoom(gl);
        drawObjects(gl);
        updateObjectsList();
    }

    abstract public void updateObjectsList();

    abstract public void drawObjects(GL2 gl);

    public void drawHealtbBar(GL2 gl) {
        //set to ortho matrix to draw in 2d
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glOrtho(-0.5f, 10f, -10f, 0.5f, -1f, 1f);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        healthBar.drawHealthBar(gl);
        //return the PROJECTION matrix and then to vm
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glPopMatrix();
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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                exit(true);
                break;
            //player movement:
            case KeyEvent.VK_W:
                WIsPressed = true;
//                if(!CollisionCheck.isHit())/**
//                 here i need the corrdinates of all
//                 the boxes and skys
//                 and moving objects(in the moment) of
//                 this level in static local(in the class)
//                 - for doors arrows ,trophie, and lasers its diffeneret(not just blocking
//                 but changing the gameplay) , btw after the throphie goes up and there's fireworks for 10 seconds the system exits with good bye :)*/
                    player.move(0, 0, 11);
                break;
            case KeyEvent.VK_S:
                SIsPressed = true;
//                if(!CollisionCheck.isHit())
                    player.move(0, 0, -11);
                break;
            case KeyEvent.VK_D:
                DIsPressed = true;
                player.move(11, 0, 0);
                break;
            case KeyEvent.VK_A:
                AIsPressed = true;
                player.move(-11, 0, 0);
                break;
            case KeyEvent.VK_E:
                EIsPressed = true;
                player.move(0, 11, 0);
                break;
            case KeyEvent.VK_Q:
                QIsPressed = true;
                player.move(0, -11, 0);
                break;
            //camera movement:
            case KeyEvent.VK_I:
                IIsPressed = true;
                player.camMove(1, "X");
                break;
            case KeyEvent.VK_K:
                KIsPressed = true;
                player.camMove(-1, "X");
                break;
            case KeyEvent.VK_L:
                LIsPressed = true;
                player.camMove(-1, "Y");
                break;
            case KeyEvent.VK_J:
                JIsPressed = true;
                player.camMove(1, "Y");
                break;
            case KeyEvent.VK_O:
                OIsPressed = true;
                player.camMove(-1, "Z");
                break;
            case KeyEvent.VK_U:
                UIsPressed = true;
                player.camMove(1, "Z");
                break;
            case KeyEvent.VK_F1:
                F1Screen.show();
                break;
            default:
                break;
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
            case KeyEvent.VK_F2:
                exit(false);
                FourthRoom fr = new FourthRoom();
                //fr.start();
                break;
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

    public static void exit(boolean system) {
        animator.stop();
        frame.dispose();
        if (system) {
            System.exit(0);
        }
    }

    public BaseRoom getSelfObj() {
        return null;
    }

    public void start() {
        canvas.addGLEventListener(getSelfObj());
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
}
