//Viki Burshtein 328684642
//Tomer Paz 315311365
package Game;

import java.util.ArrayList;
import java.util.List;

public class ObjectsForCollision {
    public List<float[]> objectsList = new ArrayList<>();
    private List<Float> rotation = new ArrayList<>();

    public void rotateAllBy(float angle) {
        for(int i=0; i<rotation.size(); i++){
            rotation.set(i, (rotation.get(i) + angle) % 360);
        }
    }

    public void rotateOneBy(float angle, int index) {
        rotation.set(index, (rotation.get(index) + angle) % 360);
    }

    public float getRotation(int index){
        return rotation.get(index);
    }

    public void addObject(float[] coordinates) {
        objectsList.add(coordinates);
        rotation.add(0f);
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

    public void moveOneObjectUpByIndex(int index, float y) {
        float[] newCoordinates = objectsList.get(index);
        newCoordinates[1] = y;
        objectsList.set(index, newCoordinates);
    }

    public void moveOneObjectUpByCoordinates(float[] coordinates, float y) {
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