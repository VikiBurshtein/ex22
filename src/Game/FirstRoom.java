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
    private ObjectsForCollision monkeys1 = new ObjectsForCollision();
    private ObjectsForCollision monkeys2 = new ObjectsForCollision();
    private ObjectsForCollision monkeys3 = new ObjectsForCollision();
    private ObjectsForCollision monkeys4 = new ObjectsForCollision();
    private ObjectsForCollision balls1 = new ObjectsForCollision();
    private ObjectsForCollision balls2 = new ObjectsForCollision();
    private ObjectsForCollision balls3 = new ObjectsForCollision();
    private ObjectsForCollision balls4 = new ObjectsForCollision();
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
        initializeCoinsCoordinates();
    }

    public void updateObjectsList() {
        //<Coins><Monkeys><Balls><Door><Left wall><Right wall><ceiling><floor><Back wall><Front Wall>
        ObjectsForCollision monkeys = new ObjectsForCollision();
        monkeys.addObject(monkeys1.getObject(0));
        monkeys.addObject(monkeys2.getObject(0));
        monkeys.addObject(monkeys3.getObject(0));
        monkeys.addObject(monkeys4.getObject(0));
        ObjectsForCollision balls = new ObjectsForCollision();
        balls.addObject(balls1.getObject(0));
        balls.addObject(balls2.getObject(0));
        balls.addObject(balls3.getObject(0));
        balls.addObject(balls4.getObject(0));
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
        if (shoot) {
            float[] monkey;

            //first ball
            coordinates = balls1.getObject(0);
            coordinates = balls1.moveObject(coordinates, 3f, 0, 6f);
            drawOneBall(gl, coordinates);

            if (coordinates[2] > 400) {
                shoot = false;
                monkey = monkeys1.getObject(0);
                balls1.moveObjectTo(coordinates, monkey[0], monkey[1] - 10, monkey[2]);
            }

            //second ball
            coordinates = balls2.getObject(0);
            coordinates = balls2.moveObject(coordinates, -3f, 0, 6f);
            drawOneBall(gl, coordinates);

            if (coordinates[2] > 400) {
                monkey = monkeys2.getObject(0);
                balls2.moveObjectTo(coordinates, monkey[0], monkey[1] - 10, monkey[2]);
            }

            //third ball
            coordinates = balls3.getObject(0);
            coordinates = balls3.moveObject(coordinates, -3f, 0, -6f);
            drawOneBall(gl, coordinates);

            if (coordinates[2] < -400) {
                monkey = monkeys3.getObject(0);
                balls3.moveObjectTo(coordinates, monkey[0], monkey[1] - 10, monkey[2]);
            }

            //fourth ball
            coordinates = balls4.getObject(0);
            coordinates = balls4.moveObject(coordinates, 3f, 0, -6f);
            drawOneBall(gl, coordinates);

            if (coordinates[2] < -400) {
                monkey = monkeys4.getObject(0);
                balls4.moveObjectTo(coordinates, monkey[0], monkey[1] - 10, monkey[2]);
            }
        } else {
            float[] coordinates1 = balls1.getObject(0);
            float[] coordinates2 = balls2.getObject(0);
            float[] coordinates3 = balls3.getObject(0);
            float[] coordinates4 = balls4.getObject(0);
            if (monkeyUp) {
                coordinates1 = balls1.moveObject(coordinates1, 0, 0.2f, 0);
                coordinates2 = balls2.moveObject(coordinates2, 0, 0.2f, 0);
                coordinates3 = balls3.moveObject(coordinates3, 0, 0.2f, 0);
                coordinates4 = balls4.moveObject(coordinates4, 0, 0.2f, 0);
            } else {
                coordinates1 = balls1.moveObject(coordinates1, 0, -0.2f, 0);
                coordinates2 = balls2.moveObject(coordinates2, 0, -0.2f, 0);
                coordinates3 = balls3.moveObject(coordinates3, 0, -0.2f, 0);
                coordinates4 = balls4.moveObject(coordinates4, 0, -0.2f, 0);
            }
            if ((int) coordinates1[1] == -50 || (int) coordinates1[1] == 30) {
                shoot = true;
            }
            drawOneBall(gl, coordinates1);
            drawOneBall(gl, coordinates2);
            drawOneBall(gl, coordinates3);
            drawOneBall(gl, coordinates4);
        }
    }


    public void drawMonkeys(GL2 gl) {
        float[] coordinates1,coordinates2,coordinates3,coordinates4;
        for (int i = 0; i < monkeys1.getSize(); i++) {
            coordinates1 = monkeys1.getObject(i);
            coordinates2 = monkeys2.getObject(i);
            coordinates3 = monkeys3.getObject(i);
            coordinates4 = monkeys4.getObject(i);

            //check if reached top or bottom and move
            if (coordinates1[1] > roomHeight - 20) {
                monkeyUp = false;
            }
            if (coordinates1[1] < -(roomHeight - 20)) {
                monkeyUp = true;
            }

            if (monkeyUp) {
                coordinates1 = monkeys1.moveObject(coordinates1, 0, 0.2f, 0);
                drawOneMonkey(gl, coordinates1,1);

                coordinates2 = monkeys2.moveObject(coordinates2, 0, 0.2f, 0);
                drawOneMonkey(gl, coordinates2,2);

                coordinates3 = monkeys3.moveObject(coordinates3, 0, 0.2f, 0);
                drawOneMonkey(gl, coordinates3,3);

                coordinates4 = monkeys4.moveObject(coordinates4, 0, 0.2f, 0);
                drawOneMonkey(gl, coordinates4,4);
            } else {
                coordinates1 = monkeys1.moveObject(coordinates1, 0, -0.2f, 0);
                drawOneMonkey(gl, coordinates1,1);

                coordinates2 = monkeys2.moveObject(coordinates2, 0, -0.2f, 0);
                drawOneMonkey(gl, coordinates2,2);

                coordinates3 = monkeys3.moveObject(coordinates3, 0, -0.2f, 0);
                drawOneMonkey(gl, coordinates3,3);

                coordinates4 = monkeys4.moveObject(coordinates4, 0, -0.2f, 0);
                drawOneMonkey(gl, coordinates4,4);
            }
        }
    }

    public void drawOneMonkey(GL2 gl, float[] coordinates, int number) {
        gl.glPushMatrix();
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(15, 15, 15);
        if(number == 1){
            gl.glRotatef(monkeys1.getRotation(), 0, monkeys1.getRotation(), 0);
        } else if(number == 2){
            gl.glRotatef(monkeys2.getRotation(), 0, monkeys2.getRotation(), 0);
        } else if(number == 3){
            gl.glRotatef(monkeys3.getRotation(), 0, monkeys3.getRotation(), 0);
        } else {
            gl.glRotatef(monkeys4.getRotation(), 0, monkeys4.getRotation(), 0);
        }

        monkeyTexture.bind(gl);
        monkeyModel.drawModel(gl);
        gl.glPopMatrix();
    }


    public void drawOneBall(GL2 gl, float[] coordinates) {
        gl.glPushMatrix();
        gl.glTranslatef(coordinates[0], coordinates[1], coordinates[2]);
        gl.glScalef(5, 5, 5);
        ballTexture.bind(gl);
        ballModel.drawModel(gl);
        gl.glPopMatrix();
    }

    public void setTextures(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
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
        player = new PlayerLogic(stepQuanity, camAngle, xAxis, yAxis, zAxis, 0, 0, 395);
    }

    public void initializeMonkeysCoordinates() {
        float[] monkey1 = {-150, -80, -350};
        monkeys1.addObject(monkey1);
        monkeys1.rotateBy(25);

        float[] monkey2 = {150, -80, -350};
        monkeys2.addObject(monkey2);
        monkeys2.rotateBy(335);

        float[] monkey3 = {150, -80, 350};
        monkeys3.addObject(monkey3);
        monkeys3.rotateBy(235);

        float[] monkey4 = {-150, -80, 350};
        monkeys4.addObject(monkey4);
        monkeys4.rotateBy(115);
    }

    public void initializeDoorCoordinates() {
        doors.addObject(new float[]{0, -10, 0});
    }

    public void initializeCoinsCoordinates() {
        coins = new ObjectsForCollision();
        coins.addObject(new float[]{-90, -90, -200});
        coins.addObject(new float[]{90, -90, -200});
        coins.addObject(new float[]{-45, 90, -100});
        coins.addObject(new float[]{45, 90, -100});
        coins.addObject(new float[]{0, 50, 0});
        coins.addObject(new float[]{-45, 20, 100});
        coins.addObject(new float[]{45, 20, 100});
        coins.addObject(new float[]{-90, -90, 200});
        coins.addObject(new float[]{90, -90, 200});

        coinsBoolean = new ArrayList<>();
        for (int i = 0; i < coins.getSize(); i++) {
            coinsBoolean.add(true);
        }
    }

    public void initializeBallsCoordinates() {
        float[] monkey;
        monkey = monkeys1.getObject(0);
        balls1.addObject(new float[]{monkey[0], monkey[1] - 10, monkey[2]});

        monkey = monkeys2.getObject(0);
        balls2.addObject(new float[]{monkey[0], monkey[1] - 10, monkey[2]});

        monkey = monkeys3.getObject(0);
        balls3.addObject(new float[]{monkey[0], monkey[1] - 10, monkey[2]});

        monkey = monkeys4.getObject(0);
        balls4.addObject(new float[]{monkey[0], monkey[1] - 10, monkey[2]});
    }
}