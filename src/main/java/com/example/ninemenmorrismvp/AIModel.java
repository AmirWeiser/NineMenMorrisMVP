package com.example.ninemenmorrismvp;

import javafx.scene.control.Button;

import java.util.*;

public class AIModel {

    final Board board;
    final View view;

    public AIModel(Board board, View view)
    {
        this.board = board;
        this.view = view;
    }

//    /**
//     * Returns the best spot for the computer player to place a piece on the board.
//     * The function first checks if there is an empty spot that can form a mill or block an opponent's mill.
//     * If such a spot exists, it returns the index of that spot.
//     * If there are no such spots, the function returns a random index that is not already occupied by a piece.
//     *
//     * @return An integer representing the index of the best spot to place a piece on the board.
//     */
//
//    public int getBestSpotPhraseOne() {
//        int bestSpot = fillMillOrBlockMill();
//        if (bestSpot != -1) return bestSpot;
//
//        // Return a random unused index
//        List<Integer> usedNumbers = new ArrayList<>();
//        for (int i = 0; i < 24; i++) {
//            int index = getRandomUnusedIndex(usedNumbers);
//            if (!this.board.isOccupied(index)) {
//                bestSpot = index;
//                break;
//            }
//            usedNumbers.add(index);
//        }
//        return bestSpot;
//    }
//
//    /**
//     * Returns a random, unused index on the game board.
//     *
//     * @return An integer value representing the index of an unoccupied spot on the game board.
//     */
//    private int getRandomUnusedIndex(List<Integer> usedNumbers) {
//        Random random = new Random();
//        int attempts = 0;
//        int index = random.nextInt(23);
//        while (usedNumbers.contains(index)) {
//            index = random.nextInt(23);
//            attempts++;
//            if (attempts >= 50) break;
//        }
//        return index;
//    }
//
//    /**
//     * Determines the best spot for the AI to place its piece using a set of rules:
//     * 1. Place pieces to create mills
//     * 2. Block enemies pieces from creating mills
//     * 3. Place pieces next to your own pieces
//     *
//     * @return the best spot to place a piece, or -1 if no spots are available
//     */
//    public int fillMillOrBlockMill() {
//        int bestSpot = -1;
//        List<Integer> usedNumbers = new ArrayList<>();
//
//        // Rule 1: Place pieces to create mills
//        for (int i = 0; i < 24; i++) {
//            int index = getRandomUnusedIndex(usedNumbers);
//            if (!this.board.isOccupied(index)) {
//                if (this.board.checkMillAI(index)) {
//                    bestSpot = index;
//                    break;
//                }
//            }
//            usedNumbers.add(index);
//        }
//        System.out.println("============================================");
//        System.out.println("after first rule, best spot is: " + bestSpot);
//
//        if (bestSpot != -1) return bestSpot;
//        usedNumbers.clear();
//
//        // Rule 2: Block enemies pieces from creating mills
//        for (int i = 0; i < 24; i++) {
//            int index = getRandomUnusedIndex(usedNumbers);
//            if (!this.board.isOccupied(index)) {
//                if (this.board.isBlockMillPossible(index)) {
//                    bestSpot = index;
//                    break;
//                }
//            }
//            usedNumbers.add(index);
//        }
//
//        System.out.println("after second rule, best spot is: " + bestSpot);
//        if (bestSpot != -1) return bestSpot;
//        usedNumbers.clear();
//
//        // Rule 3: Place pieces next to your own pieces
//        for (int i = 0; i < 24; i++) {
//            int index = getRandomUnusedIndex(usedNumbers);
//            if ((this.board.blackPieces & (1 << index)) != 0) {
//                for (int j = 0; j < 24; j++) {
//                    if (!this.board.isOccupied(j)) {
//                        if (this.board.isNeighbor(index, j)) {
//                            bestSpot = j;
//                            break;
//                        }
//                    }
//                }
//            }
//            usedNumbers.add(index);
//        }
//        System.out.println("after third rule, best spot is: " + bestSpot);
//
//        return bestSpot;
//    }

    public int getBestRemoveOption() {
        int bestSpot = -1;
        Random random = new Random();
        List<Integer> usedNumbers = new ArrayList<>();


        for (int i = 0; i < 24; i++) {
            int index = random.nextInt(23);
            while (usedNumbers.contains(index)) {
                index = random.nextInt(23);
            }
            if (((1 << index) | this.board.whitePieces) == this.board.whitePieces && !this.board.isPieceOnMill(index, AppConstants.BLACK)) {
                bestSpot = index;
                break;
            }
            usedNumbers.add(index);
        }
        return bestSpot;

    }

    public int getBestRemoveOption_HEURISTIC_FUNCTION()
    {
        int bestSpot = -1, maxEvaluation = 0;
        HashMap<Integer, Integer> possibleRemovesEvaluation = new LinkedHashMap<>();

        List<Integer> list = getAllPossibleRemoveMoves();

        for (int i = 0; i < list.size(); i++) {
            int key = list.get(i);
            int value = 0;
            possibleRemovesEvaluation.put(key, value);
        }

        for (Map.Entry<Integer, Integer> entry : possibleRemovesEvaluation.entrySet()) entry.setValue(isHasNeighbor(entry.getKey()));
        for (Map.Entry<Integer, Integer> entry : possibleRemovesEvaluation.entrySet())
        {
            if (entry.getValue() > maxEvaluation)
            {
                maxEvaluation = entry.getValue();
                bestSpot = entry.getKey();
            }
        }
        System.out.println("Best move evaluation: " + maxEvaluation);
        if (maxEvaluation == 0) bestSpot = getBestRemoveOption();
        return bestSpot;
    }


    public int isHasNeighbor(int index)
    {

            if (this.view.isWhite(index))
            {
                for (Map.Entry<Integer, ArrayList<Integer>> entry : this.board.Neighbors.entrySet())
                {
                    if (entry.getKey() == index)
                    {
                        for (int j = 0; j < entry.getValue().size(); j++)
                        {
                            if (this.view.isWhite(entry.getValue().get(j))) return 2;
                        }
                        break;
                    }
                }
            }

        return 0;
    }




//    public int[] getBestSpotPhraseTwo(View view) {
//        int[] arr = new int[2];
//        Random random = new Random();
//        int index1 = random.nextInt(23);
//        outerloop:
//        for (int index = 0; index < 100; index++) {
//            for (Map.Entry<Button, Integer> entry : view.buttonIntegerHashMap.entrySet()) {
//                if (entry.getValue() == index1) {
//                    if (entry.getKey().getStyle().equals("-fx-background-color: black;")) {
//                        for (Map.Entry<Integer, ArrayList<Integer>> entry1 : this.board.Neighbors.entrySet()) {
//                            if (entry1.getKey() == index1) {
//                                for (int j = 0; j < entry1.getValue().size(); j++) {
//                                    if (!this.board.isOccupied(entry1.getValue().get(j))) {
//                                        arr[0] = index1;
//                                        arr[1] = entry1.getValue().get(j);
//                                        break outerloop;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                index1 = random.nextInt(23);
//            }
//
//        }
//
//        return arr;
//    }

    public int[] getBestSpotPhraseTwo_HEURISTIC_FUNCTION()
    {
        int[] bestMoves = new int[2];
        int maxEvaluation = -5, evaluation = 0;
        HashMap<HashMap<Integer,Integer>, Integer> possibleMovesEvaluation = new LinkedHashMap<>();
        HashMap<Integer,Integer>possibleMovePieces = getAllPossibleMoving();
        for (Map.Entry<Integer, Integer> entry : possibleMovePieces.entrySet())
        {
            HashMap<Integer,Integer>Tmp = new LinkedHashMap<>();
            Tmp.put(entry.getKey(), entry.getValue());
            this.board.blackPieces ^= (1 << entry.getKey());
            possibleMovesEvaluation.put(Tmp, isPotentialMillFormation(entry.getValue()) + isBlockMillPossible(entry.getValue()) + isMillActive(entry.getKey()) + isPieceNeighborAndMillNotOccupied(entry.getValue()));
            this.board.blackPieces |= (1 << entry.getKey());
        }

        for (HashMap.Entry<HashMap<Integer,Integer>, Integer> entry : possibleMovesEvaluation.entrySet())
        {
            evaluation = entry.getValue();
            if (evaluation > maxEvaluation)
            {
                maxEvaluation = evaluation;
                HashMap<Integer,Integer> moves = entry.getKey();
                for (HashMap.Entry<Integer, Integer> moveEntry : moves.entrySet()) {
                    bestMoves[0] = moveEntry.getKey();
                    bestMoves[1] = moveEntry.getValue();
                    // Exit the loop after getting the first key-value pair
                    break;
                }
            }
        }

        System.out.println("bestMove from index: " + bestMoves[0]);
        System.out.println("bestMove to index: " + bestMoves[1]);
        System.out.println("bestMove evaluation: " + maxEvaluation);
        return bestMoves;

    }


    public int HEURISTIC_FUNCTION()
    {
        int bestSpot = -1, maxEvaluation = -5;
        Random random = new Random();
        HashMap<Integer, Integer> possibleMovesEvaluation = new LinkedHashMap<>();

        List<Integer> list = getAllPossibleMoves();

        for (int i = 0; i < list.size(); i++) {
            int key = list.get(i);
            int value = 0;
            possibleMovesEvaluation.put(key, value);
        }

        for (Map.Entry<Integer, Integer> entry : possibleMovesEvaluation.entrySet()) entry.setValue(isPotentialMillFormation(entry.getKey()) + isBlockMillPossible(entry.getKey()) + isBlockMiddleMillsPiece(entry.getKey()) +  isBetweenConnectedMiils(entry.getKey()) +  connectedMiddles(entry.getKey()) +  isPieceNeighborAndMillNotOccupied(entry.getKey()) + isPieceNeighbor(entry.getKey()));

        for (Map.Entry<Integer, Integer> entry : possibleMovesEvaluation.entrySet())
        {
            if (entry.getValue() > maxEvaluation)
            {
                maxEvaluation = entry.getValue();
                bestSpot = entry.getKey();
            }
        }
        System.out.println("Best move evaluation: " + maxEvaluation);
        if (maxEvaluation == 0)
        {
            List<Integer> numbers = Arrays.asList(1, 4, 7, 9, 10, 11, 12, 13, 14, 16, 19, 22);
            int index = random.nextInt(numbers.size());
            bestSpot = numbers.get(index);
            while (this.board.isOccupied(bestSpot))
            {
                bestSpot = numbers.get(index);
            }
        }
        return bestSpot;
    }

//    public int getWhiteMillCount()
//    {
//        int count = 0;
//        for (Map.Entry<Integer, Integer> entry : this.board.whiteMills.entrySet())
//        {
//            if (entry.getValue() == 1) count++;
//        }
//        return count;
//    }
//
//    public int getBlackMillCount()
//    {
//        int count = 0;
//        for (Map.Entry<Integer, Integer> entry : this.board.blackMills.entrySet())
//        {
//            if (entry.getValue() == 1) count++;
//        }
//        return count;
//    }

    public int isMillActive(int index)
    {
        for (Map.Entry<Integer, Integer> entry : this.board.blackMills.entrySet())
        {
            if ((entry.getKey() & (1 << index)) != 0 && entry.getValue() == 1)
            {
                for (Map.Entry<Integer, ArrayList<Integer>> entry1 : this.board.Neighbors.entrySet())
                {
                    if (entry1.getKey() == index)
                    {
                        for (int i = 0; i < entry1.getValue().size(); i++)
                        {
                            if (!this.board.isOccupied(entry1.getValue().get(i)))
                            {
                                return 6;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public int isPotentialMillFormation (int index)
    {
        if (!this.board.isOccupied(index))
        {
            if (this.board.checkMillAI(index))
            {
                System.out.println("isPotentialMillFormation index: " + index);
                return 15;
            }
        }
        return 0;
    }

    public int isBlockMillPossible(int index)
    {
        if (!this.board.isOccupied(index))
        {
            if (this.board.isBlockMillPossible(index))
            {
                System.out.println("isBlockMillPossible index: " + index);
                return 7;
            }
        }
        return 0;
    }

    public int isPieceNeighborAndMillNotOccupied(int index)
    {
        if (isPieceNeighbor(index) == 1)
        {
            for (Map.Entry<Integer, ArrayList<Integer>> entry : this.board.Neighbors.entrySet())
            {
                if (entry.getKey() == index)
                {
                    for (int i = 0; i < entry.getValue().size(); i++)
                    {
                        if (!this.board.isOccupied(entry.getValue().get(i)))
                        {
                            this.board.blackPieces |= (1 << index);
                            if (this.board.checkMillAI(entry.getValue().get(i)))
                            {
                                System.out.println("isPieceNeighborAndMillNotOccupied index: " + index);
                                this.board.blackPieces ^= (1 << index);
                                return 1;
                            }
                            this.board.blackPieces ^= (1 << index);
                        }
                        else
                        {
                            for (Map.Entry<Integer, ArrayList<Integer>> entry1 : this.board.Neighbors.entrySet())
                            {
                                if (entry1.getKey() == entry.getValue().get(i))
                                {
                                    for (int j = 0; j < entry1.getValue().size(); j++) {
                                        if (!this.board.isOccupied(entry1.getValue().get(j)))
                                        {
                                            this.board.blackPieces |= (1 << index);
                                            if (this.board.checkMillAI(entry1.getValue().get(j)))
                                            {
                                                System.out.println("isPieceNeighborAndMillNotOccupied index: " + index);
                                                this.board.blackPieces ^= (1 << index);
                                                return 1;
                                            }
                                            this.board.blackPieces ^= (1 << index);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return 0;
    }

    public int isBlockMiddleMillsPiece(int index)
    {
        for (Map.Entry<Integer, ArrayList<Integer>> entry : this.board.Neighbors.entrySet())
        {
            if (index == entry.getKey())
            {
                for (int i = 0; i < entry.getValue().size(); i++)
                {
                    if (!this.view.isWhite(entry.getValue().get(i))) return 0;
                }
                return 5;
            }
        }
        return 0;
    }

    public int isBetweenConnectedMiils(int index)
    {
        for (Map.Entry<Integer, ArrayList<Integer>> entry : this.board.Neighbors.entrySet())
        {
            if (index == entry.getKey())
            {
                for (int i = 0; i < entry.getValue().size(); i++)
                {
                    if (!this.view.isBlack(entry.getValue().get(i))) return 0;
                }
                return 4;
            }
        }
        return 0;
    }

    public int connectedMiddles(int index)
    {
        this.board.setConnectedMiddlesHashMap();
        for (Map.Entry<Integer, Integer> entry: this.board.connectedMiddles.entrySet())
        {
            if ((index == entry.getKey() && this.view.isBlack(entry.getValue()))
            || (index == entry.getValue() && this.view.isBlack(entry.getKey()))) return 3;
        }
        return  0;
    }


    public int isPieceNeighbor(int index)
    {
        for (int i = 0; i < 24; i++)
        {
            if (!this.board.isOccupied(index) && this.view.isBlack(i))
            {
                if (this.board.isNeighbor(index, i))
                {
                    System.out.println("isPieceNeighbor index: " + index);
                    return 1;
                }
            }
        }
        return 0;
    }

    public List<Integer>getAllPossibleMoves()
    {
        List<Integer>possibleMoves = new ArrayList<>();
        for (int i = 0; i < 24; i++)
        {
            if (!this.board.isOccupied(i))
            {
                possibleMoves.add(i);
            }
        }
        return possibleMoves;
    }

    public List<Integer> getAllPossibleRemoveMoves()
    {
        List<Integer>possibleRemoves = new ArrayList<>();
        for (int i = 0; i < 24; i++)
        {
            if (this.view.isWhite(i) && !this.board.isPieceOnMill(i, AppConstants.BLACK))
            {
                possibleRemoves.add(i);
            }
        }
        return possibleRemoves;
    }

    public HashMap<Integer, Integer> getAllPossibleMoving()
    {
        HashMap<Integer, Integer>possibleRemoves = new LinkedHashMap<>();
        for (int i = 0; i < 24; i++)
        {
            if (this.view.isBlack(i)) {
                breakLoop:
                for (Map.Entry<Integer, ArrayList<Integer>> entry: this.board.Neighbors.entrySet())
                {
                    if (entry.getKey() == i)
                    {
                        for (int j = 0; j < entry.getValue().size(); j++)
                        {
                            if (!this.board.isOccupied(entry.getValue().get(j)))
                            {
                                possibleRemoves.put(i, entry.getValue().get(j));
                            }
                        }
                        break breakLoop;
                    }
                }
            }
        }
        return possibleRemoves;
    }

}

























