import java.awt.Frame;
import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import com.jogamp.newt.Window;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class PlayerLogic extends KeyAdapter implements GLEventListener {
    private float xrot;        // X Rotation ( NEW )
    private float yrot;        // Y Rotation ( NEW )
    private float zrot;        // Z Rotation ( NEW )
    private float eyeX,eyeY,eyeZ,cX,cY,cZ,upX,upY,upZ;
    private Texture texture;
    private boolean iIsPressed, kIsPressed, lIsPressed, jIsPressed, oIsPressed, uIsPressed;
    private boolean wIsPressed, sIsPressed, dIsPressed, aIsPressed, eIsPressed, qIsPressed;

    static GLU glu = new GLU();
    static GLCanvas canvas = new GLCanvas();
    static Frame frame = new Frame("Jogl 3D Shape/Rotation");
    static Animator animator = new Animator(canvas);

    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2(); //get the GL object
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); //clear the depth buffer and the color buffer
        gl.glLoadIdentity();  // Reset The View
        gl.glTranslatef(0.0f, 0.0f, -5.0f);
        glu.gluLookAt(eyeX,eyeY,eyeZ,cX,cY,cZ,upX,upY,upZ); //set the camera view


        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);

        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        texture.bind(gl);

        gl.glBegin(GL2.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);// give the texture coordinates to bind
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);// the vertex
        gl.glTexCoord2f(2f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(2f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        // Back Face
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        // Top Face
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        // Bottom Face
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        // Right face
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        // Left Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();

        //הרמת המבט כלפי מעלה (ציר סיבוב X)
        if (iIsPressed) {
            //eyeX += 0.01f;
        }
        //הורדת המבט כלפי מטה (ציר סיבוב X)
        if (kIsPressed) {
            //eyeX -= 0.01f;
        }
        //הזזת המבט ימינה (ציר סיבוב Y)
        if (lIsPressed) {
        }
        //הזזת המבט שמאלה (ציר סיבוב Y)
        if (jIsPressed) {
        }
        //הטיית המבט ימינה (ציר סיבוב Z)
        if (oIsPressed) {
        }
        //הטיית המבט שמאלה (ציר סיבוב Z)
        if (uIsPressed) {
        }
        //תנועה קדימה בכיוון המבט
        if (wIsPressed) {
        }
        //תנועה אחורה בכיוון המבט
        if (sIsPressed) {
        }
        //תנועה ימינה תוך כדי שמירה על כיוון המבט
        if (dIsPressed) {
        }
        //תנועה שמאלה תוך כדי שמירה על כיוון המבט
        if (aIsPressed) {
        }
        //תנועה למעלה תוך כדי שמירה על כיוון המבט
        if (eIsPressed) {
        }
        //תנועה למטה תוך כדי שמירה על כיוון המבט
        if (qIsPressed) {
        }
    }

    public void displayChanged(GLAutoDrawable gLDrawable,
                               boolean modeChanged, boolean deviceChanged) {
    }

    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL2.GL_DEPTH_TEST);              // Enables Depth Testing
        gl.glDepthFunc(GL2.GL_LEQUAL);               // The Type Of Depth Testing To Do
        // Really Nice Perspective Calculations
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
            String filename = "C:\\Users\\vikib\\OneDrive\\Documents\\Uni\\GRAPHICS\\ex22\\src/Picture1.jpg"; // the FileName to open
            texture = TextureIO.newTexture(new File(filename), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        if (drawable instanceof Window) {
            Window window = (Window) drawable;
            window.addKeyListener(this);
        } else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) drawable;
            new AWTKeyAdapter(this, drawable).addTo(comp);
        }
        iIsPressed = false;
        kIsPressed = false;
        lIsPressed = false;
        jIsPressed = false;
        oIsPressed = false;
        uIsPressed = false;
        wIsPressed = false;
        sIsPressed = false;
        dIsPressed = false;
        aIsPressed = false;
        eIsPressed = false;
        qIsPressed = false;
        eyeX = 0.5f;
        eyeY = 0.5f;
        eyeZ = 0.5f;
        cX = 0.5f;
        cY = 0.5f;
        cZ = 0.5f;
        upX = 0.5f;
        upY = 0.5f;
        upZ = 0.5f;
    }

    public void reshape(GLAutoDrawable drawable, int x,
                        int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        if (height <= 0) {
            height = 1;
        }
        float h = (float) width / (float) height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                exit();
                break;
            case KeyEvent.VK_I:
                iIsPressed = true;
                break;
            case KeyEvent.VK_K:
                kIsPressed = true;
                break;
            case KeyEvent.VK_L:
                lIsPressed = true;
                break;
            case KeyEvent.VK_J:
                jIsPressed = true;
                break;
            case KeyEvent.VK_O:
                oIsPressed = true;
                break;
            case KeyEvent.VK_U:
                uIsPressed = true;
                break;
            case KeyEvent.VK_W:
                wIsPressed = true;
                break;
            case KeyEvent.VK_S:
                sIsPressed = true;
                break;
            case KeyEvent.VK_D:
                dIsPressed = true;
                break;
            case KeyEvent.VK_A:
                aIsPressed = true;
                break;
            case KeyEvent.VK_E:
                eIsPressed = true;
                break;
            case KeyEvent.VK_Q:
                qIsPressed = true;
                break;
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_I:
                iIsPressed = false;
                break;
            case KeyEvent.VK_K:
                kIsPressed = false;
                break;
            case KeyEvent.VK_L:
                lIsPressed = false;
                break;
            case KeyEvent.VK_J:
                jIsPressed = false;
                break;
            case KeyEvent.VK_O:
                oIsPressed = false;
                break;
            case KeyEvent.VK_U:
                uIsPressed = false;
                break;
            case KeyEvent.VK_W:
                wIsPressed = false;
                break;
            case KeyEvent.VK_S:
                sIsPressed = false;
                break;
            case KeyEvent.VK_D:
                dIsPressed = false;
                break;
            case KeyEvent.VK_A:
                aIsPressed = false;
                break;
            case KeyEvent.VK_E:
                eIsPressed = false;
                break;
            case KeyEvent.VK_Q:
                qIsPressed = false;
                break;
            default:
                break;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void exit() {
        animator.stop();
        frame.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        canvas.addGLEventListener(new Room());
        frame.add(canvas);
        frame.setSize(2000, 1000);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        frame.setVisible(true);
        animator.start();
        canvas.requestFocus();
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub

    }
}