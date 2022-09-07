package com.montex.zombiesandcommands.screen;
import com.montex.zombiesandcommands.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.montex.zombiesandcommands.manager.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.*;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;


public class LoadingScreen extends ScreenAdapter
{
	
	MyGdxGame game;

	boolean loaded = false;
	
	Stage stage;
	
	Skin skin;
	
	TextButton button;

	public LoadingScreen(MyGdxGame game)
	{
		this.game = game;
		
		try {
		
			stage = new Stage();
			
			Gdx.input.setInputProcessor(stage); 
			
			skin = new Skin(); 
			
			Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
			
			pixmap.setColor(Color.WHITE);
			
			pixmap.fill();
			
			skin.add("white", new Texture(pixmap)); 
			
			skin.add("default", new BitmapFont()); 
			
			TextButtonStyle textButtonStyle = new TextButtonStyle();
			
			textButtonStyle.font = skin.getFont("default");
			skin.add("default", textButtonStyle); 
			
			Table table = new Table();
			table.setFillParent(true);
			stage.addActor(table); 
			
			button = new TextButton("Loading...", skin);
			button.setScale(5);
			table.add(button); 
			
		} catch(Exception e){
			System.out.println(e.toString());
		}
		
	}

	@Override
	public void show()
	{
		
		try {
		Gdx.input.setInputProcessor(new InputAdapter(){
				@Override
				public boolean touchDown(int x, int y, int p, int b)
				{
					if (loaded)
					{
						hide();
						game.setScreen(new MainScreen(game));
					}
					return true;
				}
			});
		
		} catch(Exception e){
			System.out.println(e.toString());
		}
	}

	@Override
	public void render(float dt)
	{
		Gdx.gl.glClearColor(0.19f, 0.19f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(dt);
		
		stage.draw();
		
		if (ResourceManager.assets.update())
		{
			button.setText("Loaded. Tap to continue");
			
			loaded = true;
		}
	}
	@Override
	public void hide()
	{
		Gdx.input.setInputProcessor(null);
	}
}
