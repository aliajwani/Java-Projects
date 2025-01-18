/**
 * Configurations.java
 * Ali Ajwani
 * 251374819 - aajwani2
 * October 27, 2024
 *
 * This class represents the game configurations, including the game board,
 * board size, winning conditions, and maximum levels for the game.
 */
public class Configurations {
    private char[][] board; // A 2D array that represents the game board
    private int boardSize; // The size of the board 
    private int lengthToWin; // The number of consecutive symbols needed to win the game
    private int maxLevels; // The maximum number of levels for the game

    /**
     * This function constructs a new board
     *
     * @param boardSize   the size of the board
     * @param lengthToWin the number of consecutive symbols needed to win
     * @param maxLevels   the maximum number of levels for the game
     */
    public Configurations(int boardSize, int lengthToWin, int maxLevels) {
        this.boardSize = boardSize;
        this.lengthToWin = lengthToWin;
        this.maxLevels = maxLevels;
        this.board = new char[boardSize][boardSize];

        // Initializes the board with empty spaces
        int i = 0;
        int j = 0;

        while (i < boardSize) {
            board[i][j] = ' ';
            j++;

            if (j == boardSize) {
                j = 0;
                i++;
            }
        }
    }

    /**
     * This function returns a new HashDictionary with a size of 8000.
     *
     * @return a new HashDictionary 
     */
    public HashDictionary createDictionary() {
        return new HashDictionary(8000);
    }

    /**
     * This function checks if the current board configuration is already in the dictionary.
     *
     * @param hashTable the hash dictionary to check against
     * @return the score of the configuration if found, otherwise -1
     */
    public int repeatedConfiguration(HashDictionary hashTable) {
        String config = boardToString();
        return hashTable.get(config);
    }

    /**
     * This function adds the current board configuration and its score to the dictionary.
     *
     * @param hashTable the hash dictionary to add the configuration to
     * @param score     the score associated with the configuration
     */
    public void addConfiguration(HashDictionary hashTable, int score) {
        String config = boardToString();
        try {
            hashTable.put(new Data(config, score));
        } catch (DictionaryException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function saves a play on the board at the specified row and column with the given symbol.
     *
     * @param row    the row index
     * @param col    the column index
     * @param symbol the symbol to place
     */
    public void savePlay(int row, int col, char symbol) {
        board[row][col] = symbol;
    }

    /**
     * This function checks if a square is empty.
     *
     * @param row the row index
     * @param col the column index
     * @return true if the square is empty, false otherwise
     */
    public boolean squareIsEmpty(int row, int col) {
        return board[row][col] == ' ';
    }

    
    /**
    * This function checks if a given symbol has won the game by checking all possible lines (rows, columns, diagonals).
     *
     * @param symbol the symbol to check for a win
     * @return true if the symbol has a winning configuration, false otherwise
     */
    public boolean wins(char symbol) {
        int i = 0;
        while (i < boardSize) {
            int j = 0;
            while (j < boardSize) {
                if (checkLine(i, j, symbol)) {
                    return true;
                }
                j++;
            }
            i++;
        }
        return false;
    }
    
    /**
     * This function checks if the game is a draw, meaning no empty squares and no winners.
     *
     * @return true if the game is a draw, false otherwise
     */
    public boolean isDraw() {
        int i = 0;
        int j = 0;

        while (i < boardSize) {
            if (board[i][j] == ' ') {
                return false;
            }
            j++;

            if (j == boardSize) {
                j = 0;
                i++;
            }
        }

        return !wins('X') && !wins('O');
    }

    /**
     * This function evaluates the board and returns a score.
     *
     * @return 3 if 'O' wins, 0 if 'X' wins, 2 for a draw, and 1 if the game is ongoing
     */
    public int evalBoard() {
        if (wins('O')) {
            return 3;
        } else if (wins('X')) {
            return 0;
        } else if (isDraw()) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * This function converts the board to a string representation.
     *
     * @return a string representation of the board
     */
    private String boardToString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int j = 0;

        while (i < boardSize) {
            sb.append(board[i][j]);
            j++;

            if (j == boardSize) {
                j = 0;
                i++;
            }
        }
        return sb.toString();
    }

    
    

    /**
    * This function checks if there is a winning line (row, column, or diagonal) starting at the specified row and column for the given symbol.
    *
    * @param row    the starting row index
    * @param col    the starting column index
    * @param symbol the symbol to check for a winning line
    * @return true if there is a winning line, false otherwise
    */
    private boolean checkLine(int row, int col, char symbol) {
        // Check row (left to right)
        if (col + lengthToWin <= boardSize) {
            boolean rowWin = true;
            int i = 0;
            while (i < lengthToWin) {
                if (board[row][col + i] != symbol) {
                    rowWin = false;
                    break;
                }
                i++;
            }
            if (rowWin) return true;
        }
    
        // Check column (top to bottom)
        if (row + lengthToWin <= boardSize) {
            boolean colWin = true;
            int i = 0;
            while (i < lengthToWin) {
                if (board[row + i][col] != symbol) {
                    colWin = false;
                    break;
                }
                i++;
            }
            if (colWin) return true;
        }
    
        // Check diagonal (top-left to bottom-right)
        if (row + lengthToWin <= boardSize && col + lengthToWin <= boardSize) {
            boolean diag1Win = true;
            int i = 0;
            while (i < lengthToWin) {
                if (board[row + i][col + i] != symbol) {
                    diag1Win = false;
                    break;
                }
                i++;
            }
            if (diag1Win) return true;
        }
    
        // Check diagonal (bottom-left to top-right)
        if (row - lengthToWin + 1 >= 0 && col + lengthToWin <= boardSize) {
            boolean diag2Win = true;
            int i = 0;
            while (i < lengthToWin) {
                if (board[row - i][col + i] != symbol) {
                    diag2Win = false;
                    break;
                }
                i++;
            }
            if (diag2Win) return true;
        }
    
        return false;
    }
       
}
