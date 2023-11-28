package assignment.as4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.lang.Exception;
import java.util.LinkedHashMap;

public class Main {
    private static Board gameBoard;
    private static List<EntityPosition> positions = new ArrayList<>();
    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        File file = new File("output.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            int D = in.nextInt();
            if (4 > D || D > 1000) {
                throw new InvalidBoardSizeException();
            }
            int N = in.nextInt();
            if (N < 1 || N > 16) {
                throw new InvalidNumberOfInsectsException();
            }
            int M = in.nextInt();
            if (N < 1 || M > 200) {
                throw new InvalidNumberOfFoodPointsException();
            }
            gameBoard = new Board(D);
            String Color = "";
            String InsectType = "";
            int XCoordinate;
            int YCoordinate;
            while (N > 0) {
                Color = in.next();
                if (!InsectColor.colorExists(Color)) {
                    throw new InvalidInsectPointException();
                }
                InsectType = in.next();
                XCoordinate = Integer.parseInt(in.next());
                YCoordinate = Integer.parseInt(in.next());
                if (XCoordinate < 1 || XCoordinate > D || YCoordinate < 1 || YCoordinate > D) {
                    throw new InvalidEntityPositionException();
                }
                switch (InsectType) {
                    case "Grasshopper":
                        EntityPosition pos1 = new EntityPosition(XCoordinate, YCoordinate);
                        if (gameBoard.getEntity(pos1) instanceof Insect) {
                            if (((Insect) gameBoard.getEntity(pos1)).getColor().equals(Color) && gameBoard.getEntity(pos1) instanceof Grasshopper) {
                                throw new DuplicateInsectException();
                            }
                        }
                        Grasshopper gr = new Grasshopper(pos1, InsectColor.toColor(Color));
                        gameBoard.addEntity(gr);
                        positions.add(pos1);
                        break;
                    case "Ant":
                        EntityPosition pos2 = new EntityPosition(XCoordinate, YCoordinate);
                        if (gameBoard.getEntity(pos2) instanceof Insect) {
                            if (((Insect) gameBoard.getEntity(pos2)).getColor().equals(Color) && gameBoard.getEntity(pos2) instanceof Ant) {
                                throw new DuplicateInsectException();
                            }
                        }
                        Ant ant = new Ant(pos2, InsectColor.toColor(Color));
                        gameBoard.addEntity(ant);
                        positions.add(pos2);
                        break;
                    case "Butterfly":
                        EntityPosition pos3 = new EntityPosition(XCoordinate, YCoordinate);
                        if (gameBoard.getEntity(pos3) instanceof Insect) {
                            if (((Insect) gameBoard.getEntity(pos3)).getColor().equals(Color) && gameBoard.getEntity(pos3) instanceof Butterfly) {
                                throw new DuplicateInsectException();
                            }
                        }
                        Butterfly bt = new Butterfly(pos3, InsectColor.toColor(Color));
                        gameBoard.addEntity(bt);
                        positions.add(pos3);
                        break;
                    case "Spider":
                        EntityPosition pos4 = new EntityPosition(XCoordinate, YCoordinate);
                        if (gameBoard.getEntity(pos4) instanceof Insect) {
                            if (((Insect) gameBoard.getEntity(pos4)).getColor().equals(Color) && gameBoard.getEntity(pos4) instanceof Spider) {
                                throw new DuplicateInsectException();
                            }
                        }
                        Spider sp = new Spider(pos4, InsectColor.toColor(Color));
                        gameBoard.addEntity(sp);
                        positions.add(pos4);
                        break;
                    default:
                        throw new InvalidInsectTypeException();
                }
                N--;
            }
            int x;
            int y;
            int value;
            while (M > 0) {
                value = Integer.parseInt(in.next());
                x = Integer.parseInt(in.next());
                y = Integer.parseInt(in.next());
                if (x < 1 || x> D || y < 1 || y > D) {
                    throw new InvalidEntityPositionException();
                }
                EntityPosition enPos = new EntityPosition(x, y);
                if (gameBoard.getEntity(enPos) != null) {
                    throw new TwoEntitiesOnSamePositionException();
                }
                gameBoard.addEntity(new FoodPoint(enPos, value));
                M--;
            }
        } catch (InvalidBoardSizeException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (InvalidNumberOfInsectsException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (InvalidNumberOfFoodPointsException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (InvalidInsectTypeException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (InvalidInsectPointException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (InvalidEntityPositionException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (DuplicateInsectException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (TwoEntitiesOnSamePositionException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        for (EntityPosition position : positions) {
            BoardEntity entity = gameBoard.getEntity(position);
            if (entity instanceof Insect) {
                System.out.printf("%s %s %s %s\n", ((Insect) entity).color.getTestRepresentation(),
                        gameBoard.getInsectType((Insect) entity),
                        gameBoard.getDirection((Insect) entity).getTextRepresentation(),
                        gameBoard.getDirectionSum((Insect) entity));
                gameBoard.removeEntity(entity.entityPosition);
            }
        }
    }
}

class Butterfly extends Insect implements OrthogonalMoving, DiagonalMoving {
    public Butterfly(EntityPosition entityPosition, InsectColor color) {
        super(entityPosition, color);
    }
    @Override
    public Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize) {
        int maxFoodPoints = 0;
        Direction dir = Direction.N;
        int val = this.getOrthogonalDirectionVisibleValue(Direction.N, super.entityPosition, boardData, boardSize);
        if (val >= maxFoodPoints) {
            maxFoodPoints = val;
        }
        val = this.getOrthogonalDirectionVisibleValue(Direction.E, super.entityPosition, boardData, boardSize);
        if (val > maxFoodPoints) {
            maxFoodPoints = val;
            dir = Direction.E;
        }
        val = this.getOrthogonalDirectionVisibleValue(Direction.S, super.entityPosition, boardData, boardSize);
        if (val > maxFoodPoints) {
            maxFoodPoints = val;
            dir = Direction.S;
        }
        val = this.getOrthogonalDirectionVisibleValue(Direction.W, super.entityPosition, boardData, boardSize);
        if (val > maxFoodPoints) {
            maxFoodPoints = val;
            dir = Direction.W;
        }
        return dir;
    }
    @Override
    public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
        return travelOrthogonally(dir, super.entityPosition, super.color, boardData, boardSize);
    }
    @Override
    public int getOrthogonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition, Map<String, BoardEntity> boardData, int boardSize) {
        int x = super.getXCoordinate();
        int y = super.getYCoordinate();
        int n = 0;
        switch (dir) {
            case S:
                for (int i = x + 1; i <= boardSize; i++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case N:
                for (int i = x - 1; i > 0; i--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case E:
                for (int i = y + 1; i <= boardSize; i++) {
                    BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case W:
                for (int i = y - 1; i > 0; i--) {
                    BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
        }
        return n;
    }
    @Override
    public int getDiagonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition, Map<String, BoardEntity> boardData, int boardSize) {
        int x = super.getXCoordinate();
        int y = super.getYCoordinate();
        int n = 0;
        switch (dir) {
            case SE:
                for (int i = x + 1, j = y + 1; i <= boardSize && j <= boardSize; i++, j++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case NE:
                for (int i = x - 1, j = y + 1; i > 0 && j <= boardSize; i--, j++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case SW:
                for (int i = x + 1, j = y - 1; i <= boardSize && j > 0; i++, j--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case NW:
                for (int i = x - 1, j = y - 1; i > 0 && j > 0; i--, j--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
        }
        return n;
    };
    @Override
    public int travelOrthogonally(Direction dir, EntityPosition entityPosition, InsectColor color, Map<String, BoardEntity> boardData, int boardSize) {
        int x = super.getXCoordinate();
        int y = super.getYCoordinate();
        int amount = 0;
        switch (dir) {
            case S:
                for (int i = x + 1; i <= boardSize; i++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case N:
                for (int i = x - 1; i > 0; i--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case E:
                for (int i = y + 1; i <= boardSize; i++) {
                    BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case W:
                for (int i = y - 1; i > 0; i--) {
                    BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
        }
        return amount;
    };
    @Override
    public int travelDiagonally(Direction dir, EntityPosition entityPosition, InsectColor color, Map<String, BoardEntity> boardData, int boardSize) {
        int x = super.getXCoordinate();
        int y = super.getYCoordinate();
        int amount = 0;
        switch (dir) {
            case SE:
                for (int i = x + 1, j = y + 1; i <= boardSize && j <= boardSize; i++, j++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case NE:
                for (int i = x - 1, j = y + 1; i > 0 && j <= boardSize; i--, j++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        System.out.println(boardData.get(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY()));
                        boardData.remove(String.valueOf(i) + " " + String.valueOf(j));
                    }
                }
                break;
            case SW:
                for (int i = x + 1, j = y - 1; i <= boardSize && j > 0; i++, j--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case NW:
                for (int i = x - 1, j = y - 1; i > 0 && j > 0; i--, j--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
        }
        return amount;
    }
}

class Ant extends Insect implements OrthogonalMoving, DiagonalMoving {
    public Ant(EntityPosition entityPosition, InsectColor color) {
        super(entityPosition, color);
    }
    public Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize) {
        int maxFoodPoints = 0;
        Direction dir = Direction.N;
        int val = this.getOrthogonalDirectionVisibleValue(Direction.N, super.entityPosition, boardData, boardSize);
        if (val >= maxFoodPoints) {
            maxFoodPoints = val;
        }
        val = this.getOrthogonalDirectionVisibleValue(Direction.E, super.entityPosition, boardData, boardSize);
        if (val > maxFoodPoints) {
            maxFoodPoints = val;
            dir = Direction.E;
        }
        val = this.getOrthogonalDirectionVisibleValue(Direction.S, super.entityPosition, boardData, boardSize);
        if (val > maxFoodPoints) {
            maxFoodPoints = val;
            dir = Direction.S;
        }
        val = this.getOrthogonalDirectionVisibleValue(Direction.W, super.entityPosition, boardData, boardSize);
        if (val > maxFoodPoints) {
            maxFoodPoints = val;
            dir = Direction.W;
        }
        val = this.getDiagonalDirectionVisibleValue(Direction.NE, super.entityPosition, boardData, boardSize);
        if (val > maxFoodPoints) {
            maxFoodPoints = val;
            dir = Direction.NE;
        }
        val = this.getDiagonalDirectionVisibleValue(Direction.SE, super.entityPosition, boardData, boardSize);
        if (val > maxFoodPoints) {
            maxFoodPoints = val;
            dir = Direction.SE;
        }
        val = this.getDiagonalDirectionVisibleValue(Direction.SW, super.entityPosition, boardData, boardSize);
        if (val > maxFoodPoints) {
            maxFoodPoints = val;
            dir = Direction.SW;
        }
        val = this.getDiagonalDirectionVisibleValue(Direction.NW, super.entityPosition, boardData, boardSize);
        if (val > maxFoodPoints) {
            maxFoodPoints = val;
            dir = Direction.NW;
        }
        return dir;
    }
    public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
        return travelOrthogonally(dir, super.entityPosition, super.color, boardData, boardSize) + travelDiagonally(dir, super.entityPosition, super.color, boardData, boardSize);
    }
    @Override
    public int getOrthogonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition, Map<String, BoardEntity> boardData, int boardSize) {
        int x = super.getXCoordinate();
        int y = super.getYCoordinate();
        int n = 0;
        switch (dir) {
            case S:
                for (int i = x + 1; i <= boardSize; i++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case N:
                for (int i = x - 1; i > 0; i--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case E:
                for (int i = y + 1; i <= boardSize; i++) {
                    BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case W:
                for (int i = y - 1; i > 0; i--) {
                    BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
        }
        return n;
    }
    @Override
    public int getDiagonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition, Map<String, BoardEntity> boardData, int boardSize) {
        int x = super.getXCoordinate();
        int y = super.getYCoordinate();
        int n = 0;
        switch (dir) {
            case SE:
                for (int i = x + 1, j = y + 1; i <= boardSize && j <= boardSize; i++, j++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case NE:
                for (int i = x - 1, j = y + 1; i > 0 && j <= boardSize; i--, j++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case SW:
                for (int i = x + 1, j = y - 1; i <= boardSize && j > 0; i++, j--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case NW:
                for (int i = x - 1, j = y - 1; i > 0 && j > 0; i--, j--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
        }
        return n;
    };
    @Override
    public int travelOrthogonally(Direction dir, EntityPosition entityPosition, InsectColor color, Map<String, BoardEntity> boardData, int boardSize) {
        int x = super.getXCoordinate();
        int y = super.getYCoordinate();
        int amount = 0;
        switch (dir) {
            case S:
                for (int i = x + 1; i <= boardSize; i++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case N:
                for (int i = x - 1; i > 0; i--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case E:
                for (int i = y + 1; i <= boardSize; i++) {
                    BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case W:
                for (int i = y - 1; i > 0; i--) {
                    BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
        }
        return amount;
    };
    @Override
    public int travelDiagonally(Direction dir, EntityPosition entityPosition, InsectColor color, Map<String, BoardEntity> boardData, int boardSize) {
        int x = super.getXCoordinate();
        int y = super.getYCoordinate();
        int amount = 0;
        switch (dir) {
            case SE:
                for (int i = x + 1, j = y + 1; i <= boardSize && j <= boardSize; i++, j++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case NE:
                for (int i = x - 1, j = y + 1; i > 0 && j <= boardSize; i--, j++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        System.out.println(boardData.get(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY()));
                        boardData.remove(String.valueOf(i) + " " + String.valueOf(j));
                    }
                }
                break;
            case SW:
                for (int i = x + 1, j = y - 1; i <= boardSize && j > 0; i++, j--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case NW:
                for (int i = x - 1, j = y - 1; i > 0 && j > 0; i--, j--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
        }
        return amount;
    }
}
class Spider extends Insect implements OrthogonalMoving, DiagonalMoving {
    public Spider(EntityPosition entityPosition, InsectColor color) {
        super(entityPosition, color);
    }
    public Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize) {
        int maxFoodPoints = 0;
        Direction dir = Direction.NE;
        int val = this.getDiagonalDirectionVisibleValue(Direction.NE, super.entityPosition, boardData, boardSize);
        if (val > maxFoodPoints) {
            maxFoodPoints = val;
            dir = Direction.NE;
        }
        val = this.getDiagonalDirectionVisibleValue(Direction.SE, super.entityPosition, boardData, boardSize);
        if (val > maxFoodPoints) {
            maxFoodPoints = val;
            dir = Direction.SE;
        }
        val = this.getDiagonalDirectionVisibleValue(Direction.SW, super.entityPosition, boardData, boardSize);
        if (val > maxFoodPoints) {
            maxFoodPoints = val;
            dir = Direction.SW;
        }
        val = this.getDiagonalDirectionVisibleValue(Direction.NW, super.entityPosition, boardData, boardSize);
        if (val > maxFoodPoints) {
            maxFoodPoints = val;
            dir = Direction.NW;
        }
        return dir;
    }
    public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
        return travelDiagonally(dir, super.entityPosition, super.color, boardData, boardSize);
    }
    @Override
    public int getOrthogonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition, Map<String, BoardEntity> boardData, int boardSize) {
        int x = super.getXCoordinate();
        int y = super.getYCoordinate();
        int n = 0;
        switch (dir) {
            case S:
                for (int i = x + 1; i <= boardSize; i++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case N:
                for (int i = x - 1; i > 0; i--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case E:
                for (int i = y + 1; i <= boardSize; i++) {
                    BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case W:
                for (int i = y - 1; i > 0; i--) {
                    BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
        }
        return n;
    }
    @Override
    public int getDiagonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition, Map<String, BoardEntity> boardData, int boardSize) {
        int x = super.getXCoordinate();
        int y = super.getYCoordinate();
        int n = 0;
        switch (dir) {
            case SE:
                for (int i = x + 1, j = y + 1; i <= boardSize && j <= boardSize; i++, j++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case NE:
                for (int i = x - 1, j = y + 1; i > 0 && j <= boardSize; i--, j++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case SW:
                for (int i = x + 1, j = y - 1; i <= boardSize && j > 0; i++, j--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
            case NW:
                for (int i = x - 1, j = y - 1; i > 0 && j > 0; i--, j--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof FoodPoint) {
                        n += ((FoodPoint) obj).value;
                    }
                }
                return n;
        }
        return n;
    };
    @Override
    public int travelOrthogonally(Direction dir, EntityPosition entityPosition, InsectColor color, Map<String, BoardEntity> boardData, int boardSize) {
        int x = super.getXCoordinate();
        int y = super.getYCoordinate();
        int amount = 0;
        switch (dir) {
            case S:
                for (int i = x + 1; i <= boardSize; i++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case N:
                for (int i = x - 1; i > 0; i--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case E:
                for (int i = y + 1; i <= boardSize; i++) {
                    BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case W:
                for (int i = y - 1; i > 0; i--) {
                    BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
        }
        return amount;
    };
    @Override
    public int travelDiagonally(Direction dir, EntityPosition entityPosition, InsectColor color, Map<String, BoardEntity> boardData, int boardSize) {
        int x = super.getXCoordinate();
        int y = super.getYCoordinate();
        int amount = 0;
        switch (dir) {
            case SE:
                for (int i = x + 1, j = y + 1; i <= boardSize && j <= boardSize; i++, j++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case NE:
                for (int i = x - 1, j = y + 1; i > 0 && j <= boardSize; i--, j++) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        System.out.println(boardData.get(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY()));
                        boardData.remove(String.valueOf(i) + " " + String.valueOf(j));
                    }
                }
                break;
            case SW:
                for (int i = x + 1, j = y - 1; i <= boardSize && j > 0; i++, j--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case NW:
                for (int i = x - 1, j = y - 1; i > 0 && j > 0; i--, j--) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(j));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
        }
        return amount;
    }
}
class InvalidBoardSizeException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid board size";
    }
}
class InvalidNumberOfInsectsException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid number of insects";
    }
}
class InvalidNumberOfFoodPointsException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid number of food points";
    }
}
class InvalidInsectPointException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid insect color";
    }
}
class InvalidInsectTypeException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid insect type";
    }
}
class InvalidEntityPositionException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid entity position";
    }
}
class DuplicateInsectException extends Exception {
    @Override
    public String getMessage() {
        return "Duplicate insects";
    }
}
class TwoEntitiesOnSamePositionException extends Exception {
    @Override
    public String getMessage() {
        return "Two entities in the same position";
    }
}
class Board {
    private Map<String, BoardEntity> boardData = new LinkedHashMap<>();
    private int size;
    public Board(int boardSize) {
        size = boardSize;
    }
    public void removeEntity(EntityPosition position) {
        boardData.remove(position.getX() + " " + position.getY());
    }
    public void addEntity(BoardEntity entity) {
        boardData.put(entity.getEntityPosition(), entity);
    }
    public BoardEntity getEntity(EntityPosition position) {
        return boardData.get(position.getX() + " " + position.getY());
    }
    public Direction getDirection(Insect insect) {
        return insect.getBestDirection(boardData, size);
    }
    public int getDirectionSum(Insect insect) {
        return insect.travelDirection(insect.getBestDirection(boardData, size), boardData, size);
    }
    public String getInsectType(Insect insect) {
        if (insect instanceof Ant) {
            return "Ant";
        } else if (insect instanceof Butterfly) {
            return "Butterfly";
        } else if (insect instanceof Spider) {
            return "Spider";
        } else if (insect instanceof Grasshopper) {
            return "Grasshopper";
        }
        return "";
    }
}
class Grasshopper extends Insect {
    public Grasshopper(EntityPosition entityPosition, InsectColor color) {
        super(entityPosition, color);
    }
    public Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize) {
        int x = super.getXCoordinate();
        int y = super.getYCoordinate();
        int maxFoodPoints = 0;
        Direction dir = Direction.N;
        int n = 0;
        for (int i = x - 2; i >= 1; i -= 2) {
            BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
            if (obj instanceof FoodPoint) {
                n += ((FoodPoint) obj).value;
            }
        }
        if (maxFoodPoints < n) {
            maxFoodPoints = n;
        }
        n = 0;
        for (int i = y + 2; i <= boardSize; i += 2) {
            BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
            if (obj instanceof FoodPoint) {
                n += ((FoodPoint) obj).value;
            }
        }
        if (maxFoodPoints < n) {
            maxFoodPoints = n;
            dir = Direction.E;
        }
        n = 0;
        for (int i = x + 2; i <= boardSize; i += 2) {
            BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
            if (obj instanceof FoodPoint) {
                n += ((FoodPoint) obj).value;
            }
        }
        if (maxFoodPoints < n) {
            maxFoodPoints = n;
            dir = Direction.S;
        }
        n = 0;
        for (int i = y - 2; i >= 1; i -= 2) {
            BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
            if (obj instanceof FoodPoint) {
                n += ((FoodPoint) obj).value;
            }
        }
        if (maxFoodPoints < n) {
            maxFoodPoints = n;
            dir = Direction.W;
        }
        return dir;
    }
    public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
        int amount = 0;
        int x = super.getXCoordinate();
        int y = super.getYCoordinate();
        switch (dir) {
            case E:
                for (int i = y + 2; i <= boardSize; i += 2) {
                    BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case W:
                for (int i = y - 2; i >= 1; i -= 2) {
                    BoardEntity obj = boardData.get(String.valueOf(x) + " " + String.valueOf(i));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case N:
                for (int i = x - 2; i >= 1; i -= 2) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            case S:
                for (int i = x + 2; i <= boardSize; i += 2) {
                    BoardEntity obj = boardData.get(String.valueOf(i) + " " + String.valueOf(y));
                    if (obj instanceof Insect) {
                        if (!((Insect) obj).color.equals(color)) {
                            break;
                        }
                    }
                    if (obj instanceof FoodPoint) {
                        amount += ((FoodPoint) obj).value;
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " " + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
        }
        return amount;
    }
}
abstract class Insect extends BoardEntity {
    protected InsectColor color;
    public Insect(EntityPosition position, InsectColor color) {
        super.entityPosition = position;
        this.color = color;
    }
    public String getColor() {
        return color.getTestRepresentation();
    }
    abstract public Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize);
    abstract public int travelDirection(Direction direction, Map<String, BoardEntity> boardData, int boardSize);
}
abstract class BoardEntity {
    protected EntityPosition entityPosition;
    public int getXCoordinate() {
        return Integer.parseInt(entityPosition.getX());
    }
    public int getYCoordinate() {
        return Integer.parseInt(entityPosition.getY());
    }
    public String getEntityPosition() {
        return entityPosition.getX() + " " + entityPosition.getY();
    }
}
enum Direction {
    N("North"),
    E("East"),
    S("South"),
    W("West"),
    NE("North-East"),
    SE("South-East"),
    SW("South-West"),
    NW("North-West");
    private String textRepresentation;
    Direction(String text) {
        textRepresentation = text;
    }
    public String getTextRepresentation() {
        return textRepresentation;
    }
}
class FoodPoint extends BoardEntity {
    protected int value;
    public FoodPoint(EntityPosition position, int value) {
        this.value = value;
        super.entityPosition = position;
    }
}
class EntityPosition {
    private int x;
    private int y;
    public EntityPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public String getX() {
        return String.valueOf(x);
    }
    public String getY() {
        return String.valueOf(y);
    }
}
interface DiagonalMoving {
    int getDiagonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition, Map<String, BoardEntity> boardData, int boardSize);
    int travelDiagonally(Direction dir, EntityPosition entityPosition, InsectColor color, Map<String, BoardEntity> boardData, int boardSize);
}
interface OrthogonalMoving {
    int getOrthogonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition, Map<String, BoardEntity> boardData, int boardSize);
    int travelOrthogonally(Direction dir, EntityPosition entityPosition, InsectColor color, Map<String, BoardEntity> boardData, int boardSize);
}