import java.util.*;

/**This is the main class used for the random map generation
 * algorithm. It uses Perlin Noise to generate values which
 * are later used to choose which tiles will go where. There
 * are also bonus rules, used to add some order to the chaos
 * of the map.
 */
public class PerlinGenerator extends PerlinNoise2D {

    public static int[][] perlinMap;
    public static int mapSize = 32;
    Random rand = new Random();

    public PerlinGenerator() {
        perlinMap = new int[mapSize][mapSize];
    }
    /**This method generates the perlin map and assigns it numbers.
     * These numbers will later be used to create the actual map.
     * It uses offsets to avoid the problem Perlin noise has when
     * encountering integers and to add variety to the maps created.
     */

    public void generatePerlin() {
        perlinMap = new int[mapSize][mapSize];
        Random rand = new Random();
        double XOffset = rand.nextDouble(1000);
        double YOffset = rand.nextDouble(1000);
        double scale = 0.15;    //controls the smoothness of the transitions
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                double noiseValue = noise((i * 1.01 + XOffset) * scale,
                    (j * 1.03 + YOffset) * scale);

                noiseValue = (noiseValue + 1) / 2; //normalize the values
                if (noiseValue > 0.5) {
                    perlinMap[i][j] = 3;
                } else {
                    perlinMap[i][j] = 2;
                }

            }
        }

        applyRules(perlinMap);
        structureRules(perlinMap);
    }
    /**This method applies the base rules to the map
     * after generation.
     */

    public void applyRules(int[][] map) {
        holeRules(map);
        borderRules(map);
    }

    /**This method makes sure the map has walls surrounding it.
     * These walls change their color based on what tile they're
     * replacing to preserve the original coloring of the map.
     */

    public void borderRules(int[][] map) {
        for (int j = 0; j < mapSize; j++) {
            map[0][j] = map[1][j] == 2 ? 0 : 1;
            map[mapSize - 1][j] = map[mapSize - 2][j] == 2 ? 0 : 1;
            map[j][0] = map[j][1] == 2 ? 0 : 1;
            map[j][mapSize - 1] = map[j][mapSize - 2] == 2 ? 0 : 1;
        }
        map[0][0] = map[1][1] == 2 ? 0 : 1;
        map[mapSize - 1][0] = map[mapSize - 2][1] == 2 ? 0 : 1;
        map[0][mapSize - 1] = map[1][mapSize - 2] == 2 ? 0 : 1;
        map[mapSize - 1][mapSize - 1] = map[mapSize - 2][mapSize - 2] == 2 ? 0 : 1;   //for corners
    }
    /**This method gets rid of any 'holes' in the map.
     * By holes, I mean very small formations of a kind of 
     * tile in a biome dominated by the other kind. This hole
     * rule is actually applied at the border, since perlin
     * generation would not have this problem anywhere else.
     */

    public void holeRules(int[][] map) {
        for (int j = 0; j < mapSize; j++) {
            map[j][1] = map[j][2] == 2 ? 2 : 3;
            map[j][mapSize - 2] = map[j][mapSize - 3] == 2 ? 2 : 3;
            map[1][j] = map[2][j] == 2 ? 2 : 3;
            map[mapSize - 2][j] = map[mapSize - 3][j] == 2 ? 2 : 3;
        }

    }

    /**This method generates a square like structure of walls. It has the same principle
     * of preserving the color of the thile it replaces.
     */
    public void generateSquare(int squareSize, int[][] map, int startPositionX, int startPositionY) {
        for (int i = 0; i < (squareSize / 2) + 1; i++) {
            for (int j = 0; j < (squareSize / 2) + 1; j++) {
                if (checkBorders(map, startPositionX + i, startPositionY + j)){
                    map[startPositionX + i][startPositionY + j] = 
                        map[startPositionX + i][startPositionY + j] == 2 ? 0 : 1;
                }
            }
        }
    }
    /**Same as the generate square method, but for a vertical line. */

    public void generateVerticalLine(int lineSize, int[][] map, int startPositionX, int startPositionY) {
        if (startPositionY < 2) {
            startPositionY = 2;
        }
        for (int j = 0; j < lineSize; j++) {
            if (checkBorders(map, startPositionX, startPositionY + j)) {
                map[startPositionX][startPositionY + j] = 
                    map[startPositionX][startPositionY + j] == 2 ? 0 : 1;
            }
        }
        if (map[startPositionX][startPositionY - 2] == 0 
            || map[startPositionX][startPositionY - 2] == 1) {

            map[startPositionX][startPositionY - 1] = 
                map[startPositionX][startPositionY - 1] == 2 ? 0 : 1;

        }
    }

    /**Now for a horizontal line. */

    public void generateHorizontalLine(int lineSize, int[][] map, int startPositionX, int startPositionY) {
        if (startPositionX < 2) {
            startPositionX = 2;
        }
        for (int i = 0; i < lineSize; i++) {
            if (checkBorders(map, startPositionX + i, startPositionY)) {
                map[startPositionX + i][startPositionY] = 
                    map[startPositionX + i][startPositionY] == 2 ? 0 : 1;
            }
        }
        if (map[startPositionX - 2][startPositionY] == 0 
            || map[startPositionX - 2][startPositionY] == 1) {

            map[startPositionX - 1][startPositionY] = 
                map[startPositionX - 1][startPositionY] == 2 ? 0 : 1;

        }
    }
    /**This method creates and L structure by using the methods from before
     * to generate a vertical and a horizontal line.
     */

    public void generateLRight(int lineSize, int[][] map, int startPositionX, int startPositionY) {
        generateHorizontalLine(lineSize, map, startPositionX, startPositionY);
        generateVerticalLine(lineSize, map, startPositionX + lineSize - 1, startPositionY + 1);
    }
    /**The same as before, but now it points to the left. */

    public void generateLLeft(int lineSize, int[][] map, int startPositionX, int startPositionY) {
        generateVerticalLine(lineSize, map, startPositionX, startPositionY);
        generateHorizontalLine(lineSize, map, startPositionX + 1, startPositionY + lineSize - 1);
    }

    /**This method places structures on the map at random based on the map size. */

    public void structureRules(int[][] map) {
        //int size = map.length;
        int maxStructures = mapSize / 8 + 3;
        int randomStructure;
        int maxStructureSize = mapSize / 4;


        for (int i = 0; i < maxStructures; i++){
            int xPos = rand.nextInt(mapSize - maxStructureSize - 2) + 2;
            int yPos = rand.nextInt(mapSize - maxStructureSize - 2) + 2;

            randomStructure = rand.nextInt(5);

            int structureSize = rand.nextInt(maxStructureSize + 1) + 1;
            generateStructure(xPos, yPos, map, randomStructure, structureSize);
        }
    }


    /**This method organizez all the structure methods in one. */


    private void generateStructure( int xPos, int yPos,int[][] map, int randomStructure, int structureSize) {
        switch (randomStructure) {
            case 0:
                generateSquare(structureSize, map, xPos, yPos);
                break;
            case 1:
                generateVerticalLine(structureSize, map, xPos, yPos);
                break;
            case 2:
                generateHorizontalLine(structureSize, map, xPos, yPos);
                break;
            case 3:
                generateLRight(structureSize, map, xPos, yPos);
                break;
            case 4:
                generateLLeft(structureSize, map, xPos, yPos);
        }
    }

    /**
     * This method makes sure the structures don't spawn where the player is
     * or where they would connect to the border walls in order to avoid
     * impossible paths.
     */
    private boolean checkBorders(int[][] map, int Xposition, int Yposition){

        if (Xposition + 2 >= mapSize || Yposition + 2 >= mapSize) {
            return false;
        }
        if (Xposition == 12 && Yposition == 9) {
            return false;
        }

        return map[Xposition + 2][Yposition + 2] != 0 && map[Xposition + 2][Yposition + 2] != 1
                && map[Xposition + 2][Yposition] != 0 && map[Xposition + 2][Yposition] != 1
                && map[Xposition][Yposition + 2] != 0 && map[Xposition][Yposition + 2] != 1;
        //returns false if it touches any border blocks.
    }
}
