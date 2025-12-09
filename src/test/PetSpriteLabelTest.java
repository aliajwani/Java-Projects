package my_package;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for the PetSpriteLabel class.
 */
class PetSpriteLabelTest {

    private PetSpriteLabel petSpriteLabel;

    /**
     * Sets up test objects before each test.
     */
    @BeforeEach
    void setUp() {
        petSpriteLabel = new PetSpriteLabel("TestSprite");
    }

    /**
     * Tests the getSpriteName method.
     */
    @Test
    void testGetSpriteName() {
        assertEquals("TestSprite", petSpriteLabel.getSpriteName(), "Sprite name should be 'TestSprite'");
    }

    /**
     * Tests the setSpriteName method.
     */
    @Test
    void testSetSpriteName() {
        petSpriteLabel.setSpriteName("NewSprite");
        assertEquals("NewSprite", petSpriteLabel.getSpriteName(), "Sprite name should be updated to 'NewSprite'");
    }
}
