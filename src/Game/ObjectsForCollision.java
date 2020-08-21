package Game;

import java.util.ArrayList;
import java.util.List;

public class ObjectsForCollision {
    public List<float[]> objectsList = new ArrayList<>();
    private float rotation = 0;

    public void rotateBy(float angle) {
        rotation = (rotation + angle) % 360;
    }

    public float getRotation(){
        return rotation;
    }

    public void addObject(float[] coordinates) {
        objectsList.add(coordinates);
    }

    public void deleteObject(float[] coordinates) {
        objectsList.remove(coordinates);
    }

    public void deleteObject(int index) {
        objectsList.remove(index);
    }

    public float[] getObject(int number) {
        return objectsList.get(number);
    }

    public List<float[]> getObjectsList() {
        return objectsList;
    }

    public int getSize() {
        return objectsList.size();
    }

    public float[] moveObject(float[] obj, float x, float y, float z) {
        obj[0] = obj[0] + x;
        obj[1] = obj[1] + y;
        obj[2] = obj[2] + z;
        return obj;
    }

    public void moveSpike(int index, float y) {
        float[] newCoordinates = objectsList.get(index);
        newCoordinates[1] = y;
        objectsList.set(index, newCoordinates);
    }

    public void moveSpike(float[] coordinates, float y) {
        int index = -1;
        for(int i=0; i<objectsList.size(); i++){
            if(objectsList.get(i)[0] == coordinates[0] && objectsList.get(i)[1] == coordinates[1] && objectsList.get(i)[0] == coordinates[0]){
                index = i;
                break;
            }
        }
        if(index != -1) {
            coordinates[1] = y;
            objectsList.set(index, coordinates);
        }
    }

    public float[] moveObjectTo(float[] obj, float x, float y, float z) {
        obj[0] = x;
        obj[1] = y;
        obj[2] = z;
        return obj;
    }
}