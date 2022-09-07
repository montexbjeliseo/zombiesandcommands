package com.montex.zombiesandcommands.screen;

import com.montex.zombiesandcommands.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.montex.zombiesandcommands.unit.*;
import com.badlogic.gdx.math.*;
import com.montex.zombiesandcommands.items.*;
import com.montex.zombiesandcommands.manager.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import static com.montex.zombiesandcommands.util.Util.absolute;

public class GameScreen extends ScreenAdapter implements InputProcessor
{
	MyGdxGame game;

	boolean prepared = false;

	Stage hud;

	Stage pause;

	Skin skin;

	InputMultiplexer im;
	
	boolean paused = false;
	
	TextButton resume, setting, finish;
	
	int shooting = -1, 
		playerId;

	public GameScreen(final MyGdxGame game){
		this.game = game;

		Skin skin = ResourceManager.skin();
		
		hud = new Stage();

		pause = new Stage();

		Table t = new Table();
		
		t.setTouchable(Touchable.enabled);
		
		t.background(skin.newDrawable("white", new Color(1f, 1f, 1f, 0.8f)));
		
		t.setFillParent(true);
		
		resume = new TextButton("Volver al juego", skin);
		resume.pad(10);
		resume.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float eventX, float eventY, int p, int b){
					paused = false;
					show();
					return true;
				}
			});
		
		setting = new TextButton("Opciones", skin);
		setting.pad(10);
		
		finish = new TextButton("Abandonar juego", skin);
		finish.pad(10);
		finish.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float eventX, float eventY, int p, int b){
					game.setScreen(new MainScreen(game));
					return true;
				}
			});
		
		t.add(resume).pad(10);
		t.row();
		t.add(setting).pad(10).fillX();
		t.row();
		t.add(finish).pad(10);
		
		pause.addActor(t);
	}

	@Override
	public void show(){
		im = new InputMultiplexer(this, hud);

		Gdx.input.setInputProcessor(im);
		
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void render(float dt){        
		try
		{
			OrthographicCamera cam = CameraManager.camera;

			if (ResourceManager.assets.update())
			{
				if (!prepared)
				{
					LevelManager.loadCurrentLevel();
					prepared = true;
					ResourceManager.ambience("windy.mp3");
				}

				Gdx.gl.glClearColor(0, 0, 0, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
				Player player = SpriteManager.getPlayerById(playerId);

				cam.position.set(new Vector3(player.position.x, player.position.y, 0));

				cam.update();

				game.batch.setProjectionMatrix(cam.combined);
				
				if (!paused&&Gdx.input.isTouched())
				{

					

					float inputX = Gdx.input.getX();

					float inputY = Gdx.input.getY();

					Vector3 aim = CameraManager.camera.unproject(new Vector3(inputX, inputY, 0));

					player.aim(new Circle(aim.x, aim.y, 5));

					for (Unit u : SpriteManager.allUnits())
					{

						if (Intersector.overlaps(u.position, new Circle(player.aim().x, player.aim().y, 5)) && !u.isDead())
						{
							player.fire();
							player.stop();

						}
						else
						{
							player.walk();
						}

					}

				}

				SpriteManager.update(Gdx.graphics.getDeltaTime());

				RenderManager.drawFrame(game.batch);
				
				if(paused){
					pause.act(dt);
					pause.draw();
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("GameScreen: "+e.getLocalizedMessage());
		}

	}

	@Override
	public void hide(){
		Gdx.input.setInputProcessor(null);
	}

	public boolean keyDown(int keycode){
		if(keycode == Input.Keys.BACK){
			if(paused){
				paused = false;
				im = new InputMultiplexer(this, hud);
				Gdx.input.setInputProcessor(im);
			} else {
				paused = true;
				im = new InputMultiplexer(pause);
				Gdx.input.setInputProcessor(pause);
			}
			return true;
		}
		return false;
	}

	public boolean keyUp(int keycode){
		return false;
	}

	public boolean keyTyped(char character){
		return false;
	}

	public boolean touchDown(int x, int y, int pointer, int button){
		
		return false;
	}

	public boolean touchUp(int x, int y, int pointer, int button){
		return false;
	}

	public boolean touchDragged(int x, int y, int pointer){
		System.out.println("TouchDragged!");

		return false;
	}

	public boolean mouseMoved(int x, int y){
		return false;
	}

	public boolean scrolled(float amountX, float amountY){
		return false;
	}

	public boolean scrolled(int i){
		return false;
	}
}
