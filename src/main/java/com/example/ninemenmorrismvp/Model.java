package com.example.ninemenmorrismvp;

import java.util.HashMap;
import java.util.Map;

public class Model {

    int countWhitePieces = 9;
    int countBlackPieces = 9;


    int countWhitePiecesOnBoard = 0;
    int countBlackPiecesOnBoard = 0;


    GameState gameState = GameState.PHRASE_ONE;


    public long whitePieces = 0b000000000000000000000000;
    public long blackPieces = 0b000000000000000000000000;

    public long mill = 0b111;

    public int flag = 0;
    static int A1 = 1 << 0;
    static int D1 = 1 << 1;
    static int G1 = 1 << 2;
    static int B2 = 1 << 3;
    static int D2 = 1 << 4;
    static int F2 = 1 << 5;
    static int C3 = 1 << 6;
    static int D3 = 1 << 7;
    static int E3 = 1 << 8;
    static int A4 = 1 << 9;
    static int B4 = 1 << 10;
    static int C4 = 1 << 11;
    static int E4 = 1 << 12;
    static int F4 = 1 << 13;
    static int G4 = 1 << 14;
    static int C5 = 1 << 15;
    static int D5 = 1 << 16;
    static int E5 = 1 << 17;
    static int B6 = 1 << 18;
    static int D6 = 1 << 19;
    static int F6 = 1 << 20;
    static int A7 = 1 << 21;
    static int D7 = 1 << 22;
    static int G7 = 1 << 23;

    // mills

    static int hor1 = A1 | D1 | G1;
    static int hor2 = B2 | D2 | F2;
    static int hor3 = C3 | D3 | E3;
    static int hor4_1 = A4 | B4 | C4;
    static int hor4_2 = E4 | F4 | G4;
    static int hor5 = C5 | D5 | E5;
    static int hor6 = B6 | D6 | F6;
    static int hor7 = A7 | D7 | G7;

    static int ver1 = A1 | A4 | A7;
    static int ver2 = B2 | B4 | B6;
    static int ver3 = C3 | C4 | C5;
    static int ver4_1 = D1 | D2 | D3;
    static int ver4_2 = D5 | D6 | D7;
    static int ver5 = E3 | E4 | E5;
    static int ver6 = F2 | F4 | F6;
    static int ver7 = G1 | G4 | G7;
    HashMap<Integer, Integer> whiteMills = new HashMap<>();
    HashMap<Integer, Integer> blackMills = new HashMap<>();

    public void setWhiteMillList()
    {
        whiteMills.put(hor1, 0);
        whiteMills.put(hor2, 0);
        whiteMills.put(hor3, 0);
        whiteMills.put(hor4_1, 0);
        whiteMills.put(hor4_2, 0);
        whiteMills.put(hor5, 0);
        whiteMills.put(hor6, 0);
        whiteMills.put(hor7, 0);

        whiteMills.put(ver1, 0);
        whiteMills.put(ver2, 0);
        whiteMills.put(ver3, 0);
        whiteMills.put(ver4_1, 0);
        whiteMills.put(ver4_2, 0);
        whiteMills.put(ver5, 0);
        whiteMills.put(ver6, 0);
        whiteMills.put(ver7, 0);
    }
    public void setBlackMillList()
    {
        blackMills.put(hor1, 0);
        blackMills.put(hor2, 0);
        blackMills.put(hor3, 0);
        blackMills.put(hor4_1, 0);
        blackMills.put(hor4_2, 0);
        blackMills.put(hor5, 0);
        blackMills.put(hor6, 0);
        blackMills.put(hor7, 0);

        blackMills.put(ver1, 0);
        blackMills.put(ver2, 0);
        blackMills.put(ver3, 0);
        blackMills.put(ver4_1, 0);
        blackMills.put(ver4_2, 0);
        blackMills.put(ver5, 0);
        blackMills.put(ver6, 0);
        blackMills.put(ver7, 0);
    }

    public void setGameState()
    {
        if(checkMill()) {gameState = GameState.MILL;}
        else if (countWhitePieces == countWhitePiecesOnBoard && countBlackPieces == countBlackPiecesOnBoard) gameState = GameState.PHRASE_TWO;
        else if(checkWin()) gameState = GameState.PHRASE_THREE;
        else gameState = GameState.PHRASE_ONE;
    }

    public boolean checkWin()
    {
        if (countWhitePieces == 2 || countBlackPieces == 2) return true;

        return false;
    }

    public void displayPiece(int index, int color)
    {
        if (color == AppConstants.BLACK)
        {
            blackPieces |= (1 << index);
            if(gameState == GameState.PHRASE_ONE) countBlackPiecesOnBoard++;
            setGameState();
            return;
        }
        whitePieces |= (1 << index);
        if(gameState == GameState.PHRASE_ONE) countWhitePiecesOnBoard++;
        setGameState();
    }

    public void remove(int index, int color)
    {
        if(color == AppConstants.WHITE)
        {
            blackPieces ^= (1 << index);
            if(gameState == GameState.MILL) countBlackPiecesOnBoard--;
            if(gameState == GameState.MILL) countBlackPieces--;
            setGameState();
            return;
        }

        whitePieces ^= (1 << index);
        if(gameState == GameState.MILL) countWhitePiecesOnBoard--;
        if(gameState == GameState.MILL) countWhitePieces--;
        setGameState();
    }


    public boolean isPieceOnMill(int index, int color)
    {
        if (color == AppConstants.BLACK)
        {
            for (Map.Entry<Integer, Integer> entry : whiteMills.entrySet())
            {
                if ((entry.getKey() & (1 << index)) != 0 && entry.getValue() == 1)
                {
                    return true;
                }
            }
        }
        else if (color == AppConstants.WHITE)
        {
            for (Map.Entry<Integer, Integer> entry : blackMills.entrySet())
            {
                if ((entry.getKey() & (1 << index)) != 0 && entry.getValue() == 1)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfAllEnemyPiecesOnMills(int color)
    {
        if (color == AppConstants.BLACK)
        {
            for (Map.Entry<Integer, Integer> entry : whiteMills.entrySet())
            {
                if ((whitePieces & entry.getKey()) != 0 && entry.getValue() != 1)
                {
                    return false;
                }
            }
        }
        else if(color == AppConstants.WHITE)
        {
            for (Map.Entry<Integer, Integer> entry : blackMills.entrySet())
            {
                if ((blackPieces & entry.getKey()) != 0 && entry.getValue() != 1)
                {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean isOccupied(int index)
    {
        if ((whitePieces & (1 << index)) != 0) return true;
        if ((blackPieces & (1 << index)) != 0) return true;
        return false;
    }

    public boolean checkMill()
    {
        if (flag == 0)
        {
            setWhiteMillList();
            setBlackMillList();
            flag = 1;
        }
 //       if (color == AppConstants.BLACK)
//       {
            for (Map.Entry<Integer, Integer> entry : blackMills.entrySet())
            {
                if ((entry.getKey() & blackPieces) == entry.getKey() && entry.getValue() == 0)
                {
                    entry.setValue(1);
                    return true;
                }
            }
 //       }
 //       else if (color == AppConstants.WHITE)
 //       {
            for (Map.Entry<Integer, Integer> entry : whiteMills.entrySet())
            {
                if ((entry.getKey() & whitePieces) == entry.getKey() && entry.getValue() == 0)
                {
                    entry.setValue(1);
                    return true;
                }
            }
 //       }
        return false;
    }


    public void move(int fromIndex, int toIndex, int color)
    {
        if (color == AppConstants.WHITE) color = AppConstants.BLACK;
        else if (color == AppConstants.BLACK) color = AppConstants.WHITE;
        remove(fromIndex,color);
        checkIfMillChanged();
        if (color == AppConstants.WHITE) color = AppConstants.BLACK;
        else if (color == AppConstants.BLACK) color = AppConstants.WHITE;
        displayPiece(toIndex, color);
    }

    public void checkIfMillChanged()
    {
        for (Map.Entry<Integer, Integer> entry : blackMills.entrySet())
        {
            if ((entry.getKey() & blackPieces) != entry.getKey() && entry.getValue() == 1)
            {
                entry.setValue(0);
            }
        }
        for (Map.Entry<Integer, Integer> entry : whiteMills.entrySet())
        {
            if ((entry.getKey() & whitePieces) != entry.getKey() && entry.getValue() == 1)
            {
                entry.setValue(0);
            }
        }
    }

    public boolean isRemoveIndexFine(int index, int color)
    {
        if (color == AppConstants.WHITE)
        {
            if (isOccupied(index) && !(((1<<index) & whitePieces) != 0))
            {
                return true;
            }
        }
        else if (color == AppConstants.BLACK)
        {
            if (isOccupied(index) && !(((1<<index) & blackPieces) != 0))
            {
                return true;
            }
        }
        return false;
    }


}
