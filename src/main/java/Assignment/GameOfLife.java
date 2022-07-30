package Assignment;

public class GameOfLife {

    public static void main(String[] args) throws InterruptedException {
        boolean[][] randomBoard = getRandomBoard();
        Board board = new Board(randomBoard);
        System.out.println("Generating Random board!!\n");

        Thread.sleep(1000);

        System.out.println("Initial Generation!!");
        board.showBoard();
        System.out.println("\n");
        while (board.isGameCompleted()) {
            board.calculateNextGenerationOfBoard();
            if (board.isGameCompleted()) {
                System.out.println("Next Generation!!");
                board.showBoard();
                System.out.println("\n");
                Thread.sleep(1000);
            }
        }
        System.out.println("Game completed !!");
    }


    private static boolean[][] getRandomBoard() {
        int m = 5;
        int n = 5;
        boolean[][] randomBoard = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                randomBoard[i][j] = Math.random() > 0.5;
            }
        }


        return randomBoard;
    }


}
