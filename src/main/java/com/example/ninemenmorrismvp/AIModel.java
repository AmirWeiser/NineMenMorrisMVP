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

    /**
     * Chooses the index of a button to be removed if it is legal
     * @return the index of the button
     */
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

    /**
     * Iterating thorough all the possible delete moves and checking which move has the best potential for winning the game
     * @return the index if the piece to delete
     */
    public int HEURISTIC_FUNCTION_DELETE()
    {
        int bestSpot = -1, maxEvaluation = 0;
        HashMap<Integer, Integer> possibleRemovesEvaluation = new LinkedHashMap<>();

        List<Integer> list = getAllPossibleRemoveMoves();

        for (int i = 0; i < list.size(); i++) {
            int key = list.get(i);
            int value = 0;
            possibleRemovesEvaluation.put(key, value);
        }

        for (Map.Entry<Integer, Integer> entry : possibleRemovesEvaluation.entrySet()) entry.setValue(countWhiteNeighbors(entry.getKey()));
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

    /**
     * counts the neighbors of the specific index
     * @param index the index of the piece
     * @return the number of neighbors
     */
    public int countWhiteNeighbors(int index)
    {
        int count = 0;
        for (Map.Entry<Integer, ArrayList<Integer>> entry : this.board.Neighbors.entrySet())
        {
            if (entry.getKey() == index)
            {
                for (int i = 0; i < entry.getValue().size(); i++)
                {
                    if (this.view.isWhite(entry.getValue().get(i))) count++;
                }
            }
        }
        return count;
    }


//    public int isHasNeighbor(int index)
//    {
//
//            if (this.view.isWhite(index))
//            {
//                for (Map.Entry<Integer, ArrayList<Integer>> entry : this.board.Neighbors.entrySet())
//                {
//                    if (entry.getKey() == index)
//                    {
//                        for (int j = 0; j < entry.getValue().size(); j++)
//                        {
//                            if (this.view.isWhite(entry.getValue().get(j))) return 2;
//                        }
//                        break;
//                    }
//                }
//            }
//
//        return 0;
//    }





    /**
     * Iterating thorough all the possible Moving moves and checking which move has the best potential for winning the game
     * @return an Integer array, the first element is the piece to move, the second element is the destination button.
     */
    public int[] HEURISTIC_FUNCTION_PHRASE_TWO()
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

        if (isPotentialMillFormation(bestMoves[1]) != 0) System.out.println("isPotentialMillFormation");
        if (isBlockMillPossible(bestMoves[1]) != 0) System.out.println("isBlockMillPossible");
        if (isMillActive(bestMoves[0]) != 0) System.out.println("isMillActive");
        if (isPieceNeighborAndMillNotOccupied(bestMoves[1]) != 0) System.out.println("isPieceNeighborAndMillNotOccupied");

        System.out.println("bestMove evaluation: " + maxEvaluation);
        return bestMoves;

    }


    /**
     * Iterating thorough all the possible displaying moves and checking which move has the best potential for winning the game
     * @return the index of the best move.
     */
    public int HEURISTIC_FUNCTION_PHRASE_ONE()
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

        if (isPotentialMillFormation(bestSpot) != 0) System.out.println("isPotentialMillFormation");
        if (isBlockMillPossible(bestSpot) != 0) System.out.println("isBlockMillPossible");
        if (isBlockMiddleMillsPiece(bestSpot) != 0) System.out.println("isBlockMiddleMillsPiece");
        if (isBetweenConnectedMiils(bestSpot) != 0) System.out.println("isBetweenConnectedMiils");
        if (connectedMiddles(bestSpot) != 0) System.out.println("connectedMiddles");
        if (isPieceNeighborAndMillNotOccupied(bestSpot) != 0) System.out.println("isPieceNeighborAndMillNotOccupied");
        if (isPieceNeighbor(bestSpot) != 0) System.out.println("isPieceNeighbor");
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

    /**
     * checks if the index is on an active mill
     * @param index index of the button
     * @return heuristic evaluation
     */
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

    /**
     * checks if by displaying the piece, it creates a mill
     * @param index index of the button
     * @return heuristic evaluation
     */
    public int isPotentialMillFormation (int index)
    {
        if (!this.board.isOccupied(index))
        {
            if (this.board.checkMillAI(index))
            {
                return 50;
            }
        }
        return 0;
    }

    /**
     * checks if by displaying the piece, it clocks white mill
     * @param index index of the button
     * @return heuristic evaluation
     */
    public int isBlockMillPossible(int index)
    {
        if (!this.board.isOccupied(index))
        {
            if (this.board.isBlockMillPossible(index))
            {
                return 30;
            }
        }
        return 0;
    }

    /**
     * checks if a piece has a black neighbor and that the black neighbor has a free neighbor, so that it could be a potential mill formation
     * @param index index of the button
     * @return heuristic evaluation
     */
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

    /**
     * checks if by displaying the piece, it blocks two connected middle mills.
     * @param index index of the button
     * @return heuristic evaluation
     */
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
                    return 10;
            }
        }
        return 0;
    }

    /**
     * checks if an index, is the connected spot between two middle mills
     * @param index index of the button
     * @return heuristic evaluation
     */
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
                if (isPieceNeighborAndMillNotOccupied(index) == 1)
                {
                    return 7;
                }
            }
        }
        return 0;
    }

    /**
     * checks if an index is a middle mill, and its opposite middle mill is occupied by a black piece
     * @param index index of the button
     * @return heuristic evaluation
     */
    public int connectedMiddles(int index)
    {
        this.board.setConnectedMiddlesHashMap();
        for (Map.Entry<Integer, Integer> entry: this.board.connectedMiddles.entrySet())
        {
            if ((index == entry.getKey() && this.view.isBlack(entry.getValue()))
            || (index == entry.getValue() && this.view.isBlack(entry.getKey()))) return 5;
        }
        return  0;
    }


    /**
     * checks if a piece is a neighbor of a black piece
     * @param index index of the button
     * @return heuristic evaluation
     */
    public int isPieceNeighbor(int index)
    {
        for (int i = 0; i < 24; i++)
        {
            if (!this.board.isOccupied(index) && this.view.isBlack(i))
            {
                if (this.board.isNeighbor(index, i))
                {
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * @return return all possible spots that are not occupied
     */
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

    /**
     * @return return all possible spots that are legal to be deleted
     */
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

    /**
     * @return return all possible spots that are legal to be moved
     */
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

























