package dev.ooad.model.persistence;

import dev.ooad.model.Member;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representing persistence in our application.
 * Handles serialization of objects and file writing/reading.
 */
public class Storage {

    private final String path;
    private final File storage;
    private Map<Integer, Member> registry;

    public Storage() {
        this.storage = new File("storage.ser");
        this.path = storage.getAbsolutePath();
        this.registry = new HashMap<>();
    }

    /**
     * Reads the file at "path". If none exists, it creates an empty one.
     * @return - Map containing the current registry (contents of the file)
     */
    @SuppressWarnings("unchecked")
    public Map<Integer, Member> readFile() {

        try {
            storage.createNewFile(); // if file already exists this will do nothing

            FileInputStream readData = new FileInputStream(this.path);
            ObjectInputStream readStream = new ObjectInputStream(readData);

            // read the object from the file and store it in currentScores
            registry = (Map<Integer, Member>) readStream.readObject();
            readStream.close();
        }
        catch (Exception ex) {
        }

        return registry;
    }

    /**
     * Writes to the file at "path".
     * Clones the object passed as argument and serializes it in order to persist the data.
     * @param map - the Map to store
     */
    public void writeFile(Map<Integer, Member> map) {

        // clone the map
        registry.putAll(map);

        try {
            FileOutputStream writeData = new FileOutputStream(this.path);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            // write the ArrayList to the designated file
            writeStream.writeObject(registry);
            writeStream.close();
        }
        catch (IOException ex) {
        }
    }
}
