package br.com.sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class Controller {

    private SudokuGameLogic gameLogic = new SudokuGameLogic();
    @FXML
    private GridPane mainGrid;

    @FXML
    private Label winLabel;

    private Button selectedButton;

    public void initialize()
    {
        gameLogic.generateRandomPuzzle(38);
        syncBoardToUI();
    }

    public void syncBoardToUI()
    {
        for(javafx.scene.Node blockNode : mainGrid.getChildren())
        {
            if(blockNode instanceof GridPane block)
            {
                int bRow = (GridPane.getRowIndex(block) == null) ? 0 : GridPane.getRowIndex(block);
                int bCol = (GridPane.getColumnIndex(block) == null) ? 0 : GridPane.getColumnIndex(block);
                for (javafx.scene.Node buttonNode : block.getChildren())
                {
                    if(buttonNode instanceof Button btn)
                    {
                        int lRow = (GridPane.getRowIndex(btn) == null) ? 0 : GridPane.getRowIndex(btn);
                        int lCol = (GridPane.getColumnIndex(btn) == null) ? 0 : GridPane.getColumnIndex(btn);

                        // 3. Calculate global coordinates to fetch value
                        int globalRow = lRow + (bRow * 3);
                        int globalCol = lCol + (bCol * 3);
                        int value = gameLogic.getCellValue(globalRow, globalCol);

                        if (value != 0) {
                            btn.setText(String.valueOf(value));
                            // Optional: make puzzle numbers look different/bold
                            btn.setStyle("-fx-font-weight: bold; -fx-opacity: 1.0;");
                            // Optional: disable these buttons so players can't change them
                            // btn.setDisable(true);
                        } else {
                            btn.setText("");
                        }
                    }
                }
            }

        }
    }

    private void checkWin()
    {
        int count = 0;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                if((gameLogic.getCellValue(i, j) != 0))
                    return;
            }
        winLabel.setStyle("status-label-win");
        winLabel.setText("You win!!!");

    }


    @FXML
    private void handleCellClick(ActionEvent event)
    {
        if(selectedButton != null)
            selectedButton.setStyle("");
        selectedButton = (Button) event.getSource();
        selectedButton.setStyle("-fx-background-color: #b3e5fc; -fx-border-color: #03a9f4;");
        selectedButton.requestFocus(); //Request focus so the button "hears" the keyboard

        int row = getRowIndex();
        int col = getColIndex();

        int blockRow = getBlockRowIndex();
        int blockCol = getBlockColIndex();

        row += blockRow * 3;
        col += blockCol * 3;

        System.out.printf("%d %d%n", row, col);
    }

    public Integer getRowIndex()
    {
        Integer rowIndex = GridPane.getRowIndex(selectedButton);
        return rowIndex == null ? 0 : rowIndex;
    }

    public Integer getColIndex()
    {
        Integer colIndex = GridPane.getColumnIndex(selectedButton);
        return colIndex == null ? 0 : colIndex;
    }

    public Integer getBlockRowIndex()
    {
        GridPane parentBlock = (GridPane) selectedButton.getParent();
        Integer bRowIndex = GridPane.getRowIndex(parentBlock);
        return bRowIndex == null ? 0 : bRowIndex;
    }

    public Integer getBlockColIndex()
    {
        GridPane parentBlock = (GridPane) selectedButton.getParent();
        Integer bColIndex = GridPane.getColumnIndex(parentBlock);
        return bColIndex == null ? 0 : bColIndex;
    }

    @FXML
    private void handleKeyPress(KeyEvent event)
    {
        if(selectedButton != null)
        {
            String input = event.getText();
            if (input.matches("[1-9]")) {
                int value = Integer.parseInt(input);
                int row = getRowIndex();
                int col = getColIndex();
                int blockRow = getBlockRowIndex();
                int blockCol = getBlockColIndex();
                if(gameLogic.isValidMove(blockRow, blockCol, row, col, value)) {
                    selectedButton.setText(input);
                    gameLogic.setCellValue(blockRow, blockCol, row, col, value);
                }
                else
                    System.out.println("Invalid Move");

            } else if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
                selectedButton.setText("");
                gameLogic.setCellValue(getBlockRowIndex(), getBlockColIndex(), getRowIndex(), getColIndex(), 0);
            }
        }
    }


}
