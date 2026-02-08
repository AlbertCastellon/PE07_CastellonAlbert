# ♟️ PE07 – Juego de Ajedrez en Java

Proyecto de ajedrez por consola desarrollado en **Java**, donde dos jugadores pueden disputar partidas completas siguiendo las reglas básicas del ajedrez: movimientos válidos, capturas, promoción de peones y finalización de la partida al capturar el rey.

---

## 📌 Características principales

- Juego **por turnos** para dos jugadores.
- Representación del tablero de ajedrez **8x8** mediante arrays.
- Movimientos implementados para:
  - Peón (avance, captura y promoción)
  - Torre
  - Alfil
  - Caballo
  - Reina
  - Rey
- **Promoción de peones** al alcanzar la última fila.
- Control de piezas propias y enemigas.
- Registro de piezas capturadas.
- Interfaz por consola clara y legible.
- Posibilidad de jugar **varias partidas seguidas**.

---

## 🧩 Representación del tablero

- Las piezas **blancas** se representan con letras **mayúsculas**.
- Las piezas **negras** se representan con letras **minúsculas**.
- Casillas vacías: `·`

| Pieza   | Blanco | Negro |
|--------|--------|-------|
| Rey    | K      | k     |
| Reina  | Q      | q     |
| Torre  | T      | t     |
| Alfil  | A      | a     |
| Caballo| C      | c     |
| Peón   | P      | p     |

Las filas van de **8 a 1** (índice 0 a 7) y las columnas de **a a h**.

---

## ▶️ Cómo ejecutar el programa

1. Asegúrate de tener **Java JDK** instalado.
2. Compila el archivo:
   ```bash
   javac PE07.java