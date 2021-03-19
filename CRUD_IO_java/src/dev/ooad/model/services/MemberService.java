package dev.ooad.model.services;

import dev.ooad.model.Member;
import dev.ooad.model.persistence.Storage;

import java.util.*;

/**
 * Service handling saving, updating and deleting Members. Also generates unique IDs for the objects.
 * Implements the CrudService interface.
 */
public class MemberService implements CrudService<Integer, Member> {

    protected Map<Integer, Member> map;
    private final Storage storage;

    /**
     * Constructor: Creates a Storage-object and accesses the persistence (reads the file) during creation.
     */
    public MemberService() {
        storage = new Storage();
        map = storage.readFile();
    }

    /**
     * Returns a Set of all member objects currently present in storage.
     * @return - HashSet of member objects
     */
    public Set<Member> findAll() {
        return new HashSet<>(map.values());
    }

    /**
     * Returns a member with the specified ID from storage.
     * @param id - Integer
     * @return - Member object
     */
    public Member findById(Integer id) {
        return map.get(id);
    }

    /**
     * Saves the passed in member object to storage.
     * @param member - the member to store
     * @throws RuntimeException - if null is passed into the method
     * @return - the object if successful, otherwise null
     */
    public Member save(Member member) {

        if (member != null) {

            // if the member object already exists
            if (map.containsValue(member)) {
                return null;
            }

            // check if member with entered personal number already exists
            if (memberPersnrAlreadyExists(member.getPersonalNumber())) {
                return null;
            }

            // if no ID value - set it
            if (member.getId() == null) {
                member.setId(getNextId());
            }

            map.put(member.getId(), member);
            //storage.writeFile(map);
        }
        else {
            throw new RuntimeException("Member cannot be null");
        }

        return member;
    }

    /**
     * Updates the passed in member object.
     * @param member - the member to update
     * @throws RuntimeException - if null is passed into the method
     * @return - the object if successful, otherwise null
     */
    public Member update(Member member) {

        if (member != null) {
            map.put(member.getId(), member);
           // storage.writeFile(map);
        }
        else {
            throw new RuntimeException("Member cannot be null");
        }

        return member;
    }

    /**
     * Delete the member with the specified ID from storage.
     * @param id - membership ID of the member to remove
     */
    public void deleteById(Integer id) {
        map.remove(id);
        //storage.writeFile(map);
    }

    /**
     * Delete the specified member from storage.
     * @param member - member object to remove
     */
    public void delete(Member member) {
        map.entrySet().removeIf(entry -> entry.getValue().equals(member));
        //storage.writeFile(map);
    }

    /**
     * Saves all changes by sending them to Storage.
     * Should only be done upon exiting the application as intended.
     */
    public void registryToStorage() {
        storage.writeFile(map);
    }

    /**
     * Checks if a member with the entered personal number already exists in the registry.
     * @param persnr - the personal number to evaluate
     * @return - boolean
     */
    public boolean memberPersnrAlreadyExists(long persnr) {

        // if personal number is not unique
        for (Member m : map.values()) {
            if (m.getPersonalNumber() == persnr) {
                return true;
            }
        }

        return false;
    }

    // generate ID
    private Integer getNextId() {

        Integer nextId = null;

        // if map is not empty, set ID to current amount + 1
        try {
            nextId = Collections.max(map.keySet()) + 1;
        }
        catch (NoSuchElementException ex) {
            nextId = 1;
        }

        return nextId;
    }
}
