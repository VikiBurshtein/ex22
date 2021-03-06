//Viki Burshtein 328684642
//Tomer Paz 315311365
package Game;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RoomNameAndCoins {
    private Texture RoomNameAndCoinsTexture;
    private float material[] = {0.8f, 0.8f, 0.8f, 1.0f};

    public RoomNameAndCoins() {
        setRoomNameAndCoins();
    }

    public void setRoomNameAndCoins() {
        String texture = "resources/screenTextures/roomNameAndCoins.jpg";
        try {
            RoomNameAndCoinsTexture = TextureIO.newTexture(new File(texture), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void drawRoomNameAndCoins(GL2 gl) {
        //light
        gl.glDisable(GL2.GL_LIGHTING);
        gl.glDisable(GL2.GL_LIGHT0);

        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);

        RoomNameAndCoinsTexture.bind(gl);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, material, 0);

        gl.glBegin(GL2GL3.GL_QUADS);

        gl.glTexCoord2f(0, 0);
        gl.glVertex2f(9f, -0.2f);

        gl.glTexCoord2f(1, 0);
        gl.glVertex2f(9.3f, -0.2f);

        gl.glTexCoord2f(1, 1);
        gl.glVertex2f(9.3f, 0.2f);

        gl.glTexCoord2f(0, 1);
        gl.glVertex2f(9f, 0.2f);


        gl.glEnd();

        //light
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
    }
}