
package Game;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;
import java.io.File;
import java.io.IOException;
import java.util.List;

/** represent as object on center of the screen with visuality of Matrix "Green Rain"
 * will be not be considered room, but an object so it will be able to visualize on a running room.
 */

public class F1Screen {

    private Texture f1Texture;
    private float material[] = {0.8f, 0.8f, 0.8f, 1.0f};
    public static List<String> instructions;

    public F1Screen(String roomName){
        setF1Screen();
    }

    public void setF1Screen(){
        String texture = "resources/screenTextures/f1.jpg";
        try{
            f1Texture = TextureIO.newTexture(new File(texture), true);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void drawF1(GL2 gl) {
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);

        f1Texture.bind(gl);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, material, 0);

        gl.glBegin(GL2GL3.GL_QUADS);

        gl.glTexCoord2f(0, 0);
        gl.glVertex2f(-0.25f, -9.5f);

        gl.glTexCoord2f(1, 0);
        gl.glVertex2f(9.75f, -9.5f);

        gl.glTexCoord2f(1, 1);
        gl.glVertex2f(9.75f, 0f);

        gl.glTexCoord2f(0, 1);
        gl.glVertex2f(-0.25f, 0f);

        gl.glEnd();

    }

}