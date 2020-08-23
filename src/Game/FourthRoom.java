//Viki Burshtein 328684642
//Tomer Paz 315311365
package Game;

import com.jogamp.newt.Window;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FourthRoom extends BaseRoom {
    private Texture tableTexture, gobletTexture, spikesTexture;
    private WavefrontObjectLoader_DisplayList tableModel, gobletModel, spikesModel;
    private float position0[] = {10f, 0f, -5f, 1.0f};    // red light on the cubes from the top
    private float gobletRotation = 0.0f;
    private float gobletElevation = -70.0f;
    private boolean showWin = false;
    private Win winningScreen;

    FourthRoom() {
        roomName = "fourthRoom";
        roomNameToShow = "Fourth Room";
        roomWidth = 200.0f;
        roomHeight = 100.0f;
        roomDepth = 400.0f;
        canvas = new GLCanvas();
        animator = new Animator(canvas);
        objects = new ArrayList<>();
        glu = new GLU();
        frame = new Frame("");
        initializeCoinsCoordinates();
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        final GL2 gl = glAutoDrawable.getGL().getGL2();
        healthBar = new HealthBar();
        f1Screen = new F1Screen(roomName);
        winScreen = new Win();
        roomNameAndCoins = new RoomNameAndCoins();
        panel = new Panel();
        winningScreen = new Win();
        flash = new Flash();
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
        //if player won
        if(showWin){
            drawWinningScreen(gl);
        }else if(showFlash) {
            drawFlash(gl);
        } else {
            if(showF1) {
                drawF1(gl);
                renderer.beginRendering(3000, 2000);
                int enter = 40;
                for(int i=0; i<F1Screen.instructions.size(); i++){
                    renderer.draw(F1Screen.instructions.get(i), 300, 1700 - (enter * i));
                }
                renderer.endRendering();
                gl.glPopAttrib();
            }else{
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
    }

    public void drawWinningScreen(GL2 gl) {
        //set to ortho matrix to draw in 2d
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glOrtho(-0.5f, 10f, -10f, 0.5f, -1f, 1f);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        winScreen.drawWin(gl);
        //return the PROJECTION matrix and then to vm
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glPopMatrix();
    }

    public void drawObjects(GL2 gl) {
        drawTable(gl);
        drawGoblet(gl);
        drawSpikes(gl);
        drawCoins(gl);
    }

    public void drawTable(GL2 gl) {
        for (int i = 0; i < tables.getSize(); i++) {
            drawOneTable(gl, tables.getObject(i));
        }
    }

    public void drawGoblet(GL2 gl) {
        for (int i = 0; i < goblets.getSize(); i++) {
            drawOneGoblet(gl, goblets.getObject(i));
        }
        goblets.rotateAllBy(1);
    }

    public void drawSpikes(GL2 gl) {
        for (int i = 0; i < spikesForDrawing.getSize(); i++) {
            drawOneSpike(gl, spikesForDrawing.getObject(i));
        }
    }

    public void drawOneSpike(GL2 gl, float[] coordinates) {
        gl.glPushMatrix();
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(15, 15, 15);
        spikesTexture.bind(gl);
        spikesModel.drawModel(gl);
        gl.glPopMatrix();
    }

    public void drawOneTable(GL2 gl, float[] coordinates) {
        gl.glPushMatrix();
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(15, 15, 15);
        tableTexture.bind(gl);
        tableModel.drawModel(gl);
        gl.glPopMatrix();
    }

    public void drawOneGoblet(GL2 gl, float[] coordinates) {
        gl.glPushMatrix();
        if (gobletRises) {
            coordinates = goblets.moveObject(coordinates, 0, 0.2f, 0);
            if(coordinates[1] > -20){
                gobletRises = false;
                showWin = true;
            }
        }
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(5, 5, 5);
        if (gobletRises) {
            gl.glRotatef(goblets.getRotation(0), 0, goblets.getRotation(0), 0);
        }
        gobletTexture.bind(gl);
        gobletModel.drawModel(gl);
        gl.glPopMatrix();
    }

    public void setTextures(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
            String goblet = "resources/" + roomName + "/objectTextures/goblet.jpg";
            gobletTexture = TextureIO.newTexture(new File(goblet), true);
            String table = "resources/" + roomName + "/objectTextures/table.jpg";
            tableTexture = TextureIO.newTexture(new File(table), true);
            String spikes = "resources/" + roomName + "/objectTextures/spikes.jpg";
            spikesTexture = TextureIO.newTexture(new File(spikes), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        setRoomTextures(gl);
    }

    public void initializeWallsCoordinates() {
        leftWall = new ObjectsForCollision();
        rightWall = new ObjectsForCollision();
        ceiling = new ObjectsForCollision();
        floor = new ObjectsForCollision();
        frontWall = new ObjectsForCollision();
        backWall = new ObjectsForCollision();
        leftWall.addObject(new float[]{-200, 0, 0});
        rightWall.addObject(new float[]{200, 0, 0});
        ceiling.addObject(new float[]{100, 0, 0});
        floor.addObject(new float[]{-100, 0, 0});
        frontWall.addObject(new float[]{-400, 0, 0});
        backWall.addObject(new float[]{400, 0, 0});
    }

    public void initializeGobletsCoordinates() {
        goblets.addObject(new float[]{0, -70, -200.0f});
    }

    public void initializeTablesCoordinates() {
        tables.addObject(new float[]{0, -100, -200});
    }

    public void initializeSpikesCoordinates(){
        float spikesHeightToDraw = -400;
        spikesForDrawing.addObject(new float[]{-100, spikesHeightToDraw, 350});
        spikesForDrawing.addObject(new float[]{100, spikesHeightToDraw, 350});
        spikesForDrawing.addObject(new float[]{-50, spikesHeightToDraw, 250});
        spikesForDrawing.addObject(new float[]{50, spikesHeightToDraw, 250});
        spikesForDrawing.addObject(new float[]{0, spikesHeightToDraw, 150});
        spikesForDrawing.addObject(new float[]{-150, spikesHeightToDraw, 50});
        spikesForDrawing.addObject(new float[]{-50, spikesHeightToDraw, 50});
        spikesForDrawing.addObject(new float[]{50, spikesHeightToDraw, 50});
        spikesForDrawing.addObject(new float[]{150, spikesHeightToDraw, 50});
        spikesForDrawing.addObject(new float[]{-100, spikesHeightToDraw, -50});
        spikesForDrawing.addObject(new float[]{100, spikesHeightToDraw, -50});
        spikesForDrawing.addObject(new float[]{-50, spikesHeightToDraw, -150});
        spikesForDrawing.addObject(new float[]{50, spikesHeightToDraw, -150});

        spikesForCollision.addObject(new float[]{-100, -100, 350});
        spikesForCollision.addObject(new float[]{100, -100, 350});
        spikesForCollision.addObject(new float[]{-50, -100, 250});
        spikesForCollision.addObject(new float[]{50, -100, 250});
        spikesForCollision.addObject(new float[]{0, -100, 150});
        spikesForCollision.addObject(new float[]{-150, -100, 50});
        spikesForCollision.addObject(new float[]{-50, -100, 50});
        spikesForCollision.addObject(new float[]{50, -100, 50});
        spikesForCollision.addObject(new float[]{150, -100, 50});
        spikesForCollision.addObject(new float[]{-100, -100, -50});
        spikesForCollision.addObject(new float[]{100, -100, -50});
        spikesForCollision.addObject(new float[]{-50, -100, -150});
        spikesForCollision.addObject(new float[]{50, -100, -150});
    }


    public void loadObjects() {
        tableModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/table.obj");
        gobletModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/goblet.obj");
        spikesModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/spikes.obj");
        initializeGobletsCoordinates();
        initializeTablesCoordinates();
        initializeSpikesCoordinates();
        initializeCoinsCoordinates();
        initializeWallsCoordinates();
    }

    public void setPlayer(){
        float xAxis[] = {1,0,0};
        float yAxis[] = {0,1,0};
        float zAxis[] = {0,0,-1};
        player = new PlayerLogic(stepQuanity, camAngle, xAxis, yAxis, zAxis, 0,0,395);
    }

    public void initializeCoinsCoordinates() {
        coins = new ObjectsForCollision();
        coins.addObject(new float[]{-90, -95, -200});
        coins.addObject(new float[]{90, -95, -200});
        coins.addObject(new float[]{-45, -95, -100});
        coins.addObject(new float[]{45, -95, -100});
        coins.addObject(new float[]{0, -95, 0});
        coins.addObject(new float[]{-45, -95, 100});
        coins.addObject(new float[]{45, -95, 100});
        coins.addObject(new float[]{-90, -95, 200});
        coins.addObject(new float[]{90, -95, 200});

        coinsBoolean = new ArrayList<>();
        for(int i=0; i<coins.getSize(); i++){
            coinsBoolean.add(true);
        }
    }

    @Override
    public void updateObjectsList() {
        //<Coins><Square of the spikes><The Table of the goblet><Left wall><Right wall><ceiling><floor><Back wall><Front Wall>
        objects = new ArrayList() {{
            add(coins.getObjectsList());
            add(spikesForCollision.getObjectsList());
            add(tables.getObjectsList());
            add(leftWall.getObjectsList());
            add(rightWall.getObjectsList());
            add(ceiling.getObjectsList());
            add(floor.getObjectsList());
            add(backWall.getObjectsList());
            add(frontWall.getObjectsList());
        }};
    }
}
