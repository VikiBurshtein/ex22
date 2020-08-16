package Game;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HealthBar {
    private Texture texture100, texture50, texture0, healthBarTexture;
    private float material[] = {0.8f, 0.8f, 0.8f, 1.0f};

    public HealthBar() {
        setHealthBar();
    }

    public void setHealthBar(){
        try{
            texture100 = TextureIO.newTexture(new File("resources/screenTextures/health100.png"), true);
            texture50 = TextureIO.newTexture(new File("resources/screenTextures/health50.png"), true);
            texture0 = TextureIO.newTexture(new File("resources/screenTextures/health0.png"), true);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void drawHealthBar(GL2 gl, int life) {
        if(life == 2){
            healthBarTexture = texture100;
        } else if(life == 1){
            healthBarTexture = texture50;
        } else {
            healthBarTexture = texture0;
        }
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);

        healthBarTexture.bind(gl);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, material, 0);

        gl.glBegin(GL2GL3.GL_QUADS);

        gl.glTexCoord2f(0, 0);
        gl.glVertex2f(-0.25f, -0.2f);

        gl.glTexCoord2f(1, 0);
        gl.glVertex2f(1, -0.2f);

        gl.glTexCoord2f(1, 1);
        gl.glVertex2f(1, 0.6f);

        gl.glTexCoord2f(0, 1);
        gl.glVertex2f(-0.25f, 0.6f);


        gl.glEnd();
    }
}