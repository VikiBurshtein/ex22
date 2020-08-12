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

public class FirstRoom extends BaseRoom {
    private Texture coinTexture, monkeyTexture, arrowTexture;
    private WavefrontObjectLoader_DisplayList coinModel, monkeyModel, arrowModel;
    private ObjectsForCollision monkeys = new ObjectsForCollision();
    private ObjectsForCollision coins = new ObjectsForCollision();
    private ObjectsForCollision arrows = new ObjectsForCollision();
    private ObjectsForCollision doors = new ObjectsForCollision();


    private float position0[] = {10f, 0f, -5f, 1.0f};    // red light on the cubes from the top

    private boolean monkeyUp = false;

    FirstRoom() {
        roomName = "firstRoom";
        roomNameToShow = "First Room";
        roomWidth = 200.0f;
        roomHeight = 100.0f;
        roomDepth = 400.0f;
        canvas = new GLCanvas();
        animator = new Animator(canvas);
        objects = new ArrayList<>();
        glu = new GLU();
        frame = new Frame("");

    }

    public void updateObjectsList(){
        //<Coins><Monkeys><Arrows><Door><Left wall><Right wall><ceiling><floor><Back wall><Front Wall>
        objects = new ArrayList() {{
            add(coins.getObjectsList());
            add(monkeys.getObjectsList());
            add(arrows.getObjectsList());
            add(doors);
            add(leftWall);
            add(rightWall);
            add(ceiling);
            add(floor);
            add(backWall);
            add(frontWall);
        }};
    }

    public void drawObjects(GL2 gl) {
        drawCoins(gl);
        drawMonkeys(gl);
        drawArrows(gl);
        drawHealtbBar(gl);
    }

    public void drawArrows(GL2 gl) {
        float[] coordinates;
        for (int i = 0; i < arrows.getSize(); i++) {
            coordinates = arrows.getObject(i);
            if (monkeyUp) {
                coordinates = arrows.moveObject(coordinates, 0, 0.1f, -0.1f);
            } else {
                coordinates = arrows.moveObject(coordinates, 0, -0.1f, 0.1f);
            }
            drawOneArrow(gl, coordinates);
        }
        //rotate arrows
        arrows.rotateBy(0.7f);
    }

    public void drawMonkeys(GL2 gl) {
        float[] coordinates;

        for (int i = 0; i < monkeys.getSize(); i++) {
            coordinates = monkeys.getObject(i);
            //check if reached top or bottom and move
            if (coordinates[1] > roomHeight - 20) {
                monkeyUp = false;
            }
            if (coordinates[1] < -(roomHeight - 20)) {
                monkeyUp = true;
            }

            if (monkeyUp) {
                coordinates = monkeys.moveObject(coordinates, 0, 0.1f, -0.1f);
            } else {
                coordinates = monkeys.moveObject(coordinates, 0, -0.1f, 0.1f);
            }
            drawOneMonkey(gl, coordinates);
        }

        //rotate monkeys
        monkeys.rotateBy(359.3f);
    }

    public void drawCoins(GL2 gl) {
        for (int i = 0; i < coins.getSize(); i++) {
            drawOneCoin(gl, coins.getObject(i));
        }
        coins.rotateBy(3);
    }

    public void drawOneMonkey(GL2 gl, float[] coordinates) {
        gl.glPushMatrix();
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(15, 15, 15);
        gl.glRotatef(monkeys.getRotation(), 0, monkeys.getRotation(), 0);
        monkeyTexture.bind(gl);
        monkeyModel.drawModel(gl);
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

    public void drawOneArrow(GL2 gl, float[] coordinates) {
        gl.glPushMatrix();
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(5, 5, 5);
        gl.glRotatef(90,90,0,0);
        gl.glRotatef(arrows.getRotation(),0,0,arrows.getRotation());
        arrowTexture.bind(gl);
        arrowModel.drawModel(gl);
        gl.glPopMatrix();
    }

    public void setTextures(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
            //objects texture
            String coin = "resources/basicObjects/textures/coin.jpg";
            coinTexture = TextureIO.newTexture(new File(coin), true);
            String monkey = "resources/" + roomName + "/objectTextures/monkey.jpg";
            monkeyTexture = TextureIO.newTexture(new File(monkey), true);
            String arrow = "resources/" + roomName + "/objectTextures/arrow.jpg";
            arrowTexture = TextureIO.newTexture(new File(arrow), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        setRoomTextures(gl);
    }

    public void loadObjects() {
        coinModel = new WavefrontObjectLoader_DisplayList("basicObjects/objects/coin.obj");
        monkeyModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/monkey.obj");
        arrowModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/arrow.obj");
        initializeCoinsCoordinates();
        initializeMonkeysCoordinates();
        initializeArrowsCoordinates();
        initializeWallsCoordinates();
        initializeDoorCoordinates();
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

    public void setPlayer(){
        player = new PlayerLogic(stepQuanity, camAngle, 1, 1, -1, 0,0,399);
    }

    public void initializeMonkeysCoordinates() {
        float[] monkey0 = {-120, -60, -10};
        float[] monkey1 = {120, -60, -10};
        monkeys.addObject(monkey0);
        monkeys.addObject(monkey1);
    }

    public void initializeDoorCoordinates() {
        doors.addObject(new float[]{0, -10, 0});
    }

    public void initializeCoinsCoordinates() {
        coins.addObject(new float[]{-90, -90, -200});
        coins.addObject(new float[]{90, -90, -200});
        coins.addObject(new float[]{-45, 90, -100});
        coins.addObject(new float[]{45, 90, -100});
        coins.addObject(new float[]{0, 50, 0});
        coins.addObject(new float[]{-45, 20, 100});
        coins.addObject(new float[]{45, 20, 100});
        coins.addObject(new float[]{-90, -90, 200});
        coins.addObject(new float[]{90, -90, 200});
    }

    public void initializeArrowsCoordinates() {
        float[] monkey;
        for(int i=0; i<monkeys.getSize(); i++){
            monkey = monkeys.getObject(i);
            arrows.addObject(new float[] {monkey[0],monkey[1] - 10,monkey[2]});
        }
    }
}