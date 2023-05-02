package com.example.ninemenmorrismvp;

import javafx.scene.control.Button;

import java.util.Map;

/**
 * Responsible for the main program, interacts between the Board (model) class and the View Class
 */
public class Presenter implements Ipresenter{

    Board board = new Board();
    View view;
    AIModel AImodel;

    public Presenter(View view) {
        this.view = view;
        this.AImodel = new AIModel(board, view);
    }

    private int USER_TURN = AppConstants.WHITE;

    int lastWhiteIndex = -1;
    int lastBlackIndex = -1;
    Button btnTemp;

    boolean isAI = false;

    /**
     * Responsible for the game program. The game is separated into three Phrases. Phrase one for placing pieces, Phrase two for moving pieces, Phrase three for ending the game and MILL for handling mills.
     * @param btn The button that had pressed
     * @param index The index of the button
     */
    public void userClick(Button btn, int index)
    {
        GameState state = board.gameState;
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
                if (USER_TURN == AppConstants.WHITE && !board.isOccupied(index))
                {
                    board.displayPiece(index, AppConstants.WHITE);
                    view.setWhiteBackground(btn);
                    if (board.gameState == GameState.MILL)
                    {
                        if(!board.checkIfAllEnemyPiecesOnMills(USER_TURN))
                        {
                            view.displayMessage("Remove black piece!");
                            btn.setStyle("-fx-background-color: white; -fx-border-color: green; -fx-border-width: 2px;");
                            btnTemp = btn;
                            setEnemiesBorder(USER_TURN);
                            break;
                        }
                        board.gameState = GameState.PHRASE_ONE;
                    }
                    if(isAI)
                    {
                        handleAIPhraseOne();
                        if (board.gameState == GameState.MILL)
                        {
                            handleAIMill();
                        }
                        break;
                    }
                    USER_TURN = AppConstants.BLACK;
                    view.displayMessage("Turn: Black");
                    break;
                }
                else if (USER_TURN == AppConstants.BLACK && !board.isOccupied(index))
                {
                        board.displayPiece(index, USER_TURN);
                        view.setBlackBackground(btn);
                        if (board.gameState == GameState.MILL)
                        {
                            if(!board.checkIfAllEnemyPiecesOnMills(USER_TURN))
                            {
                                view.displayMessage("Remove white piece!");
                                btn.setStyle("-fx-background-color: black; -fx-border-color: green; -fx-border-width: 2px;");
                                btnTemp = btn;
                                setEnemiesBorder(USER_TURN);
                                break;
                            }
                            board.gameState = GameState.PHRASE_ONE;
                        }
                        USER_TURN = AppConstants.WHITE;
                        view.displayMessage("Turn: White");
                }

                break;
            }
            case PHRASE_TWO:
            {
                view.displayMode("Mode: Moving");
                if (board.isOccupied(index) || lastWhiteIndex != -1 || lastBlackIndex != -1)
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
                             if(board.isNeighbor(lastWhiteIndex, index) || board.countWhitePieces == 3)
                             {
                                 board.move(lastWhiteIndex, index, AppConstants.WHITE);
                                 view.removeWhiteBackground(btnTemp);
                                 view.setWhiteBackground(btn);
                                 lastWhiteIndex = -1;
                                 if (board.gameState == GameState.MILL)
                                 {
                                     if (!board.checkIfAllEnemyPiecesOnMills(USER_TURN))
                                     {
                                         view.displayMessage("Remove black piece!");
                                         btn.setStyle("-fx-background-color: white; -fx-border-color: green; -fx-border-width: 2px;");
                                         btnTemp = btn;
                                         setEnemiesBorder(USER_TURN);
                                         break;
                                     }
                                     board.gameState = GameState.PHRASE_TWO;
                                 }
                                 if (isAI)
                                 {
                                     handleAIPhraseTwo();
                                     if (board.gameState == GameState.MILL)
                                     {
                                         handleAIMill();
                                     }
                                     break;
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
                            if(board.isNeighbor(lastBlackIndex, index) || board.countBlackPieces == 3)
                            {
                                board.move(lastBlackIndex, index, AppConstants.BLACK);
                                view.removeBlackBackground(btnTemp);
                                view.setBlackBackground(btn);
                                lastBlackIndex = -1;
                                if (board.gameState == GameState.MILL) {
                                    if (!board.checkIfAllEnemyPiecesOnMills(USER_TURN)) {
                                        view.displayMessage("Remove white piece!");
                                        btn.setStyle("-fx-background-color: black; -fx-border-color: green; -fx-border-width: 2px;");
                                        btnTemp = btn;
                                        setEnemiesBorder(USER_TURN);
                                        break;
                                    }
                                    board.gameState = GameState.PHRASE_TWO;
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
                    if (board.isRemoveIndexFine(index, USER_TURN))
                    {
                        if (!board.checkIfAllEnemyPiecesOnMills(USER_TURN))
                        {
                            if (!board.isPieceOnMill(index, USER_TURN))
                            {
                                board.remove(index, USER_TURN);
                                view.removeBlackBackground(btn);
                                removeEnemiesBorder(USER_TURN);
                                btnTemp.setStyle("-fx-background-color: white;");
                                if (isAI)
                                {
                                    view.displayMessage("Turn: White");
                                    board.setGameState();
                                    if (board.gameState == GameState.PHRASE_ONE)
                                    {
                                        handleAIPhraseOne();
                                        if (board.gameState == GameState.MILL)
                                        {
                                            handleAIMill();
                                        }
                                        break;
                                    }
                                    else if (board.gameState == GameState.PHRASE_TWO)
                                    {
                                        handleAIPhraseTwo();
                                        if (board.gameState == GameState.MILL)
                                        {
                                            handleAIMill();
                                        }
                                        break;
                                    }
                                }
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
                    if (board.isRemoveIndexFine(index, USER_TURN))
                    {
                        if (!board.checkIfAllEnemyPiecesOnMills(USER_TURN))
                        {
                            if (!board.isPieceOnMill(index, USER_TURN)) {
                                board.remove(index, USER_TURN);
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

    /**
     * Receiving best spot for Phrase one of the game from the AImodel class
     */
    public void handleAIPhraseOne()
    {
        int AIindex = AImodel.HEURISTIC_FUNCTION_PHRASE_ONE();
        System.out.println("AIindex is: " + AIindex);
        board.displayPiece(AIindex, AppConstants.BLACK);
        for (Map.Entry<Button, Integer> entry : view.buttonIntegerHashMap.entrySet())
        {
            if (entry.getValue() == AIindex)
            {
                view.setBlackBackground(entry.getKey());
            }
        }
    }

    /**
     * Receiving best move option for Phrase two of the game from the AImodel class
     */
    public void handleAIPhraseTwo()
    {
        int arr [];
        arr = AImodel.HEURISTIC_FUNCTION_PHRASE_TWO();
        int fromAIindex = arr[0];
        int toAIindex = arr[1];
        System.out.println("fromAIindex is: " + fromAIindex);
        System.out.println("toAIindex is: " + toAIindex);
        board.move(fromAIindex, toAIindex, AppConstants.BLACK);
        for (Map.Entry<Button, Integer> entry : view.buttonIntegerHashMap.entrySet())
        {
            if (entry.getValue() == fromAIindex)
            {
                view.removeBlackBackground(entry.getKey());
            }
            if (entry.getValue() == toAIindex)
            {
                view.setBlackBackground(entry.getKey());
            }
        }
    }

    /**
     * Receiving best spot to delete for MILL Phrase of the game from the AImodel class
     */
    public void handleAIMill()
    {
        int AIindex = AImodel.HEURISTIC_FUNCTION_DELETE();
        board.remove(AIindex, AppConstants.BLACK);
        for (Map.Entry<Button, Integer> entry : view.buttonIntegerHashMap.entrySet())
        {
            if (entry.getValue() == AIindex)
            {
                view.removeBlackBackground(entry.getKey());
            }
        }
        view.displayMessage("Turn: White");
    }

    /**
     * Activating or deactivating the AI option
     */
    public void AiPressed()
    {

        if (!isAI) isAI = true;
        else isAI = false;

    }

    /**
     * Restarts the whole game
     */
    public void newGame()
    {
        board.restart();
        view.displayMessage("New game! turn: white");
        view.displayMode("Mode: Placing");
        USER_TURN = AppConstants.WHITE;
    }


    /**
     * Sets the enemies border color to black if a  mill has formed.
     * @param color The color of the current player
     */
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
                    if (!board.isPieceOnMill(index, color))
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
                    if (!board.isPieceOnMill(index, color))
                    {
                        view.buttons.get(i).setStyle("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 2px;");
                    }
                }
            }
        }
    }

    /**
     * Removes the enemies red border to original color after a  mill has handled.
     * @param color The color of the current player
     */
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
                    if (!board.isPieceOnMill(index, color))
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
                    if (!board.isPieceOnMill(index, color))
                    {
                        view.buttons.get(i).setStyle("-fx-background-color: white;");
                    }
                }
            }
        }
    }



}
