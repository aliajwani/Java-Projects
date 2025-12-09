package my_package;
import my_package.Pet;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;

/**
 * Manages the game logic and state changes for a virtual pet.
 * This class handles periodic updates of pet stats, state changes, and various pet activities.
 * @author Oizedon
 */
public class GameManager {
   Pet pet;
   private Timer timer;
   private final int DECREASE_INTERVAL_MS = 1000; // Decrease every 5 second
   
   /**
    * Constructs a new GameManager with the specified pet.
    * @param pet The pet instance to manage
    */
   public GameManager(Pet pet){
       this.pet = pet;
   }

   /**
    * Puts the pet to sleep, restoring its sleep to maximum and increasing its score.
    */
   public void sleepPet(){
    pet.setSleep(pet.getMaxSleep());
    pet.setScore(pet.getScore()+1);
   }

   /**
    * Takes the pet to the vet, restoring its health to maximum.
    * This action decreases the pet's score as a penalty.
    */
   public void vetPet(){
       pet.setHealth(pet.getMaxHealth());
       pet.setScore(pet.getScore()-10);
       pet.statLimiter();
   }

   /**
    * Allows the pet to play, increasing happiness but decreasing sleep and fullness.
    * This action increases the pet's score.
    */
   public void playPet(){
       pet.setHappiness(pet.getHappiness() + 5);
       pet.setSleep(pet.getSleep() - 2);
       pet.setFullness(pet.getFullness() - 2);
       pet.statLimiter();
       pet.setScore(pet.getScore()+1);
   }

   /**
    * Makes the pet exercise, increasing health but decreasing sleep and fullness.
    * This action increases the pet's score.
    */
   public void exercisePet(){
       pet.setHealth(pet.getHealth() + 4);
       pet.setSleep(pet.getSleep() - 2);
       pet.setFullness(pet.getFullness() - 2);
       pet.statLimiter();
       pet.setScore(pet.getScore()+1);
   }
   
   /**
    * Starts the periodic gameplay loop that updates pet stats and states.
    * This method initializes a timer that runs every second to:
    * - Update pet stats (sleep, happiness, fullness)
    * - Handle special state effects (asleep, hungry, angry)
    * - Manage state transitions based on pet conditions
    * - Update the pet's score
    */
   public void periodicGameplay() {
        timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            private int tickCount = 0;
            @Override
            public void run() {
                // Tick counter to do certain funcions more/less often
                tickCount++;
                // Code to complete pet state requirements
                if("asleep".equals(pet.getState())){
                    pet.setSleep(pet.getSleep()+1);
                }
                if("hungry".equals(pet.getState()) && tickCount%5==0){
                    pet.setHappiness(pet.getHappiness() - 2); // Happiness rate of decline is doubled
                    pet.setHealth(pet.getHealth() - 2);
                }
                if (tickCount%5==0){
                    pet.setScore(pet.getScore()+5);
                    if(!"asleep".equals(pet.getState()))
                        pet.setSleep(pet.getSleep() - 4);
                    pet.setHappiness(pet.getHappiness() - 2);
                    pet.setFullness(pet.getFullness() - 1);
                }
                pet.statLimiter();
                // If pet does not have a state set as default
                // If pet has a state check if it is still in that state
                if ("asleep".equals(pet.getState()) && pet.getSleep()==pet.getMaxSleep())
                        pet.setState("default");
                if ("hungry".equals(pet.getState()) && pet.getFullness()>0)
                        pet.setState("default");
                if ("angry".equals(pet.getState()) && pet.getHappiness()>(pet.getMaxHappiness()/2))
                    pet.setState("default");
                pet.setState();
            }
        }, 0, DECREASE_INTERVAL_MS);
    }

    /**
     * Stops the periodic gameplay loop by canceling the timer.
     * This should be called when the game is being closed or paused.
     */
    public void stopPeriodicGameplay(){
        timer.cancel();
    }
}
