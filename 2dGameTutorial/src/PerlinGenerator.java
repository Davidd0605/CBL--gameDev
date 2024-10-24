import java.util.*;

public class PerlinGenerator extends PerlinNoise2D {

    //We want to have three types of tiles for ground
    //Walls at the border that share the color of the ground
    //Random pickups
    //Wall formations that don't block the player
    //First of all, ground creation

    static int[][] perlinMap;
    public static int mapSize = 32;
    Random rand = new Random();

    //public int generatedSize = 0;
    //TODO add parameter for map generator size
    public PerlinGenerator() {
        perlinMap = new int[mapSize][mapSize];
    }

    public void generatePerlin() {
        perlinMap = new int[mapSize][mapSize];
        Random rand = new Random();
        //System.out.print("New perlin created");
        double XOffset = rand.nextDouble(1000);
        double YOffset = rand.nextDouble(1000);
        double scale = 0.15;    //controls the smoothness of the transitions
        //System.out.println(mapSize);
        for (int i = 0; i < mapSize; i++) {
            //System.out.println("Started entering for");
            for (int j = 0; j < mapSize; j++) {
                //System.out.println("Entered for");
                double noiseValue = noise((i * 1.01 + XOffset) * scale, (j * 1.03 + YOffset) * scale);
                noiseValue = (noiseValue + 1) / 2; //normalize the values
                if (noiseValue > 0.5) {
                    perlinMap[i][j] = 3;
                    //System.out.println("Creating map");
                } else {
                    perlinMap[i][j] = 2;
                }

            }
        }

        applyRules(perlinMap);
        structureRules(perlinMap);
    }

    public void applyRules(int[][] map) {
        holeRules(map);
        borderRules(map);
        //structureRules(map);
    }

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

    public void holeRules(int[][] map) {
        for (int j = 0; j < mapSize; j++) {
            map[j][1] = map[j][2] == 2 ? 2 : 3;
            map[j][mapSize - 2] = map[j][mapSize - 3] == 2 ? 2 : 3;
            map[1][j] = map[2][j] == 2 ? 2 : 3;
            map[mapSize - 2][j] = map[mapSize - 3][j] == 2 ? 2 : 3;
        }

    }

    public void generateSquare(int squareSize, int[][] map, int startPositionX, int startPositionY) {
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                if (checkBorders(map, startPositionX + i, startPositionY + j))
                    map[startPositionX + i][startPositionY + j] = map[startPositionX + i][startPositionY + j] == 2 ? 0 : 1;
            }
        }
    }

    public void generateVerticalLine(int lineSize, int[][] map, int startPositionX, int startPositionY) {
        if(startPositionY<2){
            startPositionY=2;
        }
        for (int j = 0; j < lineSize; j++) {
            if (checkBorders(map, startPositionX, startPositionY + j)) {
                map[startPositionX][startPositionY + j] = map[startPositionX][startPositionY + j] == 2 ? 0 : 1;
            }
        }
        if (map[startPositionX][startPositionY - 2] == 0 || map[startPositionX][startPositionY - 2] == 1) {
            map[startPositionX][startPositionY - 1] = map[startPositionX][startPositionY - 1] == 2 ? 0 : 1;
        }
    }

    public void generateHorizontalLine(int lineSize, int[][] map, int startPositionX, int startPositionY) {
        if(startPositionX < 2){
            startPositionX=2;
        }
        for (int i = 0; i < lineSize; i++) {
            if (checkBorders(map, startPositionX + i, startPositionY)) {
                map[startPositionX + i][startPositionY] = map[startPositionX + i][startPositionY] == 2 ? 0 : 1;
            }
        }
        if (map[startPositionX - 2][startPositionY] == 0 || map[startPositionX - 2][startPositionY] == 1) {
            map[startPositionX - 1][startPositionY] = map[startPositionX - 1][startPositionY] == 2 ? 0 : 1;
        }
    }

    public void generateLRight(int lineSize, int[][] map, int startPositionX, int startPositionY) {
        generateHorizontalLine(lineSize, map, startPositionX, startPositionY);
        generateVerticalLine(lineSize, map, startPositionX + lineSize-1, startPositionY + 1);
    }

    public void generateLLeft(int lineSize, int[][] map, int startPositionX, int startPositionY) {
        generateVerticalLine(lineSize, map, startPositionX, startPositionY);
        generateHorizontalLine(lineSize, map, startPositionX + 1, startPositionY + lineSize-1);
    }

    public void structureRules(int[][] map) {
        //int size = map.length;
        int maxStructures = mapSize / 8 + 3;
        int randomStructure;
        int maxStructureSize = mapSize / 4;


        for(int i = 0; i < maxStructures; i++){
            int xPos = rand.nextInt(mapSize-maxStructureSize-2)+2;
            int yPos = rand.nextInt(mapSize-maxStructureSize-2)+2;

            randomStructure = rand.nextInt(5);

            int structureSize = rand.nextInt(maxStructureSize+1)+1;
            generateStructure(xPos, yPos, map, randomStructure, structureSize);
        }
    }





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

    private boolean checkBorders(int[][] map, int Xposition, int Yposition){

        if(Xposition+2 >= mapSize || Yposition+2 >= mapSize){
            return false;
        }
        if(Xposition == 12 && Yposition == 9){
            return false;
        }

        return map[Xposition + 2][Yposition + 2] != 0 && map[Xposition + 2][Yposition + 2] != 1
                && map[Xposition + 2][Yposition] != 0 && map[Xposition + 2][Yposition] != 1
                && map[Xposition][Yposition + 2] != 0 && map[Xposition][Yposition + 2] != 1;
        //returns false if it touches any border blocks.
    }
}
