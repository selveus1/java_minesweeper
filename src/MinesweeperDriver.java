import java.util.Scanner;

public class MinesweeperDriver {

    private Minesweeper minesweeper;
    private Scanner scanner;
    private final int LOWEST_DIM = 8;
    private final int HIGHEST_DIM = 100;

    public MinesweeperDriver(){
        scanner = new Scanner(System.in);
    }


    public void initialize(){


        // Get player to enter value for dimensions for grid
        int dimensions = 0;
        boolean areDimensionsValid = false;
        while(!areDimensionsValid) {
            System.out.println("Enter in a value for the grid size between 8 and 100. Remember -> the bigger the grid, the higher the difficulty.");
            dimensions = scanner.nextInt();

            if(dimensions >= LOWEST_DIM && dimensions <= HIGHEST_DIM) {
                areDimensionsValid = true;
            } else {
                System.out.println("Value was not valid. Enter a grid size between 8 and 100.");
            }
        }

        minesweeper = new Minesweeper(dimensions);
        minesweeper.setUpField();

   }


    public void run(){

        // Display field
        minesweeper.displayField();

        // While the game is not done, continue looping through and playing the game.
        while( !minesweeper.getIsDone() ) {

            // Player selects a cell
            System.out.println("Enter in a value for X between 0 and " + (minesweeper.getN() - 1) + ": ");
            int xPosition = scanner.nextInt();

            System.out.println("Enter in a value for Y between 0 and " + (minesweeper.getN() - 1) + ": ");
            int yPosition = scanner.nextInt();

            // Validate the inputs
            if( xPosition < 0 || xPosition > minesweeper.getN() || yPosition < 0 || yPosition > minesweeper.getN() ) {
                System.out.println("Values must be X and/or Y must between 0 and " + (minesweeper.getN() - 1) + ". Try again. ");
                continue;
            }

            // Coordinates are sent to the game
            minesweeper.selectCell(xPosition, yPosition);

            // Game state (won, done, etc.) is determined
            if(minesweeper.getIsWon()){
                System.out.println("Yay!! You've won!!!!!");
                break;
            } else if (minesweeper.getIsDone() && !minesweeper.getIsWon()) {
                System.out.println("Oh no!! You lost!!!");
                break;
            } else {
                // Game continues, keep looping
                minesweeper.displayField();
            }

        }

        minesweeper.displayField(); // Should show entire field uncovered
    }


    public static void main(String[] args) {

        MinesweeperDriver minesweeperDriver = new MinesweeperDriver();

        minesweeperDriver.initialize();
        minesweeperDriver.run();
    }
}
