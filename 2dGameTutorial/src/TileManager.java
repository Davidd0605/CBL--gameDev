import Tiles.tiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    tiles[] tile = new tiles[10];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new tiles[10];

        getTileImage();

    }

    public void getTileImage() {

        try {
            tile[0] = new tiles();

            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("block_01.png")));


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);


    }



}
