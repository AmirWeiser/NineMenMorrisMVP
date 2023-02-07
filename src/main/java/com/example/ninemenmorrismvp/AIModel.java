//package com.example.ninemenmorrismvp;
//
//import java.util.Map;
//
//public class AIModel extends Model {
//
//    public int getBestSpot(long board, int color)
//    {
//        return 0;
//    }
//
//    public int fillMill(long board, int color)
//    {
//        int bestSpot = -1;
//
//        // Apply the first rule: Place pieces to create mills
//        long freeSpots = ~(whitePieces | blackPieces);
//        for (int i = 0; i < 24; i++) {
//            int spot = 1 << i;
//            if ((freeSpots & spot) != 0) {
//                long temp = whitePieces | blackPieces | spot;
//                if (isMill(temp, spot)) {
//                    bestSpot = i;
//                    break;
//                }
//            }
//        }
//        return bestSpot;
//    }
//
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
//}
