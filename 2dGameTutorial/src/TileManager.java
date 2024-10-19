

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager extends PerlinGenerator {

    public GamePanel gp;
    public tiles[] tile = new tiles[10];
    public int[][] mapTileNum;
    public KeyHandler keyHandler;



    public TileManager(GamePanel gp) {
        this.gp = gp;
        KeyHandler keyHandler = new KeyHandler(gp);
        this.keyHandler = keyHandler;

        tile = new tiles[10];
        //mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        mapTileNum = new int[24][24];
        generatePerlin();
        getTileImage();
        //loadMap("/maps/miniMap.txt");


    }

    public void getTileImage() {

        try {
            tile[0] = new tiles();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("Tiles/block_01.png"));
            tile[0].collision = false;

            tile[1] = new tiles();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("Tiles/crate_05.png"));
            tile[1].collision = false;

            tile[2] = new tiles();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("Tiles/ground_01.png"));

            tile[3] = new tiles();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("Tiles/ground_02.png"));

            tile[4] = new tiles();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("Tiles/block_07.png"));

            tile[5] = new tiles();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("Tiles/ground_06.png"));


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

    public void generateMap(){

    }
    public void draw(Graphics2D g2){
        if(keyHandler.OPressed){
            keyHandler.OPressed = false;
//            g2.dispose();
            generatePerlin();
        }
        int worldRow = 0;
        int worldCol = 0;
        while(worldRow < 24 && worldCol < 24){  //worldRow < gp.maxWorldRow && worldCol < gp.maxWorldCol

            int tileNum = perlinMap[worldCol][worldRow];    //int tileNum = mapTileNum[worldCol][worldRow]

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = (int) ((worldX - gp.player.worldX) + gp.player.screenX);  //the tutorial did not need the (int)
            int screenY = (int) ((worldY - gp.player.worldY) + gp.player.screenY);  //it's +player.screen in order to offset the correct coordinate for the tile since the player is in the middle of the screen

            if(worldX +gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize< gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize> gp.player.worldY - gp.player.screenY && worldY -gp.tileSize< gp.player.worldY + gp.player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }

            worldCol++;


            if(worldCol == gp.maxWorldCol){
                worldCol = 0;

                worldRow++;

            }
        }

    }
//    public void drawFullMap(Graphics2D g2) {  //this one just draws a map full of one kind of tile
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
