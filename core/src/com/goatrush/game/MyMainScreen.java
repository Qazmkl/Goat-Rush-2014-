package com.goatrush.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class MyMainScreen implements Screen {
	
	// Declares variables
	Texture rush;
	Texture grass;
	Sprite rushSprite;
	Sprite grassSprite;
	
	// Declares a scores linkedlist
	public Scores goats;
    final GoatRush game;

    public MyMainScreen(final GoatRush gam, Scores goats) // ** constructor called initially **//
    {
    	// Instantiates game variable
    	game = gam;
    	
    	// Instantiates textures/sprites
		rush = new Texture(Gdx.files.internal("rush1.png"));
		grass = new Texture(Gdx.files.internal("grass1.png"));
		grassSprite = new Sprite (grass);
		rushSprite = new Sprite (rush);
		
		// Passes on goats linkedlist
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
    	game.batch.draw(rushSprite, 350,350);
    	game.font.draw(game.batch, "How to Play:", 480, 220);
    	game.font.draw(game.batch, "Move using the arrow keys and dodge trees!", 500, 200);
    	game.font.draw(game.batch, "Touch the top half of the screen to go up or the bottom half to go down!", 460, 180);

        game.font.draw(game.batch, "Press any key to begin or touch anywhere to begin!", 510, 160);
        game.batch.end();

        // If the screen is touched or if any key is pressed it will go back to
        // the main menu
        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
            game.setScreen(new MyGameScreen(game, goats));
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

