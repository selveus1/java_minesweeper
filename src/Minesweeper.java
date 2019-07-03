import java.util.Arrays;
import java.util.Random;

public class Minesweeper {

    private final int BEGINNER_GRID_DIM = 8;
    private final int BEGINNER_NUM_MINES = 10;

    private int N; // dimensions for grids
    private String[][] minefield;
    private boolean[][] display;

    private boolean isWon; // Checks if the player is done (has detonated a mine OR uncovered all non-mines) with the game
    private boolean isDone; // Start as false in order for player to keep playing

    private final String MINE = "*";
    private final String EMPTY = "-";

    private int cellsUncovered;
    private int mineRatio; // Number of mines to be placed on field


    public Minesweeper(int gridSize){
        N = gridSize;

        minefield = new String[N][N];
        display = new boolean[N][N];

        mineRatio = (N * BEGINNER_NUM_MINES) / BEGINNER_GRID_DIM;
        cellsUncovered = N*N;
    }


    public void setUpField(){

        // Initialize field with empty strings
        initFieldWithStrings();

        // Generate mines for the minefield
        generateMines();

        // Iterate thru field and update cells that have neighboring mines near them
        generateNumbers();

        // Set isDone as FALSE
        isDone = false;

    }



    public void displayField() {

        // Iterate thru display grid
            // if cell == TRUE, then field value is displayed to player
            // if cell == FALSE, then field value is not displayed to player

        System.out.print("    ");
        for(int k = 0; k < N; k++){

            System.out.print(k + "   ");
        }
        System.out.println("");

        //System.out.println("  -----------------------------------------");
        for(int i = 0; i < N; i++){

            System.out.print(i + "  ");
            //System.out.print(" | ");

            for(int j = 0; j < N; j++){

                if(display[i][j]) {
                    System.out.print(minefield[i][j] + " | ");
                } else {
                    System.out.print("  | ");
                }
            }
            System.out.println("");
            System.out.println("  -----------------------------------------");
        }
    }


    public void selectCell(int xPosition, int yPosition) {

        // Validation should be done by the time this method is called
        if (minefield[xPosition][yPosition].equalsIgnoreCase(MINE)) {
            // Mine has been detonated, game is over
            isDone = true;
            isWon = false;
            uncoverDisplay();
            return;
        }


        if(minefield[xPosition][yPosition].equalsIgnoreCase(EMPTY)) {
            clearArea(xPosition, yPosition);
        }
        else { // Only reveal a number
            display[xPosition][yPosition] = true;
            cellsUncovered--;
        }

        // Check if player has uncovered all possible non-mine cells
        hasWon();

    }


    private void hasWon(){

        //If there is no more cells left to uncover and no mines have been detonated, player wins
        if( cellsUncovered == ( (N*N) - mineRatio ) && !isDone) {
            isWon = true;
            isDone = true;
        }
    }


    private void clearArea(int x, int y){
        // Display all empty cell near position (x,y) until a number is encountered (maybe use BFS)
        if( x>=0 && x < N && y >=0 && y < N && !display[x][y]) {

            // Either empty space or number, so clear it
            if(minefield[x][y].equalsIgnoreCase(EMPTY) || !minefield[x][y].equalsIgnoreCase(MINE)) {
                display[x][y] = true;
                cellsUncovered--;

                // Empty cell so explore some more
                if(minefield[x][y].equalsIgnoreCase(EMPTY)) {
                    clearArea(x-1, y-1);
                    clearArea(x-1, y);
                    clearArea(x-1, y+1);

                    clearArea(x, y-1);
                    clearArea(x, y+1);

                    clearArea(x+1, y-1);
                    clearArea(x+1, y);
                    clearArea(x+1, y+1);
                }
            }
        }

        return;
    }



    public boolean getIsWon() {
        return isWon;
    }



    public boolean getIsDone() {
        return isDone;
    }



    public int getN() {
        return N;
    }


    private void initFieldWithStrings(){
        for (String[] row: minefield)
            Arrays.fill(row, "");

    }


    private void uncoverDisplay() {
        for (boolean[] row: display)
            Arrays.fill(row, true);
    }


    private void generateMines(){

        // Get random generator
        Random rand = new Random();


        // Generate 12 mines for the entire grid
        int minesPlaced = 0;
        while(minesPlaced < mineRatio) {

            // Generate random X position in range 0 to N
            int randomX = rand.nextInt(N);

            // Generate random Y position in range 0 to N
            int randomY = rand.nextInt(N);

            // Place mine in minefield
            if(!minefield[randomX][randomY].equalsIgnoreCase(MINE) && (randomX != 0 && randomY != 0)) {
                minefield[randomX][randomY] = MINE;

                // Increment minesPlaced
                minesPlaced++;
            }
        }



    }


    private void generateNumbers(){

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){

                if(!minefield[i][j].equalsIgnoreCase(MINE)) {

                    int numMines = 0;

                    // Top row
                    if( (i-1) >=0 && (j-1) >= 0 && minefield[i-1][j-1].equalsIgnoreCase(MINE)) {
                        numMines++;
                    }

                    if( (i-1) >=0 && minefield[i-1][j].equalsIgnoreCase(MINE)) {
                        numMines++;
                    }

                    if( (i-1) >=0 && (j+1) < N && minefield[i-1][j+1].equalsIgnoreCase(MINE)) {
                        numMines++;
                    }

                    // Middle row
                    if( (j-1) >= 0 && minefield[i][j-1].equalsIgnoreCase(MINE)) {
                        numMines++;
                    }

                    if( (j+1) < N && minefield[i][j+1].equalsIgnoreCase(MINE)) {
                        numMines++;
                    }

                    // Bottom row
                    if( (i+1) < N && (j-1) >= 0 && minefield[i+1][j-1].equalsIgnoreCase(MINE)) {
                        numMines++;
                    }
                    if( (i+1) < N && minefield[i+1][j].equalsIgnoreCase(MINE)) {
                        numMines++;
                    }

                    if( (i+1) < N && (j+1) < N && minefield[i+1][j+1].equalsIgnoreCase(MINE)) {
                        numMines++;
                    }

                    // Set current cell to number of mines
                    if(numMines > 0)
                        minefield[i][j] = String.valueOf(numMines);
                    else
                        minefield[i][j] = EMPTY;

                }

            }
        }
    }


}
