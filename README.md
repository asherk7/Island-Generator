# Assignment A2: Mesh Generator

  - Spencer McLean [mcleas13@mcmaster.ca]
  - Asher Khan [khanm406@mcmaster.ca]
  - Mehdi Syed [syedm55@mcmaster.ca]

## How to run the product

_This section needs to be edited to reflect how the user can interact with thefeature released in your project_

### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run:

```
mosser@azrael A2 % mvn install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, and a file named `visualizer.jar` in the `visualizer` one. 

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. The product takes one single argument (so far), the name of the file where the generated mesh will be stored as binary.

```
mosser@azrael A2 % cd generator 
mosser@azrael generator % java -jar generator.jar sample.mesh
mosser@azrael generator % ls -lh sample.mesh
-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 sample.mesh
mosser@azrael generator % 
```

### Visualizer

To visualize an existing mesh, go the the `visualizer` directory, and use `java -jar` to run the product. The product take two arguments (so far): the file containing the mesh, and the name of the file to store the visualization (as an SVG image).

```
mosser@azrael A2 % cd visualizer 
mosser@azrael visualizer % java -jar visualizer.jar ../generator/sample.mesh sample.svg

... (lots of debug information printed to stdout) ...

mosser@azrael visualizer % ls -lh sample.svg
-rw-r--r--  1 mosser  staff    56K 29 Jan 10:53 sample.svg
mosser@azrael visualizer %
```

To start debug mode, use '-X' at the end of the java command;
`java -jar visualizer.jar ../generator/sample.mesh sample.svg -X`
 
### Master Commands:

java -jar island/island.jar -i "input file name" -o "output file name" --mode "island mode" --shape "shape" --altitude "elevation" --lakes "number" --rivers "number" --aquifers "number" --soil "type" --biomes "type"  
### Island Command:
To check what commands user can run, type `java -jar island/island.jar -h`  

Current modes:
  - lagoon
  - island  
Current shapes:
  - circle
  - triangle
  - rectangle
  - star  
Current elevations:
  - volcano
  - hills
  - plains  
Current soils:
  - sand
  - drysoil
  - wetsoil  
current biomes:
  - tundra
  - tropical
  - canada  

### Irregular Lagoon island cmd:
java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 1500 -o img/irregular.mesh && java -jar island/island.jar -i img/irregular.mesh -o img/lagoon.mesh --mode lagoon && java -jar visualizer/visualizer.jar -i img/lagoon.mesh -o img/irregular.svg

### Irregular Circle Island with 3 different altitudes, 3 lakes, 10 rivers, 5 aquifers, wetsoil, tundra cmd:
java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 1500 -o img/irregular.mesh && java -jar island/island.jar -i img/irregular.mesh -o img/island.mesh --mode island --shape circle --altitude hills --lakes 3 --rivers 10 --aquifers 5 --soil wetsoil --biomes tundra && java -jar visualizer/visualizer.jar -i img/island.mesh -o img/irregular.svg

java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 1500 -o img/irregular.mesh && java -jar island/island.jar -i img/irregular.mesh -o img/island.mesh --mode island --shape circle --altitude volcano --lakes 3 --rivers 10 --aquifers 5 --soil wetsoil --biomes tundra && java -jar visualizer/visualizer.jar -i img/island.mesh -o img/irregular.svg

java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 1500 -o img/irregular.mesh && java -jar island/island.jar -i img/irregular.mesh -o img/island.mesh --mode island --shape circle --altitude plains --lakes 3 --rivers 10 --aquifers 5 --soil wetsoil --biomes tundra && java -jar visualizer/visualizer.jar -i img/island.mesh -o img/irregular.svg

# Regular Grid:
java -jar generator/generator.jar -k grid -h 1080 -w 1920 -s 20 -o img/grid.mesh && java -jar visualizer/visualizer.jar -i img/grid.mesh -o img/grid.svg

# Regular Grid - Debug Mode:
java -jar generator/generator.jar -k grid -h 1080 -w 1920 -s 20 -o img/grid.mesh && java -jar visualizer/visualizer.jar -i img/grid.mesh -o img/grid_debug.svg -x 

# Irregular Grid:
java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 1500 -o img/irregular.mesh && java -jar visualizer/visualizer.jar -i img/irregular.mesh -o img/irregular.svg

# Irregular Grid - Debug Mode:
java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 1500 -o img/irregular.mesh && java -jar visualizer/visualizer.jar -i img/irregular.mesh -o img/irregular_debug.svg -x

To viualize the SVG file:

  - Open it with a web browser
  - Convert it into something else with tool slike `rsvg-convert`

## How to contribute to the project

When you develop features and enrich the product, remember that you have first to `package` (as in `mvn package`) it so that the `jar` file is re-generated by maven.

## Backlog

### Definition of Done

A feature is done when it meets all conditions that is required of it, satisfies the user, and runs without any errors. 

### Product Backlog

| Id | Feature title                                                                     | Who? | Start    | End       | Status |
|:--:|-----------------------------------------------------------------------------------|------|----------|-----------|--------|
| F1 | draw segments between vertices to visualize the squares                           |   Asher/Mehdi | 02/08/23 | 02/09/23  | D      |
| F2 | Creating the Polygon data type, referencing the neighbours and creating centroids | Asher | 02/18/23 | 02/20/23  | D      |
| F3 | Creating the Mesh ADT, and implementing precision model                           | Spencer | 02/18/23      | 02/25/23  | D      |
| F4 | Implementing debug mode and command line features to activate debug mode          | Mehdi | 02/18/23      | 02/24/23  | D      |
| F5 | Implementing command line arguments for controlling mesh generation               | Mehdi | 02/24/23   | 03/02/23       |   D     |
| F6 | Computing Voronoi Diagram                                                         | Spencer | 02/24/23   | 03/02/23       |   D     |
| F7 | Implementing Lloyd Relaxtion                                                      | Mehdi | 02/24/23   | 03/02/23       |   D     |
| F8 | Computing neighbourhood relations using Delaunays Triangulation                   | Asher | 02/24/23   | 03/02/23       |   D     |
| F9 | colour in polygons using biome attributes                                         | Asher/Mehdi | 03/06/23   |   03/07/23     |    D   |
| F10 | Visualize island, lagoon, and ocean                                              | Asher/Mehdi | 03/06/23   |   03/07/23     |    D    |
| F11 | Create beaches by adding property to polygons                                    | Asher/Mehdi | 03/06/23   |   03/08/23     |    D    |
| F12 | Create option for different shaped islands                                       | Asher | 03/16/23   |    03/17/23    |    D    |
| F13 | Add elevation to each polygon                                                    | Mehdi/Asher | 03/17/23   |    03/21/23    |    D    |
| F14 | Create and visualize lakes                                                       | Spencer/Asher | 03/19/23   |    03/21/23    |    D    |
| F15 | Create and visualize rivers                                                      | Asher | 03/21/23   |    03/22/23    |    D    |
| F16 | Create aquifers                                                                  | Spencer | 03/22/23   |    03/22/23    |    D   |
| F17 | Merge intersecting rivers                                                        | Asher | 03/21/23   |    03/23/23    |    D   |
| F18 | implement soil absorption                                                        | Spencer/Asher | 03/22/23   |    03/24/23    |    D   |
| F19 | Create biomes for each polygon                                                   | Asher | 03/25/23   |    03/26/23    |    D   |
| F20 | Implement Whittaker diagrams                                                     | Asher | 03/25/23   |    03/26/23    |    D   |
| F21 | adding seeds for each unique island                                              | name | start   |    end    |    P   |
