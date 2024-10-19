import java.util.*;

public class PerlinGenerator extends PerlinNoise2D {

    //We want to have three types of tiles for ground
    //Walls at the border that share the color of the ground
    //Random pickups
    //Wall formations that don't block the player
    //First of all, ground creation

    static int[][] perlinMap;
    //int mapSize;
    //TODO add parameter for map generator size
    public PerlinGenerator(int width, int length) {
        perlinMap = new int[width][length];
    }
    public void generatePerlin(int width, int length) {
        Random rand = new Random();
        System.out.print("New perlin created");
        double XOffset = rand.nextDouble(1000);
        double YOffset = rand.nextDouble(1000);
        double scale = 0.15;    //controls the smoothness of the transitions
        for(int i=0;i<width;i++){
            for(int j=0;j<length;j++){
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
        for(int j=0;j<24;j++){
            map[0][j]= map[1][j] == 2 ? 0 : 1;
            map[23][j]=map[22][j] == 2 ? 0 : 1;
            map[j][0]=map[j][1] == 2 ? 0 : 1;
            map[j][23]=map[j][22] == 2 ? 0 : 1;
        }
        map[0][0] = map[1][1] == 2 ? 0 : 1;
        map[23][0] = map[22][1] == 2 ? 0 : 1;
        map[0][23]=map[1][22] == 2 ? 0 : 1;
        map[23][23]=map[22][22] == 2 ? 0 : 1;   //for corners
    }

    public void holeRules(int[][] map){
        for(int j=0;j<24;j++){
            map[j][1] = map[j][2] == 2 ? 2 : 3;
            map[j][22] = map[j][21] == 2 ? 2 : 3;
            map[1][j] = map[2][j] == 2 ? 2 : 3;
            map[22][j] = map[21][j] == 2 ? 2 : 3;
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
