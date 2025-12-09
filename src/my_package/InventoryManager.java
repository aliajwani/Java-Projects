package my_package;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.ArrayList;

/**
 * Manages the inventory system for a virtual pet, handling both food and gift items.
 * This class provides functionality to store, retrieve, and manage items that can be given to the pet.
 * It includes persistent storage using JSON files for both food and gift inventories.
 */
public class InventoryManager {
    private Pet pet;
    private Food[] foodItems = new Food[6]; // Holds food items
    private Gift[] giftItems = new Gift[6]; // Holds gift items
    private static final String FOOD_FILE = "src/my_package/assets/data/foodInventory.json";
    private static final String GIFT_FILE = "src/my_package/assets/data/giftInventory.json";
    private final ObjectMapper mapper = new ObjectMapper(); // Jackson ObjectMapper

    /**
     * Constructs a new InventoryManager for the specified pet.
     * Initializes the inventory and loads existing items from JSON files.
     * @param pet The pet instance to manage inventory for
     */
    public InventoryManager(Pet pet) {
        this.pet = pet;
        loadFoodInventory();
        loadGiftInventory();
    }

    /**
     * Adds a food item to the inventory if there's an empty slot available.
     * @param food The food item to add to the inventory
     * @return true if the food was successfully added, false if no empty slots are available
     */
    public boolean addFood(Food food) {
        for (int i = 0; i < foodItems.length; i++) {
            if (foodItems[i] == null) { // Check for empty slot
                foodItems[i] = food; // Add food to the empty slot
                saveFoodInventory(); // Save immediately after adding
                return true; // Successfully added food
            }
        }
        return false; // No empty slot available
    }

    /**
     * Removes a food item from the specified index in the inventory.
     * @param index The index of the food item to remove (0-5)
     */
    public void removeFood(int index) {
        foodItems[index] = null; // Make the slot empty
        saveFoodInventory(); // Save immediately after removing
    }

    /**
     * Adds a gift item to the inventory if there's an empty slot available.
     * @param gift The gift item to add to the inventory
     * @return true if the gift was successfully added, false if no empty slots are available
     */
    public boolean addGift(Gift gift) {
        for (int i = 0; i < giftItems.length; i++) {
            if (giftItems[i] == null) { // Check for empty slot
                giftItems[i] = gift; // Add gift to the empty slot
                saveGiftInventory(); // Save immediately after adding
                return true; // Successfully added gift
            }
        }
        return false; // No empty slot available
    }

    /**
     * Removes a gift item from the specified index in the inventory.
     * @param index The index of the gift item to remove (0-5)
     */
    public void removeGift(int index) {
        giftItems[index] = null; // Make the slot empty
        saveGiftInventory(); // Save immediately after removing
    }

    /**
     * Gives food to the pet from the specified inventory slot and updates the pet's fullness.
     * The food item is removed from the inventory after being given.
     * @param index The index of the food item to give (0-5)
     */
    public void giveFood(int index) {
        Food food = foodItems[index];
        if (food != null) {
            pet.setFullness(pet.getFullness() + food.getRating()); // Update pet's fullness based on food rating
            foodItems[index] = null; // Remove food from inventory
            saveFoodInventory(); // Save immediately after removing
        }
    }

    /**
     * Gives a gift to the pet from the specified inventory slot and updates the pet's happiness.
     * The gift item is removed from the inventory after being given.
     * @param index The index of the gift item to give (0-5)
     */
    public void giveGift(int index) {
        Gift gift = giftItems[index];
        if (gift != null) {
            pet.setHappiness(pet.getHappiness() + gift.getRating()); // Update pet's happiness based on gift rating
            giftItems[index] = null; // Remove gift from inventory
            saveGiftInventory(); // Save immediately after removing
        }
    }

    /**
     * Checks if the food slot at the specified index is empty.
     * @param index The index to check (0-5)
     * @return true if the slot is empty, false otherwise
     */
    public boolean isFoodSlotEmpty(int index) {
        return foodItems[index] == null; // Check if the item at the index is null
    }

    /**
     * Checks if the gift slot at the specified index is empty.
     * @param index The index to check (0-5)
     * @return true if the slot is empty, false otherwise
     */
    public boolean isGiftSlotEmpty(int index) {
        return giftItems[index] == null; // Check if the item at the index is null
    }
    
    /**
     * Gets the type of food item at the specified index.
     * @param index The index of the food item (0-5)
     * @return The type of the food item at the specified index
     */
    public String getFoodTypeAtIndex(int index) {
        return foodItems[index].getType(); // Assuming Food has
    }

    /**
     * Gets the type of gift item at the specified index.
     * @param index The index of the gift item (0-5)
     * @return The type of the gift item at the specified index
     */
    public String getGiftTypeAtIndex(int index) {
        return giftItems[index].getType(); // Assuming Gift has a getType() method
    } 
            
    /**
     * Loads the food inventory from the JSON file.
     * If there's an error loading the file, it will be printed to the console.
     */
    private void loadFoodInventory() {
        File file = new File(FOOD_FILE);
        try{
            foodItems = mapper.readValue(file, Food[].class);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Loads the gift inventory from the JSON file.
     * If there's an error loading the file, it will be printed to the console.
     */
    private void loadGiftInventory() {
        File file = new File(GIFT_FILE);
        try{
            giftItems = mapper.readValue(file, Gift[].class);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Saves the current food inventory to the JSON file.
     * If there's an error saving the file, it will be printed to the error stream.
     */
    public void saveFoodInventory(){
        try{
            mapper.writeValue(new File(FOOD_FILE), foodItems);
        }catch(Exception e){
            System.err.println(e);
        }
    }

    /**
     * Saves the current gift inventory to the JSON file.
     * If there's an error saving the file, it will be printed to the console.
     */
    private void saveGiftInventory() {
        try {
            mapper.writeValue(new File(GIFT_FILE), giftItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}