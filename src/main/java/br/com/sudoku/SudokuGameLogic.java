package br.com.sudoku;

import java.security.SecureRandom;

public class SudokuGameLogic {
    private int[][] board = new int[9][9];

    public void generateRandomPuzzle(int number)
    {
        SecureRandom random = new SecureRandom();
        int placed = 0;

        while(placed < number)
        {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            int val = random.nextInt(9) + 1;

            if(board[row][col] == 0 && isValidMove(row / 3, col / 3, row, col, val))
            {
                setCellValue(row, col, val);
                placed++;
            }
        }
    }

    public boolean isValidMove (int bRow,int bCol,int row, int col, int value)
    {
        return !hasEqualAtRowOrCol(row, col, value) &&
                !hasEqualAtSubTable(bRow, bCol, value);
    }

    public boolean hasEqualAtRowOrCol(int globalRow, int globalCol, int value)
    {
        for (int i = 0; i < 9; i++)
        {
            if(i != globalRow && value == board[i][globalCol])
                return true;
            if(i != globalCol && value == board[globalRow][i])
                return true;
        }
        return false;
    }

    public boolean hasEqualAtSubTable(int bRowIndex, int bColIndex ,int value)
    {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(value == board[i + bRowIndex * 3][j + bColIndex * 3])
                    return true;
            }
        }
        return false;
    }

    public void setCellValue(int blockRow, int blockCol, int row, int col ,int value) {
        board[row + (blockRow * 3)][col + (blockCol * 3)] = value;
    }

    public void setCellValue(int globalRow, int globalCol ,int value) {
        board[globalRow][globalCol] = value;
    }

    public int getCellValue(int row, int col) {
        return board[row][col];
    }

}
