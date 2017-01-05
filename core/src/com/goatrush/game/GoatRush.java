package com.goatrush.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GoatRush extends Game {
	
	public SpriteBatch batch;
    public BitmapFont font;
    public Scores goats;
    
	@Override
	public void create () {
	
		font = new BitmapFont();
		batch = new SpriteBatch();
		goats = new Scores();
		
		
		this.setScreen(new MyMainScreen(this, goats));
	}
	

	@Override
	public void render () {

		super.render();
	}
	
		
	@Override
	public void dispose(){	
		font.dispose();
		batch.dispose();
	}
	
	
	@Override
	public void resize(int height, int width){
		
	}
	
	
	@Override
	public void pause(){	
	}
	
	@Override
	public void resume(){	
	}
}

