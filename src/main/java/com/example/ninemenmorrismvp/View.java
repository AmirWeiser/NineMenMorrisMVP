package com.example.ninemenmorrismvp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class View implements Iview{

    Presenter presenter;

    @FXML
    private Label labelID;

    @FXML
    private Label modeLabel;

    public View()
    {
        this.presenter= new Presenter(this);
    }
    @Override
    public void displayMessage(String str) {labelID.setText(str);}

    public void displayMode(String str){modeLabel.setText(str);}

    @Override
    public void setWhiteBackground(Button b) {b.setStyle("-fx-background-color: white;");}

    @Override
    public void setBlackBackground(Button b) {b.setStyle("-fx-background-color: black;");}

    @Override
    public void removeWhiteBackground(Button b) {b.setStyle("");}

    @Override
    public void removeBlackBackground(Button b) {b.setStyle("");}

    @FXML
    private Button btn1;
    @FXML
    private Button btn10;
    @FXML
    private Button btn11;
    @FXML
    private Button btn12;
    @FXML
    private Button btn13;
    @FXML
    private Button btn14;
    @FXML
    private Button btn15;
    @FXML
    private Button btn16;
    @FXML
    private Button btn17;
    @FXML
    private Button btn18;
    @FXML
    private Button btn19;
    @FXML
    private Button btn2;
    @FXML
    private Button btn20;
    @FXML
    private Button btn21;
    @FXML
    private Button btn22;
    @FXML
    private Button btn23;
    @FXML
    private Button btn24;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button btn9;

    ArrayList<Button> buttons = new ArrayList<>();
    HashMap<Button, Integer> buttonIntegerHashMap = new HashMap<>();

    @FXML
    /**
     Initializes the game buttons and calls the userClick function that is responsible for the progress of the game
     //     */
    protected void onStartGameClick()
    {



        buttons.add(btn1);
        buttons.add(btn2);
        buttons.add(btn3);
        buttons.add(btn4);
        buttons.add(btn5);
        buttons.add(btn6);
        buttons.add(btn7);
        buttons.add(btn8);
        buttons.add(btn9);
        buttons.add(btn10);
        buttons.add(btn11);
        buttons.add(btn12);
        buttons.add(btn13);
        buttons.add(btn14);
        buttons.add(btn15);
        buttons.add(btn16);
        buttons.add(btn17);
        buttons.add(btn18);
        buttons.add(btn19);
        buttons.add(btn20);
        buttons.add(btn21);
        buttons.add(btn22);
        buttons.add(btn23);
        buttons.add(btn24);

        buttonIntegerHashMap.put(btn1, 0);
        buttonIntegerHashMap.put(btn2, 1);
        buttonIntegerHashMap.put(btn3, 2);
        buttonIntegerHashMap.put(btn4, 3);
        buttonIntegerHashMap.put(btn5, 4);
        buttonIntegerHashMap.put(btn6, 5);
        buttonIntegerHashMap.put(btn7, 6);
        buttonIntegerHashMap.put(btn8, 7);
        buttonIntegerHashMap.put(btn9, 8);
        buttonIntegerHashMap.put(btn10, 9);
        buttonIntegerHashMap.put(btn11, 10);
        buttonIntegerHashMap.put(btn12, 11);
        buttonIntegerHashMap.put(btn13, 12);
        buttonIntegerHashMap.put(btn14, 13);
        buttonIntegerHashMap.put(btn15, 14);
        buttonIntegerHashMap.put(btn16, 15);
        buttonIntegerHashMap.put(btn17, 16);
        buttonIntegerHashMap.put(btn18, 17);
        buttonIntegerHashMap.put(btn19, 18);
        buttonIntegerHashMap.put(btn20, 19);
        buttonIntegerHashMap.put(btn21, 20);
        buttonIntegerHashMap.put(btn22, 21);
        buttonIntegerHashMap.put(btn23, 22);
        buttonIntegerHashMap.put(btn24, 23);

        for (Button b : buttons)
        {
            displayMessage("Turn: White");
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent)
                {
                    String s = b.getId().replace("btn", "");
                    int index = Integer.valueOf(s) - 1;
                    presenter.userClick(b, index);
                }
            });
        }
    }


    @FXML
    /**
     * restarts the buttons
     */
    protected void newGame()
    {
        presenter.newGame();
        btn1.setStyle("");
        btn2.setStyle("");
        btn3.setStyle("");
        btn4.setStyle("");
        btn5.setStyle("");
        btn6.setStyle("");
        btn7.setStyle("");
        btn8.setStyle("");
        btn9.setStyle("");
        btn10.setStyle("");
        btn11.setStyle("");
        btn12.setStyle("");
        btn13.setStyle("");
        btn14.setStyle("");
        btn15.setStyle("");
        btn16.setStyle("");
        btn17.setStyle("");
        btn18.setStyle("");
        btn19.setStyle("");
        btn20.setStyle("");
        btn21.setStyle("");
        btn22.setStyle("");
        btn23.setStyle("");
        btn24.setStyle("");
    }

    @FXML
    /**
     * calls the gameInformation function on the Presenter class
     */
    protected void gameInformation()
    {
        presenter.AiPressed();
    }


    /**
     * checking whether an index is black or not
     * @param index the index of the button
     * @return true if black, false if not
     */
    public boolean isBlack(int index)
    {
        for (Map.Entry<Button, Integer> entry : this.buttonIntegerHashMap.entrySet())
        {
            if (entry.getValue() == index)
            {
                if (entry.getKey().getStyle().equals("-fx-background-color: black;")) return true;
            }
        }
        return false;
    }

    /**
     * checking whether an index is white or not
     * @param index the index of the button
     * @return true if white, false if not
     */
    public boolean isWhite(int index)
    {
        for (Map.Entry<Button, Integer> entry : this.buttonIntegerHashMap.entrySet())
        {
            if (entry.getValue() == index)
            {
                if (entry.getKey().getStyle().equals("-fx-background-color: white;")) return true;
            }
        }
        return false;
    }


}