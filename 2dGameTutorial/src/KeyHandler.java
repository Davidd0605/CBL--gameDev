import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
public class KeyHandler implements KeyListener {
    public GamePanel gamePanel;
    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    boolean shiftPressed = false;
    boolean upPressed, downPressed, leftPressed, rightPressed, turnFPSUp, turnFPSDown, atkPressed, escPressed = false, OPressed = false;
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_SHIFT) {
            shiftPressed = true;
        }
        //Title State
        if(gamePanel.gameState == gamePanel.titleState){
            if(key == KeyEvent.VK_A && gamePanel.ui.optionScroll == 1 && gamePanel.ui.showSize){
                gamePanel.ui.mapSize--;
            }
            if(key == KeyEvent.VK_D && gamePanel.ui.optionScroll== 1 && gamePanel.ui.showSize){
                gamePanel.ui.mapSize++;
            }
            if(key == KeyEvent.VK_SPACE){
                if(gamePanel.ui.optionScroll == 0){
                    gamePanel.gameState = gamePanel.playState;
                }
                if(gamePanel.ui.optionScroll == 1){
                    gamePanel.ui.showSize = !gamePanel.ui.showSize;
                }
                if(gamePanel.ui.optionScroll == 2){
                    System.exit(0);
                }

            }
        }

        //Play State
        if(key == KeyEvent.VK_SPACE) {
            atkPressed = true;
            if(gamePanel.gameState == gamePanel.pauseState) {
                switch (gamePanel.ui.optionScreen) {
                    case 0:
                        switch(gamePanel.ui.optionScroll) {
                            case 1:
                                gamePanel.restartGame();
                                break;
                            case 2:
                                gamePanel.ui.optionScroll = 0;
                                gamePanel.ui.optionScreen = 1;
                                break;
                            case 3:
                                gamePanel.ui.optionScroll = 0;
                                gamePanel.ui.optionScreen = 2;
                                break;
                            case 4:
                                gamePanel.gameState = gamePanel.playState;
                                break;
                            default: break;
                        }
                        break;
                    case 1:
                        switch(gamePanel.ui.optionScroll) {
                            case 1:
                                if(gamePanel.ui.musicVolumeInd == 0)
                                    gamePanel.ui.musicVolumeInd = 100;
                                else {
                                    gamePanel.ui.musicVolumeInd = 0;
                                }
                                break;
                            case 2:
                                if(gamePanel.ui.SFXVolumeInd == 0)
                                    gamePanel.ui.SFXVolumeInd = 100;
                                else {
                                    gamePanel.ui.SFXVolumeInd = 0;
                                }
                                gamePanel.SFX.volumeInd = gamePanel.ui.SFXVolumeInd;
                                break;
                            case 3:
                                gamePanel.ui.optionScroll = 0;
                                gamePanel.ui.optionScreen = 0;
                                break;
                            default: break;
                        }
                        break;
                    case 2:
                        switch(gamePanel.ui.optionScroll) {
                            case 1:
                                System.exit(0);
                                break;
                            case 2:
                                gamePanel.ui.optionScroll = 0;
                                gamePanel.ui.optionScreen = 0;
                                break;
                        }
                        break;
                }
            }
            if(gamePanel.gameState == gamePanel.overState) {
                switch (gamePanel.ui.optionScroll) {
                    case 1:
                        gamePanel.restartGame();
                        break;
                    case 2:
                        System.exit(0);
                        break;
                }
            }
            if(gamePanel.gameState == gamePanel.winState) {
                switch (gamePanel.ui.optionScroll) {
                    case 1:
                        gamePanel.waveNumber = 6;
                        gamePanel.gameState = gamePanel.playState;
                        break;
                    case 2:
                        System.exit(0);
                        break;
                }
            }
        }

        //TEMPORARY FPS CONTROL FOR GAME TESTING
        if(key == KeyEvent.VK_R) {
            turnFPSUp = true;
        }
        if(key == KeyEvent.VK_T) {
            turnFPSDown = true;
        }
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            upPressed = true;
            if(gamePanel.gameState == gamePanel.titleState) {
                gamePanel.playSFX(0);
                gamePanel.ui.optionScroll --;
                if(gamePanel.ui.optionScroll < gamePanel.ui.minOptionScroll)
                    gamePanel.ui.optionScroll = gamePanel.ui.maxOptionScroll;
            }
            if(gamePanel.gameState == gamePanel.pauseState) {
                gamePanel.playSFX(0);
                switch (gamePanel.ui.optionScreen) {
                    case 0, 1, 2:
                        gamePanel.ui.optionScroll --;
                        if(gamePanel.ui.optionScroll < gamePanel.ui.minOptionScroll)
                            gamePanel.ui.optionScroll = gamePanel.ui.maxOptionScroll;
                        break;
                    default:
                        break;
                }

            }
        }
        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            downPressed = true;
            if(gamePanel.gameState == gamePanel.titleState) {
                gamePanel.playSFX(0);
                gamePanel.ui.optionScroll ++;
                if(gamePanel.ui.optionScroll > gamePanel.ui.maxOptionScroll)
                    gamePanel.ui.optionScroll = gamePanel.ui.minOptionScroll;
            }
            if(gamePanel.gameState == gamePanel.pauseState) {
                gamePanel.playSFX(0);
                switch (gamePanel.ui.optionScreen) {
                    case 0, 1, 2:
                        gamePanel.ui.optionScroll ++;
                        if(gamePanel.ui.optionScroll > gamePanel.ui.maxOptionScroll)
                            gamePanel.ui.optionScroll = gamePanel.ui.minOptionScroll;
                        break;

                    default:
                        break;
                }
            }
        }
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            leftPressed = true;
            if(gamePanel.gameState == gamePanel.pauseState) {
                if(gamePanel.ui.optionScreen == 1) {
                    if(gamePanel.ui.optionScroll == 1 && gamePanel.ui.musicVolumeInd > 0)
                        gamePanel.ui.musicVolumeInd--;
                    if(gamePanel.ui.optionScroll == 2  && gamePanel.ui.SFXVolumeInd > 0) {
                        gamePanel.ui.SFXVolumeInd--;
                        gamePanel.SFX.volumeInd = gamePanel.ui.SFXVolumeInd;
                    }
                }
            }
            if(gamePanel.gameState == gamePanel.overState) {
                gamePanel.playSFX(0);
                gamePanel.ui.optionScroll --;
                if(gamePanel.ui.optionScroll < gamePanel.ui.minOptionScroll)
                    gamePanel.ui.optionScroll = gamePanel.ui.maxOptionScroll;
            }
            if(gamePanel.gameState == gamePanel.winState) {
                gamePanel.playSFX(0);
                gamePanel.ui.optionScroll --;
                if(gamePanel.ui.optionScroll < gamePanel.ui.minOptionScroll)
                    gamePanel.ui.optionScroll = gamePanel.ui.maxOptionScroll;
            }
        }
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            rightPressed = true;
            if(gamePanel.gameState == gamePanel.pauseState) {
                if(gamePanel.ui.optionScreen == 1) {
                    if(gamePanel.ui.optionScroll == 1 && gamePanel.ui.musicVolumeInd < 100)
                        gamePanel.ui.musicVolumeInd++;
                    if(gamePanel.ui.optionScroll == 2  && gamePanel.ui.SFXVolumeInd < 100) {
                        gamePanel.ui.SFXVolumeInd++;
                        gamePanel.SFX.volumeInd = gamePanel.ui.SFXVolumeInd;
                    }
                }
            }
            if(gamePanel.gameState == gamePanel.overState) {
                gamePanel.playSFX(0);
                gamePanel.ui.optionScroll ++;
                if(gamePanel.ui.optionScroll > gamePanel.ui.maxOptionScroll)
                    gamePanel.ui.optionScroll = gamePanel.ui.minOptionScroll;
            }
            if(gamePanel.gameState == gamePanel.winState) {
                gamePanel.playSFX(0);
                gamePanel.ui.optionScroll ++;
                if(gamePanel.ui.optionScroll > gamePanel.ui.maxOptionScroll)
                    gamePanel.ui.optionScroll = gamePanel.ui.minOptionScroll;
            }
        }
        if(key == KeyEvent.VK_ESCAPE) {

            switch (gamePanel.gameState) {
                case 0:
                    gamePanel.gameState = gamePanel.pauseState;
                    gamePanel.ui.optionScreen = 0;
                    gamePanel.playSFX(0);
                    break;
                case 1:
                    gamePanel.gameState = gamePanel.playState;
                    gamePanel.playSFX(0);
                    break;
            }
        }

        //TODO REMOVE
        if(key == KeyEvent.VK_O){
            gamePanel.gameState = gamePanel.winState;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }
        if(key == KeyEvent.VK_SPACE) {
            atkPressed = false;
        }
        if(key == KeyEvent.VK_R) {
            turnFPSUp = false;
        }
        if(key == KeyEvent.VK_T) {
            turnFPSDown = false;
        }
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }
}
