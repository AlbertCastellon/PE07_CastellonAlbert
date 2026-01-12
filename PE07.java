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
        boolean done = false;
        System.out.println("Introdueix el nom del primer jugador:");
        players[0] = getPlayerName();
        System.out.println("Introdueix el nom del segon jugador:");
        players[1] = getPlayerName(); 

        initializeChessboard(chessboard);
        

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

    public String getPlayerName() {
        String name = "";
        try {
            name = sc.nextLine();
        }catch(Exception e){
            System.out.println("Error");
        }
        

        return name;
    }

    public void set(boolean done) {
        boolean finished = false;
        String keepPlaying = "";
        do{
            int winner = play(finished);

            System.out.println("Vols seguir jugant?");
            do{
                keepPlaying = sc.nextLine();
                if(keepPlaying.equals("no")){
                    done = true;
                }else if (keepPlaying.equals("si")){

                }else {
                    System.out.println("Resposta invàlida");
                }
            }while(keepPlaying.equals(""))
            
        }while(!done);
    }

    public int play(boolean finished) {
        int winner = 0;
        do{

        }while(!finished);

        return winner;
    }
}
