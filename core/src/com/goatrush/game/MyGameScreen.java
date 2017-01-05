package com.goatrush.game;

// Imports classes from badlogic
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

// The actual screen where the user plays the game
public class MyGameScreen implements Screen{
	
	// Variable/object declarations
    final GoatRush game;
    private SpriteBatch batch;
    private Texture texture;
    
    // Font object declaration
    BitmapFont font;
	
	TextureRegion currentFrame;
	TextureRegion[] standFrames;
	
	// Textures declaration 
	Texture standSheet;
	Texture tree;
	Texture grass;
	Texture rush;
	Texture speedup;
	
	// Sprites declaration
	Sprite speedupSprite;
	Sprite rushSprite;
	Sprite grassSprite;
	
	// animation for the goat sprite sheet
	Animation standAnimation;
	
	// spritebatch declaration
	SpriteBatch spriteBatch;
	SpriteBatch dying;
	SpriteBatch grassBkg;
	
	// sound declaration
	Sound intense;
	
	// rectangle for the goat's hitbox
	Rectangle goat;
	
	// array of trees
	Array<Rectangle> trees;
	
	long lastTreeTime;
	long death;
	
	// variable  declarations for counters and score
	int score;
	int treesGathered;
	int treesPassed = 0;
	double counter = 1;
	
	// this is for the sprite batch. there are 4 columns and 2 rows of goats in the batch
	private static final int    FRAME_COLS = 4;    
    private static final int    FRAME_ROWS = 2;
    
    float stateTime;
    public Scores goats;
    
    public MyGameScreen(final GoatRush gam, Scores goats) //passes the game object and scores as parameters
    {
    	this.game = gam; 
    	
    	// instantiates the font
    	font = new BitmapFont();
    	
		// instantiates the textures
		speedup = new Texture (Gdx.files.internal("speed.png"));
		standSheet = new Texture(Gdx.files.internal("run.png"));
		tree = new Texture(Gdx.files.internal("tree.png"));
		grass = new Texture(Gdx.files.internal("grass1.png"));
		rush = new Texture(Gdx.files.internal("rush1.png"));
		
		// instantiates the sprites
		grassSprite = new Sprite (grass);
		rushSprite = new Sprite (rush);
		speedupSprite = new Sprite(speedup);
		
		// instantiates the sound
		intense = Gdx.audio.newSound(Gdx.files.internal("music.mp3"));
		
		// passing on the linked list for scores
		this.goats = goats;
		
		// making the goat sprite sheet animation
        TextureRegion[][] tmp = TextureRegion.split(standSheet, standSheet.getWidth()/FRAME_COLS, standSheet.getHeight()/FRAME_ROWS);
        standFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                standFrames[index++] = tmp[i][j];
            }
        }
        
        standAnimation = new Animation(0.1f, standFrames);
        
        // instantiates the spritebatch
        spriteBatch = new SpriteBatch();
        stateTime = 0f;   
        
        // instantiates the goat rectangle for hitbox
        goat = new Rectangle();
        goat.x = 0;
        goat.y = 270;
        goat.width = 100;
        goat.height = 110;
        
        // instantiates the array of trees
        trees = new Array<Rectangle>();
        
        // calls spawntree method
		spawnTree();
    }  
    
    // this method applies the grassy background
    private void renderBackground(){
		grassSprite.draw(spriteBatch);
	}
    
    // this method spawns trees at certain y-coordinates on the screen randomly
    private void spawnTree() {
		int random;
		random = (int)(Math.random()*4 + 1);
        Rectangle tree = new Rectangle();
        tree.x = 2048;
        
        switch (random){
        	case 1: tree.y = 0;break; 
        	case 2: tree.y = 200;break;
        	case 3: tree.y = 400; break;
        	case 4: tree.y = 600;
        }
        
        tree.width = 90;
        tree.height = 130;
        trees.add(tree);
        lastTreeTime = TimeUtils.nanoTime();
    }
    
    // this method renders everything within it every frame
    @Override
    public void render(float delta) {
    	
    	// begins the spritebatch in order to draw images/textures/sprites on the screen
    	spriteBatch.begin();
		
    	// clears the background screen
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); 
		
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = standAnimation.getKeyFrame(stateTime, true);
        
        // renders the grassy background
        renderBackground();
        
        // this draws the goat animation at the same place the goat rectangle is located
        spriteBatch.draw(currentFrame,goat.x, goat.y);
        
        // draws the trees at the same place the tree rectangle is located
        for (Rectangle treed : trees){
        	spriteBatch.draw(tree, treed.x, treed.y);
        }
        
        // up-scales the font for view-ability 
        font.setScale(2); 
        
        // writes the score in the bottom left corner
        font.draw(spriteBatch, "Score: "+treesPassed, 0, 25);
        
        // draws the goat rush sprite when the score is 28...this is when the music gets intense
        if (treesPassed >= 28 && treesPassed <= 30){
        	spriteBatch.draw(rushSprite, 550, 300);     	
        }
        
        // draws the speed up sprite when score reaches 40 
        if (treesPassed >= 40 && treesPassed <= 42){
        	spriteBatch.draw(speedupSprite, 950, 0);
        }
        //if the user touches top half of the screen, the goat moves up; otherwise it moves down
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            if (touchPos.y > (Gdx.graphics.getHeight()/2)){
            	if (treesPassed >= 40){
                	goat.y -= 600* Gdx.graphics.getDeltaTime();

            	}
            	else{
                	goat.y -= 400* Gdx.graphics.getDeltaTime();

            	}
            	
            }
            else{
            	if (treesPassed >= 40){
                	goat.y += 600* Gdx.graphics.getDeltaTime();

            	}
            	else{
                	goat.y += 400* Gdx.graphics.getDeltaTime();

            	} 
            }
            
        }
        
        // if left or a is pressed move the goat rectangle left
        if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)){
        	
        	// if the score is above 40, the goat moves 200 pixels faster (power up)
        	if (treesPassed >= 40){
            	goat.x -= 600* Gdx.graphics.getDeltaTime();

        	}
        	// if the score is less than 40, the goat moves at 400 pixels
        	else{
            	goat.x -= 400* Gdx.graphics.getDeltaTime();

        	}
        }
        
        // if right or d is pressed, move the goat rectangle right
        if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)){
        	if (treesPassed >= 40){
            	goat.x += 600* Gdx.graphics.getDeltaTime();

        	}
        	else{
            	goat.x += 400* Gdx.graphics.getDeltaTime();

        	}
    	}
        
        // if up or w is pressed, move the goat rectangle up
        if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)){
        	if (treesPassed >= 40){
            	goat.y += 600* Gdx.graphics.getDeltaTime();

        	}
        	else{
            	goat.y += 400* Gdx.graphics.getDeltaTime();

        	} 
    	}
        
        // if down or s is pressed, move the goat rectangle down
        if (Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)){
        	if (treesPassed >= 40){
            	goat.y -= 600* Gdx.graphics.getDeltaTime();

        	}
        	else{
            	goat.y -= 400* Gdx.graphics.getDeltaTime();

        	}
    	}
        
		// this prevents the goat from moving off the screen on the left
        if (goat.x < 0){
        	goat.x = 0;
        }
        
        // this prevents the goat from moving off the screen on the right
        if (goat.x > 2000){
        	goat.x = 2000;
        }
        
        // this prevents the goat from moving off the screen on the bottom
        if (goat.y < 0){
        	goat.y = 0;
        }
        
        // this prevents the goat from moving off the screen on the top
        if (goat.y > 650 - 64){
        	goat.y = 650 - 64;
        }
        
        // if the last tree was spawned a certain time ago, spawn the next tree
        if (TimeUtils.nanoTime() - lastTreeTime > 1000000000 || treesPassed >= 40 && TimeUtils.nanoTime() - lastTreeTime > 500000000){
        	spawnTree();
        }
           
        // this loop allows each of the trees to move at a certain speed
        Iterator<Rectangle> iter = trees.iterator();
        while (iter.hasNext()) {
            Rectangle tree = iter.next();
            
            // this speeds up the trees based on how many have passed by. After a while, the trees stay at a certain speed
            if (counter*1.1>=5){
                tree.x -= (300*6) * Gdx.graphics.getDeltaTime();

            }else{
                tree.x -= (300*(counter*1.1)) * Gdx.graphics.getDeltaTime();

            }
            
            // if the trees have gone off the screen on the left, remove the item
            if (tree.x + 100 < 0){
                iter.remove();
                counter+=0.1;
                treesPassed++;
            }
            
            // if the tree rectangle overlaps the goat rectangle, set the screen to game over
            if (tree.overlaps(goat)) {
            	goats.addFront(treesPassed);
            	
            	intense.stop();
            	game.setScreen(new GameOverScreen(game, goats));
            }	
        		
        }
        
        // ends the spritebatch
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    	// plays the background music when the screen is shown
        intense.play(); 
    }

    @Override
    public void hide() {
       
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    	standSheet.dispose();
    	tree.dispose();
    	intense.dispose();
        texture.dispose();
        batch.dispose();
    }

}

