package build;

import adt.Edge;
import adt.Graph;
import adt.Node;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class makeGraph {

    //in remakeMesh, under makepolygons, have a makecity function
    //makecity will make a capital city and whatever amount of cities, making sure they aren't on lakes or oceans
    //it will then send the mesh here, and we return the graph adt
    //use a loop and run the shortest path, using the graph adt and the 2 cities as parameters
    //graph should have a function that takes in a x and y coordinate, and finds the node associated with it
    //use that to get the nodes that belong to each city, and send those in as parameters
    //we take the paths and add it to the list of segments

    public Graph run(Structs.Mesh.Builder mesh){
        Graph graph = new Graph();
        outer:
        for (Structs.Vertex v: mesh.getVerticesList()){
            //make sure polygon centroids aren't created as nodes
            for (Structs.Polygon p: mesh.getPolygonsList()){
                if (mesh.getVerticesList().indexOf(v) == p.getCentroidIdx()){
                    continue outer;
                }
            }
            Node n = new Node(v.getX(), v.getY()); //mapping node index to the same index as the vertex index
            for (Structs.Segment s: mesh.getSegmentsList()){
                if (s.getV1Idx() == mesh.getVerticesList().indexOf(v)){
                    n.registerNeighbour(s.getV2Idx());
                }
                else if (s.getV2Idx() == mesh.getVerticesList().indexOf(v)){
                    n.registerNeighbour(s.getV1Idx());
                }
            }
            graph.registerNode(n);
        }
        for (Node n: graph.getNodeList()){
            for (Integer i: n.getNeighbours()){
                Node neighbour = graph.getNode(i);
                Edge e = new Edge(n, neighbour);
                graph.registerEdge(e);
            }
        }
        return graph;
    }
}
