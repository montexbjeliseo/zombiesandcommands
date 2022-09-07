package com.montex.zombiesandcommands.manager;
import com.badlogic.gdx.graphics.g2d.*;
import com.montex.zombiesandcommands.unit.*;
import com.badlogic.gdx.utils.*;
import com.montex.zombiesandcommands.*;
import com.badlogic.gdx.graphics.*;
import com.montex.zombiesandcommands.items.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.math.*;


public class RenderManager
{
	
	public static ShapeRenderer shaper = new ShapeRenderer();;
	
	public static TiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(LevelManager.map);

	public static void drawFrame(SpriteBatch batch)
	{
		CameraManager.camera.update();
		
		shaper.setProjectionMatrix(CameraManager.camera.combined);
		
		mapRenderer.setView(CameraManager.camera);
		
		mapRenderer.render(new int[]{0, 1});
		
		
		
		shaper.begin(ShapeRenderer.ShapeType.Line);
		
		for(Unit u : SpriteManager.allUnits()){

			shaper.setColor(Color.OLIVE);
			
			shaper.circle(u.mask.x, u.mask.y, u.lvision.radius);
			
			shaper.setColor(Color.RED);

			shaper.circle(u.mask.x, u.mask.y, u.mask.radius);

		}
		
		shaper.end();
		
		batch.begin();

		for(Bullet b : SpriteManager.bullets){
			b.render(batch);
		}
		

		for (Unit u : SpriteManager.allUnits())
		{
			u.render(batch);
			
		}
		
		//SpriteManager.player.render(batch);
		
		batch.end();
		
		shaper.begin(ShapeRenderer.ShapeType.Filled);
		for(Unit u : SpriteManager.allUnits()){
			
			Rectangle hpred = new Rectangle();
			
			hpred.x = u.position.x - u.position.radius;
			
			hpred.y = u.position.y - 3;
			
			hpred.width = u.position.radius * 2;
			
			hpred.height = + 1;
			
			shaper.setColor(Color.RED);
			
			shaper.rect(hpred.x, hpred.y, hpred.width, 5);
			
			shaper.setColor(Color.GREEN);
			
			shaper.rect(hpred.x, hpred.y, hpred.width * u.health / 100, 5);
			
		}
		
		shaper.end();
		
		BitmapFont bfnt = new BitmapFont();
		
		bfnt.setColor(Color.RED);
		
		batch.begin();
		
		for(Command c : SpriteManager.commands){
			bfnt.draw(batch, c.ai.currentStatus.toString(), c.position.x, c.position.y);
		}
		
		batch.end();
		
	}

	private void drawHud()
	{ }
}
