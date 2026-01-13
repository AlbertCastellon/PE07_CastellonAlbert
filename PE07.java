import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class PE07 {

    int currentTurn = 0;
    Scanner sc = new Scanner(System.in);
    char[] xCoordinates = new char[8];
    char[] yCoordinates = new char[8];
    ArrayList<String> plays = new ArrayList<String>();
    
    public static void main(String[] args) {
        PE07 p = new PE07();
        p.principal();
    }
    public void principal(){


        char[][] chessboard = new char[8][8];
        String[] players = new String[2];
        for(int i = 0; i < xCoordinates.length; i++){
            xCoordinates[i] = (char) ('a' + i);
            yCoordinates[i] = (char) ('1' + i);
            
        }
        
        System.out.println("Introdueix el nom del primer jugador:");
        players[0] = getPlayerName();
        System.out.println("Introdueix el nom del segon jugador:");
        players[1] = getPlayerName(); 

        initializeChessboard(chessboard);
        play(players);
        

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

    public void play(String[] players) {
        boolean done = false;
        String keepPlaying = "";
        do{
            int winner = game();

            System.out.println("Vols seguir jugant?");
            do{
                keepPlaying = sc.nextLine();
                if(keepPlaying.equals("no")){
                    done = true;
                }else if (keepPlaying.equals("si")){
                    if(players[0] != players[winner]){
                        String loser = players[0]; 
                        players[0] = players[winner];
                        players[1] = loser;
                    }
                    ;
                }else {
                    System.out.println("Resposta invàlida");
                    keepPlaying = "";
                }
            }while(keepPlaying.equals(""));
            
        }while(!done);
    }

    public int game() {
        boolean finished = false;
        int winner = 0;
        do{
            turn();

        }while(!finished);

        return winner;
    }
    public void turn() {
        int currentPlayer = currentTurn % 2;
        
    }

    public String getNextPlay() {
        String nextPlay = "";
        do{
            try {
                System.out.println("Introdueix la jugada (coordenades d'origen-espai-coordenades de destí ex: e4 f3)");
                nextPlay = sc.nextLine();
            }catch(Exception e){
                System.out.println("Error");
            }
            boolean rightFormat = (nextPlay.length() == 5) && (nextPlay.charAt(2) == ' ');
            boolean inBoundsOrigin = (contains(xCoordinates, nextPlay.charAt(0))) && (contains(yCoordinates, nextPlay.charAt(1)));
            boolean inBoundsDestination =(contains(xCoordinates, nextPlay.charAt(3))) && (contains(yCoordinates, nextPlay.charAt(4)));
            if(!rightFormat){
                System.out.println("Format incorrecte");
                nextPlay = "";
            }else if (!inBoundsDestination && !inBoundsOrigin){
                System.out.println("Coordenades invàlides");
            }else {
                plays.add(nextPlay);
            }
        }while(!nextPlay.equals(""));
        return nextPlay;
    }

    public boolean contains(char[] array, char target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return true;
            }
        }
        return false;

    }
}
