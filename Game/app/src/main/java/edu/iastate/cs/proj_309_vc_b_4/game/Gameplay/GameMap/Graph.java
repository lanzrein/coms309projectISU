package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.util.SimpleArrayMap;
import android.util.ArrayMap;

import java.lang.reflect.Array;
import java.util.HashSet;

import javax.sql.ConnectionPoolDataSource;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Position;

/**
 * Created by Joe on 10/19/2017.
 *
 * This is an implementation of directed weighted Graph data structure that uses ArrayMaps tos store vertices
 * and hash sets to store edges.
 * This graph class is used to represent the path in the map and find the optimal route via the AStar algorithm.
 */
public class Graph {
    private ArrayMap<Position, Vertex> vertices;

    /**
     * Creates new graph
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Graph() {
        vertices = new ArrayMap<>();
    }


    /**
     * adds edge to the graph
     * @param from
     * @param to
     * @param weight
     */
    public void addEdge(Position from, Position to, int weight){
        if (vertices.get(from) == null) {
            vertices.put(from, new Vertex(from));
        }
        if (vertices.get(to) == null) {
            vertices.put(to, new Vertex(to));
        }
        vertices.get(from).edges.add(new Edge(vertices.get(from), vertices.get(to), weight));
    }

    /**
     * Returns true if the given vertex is in this graph
     * @param v the given vertex
     * @return true if vertex is in this graph, false if not
     */
    public boolean contains(Vertex v) {
        if (v == null)
            return false;
        if (vertices.get(v.position) == null) {
            return false;
        }
        return true;
    }

    /**
     * Returns the vertices in this graph
     * @return ArrayMap of Positions and Vertices
     */
    public ArrayMap<Position,Vertex> getVertices() {
        return vertices;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i) != null) {
                s += i + ": " + vertices.get(i).toString() + "\n";
            }
        }
        return s;
    }

    /**
     * This class represents an edge in this graph
     */
    public class Edge {
        protected int weight;
        protected Vertex from;
        protected Vertex to;

        protected Edge(Vertex from, Vertex to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        /**
         * Returns the weight of this edge
         * @return weight of this edge
         */
        public int getWeight() {
            return weight;
        }

        /**
         * Returns the Position this edge came from
         * @return instance of Position
         */
        public Position getFrom() {
            return from.position;
        }

        /**
         * Returns the Position this edge is going to
         * @return instance of Position
         */
        public Position getTo() {
            return to.position;
        }
    }

    /**
     * The following class represents a vertex. Vertices in this graph all have a Position associated with them.
     */
    public class Vertex {
        protected HashSet<Edge> edges;

        //cell that this vertex represents
        protected Position position;

        protected Vertex(Position position) {
            this.position = position;
            edges = new HashSet<>();
        }

        /**
         * Returns the edges from this vertex
         * @return hashset of edges
         */
        public HashSet<Edge> getEdges() {
            return edges;
        }

        public int hashCode() {
            return position.hashCode();
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            }
            Vertex t = (Vertex) o;
            return position.equals(((Vertex) o).position);
        }

        public String toString() {
            String s = "";
            for (Edge e : edges) {
                s += "["+e.from.position + "," + e.to.position + "] ";
            }
            return s;
        }

        /**
         * Returns the Position associated with this vertex
         * @return instance of position
         */
        public Position getPosition(){
            return position;
        }

    }
}
