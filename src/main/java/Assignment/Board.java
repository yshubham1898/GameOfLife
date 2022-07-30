package Assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private final int[][] validNeighborCellPositions = {
            {-1, 0}, // left
            {-1, 1}, // bottom left
            {0, 1}, // bottom
            {1, 1}, // bottom right
            {1, 0}, // right
            {1, -1}, // top right
            {0, -1}, // top
            {-1, -1} // top left
    };

    private boolean isNextStepRequired = true;

    private List<List<Cell>> board;

    public Board(boolean[][] board) {
        this.initBoard(board);
    }

    public List<List<Cell>> calculateNextGenerationOfBoard() {
        this.isNextStepRequired = false;

        for (int i = 0; i < this.board.size(); i++) {
            for (int j = 0; j < this.board.get(0).size(); j++) {
                Cell cell = this.board.get(i).get(j);
                List<Cell> neighbors = getCellNeighbors(cell);
                cell.lazyUpdateLifeStatus(neighbors);
            }
        }

        for (List<Cell> cellsRow: board) {
            for (Cell cell: cellsRow) {
                boolean oldLifeStatus = cell.isAlive();
                cell.refreshLifeStatus();
                boolean newLifeStatus = cell.isAlive();
                if (oldLifeStatus != newLifeStatus) {
                    this.isNextStepRequired = true;
                }
            }
        }

        return this.board;
    }

    public void showBoard() {
        for (List<Cell> cells: this.board) {
            for (Cell cell: cells) {
                System.out.print(cell.isAlive() ? "* " : "_ ");
            }
            System.out.println();
        }
    }

    public boolean isGameCompleted() {
        return isNextStepRequired;
    }

    private List<Cell> getCellNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int horizontalBoardLength = this.board.get(0).size();
        int verticalBoardLength = this.board.size();
        for (int[] neighborCellPosition: validNeighborCellPositions) {
            int x = neighborCellPosition[0];
            int y = neighborCellPosition[1];
            int newX = cell.getX() + x;
            int newY = cell.getY() + y;
            boolean validHorizontalPosition = ( newX >= 0 ) && ( newX < verticalBoardLength );
            boolean validVerticalPosition = ( newY >= 0 ) && ( newY < horizontalBoardLength );
            if ( validVerticalPosition && validHorizontalPosition ) {
                neighbors.add(this.board.get(newX).get(newY));
            }
        }
        return neighbors;
    }

    private void initBoard(boolean[][] board) {
        AtomicInteger rowIndex = new AtomicInteger(-1);
        this.board = Arrays.stream(board).map(boardRow -> {
            rowIndex.incrementAndGet();
            AtomicInteger columnIndex = new AtomicInteger(0);
            return IntStream.range(0, boardRow.length)
                    .mapToObj(idx -> boardRow[idx]).map(isAlive -> new Cell(rowIndex.get(), columnIndex.getAndIncrement(), isAlive)).collect(Collectors.toList());
        }).collect(Collectors.toList());
    }

}
