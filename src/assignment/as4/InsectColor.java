package assignment.as4;

public enum InsectColor {
    RED("Red"),
    GREEN("Green"),
    BLUE("Blue"),
    YELLOW("Yellow");
    private String testRepresentation;

    InsectColor(String testRepresentation) {
        this.testRepresentation = testRepresentation;
    }

    public String getTestRepresentation() {
        return testRepresentation;
    }

    public static InsectColor toColor(String s) {
        return InsectColor.valueOf(s.toUpperCase());
    }
    public static boolean colorExists(String s) {
        switch (s) {
            case "Red", "Green", "Blue", "Yellow": return true;
            default: return false;
        }
    }
}
