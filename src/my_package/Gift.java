package my_package;

/**
 * Represents a gift item that can be given to a pet.
 * Each gift has a type and a rating value that determines its desirability.
 * 
 * @author danielantal
 */
public class Gift {
    // Attributes
    private String type;  // The type of gift
    private int rating;   // The rating of the gift

    // Static arrays to hold gift types and their corresponding ratings
    private static final String[] GIFT_TYPES = {"Toy", "Bone", "Ball"};
    private static final int[] GIFT_RATINGS = {12, 6, 3}; // Corresponding ratings

    /**
     * Constructs a new Gift with a specific type and rating based on the provided index.
     * The index corresponds to the position in the GIFT_TYPES and GIFT_RATINGS arrays.
     * @param index The index to determine the gift type and rating (0 for Toy, 1 for Bone, 2 for Ball)
     */
    public Gift(int index) {
        this.type = GIFT_TYPES[index];
        this.rating = GIFT_RATINGS[index];
    }
    
    /**
     * Constructs an empty Gift with no type and zero rating.
     */
    public Gift(){
        this.type=null;
        this.rating=0;
    }

    /**
     * Gets the type of the gift.
     * @return The type of the gift (Toy, Bone, or Ball)
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the rating value of the gift.
     * @return The rating value of the gift
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the type of the gift.
     * @param type The new type to set for the gift
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the rating value of the gift.
     * @param rating The new rating value to set for the gift
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
}