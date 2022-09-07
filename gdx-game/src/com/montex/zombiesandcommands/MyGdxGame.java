package com.montex.zombiesandcommands;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

import com.montex.zombiesandcommands.unit.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;
import com.montex.zombiesandcommands.manager.*;
import com.badlogic.gdx.audio.*;
import com.montex.zombiesandcommands.items.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.montex.zombiesandcommands.screen.*;


public class MyGdxGame extends Game implements ApplicationListener
{
	
	public SpriteBatch batch;
	
	public static final int MAIN = 0;
	public static final int CREATE = 1;
	public static final int JOIN = 2;
	public static final int CONNECTING = 4;
	public static final int PLAYING = 5;
	public static final int PAUSE = 6;
		
	public int currentScreen = MAIN;
	
	@Override
	public void create()
	{
		batch = new SpriteBatch();
		
		ResourceManager.load();
		
		CameraManager.camera.setToOrtho(false, (float)Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight());
		
		setScreen(new LoadingScreen(this));
	}
	
	public float getTileSize(){
		return Gdx.graphics.getHeight() / 20;
	}
	
	public Graphics getGraphics(){
		return Gdx.graphics;
	}
	
	public Files getFiles(){
		return Gdx.files;
	}
	
	public Audio getAudio(){
		return this.getAudio();
	}
	
	@Override
	public void dispose()
	{
		batch.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}
}
