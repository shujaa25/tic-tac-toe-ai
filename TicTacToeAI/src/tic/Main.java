package tic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    static class Move{
        int row, col;
    }

    static char player = 'x', opponent = 'o';

    static boolean areMovesLeft(char board[][]){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] == '_') {
                    return true;
                }
            }
        }
        return false;
    }

    static int evaluate(char board[][]){
        for(int row = 0; row < 3; row++){
            if(board[row][0] == board[row][1] && board[row][1] == board[row][2]){
                if(board[row][0] == player){
                    return 10;
                }else if(board[row][0] == opponent){
                    return -10;
                }
            }
        }

        for(int col = 0; col < 3; col++){
            if(board[0][col] == board[1][col] && board[1][col] == board[2][col]){
                if(board[0][col] == player){
                    return 10;
                }else if(board[0][col] == opponent){
                    return -10;
                }
            }
        }

        if(board[0][0] == board[1][1] && board[1][1] == board[2][2]){
            if(board[0][0] == player){
                return 10;
            }else if(board[0][0] == opponent){
                return -10;
            }
        }
        if(board[0][2] == board[1][1] && board[1][1] == board[2][0]){
            if(board[0][2] == player){
                return 10;
            }else if(board[0][2] == opponent){
                return -10;
            }
        }

        return 0;
    }

    static int minimax(char board[][], int depth, boolean isMax){
        int score = evaluate(board);
        if(score == 10){
            return score;
        }
        if(score == -10){
            return score;
        }
        if(areMovesLeft(board) == false){
            return 0;
        }

        if(isMax){
            int best = -1000;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if(board[i][j] == '_'){
                        board[i][j] = player;

                        best = Math.max(best, minimax(board, depth+1, !isMax));

                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }else{
            int best = 1000;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if(board[i][j] == '_'){
                        board[i][j] = opponent;

                        best = Math.min(best, minimax(board, depth+1, !isMax));

                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
    }

    static Move findBestMove(char board[][]){
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] == '_'){
                    board[i][j] = player;

                    int moveVal = minimax(board, 0, false);

                    board[i][j] = '_';

                    if(moveVal > bestVal){
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    public static void main(String[] args) {
        char board[][] = {{'_', '_', '_'},
                {'_', '_', '_'},
                {'_', '_', '_'}};

        JFrame jFrame = new JFrame();
        JButton b[][] = new JButton[3][3];

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                b[i][j] = new JButton();
                jFrame.add(b[i][j]);
                b[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for(int i = 0; i < 3; i++){
                            for(int j = 0; j < 3; j++){
                                if(e.getSource() == b[i][j]){
                                    b[i][j].setText("o");
                                    b[i][j].setEnabled(false);
                                    board[i][j] = 'o';
                                    if(areMovesLeft(board)){
                                        Move move = findBestMove(board);
                                        board[move.row][move.col] = 'x';
                                        b[move.row][move.col].setText("x");
                                        b[move.row][move.col].setEnabled(false);
                                    }else if(evaluate(board) == 0){
                                        JOptionPane.showMessageDialog(jFrame, "MATCH TIE");
                                        System.exit(0);
                                    }
                                    if(evaluate(board) > 0){
                                        JOptionPane.showMessageDialog(jFrame, "YOU LOST");
                                        System.exit(0);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                });
            }
        }

        jFrame.setSize(300,300);
        jFrame.setLayout(new GridLayout(3,3));
        jFrame.setVisible(true);
    }
}
