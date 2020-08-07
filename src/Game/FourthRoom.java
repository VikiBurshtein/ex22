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
import java.util.List;

public class FourthRoom extends BaseRoom {
    private Texture tableTexture, gobletTexture;
    private WavefrontObjectLoader_DisplayList tableModel, gobletModel;
    private float position0[] = {10f, 0f, -5f, 1.0f};    // red light on the cubes from the top
    private float gobletRotation = 0.0f;
    private float gobletElevation = -70.0f;
    private boolean gobletIsUP = false;

    private ObjectsForCollision goblets = new ObjectsForCollision();
    private ObjectsForCollision tables = new ObjectsForCollision();

    FourthRoom() {
        roomName = "fourthRoom";
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
        //draw table
        for (int i = 0; i < tables.getSize(); i++) {
            drawOneTable(gl, tables.getObject(i));
        }

        //draw goblet
        for (int i = 0; i < goblets.getSize(); i++) {
            drawOneGoblet(gl, goblets.getObject(i));
        }
        goblets.rotateBy(0.3f);
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
        }
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(5, 5, 5);
        gobletTexture.bind(gl);
        gobletModel.drawModel(gl);
        gl.glPopMatrix();
        gobletIsUP = true;
    }

    public void setTextures(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
            //objects texture
            String goblet = "resources/" + roomName + "/objectTextures/goblet.jpg";
            gobletTexture = TextureIO.newTexture(new File(goblet), true);
            String table = "resources/" + roomName + "/objectTextures/table.jpg";
            tableTexture = TextureIO.newTexture(new File(table), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        setRoomTextures(gl);
    }

    public void initializeGobletsCoordinates() {
        goblets.addObject(new float[]{0, -70, -200.0f});
    }

    public void initializeTablesCoordinates() {
        tables.addObject(new float[]{0, -100, -200});
    }

    public void loadObjects() {
        tableModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/table.obj");
        gobletModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/goblet.obj");
        initializeGobletsCoordinates();
        initializeTablesCoordinates();
    }

    @Override
    public void updateObjectsList() {

    }


}
