import java.util.*;

public class PerlinGenerator extends PerlinNoise2D {

    //We want to have three types of tiles for ground
    //Walls at the border that share the color of the ground
    //Random pickups
    //Wall formations that don't block the player
    //First of all, ground creation

    static int[][] perlinMap;
    public static int mapSize=32;
    //public int generatedSize = 0;
    //TODO add parameter for map generator size
    public PerlinGenerator() {
        perlinMap = new int[mapSize][mapSize];
    }
    public void generatePerlin() {
        perlinMap = new int[mapSize][mapSize];
        Random rand = new Random();
        double XOffset = rand.nextDouble(1000);
        double YOffset = rand.nextDouble(1000);
        double scale = 0.15;    //controls the smoothness of the transitions
        for(int i=0;i<mapSize;i++){
            for(int j=0;j<mapSize;j++){
                double noiseValue = noise((i * 1.01 + XOffset) * scale, (j* 1.03 + YOffset) * scale );
                noiseValue = (noiseValue +1)/2; //normalize the values
                if(noiseValue>0.5){
                    perlinMap[i][j] = 3;
                }
                else{
                    perlinMap[i][j] = 2;
                }

            }
        }

        applyRules(perlinMap);
    }

    public void applyRules(int[][] map){
        holeRules(map);
        borderRules(map);
    }

    public void borderRules(int[][] map){
        for(int j=0; j< mapSize;j++){
            map[0][j]= map[1][j] == 2 ? 0 : 1;
            map[mapSize-1][j]=map[mapSize-2][j] == 2 ? 0 : 1;
            map[j][0]=map[j][1] == 2 ? 0 : 1;
            map[j][mapSize-1]=map[j][mapSize-2] == 2 ? 0 : 1;
        }
        map[0][0] = map[1][1] == 2 ? 0 : 1;
        map[mapSize-1][0] = map[mapSize-2][1] == 2 ? 0 : 1;
        map[0][mapSize-1]=map[1][mapSize-2] == 2 ? 0 : 1;
        map[mapSize-1][mapSize-1]=map[mapSize-2][mapSize-2] == 2 ? 0 : 1;   //for corners
    }

    public void holeRules(int[][] map){
        for(int j=0;j< mapSize;j++){
            map[j][1] = map[j][2] == 2 ? 2 : 3;
            map[j][mapSize-2] = map[j][mapSize-3] == 2 ? 2 : 3;
            map[1][j] = map[2][j] == 2 ? 2 : 3;
            map[mapSize-2][j] = map[mapSize-3][j] == 2 ? 2 : 3;
        }

    }
//    public static void main(String[] args) {
//        PerlinGenerator pg = new PerlinGenerator();
//        Random rand = new Random();
//        for(int i=0;i<30;i++){
//            for(int j=0;j<30;j++){
//                double noiseValue = pg.noise(i * 1.01, j* 1.03);
//                noiseValue = (noiseValue+1)/2;
//                if(noiseValue<1 && noiseValue > 0.7){
//                    perlinMap[i][j] = 0;
//                }
//                else if(noiseValue>0.5 && noiseValue < 0.7){
//                    perlinMap[i][j] = 3;
//                }
//                else if(noiseValue>0.3 && noiseValue < 0.5){
//                    perlinMap[i][j] = 1;
//                }
//                else if(noiseValue < 0.3){
//                    perlinMap[i][j] = 2;
//                }
//
//                System.out.printf("%.2f ", noiseValue);
//            }
//            System.out.println();
//        }
//
//        for(int i=0;i<30;i++){
//            for(int j=0;j<30;j++){
//                System.out.print(perlinMap[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }

}
