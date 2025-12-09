package my_package;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Represents a virtual pet with various attributes like health, happiness, and fullness.
 * @author Oizedon
 */
public class Pet {
    private String type;
    private final int[][] stats={ {50, 50, 50, 50}, {50, 75, 100, 75}, {75, 100, 75, 50}, {100, 50, 75, 100} };
    private String name;
    private int health;
    private int happiness;
    private int fullness;
    private int sleep;
    private int cooldown;
    private String state;
    private int score;
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /**
     * Constructs a new Pet with the specified attributes.
     * @param name The name of the pet
     * @param type The type of pet (owl, cat...)
     */
    public Pet(String name, String type) {
        this.type = type;
        
        this.name = name;
        this.health = stats[typeToInt(this.type)][0];
        this.happiness = stats[typeToInt(this.type)][1];
        this.fullness = stats[typeToInt(this.type)][2];
        this.sleep = stats[typeToInt(this.type)][3];
        this.cooldown = 0;
        this.state = "default";
        this.score=0;
    }
    
    /**
     * Default constructor for Jackson 
     */
    public Pet() {
        // Default values
        this.name = null;
        this.type = null;
        this.health = stats[typeToInt(this.type)][0];
        this.happiness = stats[typeToInt(this.type)][1];
        this.fullness = stats[typeToInt(this.type)][2];
        this.sleep = stats[typeToInt(this.type)][3];
        this.cooldown = 0;
        this.state = "default";
        this.score=0;
    }
    /**
     * Revives the pet, sets all stats to max.
     * 
     */
    public void revivePet(){
        this.health = stats[typeToInt(this.type)][0];
        this.happiness = stats[typeToInt(this.type)][1];
        this.fullness = stats[typeToInt(this.type)][2];
        this.sleep = stats[typeToInt(this.type)][3];
        this.cooldown = 0;
        this.state = "default";
    }
    
    /**
     * Gets the name of the pet.
     * @return The pet's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the health value of the pet.
     * @return The pet's health value
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * Gets the happiness value of the pet.
     * @return The pet's happiness value
     */
    public int getHappiness() {
        return happiness;
    }
    
    /**
     * Gets the fullness value of the pet.
     * @return The pet's fullness value
     */
    public int getFullness() {
        return fullness;
    }
    
    /**
     * Gets the sleep value of the pet.
     * @return The pet's sleep value
     */
    public int getSleep() {
        return sleep;
    }
    
    /**
     * Gets the cooldown value of the pet.
     * @return The pet's cooldown value
     */
    public int getCooldown() {
        return cooldown;
    }
    
    /**
     * Gets the current state of the pet.
     * @return The pet's current state
     */
    public String getState() {
        return state;
    }
    /**
     * Gets the current type of the pet.
     * @return The pet's current type
     */
    public String getType(){
        return type;
    }
    /**
     * Gets the current score.
     * @return This saves score
     */
    public int getScore(){
        return score;
    }

    /**
     * Sets the name of the pet.
     * @param name The new name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Sets the current type of the pet.
     * @param type the new type of the pet
     */
    public void setType(String type){
        this.type = type;
    }
    /**
     * Sets the health value of the pet.
     * @param health The new health value to set
     */
    public void setHealth(int health) {
        int oldValue = this.health;
        this.health = health;
        changes.firePropertyChange("health", oldValue, health);
    }
    
    /**
     * Sets the happiness value of the pet.
     * @param happiness The new happiness value to set
     */
    public void setHappiness(int happiness) {
        int oldValue = this.happiness;
        this.happiness = happiness;
        changes.firePropertyChange("happiness", oldValue, happiness);
    }
    
    /**
     * Sets the fullness value of the pet.
     * @param fullness The new fullness value to set
     */
    public void setFullness(int fullness) {
        int oldValue = this.fullness;
        this.fullness = fullness;
        changes.firePropertyChange("fullness", oldValue, fullness);
    }
    
    /**
     * Sets the sleep value of the pet.
     * @param sleep The new sleep value to set
     */
    public void setSleep(int sleep) {
        int oldValue = this.sleep;
        this.sleep = sleep;
        changes.firePropertyChange("sleep", oldValue, sleep);
    }
    
    /**
     * Sets the score for this pet.
     * @param score integer score value
     */
    public void setScore(int score) {
        int oldValue=this.score;
        this.score = score;
        changes.firePropertyChange("score", oldValue, score);
    }
    
    /**
     * Sets the state of the pet.
     * @param state The new state to set
     */
    public void setState(String state) {
        String oldValue = this.state;
        this.state = state;
        changes.firePropertyChange("state", oldValue, state);
    }
    
    /**
     * Sets the cool down value of the pet.
     * @param cooldown The new cool down value to set
     */
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    
    /**
     * Sets the state of the pet based on stats. 
     * 
     */
    public void setState() {
        if ("default".equals(this.getState())){
            if (this.sleep<1){
                this.setState("asleep");
                this.health=this.health-15;
            }
            else if (this.fullness<1) this.setState("hungry");
            else if (this.happiness<1) this.setState("angry");
            else this.setState("default");
        }
        if (this.health<1) this.setState("dead");
    }

    /**
     * Updates the current stats
     * @param listener The listener used to get the change
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    /**
     * Gets the max health of the pet.
     * @return the pets max value
     */
    @JsonIgnore
    public int getMaxHealth() {
        return stats[typeToInt(this.type)][0];
    }
    /**
     * Gets the max happiness of the pet.
     * @return the pets max value
     */
    @JsonIgnore
    public int getMaxHappiness() {
        return stats[typeToInt(this.type)][1];
    }
    /**
     * Gets the max fullness of the pet.
     * @return the pets max value
     */
    @JsonIgnore
    public int getMaxFullness() {
        return stats[typeToInt(this.type)][2];
    }
    /**
     * Gets the max sleep of the pet.
     * @return the pets max value
     */
    @JsonIgnore
    public int getMaxSleep() {
        return stats[typeToInt(this.type)][3];
    }
    /**
     * Used after adding to stat to make sure it is within the correct limit
     * if too high, limit at max. If too low limit at 0.
     */ 
    public void statLimiter() {
        if (this.health>stats[typeToInt(this.type)][0]) this.health=stats[typeToInt(this.type)][0];
        if (this.happiness>stats[typeToInt(this.type)][1]) this.health=stats[typeToInt(this.type)][1];
        if (this.fullness>stats[typeToInt(this.type)][2]) this.health=stats[typeToInt(this.type)][2];
        if (this.sleep>stats[typeToInt(this.type)][3]) this.health=stats[typeToInt(this.type)][3];
        if (this.health<0) this.health=0;
        if (this.happiness<0) this.happiness=0;
        if (this.fullness<0) this.fullness=0;
        if (this.sleep<0) this.sleep=0;
        if (this.score<0) this.score=0;
    }
    
    
    /**
     * Used after adding to stat to make sure it is within the correct limit
     * if too high, limit at max. If too low limit at 0.
     * @param type the type of pet (cat, owl...)
     * @return return the array index for the correlated pet
     */ 
    private int typeToInt(String type){
        if(type==null) return 0;
        else if (type.equals("cat")) return 1;
        else if (type.equals("fox")) return 2;
        else if (type.equals("owl")) return 3;
        
        return 0;
    }
}
