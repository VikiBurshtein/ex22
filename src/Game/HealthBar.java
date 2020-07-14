package Game;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HealthBar {
    private Texture healthBarTexture;
    private float material[] = {0.8f, 0.8f, 0.8f, 1.0f};
    private int healthScore = 100;

    public HealthBar() {
        setHealthBar();
    }

    public int getHealthScore(){
        return healthScore;
    }

    public void decreaseHealth(){
        healthScore = healthScore - 50;
        if(healthScore < 0){
            healthScore = 0;
        }
        setHealthBar();
    }

    public void restoreHealth(){
        healthScore = 100;
        setHealthBar();
    }

    public void setHealthBar(){
        String texture;
        if(healthScore == 100){
            texture = "resources/thirdRoom/objectTextures/health100.png";
        } else if(healthScore == 50){
            texture = "resources/thirdRoom/objectTextures/health50.png";
        } else {
            texture = "resources/thirdRoom/objectTextures/health0.png";
        }
        try{
            healthBarTexture = TextureIO.newTexture(new File(texture), true);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void drawHealthBar(GL2 gl) {
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);

        healthBarTexture.bind(gl);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, material, 0);

        gl.glBegin(GL2GL3.GL_QUADS);

        gl.glTexCoord2f(0, 0);
        gl.glVertex2f(-0.25f, 0);

        gl.glTexCoord2f(1, 0);
        gl.glVertex2f(1, 0);

        gl.glTexCoord2f(1, 1);
        gl.glVertex2f(1, 0.4f);

        gl.glTexCoord2f(0, 1);
        gl.glVertex2f(-0.25f, 0.4f);


        gl.glEnd();
    }
}