import java.util.Scanner;
import java.util.ArrayList;

public class PE07 {

    int currentTurn = 0;
    Scanner sc = new Scanner(System.in);
    char[] xCoordinates = new char[8];
    char[] yCoordinates = new char[8];
    ArrayList<String> plays = new ArrayList<String>();
    char[][] chessboard = new char[8][8];
    String[] players = new String[2];
    final char EMPTY = '·';

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
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard.length; j++) {
                if (chessboard[i][j] == '\0') {
                    chessboard[i][j] = EMPTY;
                }
            }
        }
    }

    public void printBoard() {
        for (int line = 0; line < chessboard.length; line++) {
            System.out.print(8 - line);
            System.out.print("||");
            for (int col = 0; col < chessboard.length; col++) {
                System.out.print(" ");
                System.out.print(chessboard[line][col]);
            }
            System.out.println("");
        }
        System.out.println("--------------------");
        System.out.print(" ||");
        for (int col = 0; col < xCoordinates.length; col++) {
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
                    if (!players[0].equals(players[winner])) {
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
            finished = !turn();

        } while (!finished);
        winner = (currentTurn - 1) % 2; // l'últim que ha mogut
        System.out.println("Ha guanyat: " + players[winner]);
        return winner;
    }

    public boolean turn() {
        int currentPlayer = currentTurn % 2;
        int enemyPlayer = (currentPlayer + 1) % 2;

        System.out.println("És el torn de: " + players[currentPlayer]);
        plays.add(getNextPlay(currentPlayer));
        currentTurn++;
        return kingAlive(enemyPlayer);

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
            } else if (chessboard[yOrigin][xOrigin] == EMPTY) {
                System.out.println("Has de moure una peça");
                nextPlay = "";
            } else if (!yourPiece(chessboard, xOrigin, yOrigin, curPlayer)) {
                System.out.println("Només pots moure les teves peces");
                nextPlay = "";
            } else {
                moved = movements(curPlayer, yOrigin, xOrigin, yDesti, xDesti);
            }
        } while (moved != true);
        printBoard();
        return nextPlay;
    }

    public int getIndex(char[] coordinates, char target) {
        return new String(coordinates).indexOf(target);
    }

    public boolean outBounds(int xO, int yO, int xD, int yD) {
        return ((xO == -1 || yO == -1) || (xD == -1 || yD == -1));
    }

    public boolean yourPiece(char[][] chb, int x, int y, int curPlayer) {
        boolean yourPiece = true;
        if (curPlayer == 0) {
            if (chb[y][x] == Character.toUpperCase(chb[y][x]) && chb[y][x] != EMPTY) {
                yourPiece = true;
            } else {
                yourPiece = false;
            }
        } else {
            if (chb[y][x] == Character.toLowerCase(chb[y][x]) && chb[y][x] != EMPTY) {
                yourPiece = true;
            } else {
                yourPiece = false;
            }
        }

        return yourPiece;
    }

    public boolean movePawn(int player, int yO, int xO, int yD, int xD) {
        boolean moved = true;
        if (player == 0) {
            if (yO - yD == 1 && chessboard[yD][xD] == EMPTY) {
                System.out.println("El peó avança 1 casella.");
                chessboard[yD][xD] = chessboard[yO][xO];
                chessboard[yO][xO] = EMPTY;
            } else if ((yO == 6 && yO - yD == 2) && (chessboard[yD][xD] == EMPTY)
                    && (chessboard[(yO - 1)][xD]) == EMPTY) {
                System.out.println("El peó avança 2 caselles.");
                chessboard[yD][xD] = chessboard[yO][xO];
                chessboard[yO][xO] = EMPTY;
            } else {
                System.out.println("El peó no pot moure's a aquesta casella.");
                moved = false;
            }
        } else {
            if (yO - yD == -1 && chessboard[yD][xD] == EMPTY) {
                System.out.println("El peó avança 1 casella.");
                chessboard[yD][xD] = chessboard[yO][xO];
                chessboard[yO][xO] = EMPTY;
            } else if ((yO == 1 && yO - yD == -2) && (chessboard[yD][xD] == EMPTY)
                    && (chessboard[(yO + 1)][xD]) == EMPTY) {
                System.out.println("El peó avança 2 caselles.");
                chessboard[yD][xD] = chessboard[yO][xO];
                chessboard[yO][xO] = EMPTY;
            } else {
                System.out.println("El peó no pot moure's a aquesta casella.");
                moved = false;
            }
        }
        return moved;
    }

    public boolean capturePawn(int player, int yO, int xO, int yD, int xD) {
        boolean moved = true;
        if (player == 0) {
            if (yO - yD == 1 && (Math.abs(xO - xD) == 1) && !yourPiece(chessboard, xD, yD, player)
                    && chessboard[yD][xD] != EMPTY) {
                System.out.println("El peó captura la peça.");
                chessboard[yD][xD] = chessboard[yO][xO];
                chessboard[yO][xO] = EMPTY;
            } else {
                System.out.println("El peó no pot moure's a aquesta casella.");
                moved = false;
            }
        } else {
            if (yO - yD == -1 && (Math.abs(xO - xD) == 1) && !yourPiece(chessboard, xD, yD, player)
                    && chessboard[yD][xD] != EMPTY) {
                System.out.println("El peó captura la peça.");
                chessboard[yD][xD] = chessboard[yO][xO];
                chessboard[yO][xO] = EMPTY;
            } else {
                System.out.println("El peó no pot moure's a aquesta casella.");
                moved = false;
            }
        }
        return moved;
    }

    public boolean moveBishop(int player, int yO, int xO, int yD, int xD) {
        boolean moved = false;
        if (Math.abs(yO - yD) != Math.abs(xO - xD)) {
            System.out.println("La peça no es pot moure en aquesta direcció");
        } else if (yourPiece(chessboard, xD, yD, player)) {
            System.out.println("No pots capturar les teves peces");
        } else {
            int i = 1;
            if (xO - xD < 0 && yO - yD < 0) {
                while (i < Math.abs(xO - xD)) {
                    if (chessboard[yO + i][xO + i] != EMPTY) {
                        moved = false;
                        System.out.println("Hi ha una peça al camí");
                        return moved;
                    }
                    i++;
                }
            } else if (xO - xD > 0 && yO - yD < 0) {
                while (i < Math.abs(xO - xD)) {
                    if (chessboard[yO + i][xO - i] != EMPTY) {
                        moved = false;
                        System.out.println("Hi ha una peça al camí");
                        return moved;
                    }
                    i++;
                }
            } else if (xO - xD > 0 && yO - yD > 0) {
                while (i < Math.abs(xO - xD)) {
                    if (chessboard[yO - i][xO - i] != EMPTY) {
                        moved = false;
                        System.out.println("Hi ha una peça al camí");
                        return moved;
                    }
                    i++;
                }
            } else if (xO - xD < 0 && yO - yD > 0) {
                while (i < Math.abs(xO - xD)) {
                    if (chessboard[yO - i][xO + i] != EMPTY) {
                        moved = false;
                        System.out.println("Hi ha una peça al camí");
                        return moved;
                    }
                    i++;
                }
            }
            moved = true;
            chessboard[yD][xD] = chessboard[yO][xO];
            chessboard[yO][xO] = EMPTY;
            System.out.println("La peça s'ha mogut correctament");
        }

        return moved;
    }

    public boolean moveRook(int player, int yO, int xO, int yD, int xD) {
        boolean moved = false;
        if (yO == yD && xO == xD) {
            System.out.println("Has de moure alguna peça");
        } else if (yO - yD != 0 && xO - xD != 0) {
            System.out.println("La peça no es pot moure en aquesta direcció");
        } else if (yourPiece(chessboard, xD, yD, player)) {
            System.out.println("No pots capturar les teves peces");
        } else {
            int i = 1;
            if (xO == xD) {
                if (yO < yD) {
                    while (i < Math.abs(yO - yD)) {
                        if (chessboard[yO + i][xO] != EMPTY) {
                            moved = false;
                            System.out.println("Hi ha una peça al camí");
                            return moved;
                        }
                        i++;
                    }
                } else if (yO > yD) {
                    while (i < Math.abs(yO - yD)) {
                        if (chessboard[yO - i][xO] != EMPTY) {
                            moved = false;
                            System.out.println("Hi ha una peça al camí");
                            return moved;
                        }
                        i++;
                    }
                }

            } else {
                if (xO < xD) {
                    while (i < Math.abs(xO - xD)) {
                        if (chessboard[yO][xO + i] != EMPTY) {
                            moved = false;
                            System.out.println("Hi ha una peça al camí");
                            return moved;
                        }
                        i++;
                    }
                } else if (xO > xD) {
                    while (i < Math.abs(xO - xD)) {
                        if (chessboard[yO][xO - i] != EMPTY) {
                            moved = false;
                            System.out.println("Hi ha una peça al camí");
                            return moved;
                        }
                        i++;
                    }
                }

            }
            moved = true;
            chessboard[yD][xD] = chessboard[yO][xO];
            chessboard[yO][xO] = EMPTY;
            System.out.println("La peça s'ha mogut correctament");
        }

        return moved;
    }

    public boolean moveKing(int player, int yO, int xO, int yD, int xD) {
        boolean moved = false;
        int dy = Math.abs(yO - yD);
        int dx = Math.abs(xO - xD);

        if ((dy == 0 && dx == 0) || dy > 1 || dx > 1 || yourPiece(chessboard, xD, yD, player)) {
            System.out.println("El rei no pot moure's a aquesta casella");
        } else {
            moved = true;
            chessboard[yD][xD] = chessboard[yO][xO];
            chessboard[yO][xO] = EMPTY;
            System.out.println("El rei s'ha mogut correctament");
        }

        return moved;
    }

    public boolean moveQueen(int player, int yO, int xO, int yD, int xD) {

        if (xO == xD || yO == yD) {
            return moveRook(player, yO, xO, yD, xD);
        } else if (Math.abs(yO - yD) == Math.abs(xO - xD)) {
            return moveBishop(player, yO, xO, yD, xD);
        } else {
            System.out.println("La reina no es pot moure en aquesta direcció");
            return false;
        }
    }

    public boolean moveKnight(int player, int yO, int xO, int yD, int xD) {
        boolean moved = false;
        if (!((Math.abs(yO - yD) == 2 && Math.abs(xO - xD) == 1)
                || (Math.abs(yO - yD) == 1 && Math.abs(xO - xD) == 2))) {
            System.out.println("El caball no pot moure's a aquesta casella");
        } else if (yourPiece(chessboard, xD, yD, player)) {
            System.out.println("No pots capturar les teves peces");
        } else {
            moved = true;
            chessboard[yD][xD] = chessboard[yO][xO];
            chessboard[yO][xO] = EMPTY;
            System.out.println("El caball s'ha mogut correctament");
        }

        return moved;
    }

    public boolean kingAlive(int player) {
        char king = ' ';
        if (player == 0) {
            king = 'K';
        } else {
            king = 'k';
        }
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                if (chessboard[i][j] == king) {
                    return true;
                }
            }
        }
        return false;

    }

    public boolean movements(int player, int yO, int xO, int yD, int xD) {
        boolean moved = false;
        char piece = Character.toLowerCase(chessboard[yO][xO];
        if (Character.toLowerCase(chessboard[yO][xO]) == 'p') {
                    if (xO == xD) {
                        moved = movePawn(player, yO, xO, yD, xD);
                    } else {
                        moved = capturePawn(player, yO, xO, yD, xD);
                    }
                    if (moved) {
                        if (yD == 0 && chessboard[yD][xD] == 'P') {
                            chessboard[yD][xD] = Character.toUpperCase(promotion());
                        } else if (yD == 7 && chessboard[yD][xD] == 'p') {
                            chessboard[yD][xD] = Character.toLowerCase(promotion());
                        }
                    }
                } else if (piece == 'a') {
                    moved = moveBishop(player, yO, xO, yD, xD);
                } else if (piece == 'k') {
                    moved = moveKing(player, yO, xO, yD, xD);
                } else if (piece == 't') {
                    moved = moveRook(player, yO, xO, yD, xD);
                } else if (piece == 'q') {
                    moved = moveQueen(player, yO, xO, yD, xD);
                } else if (piece == 'c') {
                    moved = moveKnight(player, yO, xO, yD, xD);
                }
                return moved;
    }
    
    public char promotion() {
        char newPiece = ' ';
        System.out.println("Per quina peça vols reemplaçar el peó?");
        do {
            newPiece = sc.nextLine().charAt(0);
            if (Character.toLowerCase(newPiece) != 'q' && Character.toLowerCase(newPiece) != 'c'
                    && Character.toLowerCase(newPiece) != 'a' && Character.toLowerCase(newPiece) != 't') {
                System.out.println("Escull una peça vàlida");
                newPiece = ' ';
            }
        } while (newPiece == ' ');

        return newPiece;
    }
}
