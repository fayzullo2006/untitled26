package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here



        class TicTacToeGame {

            private char[][] board = new char[3][3];

            private static final String TERMINATE = "!";
            private static final char X = 'X';
            private static final char O = 'O';
            private static final int HORIZONTAL = 0;
            private static final int VERTICAL = 1;
            private static final int CROSS = 2;
            private static final int REVERSE_CROSS = 3;

            private boolean GAME_IN_PROGRESS = false;
            private boolean PLAYER_ONE_WON = false;
            private boolean PLAYER_TWO_WON = false;
            private boolean GAME_DRAWN = false;

            public TicTacToeGame() {
                System.out.println("@@@@@@@@@@@@@@@@@@@ Tic Tac Toe @@@@@@@@@@@@@@@@@@@@@@@@");
                for (int i = 0; i < board.length; i++) {
                    Arrays.fill(board[i], '_');
                }
                System.out.println("\n**************** BOARD INITIALIZED *********************");
            }

            private void play() {
                Scanner in = new Scanner(System.in);
                boolean isPlayerOne = true;
                startGame();

                while (GAME_IN_PROGRESS && !(PLAYER_ONE_WON || PLAYER_TWO_WON || GAME_DRAWN)) {
                    int[] input = getPlayerInput(isPlayerOne, in);
                    if (input[0] == -1 || input[1] == -1) {
                        continue;
                    }

                    char c = isPlayerOne ? X : O;
                    if (!placeXO(input[0], input[1], c)) {
                        continue;
                    }

                    printBoard();
                    if (checkWinCondition(input[0], input[1], c)) {
                        if (isPlayerOne) {
                            PLAYER_ONE_WON = true;
                        } else {
                            PLAYER_TWO_WON = true;
                        }
                    } else if (checkForDraw()) {
                        GAME_DRAWN = true;
                    }

                    isPlayerOne = !isPlayerOne;
                }

                showGameResults();
                endGame();
            }


            private boolean checkWinCondition(int row, int col, char check) {
                return (dfs(row, col, HORIZONTAL, check) == 3) ||
                        (dfs(row, col, VERTICAL, check) == 3) ||
                        (dfs(row, col, CROSS, check) == 3) ||
                        (dfs(row, col, REVERSE_CROSS, check) == 3);
            }

            private boolean checkForDraw() {
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[i].length; j++) {
                        if (board[i][j] == '_') {
                            return false;
                        }
                    }
                }
                return true;
            }

            private int dfs(int row, int col, int dir, char check) {
                if (row < 0 || row >= board.length || col < 0 || col >= board[row].length || board[row][col] != check) {
                    return 0;
                }
                char temp = board[row][col];
                board[row][col] = 'V';
                int res = 0;

                if (dir == HORIZONTAL) {
                    res = 1 + dfs(row + 1, col, dir, check) + dfs(row - 1, col, dir, check);
                } else if (dir == VERTICAL) {
                    res = 1 + dfs(row, col + 1, dir, check) + dfs(row, col - 1, dir, check);
                } else if (dir == CROSS) {
                    res = 1 + dfs(row + 1, col + 1, dir, check) + dfs(row - 1, col - 1, dir, check);
                } else if (dir == REVERSE_CROSS) {
                    res = 1 + dfs(row + 1, col - 1, dir, check) + dfs(row - 1, col + 1, dir, check);
                }

                board[row][col] = temp;
                return res;
            }

            private boolean placeXO(int row, int col, char c) {
                if (row >= 0 && row < board.length && col >= 0 && col < board[row].length && board[row][col] == '_') {
                    board[row][col] = c;
                    return true;
                }
                System.out.println("----------------Invalid board position-----------------");
                return false;
            }

            private void printBoard() {
                System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%% BOARD %%%%%%%%%%%%%%%%%%%%%%%%%%%\n");
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[i].length; j++) {
                        System.out.print(String.valueOf(board[i][j]) + "\t");
                    }
                    System.out.println("\n");
                }
            }

            private void startGame() {
                GAME_IN_PROGRESS = true;
                System.out.println("\n<<<<<<<<<<< \"GAME STARTED. PRESS ! TO END.\" >>>>>>>>>>>>");
                System.out.println("\n========================================================");
            }

            private void showGameResults() {
                String gameResult = ((PLAYER_ONE_WON) ? "$$$$$$$$$$$$$$$$$ PLAYER ONE WINS! $$$$$$$$$$$$$$$$$$" :
                        ((PLAYER_TWO_WON) ? "$$$$$$$$$$$$$$$$$ PLAYER TWO WINS! $$$$$$$$$$$$$$$$$$" :
                                "$$$$$$$$$$$$$$$$$ GAME ENDED IN A DRAW! $$$$$$$$$$$$$$$$$$"));

                System.out.println("\n" + gameResult);
            }

            private void endGame() {
                GAME_IN_PROGRESS = false;
                System.out.println("\n========================================================");
                System.out.println("\n<<<<<<<<<<<<<<<<<<< ENDING GAME >>>>>>>>>>>>>>>>>>>>>>>>");
                System.out.println("\n@@@@@@@@@@@@@@@@@@@@@ GOODBYE! @@@@@@@@@@@@@@@@@@@@@@@@@");
            }

            private int[] getPlayerInput(boolean isPlayerOne, Scanner in) {
                System.out.println("\n[Player " + (isPlayerOne ? "X" : "O")
                        + "] ====> Enter your move [0-2][0-2]:");

                String input = in.nextLine();
                if (input.contains(TERMINATE)) {
                    GAME_IN_PROGRESS = false;
                    System.out.println("------------ \"!\" Pressed. Terminating Game. ------------");
                } else {
                    String[] values = input.split(" ");
                    if (values.length == 2) {
                        try {
                            int row = Integer.parseInt(values[0]);
                            int col = Integer.parseInt(values[1]);
                            if (isValid(row) && isValid(col)) {
                                return new int[]{row, col};
                            }
                        } catch (NumberFormatException e) {

                        }
                    }
                    System.out.println("---------------------Invalid input----------------------");
                    System.out.println("----------Enter input in the format [0-2] [0-2]---------");
                }
                return new int[]{-1, -1};
            }

            private boolean isValid(int pos) {
                return pos >= 0 && pos < 3;
            }

            public static void main(String[] args) {
                TicTacToeGame ticTacToeGame = new TicTacToeGame();
                ticTacToeGame.play();
            }
        }
    }
}
