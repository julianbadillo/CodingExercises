import java.util.LinkedList;

/**
 * Created by jbadillo on 4/1/2016.
 */
public class Kirk {
    // State machine
    char [][] mat;
    char [][] moving;
    boolean [][] visited;
    Integer [][] dist;
    int[] rv = {1,-1,0,0};
    int [] cv = {0,0,1,-1};
    char [] d = "DURL".toCharArray();
    char [] di = "UDLR".toCharArray();
    int R, C, A;
    int KR, KC;
    int contR, contC;
    int startR, startC;
    String move;
    enum KirkStatus {
        CREATED,
        DISCOVERING,
        GOING_TO_CR,
        GOING_TO_EXIT
    }
    KirkStatus status;
    public Kirk(int R, int C, int A) {
        this.R = R;
        this.C = C;
        this.A = A;
        status = KirkStatus.CREATED;
        move = "";
        moving = new char[R][C];
        dist = new Integer[R][C];
        visited = new boolean[R][C];

    }

    void setStartR(int startR, int startC){
        this.startR = startR;
        this.startC = startC;
        status = KirkStatus.DISCOVERING;
    }

    void updateMat(char [][] mat, int KR, int KC){
        this.mat = mat;
        this.KR = KR;
        this.KC = KC;
    }

    String nextMove(){

        switch (status){
            case DISCOVERING:
                // if control room found and there is a path
                if(controlRoomFound() && bfs(contR, contC)){
                    // check that distance between control room and exit is lower than Alarm turns
                    if(dist[startR][startC] != null && dist[startR][startC] <= A) {
                        status = KirkStatus.GOING_TO_CR;
                        return nextMove();
                    }
                }

                // run a DFS optimal for all ?'s, keeping
                // keeping directions and distances
                // calculate decision
                find_question();
                break;
            case GOING_TO_CR:
                System.err.println("------------------------------------------------------------- CR");
                System.err.println(dist[startR][startC]+" vs "+A);
                // if reached control ROOM, go back to exit
                if(KR == contR && KC == contC){
                    status = KirkStatus.GOING_TO_EXIT;
                    //run disktra to Exit
                    bfs(startR, startC);
                }
                break;
            case GOING_TO_EXIT:

                break;
        }

        if(moving[KR][KC] == 'U')
            this.move = "UP";
        else if(moving[KR][KC] == 'D')
            this.move = "DOWN";
        else if(moving[KR][KC] == 'R')
            this.move = "RIGHT";
        else if(moving[KR][KC] == 'L')
            this.move = "LEFT";
        return this.move;
    }


    /***
     * BFS search for closest path.
     * @param endr
     * @param endc
     */
    boolean bfs(int endr, int endc){
        moving = new char[R][C];
        dist = new Integer[R][C];
        visited = new boolean[R][C];

        LinkedList<int[]> queued = new LinkedList<>();

        moving = new char[R][C];
        dist = new Integer[R][C];
        visited = new boolean[R][C];

        dist[endr][endc] = 0;
        int [] pos = new int[]{endr, endc};
        queued.add(pos);
        while (!queued.isEmpty()){
            int [] p = queued.poll();
            int r = p[0], c = p[1];
            //if already visited, nothing else to do
            if(visited[r][c]) continue;
            visited[r][c] = true;

            //go with neighbours
            for (int i=0; i< 4; i++) {
                if (r + rv[i] < 0 || R <= r + rv[i]) continue;
                if (c + cv[i] < 0 || C <= c + cv[i]) continue;
                // Not a wall or unknown
                int nr = r+rv[i], nc = c+cv[i];
                if(!visited[nr][nc] && mat[nr][nc] != '#' && mat[nr][nc] != '?'){
                    // better distance
                    if(dist[nr][nc] == null || dist[r][c] + 1 < dist[nr][nc]){
                        dist[nr][nc] = dist[r][c] + 1;
                        moving[nr][nc] = di[i]; // direction of movement
                    }
                    queued.addLast(new int[]{nr,nc});
                }
            }
        }
        //if no path found
        if(dist[KR][KC] == null)
            return false;
        return true;

    }

    /**
     * Depth search for the closest question mark
     */
    boolean find_question(){
        moving = new char[R][C];
        dist = new Integer[R][C];

        for(int r=0; r < R; r++){
            for(int c=0; c < C; c++){
                if(mat[r][c] == '?'){
                    dist[r][c] = 0;
                } else {
                    dist[r][c] = R*C;
                }
            }
        }
        boolean one = false;
        while(true) {
            boolean changed = false;
            for(int r=0; r < R; r++){
                for(int c=0; c < C; c++){
                    // get all surroundings / ignore walls and Control Room
                    if(mat[r][c] != '#' && mat[r][c] != 'C'){
                        for (int i=0; i< 4; i++) {
                            if( r+rv[i] < 0 || R <= r+rv[i] ) continue;
                            if( c+cv[i] < 0 || C <= c+cv[i] ) continue;

                            if (dist[r+rv[i]][c+cv[i]] + 1 < dist[r][c]) {
                                dist[r][c] = dist[r+rv[i]][c+cv[i]] + 1;
                                changed = true;
                                one = true;
                                moving[r][c] = d[i];
                            }
                        }
                    }

                }
            }
            if(!changed)
                break;
        }
        return one;
    }

    boolean controlRoomFound(){
        for (int i = 0; i < R; i++)
            for (int j = 0; j < C; j++)
                if(mat[i][j] == 'C') {
                    contR = i;
                    contC = j;
                    return true;
                }
        return false;
    }

    void print_moving(){
        for(char[] l : moving) {
            String s = "";
            for(char c: l)
                s += " "+ (c == 0 ? " ": c);
            System.err.println(s);
        }
    }

    void print_dist(){
        for(Integer[] l : dist) {
            String s = "";
            for (Integer i : l)
                s += i == null? "  " : String.format("%02d", i);
            System.err.println(s);
        }
    }

    void print_mat(){
        for(char[] l : mat) {
            String s = new String(l);
            System.err.println(s);
        }
    }

}