//package Tile;

import java.awt.image.BufferedImage;

/**Base class for tiles. The main object required to build the map. 
 * Their size is 48x48 pixels.
 */

public class tiles {
    public BufferedImage image;
    public boolean collision = false;
    public boolean interactable = false;
}
