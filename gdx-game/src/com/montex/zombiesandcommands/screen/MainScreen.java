package com.montex.zombiesandcommands.screen;

import com.montex.zombiesandcommands.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.montex.zombiesandcommands.manager.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.*;


import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class MainScreen extends ScreenAdapter
{

	MyGdxGame game;

	Stage stage;

	TextButton newGame, join, quit;
	
	TextField name;

	public MainScreen(final MyGdxGame game){
		this.game = game;

		try
		{

			stage = new Stage(new ScreenViewport());
			
			Skin skin = ResourceManager.skin();
			
			Table table = new Table();

			table.setFillParent(true);

			name = new TextField("Player", skin);
			name.addListener(new InputListener(){

					@Override
					public boolean touchDown(InputEvent event, float eventX, float eventY, int p, int b){
						
						Gdx.input.getTextInput(new Input.TextInputListener(){
								public void input(String text){
									name.setText(text);
								}

								public void canceled(){
								}
						}, "Ingrese su nombre", name.getText());
						return true;
					}
				});

			Label nameLabel = new Label("Nombre: ", skin);
			
			newGame = new TextButton("Nuevo juego", skin);
			newGame.pad(10);
			newGame.addListener(new InputListener(){

					@Override
					public boolean touchDown(InputEvent event, float eventX, float eventY, int p, int b){
						
						if(!name.getText().equals("")){
							game.setScreen(new CreateScreen(game));
						}
						
						return false;
					}

				});
				
			join = new TextButton("Unirse al juego", skin);
			join.pad(10);
			join.addListener(new InputListener(){

					@Override
					public boolean touchDown(InputEvent event, float eventX, float eventY, int p, int b){

						return false;
					}

			});
			
			quit = new TextButton("Salir", skin);
			quit.pad(10);
			quit.addListener(new InputListener(){

					@Override
					public boolean touchDown(InputEvent event, float eventX, float eventY, int p, int b){
						System.exit(0);
						return false;
					}

				});

			table.add(nameLabel).width(200).height(50).pad(10);

			table.add(name).pad(10);

			table.row();

			table.add(newGame).colspan(2).fillX().pad(10); 
			
			table.row();
			
			table.add(join).colspan(2).fillX().pad(10);
			
			table.row();
			
			table.add(quit).colspan(2).fillX().pad(10);

			//table.debug();

			stage.addListener(new ActorGestureListener() {
					@Override	
					public boolean longPress(Actor actor, float x, float y){
						System.out.println("long press " + x + ", " + y);
						return true;
					}

					public void fling(InputEvent event, float velocityX, float velocityY, int button){
						System.out.println("fling " + velocityX + ", " + velocityY);
					}

					public void zoom(InputEvent event, float initialDistance, float distance){
						System.out.println("zoom " + initialDistance + ", " + distance);
					}
				});

			stage.addActor(table);

		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}

	}

	@Override
	public void show(){
		Gdx.input.setInputProcessor(stage);
		game.currentScreen = MyGdxGame.MAIN;
	}

	@Override
	public void render(float dt){
		Gdx.gl.glClearColor(0.19f, 0.19f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		try
		{
			
			stage.act(dt);
			stage.draw();

		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	@Override
	public void hide(){
		Gdx.input.setInputProcessor(null);
	}
}
