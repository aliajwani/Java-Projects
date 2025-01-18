/**
 * Data.java
 * Ali Ajwani
 * 251374819 - aajwani2
 * October 27, 2024
 * 
 * The Data class represents a data structure containing a configuration string and a score.
 */
public class Data {

    /**
     * The configuration string for this Data object.
     */
    private String config;
 
    /**
     * The score associated with this Data object.
     */
    private int score;
 
    /**
     * Constructs a new Data object with a specified configuration and score.
     *
     * @param var1 the configuration string to set
     * @param var2 the score to set
     */
    public Data(String var1, int var2) {
       this.config = var1;
       this.score = var2;
    }
 
    /**
     * Gets the configuration string of this Data object.
     *
     * @return the configuration string
     */
    public String getConfiguration() {
       return this.config;
    }
 
    /**
     * Gets the score of this Data object.
     *
     * @return the score
     */
    public int getScore() {
       return this.score;
    }
 }