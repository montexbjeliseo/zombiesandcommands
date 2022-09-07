package com.montex.zombiesandcommands.screen;
import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.montex.zombiesandcommands.*;
import com.badlogic.gdx.graphics.*;
import com.montex.zombiesandcommands.manager.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.Array;

public class CreateScreen extends ScreenAdapter
{

	MyGdxGame game;

	Stage stage;

	TextButton start, back;

	List<String> levels;

	public CreateScreen(final MyGdxGame game){
		this.game = game;
		try
		{
			stage = new Stage(new ScreenViewport());
			Skin skin = ResourceManager.skin();

			Table table = new Table();
			table.setFillParent(true);
			table.debug();
			
			

			start = new TextButton("Comenzar", skin);
			start.pad(10);
			
			start.addListener(new InputListener(){
					@Override
					public boolean touchDown(InputEvent event, float eventX, float eventY, int p, int b){
						game.setScreen(new GameScreen(game));
						return false;
					}
			});
			
			back = new TextButton("Atras", skin);
			back.pad(10);
			
			back.addListener(new InputListener(){
					@Override
					public boolean touchDown(InputEvent event, float eventX, float eventY, int p, int b){
						game.setScreen(new MainScreen(game));
						return false;
					}
			});
			
			levels = new List<String>(skin);
			levels.setHeight(Gdx.graphics.getHeight() / 2);
			levels.setWidth(Gdx.graphics.getWidth() / 2);
			
			ScrollPane pane = new ScrollPane(levels, skin);
			pane.setScrollX(0);
			pane.setScrollY(0);

			levels.setItems(ResourceManager.getLevels());

			table
				.add(pane)
				.pad(10)
				.colspan(2)
				.expand()
				.fill();
			
			table.row();

			table.add(back).pad(10).fillX();
			
			table.add(start).pad(10).fillX();

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
	}

	@Override
	public void render(float dt){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(dt);

		stage.draw();

	}
	@Override
	public void hide(){
		Gdx.input.setInputProcessor(null);
	}
}
