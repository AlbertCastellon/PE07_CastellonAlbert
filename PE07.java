import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class PE07 {

    int currentTurn = 0;
    Scanner sc = new Scanner(System.in);
    char[] xCoordinates = new char[8];
    char[] yCoordinates = new char[8];
    ArrayList<String> plays = new ArrayList<String>();
    char[][] chessboard = new char[8][8];
    String[] players = new String[2];

    public static void main(String[] args) {
        PE07 p = new PE07();
        p.principal();
    }

    public void principal() {

        for (int i = 0; i < xCoordinates.length; i++) {
            xCoordinates[i] = (char) ('a' + i);
            yCoordinates[i] = (char) ('8' - i);
        }

        System.out.println("Introdueix el nom del primer jugador:");
        players[0] = getPlayerName();
        System.out.println("Introdueix el nom del segon jugador:");
        players[1] = getPlayerName();

        initializeChessboard();
        printBoard();
        play(players);

    }

    public void initializeChessboard() {
        chessboard[0][0] = 't';
        chessboard[0][7] = 't';
        chessboard[0][1] = 'c';
        chessboard[0][6] = 'c';
        chessboard[0][2] = 'a';
        chessboard[0][5] = 'a';
        chessboard[0][3] = 'q';
        chessboard[0][4] = 'k';

        chessboard[7][0] = 'T';
        chessboard[7][7] = 'T';
        chessboard[7][1] = 'C';
        chessboard[7][6] = 'C';
        chessboard[7][2] = 'A';
        chessboard[7][5] = 'A';
        chessboard[7][3] = 'Q';
        chessboard[7][4] = 'K';

        for (int i = 0; i < chessboard.length; i++) {
            chessboard[1][i] = 'p';
            chessboard[6][i] = 'P';
        }
        for(int i = 0; i < chessboard.length; i++){
            for(int j = 0; j < chessboard.length; j++){
                if(chessboard[i][j] == '\0'){
                   chessboard[i][j] = '·'; 
                }
            }
        }
    }

    public void printBoard() {
        for(int line = 0; line < chessboard.length; line++){
            System.out.print(8 - line);
            System.out.print("||");
            for(int col = 0; col < chessboard.length; col++){
                System.out.print(" ");
                System.out.print(chessboard[line][col]);
            }
            System.out.println("");
        }
        System.out.println("--------------------");
        System.out.print(" ||");
        for(int col = 0; col < xCoordinates.length; col++){
            System.out.print(" ");
            System.out.print(xCoordinates[col]);
        }
        System.out.println("");
}

    public String getPlayerName() {
        String name = "";
        try {
            name = sc.nextLine();
        } catch (Exception e) {
            System.out.println("Error");
        }

        return name;
    }

    public void play(String[] players) {
        boolean done = false;
        String keepPlaying = "";
        do {
            int winner = game();

            System.out.println("Vols seguir jugant?");
            do {
                keepPlaying = sc.nextLine();
                if (keepPlaying.equals("no")) {
                    done = true;
                } else if (keepPlaying.equals("si")) {
                    if (players[0] != players[winner]) {
                        String loser = players[0];
                        players[0] = players[winner];
                        players[1] = loser;
                    }
                    ;
                } else {
                    System.out.println("Resposta invàlida");
                    keepPlaying = "";
                }
            } while (keepPlaying.equals(""));

        } while (!done);
    }

    public int game() {
        boolean finished = false;
        int winner = 0;
        do {
            turn();

        } while (!finished);

        return winner;
    }

    public void turn() {
        int currentPlayer = currentTurn % 2;

        System.out.println("És el torn de: " + players[currentPlayer]);
        getNextPlay(currentPlayer);
        currentTurn++;
    }

    public String getNextPlay(int curPlayer) {
        boolean moved = false;
        String nextPlay = "";
        do {
            try {
                System.out.println("Introdueix la jugada (coordenades d'origen-espai-coordenades de destí ex: e4 f3)");
                nextPlay = sc.nextLine();
            } catch (Exception e) {
                System.out.println("Error");
            }
            boolean rightFormat = (nextPlay.length() == 5) && (nextPlay.charAt(2) == ' ');
            int xOrigin = getIndex(xCoordinates, nextPlay.charAt(0));
            int yOrigin = getIndex(yCoordinates, nextPlay.charAt(1));
            int xDesti = getIndex(xCoordinates, nextPlay.charAt(3));
            int yDesti = getIndex(yCoordinates, nextPlay.charAt(4));
            
            if (!rightFormat) {
                System.out.println("Format incorrecte");
                nextPlay = "";
            } else if (outBounds(xOrigin, yOrigin, xDesti, yDesti)) {
                System.out.println("Coordenades invàlides");
                nextPlay = "";
            } else if(chessboard[yOrigin][xOrigin] == '·') {
                System.out.println("Has de moure una peça");
                nextPlay = "";
            }else if(!yourPiece(chessboard, xOrigin, yOrigin, curPlayer)){
                System.out.println("Només pots moure les teves peces");
                nextPlay = "";
            }else {
                if(Character.toLowerCase(chessboard[yOrigin][xOrigin]) == 'p'){
                    if(xOrigin == yOrigin) {
                        moved = movePawn(curPlayer, yOrigin, xOrigin, yDesti, xDesti);
                    }else {
                        moved = capturePawn(curPlayer, yOrigin, xOrigin, yDesti, xDesti);
                    }  
                }

            }
        } while (moved != true);
        printBoard();
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
    
    public int getIndex(char[] coordinates, char target){
        return new String(coordinates).indexOf(target);
    }
    
    public boolean outBounds(int xO, int yO, int xD, int yD){
        return ((xO == -1 || yO == -1) || (xD == -1 || yD == -1));
    }

    public boolean yourPiece(char[][] chb, int x, int y, int curPlayer){
        boolean yourPiece = true;
        if(curPlayer == 0){
            if(chb[y][x] == Character.toUpperCase(chb[y][x]) && chb[y][x] != '.'){
               yourPiece = true;
            }else {
                yourPiece = false;
            }
        }else {
            if(chb[y][x] == Character.toLowerCase(chb[y][x]) && chb[y][x] != '.'){
               yourPiece = true;
            }else {
                yourPiece = false;
            }
        }
        
        return yourPiece;
    } 

    public boolean movePawn(int player, int yO, int xO, int yD, int xD){
        boolean moved = true;
        if(player == 0){
            if(yO - yD == 1 && chessboard[yD][xD] == '·'){
                System.out.println("El peó avança 1 casella.");
                chessboard[yD][xD] = chessboard[yO][xO];
                chessboard[yO][xO] = '·';
            }else if((yO == 6 && yO - yD == 2) && (chessboard[yD][xD] == '·') && (chessboard[(yO-1)][xD])== '·'){
                System.out.println("El peó avança 2 caselles.");
                chessboard[yD][xD] = chessboard[yO][xO];
                chessboard[yO][xO] = '·';
            }else {
                System.out.println("El peó no pot moure's a aquesta casella.");
                moved = false;
            }
        }else {
            if(yO - yD == -1 && chessboard[yD][xD] == '·'){
                System.out.println("El peó avança 1 casella.");
                chessboard[yD][xD] = chessboard[yO][xO];
                chessboard[yO][xO] = '·';
            }else if((yO == 1 && yO - yD == -2) && (chessboard[yD][xD] == '·') && (chessboard[(yO+1)][xD])== '·'){
                System.out.println("El peó avança 2 caselles.");
                chessboard[yD][xD] = chessboard[yO][xO];
                chessboard[yO][xO] = '·';
            }else {
                System.out.println("El peó no pot moure's a aquesta casella.");
                moved = false;
            }
        }
        return moved;
    }
    
    public boolean capturePawn(int player, int yO, int xO, int yD, int xD){
        boolean moved = true;
        if(player == 0){
            if(yO - yD == 1 && (Math.abs(xO - xD)  == 1) && !yourPiece(chessboard, xD, yD, player) && chessboard[yD][xD] != '.'){
                System.out.println("El captura la peça.");
                chessboard[yD][xD] = chessboard[yO][xO];
                chessboard[yO][xO] = '·';
            }else {
                System.out.println("El peó no pot moure's a aquesta casella.");
                moved = false;
            }
        }else {
            if(yO - yD == -1 && (Math.abs(xO - xD)  == 1) && !yourPiece(chessboard, xD, yD, player) && chessboard[yD][xD] != '.'){
                System.out.println("El captura la peça.");
                chessboard[yD][xD] = chessboard[yO][xO];
                chessboard[yO][xO] = '·';
            }else {
                System.out.println("El peó no pot moure's a aquesta casella.");
                moved = false;
            }
        }
        return moved;
    }

    public boolean moveBishop(int player, int yO, int xO, int yD, int xD){
        boolean moved = false;


        return moved;
    }

    public boolean moveRook(int player, int yO, int xO, int yD, int xD){
        boolean moved = false;

        
        return moved;
    }
    public boolean moveKing(int player, int yO, int xO, int yD, int xD){
        boolean moved = false;
        if(Math.abs(yO-yD) > 1 || Math.abs(xO-xD) > 1 || yourPiece(chessboard, xD, yD, player)){
            System.out.println("El rei no pot moure's a aquesta casella");
        }else {
            moved = true;
            chessboard[yD][xD] = chessboard[yO][xO];
            chessboard[yO][xO] = '·';
            System.out.println("El rei s'ha mogut correctament");
        }
        
        return moved;
    }

}
