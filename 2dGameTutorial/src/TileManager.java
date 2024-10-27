import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

/**Base class for everything tile related. From images
 * to building the actual map, this class is responsible
 * for making the ground the player walks on.
 */
public class TileManager extends PerlinGenerator {

    public GamePanel gp;
    //public PerlinGenerator peg;
    public tiles[] tile = new tiles[10];
    public int[][] mapTileNum;
    //boolean drawPath = true;
    public KeyHandler keyHandler;
    boolean structuresGenerated = false;


    /**Base constructor. Sets all images to the tiles and generates a perlin array
     * which and assigns it to the mapTileNum for later construction.
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;
        KeyHandler keyHandler = new KeyHandler(gp);
        this.keyHandler = keyHandler;
        tile = new tiles[10];
        generatePerlin();
        mapTileNum = perlinMap;
        getTileImage();
    }

    /**Assigns images to all types of tiles in an array. */

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

    /**Draw method for the tile manager. Draws a tile map
     * based on a supplied matrix generated using perlin noise
     * and with added rules. Every number corresponds to a 
     * different kind of tile.
     */

    public void draw(Graphics2D g2){
        if (keyHandler.OPressed) {
            keyHandler.OPressed = false;
            generatePerlin();
            structuresGenerated = !structuresGenerated;
            mapTileNum = perlinMap;
            structureRules(mapTileNum);
        }
        applyRules(mapTileNum);
        if (!structuresGenerated) { 
            structureRules(mapTileNum);
            structuresGenerated = true;
        }


        int worldRow = 0;
        int worldCol = 0;
        while (worldRow < mapSize && worldCol < mapSize) { 

            int tileNum = mapTileNum[worldCol][worldRow];    

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = (int) ((worldX - gp.player.worldX) + gp.player.screenX);  
            int screenY = (int) ((worldY - gp.player.worldY) + gp.player.screenY); 
            //it's + player.screen in order to offset the correct coordinate 
            //for the tile since the player is in the middle of the screen

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX 
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY 
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }
            worldCol++;
            if (worldCol == PerlinGenerator.mapSize) {
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
        //        } 
        //Draws the path the enemies will take when chasing the player. 
        //Left in code in case the corrector is interested in seeing it.

    }
}
