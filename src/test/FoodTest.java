package my_package;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for the Food class.
 */
class FoodTest {
    
    private Food defaultFood;
    private Food biscuitFood;
    private Food meatFood;
    private Food breadFood;

    /**
     * Sets up test objects before each test.
     */
    @BeforeEach
    void setUp() {
        defaultFood = new Food();
        biscuitFood = new Food(0); // "Biscuit", 12
        meatFood = new Food(1); // "Meat", 6
        breadFood = new Food(2); // "Bread", 3
    }

    /**
     * Tests the default constructor.
     */
    @Test
    void testDefaultConstructor() {
        assertNull(defaultFood.getType(), "Default food type should be null");
        assertEquals(0, defaultFood.getRating(), "Default food rating should be 0");
    }

    /**
     * Tests the parameterized constructor with valid indices.
     */
    @Test
    void testParameterizedConstructor() {
        assertEquals("Biscuit", biscuitFood.getType(), "Biscuit food type should be 'Biscuit'");
        assertEquals(12, biscuitFood.getRating(), "Biscuit food rating should be 12");
        
        assertEquals("Meat", meatFood.getType(), "Meat food type should be 'Meat'");
        assertEquals(6, meatFood.getRating(), "Meat food rating should be 6");
        
        assertEquals("Bread", breadFood.getType(), "Bread food type should be 'Bread'");
        assertEquals(3, breadFood.getRating(), "Bread food rating should be 3");
    }
    
    /**
     * Tests setting the food type.
     */
    @Test
    void testSetType() {
        defaultFood.setType("Pizza");
        assertEquals("Pizza", defaultFood.getType(), "Food type should be 'Pizza'");
    }

    /**
     * Tests setting the food rating.
     */
    @Test
    void testSetRating() {
        defaultFood.setRating(8);
        assertEquals(8, defaultFood.getRating(), "Food rating should be 8");
    }
    
    /**
     * Tests that an invalid index throws an ArrayIndexOutOfBoundsException.
     */
    @Test
    void testInvalidIndexThrowsException() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> new Food(5), "Creating a Food object with an invalid index should throw ArrayIndexOutOfBoundsException");
    }
}