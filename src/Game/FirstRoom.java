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
    private Texture monkeyTexture, ballTexture;
    private WavefrontObjectLoader_DisplayList monkeyModel, ballModel;
    private ObjectsForCollision monkeys = new ObjectsForCollision();
    private ObjectsForCollision balls = new ObjectsForCollision();
    private ObjectsForCollision doors = new ObjectsForCollision();


    private float position0[] = {10f, 0f, -5f, 1.0f};    // red light on the cubes from the top

    private boolean monkeyUp = false;
    private boolean shoot = false;

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

    public void updateObjectsList() {
        //<Coins><Monkeys><Balls><Door><Left wall><Right wall><ceiling><floor><Back wall><Front Wall>
        objects = new ArrayList() {{
            add(coins.getObjectsList());
            add(monkeys.getObjectsList());
            add(balls.getObjectsList());
            add(doors.getObjectsList());
            add(leftWall.getObjectsList());
            add(rightWall.getObjectsList());
            add(ceiling.getObjectsList());
            add(floor.getObjectsList());
            add(backWall.getObjectsList());
            add(frontWall.getObjectsList());
        }};
    }

    public void drawObjects(GL2 gl) {
        drawCoins(gl);
        drawMonkeys(gl);
        drawBalls(gl);
    }

    public void drawBalls(GL2 gl) {
        float[] coordinates;
        if(shoot){
            for (int i = 0; i < balls.getSize(); i++) {
                coordinates = balls.getObject(i);
                coordinates = balls.moveObject(coordinates, 3f, 0, 6f);
                drawOneBall(gl, coordinates);
                if (coordinates[2] > 400) {
                    shoot = false;
                    float[] monkey = monkeys.getObject(0);
                    balls.moveObjectTo(coordinates, monkey[0], monkey[1] - 10, monkey[2]);
                }
            }
        } else {
            for (int i = 0; i < balls.getSize(); i++) {
                coordinates = balls.getObject(i);

                if (monkeyUp) {
                    coordinates = balls.moveObject(coordinates, 0, 0.2f, 0);
                } else {
                    coordinates = balls.moveObject(coordinates, 0, -0.2f, 0);
                }
                if ((int)coordinates[1] == -50 || (int)coordinates[1] == 30) {
                    shoot = true;
                }
                drawOneBall(gl, coordinates);
            }
        }
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
                coordinates = monkeys.moveObject(coordinates, 0, 0.2f, 0);
            } else {
                coordinates = monkeys.moveObject(coordinates, 0, -0.2f, 0);
            }
            drawOneMonkey(gl, coordinates);
        }
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



    public void drawOneBall(GL2 gl, float[] coordinates) {
        gl.glPushMatrix();
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(5, 5, 5);
        gl.glRotatef(90, 90, 0, 0);
        gl.glRotatef(balls.getRotation(), 0, 0, balls.getRotation());
        ballTexture.bind(gl);
        ballModel.drawModel(gl);
        gl.glPopMatrix();
    }

    public void setTextures(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
            //objects texture
//            String coin = "resources/basicObjects/textures/coin.jpg";
//            coinTexture = TextureIO.newTexture(new File(coin), true);
            String monkey = "resources/" + roomName + "/objectTextures/monkey.jpg";
            monkeyTexture = TextureIO.newTexture(new File(monkey), true);
            String ball = "resources/" + roomName + "/objectTextures/ball.jpg";
            ballTexture = TextureIO.newTexture(new File(ball), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        setRoomTextures(gl);
    }

    public void loadObjects() {
        monkeyModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/monkey.obj");
        ballModel = new WavefrontObjectLoader_DisplayList(roomName + "/objects/ball.obj");
        initializeCoinsCoordinates();
        initializeMonkeysCoordinates();
        initializeBallsCoordinates();
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

    public void setPlayer() {
        float xAxis[] = {1, 0, 0};
        float yAxis[] = {0, 1, 0};
        float zAxis[] = {0, 0, -1};
        player = new PlayerLogic(stepQuanity, camAngle, xAxis, yAxis, zAxis, 0, 0, 399);
    }

    public void initializeMonkeysCoordinates() {
        float[] monkey = {-150, -80, -350};
        monkeys.addObject(monkey);
        monkeys.rotateBy(25);
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

        for(int i=0; i<coins.getSize(); i++){
            coinsBoolean.add(true);
        }
    }

    public void initializeBallsCoordinates() {
        float[] monkey;
        for (int i = 0; i < monkeys.getSize(); i++) {
            monkey = monkeys.getObject(i);
            balls.addObject(new float[]{monkey[0], monkey[1] - 10, monkey[2]});
        }
    }
}