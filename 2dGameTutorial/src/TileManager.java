import Tiles.tiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    tiles[] tile = new tiles[10];
    int[][] mapTileNum;


    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new tiles[10];
        mapTileNum = new int[gp.noColumns][gp.noRows];

        getTileImage();
        loadMap("/maps/testMap.txt");

    }

    public void getTileImage() {

        try {
            tile[0] = new tiles();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("Tiles/block_01.png"));
            tile[0].collision = true;

            tile[1] = new tiles();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("Tiles/ground_01.png"));

            tile[2] = new tiles();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("Tiles/crate_05.png"));
            tile[2].collision = true;


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filepath){
        try{
            InputStream is = getClass().getResourceAsStream(filepath);   //importing the text file
            BufferedReader br = new BufferedReader(new InputStreamReader(is));  //read the text file

            int lineCounter = 0;
            while(br.ready()){
                String line = br.readLine();    //read a single line
                String[] numbers = line.split(" ");

                for(int col = 0; col < numbers.length; col++){
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][lineCounter] = num;
                }

                lineCounter++;
            }
            br.close();

        } catch(Exception e){
            //TODO HANDLE EXCEPTION
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        int x =0;
        int y =0;
        int row = 0;
        int col = 0;
        while(row < gp.noRows && col < gp.noColumns){

            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if(col == 16){
                col = 0;
                x=0;
                row++;
                y +=gp.tileSize;
            }
        }

    }
//    public void drawFullMap(Graphics2D g2) {
//        int x =0;
//        int y =0;
//        int row = 0;
//        int col = 0;
//        while(row < gp.noRows && col < gp.noColumns){
//
//            g2.drawImage(tile[1].image, x, y, gp.tileSize, gp.tileSize, null);
//            col++;
//            x += gp.tileSize;
//
//            if(col == 16){
//                col = 0;
//                x=0;
//                row++;
//                y +=gp.tileSize;
//            }
//        }
//    }




}
