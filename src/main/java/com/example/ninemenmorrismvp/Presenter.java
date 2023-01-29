package com.example.ninemenmorrismvp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Map;

public class Presenter implements Ipresenter{

    Model model = new Model();
    View view ;
    private int USER_TURN = AppConstants.WHITE;

    int lastWhiteIndex = -1;
    int lastBlackIndex = -1;
    Button btnTemp;

    public  Presenter(View view)
    {
        this.view=view;
    }
    public void userClick(Button btn, int index)
    {
        GameState state = model.gameState;
        switch (state)
        {
            case PHRASE_THREE:
            {
                view.displayMode("");
                view.displayMessage("Game Over!");
                break;
            }

            case PHRASE_ONE:
            {
                view.displayMode("Mode: Placing");
                if (USER_TURN == AppConstants.WHITE && !model.isOccupied(index))
                {
                    model.displayPiece(index, USER_TURN);
                    view.setWhiteBackground(btn);
                    if (model.gameState == GameState.MILL)
                    {
                        if(!model.checkIfAllEnemyPiecesOnMills(USER_TURN))
                        {
                            view.displayMessage("Remove black piece!");
                            btn.setStyle("-fx-background-color: white; -fx-border-color: green; -fx-border-width: 2px;");
                            btnTemp = btn;
                            setEnemiesBorder(USER_TURN);
                            break;
                        }
                        model.gameState = GameState.PHRASE_ONE;
                    }
                        USER_TURN = AppConstants.BLACK;
                        view.displayMessage("Turn: Black");
                }
                else if (USER_TURN == AppConstants.BLACK && !model.isOccupied(index))
                {
                    model.displayPiece(index, USER_TURN);
                    view.setBlackBackground(btn);
                    if (model.gameState == GameState.MILL)
                    {
                        if(!model.checkIfAllEnemyPiecesOnMills(USER_TURN))
                        {
                            view.displayMessage("Remove white piece!");
                            btn.setStyle("-fx-background-color: black; -fx-border-color: green; -fx-border-width: 2px;");
                            btnTemp = btn;
                            setEnemiesBorder(USER_TURN);
                            break;
                        }
                        model.gameState = GameState.PHRASE_ONE;
                    }
                        USER_TURN = AppConstants.WHITE;
                        view.displayMessage("Turn: White");
                }

                break;
            }
            case PHRASE_TWO:
            {
                view.displayMode("Mode: Moving");
                if (model.isOccupied(index) || lastWhiteIndex != -1 || lastBlackIndex != -1)
                {
                    if (USER_TURN == AppConstants.WHITE && lastWhiteIndex == -1)
                    {
                        if (btn.getStyle().equals("-fx-background-color: white;"))
                        {
                            view.displayMessage("Move piece!");
                            lastWhiteIndex = index;
                            btnTemp = btn;
                            btn.setStyle("-fx-background-color: white; -fx-border-color: green; -fx-border-width: 2px;");
                            break;
                        }
                    }
                    else if (USER_TURN == AppConstants.WHITE && lastWhiteIndex != -1)
                    {
                        if (btn.getStyle().equals(""))
                        {
                             if(model.isNeighbor(lastWhiteIndex, index) || model.countWhitePieces == 3)
                             {
                                 model.move(lastWhiteIndex, index, AppConstants.WHITE);
                                 view.removeWhiteBackground(btnTemp);
                                 view.setWhiteBackground(btn);
                                 lastWhiteIndex = -1;
                                 if (model.gameState == GameState.MILL) {
                                     if (!model.checkIfAllEnemyPiecesOnMills(USER_TURN)) {
                                         view.displayMessage("Remove black piece!");
                                         btn.setStyle("-fx-background-color: white; -fx-border-color: green; -fx-border-width: 2px;");
                                         btnTemp = btn;
                                         setEnemiesBorder(USER_TURN);
                                         break;
                                     }
                                     model.gameState = GameState.PHRASE_TWO;
                                 }
                                 USER_TURN = AppConstants.BLACK;
                                 view.displayMessage("Turn: Black");
                             }
                        }
                        break;
                    }
                    else if (USER_TURN == AppConstants.BLACK && lastBlackIndex == -1)
                    {
                        if (btn.getStyle().equals("-fx-background-color: black;"))
                        {
                            view.displayMessage("Move piece!");
                            lastBlackIndex = index;
                            btnTemp = btn;
                            btn.setStyle("-fx-background-color: black; -fx-border-color: green; -fx-border-width: 2px;");
                            break;
                        }
                    }
                    else if (USER_TURN == AppConstants.BLACK && lastBlackIndex != -1)
                    {
                        if (btn.getStyle().equals(""))
                        {
                            if(model.isNeighbor(lastBlackIndex, index) || model.countBlackPieces == 3)
                            {
                                model.move(lastBlackIndex, index, AppConstants.BLACK);
                                view.removeBlackBackground(btnTemp);
                                view.setBlackBackground(btn);
                                lastBlackIndex = -1;
                                if (model.gameState == GameState.MILL) {
                                    if (!model.checkIfAllEnemyPiecesOnMills(USER_TURN)) {
                                        view.displayMessage("Remove white piece!");
                                        btn.setStyle("-fx-background-color: black; -fx-border-color: green; -fx-border-width: 2px;");
                                        btnTemp = btn;
                                        setEnemiesBorder(USER_TURN);
                                        break;
                                    }
                                    model.gameState = GameState.PHRASE_TWO;
                                }
                                USER_TURN = AppConstants.WHITE;
                                view.displayMessage("Turn: White");
                            }
                        }
                    }
                }
                break;
            }

            case MILL:
            {
                if (USER_TURN == AppConstants.WHITE)
                {
                    if (model.isRemoveIndexFine(index, USER_TURN))
                    {
                        if (!model.checkIfAllEnemyPiecesOnMills(USER_TURN))
                        {
                            if (!model.isPieceOnMill(index, USER_TURN))
                            {
                                model.remove(index, USER_TURN);
                                view.removeBlackBackground(btn);
                                removeEnemiesBorder(USER_TURN);
                                btnTemp.setStyle("-fx-background-color: white;");
                                USER_TURN = AppConstants.BLACK;
                                view.displayMessage("Turn: Black");
                            }
                        }
                        else
                        {
                            USER_TURN = AppConstants.BLACK;
                            view.displayMessage("Turn: Black");
                        }
                    }
                }
                else if (USER_TURN == AppConstants.BLACK)
                {
                    if (model.isRemoveIndexFine(index, USER_TURN))
                    {
                        if (!model.checkIfAllEnemyPiecesOnMills(USER_TURN))
                        {
                            if (!model.isPieceOnMill(index, USER_TURN)) {
                                model.remove(index, USER_TURN);
                                view.removeBlackBackground(btn);
                                removeEnemiesBorder(USER_TURN);
                                btnTemp.setStyle("-fx-background-color: black;");
                                USER_TURN = AppConstants.WHITE;
                                view.displayMessage("Turn: White");
                            }
                        }
                        else
                        {
                            USER_TURN = AppConstants.WHITE;
                            view.displayMessage("Turn: White");
                        }
                    }
                }

            }

        }

    }

    public void gameInformation()
    {
        System.out.println(Long.toBinaryString(model.whitePieces));
        System.out.println(Long.toBinaryString(model.blackPieces));
        System.out.println("Player turn: " + this.USER_TURN);
        System.out.println("Game State: " + model.gameState);
        System.out.println("last black index: " + this.lastBlackIndex);
        System.out.println("last white index: " + this.lastWhiteIndex);
        System.out.println("count black pieces: " + model.countBlackPieces);
        System.out.println("count white pieces: " + model.countWhitePieces);
        System.out.println("count black pieces on board: " + model.countBlackPiecesOnBoard);
        System.out.println("count white pieces on board: " + model.countWhitePiecesOnBoard);
    }

    public void newGame()
    {
        model.restart();
        view.displayMessage("New game! turn: white");
        view.displayMode("Mode: Placing");
        USER_TURN = AppConstants.WHITE;
    }

    public void setEnemiesBorder(int color)
    {
        for (int i = 0; i < view.buttons.size(); i++)
        {
            if (color == AppConstants.WHITE)
            {
                if (view.buttons.get(i).getStyle().equals("-fx-background-color: black;"))
                {
                    String s = view.buttons.get(i).getId().replace("btn", "");
                    int index = Integer.valueOf(s) - 1;
                    if (!model.isPieceOnMill(index, color))
                    {
                        view.buttons.get(i).setStyle("-fx-background-color: black; -fx-border-color: red; -fx-border-width: 2px;");
                    }
                }
            }
            else if(color == AppConstants.BLACK)
            {
                if (view.buttons.get(i).getStyle().equals("-fx-background-color: white;"))
                {
                    String s = view.buttons.get(i).getId().replace("btn", "");
                    int index = Integer.valueOf(s) - 1;
                    if (!model.isPieceOnMill(index, color))
                    {
                        view.buttons.get(i).setStyle("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 2px;");
                    }
                }
            }
        }
    }

    public void removeEnemiesBorder(int color)
    {

        for (int i = 0; i < view.buttons.size(); i++)
        {
            if (color == AppConstants.WHITE)
            {
                if (view.buttons.get(i).getStyle().equals("-fx-background-color: black; -fx-border-color: red; -fx-border-width: 2px;"))
                {
                    String s = view.buttons.get(i).getId().replace("btn", "");
                    int index = Integer.valueOf(s) - 1;
                    if (!model.isPieceOnMill(index, color))
                    {
                        view.buttons.get(i).setStyle("-fx-background-color: black;");
                    }
                }
            }
            else if(color == AppConstants.BLACK)
            {
                if (view.buttons.get(i).getStyle().equals("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 2px;"))
                {
                    String s = view.buttons.get(i).getId().replace("btn", "");
                    int index = Integer.valueOf(s) - 1;
                    if (!model.isPieceOnMill(index, color))
                    {
                        view.buttons.get(i).setStyle("-fx-background-color: white;");
                    }
                }
            }
        }
    }

}