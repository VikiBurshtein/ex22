//Viki Burshtein 328684642
//Tomer Paz 315311365
package Game;

import Collide.CollisionCheck;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.awt.TextRenderer;
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
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

abstract public class BaseRoom extends KeyAdapter implements GLEventListener {
    public PlayerLogic player;
    private static boolean didNotEnd = true;
    public float stepQuanity = 0.2f;
    public float camAngle = 2;
    public HealthBar healthBar;
    public F1Screen f1Screen;
    public Flash flash;
    public Win winScreen;
    public RoomNameAndCoins roomNameAndCoins;
    public Panel panel;
    public boolean showF1 = false;
    public Texture coinTexture, leftWallTexture, rightWallTexture, frontWallTexture,
            backWallTexture, ceilingTexture, floorTexture;
    public float roomWidth, roomHeight, roomDepth;
    public boolean WIsPressed, SIsPressed, AIsPressed, DIsPressed, EIsPressed, QIsPressed,
            IIsPressed, KIsPressed, LIsPressed, JIsPressed, OIsPressed, UIsPressed;
    public WavefrontObjectLoader_DisplayList coinModel;
    public static GLCanvas canvas;
    public static Animator animator;
    public static List<List<float[]>> objects;
    public static GLU glu;
    public static Frame frame;
    protected boolean immune = false;
    public ObjectsForCollision coins = new ObjectsForCollision();
    public static List<Boolean> coinsBoolean = new ArrayList<>();
    public boolean gobletRises = false;
    public boolean showFlash = false;

    public ObjectsForCollision leftWall, rightWall, ceiling, floor, frontWall, backWall;
    public ObjectsForCollision goblets = new ObjectsForCollision();
    public ObjectsForCollision tables = new ObjectsForCollision();
    public static ObjectsForCollision spikesForDrawing = new ObjectsForCollision();
    public ObjectsForCollision spikesForCollision = new ObjectsForCollision();

    public String roomName;
    public String roomNameToShow;

    float color[] = {1f, 1f, 1f, 1f};
    float ambient[] = {0.05f, 0.05f, 0.05f, 1.0f};
    float position[] = {0,0,1,0};

    TextRenderer renderer;

    /**
     *
     */


    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

        final GL2 gl = glAutoDrawable.getGL().getGL2();

        healthBar = new HealthBar();
        f1Screen = new F1Screen(roomName);
        winScreen = new Win();
        flash = new Flash();
        roomNameAndCoins = new RoomNameAndCoins();
        panel = new Panel();

        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 2.0f, 0.0f);    // Background
        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL2.GL_DEPTH_TEST);              // Enables Depth Testing
        gl.glDepthFunc(GL2.GL_LEQUAL);               // The Type Of Depth Testing To Do
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

        //light
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);

        setPlayer();
        setTextures(gl);
        loadObjects();

        renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 36));

        setInstructions();
        if (glAutoDrawable instanceof com.jogamp.newt.Window) {
            com.jogamp.newt.Window window = (Window) glAutoDrawable;
            window.addKeyListener(this);
        } else if (GLProfile.isAWTAvailable() && glAutoDrawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) glAutoDrawable;
            new AWTKeyAdapter(this, glAutoDrawable).addTo(comp);
        }

        WIsPressed = false;
        SIsPressed = false;
        AIsPressed = false;
        DIsPressed = false;
        EIsPressed = false;
        QIsPressed = false;
        IIsPressed = false;
        KIsPressed = false;
        LIsPressed = false;
        JIsPressed = false;
        OIsPressed = false;
        UIsPressed = false;
    }

    public void setInstructions() {
        F1Screen.instructions = new ArrayList() {{
            add("");
            add("");
            add("Welcome to our Computational Graphics Escape room!");
            add("");
            add("Objective: You need to get to the goblet!");
            add("");
            add("Your HP bar is on the left, you have 3 lives - for each hit you will be immune for 7 seconds.");
            add("");
            add("Your score is on the right, for each coin you get 1 score; 3 scores trades for one life :) collect the coins!");
            add("");
            add("");
            add("Rooms guide:");
            add("First room: The starting room. avoid the bullets and get to the door!");
            add("Second room: You must move onto the bridge straight to the door and avoid the water at all cost.");
            add("Third room: Be careful of the Lasers! they can harm you. go fast to the door!");
            add("Fourth room: OHH hey, the floor may be spikey... it will surprise you :( don't waste time, go to the Goblet!");
            add("");
            add("");
            add("Special buttons:");
            add("F2: Pass the current room and move straight to the next one.");
            add("F3: Restart the game. everything will reset.");
            add("F4: Restore life. costs 3 scores.");
            add("");
            add("");
            add("Goodluck explorer!");
        }};
    }

    public void setRoomTextures(GL2 gl) {
        coinModel = new WavefrontObjectLoader_DisplayList("basicObjects/objects/coin.obj");
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
            String coin = "resources/basicObjects/textures/coin.jpg";
            coinTexture = TextureIO.newTexture(new File(coin), true);
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

    abstract public void setPlayer();

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    //remove coin by index
    public static void removeCoin(int index) {
        coinsBoolean.set(index, false);
    }

    public static boolean checkIfCoinExists(int index) {
        if (coinsBoolean.get(index) == true) {
            return true;
        }
        return false;
    }

    //goblet rises, game ends
    public void rise() {
        gobletRises = true;
    }

    //by index
    public static void getSpikesUp(int index) {
        spikesForDrawing.moveOneObjectUpByIndex(index, -100);
    }

    //by coordinates
    public void getSpikesUp(float[] coordinates) {
        spikesForDrawing.moveOneObjectUpByCoordinates(coordinates, -100);
    }

    public void startFlash() {
        showFlash = true;
    }

    public void stopFlash() {
        showFlash = false;
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        final GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        player.setLookAtPoint();
        gl.glLoadIdentity();  // Reset The View

        //light
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, FloatBuffer.wrap(ambient));
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, FloatBuffer.wrap(color));
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, FloatBuffer.wrap(color));
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, FloatBuffer.wrap(position));

        glu.gluLookAt(player.pos[0], player.pos[1], player.pos[2],//Specifies the position of the eye point.
                player.look[0], player.look[1], player.look[2], //Specifies the position of the reference point.
                player.yAxis[0], player.yAxis[1], player.yAxis[2]); //Specifies the direction of the up vector.

        drawRoom(gl);
        if (showF1) {
            drawF1(gl);
            renderer.beginRendering(3000, 2000);
            int enter = 40;
            for (int i = 0; i < F1Screen.instructions.size(); i++) {
                renderer.draw(F1Screen.instructions.get(i), 350, 1700 - (enter * i));
            }
            renderer.endRendering();
            gl.glPopAttrib();
        } else if (showFlash) {
            drawFlash(gl);
        } else {
            updateObjectsList();
            drawPanel(gl);
            drawObjects(gl);
            drawHealtbBar(gl);
            renderer.beginRendering(3000, 2000);
            renderer.draw(new Integer(GameScore.coins).toString(), 2800, 1900);
            renderer.endRendering();
            gl.glPopAttrib();
            drawRoomNameAndCoins(gl);
            renderer.beginRendering(3000, 2000);
            renderer.draw(roomNameToShow, 2700, 1950);
            renderer.endRendering();
            gl.glPopAttrib();
        }
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
        healthBar.drawHealthBar(gl, new Integer(GameScore.life));
        //return the PROJECTION matrix and then to vm
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glPopMatrix();
    }

    public void drawF1(GL2 gl) {
        //set to ortho matrix to draw in 2d
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glOrtho(-0.5f, 10f, -10f, 0.5f, -1f, 1f);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        f1Screen.drawF1(gl);
        //return the PROJECTION matrix and then to vm
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glPopMatrix();
    }

    public void drawFlash(GL2 gl) {
        //set to ortho matrix to draw in 2d
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glOrtho(-0.5f, 10f, -10f, 0.5f, -1f, 1f);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        flash.drawFlash(gl);
        //return the PROJECTION matrix and then to vm
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glPopMatrix();
    }

    public void drawRoomNameAndCoins(GL2 gl) {
        //set to ortho matrix to draw in 2d
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glOrtho(-0.5f, 10f, -10f, 0.5f, -1f, 1f);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        roomNameAndCoins.drawRoomNameAndCoins(gl);
        //return the PROJECTION matrix and then to vm
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glPopMatrix();

    }

    public void drawPanel(GL2 gl) {
        //set to ortho matrix to draw in 2d
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glOrtho(-0.5f, 10f, -10f, 0.5f, -1f, 1f);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        panel.drawRoomNameAndCoins(gl);
        //return the PROJECTION matrix and then to vm
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glPopMatrix();
    }


    public void drawRoom(GL2 gl) {
        gl.glEnable(GL2.GL_NORMALIZE);

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        gl.glScalef(roomWidth, roomHeight, roomDepth);

        float m_color[] = {1f, 1f, 1f, 1f};
        float[] s_color = {1f, 1f, 1f, 1f};
        float[] shininess = {120.0f};


        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, m_color, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, FloatBuffer.wrap(s_color));
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, gl.GL_SHININESS, FloatBuffer.wrap(shininess));


        //Back wall
        backWallTexture.bind(gl);
        gl.glBegin(GL2.GL_QUADS);
        gl.glNormal3f(0, 0, -1);
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
        gl.glNormal3f(0, 0, 1);
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
        gl.glNormal3f(-1, 0, 0);
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
        gl.glNormal3f(1, 0, 0);
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
        gl.glNormal3f(0, -1, 0);
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
        gl.glNormal3f(0, 1, 0);
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

    public void drawCoins(GL2 gl) {
        for (int i = 0; i < coins.getSize(); i++) {
            if (coinsBoolean.get(i)) {
                drawOneCoin(gl, coins.getObject(i), i);
            }
            coins.rotateOneBy(3, i);
        }
    }

    public void drawOneCoin(GL2 gl, float[] coordinates, int index) {
        gl.glPushMatrix();
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(5, 5, 5);
        gl.glRotatef(coins.getRotation(index), 90, 90, 90);
        coinTexture.bind(gl);
        coinModel.drawModel(gl);
        gl.glPopMatrix();
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            exit(true);
        }
        if(!didNotEnd)
            return;
        boolean hit = false;
        boolean startSecond = false;
        float[] future;
        boolean moved = false;
        int instruction = 0;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                exit(true);
                break;
            //player movement:
            case KeyEvent.VK_W:
                moved = true;
                WIsPressed = true;
                future = player.getFuturePlaceOfMove(0,0,11);
                instruction = CollisionCheck.isHitAndInstruction(objects,future,roomName);
                break;
            case KeyEvent.VK_S:
                moved = true;
                SIsPressed = true;
                future = player.getFuturePlaceOfMove(0,0,-11);
                instruction = CollisionCheck.isHitAndInstruction(objects,future,roomName);
                break;
            case KeyEvent.VK_D:
                moved = true;
                DIsPressed = true;
                future = player.getFuturePlaceOfMove(11,0,0);
                instruction = CollisionCheck.isHitAndInstruction(objects,future,roomName);
                break;
            case KeyEvent.VK_A:
                moved = true;
                AIsPressed = true;
                future = player.getFuturePlaceOfMove(-11,0,0);
                instruction = CollisionCheck.isHitAndInstruction(objects,future,roomName);
                break;
            case KeyEvent.VK_E:
                moved = true;
                EIsPressed = true;
                future = player.getFuturePlaceOfMove(0,11,0);
                instruction = CollisionCheck.isHitAndInstruction(objects,future,roomName);
                if(roomName.equals("secondRoom")){
                    instruction = 2;
                }
                break;
            case KeyEvent.VK_Q:
                moved = true;
                QIsPressed = true;
                future = player.getFuturePlaceOfMove(0,-11,0);
                instruction = CollisionCheck.isHitAndInstruction(objects,future,roomName);
                if(roomName.equals("secondRoom")){
                    instruction = 2;
                }
                break;
            //camera movement:
            case KeyEvent.VK_I:
                IIsPressed = true;
                if(roomName.equals("secondRoom")){
                    break;
                }
                player.camMove(1, "X");
                break;
            case KeyEvent.VK_K:
                KIsPressed = true;
                if(roomName.equals("secondRoom")){
                    break;
                }
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
                if(roomName.equals("secondRoom")){
                    break;
                }
                player.camMove(-1, "Z");
                break;
            case KeyEvent.VK_U:
                UIsPressed = true;
                if(roomName.equals("secondRoom")){
                    break;
                }
                player.camMove(1, "Z");
                break;
            case KeyEvent.VK_F1:
                showF1 = true;
                break;
            default:
                break;
        }
        if(moved){
//            System.out.println("X: " + player.pos[0] + " Y: " + player.pos[1] + " Z: " + player.pos[2]);
//            System.out.println("WhatToDo = " + instruction);
            float[] move = new float[3];
            if(WIsPressed){
                move[0] = 0;
                move[1] = 0;
                move[2] = 11;
            }
            else if(SIsPressed){
                move[0] = 0;
                move[1] = 0;
                move[2] = -11;
            }
            else if(DIsPressed){
                move[0] = 11;
                move[1] = 0;
                move[2] = 0;
            }
            else if(AIsPressed){
                move[0] = -11;
                move[1] = 0;
                move[2] = 0;
            }
            else if(EIsPressed){
                move[0] = 0;
                move[1] = 11;
                move[2] = 0;
            }
            else if(QIsPressed){
                move[0] = 0;
                move[1] = -11;
                move[2] = 0;
            }
            if(instruction == 0){
                player.move(move[0],move[1],move[2]);
            }
            else if(instruction == 1){
                advance();
            }
            else if(instruction == 2){
                //just do - nothing.
            }
            else if(instruction == 3){
                changeScore("addCoin");
                player.move(move[0],move[1],move[2]);
            }
            else if(instruction == 4 || instruction == 8){
                hit = true;
                //block so dont move
            }
            else if(instruction == 5 || instruction == 6){
                hit = true;
                player.move(move[0],move[1],move[2]);
            }
            else if(instruction == 7){
                hit = true;
                startSecond = true;
            }
            else if(instruction == 9){
                rise();
                didNotEnd = false;
            }
        }
        if(hit && !immune){
            immune = true;
            startFlash();
            new Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            stopFlash();
                        }
                    },
                    1500
            );
            if(changeBar("damage")){
                GameScore.reset();
                new Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                Loader.runNewRoom("firstRoom");
                            }
                        },
                        2000
                );
                return;
            }
            if(startSecond){
                new Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                Loader.runNewRoom("secondRoom");
                            }
                        },
                        2000
                );
                return;
            }
            new Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            immune = false;
                        }
                    },
                    7000
            );
        }
    }

    public void keyReleased(KeyEvent e) {
        if(!didNotEnd)
            return;
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
                break;
            case KeyEvent.VK_F1:
                showF1 = false;
                break;
            case KeyEvent.VK_F2:
                advance();
                break;
            case KeyEvent.VK_F3:
                GameScore.reset();
                Loader.runNewRoom("firstRoom");
                break;
            case KeyEvent.VK_F4:
                changeBar("addLife");
                break;
            default:
                break;
        }
    }

    public void updateScoreAndBarView() {
        int currLife = GameScore.life;//2 = 100%, 1 = 50%, 0 = 0%
        int currCoins = GameScore.coins;
        //viki update them in VIEW
    }

    public void changeScore(String command) {
        GameScore.changeScore(command);
        int currCoins = GameScore.coins;
        updateScoreAndBarView();
    }

    /*returns true iff we need to restart */
    public boolean changeBar(String command) {
        boolean endGame = GameScore.changeBar(command);
        if (!endGame) {
            updateScoreAndBarView();
        }
        return endGame;
    }

    public void exit(boolean system) {
        animator.stop();
        frame.remove(canvas);
        frame.dispose();
        restart();
        if (system) {
            System.exit(0);
        }
    }

    public static void restart() {
        glu = null;
        frame = null;
        canvas = null;
        animator = null;
        objects = null;
    }

    public void advance(){
        if (roomName.equals("firstRoom")) {
            Loader.runNewRoom("secondRoom");
        } else if (roomName.equals("secondRoom")) {
            Loader.runNewRoom("thirdRoom");
        } else if (roomName.equals("thirdRoom")) {
            Loader.runNewRoom("fourthRoom");
        }
    }

    public void start() {
        canvas.addGLEventListener(this);
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
