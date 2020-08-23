//Viki Burshtein 328684642
//Tomer Paz 315311365
package Game;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.media.opengl.GL2;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class SecondRoom extends BaseRoom {
    private Texture pathTexture, sharkTexture;
    private WavefrontObjectLoader_DisplayList pathModel, sharkModel;
    private float position0[] = {10f, 0f, -5f, 1.0f};    // red light on the cubes from the top

    private ObjectsForCollision paths = new ObjectsForCollision();
    private ObjectsForCollision sharks = new ObjectsForCollision();
    private ObjectsForCollision doors = new ObjectsForCollision();

    private float sharksSpeed = 0.5f;
    private boolean sharkRight = true;
    private boolean rotatePath;

    SecondRoom() {
        roomName = "secondRoom";
        roomNameToShow = "Second Room";
        roomWidth = 400.0f;
        roomHeight = 100.0f;
        roomDepth = 400.0f;
        canvas = new GLCanvas();
        animator = new Animator(canvas);
        objects = new ArrayList<>();
        glu = new GLU();
        frame = new Frame("");
        initializeCoinsCoordinates();
    }

    public void drawObjects(GL2 gl) {
        drawPath(gl);
        drawSharks(gl);
        drawCoins(gl);
    }

    public void drawPath(GL2 gl) {
        rotatePath = false;
        for (int i = 0; i < paths.getSize(); i++) {
            drawOnePath(gl, paths.getObject(i));
        }
    }

    public void drawSharks(GL2 gl) {
        float[] coordinates;

        for (int i = 0; i < sharks.getSize(); i++) {
            coordinates = sharks.getObject(i);
            //check if reached top or bottom and move
            if (coordinates[0] > roomWidth - 120) {
                sharkRight = false;
            }
            if (coordinates[0] < -(roomWidth - 120)) {
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
        gl.glScalef(40, 15, 40);
        if(rotatePath){
            gl.glRotatef(90,0,90,0);
            rotatePath = false;
        } else {
            rotatePath = true;
        }
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

    public void setPlayer(){
        float xAxis[] = {0.02f,0,-1f};
        float yAxis[] = {0,1,0};
        float zAxis[] = {-1f,0,-0.02f};
        player = new PlayerLogic(stepQuanity, camAngle, xAxis, yAxis, zAxis, 0,-50,395);
    }

    public void loadObjects() {
        pathModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/path.obj");
        sharkModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/shark.obj");
        initializeCoinsCoordinates();
        initializePathsCoordinates();
        initializeSharksCoordinates();
        initializeDoorCoordinates();
    }

    public void initializeDoorCoordinates() {
        doors.addObject(new float[]{0, -10, 0});
    }

    public void initializePathsCoordinates() {
        paths.addObject(new float[]{0, -100, -400});
        paths.addObject(new float[]{-130, -100, 200});
        paths.addObject(new float[]{0, -100, 0});
        paths.addObject(new float[]{130, -100, -200});
        paths.addObject(new float[]{0, -100, 400});

    }

    public void initializeSharksCoordinates() {
        sharks.addObject(new float[]{-150, -100, -350});
        sharks.addObject(new float[]{50, -100, 150});
    }

    public void initializeCoinsCoordinates() {
        coins = new ObjectsForCollision();
        coins.addObject(new float[]{-90, -50, -380});
        coins.addObject(new float[]{0, -50, -380});
        coins.addObject(new float[]{90, -50, -380});

        coins.addObject(new float[]{150, -50, -40});
        coins.addObject(new float[]{130, -50, -200});
        coins.addObject(new float[]{110, -50, -360});

        coins.addObject(new float[]{-90, -50, -20});
        coins.addObject(new float[]{0, -50, 0});
        coins.addObject(new float[]{90, -50, 20});

        coins.addObject(new float[]{-150, -50, 40});
        coins.addObject(new float[]{-130, -50, 200});
        coins.addObject(new float[]{-110, -50, 360});

        coins.addObject(new float[]{-90, -50, 380});
        coins.addObject(new float[]{0, -50, 380});
        coins.addObject(new float[]{90, -50, 380});

        coinsBoolean = new ArrayList<>();
        for(int i=0; i<coins.getSize(); i++){
            coinsBoolean.add(true);
        }
    }

    @Override
    public void updateObjectsList() {
        //<Coins><Paths><Door>
        objects = new ArrayList() {{
            add(coins.getObjectsList());
            add(paths.getObjectsList());
            add(doors.getObjectsList());
        }};
    }


}