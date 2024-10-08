public class EnemyThread implements Runnable {
    Enemy enemy;
    Thread thread;
    GamePanel gamePanel;
    private final int FPS = 45;
    public EnemyThread(Enemy enemy, GamePanel gamePanel) {
        this.enemy = enemy;
        this.gamePanel = gamePanel;
    }
    public void startGameThread() {
        thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS; //Interval in nanoseconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(thread.isAlive()) {
            update();
            gamePanel.repaint();

            // try and catch works as follows: if the program runs intro any errors in the try flag, then it will cll t
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long)remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    void update() {
        enemy.update();
    }
}

