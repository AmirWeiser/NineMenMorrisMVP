package com.example.ninemenmorrismvp;

import javafx.scene.control.Button;

import java.util.*;

public class Board {

    int countWhitePieces = 9;
    int countBlackPieces = 9;


    int countWhitePiecesOnBoard = 0;
    int countBlackPiecesOnBoard = 0;


    GameState gameState = GameState.PHRASE_ONE;


    public long whitePieces = 0b000000000000000000000000;
    public long blackPieces = 0b000000000000000000000000;

    public long mill = 0b111;

    public int flag = 0;
    public int flag1 = 0;
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
    HashMap<Integer, Integer> whiteMills = new LinkedHashMap<>();
    HashMap<Integer, Integer> blackMills = new LinkedHashMap<>();

    public HashMap<Integer, ArrayList<Integer>> Neighbors = new LinkedHashMap<>();


    public HashMap<Integer,Integer> connectedMiddles = new LinkedHashMap<>();


    public void setConnectedMiddlesHashMap()
    {
        connectedMiddles.put(1,9);
        connectedMiddles.put(1,14);
        connectedMiddles.put(9,22);
        connectedMiddles.put(22,14);
        connectedMiddles.put(4,10);
        connectedMiddles.put(4,13);
        connectedMiddles.put(10,19);
        connectedMiddles.put(19,13);
        connectedMiddles.put(7,11);
        connectedMiddles.put(7,12);
        connectedMiddles.put(11,16);
        connectedMiddles.put(16,12);
    }


//    public HashMap<Integer, ArrayList<Integer>> doubleMillOptions = new HashMap<>();
//
//    public void setdoubleMillOptionsHashMap()
//    {
//        ArrayList<Integer>list0 = new ArrayList<>(Arrays.asList(1,9));
//        ArrayList<Integer>list1 = new ArrayList<>(Arrays.asList(0,2,4));
//        ArrayList<Integer>list2 = new ArrayList<>(Arrays.asList(1,14));
//        ArrayList<Integer>list3 = new ArrayList<>(Arrays.asList(4,10));
//        ArrayList<Integer>list4 = new ArrayList<>(Arrays.asList(1,3,5,7));
//        ArrayList<Integer>list5 = new ArrayList<>(Arrays.asList(4, 13));
//        ArrayList<Integer>list6 = new ArrayList<>(Arrays.asList(7,11));
//        ArrayList<Integer>list7 = new ArrayList<>(Arrays.asList(4,6,8));
//        ArrayList<Integer>list8 = new ArrayList<>(Arrays.asList(7,12));
//        ArrayList<Integer>list9 = new ArrayList<>(Arrays.asList(0,10,21));
//        ArrayList<Integer>list10 = new ArrayList<>(Arrays.asList(3,9,11,18));
//        ArrayList<Integer>list11 = new ArrayList<>(Arrays.asList(6,10,15));
//        ArrayList<Integer>list12 = new ArrayList<>(Arrays.asList(8,13,17));
//        ArrayList<Integer>list13 = new ArrayList<>(Arrays.asList(5,12,14,20));
//        ArrayList<Integer>list14 = new ArrayList<>(Arrays.asList(2,23,13));
//        ArrayList<Integer>list15 = new ArrayList<>(Arrays.asList(11, 16));
//        ArrayList<Integer>list16 = new ArrayList<>(Arrays.asList(15,17,19));
//        ArrayList<Integer>list17 = new ArrayList<>(Arrays.asList(12,16));
//        ArrayList<Integer>list18 = new ArrayList<>(Arrays.asList(10,19));
//        ArrayList<Integer>list19 = new ArrayList<>(Arrays.asList(16,18,20,22));
//        ArrayList<Integer>list20 = new ArrayList<>(Arrays.asList(13,19));
//        ArrayList<Integer>list21 = new ArrayList<>(Arrays.asList(9,22));
//        ArrayList<Integer>list22 = new ArrayList<>(Arrays.asList(19,21,23));
//        ArrayList<Integer>list23 = new ArrayList<>(Arrays.asList(14,22));
//
//        Neighbors.put(0, list0);
//        Neighbors.put(1, list1);
//        Neighbors.put(2, list2);
//        Neighbors.put(3, list3);
//        Neighbors.put(4, list4);
//        Neighbors.put(5, list5);
//        Neighbors.put(6, list6);
//        Neighbors.put(7, list7);
//        Neighbors.put(8, list8);
//        Neighbors.put(9, list9);
//        Neighbors.put(10, list10);
//        Neighbors.put(11, list11);
//        Neighbors.put(12, list12);
//        Neighbors.put(13, list13);
//        Neighbors.put(14, list14);
//        Neighbors.put(15, list15);
//        Neighbors.put(16, list16);
//        Neighbors.put(17, list17);
//        Neighbors.put(18, list18);
//        Neighbors.put(19, list19);
//        Neighbors.put(20, list20);
//        Neighbors.put(21, list21);
//        Neighbors.put(22, list22);
//        Neighbors.put(23, list23);
//
//
//    }

    public void setNeighborsHashMap()
    {
        ArrayList<Integer>list0 = new ArrayList<>(Arrays.asList(1,9));
        ArrayList<Integer>list1 = new ArrayList<>(Arrays.asList(0,2,4));
        ArrayList<Integer>list2 = new ArrayList<>(Arrays.asList(1,14));
        ArrayList<Integer>list3 = new ArrayList<>(Arrays.asList(4,10));
        ArrayList<Integer>list4 = new ArrayList<>(Arrays.asList(1,3,5,7));
        ArrayList<Integer>list5 = new ArrayList<>(Arrays.asList(4, 13));
        ArrayList<Integer>list6 = new ArrayList<>(Arrays.asList(7,11));
        ArrayList<Integer>list7 = new ArrayList<>(Arrays.asList(4,6,8));
        ArrayList<Integer>list8 = new ArrayList<>(Arrays.asList(7,12));
        ArrayList<Integer>list9 = new ArrayList<>(Arrays.asList(0,10,21));
        ArrayList<Integer>list10 = new ArrayList<>(Arrays.asList(3,9,11,18));
        ArrayList<Integer>list11 = new ArrayList<>(Arrays.asList(6,10,15));
        ArrayList<Integer>list12 = new ArrayList<>(Arrays.asList(8,13,17));
        ArrayList<Integer>list13 = new ArrayList<>(Arrays.asList(5,12,14,20));
        ArrayList<Integer>list14 = new ArrayList<>(Arrays.asList(2,23,13));
        ArrayList<Integer>list15 = new ArrayList<>(Arrays.asList(11, 16));
        ArrayList<Integer>list16 = new ArrayList<>(Arrays.asList(15,17,19));
        ArrayList<Integer>list17 = new ArrayList<>(Arrays.asList(12,16));
        ArrayList<Integer>list18 = new ArrayList<>(Arrays.asList(10,19));
        ArrayList<Integer>list19 = new ArrayList<>(Arrays.asList(16,18,20,22));
        ArrayList<Integer>list20 = new ArrayList<>(Arrays.asList(13,19));
        ArrayList<Integer>list21 = new ArrayList<>(Arrays.asList(9,22));
        ArrayList<Integer>list22 = new ArrayList<>(Arrays.asList(19,21,23));
        ArrayList<Integer>list23 = new ArrayList<>(Arrays.asList(14,22));

        Neighbors.put(0, list0);
        Neighbors.put(1, list1);
        Neighbors.put(2, list2);
        Neighbors.put(3, list3);
        Neighbors.put(4, list4);
        Neighbors.put(5, list5);
        Neighbors.put(6, list6);
        Neighbors.put(7, list7);
        Neighbors.put(8, list8);
        Neighbors.put(9, list9);
        Neighbors.put(10, list10);
        Neighbors.put(11, list11);
        Neighbors.put(12, list12);
        Neighbors.put(13, list13);
        Neighbors.put(14, list14);
        Neighbors.put(15, list15);
        Neighbors.put(16, list16);
        Neighbors.put(17, list17);
        Neighbors.put(18, list18);
        Neighbors.put(19, list19);
        Neighbors.put(20, list20);
        Neighbors.put(21, list21);
        Neighbors.put(22, list22);
        Neighbors.put(23, list23);


    }

    public boolean isNeighbor(int currIndex, int destIndex)
    {
        if (flag1 == 0)
        {
            setNeighborsHashMap();
            flag1 = 1;
        }

        for (Map.Entry<Integer, ArrayList<Integer>> entry : this.Neighbors.entrySet())
        {
            if (entry.getKey() == currIndex)
            {
                for (int i = 0; i < entry.getValue().size(); i++)
                {
                    if (entry.getValue().get(i) == destIndex)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

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
        if(checkWin()) gameState = GameState.PHRASE_THREE;
        else if(checkMill()) {gameState = GameState.MILL;}
        else if (countWhitePieces == countWhitePiecesOnBoard && countBlackPieces == countBlackPiecesOnBoard) gameState = GameState.PHRASE_TWO;
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
            this.blackPieces |= (1 << index);
            if(gameState == GameState.PHRASE_ONE) countBlackPiecesOnBoard++;
            setGameState();
            return;
        }
        this.whitePieces |= (1 << index);
        if(gameState == GameState.PHRASE_ONE) countWhitePiecesOnBoard++;
        setGameState();
    }

    public void remove(int index, int color)
    {
        if(color == AppConstants.WHITE)
        {
            this.blackPieces ^= (1 << index);
            if(gameState == GameState.MILL) countBlackPiecesOnBoard--;
            if(gameState == GameState.MILL) countBlackPieces--;
            setGameState();
            return;
        }

        this.whitePieces ^= (1 << index);
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


    public boolean checkIfAllEnemyPiecesOnMills(int color) {
        long onMill = 0L;
        if(color == AppConstants.WHITE)
        {
            for (Map.Entry<Integer, Integer> entry : blackMills.entrySet())
            {
                if ((this.blackPieces & entry.getKey()) == entry.getKey()) {
                    onMill |= entry.getKey();
                }
            }
        }
        else if(color == AppConstants.BLACK)
        {
            for (Map.Entry<Integer, Integer> entry : whiteMills.entrySet())
            {
                if ((this.whitePieces & entry.getKey()) == entry.getKey()) {
                    onMill |= entry.getKey();
                }
            }
        }
        return onMill == this.blackPieces;
    }


    public boolean isOccupied(int index)
    {
        if ((this.whitePieces & (1 << index)) != 0) return true;
        if ((this.blackPieces & (1 << index)) != 0) return true;
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
            for (Map.Entry<Integer, Integer> entry : blackMills.entrySet())
            {
                if ((entry.getKey() & this.blackPieces) == entry.getKey() && entry.getValue() == 0)
                {
                    entry.setValue(1);
                    return true;
                }
            }
            for (Map.Entry<Integer, Integer> entry : whiteMills.entrySet())
            {
                if ((entry.getKey() & this.whitePieces) == entry.getKey() && entry.getValue() == 0)
                {
                    entry.setValue(1);
                    return true;
                }
            }
 //       }
        return false;
    }

    public boolean checkMillAI(int index)
    {

        Long blackTempBoard = this.blackPieces;
        blackTempBoard |= (1 << index);

        for (Map.Entry<Integer, Integer> entry : blackMills.entrySet())
        {
            if ((entry.getKey() & blackTempBoard) == entry.getKey() && entry.getValue() == 0)
            {
                return true;
            }
        }

        return false;
    }



    public boolean isBlockMillPossible(int index)
    {
        Long whiteTempBoard = this.whitePieces;
        whiteTempBoard |= (1 << index);

        for (Map.Entry<Integer, Integer> entry : whiteMills.entrySet())
        {
            if (((entry.getKey() & (1 << index)) != 0) && (entry.getKey() & whiteTempBoard) == entry.getKey())
            {
                return true;
            }
        }

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
            if (entry.getValue() == 1 && (entry.getKey() & this.blackPieces) != entry.getKey())
            {
                entry.setValue(0);
            }
        }
        for (Map.Entry<Integer, Integer> entry : whiteMills.entrySet())
        {
            if (entry.getValue() == 1 && (entry.getKey() & this.whitePieces) != entry.getKey())
            {
                entry.setValue(0);
            }
        }
    }

    public boolean isRemoveIndexFine(int index, int color)
    {
        if (color == AppConstants.WHITE)
        {
            if (isOccupied(index) && !(((1<<index) & this.whitePieces) != 0))
            {
                return true;
            }
        }
        else if (color == AppConstants.BLACK)
        {
            if (isOccupied(index) && !(((1<<index) & this.blackPieces) != 0))
            {
                return true;
            }
        }
        return false;
    }

    public void flying (int fromIndex, int toIndex, int color)
    {
        if (color == AppConstants.WHITE)
        {
            if (!isOccupied(toIndex))
            {
                displayPiece(toIndex, color);
                remove(fromIndex, AppConstants.BLACK);
            }
        }
        else if(color == AppConstants.BLACK)
        {
            if (!isOccupied(toIndex))
            {
                displayPiece(toIndex, color);
                remove(fromIndex, AppConstants.WHITE);
            }
        }
    }

    public void restart()
    {
        this.whitePieces = 0b000000000000000000000000;
        this.blackPieces = 0b000000000000000000000000;
        this.flag = 0;
        this.flag1 = 0;
        this.countBlackPieces = 9;
        this.countWhitePieces = 9;
        this.countWhitePiecesOnBoard = 0;
        this.countBlackPiecesOnBoard = 0;
        this.gameState = GameState.PHRASE_ONE;
        for (Map.Entry<Integer, Integer> entry : blackMills.entrySet())
            entry.setValue(0);
        for (Map.Entry<Integer, Integer> entry : whiteMills.entrySet())
            entry.setValue(0);
    }



}
