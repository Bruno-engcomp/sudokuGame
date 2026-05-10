package br.com.sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.security.Key;

public class HelloController {
    private SudokuGameLogic gameLogic = new SudokuGameLogic();
    @FXML
    private Label welcomeText;
    private Button selectedButton;


    @FXML
    private void handleCellClick(ActionEvent event)
    {
        if(selectedButton != null)
            selectedButton.setStyle("");
        selectedButton = (Button) event.getSource();
        selectedButton.setStyle("-fx-background-color: #b3e5fc; -fx-border-color: #03a9f4;");
        selectedButton.requestFocus(); //Request focus so the button "hears" the keyboard

        Integer rowIndex = GridPane.getRowIndex(selectedButton);
        Integer colIndex = GridPane.getColumnIndex(selectedButton);

        int row = rowIndex == null ? 0 : rowIndex;
        int col = colIndex == null ? 0 : colIndex;

        GridPane parentBlock = (GridPane) selectedButton.getParent();
        Integer bRowIndex = GridPane.getRowIndex(parentBlock);
        Integer bColIndex = GridPane.getColumnIndex(parentBlock);

        int blockRow = bRowIndex == null ? 0 : bRowIndex;
        int blockCol = bColIndex == null ? 0 : bColIndex;

        row += blockRow * 3;
        col += blockCol * 3;

        System.out.printf("%d %d%n", row, col);
    }

    @FXML
    private void handleKeyPress(KeyEvent event)
    {
        if(selectedButton != null)
        {
            String input = event.getText();
            if(input.matches("[1-9]"))
            {
                selectedButton.setText(input);
            }
            else if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE)
            {
                selectedButton.setText("");
            }
        }
    }
}
