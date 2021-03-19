package dev.ooad.model;

/**
 * Represents a boat object.
 */
public class Boat extends BaseEntity implements Cloneable {

    /**
     * The types of boat that are available to create.
     */
    public enum BoatType {

        SAILBOAT("Sailboat"),
        MOTORSAILER("Motorsailer"),
        KAYAK("Kayak"),
        CANOE("Canoe"),
        OTHER ("Other");

        private final String type;

        BoatType(String typeName) {
            this.type = typeName;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    private BoatType type;
    private double length;

    public Boat() {
    }

    /**
     * Constructor: Creates a new boat with the specified data.
     * @param type - the specific type of this boat
     * @param length - the length of the boat
     */
    public Boat(BoatType type, double length) {
        this.type = type;
        this.length = length;
    }

    public BoatType getType() {
        return type;
    }

    public void setType(BoatType type) {
        this.type = type;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
