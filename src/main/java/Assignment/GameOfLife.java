package Assignment;

public class GameOfLife {

    public static void main(String[] args) {
        int[][] input = {{1,1},{1,0}};

        int[][] result = nextIteration(input);

    }


    static int[][] nextIteration(int matrix[][]) {

        int M = matrix.length;
        int N = matrix[0].length;
        int[][] futureMatrixShape = new int[M][N];

        int[][] positions = {{-1, 0},
                {-1, 1},
                {0, 1},
                {1, 1},
                {1, 0},
                {1, -1},
                {0, -1},
                {-1, -1}
        };

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {

                int alive = 0;

                for (int[] position : positions) {

                    int x = position[0] + i;
                    int y = position[1] + j;

                    if (x >= 0 && x < M && y >= 0 && y < N && matrix[x][y] == 1) {
                        alive++;
                    }
                }

                //here if dead and 3 neighbour are alive, than dead ==> alive
                if (matrix[i][j] == 0 && alive == 3) {
                    futureMatrixShape[i][j] = 1;
                    //here if alive
                } else if (matrix[i][j] == 1) {

                    //if alive and 2 or 3 neighbours are alive than stays alive
                    if (alive == 2 || alive == 3) {
                        futureMatrixShape[i][j] = 1;
                    }
                }
            }
        }


        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = futureMatrixShape[i][j];
            }
        }
        return matrix;
    }


}
