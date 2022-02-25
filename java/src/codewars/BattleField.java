import java.util.LinkedList;

public class BattleField {
    /***
     *     Write a method that takes a field for well-known board game "Battleship" as an argument and returns true if it has a
     *     valid disposition of ships, false otherwise. Argument is guaranteed to be 10*10 two-dimension array.
     *     Elements in the array are numbers, 0 if the cell is free and 1 if occupied by ship.
     * Before the game begins, players set up the board and place the ships accordingly to the following rules:
     * - There must be single battleship (size of 4 cells), 2 cruisers (size 3), 3 destroyers (size 2) and 4 submarines (size 1).
     * - Any additional ships are not allowed, as well as missing ships.
     * - Each ship must be a straight line, except for submarines, which are just single cell.
     * - The ship cannot overlap or be in contact with any other ship, neither by edge nor by corner.
     * */
    public static class Ship {
        enum Type {BATTLESHIP, CRUISER, DESTROYER, SUBMARINE, ERROR}
        int x, y, w, h;

        Ship(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        /**
         * Valid dimension
         *
         * @return
         */
        public boolean isValid() {
            return w == 1 || h == 1;
        }

        public Type getType() {
            int s = Math.max(w, h);
            if (s == 1)
                return Type.SUBMARINE;
            if (s == 2)
                return Type.DESTROYER;
            if (s == 3)
                return Type.CRUISER;
            if (s == 4)
                return Type.BATTLESHIP;
            return Type.ERROR;
        }

        public boolean touches(Ship s) {
            if (x - 1 <= s.x && s.x <= x + w
                    && y - 1 <= s.y && s.y <= y + h)
                return true;
            if (s.x - 1 <= x && x <= s.x + s.w
                    && s.y - 1 <= y && y <= s.y + s.h)
                return true;
            return false;
        }

        @Override
        public boolean equals(Object obj) {
            var s = (Ship) obj;
            return x == s.x && y == s.y && w == s.w && h == s.h;
        }
    }

    public static boolean fieldValidator(int[][] field) {
        var ships = new LinkedList<Ship>();

        // traverse right-left, top-bottom
        int N = 10;
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                if (field[x][y] == 1) {
                    // how wide-long is the ship ?
                    int h = 1, w = 1;
                    while (x + w < N && field[x + w][y] == 1) w++;
                    while (y + h < N && field[x][y + h] == 1) h++;
                    ships.add(new Ship(x, y, w, h));
                    // mark used
                    for (int i = 0; i < w; i++) field[x + i][y] = -1;
                    for (int i = 0; i < h; i++) field[x][y + i] = -1;
                }
            }
        }
        // valid
        if (ships.stream().anyMatch(s -> !s.isValid()))
            return false;
        // count each type of ships
        if (ships.stream().filter(s -> s.getType() == Ship.Type.BATTLESHIP).count() != 1)
            return false;
        if (ships.stream().filter(s -> s.getType() == Ship.Type.CRUISER).count() != 2)
            return false;
        if (ships.stream().filter(s -> s.getType() == Ship.Type.DESTROYER).count() != 3)
            return false;
        if (ships.stream().filter(s -> s.getType() == Ship.Type.SUBMARINE).count() != 4)
            return false;
        // validate touching ships
        if(ships.stream().anyMatch(s1 -> ships.stream().anyMatch(s2 -> !s1.equals(s2) && s1.touches(s2))))
            return false;
        return true;
    }

}
