package my_package;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for the GameManager class.
 * It tests various pet-related functionalities such as sleeping, playing, exercising, and visiting the vet.
 */
class GameManagerTest {
    
    private GameManager gameManager;
    private Pet pet;

    /**
     * Sets up a fresh pet instance and game manager before each test.
     */
    @BeforeEach
    void setUp() {
        pet = new Pet(); // Assuming Pet has a default constructor
        gameManager = new GameManager(pet);
    }

    /**
     * Tests the sleepPet method to ensure sleep is set to maximum and score increases.
     */
    @Test
    void testSleepPet() {
        pet.setSleep(5);
        pet.setScore(10);
        gameManager.sleepPet();
        assertEquals(pet.getMaxSleep(), pet.getSleep(), "Pet's sleep should be at max after sleeping.");
        assertEquals(11, pet.getScore(), "Pet's score should increase by 1 after sleeping.");
    }

    /**
     * Tests the vetPet method to ensure health is set to maximum and score decreases.
     */
    @Test
    void testVetPet() {
        pet.setHealth(50);
        pet.setScore(20);
        gameManager.vetPet();
        assertEquals(pet.getMaxHealth(), pet.getHealth(), "Pet's health should be at max after visiting the vet.");
        assertEquals(10, pet.getScore(), "Pet's score should decrease by 10 after visiting the vet.");
    }

    /**
     * Tests the playPet method to ensure happiness increases while sleep and fullness decrease.
     */
    @Test
    void testPlayPet() {
        pet.setHappiness(10);
        pet.setSleep(10);
        pet.setFullness(10);
        pet.setScore(5);
        gameManager.playPet();
        assertEquals(15, pet.getHappiness(), "Pet's happiness should increase by 5 after playing.");
        assertEquals(8, pet.getSleep(), "Pet's sleep should decrease by 2 after playing.");
        assertEquals(8, pet.getFullness(), "Pet's fullness should decrease by 2 after playing.");
        assertEquals(6, pet.getScore(), "Pet's score should increase by 1 after playing.");
    }

    /**
     * Tests the exercisePet method to ensure health increases while sleep and fullness decrease.
     */
    @Test
    void testExercisePet() {
        pet.setHealth(30);
        pet.setSleep(10);
        pet.setFullness(10);
        pet.setScore(2);
        gameManager.exercisePet();
        assertEquals(34, pet.getHealth(), "Pet's health should increase by 4 after exercising.");
        assertEquals(8, pet.getSleep(), "Pet's sleep should decrease by 2 after exercising.");
        assertEquals(8, pet.getFullness(), "Pet's fullness should decrease by 2 after exercising.");
        assertEquals(3, pet.getScore(), "Pet's score should increase by 1 after exercising.");
    }
}
