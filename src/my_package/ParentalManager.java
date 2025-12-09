package my_package;

import java.util.ArrayList;

/**
 * Manages parental controls and monitoring features for the virtual pet game.
 * This class handles password protection, session tracking, play time monitoring,
 * and pet revival functionality.
 */
public class ParentalManager {
    private int playTime;
    private String password;
    private ArrayList<Pet> pets = new ArrayList<Pet>();
    private int numSessions;
    private int timeLimit = 1441;

    /**
     * Constructs a new ParentalManager with default settings.
     * Initializes an empty list of pets and sets default time limit.
     */
    public ParentalManager() {

    }

    /**
     * Sets the number of play sessions.
     * @param numSessions The number of sessions to set
     */
    public void setNumSessions(int numSessions) {
        this.numSessions = numSessions;
    }

    /**
     * Sets the time limit for play sessions in minutes.
     * @param timeLimit The time limit in minutes to set
     */
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    /**
     * Sets a new password, requiring verification of the old password.
     * @param new_password The new password to set
     * @param old_password The current password for verification
     * @throws Exception if the old password is incorrect or if the new password is the same as the old one
     */
    public void setPassword(String new_password, String old_password) throws Exception{
        if(this.password == null){
            this.password = new_password;
        } else{
            if(new_password.equals(old_password)){
                throw new Exception("Same Password");
            }else if(!this.password.equals(new_password)){
                throw new Exception("Wrong Password");
            }else{
                this.password = new_password;
            }
        }
    }

    /**
     * Sets the initial password for the parental controls.
     * @param password The password to set
     * @throws Exception if a password has already been set
     */
    public void setPassword(String password) throws Exception {
        if(this.password == null){
            this.password = password;
        } else{
            throw new Exception("Password already set, enter old password");
        }
    }

    /**
     * Gets the current password.
     * @return The current password
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Gets the number of play sessions.
     * @return The number of sessions
     */
    public int getNumSessions() {
        return numSessions;
    }

    /**
     * Calculates the average play time per session.
     * @return The average play time in minutes
     */
    public int avgPlayTime(){
        return playTime / numSessions;
    }

    /**
     * Revives a pet by restoring its health to maximum.
     * @param pet The pet to revive
     * @return true if the pet was successfully revived, false if the pet is not in the managed list
     */
    public boolean revivePet(Pet pet){
        if(pets.contains(pet)){
            pet.setHealth(100);
            return true;
        }else{
            return false;
        }
    }
}
