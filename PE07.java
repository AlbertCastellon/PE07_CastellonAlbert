import java.util.Scanner;
import java.util.InputMismatchException;

public class PE07 {

    int currentTurn = 0;
    Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        PE07 p = new PE07();
        p.principal();
    }
    public void principal(){


        char[][] chessboard = new char[8][8];

        String[] players = new String[2];




    }

    public void initializeChessboard(char[][] chb){
        chb[0][0] = 't';
        chb[0][7] = 't';
        chb[0][1] = 'c';
        chb[0][6] = 'c';
        chb[0][2] = 'a';
        chb[0][5] = 'a';
        chb[0][3] = 'q';
        chb[0][4] = 'k';

        chb[7][0] = 'T';
        chb[7][7] = 'T';
        chb[7][1] = 'C';
        chb[7][6] = 'C';
        chb[7][2] = 'A';
        chb[7][5] = 'A';
        chb[7][3] = 'Q';
        chb[7][4] = 'K';

        for(int i = 0; i < chb.length; i++){
            chb[1][i] = 'p';
            chb[6][i] = 'P';
        }
    }

}
