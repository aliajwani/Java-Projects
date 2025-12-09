package my_package;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Pet class.
 */
class PetTest {
    private Pet pet;

    /**
     * Initializes a pet instance before each test.
     */
    @BeforeEach
    void setUp() {
        pet = new Pet("Buddy", "cat");
    }

    /**
     * Tests the constructor and getters.
     */
    @Test
    void testConstructorAndGetters() {
        assertEquals("Buddy", pet.getName());
        assertEquals("cat", pet.getType());
        assertEquals(50, pet.getHealth()); // Expected value from stats array
        assertEquals(75, pet.getHappiness());
        assertEquals(100, pet.getFullness());
        assertEquals(75, pet.getSleep());
    }

    /**
     * Tests setting and getting attributes.
     */
    @Test
    void testSettersAndGetters() {
        pet.setName("Charlie");
        assertEquals("Charlie", pet.getName());
        
        pet.setType("fox");
        assertEquals("fox", pet.getType());
        
        pet.setHealth(60);
        assertEquals(60, pet.getHealth());
        
        pet.setHappiness(80);
        assertEquals(80, pet.getHappiness());
        
        pet.setFullness(90);
        assertEquals(90, pet.getFullness());
        
        pet.setSleep(65);
        assertEquals(65, pet.getSleep());
        
        pet.setState("asleep");
        assertEquals("asleep", pet.getState());
    }

    /**
     * Tests the revivePet method.
     */
    @Test
    void testRevivePet() {
        pet.setHealth(10);
        pet.setHappiness(20);
        pet.setFullness(30);
        pet.setSleep(40);
        pet.revivePet();
        
        assertEquals(50, pet.getHealth());
        assertEquals(75, pet.getHappiness());
        assertEquals(100, pet.getFullness());
        assertEquals(75, pet.getSleep());
        assertEquals("default", pet.getState());
    }

    /**
     * Tests the statLimiter method to ensure stats remain within valid bounds.
     */
    @Test
    void testStatLimiter() {
        pet.setHealth(200);
        pet.setHappiness(-10);
        pet.setFullness(150);
        pet.setSleep(-5);
        pet.statLimiter();
        
        assertEquals(50, pet.getHealth()); // Should be capped at max value
        assertEquals(0, pet.getHappiness()); // Should not go below 0
        assertEquals(100, pet.getFullness()); // Should be capped at max value
        assertEquals(0, pet.getSleep()); // Should not go below 0
    }

    /**
     * Tests the setState method logic.
     */
    @Test
    void testSetState() {
        pet.setSleep(0);
        pet.setState();
        assertEquals("asleep", pet.getState());
        
        pet.setFullness(0);
        pet.setState();
        assertEquals("hungry", pet.getState());
        
        pet.setHappiness(0);
        pet.setState();
        assertEquals("angry", pet.getState());
        
        pet.setHealth(0);
        pet.setState();
        assertEquals("dead", pet.getState());
    }
}