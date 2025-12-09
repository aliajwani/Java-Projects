package my_package;

/**
 * The {@code Food} class represents a type of food with an associated rating.
 * It provides constructors to create food instances based on predefined types and ratings,
 * as well as methods to access and modify the food's properties.
 */
public class Food  {
    // Attributes
    private String type;  // The type of food
    private int rating;   // The rating of the food

    // Static array to hold food types and their corresponding ratings
    private static final String[] FOOD_TYPES = {"Biscuit", "Meat", "Bread"};
    private static final int[] FOOD_RATINGS = {12, 6, 3}; // Corresponding ratings

    /**
     * Constructs a {@code Food} object using a specified index.
     * The index determines the food type and rating from the predefined arrays.
     *
     * @param index the index of the food type and rating to use
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public Food(int index) {
        this.type = FOOD_TYPES[index];
        this.rating = FOOD_RATINGS[index];
    }

    /**
     * Constructs a default {@code Food} object with {@code null} type and rating {@code 0}.
     */
    public Food() {
        this.type = null;
        this.rating = 0;
    }

    /**
     * Returns the type of the food.
     *
     * @return the food type as a {@code String}
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the rating of the food.
     *
     * @return the food rating as an {@code int}
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the type of the food.
     *
     * @param type the food type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the rating of the food.
     *
     * @param rating the food rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
}