package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.*;
import java.util.Map;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Position;

/**
 * Created by Joe on 10/21/2017.
 *
 * This class contains an implementation of the AStar path finding algorithm.
 */
public class AStar {

    private Graph map;

    //set of vertices that have been visited
    private HashSet<Graph.Vertex> visited;

    //set of vertices that are discovered but not yet evaluated
    private PriorityQueue<Graph.Vertex> discovered;

    private java.util.HashMap<Graph.Vertex,Integer> fScore,gScore;
    private HashMap<Graph.Vertex,Graph.Vertex> cameFrom;


    private Graph.Vertex start,goal;

    /**
     * Creates a new instance of this algorithm
     * @param map the map that this algorithm will find a path in
     * @param start the vertex that the path will start from
     * @param goal the vertex that a path will be found to
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public AStar(Graph map, Graph.Vertex start, Graph.Vertex goal){
        this.map = map;
        this.start = start;
        this.goal = goal;
        visited = new HashSet<>();
        cameFrom = new HashMap<>();
        fScore = new HashMap<>();
        gScore = new HashMap<>();
        discovered = new PriorityQueue<>(new FScoreCompare());
        initScores();
    }

    /**
     * Creates a new instance of this algorithm
     * @param map the map that a path will be found in
     * @param start the position that the path will start at
     * @param goal the position that a path will be found to
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public AStar(Graph map, Position start, Position goal) {
        this(map, map.getVertices().get(start), map.getVertices().get(goal));
    }


    /**
     * Runs the AStar algorithm and returns an ArrayList of positions as a path from the starting position to the end.
     * @return ArrayList of positions
     */
    public ArrayList<Position> findPath(){
        //add start to the discovered set
        discovered.add(start);
        gScore.put(start, 0);

        //heuristic cost estimate
        fScore.put(start, costHeuristic(start));

        while (!discovered.isEmpty()) {
            Graph.Vertex current = discovered.poll();

            //pathCells is found, return it
            if (current == goal) {
                return reconstructPath(cameFrom, current);
            }

            discovered.remove(current);
            visited.add(current);

            for (Graph.Edge n : current.getEdges()) {
                Graph.Vertex neighbor = n.to;
                if (visited.contains(neighbor)) {
                    continue;//skip this neighbor, it's already been visited
                }
                if (!discovered.contains(neighbor)) {
                    discovered.add(neighbor);
                }

                int tempGScore = gScore.get(current)+1;
                if (tempGScore >= gScore.get(neighbor)) {
                    continue;//this pathCells isn't better
                }

                cameFrom.put(neighbor, current);
                gScore.put(neighbor, tempGScore);

                fScore.put(neighbor, gScore.get(neighbor) + costHeuristic(neighbor));
            }
        }

        //if a pathCells doesn't exist, return null
        return null;
    }

    //gives an estimated cost from the current vertex to the goal
    //uses manhattan distance
    private int costHeuristic(Graph.Vertex current){
        int dx = Math.abs((int)current.getPosition().getX() - (int)goal.getPosition().getX());//get x and y or find some other way to do it
        int dy = Math.abs((int)current.getPosition().getY() - (int)goal.getPosition().getY());
        return 1 * (dx + dy);
    }

    private ArrayList<Position> reconstructPath(HashMap<Graph.Vertex,Graph.Vertex> cameFrom, Graph.Vertex current) {
        ArrayList<Position> path = new ArrayList<>();
        Graph.Vertex temp = current;
        path.add(current.getPosition());
        while (cameFrom.containsKey(temp)) {
            temp = cameFrom.get(temp);
            path.add(0,temp.getPosition());
        }
        return path;
    }

    //initializes the scores to max value
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initScores(){
        Map<Position,Graph.Vertex> vertices = map.getVertices();
        for (Map.Entry<Position,Graph.Vertex> vertexEntry : vertices.entrySet()) {
            fScore.put(vertexEntry.getValue(),Integer.MAX_VALUE);
            gScore.put(vertexEntry.getValue(), Integer.MAX_VALUE);
        }
    }



    private class FScoreCompare implements Comparator<Graph.Vertex>{

        @Override
        public int compare(Graph.Vertex v1, Graph.Vertex v2) {
            if (fScore.get(v1) > fScore.get(v2)) {
                return 1;
            }
            if (fScore.get(v1) < fScore.get(v2)) {
                return -1;
            }
            return 0;
        }
    }

}
