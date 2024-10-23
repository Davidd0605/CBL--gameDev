import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyHandler;
    public int FPS;
    public boolean shootButtonClicked = false;
    public PlayerThread thread;
    public boolean canAttack = true;
    public int attackCooldown = 0;
    public boolean attacking = false;
    private double initialSpeed;
    public boolean hitConnected = false;

    public int frameClock = 0;
    public Player(GamePanel gp , KeyHandler keyHandler) {

        this.gp = gp;
        this.keyHandler = keyHandler;
        setDefaultValues();
        thread = gp.playerThread;
        FPS = 50;
        hp = FPS;

        //Set param of player hit box
        hitBox = new Rectangle();
        hitBox.x = 8 ;
        hitBox.y = 16 ;
        hitBox.width = 32;
        hitBox.height = 32;
        screenX=(gp.tileSize * 8) - ((double) gp.tileSize / 2);
        screenY=((double) (gp.tileSize * gp.noRows) /2) - ((double) gp.tileSize /2);
        this.tag = "player";
    }
    public void setDefaultValues() {
        size = gp.tileSize;
        worldX = 12 * gp.tileSize;
        worldY = 9 * gp.tileSize;
        speed = 10;
        initialSpeed = speed;
        assignSprite();
    }

    public void assignSprite(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_right_2.png"));
            idle1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_idle_1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_idle_2.png"));
            atkDown1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_attack_down_1.png"));
            atkDown2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_attack_down_2.png"));
            atkLeft1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_attack_left_1.png"));
            atkLeft2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_attack_left_2.png"));
            atkRight1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_attack_right_1.png"));
            atkRight2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_attack_right_2.png"));
            atkUp1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_attack_up_1.png"));
            atkUp2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_attack_up_2.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void attacking() {
        spriteCounter++;
        if(spriteCounter == 2)
            gp.playSFX(1);
        if(spriteCounter <= 5) {
            spriteNum = 1;
        }
        if(spriteCounter > FPS / 5 && spriteCounter <= FPS) {
            spriteNum = 2;
        }
        if(spriteCounter == FPS/2 && hitConnected) {
            gp.playSFX(2);
            hitConnected = false;
        }
        if(spriteCounter > FPS * 1 / 2) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }

    }

    public void update() {
        if(FPS < 8) {
            FPS = 8;
        }
        if(hasIframes) {
            iFrameCounter++;
            if(iFrameCounter == gp.FPS) {
                hasIframes = false;
                iFrameCounter = 0;
            }
        }
        speed = 200 / FPS;
        initialSpeed = speed; // speed is i.p. to FPS
        if(keyHandler.shitfPressed) {
            speed *= 1.4;
        } else {
            speed = initialSpeed;
        }
        double xDirection = 0;
        double yDirection = 0;
        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        if(!collisionOn)
            gp.collisionChecker.checkEntity(this);


        if(attacking) {
            attacking();
        }
        else {
            if(keyHandler.downPressed) {
                direction = "down";
                yDirection = 1;
            }
            if(keyHandler.upPressed) {
                direction = "up";
                yDirection = -1;
            }
            if(keyHandler.rightPressed) {
                direction = "right";
                xDirection = 1;
            }
            if(keyHandler.leftPressed) {
                direction = "left";
                xDirection = -1;
            }
            if(xDirection == 0 && yDirection == 0) {
                direction = "idle";
            }
            if(keyHandler.atkPressed) {
                if(canAttack) {
                    canAttack = false;
                    attacking = true;
                    spriteCounter = 0;
                    spriteNum = 0;
                }
            } else {
                attacking = false;
            }
            spriteCounter++;
            if(spriteCounter > 10 * FPS / 50) {
                spriteNum = spriteNum == 2 ? 1 : 2;
                spriteCounter = 0;
            }
        }

        if(!collisionOn) {
            //Normalise the (xDirection, yDirection) vector, then multiply its length to be equal to speed
            double length = Math.sqrt(Math.pow(xDirection,2) + Math.pow(yDirection,2));
            xDirection = length == 0 ? 0 : xDirection / length;
            yDirection = length == 0 ? 0 : yDirection / length;
            worldX += xDirection * speed;
            worldY += yDirection * speed;

        }
        //TEMPORARY
        if(keyHandler.turnFPSDown && FPS > 8) {
            this.FPS--;
            System.out.println("FPS: " + this.FPS);
        }
        if(keyHandler.turnFPSUp && FPS < 60) {
            this.FPS++;
            System.out.println("FPS: " + this.FPS);
        }


    }
    public void draw(Graphics2D g2) {
        BufferedImage img = up1;
        int imgHeight = gp.tileSize;
        int imgWidth = gp.tileSize;
        double imgX = screenX;
        double imgY = screenY;
        //draw player sprite in the middle of the screen
        switch (direction) {
            case "idle":
                if(!attacking) {
                    if(spriteNum == 1)
                        img = idle1;
                    else
                        img = idle2;
                }
                else {
                    if(spriteNum == 1) {
                        img =idle1;
                    }
                    else {
                        img = idle2;
                    }
                }

                break;
            case "left":
                if(!attacking) {
                    if(spriteNum == 1)
                        img = left1;
                    else
                        img = left2;
                } else {
                    imgWidth *= 2;
                    imgX -= gp.tileSize;
                    if(spriteNum == 1) {
                        img = atkLeft1;
                    } else {

                        img = atkLeft2;
                    }
                }

                break;
            case "up":
                if(!attacking) {
                    if(spriteNum == 1)
                        img = up1;
                    else
                        img = up2;
                } else {
                    imgHeight *= 2;
                    imgY -= gp.tileSize;
                    if(spriteNum == 1) {
                        img = atkUp1;
                    } else {

                        img = atkUp2;
                    }
                }

                break;
            case "right":
                if(!attacking) {
                    if (spriteNum == 1)
                        img = right1;
                    else
                        img = right2;
                } else {
                    imgWidth *= 2;
                    if(spriteNum == 1) {
                        img = atkRight1;
                    } else {

                        img = atkRight2;
                    }
                }
                break;
            case "down":
                if(!attacking) {
                    if(spriteNum == 1)
                        img = down1;
                    else
                        img = down2;

                } else {
                    imgHeight *= 2;
                    if(spriteNum == 1) {
                        img = atkDown1;
                    }
                    else {
                        img = atkDown2;
                    }
                }
                break;

        }
        if(hasIframes) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
        }
        g2.drawImage(img, (int)imgX, (int)imgY, imgWidth, imgHeight, null);

    }
}
