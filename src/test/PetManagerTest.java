package my_package;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

/**
 * JUnit 5 test class for the PetManager class.
 * Tests functionalities of adding, removing, retrieving, and checking pets.
 */
class PetManagerTest {

    private PetManager petManager;
    private Pet testPet1;
    private Pet testPet2;

    /**
     * Sets up the PetManager and test pets before each test.
     */
    @BeforeEach
    void setUp() {
        petManager = new PetManager();
        testPet1 = new Pet("Fluffy", "cat");
        testPet2 = new Pet("Sparky", "dog");
    }

    /**
     * Tests adding a pet to the PetManager's collection.
     * Verifies that the pet was added by checking the pet by its index.
     */
    @Test
    void testAddPet() {
        petManager.addPet(testPet1, 0);
        assertEquals(testPet1, petManager.getPetbyIndex(0), "Pet should be added to the collection at index 0");
    }

    /**
     * Tests removing a pet from the PetManager's collection.
     * Verifies that the pet was removed by checking the index for null.
     */
    @Test
    void testRemovePet() {
        petManager.addPet(testPet1, 0);
        petManager.removePet(testPet1, 0);
        assertNull(petManager.getPetbyIndex(0), "Pet should be removed and index 0 should be null");
    }

    /**
     * Tests retrieving a pet by name from the collection.
     * Verifies that the pet returned matches the name searched for.
     */
    @Test
    void testGetPetByName() {
        petManager.addPet(testPet1, 0);
        Pet retrievedPet = petManager.getIndexbyName("Fluffy");
        assertEquals(testPet1, retrievedPet, "Pet with the name 'Fluffy' should be retrieved");
    }

    /**
     * Tests retrieving a pet by an index.
     * Verifies that the pet returned matches the pet at the given index.
     */
    @Test
    void testGetPetByIndex() {
        petManager.addPet(testPet1, 0);
        petManager.addPet(testPet2, 1);
        Pet retrievedPet = petManager.getPetbyIndex(1);
        assertEquals(testPet2, retrievedPet, "Pet at index 1 should be 'Sparky'");
    }

    /**
     * Tests checking if a pet exists in the collection.
     * Verifies that the pet exists in the array after being added.
     */
    @Test
    void testPetExists() {
        petManager.addPet(testPet1, 0);
        assertTrue(petManager.petExists(testPet1), "Pet should exist in the collection after being added");
    }

    /**
     * Tests saving the pets to a file.
     * This tests the functionality indirectly through methods that call savePets().
     * Verifies that no exceptions are thrown during the process.
     */
    @Test
    void testSavePets() throws IOException {
        petManager.addPet(testPet1, 0);
        petManager.savePets();
        File file = new File(petManager.PETS_FILE);
        assertTrue(file.exists(), "Pets file should exist after saving pets");
    }

    /**
     * Tests loading pets from the file (indirectly tested via constructor).
     * Verifies that the pets are correctly loaded from the file.
     */
    @Test
    void testLoadPets() {
        petManager.addPet(testPet1, 0);
        petManager.savePets();
        petManager = new PetManager();
        Pet loadedPet = petManager.getPetbyIndex(0);
        assertEquals(testPet1, loadedPet, "Pet should be correctly loaded from the file");
    }
}
