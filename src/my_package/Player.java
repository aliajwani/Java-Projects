package my_package;

/**
 * Represents a player in the virtual pet game.
 * This class manages the player's pet collection, inventory, play time, and score.
 * @author danielantal
 */
public class Player {
    private PetManager PetList;
    /** The inventory manager for the player's items */
    public InventoryManager Inventory;
    private int playtime;
    private int score;

    /**
     * Constructs a new Player with specified initial values.
     * @param petlist The PetManager containing the player's pets
     * @param inventory The InventoryManager containing the player's items
     * @param playtime The initial play time in minutes
     * @param score The initial score
     */
    public Player(PetManager petlist, InventoryManager inventory, int playtime, int score){
        this.PetList = petlist;
        this.Inventory = inventory;
        this.playtime = playtime;
        this.score = score;
    }

    /**
     * Gets the player's total play time.
     * @return The total play time in minutes
     */
    public int getPlaytime(){
        return playtime;
    }

    /**
     * Sets the player's play time.
     * @param p The new play time in minutes
     */
    public void setPlaytime(int p){
        playtime = p;
    }

    /**
     * Gets the player's current score.
     * @return The current score
     */
    public int getScore(){
        return score;
    }

    /**
     * Sets the player's score.
     * @param s The new score value
     */
    public void setScore(int s){
        score = s;
    }
    


}