package my_package;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for the ParentalManager class.
 */
class ParentalManagerTest {
    
    private ParentalManager parentalManager;
    private Pet testPet;

    /**
     * Sets up test objects before each test.
     */
    @BeforeEach
    void setUp() {
        parentalManager = new ParentalManager();
        testPet = new Pet("TestPet", "Cat"); // Ensure Pet is properly initialized
    }

    /**
     * Tests setting and retrieving the number of play sessions.
     */
    @Test
    void testSetAndGetNumSessions() {
        parentalManager.setNumSessions(5);
        assertEquals(5, parentalManager.getNumSessions(), "Number of sessions should be 5");
    }

    /**
     * Tests setting and retrieving the time limit.
     */
    @Test
    void testSetTimeLimit() {
        parentalManager.setTimeLimit(120);
        assertEquals(120, parentalManager.getTimeLimit(), "Time limit should be 120 minutes");
    }

    /**
     * Tests setting the initial password.
     */
    @Test
    void testSetPasswordInitially() throws Exception {
        parentalManager.setPassword("secure123");
        assertEquals("secure123", parentalManager.getPassword(), "Password should be 'secure123'");
    }

    /**
     * Tests setting a new password after the initial password is set.
     */
    @Test
    void testChangePassword() throws Exception {
        parentalManager.setPassword("oldPass");
        parentalManager.setPassword("newPass", "oldPass");
        assertEquals("newPass", parentalManager.getPassword(), "Password should be updated to 'newPass'");
    }

    /**
     * Tests that setting the same password throws an exception.
     */
    @Test
    void testChangePasswordToSame() throws Exception {
        parentalManager.setPassword("password123");
        Exception exception = assertThrows(Exception.class, () -> {
            parentalManager.setPassword("password123", "password123");
        });
        assertEquals("Same Password", exception.getMessage(), "Exception message should indicate same password error");
    }

    /**
     * Tests that setting a new password with a wrong old password throws an exception.
     */
    @Test
    void testChangePasswordWrongOldPassword() throws Exception {
        parentalManager.setPassword("correctPass");
        Exception exception = assertThrows(Exception.class, () -> {
            parentalManager.setPassword("newPass", "wrongPass");
        });
        assertEquals("Wrong Password", exception.getMessage(), "Exception message should indicate wrong password error");
    }

    /**
     * Tests the avgPlayTime method calculation.
     */
    @Test
    void testAvgPlayTime() {
        parentalManager.setNumSessions(4);
        parentalManager.setPlayTime(120); // Ensure playTime is set
        assertEquals(30, parentalManager.avgPlayTime(), "Average play time should be 30 minutes");
    }

    /**
     * Tests reviving a pet.
     */
    @Test
    void testRevivePet() {
        parentalManager.getPets().add(testPet); // Add pet to the list
        testPet.setHealth(50); // Set health to a non-max value
        boolean revived = parentalManager.revivePet(testPet);
        assertTrue(revived, "Pet should be revived");
        assertEquals(100, testPet.getHealth(), "Pet health should be restored to 100");
    }

    /**
     * Tests reviving a pet not in the list.
     */
    @Test
    void testRevivePetNotInList() {
        Pet anotherPet = new Pet("AnotherPet", "Dog");
        boolean revived = parentalManager.revivePet(anotherPet);
        assertFalse(revived, "Pet should not be revived since it is not in the list");
    }
}
