package com.example.ninemenmorrismvp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

            case PHRASE_ONE:
            {
                model.displayPiece(index, USER_TURN);
                if (USER_TURN == AppConstants.WHITE)
                {
                    view.setWhiteBackground(btn);
                    if (model.gameState == GameState.MILL)
                    {
                        view.displayMessage("Remove black piece!");
                        break;
                    }
                    else
                        USER_TURN = AppConstants.BLACK;
                        view.displayMessage("Turn: Black");
                }
                else if (USER_TURN == AppConstants.BLACK)
                {
                    view.setBlackBackground(btn);
                    if (model.gameState == GameState.MILL)
                    {
                        view.displayMessage("Remove white piece!");
                        break;
                    }
                    else
                        USER_TURN = AppConstants.WHITE;
                        view.displayMessage("Turn: White");
                }

                break;
            }
            case PHRASE_TWO:
            {
                if (model.isOccupied(index) || lastWhiteIndex != -1 || lastBlackIndex != -1)
                {
                    if (USER_TURN == AppConstants.WHITE && lastWhiteIndex == -1)
                    {
                        if (btn.getStyle().equals("-fx-background-color: white;"))
                        {
                            view.displayMessage("Move piece!");
                            lastWhiteIndex = index;
                            btnTemp = btn;
                            break;
                        }
                    }
                    //
                    else if (USER_TURN == AppConstants.WHITE && lastWhiteIndex != -1)
                    {
                        model.move(lastWhiteIndex, index, AppConstants.WHITE);
                        view.removeWhiteBackground(btnTemp);
                        view.setWhiteBackground(btn);
                        lastWhiteIndex = -1;
                        if (model.gameState == GameState.MILL)
                        {
                            view.displayMessage("Remove black piece!");
                            break;
                        }
                        USER_TURN = AppConstants.BLACK;
                        view.displayMessage("Turn: Black");
                        break;
                    }
                    else if (USER_TURN == AppConstants.BLACK && lastBlackIndex == -1)
                    {
                        if (btn.getStyle().equals("-fx-background-color: black;"))
                        {
                            view.displayMessage("Move piece!");
                            lastBlackIndex = index;
                            btnTemp = btn;
                            break;
                        }
                    }
                    else if (USER_TURN == AppConstants.BLACK && lastBlackIndex != -1)
                    {
                        model.move(lastBlackIndex, index, AppConstants.BLACK);
                        view.removeBlackBackground(btnTemp);
                        view.setBlackBackground(btn);
                        lastBlackIndex = -1;
                        if (model.gameState == GameState.MILL)
                        {
                            view.displayMessage("Remove black piece!");
                            break;
                        }
                        USER_TURN = AppConstants.WHITE;
                        view.displayMessage("Turn: White");
                    }
                }
                break;
            }
            case PHRASE_THREE:
            {

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
                                USER_TURN = AppConstants.BLACK;
                                view.displayMessage("Turn: Black");
                            }
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
                                USER_TURN = AppConstants.WHITE;
                                view.displayMessage("Turn: White");
                            }
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
//        System.out.println("isBlackMillLastTurn: " + this.isBlackMillLastTurn);
//        System.out.println("isWhiteMillLastTurn: " + this.isWhiteMillLastTurn);
//        System.out.println("white:");
//        for (Map.Entry<Integer, Integer> entry : model.whiteMills.entrySet())
//        {
//            System.out.print(" key: " + Long.toBinaryString(entry.getKey()) + " value: " + entry.getValue() + ",");
//        }
//        System.out.println("black:");
//        for (Map.Entry<Integer, Integer> entry : model.blackMills.entrySet())
//        {
//            System.out.print(" key: " + Long.toBinaryString(entry.getKey()) + " value: " + entry.getValue() + ",");
//        }
//        System.out.println("-----------------------------------------------------------");
//        System.out.println("-----------------------------------------------------------");
    }
}