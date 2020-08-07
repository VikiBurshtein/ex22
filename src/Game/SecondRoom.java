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
    private Texture mazeTexture;
    private WavefrontObjectLoader_DisplayList mazeModel;
    private float position0[] = {10f, 0f, -5f, 1.0f};    // red light on the cubes from the top

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
        drawMaze(gl);
    }

    public void drawMaze(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(10.0f, 0.0f, 260.0f);
        gl.glScalef(40, 100, 40);
        mazeTexture.bind(gl);
        mazeModel.drawModel(gl);
        gl.glPopMatrix();
    }

    public void setTextures(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
            //objects texture
            String maze = "resources/" + roomName + "/objectTextures/maze.jpg";
            mazeTexture = TextureIO.newTexture(new File(maze), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        setRoomTextures(gl);
    }

    public void loadObjects() {
        mazeModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/maze.obj");
    }

    @Override
    public void updateObjectsList() {

    }


}