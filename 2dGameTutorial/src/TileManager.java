import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager extends PerlinGenerator {

    public GamePanel gp;
    //public PerlinGenerator peg;
    public tiles[] tile = new tiles[10];
    public int[][] mapTileNum;
    //boolean drawPath = true;
    public KeyHandler keyHandler;
    boolean structuresGenerated = false;



    public TileManager(GamePanel gp) {
        this.gp = gp;
        KeyHandler keyHandler = new KeyHandler(gp);
        this.keyHandler = keyHandler;
        tile = new tiles[10];
        //this.mapSize = gp.ui.mapSize;
        generatePerlin();
        //applyRules(perlinMap);
        mapTileNum = perlinMap;

        getTileImage();

        //loadMap("/maps/miniMap.txt");
    }

    public void getTileImage() {

        try {
            tile[0] = new tiles();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("Tiles/block_01.png"));
            tile[0].collision = true;

            tile[1] = new tiles();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("Tiles/crate_05.png"));
            tile[1].collision = true;

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
    public void draw(Graphics2D g2){
        //this.mapSize = gp.generatedSize;
        //System.out.println(gp.generatedSize);
        //System.out.println(mapSize);
        if(keyHandler.OPressed){
            keyHandler.OPressed = false;
//            g2.dispose();
            generatePerlin();
            structuresGenerated = !structuresGenerated;
//            applyRules(mapTileNum);
            mapTileNum = perlinMap;
            structureRules(mapTileNum);
        }
        applyRules(mapTileNum);
//        structureRules(mapTileNum);
//        structureRules(mapTileNum);
        if(!structuresGenerated){
            structureRules(mapTileNum);
            structuresGenerated = true;
        }
        //System.out.println("Applied rules");

        int worldRow = 0;
        int worldCol = 0;
        while(worldRow < mapSize && worldCol < mapSize){  //worldRow < gp.maxWorldRow && worldCol < gp.maxWorldCol

            int tileNum = mapTileNum[worldCol][worldRow];    //int tileNum = mapTileNum[worldCol][worldRow]

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = (int) ((worldX - gp.player.worldX) + gp.player.screenX);  //the tutorial did not need the (int)
            int screenY = (int) ((worldY - gp.player.worldY) + gp.player.screenY);  //it's +player.screen in order to offset the correct coordinate for the tile since the player is in the middle of the screen

            if(worldX +gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize< gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize> gp.player.worldY - gp.player.screenY && worldY -gp.tileSize< gp.player.worldY + gp.player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }

            worldCol++;


            if(worldCol == PerlinGenerator.mapSize){
                worldCol = 0;
                worldRow++;
            }
        }
//        if(drawPath){
//            g2.setColor(new Color(255, 0 , 0, 70));
//
//            for(int i =0; i <gp.pathfinder.pathList.size(); i++){
//                int worldX = gp.pathfinder.pathList.get(i).col * gp.tileSize;
//                int worldY = gp.pathfinder.pathList.get(i).row * gp.tileSize;
//                int screenX = (int) ((worldX - gp.player.worldX) + gp.player.screenX);
//                int screenY = (int) ((worldY - gp.player.worldY) + gp.player.screenY);
//
//                g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
//
//            }
//        } //Draws the path the enemies will take

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
