package Game;//names ids

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import Collide.CollisionCheck;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class SecondRoom extends BaseRoom {
    private Texture coinTexture, pathTexture, sharkTexture;
    private WavefrontObjectLoader_DisplayList coinModel, pathModel, sharkModel;
    private float position0[] = {10f, 0f, -5f, 1.0f};    // red light on the cubes from the top

    private ObjectsForCollision paths = new ObjectsForCollision();
    private ObjectsForCollision sharks = new ObjectsForCollision();
    private ObjectsForCollision coins = new ObjectsForCollision();

    private float sharksSpeed = 0.5f;
    private boolean sharkRight = true;

    SecondRoom() {
        roomName = "secondRoom";
        roomWidth = 414.0f;
        roomHeight = 100.0f;
        roomDepth = 416.0f;
        canvas = new GLCanvas();
        animator = new Animator(canvas);
        objects = new ArrayList<>();
        glu = new GLU();
        frame = new Frame("");
    }

    public void drawObjects(GL2 gl) {
        drawPath(gl);
        drawSharks(gl);
        drawCoins(gl);

        drawHealtbBar(gl);
    }

    public void drawPath(GL2 gl) {
        for (int i = 0; i < paths.getSize(); i++) {
            drawOnePath(gl, paths.getObject(i));
        }
    }

    public void drawCoins(GL2 gl) {
        for (int i = 0; i < coins.getSize(); i++) {
            drawOneCoin(gl, coins.getObject(i));
        }
        coins.rotateBy(3);
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

    public void drawSharks(GL2 gl) {
        float[] coordinates;

        for (int i = 0; i < sharks.getSize(); i++) {
            coordinates = sharks.getObject(i);
            //check if reached top or bottom and move
            if (coordinates[0] > roomWidth - 100) {
                sharkRight = false;
            }
            if (coordinates[0] < -(roomWidth - 100)) {
                sharkRight = true;
            }

            if (sharkRight) {
                coordinates = sharks.moveObject(coordinates, sharksSpeed, 0, 0);
            } else {
                coordinates = sharks.moveObject(coordinates, -sharksSpeed, 0, 0);
            }
            drawOneShark(gl, coordinates);
        }
    }

    public void drawOnePath(GL2 gl, float[] coordinates) {
        gl.glPushMatrix();
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(15, 5, 20);
        pathTexture.bind(gl);
        pathModel.drawModel(gl);
        gl.glPopMatrix();
    }

    public void drawOneShark(GL2 gl, float[] coordinates) {
        gl.glPushMatrix();
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(5, 5, 5);
        sharkTexture.bind(gl);
        sharkModel.drawModel(gl);
        gl.glPopMatrix();
    }



    public void setTextures(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
            //objects texture
            String coin = "resources/basicObjects/textures/coin.jpg";
            coinTexture = TextureIO.newTexture(new File(coin), true);
            String path = "resources/" + roomName + "/objectTextures/path.jpg";
            pathTexture = TextureIO.newTexture(new File(path), true);
            String shark = "resources/" + roomName + "/objectTextures/shark.jpg";
            sharkTexture = TextureIO.newTexture(new File(shark), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        setRoomTextures(gl);
    }

    public void loadObjects() {
        coinModel = new WavefrontObjectLoader_DisplayList("basicObjects/objects/coin.obj");
        initializeCoinsCoordinates();
        pathModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/path.obj");
        initializePathsCoordinates();
        sharkModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/shark.obj");
        initializeSharksCoordinates();
    }

    public void initializePathsCoordinates() {
        paths.addObject(new float[]{0, -100, 0});
    }

    public void initializeSharksCoordinates() {
        sharks.addObject(new float[]{-150, -100, -350});
        sharks.addObject(new float[]{50, -100, 150});
    }

    public void initializeCoinsCoordinates() {
        coins.addObject(new float[]{-90, -50, 0});
        coins.addObject(new float[]{90, -50, 0});
        coins.addObject(new float[]{-150, -50, 150});
        coins.addObject(new float[]{150, -50, -150});
    }

    @Override
    public void updateObjectsList() {
        //<Coins><Path1><Path2><Path3><Path4><Path5><Door>
        objects = new ArrayList() {{
            add(coins.getObjectsList());
            add(new ObjectsForCollision());
            add(new ObjectsForCollision());
            add(sharks.getObjectsList());
            add(new ObjectsForCollision());
            add(new ObjectsForCollision());
            add(new ObjectsForCollision());
            add(new ObjectsForCollision());
            add(new ObjectsForCollision());
            add(paths.getObjectsList());
        }};
    }


}