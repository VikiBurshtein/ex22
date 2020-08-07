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

public class ThirdRoom extends BaseRoom {
    private Texture dynamicLasersTexture;
    private WavefrontObjectLoader_DisplayList dynamicLasersModel;
    private float position0[] = {10f, 0f, -5f, 1.0f};    // red light on the cubes from the top
    private ObjectsForCollision horizontalLasers = new ObjectsForCollision();
    private ObjectsForCollision verticalLasers = new ObjectsForCollision();

    private boolean laserGoingDown = true;
    private boolean laserGoingRight = true;
    private float lasersSpeed = 0.2f;

    ThirdRoom() {
        roomName = "thirdRoom";
        roomWidth = 100.0f;
        roomHeight = 100.0f;
        roomDepth = 400.0f;
        canvas = new GLCanvas();
        animator = new Animator(canvas);
        objects = new ArrayList<>();
        glu = new GLU();
        frame = new Frame("");
    }

    public void drawObjects(GL2 gl) {
        drawLasers(gl);
        drawHealtbBar(gl);
    }

    public void drawLasers(GL2 gl) {
        float[] coordinates;

        for (int i = 0; i < horizontalLasers.getSize(); i++) {
            coordinates = horizontalLasers.getObject(i);
            //check if reached top or bottom and move
            if (coordinates[1] > -80) {
                laserGoingDown = true;
            }
            if (coordinates[1] < -270) {
                laserGoingDown = false;
            }

            if (laserGoingDown) {
                coordinates = horizontalLasers.moveObject(coordinates, 0, -lasersSpeed, 0);
            } else {
                coordinates = horizontalLasers.moveObject(coordinates, 0, lasersSpeed, 0);
            }
            drawOneHorizontalLaser(gl, coordinates);
        }

        for (int i = 0; i < verticalLasers.getSize(); i++) {
            coordinates = verticalLasers.getObject(i);
            //check if reached top or bottom and move
            if (coordinates[0] > 90) {
                laserGoingRight = false;
            }
            if (coordinates[0] < -90) {
                laserGoingRight = true;
            }

            if (laserGoingRight) {
                coordinates = verticalLasers.moveObject(coordinates, lasersSpeed, 0, 0);
            } else {
                coordinates = verticalLasers.moveObject(coordinates, -lasersSpeed, 0, 0);
            }
            drawOneVerticalLaser(gl, coordinates);
        }
    }

    public void drawOneVerticalLaser(GL2 gl, float[] coordinates) {
        gl.glPushMatrix();
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(15, 15, 15);
        gl.glRotatef(90, 90, 0, 0);
        dynamicLasersTexture.bind(gl);
        dynamicLasersModel.drawModel(gl);
        gl.glPopMatrix();
    }
    public void drawOneHorizontalLaser(GL2 gl, float[] coordinates) {
        gl.glPushMatrix();
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(15, 15, 15);
        gl.glRotatef(90, 0, 90, 0);
        dynamicLasersTexture.bind(gl);
        dynamicLasersModel.drawModel(gl);
        gl.glPopMatrix();
    }

    //@Override
    public void setTextures(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
            //objects texture
            String dynamicLasers = "resources/" + roomName + "/objectTextures/green.jpg";
            dynamicLasersTexture = TextureIO.newTexture(new File(dynamicLasers), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        setRoomTextures(gl);
    }
    public void loadObjects() {
        dynamicLasersModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/dynamicLaser.obj");
        initializeLasersCoordinates();
    }

    public void initializeLasersCoordinates() {
        float[] horizontalLaser1 = {5.0f, -100.0f, -250.0f};
        float[] horizontalLaser2 = {5.0f, -100.0f, -150.0f};
        float[] horizontalLaser3 = {5.0f, -100.0f, -50.0f};
        float[] horizontalLaser4 = {5.0f, -100.0f, 50.0f};
        float[] horizontalLaser5 = {5.0f, -100.0f, 150.0f};
        float[] horizontalLaser6 = {5.0f, -100.0f, 250.0f};

        horizontalLasers.addObject(horizontalLaser1);
        horizontalLasers.addObject(horizontalLaser2);
        horizontalLasers.addObject(horizontalLaser3);
        horizontalLasers.addObject(horizontalLaser4);
        horizontalLasers.addObject(horizontalLaser5);
        horizontalLasers.addObject(horizontalLaser6);

        float[] verticalLaser1 = {-100.0f, -50, -300.0f};
        float[] verticalLaser2 = {-100.0f, -50, -200.0f};
        float[] verticalLaser3 = {-100.0f, -50, -100.0f};
        float[] verticalLaser4 = {-100.0f, -50, 0.0f};
        float[] verticalLaser5 = {-100.0f, -50, -100.0f};
        float[] verticalLaser6 = {-100.0f, -50, -200.0f};
        float[] verticalLaser7 = {-100.0f, -50, -300.0f};

        verticalLasers.addObject(verticalLaser1);
        verticalLasers.addObject(verticalLaser2);
        verticalLasers.addObject(verticalLaser3);
        verticalLasers.addObject(verticalLaser4);
        verticalLasers.addObject(verticalLaser5);
        verticalLasers.addObject(verticalLaser6);
        verticalLasers.addObject(verticalLaser7);
    }

    @Override
    public void updateObjectsList() {
        objects = new ArrayList() {{
            add(horizontalLasers.getObjectsList());
            add(verticalLasers.getObjectsList());
        }};
    }


}