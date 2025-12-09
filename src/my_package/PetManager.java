package my_package;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;


/**
 * Manages a collection of virtual pets, handling their storage, retrieval, and manipulation.
 * Uses Jackson ObjectMapper for JSON serialization and deserialization of pet data.
 */
public class PetManager {
    private Pet[] pets = new Pet[3];
    private final String PETS_FILE = "src/my_package/assets/data/Pets.json";
    private ObjectMapper mapper;

    /**
     * Constructs a new PetManager and initializes the pets collection.
     * Creates an ObjectMapper instance and loads existing pets from JSON file.
     */
    public PetManager() {
        mapper = new ObjectMapper();
        loadPets();
    }

    /**
     * Loads pets from the JSON file into the pets array.
     * If the file doesn't exist or there's an error, initializes an empty pets array.
     */
    private void loadPets() {
        File file = new File(PETS_FILE);
        try{
            pets = mapper.readValue(file, Pet[].class);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Retrieves a pet by its name from the collection.
     * @param name The name of the pet to find
     * @return The pet with the matching name
     * @throws Exception if no pet with the given name exists
     */
    public Pet getIndexbyName(String name){
        for(Pet pet: pets){
            if(pet.getName().equals(name)){
                return pet;
            }
        }
        return null;
    }

    public Pet getPetbyIndex(int index){
        return pets[index];
    }

    /**
     * Adds a new pet to the first available empty slot in the collection.
     * @param pet The pet to add to the collection
     */
    public void addPet(Pet pet, int index){
        pets[index] = pet;
        savePets();
    }

    /**
     * Removes a pet from the collection and shifts remaining pets to maintain array organization.
     * Pets are shifted forward in the array to fill any gaps created by removal.
     * @param pet The pet to remove from the collection
     */
    public void removePet(Pet pet, int index){
        pets[index] = null;
        savePets();
    }

    public void savePets(){
        try{
        mapper.writeValue(new File(PETS_FILE), pets);
        }catch(Exception e){
            System.err.println(e);
        }
    }
    
    public boolean petExists(Pet pet){
        for (int x=0; x<3; x++){
            if (pets[x]==pet) return true;
        }
        return false;
    }
   
}

