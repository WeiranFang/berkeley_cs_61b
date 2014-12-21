package player;

import java.util.*;
import list.*;

public class GameBoard {
  private final static int DIMENSION = 8;
  private int[][] grid;  //Store the color of the chip on each grid. If there's no chip, store -1.
  private int numWhiteChips;
  private int numBlackChips;
  
  public GameBoard() {
    grid = new int[DIMENSION][DIMENSION];
    numWhiteChips = 0;
    numBlackChips = 0;
    for (int i = 0; i < DIMENSION; i++) {
      for (int j = 0; j < DIMENSION; j++) {
        grid[i][j] = -1;                     //Empty grid.
      }
    }
  }
  
  public boolean isValidMove(Move m, int color) {
    if (m.moveKind == m.QUIT) {        //QUIT move.
      return true; 
    } else if (m.moveKind == m.ADD) {  //ADD move.
      if ((numChips(color) >= 10)                                  //Chips more than 10.
            || isCorner(m.x1, m.y1)                                  //No chip may be placed in any of the four corners.
            || (color == 0 && (m.x1 == 0 || m.x1 == DIMENSION - 1))  //
            || (color == 1 && (m.y1 == 0 || m.y1 == DIMENSION - 1))  //No chip may be placed in a goal of the opposite color.
            || (grid[m.x1][m.y1] != -1)                              //No chip may be placed in a square that is already occupied.
            || isInGroup(m.x1, m.y1, color) ) {                     //A player may not have more than two chips in a connected group.
        return false;
      } else {
        return true;
      }
    } else {                           //STEP move
      if (numChips(color) != 10 || grid[m.x2][m.y2] != color) return false;
      grid[m.x2][m.y2] = -1;                                        //Assuming the chip is removed from the board.
      if ( (m.x1 == m.x2 && m.y1 == m.y2) 
            || isCorner(m.x1, m.y1)                                   //No chip may be placed in any of the four corners.
            || (color == 0 && (m.x1 == 0 || m.x1 == DIMENSION - 1))   //
            || (color == 1 && (m.y1 == 0 || m.y1 == DIMENSION - 1))   //No chip may be placed in a goal of the opposite color.
            || (grid[m.x1][m.y1] != -1)                               //No chip may be placed in a square that is already occupied.
            || isInGroup(m.x1, m.y1, color) ) {                        //A player may not have more than two chips in a connected group.
        grid[m.x2][m.y2] = color;                                   //move back the chip.
        return false;
      }
      grid[m.x2][m.y2] = color;
      return true;
    } 
  }
  
  public ArrayList<Move> validMoves(int color) {
    ArrayList<Move> moves = new ArrayList<Move>();
    if (numChips(color) < 10) {
      for (int i = 0; i < DIMENSION; i++) {
        for (int j = 0; j < DIMENSION; j++) {
          Move m = new Move(i, j);
          if (isValidMove(m, color)) {
            moves.add(m);
          }
        }
      }
    } else {
      for (int i1 = 0; i1 < DIMENSION; i1++) {
        for (int j1 = 0; j1 < DIMENSION; j1++) {
          for (int i2 = 0; i2 < DIMENSION; i2++) {
            for (int j2 = 0; j2 < DIMENSION; j2++) {
              Move m = new Move(i1, j1, i2, j2);
              if (isValidMove(m, color)) {
                moves.add(m);
              }
            }
          }
        }
      }
    }
  return moves;
  }
  
  public DList connectedChips(Chip chip) {
    DList connectionList = new DList();
    int x = chip.x;
    int y = chip.y;
    int color = chip.color;
    //Find left connection.
    if (x > 0) {
      int i = x - 1;
      while (i >= 0 && grid[i][y] != (1 - color)) {
        if (grid[i][y] == color) {
          Chip connection = new Chip(i, y, color);
          connectionList.insertFront(connection);
          break;
        } else {
          i--;
        }
      }
    }
    //Find right connection.
    if (x < DIMENSION - 1) {
      int i = x + 1;
      while (i <= DIMENSION - 1 && grid[i][y] != (1 - color)) {
        if (grid[i][y] == color) {
          Chip connection = new Chip(i, y, color);
          connectionList.insertFront(connection);
          break;
        } else {
          i++;
        }
      }
    }
    //Find up connection.
    if (y > 0) {
      int j = y - 1;
      while (j >= 0 && grid[x][j] != (1 - color)) {
        if (grid[x][j] == color) {
          Chip connection = new Chip(x, j, color);
          connectionList.insertFront(connection);
          break;
        } else {
          j--;
        }
      }
    }
    //Find down connection.
    if (y < DIMENSION - 1) {
      int j = y + 1;
      while (j <= DIMENSION - 1 && grid[x][j] != (1 - color)) {
        if (grid[x][j] == color) {
          Chip connection = new Chip(x, j, color);
          connectionList.insertFront(connection);
          break;
        } else {
          j++;
        }
      }
    }
    //Find up-left connection.
    if (x > 0 && y > 0) {
      int i = x - 1;
      int j = y - 1;
      while (i >= 0 && j >= 0 && grid[i][j] != (1 - color)) {
        if (grid[i][j] == color) {
          Chip connection = new Chip(i, j, color);
          connectionList.insertFront(connection);
          break;
        } else {
          i--;
          j--;
        }
      }
    }
    //Find up-right connection.
    if (x < DIMENSION - 1 && y > 0) {
      int i = x + 1;
      int j = y - 1;
      while (i <= DIMENSION - 1 && j >= 0 && grid[i][j] != (1 - color)) {
        if (grid[i][j] == color) {
          Chip connection = new Chip(i, j, color);
          connectionList.insertFront(connection);
          break;
        } else {
          i++;
          j--;
        }
      }
    }
    //Find down-left connection.
    if (x > 0 && y < DIMENSION - 1) {
      int i = x - 1;
      int j = y + 1;
      while (i >= 0 && j <= DIMENSION - 1 && grid[i][j] != (1 - color)) {
        if (grid[i][j] == color) {
          Chip connection = new Chip(i, j, color);
          connectionList.insertFront(connection);
          break;
        } else {
          i--;
          j++;
        }
      }
    }
    //Find down-right connection.
    if (x < DIMENSION - 1 && y < DIMENSION - 1) {
      int i = x + 1;
      int j = y + 1;
      while (i <= DIMENSION - 1 && j <= DIMENSION - 1 && grid[i][j] != (1 - color)) {
        if (grid[i][j] == color) {
          Chip connection = new Chip(i, j, color);
          connectionList.insertFront(connection);
          break;
        } else {
          i++;
          j++;
        }
      }
    }
    return connectionList;
  }
  
  public void makeMove(Move m, int color) {
    if (m.moveKind == m.QUIT) {
      clearBoard();
    } else if (m.moveKind == m.ADD) {
      grid[m.x1][m.y1] = color;
      if (color == 0) {
        numBlackChips++;
      } else {
        numWhiteChips++;
      }
    } else if (m.moveKind == m.STEP) {
      grid[m.x1][m.y1] = color;
      grid[m.x2][m.y2] = -1;
    }
  }
  
  public void clearBoard() {
    numWhiteChips = 0;
    numBlackChips = 0;
    for (int i = 0; i < DIMENSION; i++) {
      for (int j = 0; j < DIMENSION; j++) {
        grid[i][j] = -1;                     //Empty grid.
      }
    }
  }
  
  private int numChips(int color) {
    if (color == 0) {
      return numBlackChips;
    } else {
      return numWhiteChips;
    }
  }
  
  private boolean isCorner(int x, int y) {
    if ((x == 0 && y == 0) || (x == DIMENSION - 1 && y == 0) ||
        (x == 0 && y == DIMENSION - 1) || (x == DIMENSION - 1 && y == DIMENSION - 1)) {
      return true;
    }
    else return false;
  }
  
  private ArrayList<Chip> neighborChips(Chip c) {
    ArrayList<Chip> neighbors = new ArrayList<Chip>();
    int x = c.x;
    int y = c.y;
    int color = c.color;
    if (x > 0 && grid[x - 1][y] == color) {  //Left 
      Chip nbChip = new Chip(x - 1, y, color);
      neighbors.add(nbChip);
    }
    if (x < DIMENSION - 1 && grid[x + 1][y] == color) {  //Right 
      Chip nbChip = new Chip(x + 1, y, color);
      neighbors.add(nbChip);
    }
    if (y > 0 && grid[x][y - 1] == color) {  //Up 
      Chip nbChip = new Chip(x, y - 1, color);
      neighbors.add(nbChip);
    }
    if (y < DIMENSION - 1 && grid[x][y + 1] == color) {  //Down
      Chip nbChip = new Chip(x, y + 1, color);
      neighbors.add(nbChip);
    }
    if (x > 0 && y > 0 && grid[x - 1][y - 1] == color) {  //Up-left
      Chip nbChip = new Chip(x - 1, y - 1, color);
      neighbors.add(nbChip);
    }
    if (x < DIMENSION - 1 && y > 0  && grid[x + 1][y - 1] == color) {  //Up-right
      Chip nbChip = new Chip(x + 1, y - 1, color);
      neighbors.add(nbChip);
    }
    if (x > 0 && y < DIMENSION - 1  && grid[x - 1][y + 1] == color) {  //Down-left
      Chip nbChip = new Chip(x - 1, y + 1, color);
      neighbors.add(nbChip);
    }
    if (x < DIMENSION - 1 && y < DIMENSION - 1  && grid[x + 1][y + 1] == color) {  //Down-right
      Chip nbChip = new Chip(x + 1, y + 1, color);
      neighbors.add(nbChip);
    }
    return neighbors;
  }
  
  private boolean isInGroup(int x, int y, int color) {
    Chip c = new Chip(x, y, color);
    ArrayList<Chip> nbChips = neighborChips(c);
    if (nbChips.size() >= 2) {
      return true;
    } else if (nbChips.size() == 1) {
      Chip neighbor = nbChips.get(0);
      if (neighborChips(neighbor).size() > 0) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }
  
  public static void main(String[] args) {
 
  }
  
}