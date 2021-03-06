//Viki Burshtein 328684642
//Tomer Paz 315311365
package Game;

//names ids
public class PlayerLogic{

    public float xAxis[] = new float[3];
    public float yAxis[] = new float[3];
    public float zAxis[] = new float[3];
    public float pos[] = new float[3];
    public float look[] = new float[3];
    public float moveQuanity;
    public float angle;
    public float coordiTranslation[][] = new float[3][3];
    private float tmpcoordiTranslation[][] = new float[3][3];

    public PlayerLogic(float move, float angle, float[] xA, float[] yA, float[] zA, float pos0, float pos1, float pos2) {

        //start point
        xAxis = xA;
//        xAxis[0] = 0.02f;
//        xAxis[1] = 0f;
//        xAxis[2] = -1f;
        yAxis = yA;
//        zAxis[0] = -1f;
//        zAxis[1] = 0f;
//        zAxis[2] = -0.02f;
        zAxis = zA;
//        System.out.println("xAxis[0] = " + xAxis[0] + " xAxis[1] = " + xAxis[1] + " xAxis[2] = " + xAxis[2]);
//        System.out.println("yAxis[0] = " + yAxis[0] + " yAxis[1] = " + yAxis[1] + " yAxis[2] = " + yAxis[2]);
//        System.out.println("zAxis[0] = " + zAxis[0] + " zAxis[1] = " + zAxis[1] + " zAxis[2] = " + zAxis[2]);
        pos[0] = pos0; //right left
        pos[1] = pos1;//up down
        pos[2] = pos2;//in out

//        //start point
//        xAxis[0] = 1;
//        yAxis[1] = 1;
//        zAxis[2] = -1;
//
//        pos[0] = 0; //right left
//        pos[1] = 0;//up down
//        pos[2] = 399;//in out


//        xAxis[0] = 1;
//        yAxis[1] = 1;
//        zAxis[2] = -1;
//
//        pos[0] = 20;
//        pos[1] = -55;
//        pos[2] = 50;
        this.moveQuanity = move;
        this.angle = angle;
        setTransMatrix();
    }

    public void setTransMatrix() {
        for (int i = 0; i < 3; i++) {
            tmpcoordiTranslation[i][0] = coordiTranslation[i][0];
            tmpcoordiTranslation[i][1] = coordiTranslation[i][1];
            tmpcoordiTranslation[i][2] = coordiTranslation[i][2];
        }
        for (int i = 0; i < 3; i++) {
            coordiTranslation[i][0] = xAxis[i];
            coordiTranslation[i][1] = yAxis[i];
            coordiTranslation[i][2] = zAxis[i];
        }
    }

    private void reverseSetTransMatrix(){
        for (int i = 0; i < 3; i++) {
            coordiTranslation[i][0] = tmpcoordiTranslation[i][0];
            coordiTranslation[i][1] = tmpcoordiTranslation[i][1];
            coordiTranslation[i][2] = tmpcoordiTranslation[i][2];
        }
    }

    public float[] transVector(float[] v) {
        return multiplyMatrixInVector(coordiTranslation, v);
    }

    public float vectorLen(float[] v) {
        return (float)Math.sqrt(Math.pow(v[0], 2)
                + Math.pow(v[1], 2) + Math.pow(v[2], 2));
    }

    public void setLookAtPoint() {
        look[0] = pos[0] + zAxis[0];
        look[1] = pos[1] + zAxis[1];
        look[2] = pos[2] + zAxis[2];
    }

    public void move(float x_move, float y_move, float z_move) {
        float[] move = new float[3];
        move[0] = x_move;
        move[1] = y_move;
        move[2] = z_move;
        setTransMatrix();
        float[] trans_move = transVector(move);
        pos[0] = pos[0] + moveQuanity*trans_move[0];
        pos[1] = pos[1] + moveQuanity*trans_move[1];
        pos[2] = pos[2] + moveQuanity*trans_move[2];
    }

    public float[] getFuturePlaceOfMove(float x_move, float y_move, float z_move) {
        float[] move = new float[3];
        move[0] = x_move;
        move[1] = y_move;
        move[2] = z_move;
        setTransMatrix();
        float[] trans_move = transVector(move);
        float[] future = new float[3];
        future[0] = pos[0] + moveQuanity*trans_move[0];
        future[1] = pos[1] + moveQuanity*trans_move[1];
        future[2] = pos[2] + moveQuanity*trans_move[2];
        reverseSetTransMatrix();
        return future;
    }


    public void camMove(float angle, String axis) {
        float[] new_x = xAxis;
        float[] new_y = yAxis;
        float[] new_z = zAxis;
        float alfa = angle * this.angle;
        switch(axis) {
            case "X":
                new_z = addVectors(multScalar(zAxis, COS(alfa)), multScalar(yAxis, SIN(alfa)));
                new_y = subVectors(multScalar(yAxis, COS(alfa)), multScalar(zAxis, SIN(alfa)));
                break;
            case "Y":
                new_x = addVectors(multScalar(xAxis, COS(alfa)), multScalar(zAxis, SIN(alfa)));
                new_z = subVectors(multScalar(zAxis, COS(alfa)), multScalar(xAxis, SIN(alfa)));
                break;
            case "Z":
                new_x = addVectors(multScalar(xAxis, COS(alfa)), multScalar(yAxis, SIN(alfa)));
                new_y = subVectors(multScalar(yAxis, COS(alfa)), multScalar(xAxis, SIN(alfa)));
        }
        xAxis = new_x;
        yAxis = new_y;
        zAxis = new_z;
        normalization();
    }

    public void normalization() {
        float x_len = vectorLen(xAxis);
        float y_len = vectorLen(yAxis);
        float z_len = vectorLen(zAxis);

        for (int i = 0; i < 3; i++) {
            xAxis[i] = xAxis[i] / x_len;
            yAxis[i] = yAxis[i] / y_len;
            zAxis[i] = zAxis[i] / z_len;
        }
    }
    public static float[] multiplyMatrixInVector(float[][] a, float[] x) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
        float[] y = new float[m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                y[i] += a[i][j] * x[j];
        return y;
    }
    public float SIN(float x) {
        return (float)java.lang.Math.sin((float)x*3.14159/180);
    }

    public float COS(float x) {
        return (float)java.lang.Math.cos((float)x*3.14159/180);
    }
    public float[] addVectors(float[] x, float[] y) {
        float[] result = new float[3];
        result[0] = x[0]+y[0];
        result[1] = x[1]+y[1];
        result[2] = x[2]+y[2];
        return result;
    }

    public float[] subVectors(float[] x, float[] y) {
        float[] result = new float[3];
        result[0] = x[0]-y[0];
        result[1] = x[1]-y[1];
        result[2] = x[2]-y[2];
        return result;
    }

    public float[] multScalar(float[] x, float s) {
        float[] result = new float[3];
        result[0] = x[0]*s;
        result[1] = x[1]*s;
        result[2] = x[2]*s;
        return result;
    }
}