import java.util.Arrays;
import java.util.Scanner;


public class EnglishCheckers {

    // Global constants
    public static final int RED = 1;
    public static final int BLUE = -1;
    public static final int EMPTY = 0;

    public static final int SIZE = 8;

    // You can ignore these constants
    public static final int MARK = 3;
    public static EnglishCheckersGUI grid;

    public static Scanner getPlayerFullMoveScanner = null;
    public static Scanner getStrategyScanner = null;

    public static final int RANDOM = 1;
    public static final int DEFENSIVE = 2;
    public static final int SIDES = 3;
    public static final int CUSTOM = 4;


    public static void main(String[] args) {

        // ******** Don't delete *********
        // CREATE THE GRAPHICAL GRID
        grid = new EnglishCheckersGUI(SIZE);
        // *******************************

        int[][] test = new int[8][8];
        test[6][4] = 1;
//        test[1][2] = 1;
//        test[5][5] = -1;
//        test[6][4] = 1;
//        test[4][4] = 1;
//        test[4][4] = 1;
//        playMove(test,1,6,4,7,3);

//        test = playMove(test, 1, 4, 4, 6, 2);
//        test= playMove(test,-1,5,1,3,3);
//        test= playMove(test,-1,3,3,2,2);
//        test= playMove(test,1,4,2,6,4);
//        randomPlayer(test,-1);
//        showBoard(test);
//        showBoard(createBoard());
//		printMatrix(sdsds);
        interactivePlay();
//        twoPlayers();



		/* ******** Don't delete ********* */
        if (getPlayerFullMoveScanner != null) {
            getPlayerFullMoveScanner.close();
        }
        if (getStrategyScanner != null) {
            getStrategyScanner.close();
        }
        /* ******************************* */

    }


    public static int[][] createBoard() {
        int[][] board = new int[8][8]; //creating the board itself
        for (int i = 0; i < SIZE; i++) { //initiating the board with 0's
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
            }
        }
        //initiating the red  & blue cheeckers
        for (int i = 0; i < SIZE; i++) {
            if (i < 3) { //puts the reds
                if (i % 2 == 0) { //if the row's number is pair
                    for (int j = 0; j < SIZE; j = j + 2) {
                        board[i][j] = RED;
                    }
                } else { //if the row's number is odd
                    for (int j = 1; j < SIZE; j = j + 2) {
                        board[i][j] = RED;
                    }
                }
            } else if (i > 4 && i < 8) { //puts the blues
                if (i % 2 == 0) { //if the row's number is pair
                    for (int j = 0; j < SIZE; j = j + 2) {
                        board[i][j] = BLUE;
                    }
                } else { //if the row's number is odd
                    for (int j = 1; j < SIZE; j = j + 2) {
                        board[i][j] = BLUE;
                    }
                }
            }
        }
        return board;
    }


    public static int[][] playerDiscs(int[][] board, int player) {
        //count how many discs there is of the player's
        int discsNUm = 0; //counts how many discs of the same player
        for (int i = 0; i < SIZE; i++) { //go over the board
            for (int j = 0; j < SIZE; j++) {
                if (player > 0) { //if the red player
                    if (board[i][j] > 0) //if its the red player's disc
                        discsNUm++;
                } else if (player < 0) { //if its the blue player
                    if (board[i][j] < 0) //if its the blue player's disc
                        discsNUm++;
                }
            }
        }
        int currDisc = 0; //counter for the disc we fill in the matrix
        int[][] positions = new int[discsNUm][2]; //creating the positions two dimensional array
        for (int i = 0; i < SIZE; i++) { //go over the board
            for (int j = 0; j < SIZE; j++) {
                if (player > 0) { //if the red player
                    if (board[i][j] > 0) { //if its the red player's disc
                        if (currDisc < discsNUm) {
                            positions[currDisc][0] = i;
                            positions[currDisc][1] = j;
                            currDisc++;
                        }
                    }
                } else if (player < 0) { //if its the blue player
                    if (board[i][j] < 0) { //if its the blue player's disc
                        if (currDisc < discsNUm) {
                            positions[currDisc][0] = i;
                            positions[currDisc][1] = j;
                            currDisc++;
                        }
                    }
                }
            }
        }
        return positions;
    }


    public static boolean isBasicMoveValid(int[][] board, int player, int fromRow, int fromCol, int toRow, int toCol) {
        //checks if the input is legal
        if (fromRow < 0 || fromRow > SIZE - 1 || toRow < 0 || toRow > SIZE - 1 || fromCol < 0 || fromCol > SIZE - 1 || toCol < 0 || toCol > SIZE - 1)
            return false;
        if (player > 0) {//checks if player 1 has disc from where we want to move
            if (board[fromRow][fromCol] <= 0)
                return false;
        } else if (player < 0) { //checks if player -1 has disc from where we want to move
            if (board[fromRow][fromCol] >= 0)
                return false;
        }
        if (board[toRow][toCol] != 0) //checks if the square we want to move to is empty
            return false;
        //checks player's 1 not queen disc if can move
        if (board[fromRow][fromCol] == 1) {//non queen disc
            //with not queen disc can move only forward
            if ((toCol != fromCol - 1) & (toCol != fromCol + 1))//checks if cordinats correct to the cols
                return false;
            if (toRow != fromRow + 1) //not queen disc can move only forward- checks if the row cordinates is valid
                return false;
        } else if (board[fromRow][fromCol] == 2) { //check for queen disc of player 1 for "basic move"
            if ((toCol != fromCol - 1) & (toCol != fromCol + 1))
                return false;
            if ((toRow != fromRow - 1) & (toRow != fromRow + 1)) //queen can move backwards
                return false;
        }
        if (board[fromRow][fromCol] == -1) {
            //with not queen disc can move only to the cols that are next to it
            if ((toCol != fromCol - 1) & (toCol != fromCol + 1))
                return false;
            if (toRow != fromRow - 1) //not queen disc can move only forward
                return false;
        } else if (board[fromRow][fromCol] == -2) { //check for queen disc of player -1 for "basic move"
            //with  queen disc can move only to the cols that are next to it with "basic move"
            if ((toCol != fromCol - 1) & (toCol != fromCol + 1))
                return false;
            if ((toRow != fromRow - 1) & (toRow != fromRow + 1)) //queen can move backwards
                return false;
        }
        return true;
    }


    public static int[][] getAllBasicMoves(int[][] board, int player) {
        int[][] playerDiscs = playerDiscs(board, player); //getting the player's discs on the board
        int count = 0; //counts the number of moves that can do
        for (int i = 0; i < playerDiscs.length; i++) { //go through the discs of the player
            for (int j = playerDiscs[i][0] - 1; j < playerDiscs[i][0] + 2; j = j + 2) { //go through the disc's rows
                for (int k = playerDiscs[i][1] - 1; k < playerDiscs[i][1] + 2; k = k + 2) {//go through the disc's cols
                    if (isBasicMoveValid(board, player, playerDiscs[i][0], playerDiscs[i][1], j, k))
                        count++;
                }
            }
        }
        int[][] moves = new int[count][4];
        count = 0;
        for (int i = 0; i < playerDiscs.length; i++) { //go through the discs of the player
            for (int j = playerDiscs[i][0] - 1; j < playerDiscs[i][0] + 2; j = j + 2) { //go through the disc's rows
                for (int k = playerDiscs[i][1] - 1; k < playerDiscs[i][1] + 2; k = k + 2) {//go through the disc's cols
                    if (isBasicMoveValid(board, player, playerDiscs[i][0], playerDiscs[i][1], j, k)) {
                        moves[count][0] = playerDiscs[i][0];
                        moves[count][1] = playerDiscs[i][1];
                        moves[count][2] = j;
                        moves[count][3] = k;
                        count++;
                    }
                }
            }
        }
        return moves;
    }


    public static boolean isBasicJumpValid(int[][] board, int player, int fromRow, int fromCol, int toRow, int toCol) {
        //checks if the input is legal
        if (fromRow < 0 || fromRow > SIZE - 1 || toRow < 0 || toRow > SIZE - 1 || fromCol < 0 || fromCol > SIZE - 1 || toCol < 0 || toCol > SIZE - 1)
            return false;
        if (player > 0) {//checks if player 1 has disc from where we want to move
            if (board[fromRow][fromCol] <= 0)
                return false;
        } else if (player < 0) { //checks if player -1 has disc from where we want to move
            if (board[fromRow][fromCol] >= 0)
                return false;
        }
        if (player > 0) {// checks if there is disc of player -1 where we want to jump over with player 1
            if (board[fromRow][fromCol] == 1) {//if its not queen disc it can jump only forward
                if (toRow != fromRow + 2) //checks if the cordinations are valid
                    return false;
                else if ((toCol != fromCol + 2) & (toCol != fromCol - 2)) {
                    return false;
                } else if (((toRow == fromRow + 2) && (toCol == fromCol + 2))) {//if the cordinations correct checks to the right side jump
                    if (board[fromRow + 1][fromCol + 1] >= 0) //if there is non enemy disc to the right
                        return false;
                    else if (board[toRow][toCol] != 0)//if the square we want to jump to is not empty
                        return false;
                } else if (((toRow == fromRow + 2) && (toCol == fromCol - 2))) {//checks if there is enemys disc to the left
                    if (board[fromRow + 1][fromCol - 1] >= 0)//if there is non enemy disc to the left
                        return false;
                    else if (board[toRow][toCol] != 0)//if the square we want to jump to is not empty
                        return false;
                }
            } else if (board[fromRow][fromCol] == 2) {//queen disc can jump backwards aswell
                if (((toRow != fromRow - 2) && (toCol != fromCol + 2)) & ((toRow != fromRow + 2) && (toCol != fromCol - 2)) & ((toRow != fromRow - 2) && (toCol != fromCol - 2)) & ((toRow != fromRow + 2) && (toCol != fromCol + 2))) //checks if the cordinations are legal
                    return false;
                else if (((toRow == fromRow + 2) && (toCol == fromCol + 2))) {//if the cordinations correct checks to the right side jump
                    if (board[fromRow + 1][fromCol + 1] >= 0) //if there is non enemy disc to the right forward
                        return false;
                    else if (board[toRow][toCol] != 0)//if the square we want to jump to is not empty
                        return false;
                } else if (((toRow == fromRow + 2) && (toCol == fromCol - 2))) {//checks if there is enemys disc to the left forward
                    if (board[fromRow + 1][fromCol - 1] >= 0)//if there is non enemy disc to the left
                        return false;
                    else if (board[toRow][toCol] != 0)//if the square we want to jump to is not empty
                        return false;
                } else if (((toRow == fromRow - 2) && (toCol == fromCol - 2))) {//if the cordinations correct checks to the right side jump (back)
                    if (board[fromRow - 1][fromCol - 1] >= 0) //if there is non enemy disc to the right
                        return false;
                    else if (board[toRow][toCol] != 0)//if the square we want to jump to is not empty
                        return false;
                } else if (((toRow == fromRow - 2) && (toCol == fromCol + 2))) {//if the cordinations correct checks to the right side jump (back)
                    if (board[fromRow - 1][fromCol + 1] >= 0) //if there is non enemy disc to the right
                        return false;
                    else if (board[toRow][toCol] != 0)//if the square we want to jump to is not empty
                        return false;
                }
            }
        } else if (player < 0) {
            if (board[fromRow][fromCol] == -1) {//if its not queen disc it can jump only forward
                if (toRow != fromRow - 2)
                    return false;
                else if ((toCol != fromCol - 2) & (toCol != fromCol + 2))
                    return false;
                else if (((toRow == fromRow - 2) && (toCol == fromCol - 2))) {//if the cordinations correct checks to the left side jump
                    if (board[fromRow - 1][fromCol - 1] <= 0) //if there is non enemy disc to the left
                        return false;
                    else if (board[toRow][toCol] != 0)//if the square we want to jump to is not empty
                        return false;
                } else if (((toRow == fromRow - 2) && (toCol == fromCol + 2))) {//if the cordinations correct checks to the right side jump
                    if (board[fromRow - 1][fromCol + 1] <= 0)//if there is non enemy disc to the right
                        return false;
                    else if (board[toRow][toCol] != 0)//if the square we want to jump to is not empty
                        return false;
                }
            } else if (board[fromRow][fromCol] == -2) {//queen disc can jump backwards aswell
                if (((toRow != fromRow - 2) && (toCol != fromCol + 2)) & ((toRow != fromRow + 2) && (toCol != fromCol - 2) & ((toRow != fromRow - 2) && (toCol != fromCol - 2)) & ((toRow != fromRow + 2) && (toCol != fromCol + 2)))) //checks if the cordinations are legal
                    return false;
                else if (((toRow == fromRow + 2) && (toCol == fromCol + 2))) {//if the cordinations correct checks to the  side jump forward
                    if (board[fromRow + 1][fromCol + 1] <= 0) //if there is non enemy disc to the right
                        return false;
                    else if (board[toRow][toCol] != 0)//if the square we want to jump to is not empty
                        return false;
                } else if (((toRow == fromRow + 2) && (toCol == fromCol - 2))) {//checks if there is enemys disc to the left forward
                    if (board[fromRow + 1][fromCol - 1] <= 0)//if there is non enemy disc to the left
                        return false;
                    else if (board[toRow][toCol] != 0)//if the square we want to jump to is not empty
                        return false;
                } else if (((toRow == fromRow - 2) && (toCol == fromCol - 2))) {//if the cordinations correct checks to the right side jump (back)
                    if (board[fromRow - 1][fromCol - 1] <= 0) //if there is non enemy disc to the right
                        return false;
                    else if (board[toRow][toCol] != 0)//if the square we want to jump to is not empty
                        return false;
                } else if (((toRow == fromRow - 2) && (toCol == fromCol + 2))) {//if the cordinations correct checks to the right side jump (back)
                    if (board[fromRow - 1][fromCol + 1] <= 0) //if there is non enemy disc to the right
                        return false;
                    else if (board[toRow][toCol] != 0)//if the square we want to jump to is not empty
                        return false;
                }
            }
        }
        //if non of the above made it false then it is legal jump
        return true;
    }


    public static int[][] getRestrictedBasicJumps(int[][] board, int player, int row, int col) {
        int counter = 0; //counts the number of jumps possible
        for (int i = row - 2; i < row + 3; i = i + 4) { //jumps can be done only in diagonals 2 squares away
            for (int j = col - 2; j < col + 3; j = j + 4) {//checks all the options for jump and count it
                if (isBasicJumpValid(board, player, row, col, i, j))
                    counter++;
            }
        }
        int[][] moves = new int[counter][4]; //initiating the array after we know how many moves can be done
        counter = 0; //reseting counter to go over moves index
        for (int i = row - 2; i < row + 3; i = i + 4) { //checks row's number we jump to
            for (int j = col - 2; j < col + 3; j = j + 4) {//checks col's number we jump to
                if (isBasicJumpValid(board, player, row, col, i, j)) { //if its valid jump adds it to the array
                    moves[counter][0] = row;
                    moves[counter][1] = col;
                    moves[counter][2] = i;
                    moves[counter][3] = j;
                    counter++;
                }
            }
        }
        return moves;
    }

    public static int[][] getAllBasicJumps(int[][] board, int player) {
        int[][] playerDiscs = playerDiscs(board, player); //getting the player's discs on the board
        int count = 0; //counts the number of moves that can do
        for (int i = 0; i < playerDiscs.length; i++) { //go through the discs of the player
            for (int j = playerDiscs[i][0] - 2; j < playerDiscs[i][0] + 3; j = j + 4) { //go through the disc's rows
                for (int k = playerDiscs[i][1] - 2; k < playerDiscs[i][1] + 3; k = k + 4) {//go through the disc's cols
                    if (isBasicJumpValid(board, player, playerDiscs[i][0], playerDiscs[i][1], j, k))
                        count++;
                }
            }
        }
        int[][] moves = new int[count][4];
        count = 0;
        for (int i = 0; i < playerDiscs.length; i++) { //go through the discs of the player
            for (int j = playerDiscs[i][0] - 2; j < playerDiscs[i][0] + 3; j = j + 4) { //go through the disc's rows
                for (int k = playerDiscs[i][1] - 2; k < playerDiscs[i][1] + 3; k = k + 4) {//go through the disc's cols
                    if (isBasicJumpValid(board, player, playerDiscs[i][0], playerDiscs[i][1], j, k)) {
                        moves[count][0] = playerDiscs[i][0];
                        moves[count][1] = playerDiscs[i][1];
                        moves[count][2] = j;
                        moves[count][3] = k;
                        count++;
                    }
                }
            }
        }
        return moves;
    }


    public static boolean canJump(int[][] board, int player) {
        int[][] jumps = null; //initiating array that shows the cordinates of all jumps that can be made
        jumps = getAllBasicJumps(board, player);//if the array in empty there is no jumps that can be made
        if (jumps.length == 0)
            return false;
        return true;
    }


    public static boolean isMoveValid(int[][] board, int player, int fromRow, int fromCol, int toRow, int toCol) {
        if (player == 1) {
            if (getAllBasicJumps(board, 1).length == 0) {//first we checks if we can do jump because it comes before basic move
                if (isBasicMoveValid(board, player, fromRow, fromCol, toRow, toCol))//if jump cannot be done checks if can make basic move
                    return true;
            } else if (isBasicJumpValid(board, player, fromRow, fromCol, toRow, toCol))//checks if can make the cordinates jump
                return true;
        } else if (player == -1) {
            if (getAllBasicJumps(board, -1).length == 0) {//first we checks if we can do jump because it comes before basic move
                if (isBasicMoveValid(board, player, fromRow, fromCol, toRow, toCol))//if jump cannot be done checks if can make basic move
                    return true;
            } else if (isBasicJumpValid(board, player, fromRow, fromCol, toRow, toCol))//checks if can make the cordinates jump
                return true;
        }
        return false; //if cannot jump or make basic move cannot make valid move
    }


    public static boolean hasValidMoves(int[][] board, int player) {
        if (canJump(board, player))//checks if the player can do jump
            return true;
        int[][] basicMoves = null;
        basicMoves = getAllBasicMoves(board, player);//if can't make jump checks if can make basic move
        if (basicMoves.length != 0)
            return true;
        return false;
    }


    public static int[][] playMove(int[][] board, int player, int fromRow, int fromCol, int toRow, int toCol) {
        //assume that the move is valid
        if (player == 1) {//if it is player's 1 turn
            if (isBasicJumpValid(board, player, fromRow, fromCol, toRow, toCol)) { //if the move is jump
                if (board[fromRow][fromCol] == 1) {//if its jump of not queen disc
                    if (toRow == SIZE - 1) { //if the jump is to the last row
                        board[toRow][toCol] = 2; //move the disc to the "to" square and change it to queen disc
                        if (toCol == fromCol + 2) //if the jump is to the right
                            board[fromRow + 1][fromCol + 1] = 0; //remove the enemy's disc to the right
                        else if (toCol == fromCol - 2)
                            board[fromRow + 1][fromCol - 1] = 0; //remove the enemy's disc to the left
                    } else {
                        board[toRow][toCol] = 1; //move the disc to the "to" square
                        if (toCol == fromCol + 2) //if the jump is to the right
                            board[fromRow + 1][fromCol + 1] = 0; //remove the enemy's disc to the right
                        else if (toCol == fromCol - 2)
                            board[fromRow + 1][fromCol - 1] = 0; //remove the enemy's disc to the left

                    }
                } else if (board[fromRow][fromCol] == 2) { //if its queen disc
                    board[toRow][toCol] = 2; //move the disc to the "to" square
                    if ((toRow == fromRow + 2) & (toCol == fromCol + 2))  //if the jump is forward-right
                        board[fromRow + 1][fromCol + 1] = 0; //remove the enemy's disc to the right
                    else if ((toRow == fromRow + 2) & (toCol == fromCol - 2))  //if the jump is forward-left
                        board[fromRow + 1][fromCol - 1] = 0; //remove the enemy's disc to the right
                    else if ((toRow == fromRow - 2) & (toCol == fromCol + 2))  //if the jump is backwards-right
                        board[fromRow - 1][fromCol + 1] = 0; //remove the enemy's disc to the right
                    else if ((toRow == fromRow - 2) & (toCol == fromCol - 2))  //if the jump is backwards-left
                        board[fromRow - 1][fromCol - 1] = 0; //remove the enemy's disc to the right
                }
                board[fromRow][fromCol] = 0;//make the square "from" empty
            } else {
                if (board[fromRow][fromCol] == 1) { //if the disc that moves is not queen
                    if (toRow == SIZE - 1) {//if it moves to the last row of player's 1
                        board[toRow][toCol] = 2; //become queen disc
                        board[fromRow][fromCol] = 0; //moves the disc to the last row and become queen
                    } else {//if its not move to the last row
                        board[fromRow][fromCol] = 0; //remove the disc from the "from" square
                        board[toRow][toCol] = 1;//puts the disc on the "to" square
                    }
                } else if (board[fromRow][fromCol] == 2) { //if its queen's basic move
                    board[toRow][toCol] = 2;// puts the queen in the "to" square
                    board[fromRow][fromCol] = 0;//remove the queen from the "from" square
                }
            }
        } else if (player == -1) {//if it is player's -1 turn
            if (isBasicJumpValid(board, player, fromRow, fromCol, toRow, toCol)) { //if the move is jump
                if (board[fromRow][fromCol] == -1) {//if its jump of not queen disc
                    if (toRow == 0) {//if the jump is to the last row
                        board[toRow][toCol] = -2; //move the disc to the "to" square and change it to queen disc
                        if (toCol == fromCol + 2) //if the jump is to the right
                            board[fromRow - 1][fromCol + 1] = 0; //remove the enemy's disc to the right
                        else if (toCol == fromCol - 2)
                            board[fromRow - 1][fromCol - 1] = 0; //remove the enemy's disc to the left
                    } else {
                        board[toRow][toCol] = -1; //move the disc to the "to" square
                        if (toCol == fromCol + 2) //if the jump is to the right
                            board[fromRow - 1][fromCol + 1] = 0; //remove the enemy's disc to the right
                        else if (toCol == fromCol - 2)
                            board[fromRow - 1][fromCol - 1] = 0; //remove the enemy's disc to the left
                    }
                } else if (board[fromRow][fromCol] == -2) { //if its queen disc
                    board[toRow][toCol] = -2; //move the disc to the "to" square
                    if ((toRow == fromRow + 2) & (toCol == fromCol + 2))  //if the jump is forward-right
                        board[fromRow + 1][fromCol + 1] = 0; //remove the enemy's disc to the right
                    else if ((toRow == fromRow + 2) & (toCol == fromCol - 2))  //if the jump is forward-left
                        board[fromRow + 1][fromCol - 1] = 0; //remove the enemy's disc to the right
                    else if ((toRow == fromRow - 2) & (toCol == fromCol + 2))  //if the jump is backwards-right
                        board[fromRow - 1][fromCol + 1] = 0; //remove the enemy's disc to the right
                    else if ((toRow == fromRow - 2) & (toCol == fromCol - 2))  //if the jump is backwards-left
                        board[fromRow - 1][fromCol - 1] = 0; //remove the enemy's disc to the right
                }
                board[fromRow][fromCol] = 0;//make the square "from" empty
            } else {
                if (board[fromRow][fromCol] == -1) { //if the disc that moves is not queen
                    if (toRow == 0) {//if it moves to the last row of player's -1
                        board[toRow][toCol] = -2; //become queen disc
                        board[fromRow][fromCol] = 0; //moves the disc to the last row and become queen
                    } else {//if its not move to the last row
                        board[fromRow][fromCol] = 0; //remove the disc from the "from" square
                        board[toRow][toCol] = -1;//puts the disc on the "to" square
                    }
                } else if (board[fromRow][fromCol] == -2) { //if its queen's basic move
                    board[toRow][toCol] = -2;// puts the queen in the "to" square
                    board[fromRow][fromCol] = 0;//remove the queen from the "from" square
                }
            }
        }
        return board;
    }


    public static boolean gameOver(int[][] board, int player) {
        if ((playerDiscs(board, 1).length == 0) || (playerDiscs(board, -1).length == 0))//checks if one of the players dont have discs
            return true;
        else if (!(hasValidMoves(board, player)))//checks if the player has any valid moves
            return true;
        return false;
    }


    public static int findTheLeader(int[][] board) {
        int ans = 0;
        for (int i = 0; i < SIZE; i++) {//go on the board and sum the discs
            for (int j = 0; j < SIZE; j++) {
                ans = ans + board[i][j];
            }
        }
        if (ans > 0)
            return 1;
        else if (ans < 0)
            return -1;
        else {
            return 0;
        }
    }


    public static int[][] randomPlayer(int[][] board, int player) {
        int[][] cordinates = null; //the array for the jump cordinates or the basic move cordinates
        if (!(gameOver(board, player))) {//if the game is not over the player has basic move or jump to play
            if (canJump(board, player)) {//jump comes before basic move
                cordinates = getAllBasicJumps(board, player); //gets the possible jumps cordinates of the discs on the board
                int randCord = (int) (Math.random() * (cordinates.length));//choose random disc
                playMove(board, player, cordinates[randCord][0], cordinates[randCord][1], cordinates[randCord][2], cordinates[randCord][3]);//play the jump that was choosen
            } else {
                cordinates = getAllBasicMoves(board, player);
                if (cordinates.length != 0) {
                    int randCord = (int) (Math.random() * (cordinates.length));//choose random disc
                    playMove(board, player, cordinates[randCord][0], cordinates[randCord][1], cordinates[randCord][2], cordinates[randCord][3]);//play the basic move that was choosen
                }
            }
        }
        return board;
    }


    public static int[][] defensivePlayer(int[][] board, int player) {
        int[][] movesP1 = null;
        int[][] cordinates = null;
        int counter = 0;
        if (!(gameOver(board, player))) {//if the game is not over the player has basic move or jump to play
            if (canJump(board, player)) {//jump comes before basic move
                cordinates = getAllBasicJumps(board, player); //gets the possible jumps cordinates of the discs on the board
                int randCord = (int) (Math.random() * (cordinates.length));//choose random disc
                playMove(board, player, cordinates[randCord][0], cordinates[randCord][1], cordinates[randCord][2], cordinates[randCord][3]);//play the jump that was choosen
            } else {
                movesP1 = getAllBasicMoves(board, player);//the basic moves that can be done by the player
                cordinates = new int[movesP1.length][4];//array for all the moves that can be done and the enemy cannot make jump
                for (int i = 0; i < movesP1.length; i++) { //go through the moves the player can do
                    playMove(board, player, movesP1[i][0], movesP1[i][1], movesP1[i][2], movesP1[i][3]);//chec if the enemy can jump after each possible move of the player
                    if (!(canJump(board, -player))) {//check if the enemy can make a jump after the move
                        //keeps the cordinates of the defenssive move
                        cordinates[counter][0] = movesP1[i][0];
                        cordinates[counter][1] = movesP1[i][1];
                        cordinates[counter][2] = movesP1[i][2];
                        cordinates[counter][3] = movesP1[i][3];
                        counter++;
                    }
                    playMove(board, player, movesP1[i][2], movesP1[i][3], movesP1[i][0], movesP1[i][1]);//return board back to how it was before the move
                }
                if (counter == 0) {//if no matter how the player moves the enemy can not jump
                    int randMove = ((int) (Math.random() * (movesP1.length)));
                    playMove(board, player, movesP1[randMove][0], movesP1[randMove][1], movesP1[randMove][2], movesP1[randMove][3]);
                } else {
                    int randDefMove = ((int) (Math.random() * (counter)));
                    playMove(board, player, cordinates[randDefMove][0], cordinates[randDefMove][1], cordinates[randDefMove][2], cordinates[randDefMove][3]);//play def move
                }
            }
        }
        return board;
    }

    public static int[][] sidesPlayer(int[][] board, int player) {
        int[][] cordinates = null; //the array for the jump cordinates or the basic move cordinates

        if (!(gameOver(board, player))) {//if the game is not over the player has basic move or jump to play
            if (canJump(board, player)) {//jump comes before basic move
                cordinates = getAllBasicJumps(board, player); //gets the possible jumps cordinates of the discs on the board
                int randCord = (int) (Math.random() * (cordinates.length));//choose random disc
                playMove(board, player, cordinates[randCord][0], cordinates[randCord][1], cordinates[randCord][2], cordinates[randCord][3]);//play the jump that was choosen
            } else {//if can not jump
                int mark = -1;//marks the col that is nearest to the sides where the player can move to
                int counter=0;//counts the moves can be done to the sides cols
                cordinates = getAllBasicMoves(board, player);
                int[][] sideMove = new int[cordinates.length][4];
                for (int i = 0; i < cordinates.length; i++) {//go through the moves the player can do
                    if ((cordinates[i][3] == 0) || (cordinates[i][3] == 7))//checks if the player can do move to the 0 or 7 cols
                        mark = 7;
                    else if ((mark != 7) && ((cordinates[i][3] == 1) || (cordinates[i][3] == 6))) { //checks if the player can do move to the 1 or 6 cols
                        mark = 6;
                    }
                    else if ((mark != 7) && (mark != 6) && ((cordinates[i][3] == 2) || (cordinates[i][3] == 5))) {//checks if the player can do move to the 2 or 5 cols
                        mark = 5;
                    }
                    else if ((mark != 7) && (mark != 6) && (mark != 5)  && ((cordinates[i][3] == 3) || (cordinates[i][3] == 4))) {//checks if the player can do move to the 3 or 4 cols
                        mark = 4;
                    }
                }
                for (int i = 0; i < cordinates.length; i++) {
                    if (mark == 7) {//if the player can move to the 0 or 7 cols
                        if ((cordinates[i][3] == 0) || (cordinates[i][3] == 7)) {
                            sideMove[counter][0] = cordinates[i][0];
                            sideMove[counter][1] = cordinates[i][1];
                            sideMove[counter][2] = cordinates[i][2];
                            sideMove[counter][3] = cordinates[i][3];
                            counter++;
                        }
                    }
                    else if (mark == 6) {//if the player can move to the 1 or 6 cols
                        if ((cordinates[i][3] == 1) || (cordinates[i][3] == 6)) {
                            sideMove[counter][0] = cordinates[i][0];
                            sideMove[counter][1] = cordinates[i][1];
                            sideMove[counter][2] = cordinates[i][2];
                            sideMove[counter][3] = cordinates[i][3];
                            counter++;
                        }
                    }
                    else if (mark == 5) {//if the player can move to the 2 or 5 cols
                        if ((cordinates[i][3] == 2) || (cordinates[i][3] == 5)) {
                            sideMove[counter][0] = cordinates[i][0];
                            sideMove[counter][1] = cordinates[i][1];
                            sideMove[counter][2] = cordinates[i][2];
                            sideMove[counter][3] = cordinates[i][3];
                            counter++;
                        }
                    }
                    else if (mark == 4) {//if the player can move to the 3 or 4 cols
                        if ((cordinates[i][3] == 3) || (cordinates[i][3] == 4)) {
                            sideMove[counter][0] = cordinates[i][0];
                            sideMove[counter][1] = cordinates[i][1];
                            sideMove[counter][2] = cordinates[i][2];
                            sideMove[counter][3] = cordinates[i][3];
                            counter++;
                        }
                    }
                }
                int randSideMove = ((int)(Math.random() *(counter)));//random side move
                playMove(board,player,sideMove[randSideMove][0],sideMove[randSideMove][1],sideMove[randSideMove][2],sideMove[randSideMove][3]);
            }
        }
        return board;
    }


    //******************************************************************************//

    /* ---------------------------------------------------------- *
     * Play an interactive game between the computer and you      *
     * ---------------------------------------------------------- */

    public static void interactivePlay() {
        int[][] board = createBoard();
        showBoard(board);

        System.out.println("Welcome to the interactive Checkers Game !");

        int strategy = getStrategyChoice();
        System.out.println("You are the first player (RED discs)");

        boolean oppGameOver = false;
        while (!gameOver(board, RED) && !oppGameOver) {
            board = getPlayerFullMove(board, RED);

            oppGameOver = gameOver(board, BLUE);
            if (!oppGameOver) {
                EnglishCheckersGUI.sleep(200);

                board = getStrategyFullMove(board, BLUE, strategy);
            }
        }

        int winner = 0;
        if (playerDiscs(board, RED).length == 0 | playerDiscs(board, BLUE).length == 0) {
            winner = findTheLeader(board);
        }

        if (winner == RED) {
            System.out.println();
            System.out.println("\t *************************");
            System.out.println("\t * You are the winner !! *");
            System.out.println("\t *************************");
        } else if (winner == BLUE) {
            System.out.println("\n======= You lost :( =======");
        } else
            System.out.println("\n======= DRAW =======");
    }


    /* --------------------------------------------------------- *
     * A game between two players                                *
     * --------------------------------------------------------- */
    public static void twoPlayers() {
        int[][] board = createBoard();
        showBoard(board);

        System.out.println("Welcome to the 2-player Checkers Game !");

        boolean oppGameOver = false;
        while (!gameOver(board, RED) & !oppGameOver) {
            System.out.println("\nRED's turn");
            board = getPlayerFullMove(board, RED);

            oppGameOver = gameOver(board, BLUE);
            if (!oppGameOver) {
                System.out.println("\nBLUE's turn");
                board = getPlayerFullMove(board, BLUE);
            }
        }

        int winner = 0;
        if (playerDiscs(board, RED).length == 0 | playerDiscs(board, BLUE).length == 0)
            winner = findTheLeader(board);

        System.out.println();
        System.out.println("\t ************************************");
        if (winner == RED)
            System.out.println("\t * The red player is the winner !!  *");
        else if (winner == BLUE)
            System.out.println("\t * The blue player is the winner !! *");
        else
            System.out.println("\t * DRAW !! *");
        System.out.println("\t ************************************");
    }


    /* --------------------------------------------------------- *
     * Get a complete (possibly a sequence of jumps) move        *
     * from a human player.                                      *
     * --------------------------------------------------------- */
    public static int[][] getPlayerFullMove(int[][] board, int player) {
        // Get first move/jump
        int fromRow = -1, fromCol = -1, toRow = -1, toCol = -1;
        boolean jumpingMove = canJump(board, player);
        boolean badMove = true;
        getPlayerFullMoveScanner = new Scanner(System.in);//I've modified it
        while (badMove) {
            if (player == 1) {
                System.out.println("Red, Please play:");
            } else {
                System.out.println("Blue, Please play:");
            }

            fromRow = getPlayerFullMoveScanner.nextInt();
            fromCol = getPlayerFullMoveScanner.nextInt();

            int[][] moves = jumpingMove ? getAllBasicJumps(board, player) : getAllBasicMoves(board, player);
            markPossibleMoves(board, moves, fromRow, fromCol, MARK);
            toRow = getPlayerFullMoveScanner.nextInt();
            toCol = getPlayerFullMoveScanner.nextInt();
            markPossibleMoves(board, moves, fromRow, fromCol, EMPTY);

            badMove = !isMoveValid(board, player, fromRow, fromCol, toRow, toCol);
            if (badMove)
                System.out.println("\nThis is an illegal move");
        }

        // Apply move/jump
        board = playMove(board, player, fromRow, fromCol, toRow, toCol);
        showBoard(board);

        // Get extra jumps
        if (jumpingMove) {
            boolean longMove = (getRestrictedBasicJumps(board, player, toRow, toCol).length > 0);
            while (longMove) {
                fromRow = toRow;
                fromCol = toCol;

                int[][] moves = getRestrictedBasicJumps(board, player, fromRow, fromCol);

                boolean badExtraMove = true;
                while (badExtraMove) {
                    markPossibleMoves(board, moves, fromRow, fromCol, MARK);
                    System.out.println("Continue jump:");
                    toRow = getPlayerFullMoveScanner.nextInt();
                    toCol = getPlayerFullMoveScanner.nextInt();
                    markPossibleMoves(board, moves, fromRow, fromCol, EMPTY);

                    badExtraMove = !isMoveValid(board, player, fromRow, fromCol, toRow, toCol);
                    if (badExtraMove)
                        System.out.println("\nThis is an illegal jump destination :(");
                }

                // Apply extra jump
                board = playMove(board, player, fromRow, fromCol, toRow, toCol);
                showBoard(board);

                longMove = (getRestrictedBasicJumps(board, player, toRow, toCol).length > 0);
            }
        }
        return board;
    }


    /* --------------------------------------------------------- *
     * Get a complete (possibly a sequence of jumps) move        *
     * from a strategy.                                          *
     * --------------------------------------------------------- */
    public static int[][] getStrategyFullMove(int[][] board, int player, int strategy) {
        if (strategy == RANDOM)
            board = randomPlayer(board, player);
        else if (strategy == DEFENSIVE)
            board = defensivePlayer(board, player);
        else if (strategy == SIDES)
            board = sidesPlayer(board, player);

        showBoard(board);
        return board;
    }


    /* --------------------------------------------------------- *
     * Get a strategy choice before the game.                    *
     * --------------------------------------------------------- */
    public static int getStrategyChoice() {
        int strategy = -1;
        getStrategyScanner = new Scanner(System.in);
        System.out.println("Choose the strategy of your opponent:" +
                "\n\t(" + RANDOM + ") - Random player" +
                "\n\t(" + DEFENSIVE + ") - Defensive player" +
                "\n\t(" + SIDES + ") - To-the-Sides player player");
        while (strategy != RANDOM & strategy != DEFENSIVE
                & strategy != SIDES) {
            strategy = getStrategyScanner.nextInt();
        }
        return strategy;
    }


    /* --------------------------------------- *
     * Print the possible moves                *
     * --------------------------------------- */
    public static void printMoves(int[][] possibleMoves) {
        for (int i = 0; i < 4; i = i + 1) {
            for (int j = 0; j < possibleMoves.length; j = j + 1)
                System.out.print(" " + possibleMoves[j][i]);
            System.out.println();
        }
    }


    /* --------------------------------------- *
     * Mark/unmark the possible moves          *
     * --------------------------------------- */
    public static void markPossibleMoves(int[][] board, int[][] moves, int fromRow, int fromColumn, int value) {
        for (int i = 0; i < moves.length; i = i + 1)
            if (moves[i][0] == fromRow & moves[i][1] == fromColumn)
                board[moves[i][2]][moves[i][3]] = value;

        showBoard(board);
    }


    /* --------------------------------------------------------------------------- *
     * Shows the board in a graphic window                                         *
     * you can use it without understanding how it works.                          *
     * --------------------------------------------------------------------------- */
    public static void showBoard(int[][] board) {
        grid.showBoard(board);
    }


    /* --------------------------------------------------------------------------- *
     * Print the board              					                           *
     * you can use it without understanding how it works.                          *
     * --------------------------------------------------------------------------- */
    public static void printMatrix(int[][] matrix) {
        for (int i = matrix.length - 1; i >= 0; i = i - 1) {
            for (int j = 0; j < matrix.length; j = j + 1) {
                System.out.format("%4d", matrix[i][j]);
            }
            System.out.println();
        }
    }

}
