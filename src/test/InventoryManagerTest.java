package my_package;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

/**
 * JUnit 5 test class for the InventoryManager class.
 * Tests functionalities of adding, removing, and giving food and gifts, as well as inventory loading and saving.
 */
class InventoryManagerTest {

    private InventoryManager inventoryManager;
    private Pet testPet;
    private Food testFood1;
    private Gift testGift1;

    /**
     * Sets up the InventoryManager and test pet before each test.
     */
    @BeforeEach
    void setUp() {
        testPet = new Pet("Fluffy", "cat");
        inventoryManager = new InventoryManager(testPet);
        testFood1 = new Food("Fish", 5);  // Assuming Food constructor takes type and rating
        testGift1 = new Gift("Toy", 3);   // Assuming Gift constructor takes type and rating
    }

    /**
     * Tests adding a food item to the inventory.
     * Verifies that the food item was successfully added.
     */
    @Test
    void testAddFood() {
        boolean added = inventoryManager.addFood(testFood1);
        assertTrue(added, "Food should be successfully added to the inventory");
    }

    /**
     * Tests removing a food item from the inventory.
     * Verifies that the food item is removed and the slot is empty.
     */
    @Test
    void testRemoveFood() {
        inventoryManager.addFood(testFood1);
        inventoryManager.removeFood(0);
        assertTrue(inventoryManager.isFoodSlotEmpty(0), "Food slot should be empty after removal");
    }

    /**
     * Tests adding a gift item to the inventory.
     * Verifies that the gift item was successfully added.
     */
    @Test
    void testAddGift() {
        boolean added = inventoryManager.addGift(testGift1);
        assertTrue(added, "Gift should be successfully added to the inventory");
    }

    /**
     * Tests removing a gift item from the inventory.
     * Verifies that the gift item is removed and the slot is empty.
     */
    @Test
    void testRemoveGift() {
        inventoryManager.addGift(testGift1);
        inventoryManager.removeGift(0);
        assertTrue(inventoryManager.isGiftSlotEmpty(0), "Gift slot should be empty after removal");
    }

    /**
     * Tests giving food to the pet and verifying the pet's fullness.
     * Verifies that the food is removed from the inventory and the pet's fullness is updated.
     */
    @Test
    void testGiveFood() {
        inventoryManager.addFood(testFood1);
        inventoryManager.giveFood(0);
        assertTrue(inventoryManager.isFoodSlotEmpty(0), "Food slot should be empty after giving food");
        assertEquals(5, testPet.getFullness(), "Pet's fullness should be updated based on food rating");
    }

    /**
     * Tests giving a gift to the pet and verifying the pet's happiness.
     * Verifies that the gift is removed from the inventory and the pet's happiness is updated.
     */
    @Test
    void testGiveGift() {
        inventoryManager.addGift(testGift1);
        inventoryManager.giveGift(0);
        assertTrue(inventoryManager.isGiftSlotEmpty(0), "Gift slot should be empty after giving gift");
        assertEquals(3, testPet.getHappiness(), "Pet's happiness should be updated based on gift rating");
    }

    /**
     * Tests checking if a food slot is empty.
     * Verifies that the method correctly identifies an empty slot.
     */
    @Test
    void testIsFoodSlotEmpty() {
        assertTrue(inventoryManager.isFoodSlotEmpty(0), "Food slot should initially be empty");
        inventoryManager.addFood(testFood1);
        assertFalse(inventoryManager.isFoodSlotEmpty(0), "Food slot should not be empty after adding food");
    }

    /**
     * Tests checking if a gift slot is empty.
     * Verifies that the method correctly identifies an empty slot.
     */
    @Test
    void testIsGiftSlotEmpty() {
        assertTrue(inventoryManager.isGiftSlotEmpty(0), "Gift slot should initially be empty");
        inventoryManager.addGift(testGift1);
        assertFalse(inventoryManager.isGiftSlotEmpty(0), "Gift slot should not be empty after adding gift");
    }

    /**
     * Tests saving the food inventory to the file.
     * Verifies that no exceptions are thrown and the file exists after saving.
     */
    @Test
    void testSaveFoodInventory() throws IOException {
        inventoryManager.addFood(testFood1);
        inventoryManager.saveFoodInventory();
        File file = new File(InventoryManager.FOOD_FILE);
        assertTrue(file.exists(), "Food inventory file should exist after saving");
    }

    /**
     * Tests saving the gift inventory to the file.
     * Verifies that no exceptions are thrown and the file exists after saving.
     */
    @Test
    void testSaveGiftInventory() throws IOException {
        inventoryManager.addGift(testGift1);
        inventoryManager.saveFoodInventory();
        File file = new File(InventoryManager.GIFT_FILE);
        assertTrue(file.exists(), "Gift inventory file should exist after saving");
    }

    /**
     * Tests loading food and gift inventories from the files.
     * Verifies that the inventories are correctly loaded.
     */
    @Test
    void testLoadInventories() {
        inventoryManager.addFood(testFood1);
        inventoryManager.saveFoodInventory();
        inventoryManager.addGift(testGift1);
        inventoryManager.saveFoodInventory();

        inventoryManager = new InventoryManager(testPet); // Re-create to reload the inventories
        assertEquals(testFood1, inventoryManager.foodItems[0], "Food inventory should be loaded correctly");
        assertEquals(testGift1, inventoryManager.giftItems[0], "Gift inventory should be loaded correctly");
    }
}
