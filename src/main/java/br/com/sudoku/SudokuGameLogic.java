package br.com.sudoku;

public class SudokuGameLogic {
    private int[][] board = new int[9][9];

    public boolean isValidMove (int row, int col, int value)
    {
        return true;
    }

    public void setCellValue(int row, int col, int value) {
        board[row][col] = value;
    }
}
