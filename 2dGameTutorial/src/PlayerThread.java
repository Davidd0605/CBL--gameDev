/**The class for the player thread. Needed since the player
 * runs on a different FPS than the rest of the game.
 */

public class PlayerThread implements Runnable {
    Thread thread;
    Player player;
    GamePanel gamePanel;

    /**The base constructor linking to player and gamePanel. */

    PlayerThread(Player player, GamePanel gamePanel) {
        this.player = player;
        this.gamePanel = gamePanel;
    }

    /**Method initialising a new game thread for the player. */

    public void startGameThread() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / player.FPS; //Interval in nanoseconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (thread.isAlive()) {
            update();
            gamePanel.repaint();
            drawInterval = (double) 1000000000 / player.FPS;
            // try and catch works as follows: if the program runs 
            //intro any errors in the try flag, then it will cll t
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    /**This method updates game data in the 
     * play state of the game. */
    
    public void update() {
        if (gamePanel.gameState == gamePanel.playState) {
            player.update();
        }
    }
}
