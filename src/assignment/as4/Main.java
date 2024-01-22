package assignment.as4;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * This class controls actions performed on the game board.
 * @author LidaDavydova
 * @version 1.0
 */
public class Main {
    private static Board gameBoard;
    private static List<EntityPosition> positions = new ArrayList<>();
    private static final int D_UPPER_LIMIT = 1000;
    private static final int D_DOWN_LIMIT = 4;
    private static final int N_UPPER_LIMIT = 16;
    private static final int N_DOWN_LIMIT = 1;
    private static final int M_UPPER_LIMIT = 200;
    private static final int M_DOWN_LIMIT = 1;

    /**
     * This method controls gameBoard changes and catches errors.
     * @param args array of strings
     */
    public static void main(String[] args) {
        try (BufferedReader inp = new BufferedReader(new FileReader("input.txt"))) {
            File file = new File("output.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
            int d = Integer.parseInt(inp.readLine());
            if (d < D_DOWN_LIMIT || d > D_UPPER_LIMIT) {
                throw new InvalidBoardSizeException();
            }
            int n = Integer.parseInt(inp.readLine());
            if (n < N_DOWN_LIMIT || n > N_UPPER_LIMIT) {
                throw new InvalidNumberOfInsectsException();
            }
            int m = Integer.parseInt(inp.readLine());
            if (m < M_DOWN_LIMIT || m > M_UPPER_LIMIT) {
                throw new InvalidNumberOfFoodPointsException();
            }
            gameBoard = new Board(d);
            addEntity(n, d, inp);
            addFood(d, m, inp);
            for (EntityPosition position : positions) {
                BoardEntity entity = gameBoard.getEntity(position);
                if (entity instanceof Insect) {
                    String s = ((Insect) entity).color.getTestRepresentation() + " "
                            + gameBoard.getInsectType((Insect) entity) + " "
                            + gameBoard.getDirection((Insect) entity).getTextRepresentation() + " "
                            + gameBoard.getDirectionSum((Insect) entity) + "\n";
                    byte[] str = s.getBytes();
                    out.write(str);
                    gameBoard.removeEntity(entity.entityPosition);
                }
            }
            out.close();
        } catch (InvalidBoardSizeException e) {
            try (FileOutputStream out = new FileOutputStream("output.txt")) {
                byte[] str = e.getMessage().getBytes();
                out.write(str);
            } catch (IOException ex) {
                System.exit(0);
            }
        } catch (InvalidNumberOfInsectsException e) {
            try (FileOutputStream out = new FileOutputStream("output.txt")) {
                byte[] str = e.getMessage().getBytes();
                out.write(str);
            } catch (IOException ex) {
                System.exit(0);
            }
        } catch (InvalidNumberOfFoodPointsException e) {
            try (FileOutputStream out = new FileOutputStream("output.txt")) {
                byte[] str = e.getMessage().getBytes();
                out.write(str);
            } catch (IOException ex) {
                System.exit(0);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method adds Food Entity on the board.
     * @param d dimension
     * @param m number of food
     * @param inp input.txt
     */
    public static void addFood(int d, int m, BufferedReader inp) {
        try {
            int x;
            int y;
            int value;
            while (m > 0) {
                String[] line = inp.readLine().split(" ");
                value = Integer.parseInt(line[0]);
                x = Integer.parseInt(line[1]);
                y = Integer.parseInt(line[2]);
                if (x < 1 || x > d || y < 1 || y > d) {
                    throw new InvalidEntityPositionException();
                }
                EntityPosition enPos = new EntityPosition(x, y);
                if (gameBoard.getEntity(enPos) != null) {
                    throw new TwoEntitiesOnSamePositionException();
                }
                gameBoard.addEntity(new FoodPoint(enPos, value));
                m--;
            }
        } catch (InvalidEntityPositionException e) {
            try (FileOutputStream out = new FileOutputStream("output.txt")) {
                byte[] str = e.getMessage().getBytes();
                out.write(str);
            } catch (IOException ex) {
                System.exit(0);
            }
            System.exit(0);
        } catch (TwoEntitiesOnSamePositionException e) {
            try (FileOutputStream out = new FileOutputStream("output.txt")) {
                byte[] str = e.getMessage().getBytes();
                out.write(str);
                System.exit(0);
            } catch (IOException ex) {
                System.exit(0);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method adds Entity on the board.
     * @param n number of entities
     * @param d dimension
     * @param inp input.txt
     */
    public static void addEntity(int n, int d, BufferedReader inp) {
        try {
            String color;
            String insectType;
            int xCoordinate;
            int yCoordinate;
            while (n > 0) {
                String[] line = inp.readLine().split(" ");
                color = line[0];
                if (!InsectColor.colorExists(color)) {
                    throw new InvalidInsectPointException();
                }
                insectType = line[1];
                xCoordinate = Integer.parseInt(line[2]);
                yCoordinate = Integer.parseInt(line[line.length - 1]);
                if (xCoordinate < 1 || xCoordinate > d || yCoordinate < 1 || yCoordinate > d) {
                    throw new InvalidEntityPositionException();
                }
                EntityPosition pos = new EntityPosition(xCoordinate, yCoordinate);
                if (gameBoard.getEntity(pos) != null) {
                    throw new TwoEntitiesOnSamePositionException();
                }
                switch (insectType) {
                    case "Grasshopper":
                        for (EntityPosition pos2 : positions) {
                            Insect insect = (Insect) gameBoard.getEntity(pos2);
                            if (insect.getColor().equals(color) && insect instanceof Grasshopper) {
                                throw new DuplicateInsectException();
                            }
                        }
                        Grasshopper gr = new Grasshopper(pos, InsectColor.toColor(color));
                        gameBoard.addEntity(gr);
                        positions.add(pos);
                        break;
                    case "Ant":
                        for (EntityPosition pos2 : positions) {
                            Insect insect = (Insect) gameBoard.getEntity(pos2);
                            if (insect.getColor().equals(color) && insect instanceof Ant) {
                                throw new DuplicateInsectException();
                            }
                        }
                        Ant ant = new Ant(pos, InsectColor.toColor(color));
                        gameBoard.addEntity(ant);
                        positions.add(pos);
                        break;
                    case "Butterfly":
                        for (EntityPosition pos2 : positions) {
                            Insect insect = (Insect) gameBoard.getEntity(pos2);
                            if (insect.getColor().equals(color) && insect instanceof Butterfly) {
                                throw new DuplicateInsectException();
                            }
                        }
                        Butterfly bt = new Butterfly(pos, InsectColor.toColor(color));
                        gameBoard.addEntity(bt);
                        positions.add(pos);
                        break;
                    case "Spider":
                        for (EntityPosition pos2 : positions) {
                            Insect insect = (Insect) gameBoard.getEntity(pos2);
                            if (insect.getColor().equals(color) && insect instanceof Spider) {
                                throw new DuplicateInsectException();
                            }
                        }
                        Spider sp = new Spider(pos, InsectColor.toColor(color));
                        gameBoard.addEntity(sp);
                        positions.add(pos);
                        break;
                    default:
                        throw new InvalidInsectTypeException();
                }
                n--;
            }
        } catch (InvalidInsectTypeException e) {
            try (FileOutputStream out = new FileOutputStream("output.txt")) {
                byte[] str = e.getMessage().getBytes();
                out.write(str);
            } catch (IOException ex) {
                System.exit(0);
            }
            System.exit(0);
        } catch (InvalidInsectPointException e) {
            try (FileOutputStream out = new FileOutputStream("output.txt")) {
                byte[] str = e.getMessage().getBytes();
                out.write(str);
            } catch (IOException ex) {
                System.exit(0);
            }
            System.exit(0);
        } catch (InvalidEntityPositionException e) {
            try (FileOutputStream out = new FileOutputStream("output.txt")) {
                byte[] str = e.getMessage().getBytes();
                out.write(str);
            } catch (IOException ex) {
                System.exit(0);
            }
            System.exit(0);
        } catch (DuplicateInsectException e) {
            try (FileOutputStream out = new FileOutputStream("output.txt")) {
                byte[] str = e.getMessage().getBytes();
                out.write(str);
            } catch (IOException ex) {
                System.exit(0);
            }
            System.exit(0);
        } catch (TwoEntitiesOnSamePositionException e) {
            try (FileOutputStream out = new FileOutputStream("output.txt")) {
                byte[] str = e.getMessage().getBytes();
                out.write(str);
            } catch (IOException ex) {
                System.exit(0);
            }
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}

/**
 * This class extends from Insect and implements from OrthogonalMoving, DiagonalMoving interfaces.
 * @author LidaDavydova
 * @version 1.0
 */
class Butterfly extends Insect implements OrthogonalMoving, DiagonalMoving {
    /**
     * This method is a constructor for creating class's object.
     * @param entityPosition object of EntityPosition
     * @param color object of InsectColor
     */
    public Butterfly(EntityPosition entityPosition, InsectColor color) {
        super(entityPosition, color);
    }

    /**
     * This method chooses the best direction for entity object.
     * @param boardData game board
     * @param boardSize size of board
     * @return Direction best direction
     */
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

    /**
     * This method gives sum of food points for the best direction.
     * @param dir direction
     * @param boardData game board
     * @param boardSize size of board
     * @return sum of food points
     */
    @Override
    public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
        return travelOrthogonally(dir, super.entityPosition, super.color, boardData, boardSize);
    }
}
/**
 * This class extends from Insect and implements from OrthogonalMoving, DiagonalMoving interfaces.
 * @author LidaDavydova
 * @version 1.0
 */
class Ant extends Insect implements OrthogonalMoving, DiagonalMoving {
    /**
     * This method is a constructor for creating class's object.
     * @param entityPosition object of EntityPosition
     * @param color          object of InsectColor
     */
    public Ant(EntityPosition entityPosition, InsectColor color) {
        super(entityPosition, color);
    }

    /**
     * This method chooses the best direction for entity object.
     * @param boardData game board
     * @param boardSize size of board
     * @return Direction best direction
     */
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

    /**
     * This method gives sum of food points for the best direction.
     * @param dir       direction
     * @param boardData game board
     * @param boardSize size of board
     * @return sum of food points
     */
    public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
        return travelOrthogonally(dir, super.entityPosition, super.color, boardData, boardSize)
                + travelDiagonally(dir, super.entityPosition, super.color, boardData, boardSize);
    }
}

/**
 * This class extends from Insect and implements from OrthogonalMoving, DiagonalMoving interfaces.
 * @author LidaDavydova
 * @version 1.0
 */
class Spider extends Insect implements OrthogonalMoving, DiagonalMoving {
    /**
     * This method is a constructor for creating class's object.
     * @param entityPosition object of EntityPosition
     * @param color object of InsectColor
     */
    public Spider(EntityPosition entityPosition, InsectColor color) {
        super(entityPosition, color);
    }
    /**
     * This method chooses the best direction for entity object.
     * @param boardData game board
     * @param boardSize size of board
     * @return Direction the best direction
     */
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
    /**
     * This method gives sum of food points for the best direction.
     * @param dir direction
     * @param boardData game board
     * @param boardSize size of board
     * @return sum of food points
     */
    public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
        return travelDiagonally(dir, super.entityPosition, super.color, boardData, boardSize);
    }
}

/**
 * This class produces Invalid board size exception.
 * @author LidaDavydova
 * @version 1.0
 */
class InvalidBoardSizeException extends Exception {
    /**
     * This method gives message of error.
     * @return String message of error
     */
    @Override
    public String getMessage() {
        return "Invalid board size\n";
    }
}

/**
 * This class produces Invalid number of insects exception.
 * @author LidaDavydova
 * @version 1.0
 */
class InvalidNumberOfInsectsException extends Exception {
    /**
     * This method gives message of error.
     * @return String message of error
     */
    @Override
    public String getMessage() {
        return "Invalid number of insects\n";
    }
}

/**
 * This class produces Invalid number of food points exception.
 * @author LidaDavydova
 * @version 1.0
 */
class InvalidNumberOfFoodPointsException extends Exception {
    /**
     * This method gives message of error.
     * @return String message of error
     */
    @Override
    public String getMessage() {
        return "Invalid number of food points\n";
    }
}

/**
 * This class produces Invalid insect color exception.
 * @author LidaDavydova
 * @version 1.0
 */
class InvalidInsectPointException extends Exception {
    /**
     * This method gives message of error.
     * @return String message of error
     */
    @Override
    public String getMessage() {
        return "Invalid insect color\n";
    }
}

/**
 * This class produces Invalid insect color exception.
 * @author LidaDavydova
 * @version 1.0
 */
class InvalidInsectTypeException extends Exception {
    /**
     * This method gives message of error.
     * @return String message of error
     */
    @Override
    public String getMessage() {
        return "Invalid insect type\n";
    }
}

/**
 * This class produces Invalid entity position exception.
 * @author LidaDavydova
 * @version 1.0
 */
class InvalidEntityPositionException extends Exception {
    /**
     * This method gives message of error.
     * @return String message of error
     */
    @Override
    public String getMessage() {
        return "Invalid entity position\n";
    }
}

/**
 * This class produces Duplicate insects exception.
 * @author LidaDavydova
 * @version 1.0
 */
class DuplicateInsectException extends Exception {
    /**
     * This method gives message of error.
     * @return String message of error
     */
    @Override
    public String getMessage() {
        return "Duplicate insects\n";
    }
}

/**
 * This class produces Two entities in the same position exception.
 * @author LidaDavydova
 * @version 1.0
 */
class TwoEntitiesOnSamePositionException extends Exception {
    /**
     * This method gives message of error.
     * @return String message of error
     */
    @Override
    public String getMessage() {
        return "Two entities in the same position\n";
    }
}

/**
 * This class is a game board. It adds entities, gets, removes them, gets the best direction, gets insect's type.
 * @author LidaDavydova
 * @version 1.0
 */
class Board {
    private Map<String, BoardEntity> boardData = new LinkedHashMap<>();
    private int size;

    /**
     * This is a constructor for class.
     * @param boardSize size of the board
     */
    public Board(int boardSize) {
        size = boardSize;
    }

    /**
     * This method remove entity from the board.
     * @param position EntityPosition
     */
    public void removeEntity(EntityPosition position) {
        boardData.remove(position.getX() + " " + position.getY());
    }

    /**
     * This method add entity from the board.
     * @param entity BoardEntity
     */
    public void addEntity(BoardEntity entity) {
        boardData.put(entity.getEntityPosition(), entity);
    }

    /**
     * This method gets entity.
     * @param position EntityPosition
     * @return BoardEntity
     */
    public BoardEntity getEntity(EntityPosition position) {
        return boardData.get(position.getX() + " " + position.getY());
    }

    /**
     * This method gets the best direction.
     * @param insect Insect
     * @return Direction
     */
    public Direction getDirection(Insect insect) {
        return insect.getBestDirection(boardData, size);
    }

    /**
     * This method gets sum of eaten food.
     * @param insect Insect
     * @return sum of eaten food.
     */
    public int getDirectionSum(Insect insect) {
        return insect.travelDirection(insect.getBestDirection(boardData, size), boardData, size);
    }

    /**
     * This method gets Insect type.
     * @param insect Insect
     * @return Insect type
     */
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

/**
 * This class extends from Insect.
 * @author LidaDavydova
 * @version 1.0
 */
class Grasshopper extends Insect {
    /**
     * This is a constructor of a class.
     * @param entityPosition EntityPosition
     * @param color InsectColor
     */
    public Grasshopper(EntityPosition entityPosition, InsectColor color) {
        super(entityPosition, color);
    }

    /**
     * This method gets the best direction.
     * @param boardData hash map board
     * @param boardSize board size
     * @return Direction
     */
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

    /**
     * This method gets sum of food on the best direction.
     * @param dir Direction
     * @param boardData hash map board
     * @param boardSize board size
     * @return sum of eaten food
     */
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
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " "
                                + ((FoodPoint) obj).entityPosition.getY());
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
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " "
                                + ((FoodPoint) obj).entityPosition.getY());
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
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " "
                                + ((FoodPoint) obj).entityPosition.getY());
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
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " "
                                + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
            default:
                return amount;
        }
        return amount;
    }
}

/**
 * This is an abstract class for insect.
 * @author LidaDavydova
 * @version 1.0
 */
abstract class Insect extends BoardEntity {
    protected InsectColor color;

    /**
     * This is a constructor to a class.
     * @param position entity position
     * @param color color
     */
    public Insect(EntityPosition position, InsectColor color) {
        super.entityPosition = position;
        this.color = color;
    }

    /**
     * This method give text representation.
     * @return text representation
     */
    public String getColor() {
        return color.getTestRepresentation();
    }

    /**
     * This is abstract method.
     * @param boardData hash map board
     * @param boardSize board size
     * @return Direction
     */
    public abstract Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize);

    /**
     * This is abstract method.
     * @param direction
     * @param boardData hash map board
     * @param boardSize board size
     * @return Direction
     */
    public abstract int travelDirection(Direction direction, Map<String, BoardEntity> boardData, int boardSize);
}

/**
 * This an abstract class for board entity.
 * @author LidaDavydova
 * @version 1.0
 */
abstract class BoardEntity {
    protected EntityPosition entityPosition;

    /**
     * This method gives x coordinate.
     * @return integer of x coordinate
     */
    public int getXCoordinate() {
        return Integer.parseInt(entityPosition.getX());
    }

    /**
     * This method gives y coordinate.
     * @return integer of y coordinate
     */
    public int getYCoordinate() {
        return Integer.parseInt(entityPosition.getY());
    }

    /**
     * This method represent coordinates in pair.
     * @return string of x and y coordinates
     */
    public String getEntityPosition() {
        return entityPosition.getX() + " " + entityPosition.getY();
    }
}

/**
 * This is enum for directions.
 * @author LidaDavydova
 * @version 1.0
 */
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

    /**
     * This is a constructor for enum.
     * @param text direction in text
     */
    Direction(String text) {
        textRepresentation = text;
    }

    /**
     * This method gives textRepresentation of direction.
     * @return textRepresentation
     */
    public String getTextRepresentation() {
        return textRepresentation;
    }
}

/**
 * This class extends from board entity and has value of food.
 * @author LidaDavydova
 * @version 1.0
 */
class FoodPoint extends BoardEntity {
    protected int value;

    /**
     * This is a constructor for a class.
     * @param position position of food
     * @param value value of food
     */
    public FoodPoint(EntityPosition position, int value) {
        this.value = value;
        super.entityPosition = position;
    }
}

/**
 * This class has coordinates of entity.
 * @author LidaDavydova
 * @version 1.0
 */
class EntityPosition {
    private int x;
    private int y;

    /**
     * This is a constructor for a class.
     * @param x x coordinate
     * @param y y coordinate
     */
    public EntityPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This method gives x coordinate.
     * @return x
     */
    public String getX() {
        return String.valueOf(x);
    }

    /**
     * This method gives y coordinate.
     * @return y
     */
    public String getY() {
        return String.valueOf(y);
    }
}

/**
 * This is enum for insect color.
 * @author LidaDavydova
 * @version 1.0
 */
enum InsectColor {
    RED("Red"),
    GREEN("Green"),
    BLUE("Blue"),
    YELLOW("Yellow");
    private String testRepresentation;

    /**
     * This is a constructor for enum.
     * @param testRepresentation test representation of color
     */
    InsectColor(String testRepresentation) {
        this.testRepresentation = testRepresentation;
    }

    /**
     * This method gives testRepresentation of color.
     * @return testRepresentation
     */
    public String getTestRepresentation() {
        return testRepresentation;
    }

    /**
     * This method provide test representation of color to enum value.
     * @param s test representation of color
     * @return InsectColor
     */
    public static InsectColor toColor(String s) {
        return InsectColor.valueOf(s.toUpperCase());
    }

    /**
     * This method check if color exists in enum values.
     * @param s test representation of color
     * @return true if yes, false if not
     */
    public static boolean colorExists(String s) {
        switch (s) {
            case "Red": case "Green": case "Blue": case "Yellow": return true;
            default: return false;
        }
    }
}

/**
 * This is an interface for diagonal moving.
 * @author LidaDavydova
 * @version 1.0
 */
interface DiagonalMoving {
    /**
     * This method gives sum of food points for the best diagonal direction.
     * @param dir direction
     * @param entityPosition position of entity
     * @param boardData game board
     * @param boardSize size of board
     * @return sum of food points
     */
    default int getDiagonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition,
                                                Map<String, BoardEntity> boardData, int boardSize) {
        int x = Integer.parseInt(entityPosition.getX());
        int y = Integer.parseInt(entityPosition.getY());
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
            default:
                return n;
        }
    }
    /**
     * This method gives sum of food points for the best diagonal direction including
     * meeting with the same color entity.
     * @param dir direction
     * @param entityPosition position of entity
     * @param color color of entity
     * @param boardData game board
     * @param boardSize size of board
     * @return sum of food points
     */
    default int travelDiagonally(Direction dir, EntityPosition entityPosition,
                                InsectColor color, Map<String, BoardEntity> boardData, int boardSize) {
        int x = Integer.parseInt(entityPosition.getX());
        int y = Integer.parseInt(entityPosition.getY());
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
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " "
                                + ((FoodPoint) obj).entityPosition.getY());
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
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " "
                                + ((FoodPoint) obj).entityPosition.getY());
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
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " "
                                + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
            default:
                return amount;
        }
        return amount;
    }
}

/**
 * This is an interface for orthogonal moving.
 * @author LidaDavydova
 * @version 1.0
 */
interface OrthogonalMoving {
    /**
     * This method gives sum of food points for the best orthogonal direction.
     * @param dir direction
     * @param entityPosition position of entity
     * @param boardData game board
     * @param boardSize size of board
     * @return sum of food points
     */
    default int getOrthogonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition,
                                                  Map<String, BoardEntity> boardData, int boardSize) {
        int x = Integer.parseInt(entityPosition.getX());
        int y = Integer.parseInt(entityPosition.getY());
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
            default:
                return n;
        }
    }
    /**
     * This method gives sum of food points for the best orthogonal direction
     * including meeting with the same color entity.
     * @param dir direction
     * @param entityPosition position of entity
     * @param color color of entity
     * @param boardData game board
     * @param boardSize size of board
     * @return sum of food points
     */
    default int travelOrthogonally(Direction dir, EntityPosition entityPosition,
                                  InsectColor color, Map<String, BoardEntity> boardData, int boardSize) {
        int x = Integer.parseInt(entityPosition.getX());
        int y = Integer.parseInt(entityPosition.getY());
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
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " "
                                + ((FoodPoint) obj).entityPosition.getY());
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
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " "
                                + ((FoodPoint) obj).entityPosition.getY());
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
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " "
                                + ((FoodPoint) obj).entityPosition.getY());
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
                        boardData.remove(((FoodPoint) obj).entityPosition.getX() + " "
                                + ((FoodPoint) obj).entityPosition.getY());
                    }
                }
                break;
            default:
                return amount;
        }
        return amount;
    }
}
