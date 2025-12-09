package my_package;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for the Gift class.
 */
class GiftTest {
    
    private Gift defaultGift;
    private Gift toyGift;
    private Gift boneGift;
    private Gift ballGift;

    /**
     * Sets up test objects before each test.
     */
    @BeforeEach
    void setUp() {
        defaultGift = new Gift();
        toyGift = new Gift(0); // "Toy", 12
        boneGift = new Gift(1); // "Bone", 6
        ballGift = new Gift(2); // "Ball", 3
    }

    /**
     * Tests the default constructor.
     */
    @Test
    void testDefaultConstructor() {
        assertNull(defaultGift.getType(), "Default gift type should be null");
        assertEquals(0, defaultGift.getRating(), "Default gift rating should be 0");
    }

    /**
     * Tests the parameterized constructor with valid indices.
     */
    @Test
    void testParameterizedConstructor() {
        assertEquals("Toy", toyGift.getType(), "Toy gift type should be 'Toy'");
        assertEquals(12, toyGift.getRating(), "Toy gift rating should be 12");
        
        assertEquals("Bone", boneGift.getType(), "Bone gift type should be 'Bone'");
        assertEquals(6, boneGift.getRating(), "Bone gift rating should be 6");
        
        assertEquals("Ball", ballGift.getType(), "Ball gift type should be 'Ball'");
        assertEquals(3, ballGift.getRating(), "Ball gift rating should be 3");
    }
    
    /**
     * Tests setting the gift type.
     */
    @Test
    void testSetType() {
        defaultGift.setType("Frisbee");
        assertEquals("Frisbee", defaultGift.getType(), "Gift type should be 'Frisbee'");
    }

    /**
     * Tests setting the gift rating.
     */
    @Test
    void testSetRating() {
        defaultGift.setRating(8);
        assertEquals(8, defaultGift.getRating(), "Gift rating should be 8");
    }
    
    /**
     * Tests that an invalid index throws an ArrayIndexOutOfBoundsException.
     */
    @Test
    void testInvalidIndexThrowsException() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> new Gift(5), "Creating a Gift object with an invalid index should throw ArrayIndexOutOfBoundsException");
    }
}
