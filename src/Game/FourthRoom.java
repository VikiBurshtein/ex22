package Game;//names ids

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FourthRoom extends BaseRoom {
    private Texture tableTexture, gobletTexture, spikesTexture, coinTexture;
    private WavefrontObjectLoader_DisplayList coinModel, tableModel, gobletModel, spikesModel;
    private float position0[] = {10f, 0f, -5f, 1.0f};    // red light on the cubes from the top
    private float gobletRotation = 0.0f;
    private float gobletElevation = -70.0f;
    private boolean gobletIsUP = true;

    private ObjectsForCollision goblets = new ObjectsForCollision();
    private ObjectsForCollision tables = new ObjectsForCollision();
    private ObjectsForCollision spikesForDrawing = new ObjectsForCollision();
    private ObjectsForCollision spikesForCollision = new ObjectsForCollision();
    private ObjectsForCollision coins = new ObjectsForCollision();

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
    }

    public void drawObjects(GL2 gl) {
        drawTable(gl);
        drawGoblet(gl);
        drawSpikes(gl);
        drawCoins(gl);

        //draw health bar
        drawHealtbBar(gl);
    }

    public void drawCoins(GL2 gl) {
        for (int i = 0; i < coins.getSize(); i++) {
            drawOneCoin(gl, coins.getObject(i));
        }
        coins.rotateBy(3);
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
        goblets.rotateBy(1);
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
        if (gobletIsUP) {
            coordinates = goblets.moveObject(coordinates, 0, 0.1f, 0);
            if(coordinates[1] > -20){
                gobletIsUP = false;
            }
        }
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(5, 5, 5);
        gl.glRotatef(goblets.getRotation(), 0, goblets.getRotation(), 0);
        gobletTexture.bind(gl);
        gobletModel.drawModel(gl);
        gl.glPopMatrix();
    }

    public void drawOneCoin(GL2 gl, float[] coordinates) {
        gl.glPushMatrix();
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(5, 5, 5);
        gl.glRotatef(coins.getRotation(), 90, 90, 90);
        coinTexture.bind(gl);
        coinModel.drawModel(gl);
        gl.glPopMatrix();
    }

    public void setTextures(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
            //objects texture
            String coin = "resources/basicObjects/textures/coin.jpg";
            coinTexture = TextureIO.newTexture(new File(coin), true);
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
        float spikesHeightToDraw = -100;
        spikesForDrawing.addObject(new float[]{-100, spikesHeightToDraw, 350});
        spikesForDrawing.addObject(new float[]{100, spikesHeightToDraw, 350});
        spikesForDrawing.addObject(new float[]{-50, spikesHeightToDraw, 250});
        spikesForDrawing.addObject(new float[]{50, spikesHeightToDraw, 250});
        spikesForDrawing.addObject(new float[]{0, spikesHeightToDraw, 150});
        spikesForDrawing.addObject(new float[]{50, spikesHeightToDraw, 50});
        spikesForDrawing.addObject(new float[]{-50, spikesHeightToDraw, 50});
        spikesForDrawing.addObject(new float[]{150, spikesHeightToDraw, 50});
        spikesForDrawing.addObject(new float[]{-150, spikesHeightToDraw, 50});
        spikesForDrawing.addObject(new float[]{100, spikesHeightToDraw, -50});
        spikesForDrawing.addObject(new float[]{-100, spikesHeightToDraw, -50});
        spikesForDrawing.addObject(new float[]{50, spikesHeightToDraw, -150});
        spikesForDrawing.addObject(new float[]{-50, spikesHeightToDraw, -150});

        spikesForCollision.addObject(new float[]{-100, -100, 350});
        spikesForCollision.addObject(new float[]{100, -100, 350});
        spikesForCollision.addObject(new float[]{-50, -100, 250});
        spikesForCollision.addObject(new float[]{50, -100, 250});
        spikesForCollision.addObject(new float[]{0, -100, 150});
        spikesForCollision.addObject(new float[]{50, -100, 50});
        spikesForCollision.addObject(new float[]{-50, -100, 50});
        spikesForCollision.addObject(new float[]{150, -100, 50});
        spikesForCollision.addObject(new float[]{-150, -100, 50});
        spikesForCollision.addObject(new float[]{100, -100, -50});
        spikesForCollision.addObject(new float[]{-100, -100, -50});
        spikesForCollision.addObject(new float[]{50, -100, -150});
        spikesForCollision.addObject(new float[]{-50, -100, -150});

    }


    public void loadObjects() {
        coinModel = new WavefrontObjectLoader_DisplayList("basicObjects/objects/coin.obj");
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
        player = new PlayerLogic(stepQuanity, camAngle, 1, 1, -1, 0,0,399);
    }

    public void initializeCoinsCoordinates() {
        coins.addObject(new float[]{-90, -95, -200});
        coins.addObject(new float[]{90, -95, -200});
        coins.addObject(new float[]{-45, -95, -100});
        coins.addObject(new float[]{45, -95, -100});
        coins.addObject(new float[]{0, -95, 0});
        coins.addObject(new float[]{-45, -95, 100});
        coins.addObject(new float[]{45, -95, 100});
        coins.addObject(new float[]{-90, -95, 200});
        coins.addObject(new float[]{90, -95, 200});
    }

    @Override
    public void updateObjectsList() {
        //<Coins><Square of the spikes><The Table of the goblet><Left wall><Right wall><ceiling><floor><Back wall><Front Wall>
        objects = new ArrayList() {{
            add(coins.getObjectsList());
            add(spikesForCollision.getObjectsList());
            add(tables.getObjectsList());
            add(leftWall);
            add(rightWall);
            add(ceiling);
            add(floor);
            add(backWall);
            add(frontWall);
        }};
    }
}
