package com.example.ninemenmorrismvp;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class AIModel {

    final Board board;

    public AIModel(Board board) {
        this.board = board;
    }


    public int getBestSpotPhraseOne()
    {
        int bestSpot = -1;
        bestSpot = fillMillOrBlockMill();
        if (bestSpot != -1) return  bestSpot;

        else // return a random index that is not occupied
        {
            List<Integer> usedNumbers = new ArrayList<>();
            Random random = new Random();
            int attempts;
            for (int i = 0; i < 23; i++)
            {
                attempts = 0;
                int index = random.nextInt(23) + 1;
                while (usedNumbers.contains(index))
                {
                    index = random.nextInt(23) + 1;
                    attempts++;
                    if (attempts >= 50) break;
                }

                if (!this.board.isOccupied(index))
                {
                    bestSpot = index;
                    break;
                }
                usedNumbers.add(index);
            }
            return bestSpot;
        }

    }

    public int fillMillOrBlockMill()
    {
        int bestSpot = -1;
        int attempts = 0;

        List<Integer> usedNumbers = new ArrayList<>();
        Random random = new Random();

        // Apply the first rule: Place pieces to create mills
        for (int i = 0; i < 23; i++)
        {
            int index = random.nextInt(23) + 1;
            attempts = 0;
            while (usedNumbers.contains(index))
            {
                index = random.nextInt(23) + 1;
                attempts++;
                if (attempts >= 50) break;
            }
            if (!this.board.isOccupied(index))
            {
                if (this.board.checkMillAI(index))
                {
                    System.out.println("1111111");
                    bestSpot = index;
                    break;
                }
            }
            usedNumbers.add(index);
        }
        System.out.println("after first rule, best spot is: " + bestSpot);
        if (bestSpot != -1) return bestSpot;


        // Apply the second rule: Block enemies pieces from creating mills
        for (int i = 0; i < 23; i++)
        {
            int index = random.nextInt(23) + 1;
            attempts = 0;
            while (usedNumbers.contains(index))
            {
                index = random.nextInt(23) + 1;
                attempts++;
                if (attempts >= 50) break;
            }
            if (!this.board.isOccupied(index))
            {
                if (this.board.isBlockMillPossible(index))
                {
                    bestSpot = index;
                    break;
                }
            }
            usedNumbers.add(index);
        }
        System.out.println("after second rule, best spot is: " + bestSpot);
        if (bestSpot != -1) return bestSpot;

        // Apply the third rule: Place pieces next to your own pieces
        for (int i = 0; i < 23; i++)
        {
            int index = random.nextInt(23) + 1;
            attempts = 0;
            while (usedNumbers.contains(index))
            {
                index = random.nextInt(23) + 1;
                attempts++;
                if (attempts >= 50) break;
            }
            if (!this.board.isOccupied(index))
            {
                if ((this.board.blackPieces & (index<<1)) != 0)
                {
                    bestSpot = index;
                    break;
                }
            }
            usedNumbers.add(index);
        }

        System.out.println("after third rule, best spot is: " + bestSpot);
        return bestSpot;
    }

    public int getBestRemoveOption()
    {
        int bestSpot = -1;
        Random random = new Random();
        List<Integer> usedNumbers = new ArrayList<>();


        for (int i = 0; i < 23; i++)
        {
            int index = random.nextInt(23) + 1;
            while (usedNumbers.contains(index))
            {
                index = random.nextInt(23) + 1;
            }
            if (((1 << index) | this.board.whitePieces) == this.board.whitePieces && !this.board.isPieceOnMill(index, AppConstants.BLACK))
            {
                bestSpot = index;
                break;
            }
            usedNumbers.add(index);
        }
        return bestSpot;
    }

    public int[] getBestSpotPhraseTwo(View view)
    {
        int [] arr = new int[2];
        Random random = new Random();
        int index1 = random.nextInt(23) + 1;
        outerloop:
        for (int index = 0; index < 100; index++)
        {
            for (Map.Entry<Button, Integer> entry : view.buttonIntegerHashMap.entrySet())
            {
                if (entry.getValue() == index1)
                {
                    if (entry.getKey().getStyle().equals("-fx-background-color: black;"))
                    {
                        for (Map.Entry<Integer, ArrayList<Integer>> entry1 : this.board.Neighbors.entrySet())
                        {
                            if (entry1.getKey() == index1)
                            {
                                for (int j = 0; j < entry1.getValue().size(); j++)
                                {
                                    if (!this.board.isOccupied(entry1.getValue().get(j)))
                                    {
                                        arr[0] = index1;
                                        arr[1] = entry1.getValue().get(j);
                                        break outerloop;
                                    }
                                }
                            }
                        }
                    }
                }
                index1 = random.nextInt(23) + 1;
            }

        }

        return arr;
    }

//    public int isGoodMovePossible()
//    {
//
//    }

//    private boolean isMill(long temp, int spot, int color) {
//
//        long tmpBoard = 0;
//        if (color == AppConstants.WHITE)
//        {
//            tmpBoard |= whitePieces;
//            displayPiece(spot, color);
//            if (checkMill())
//            {
//                whitePieces = tmpBoard;
//                return true;
//            }
//            whitePieces = tmpBoard;
//        }
//        else if (color == AppConstants.BLACK)
//        {
//            tmpBoard |= blackPieces;
//            displayPiece(spot, color);
//            if (checkMill())
//            {
//                whitePieces = tmpBoard;
//                return true;
//            }
//            whitePieces = tmpBoard;
//        }
//
//    }
//
}
