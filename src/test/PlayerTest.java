package my_package;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for the Player class.
 * Tests the Player's properties including playtime, score, and its interactions with the PetManager and InventoryManager.
 */
class PlayerTest {

    private Player player;
    private PetManager petManager;
    private InventoryManager inventoryManager;

    /**
     * Sets up the Player, PetManager, and InventoryManager before each test.
     */
    @BeforeEach
    void setUp() {
        petManager = new PetManager();  // Assuming PetManager constructor is valid
        inventoryManager = new InventoryManager(new Pet("Fluffy", "cat"));  // Assuming InventoryManager constructor is valid
        player = new Player(petManager, inventoryManager, 10, 100);  // Default playtime: 10 minutes, score: 100
    }

    /**
     * Tests getting and setting the player's playtime.
     */
    @Test
    void testGetSetPlaytime() {
        // Verify initial playtime
        assertEquals(10, player.getPlaytime(), "Initial playtime should be 10 minutes");

        // Set new playtime and verify
        player.setPlaytime(20);
        assertEquals(20, player.getPlaytime(), "Playtime should be updated to 20 minutes");
    }

    /**
     * Tests getting and setting the player's score.
     */
    @Test
    void testGetSetScore() {
        // Verify initial score
        assertEquals(100, player.getScore(), "Initial score should be 100");

        // Set new score and verify
        player.setScore(200);
        assertEquals(200, player.getScore(), "Score should be updated to 200");
    }

    /**
     * Tests interaction between Player and PetManager.
     * Verifies the Player's PetManager is correctly initialized.
     */
    @Test
    void testPetManagerInitialization() {
        Pet pet = petManager.getPetbyIndex(0);
        assertNotNull(pet, "PetManager should contain pets");
    }

    /**
     * Tests interaction between Player and InventoryManager.
     * Verifies the Player's InventoryManager is correctly initialized.
     */
    @Test
    void testInventoryManagerInitialization() {
        assertNotNull(player.Inventory, "InventoryManager should be initialized correctly");
    }

    /**
     * Tests the Player's score and playtime after a series of game actions.
     * This test assumes that pet and inventory interactions can affect these attributes.
     */
    @Test
    void testPlayerProgress() {
        // Increase playtime and score after some interactions (simulated).
        player.setPlaytime(player.getPlaytime() + 10);
        player.setScore(player.getScore() + 50);

        assertEquals(20, player.getPlaytime(), "Playtime should be increased after game progress");
        assertEquals(150, player.getScore(), "Score should be increased after game progress");
    }
}
