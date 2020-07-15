package Mathematics;

import java.util.Vector;

public class Calculations {

    public static float vectorLen(Vector<Float> v) {
        return (float)Math.sqrt(Math.pow(v.get(0), 2)
                + Math.pow(v.get(1), 2) + Math.pow(v.get(2), 2));
    }

    public static float vectorLen(float[] v) {
        return (float)Math.sqrt(Math.pow(v[0], 2)
                + Math.pow(v[1], 2) + Math.pow(v[2], 2));
    }


    public static Vector<Float> normalization(Vector<Float> v) {
        float len = vectorLen(v);

        Vector<Float> nornVector = new Vector<Float>(3);
        nornVector.add(v.get(0) / len);
        nornVector.add(v.get(1) / len);
        nornVector.add(v.get(2) / len);

        return nornVector;
    }

    public static float SIN(float x) {
        return (float)java.lang.Math.sin((float)x*3.14159/180);
    }

    public static float COS(float x) {
        return (float)java.lang.Math.cos((float)x*3.14159/180);
    }


    public static Vector<Float> crossProduct(Vector<Float> v1, Vector<Float> v2) {
        Vector<Float> v3 = new Vector<Float>(3);
        v3.add(v1.get(1)*v2.get(2) - v1.get(2)*v2.get(1));
        v3.add(v1.get(2)*v2.get(0) - v1.get(0)*v2.get(2));
        v3.add(v1.get(0)*v2.get(1) - v1.get(1)*v2.get(0));

        return v3;
    }

    public static float dotProduct(Vector<Float> v1, Vector<Float> v2) {
        return v1.get(0)*v2.get(0) + v1.get(1)*v2.get(1) + v1.get(2)*v2.get(2);
    }


    public static float angleBetweenVectors(Vector<Float> v1, Vector<Float> v2) {
        float cosAngle =  dotProduct(v1, v2) / (vectorLen(v1) * vectorLen(v2));
        return (float) (Math.acos(cosAngle)*(180/Math.PI));
    }

    public static float[] multVecScalar(float[] x, float s) {
        float[] result = new float[3];
        result[0] = x[0]*s;
        result[1] = x[1]*s;
        result[2] = x[2]*s;
        return result;
    }

    public static float[] subVectors(float[] x, float[] y) {
        float[] result = new float[3];
        result[0] = x[0]-y[0];
        result[1] = x[1]-y[1];
        result[2] = x[2]-y[2];
        return result;
    }


    public static float[] addVectors(float[] x, float[] y) {
        float[] result = new float[3];
        result[0] = x[0]+y[0];
        result[1] = x[1]+y[1];
        result[2] = x[2]+y[2];
        return result;
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
}
