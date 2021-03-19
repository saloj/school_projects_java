package dev.ooad.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a member of a Boat Club.
 */
public class Member extends BaseEntity {

    private Integer id;
    private long personalNumber;
    private String firstName;
    private String lastName;
    private List<Boat> boats;

    public Member() {
    }

    /**
     * Constructor: Creates a new member with the specified data.
     * @param personalNumber - personal number (long) of the member to create
     * @param firstName - first name (String)
     * @param lastName - last name (String)
     */
    public Member(long personalNumber, String firstName, String lastName) {
        this.personalNumber = personalNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.boats = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getPersonalNumber() {
        return personalNumber;
    }

    public boolean setPersonalNumber(long personalNumber) {
        if (validPersonalNumber(personalNumber)) {
            this.personalNumber = personalNumber;
            return true;
        }
        else {
            return false;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the Member. Only accepts non-numerical input.
     * @param firstName - name to set
     * @return - boolean if successful or not
     */
    public boolean setFirstName(String firstName) {
        if (validName(firstName)) {
            this.firstName = firstName;
            return true;
        }
        else {
            return false;
        }
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the Member. Only accepts non-numerical input.
     * @param lastName - name to set
     * @return - boolean if successful or not
     */
    public boolean setLastName(String lastName) {
        if (validName(lastName)) {
            this.lastName = lastName;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Clones and returns the complete list of boats currently registered to this member.
     * @return - the list of boat objects
     */
    public List<Boat> getBoats() {
        List<Boat> clones = new ArrayList<>();

        try {
            for (Boat boat : boats) {
                clones.add((Boat)boat.clone());
            }
        }
        catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }

        return clones;
    }

    public int getNumberOfBoats() {
        return boats.size();
    }

    public void addBoat(Boat boat) {
        boats.add(boat);
    }

    public void removeBoat(Boat boat) {
        boats.remove(boat);
    }

    /**
     * Removes a specific boat from this particular member.
     * @param index - the index of the boat to remove
     * @return - if successful or not
     */
    public boolean removeBoatByIndex(int index) {
        boolean inBounds = (index >= 0) && (index < boats.size());

        if (inBounds) {
            boats.remove(index);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Validates the members personal number.
     * In this case assuming "standard" Swedish pnr length of 10 digits.
     * @param nr - the personal number to validate
     * @return - boolean in response if the number is of correct length
     */
    private boolean validPersonalNumber(long nr) {
        return String.valueOf(nr).length() == 10;
    }

    /**
     * Validates the members name.
     * Only non-numerical input is allowed.
     * @param input - the input to validate
     * @return - true if valid, false if not
     */
    private boolean validName(String input) {
        return !input.matches(".*\\d+.*");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        if (personalNumber != member.personalNumber) return false;
        if (!Objects.equals(firstName.toLowerCase(), member.firstName.toLowerCase())) return false;
        return Objects.equals(lastName.toLowerCase(), member.lastName.toLowerCase());
    }
}
