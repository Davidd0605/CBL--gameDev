import java.util.*;

public class PerlinGenerator extends PerlinNoise2D {

    //We want to have three types of tiles for ground
    //Walls at the border that share the color of the ground
    //Random pickups
    //Wall formations that don't block the player
    //First of all, ground creation

    static int[][] perlinMap;
    public PerlinGenerator() {
        perlinMap = new int[30][30];
    }
    public static void main(String[] args) {
        PerlinGenerator pg = new PerlinGenerator();
        Random rand = new Random();
        for(int i=0;i<30;i++){
            for(int j=0;j<30;j++){
                double noiseValue = pg.noise(i * 1.01, j* 1.03);
                noiseValue = (noiseValue+1)/2;
                if(noiseValue<1 && noiseValue > 0.7){
                    perlinMap[i][j] = 0;
                }
                else if(noiseValue>0.5 && noiseValue < 0.7){
                    perlinMap[i][j] = 3;
                }
                else if(noiseValue>0.3 && noiseValue < 0.5){
                    perlinMap[i][j] = 1;
                }
                else if(noiseValue < 0.3){
                    perlinMap[i][j] = 2;
                }

                System.out.printf("%.2f ", noiseValue);
            }
            System.out.println();
        }

        for(int i=0;i<30;i++){
            for(int j=0;j<30;j++){
                System.out.print(perlinMap[i][j] + " ");
            }
            System.out.println();
        }
    }

}
