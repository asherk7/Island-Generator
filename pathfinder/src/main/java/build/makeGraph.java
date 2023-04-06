package build;

import adt.Graph;
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
        for (Structs.Polygon p: mesh.getPolygonsList()){
            
        }
        return graph;
    }
    //take in mesh
    //iterate through mesh polygons, take the centroid and the neighbours and turn it into a node
    //iterate through set of nodes, and each nodes neighbours and make edge
    //set of edges shouldn't have duplicates if its a set of 2 nodes
    //an edge can be a set of 2 nodes(centroids) with an attribute of weight (make a function to get weight)
    //take these nodes and edges and add them to the graph adt
    //for path, just use dijsktra algo
    //return the path as an ordered list of edges(2 nodes)
    //convert this path to a list of segments and send it back (just like how moser did with polygons)
    //give each segment a property indicating that it's a path
}
