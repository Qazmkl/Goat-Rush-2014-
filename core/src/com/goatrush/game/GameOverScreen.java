package com.goatrush.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.*;

public class GameOverScreen implements Screen {
	
	// Declares textures, sprites, and the view port
	Texture gg;
	Texture grass;
	Texture dead;
	Sprite deadSprite;
	Sprite grassSprite;
	Sprite ggSprite;
	Viewport viewport;
	
	// Declares a scores linked list
	public Scores goats;
	final GoatRush game;
	
	public GameOverScreen(final GoatRush gam, Scores goats) // ** constructor called initially **//
    {
    	game = gam;
    	
    	// Instantiates textures and sprites
		gg = new Texture(Gdx.files.internal("Game_Over.png"));
		grass = new Texture(Gdx.files.internal("grass1.png"));
		grassSprite = new Sprite (grass);
		ggSprite = new Sprite (gg);
		dead = new Texture(Gdx.files.internal("dead.png"));
		deadSprite = new Sprite (dead);
		
		// Passes on the linked list
		this.goats = goats;
    }

	
	 @Override
	    public void render(float delta) {
	    	// Clears screen
	    	Gdx.gl.glClearColor(0, 0, 0, 0);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        
	        // Begins drawing
	        game.batch.begin();
	        game.batch.draw(grassSprite,0,0);
	    	game.batch.draw(ggSprite, 0,0);
	    	game.batch.draw(deadSprite, 580,400);
	    	game.font.draw(game.batch, "High Score: " + goats.highest(), 615, 230);
	    	game.font.draw(game.batch, "Your Score: " + goats.latest(), 615, 200);
	        game.font.draw(game.batch, "Press any key or touch anywhere to try again", 580, 100);
	        game.batch.end();

	        // If the screen is touched or if any key is pressed it will go back to
	        // the main menu after a short delay
	        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
	        	for(double i = 0; i<100000000;){
	            	i++;
	            }
	        	
	        	// Changes the screen to the main screen
	            game.setScreen(new MyMainScreen(game, goats));
	            dispose();
	        }
	    }
	
	@Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
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
    }
}
