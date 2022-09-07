package com.montex.zombiesandcommands.manager;

import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.montex.zombiesandcommands.unit.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import java.util.*;
import com.badlogic.gdx.utils.Array;

public class LevelManager {
	
	public static TiledMap map;
	
	int currentLevel;

	public static void loadCurrentLevel() {
	
		map = new TmxMapLoader().load("levels/demo2/demo2.tmx");
		
		SpriteManager.commands = new Array<>();
		SpriteManager.zombies = new Array<>();
		SpriteManager.bullets = new Array<>();
		SpriteManager.players = new Array<>();
		
		
		loadUnits();
	}
	
	private static void loadUnits()
	{
		
		try
		{

		    Player player = new Player();

			player.walkAnimation = new Animation(0.2f, ResourceManager.getTextureRegions("units/01.png", 3, 3, 8));

			player.idleAnimation = new Animation(0.2f, ResourceManager.getTextureRegions("units/01_0.png", 3, 3, 9));
			
			player.aim(new Circle(100, 100, 1));

			player.position = new Circle(100, 100, 50);

			player.mask = new Circle(player.position);

			player.mask.radius/=2;
			
			SpriteManager.players.add(player);
			

			Command player2 = new Command();

			player2.walkAnimation = new Animation(0.2f, ResourceManager.getTextureRegions("units/02.png", 3, 3, 8));

			player2.idleAnimation = new Animation(0.2f, ResourceManager.getTextureRegions("units/02.png", 3, 3, 1));
			
			player2.aim(new Circle(400, 400, 1));

			player2.position = new Circle(400, 400, 50);
			
			player2.mask = new Circle(player2.position);
			
			player2.mask.radius/=2;

			SpriteManager.commands.add(player2);
			
			for(int i = 0; i < 5; i++){
				Zombie z = new Zombie();
				
				z.position = new Circle(200 + 10 * i, 200 + new Random().nextInt(100) * i , 50);
				
				z.mask = new Circle(z.position);
				
				z.mask.radius /= 2;
				
				z.aim(new Circle(z.position));
				
				z.walkAnimation = new Animation(0.2f, ResourceManager.getTextureRegions("units/02.png", 3, 3, 8));
				
				z.idleAnimation = new Animation(0.2f, ResourceManager.getTextureRegions("units/01_0.png", 3, 3, 9));
				
				SpriteManager.zombies.add(z);
			}
			
		}
		catch (Exception e)
		{
			System.out.println(e.toString());

		}

	}
	
	public static boolean isFree(float x, float y){
		TiledMapTileLayer tmtl = (TiledMapTileLayer)map.getLayers().get(1);
		int mx = (int)(x / map.getProperties().get("tilewidth", int.class));
		int my = (int)(y / map.getProperties().get("tilewidth", int.class));
		return tmtl.getCell(mx, my) == null;
	}
	
}
