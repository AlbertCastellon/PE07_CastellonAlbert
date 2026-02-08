package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

/**
 * Unit test for simple App.
 */
public class AppTest {
    App game;

    @BeforeEach
    void setUp() {
        game = new PE07();
        game.initializeChessboard();
    }

    // -------------------------
    // Inicialización del tablero
    // -------------------------

    @Test
    void testInitialKingsAreAlive() {
        assertTrue(game.kingAlive(0)); // Rey blanco
        assertTrue(game.kingAlive(1)); // Rey negro
    }

    @Test
    void testInitialPawnPositions() {
        for (int i = 0; i < 8; i++) {
            assertEquals('P', game.chessboard[6][i]);
            assertEquals('p', game.chessboard[1][i]);
        }
    }

    // -------------------------
    // yourPiece
    // -------------------------

    @Test
    void testYourPieceWhite() {
        assertTrue(game.yourPiece(game.chessboard, 0, 7, 0)); // Torre blanca
        assertFalse(game.yourPiece(game.chessboard, 0, 0, 0)); // Torre negra
    }

    @Test
    void testYourPieceBlack() {
        assertTrue(game.yourPiece(game.chessboard, 0, 0, 1)); // Torre negra
        assertFalse(game.yourPiece(game.chessboard, 0, 7, 1)); // Torre blanca
    }

    // -------------------------
    // Movimiento de peón
    // -------------------------

    @Test
    void testWhitePawnMoveOneForward() {
        boolean moved = game.movePawn(0, 6, 0, 5, 0);
        assertTrue(moved);
        assertEquals('P', game.chessboard[5][0]);
        assertEquals(game.EMPTY, game.chessboard[6][0]);
    }

    @Test
    void testWhitePawnInvalidMoveBackward() {
        boolean moved = game.movePawn(0, 6, 0, 7, 0);
        assertFalse(moved);
    }

    @Test
    void testBlackPawnMoveTwoForward() {
        boolean moved = game.movePawn(1, 1, 0, 3, 0);
        assertTrue(moved);
        assertEquals('p', game.chessboard[3][0]);
        assertEquals(game.EMPTY, game.chessboard[1][0]);
    }

    // -------------------------
    // Captura de peón
    // -------------------------

    @Test
    void testPawnCapture() {
        game.chessboard[5][1] = 'p'; // Peón negro
        boolean moved = game.capturePawn(0, 6, 0, 5, 1);

        assertTrue(moved);
        assertEquals('P', game.chessboard[5][1]);
        assertEquals(game.EMPTY, game.chessboard[6][0]);
    }

    // -------------------------
    // Movimiento de torre
    // -------------------------

    @Test
    void testRookBlockedByPiece() {
        boolean moved = game.moveRook(0, 7, 0, 5, 0);
        assertFalse(moved); // Peón bloqueando
    }

    @Test
    void testRookValidMove() {
        game.chessboard[6][0] = game.EMPTY; // Quitamos peón
        boolean moved = game.moveRook(0, 7, 0, 5, 0);

        assertTrue(moved);
        assertEquals('T', game.chessboard[5][0]);
    }

    // -------------------------
    // Movimiento de caballo
    // -------------------------

    @Test
    void testKnightValidMove() {
        boolean moved = game.moveKnight(0, 7, 1, 5, 2);
        assertTrue(moved);
        assertEquals('C', game.chessboard[5][2]);
    }

    @Test
    void testKnightInvalidMove() {
        boolean moved = game.moveKnight(0, 7, 1, 6, 1);
        assertFalse(moved);
    }

    // -------------------------
    // Rey
    // -------------------------

    @Test
    void testKingMoveOneSquare() {
        game.chessboard[6][4] = game.EMPTY;
        boolean moved = game.moveKing(0, 7, 4, 6, 4);

        assertTrue(moved);
        assertEquals('K', game.chessboard[6][4]);
    }

    @Test
    void testKingInvalidMoveTooFar() {
        boolean moved = game.moveKing(0, 7, 4, 5, 4);
        assertFalse(moved);
    }

    // -------------------------
    // Eliminación del rey
    // -------------------------

    @Test
    void testKingCapturedEndsGame() {
        game.chessboard[0][4] = game.EMPTY; // Rey negro capturado
        assertFalse(game.kingAlive(1));
    }
}
