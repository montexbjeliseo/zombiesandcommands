package com.montex.zombiesandcommands.manager;

import com.montex.zombiesandcommands.unit.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.montex.zombiesandcommands.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;

import com.badlogic.gdx.audio.*;
import com.montex.zombiesandcommands.items.*;
import com.badlogic.gdx.maps.tiled.*;

import static com.montex.zombiesandcommands.util.Util.distance;

public class SpriteManager
{
	
	public static Array<Player> players = new Array<>();
	
	public static Array<Command> commands = new Array<>();

	public static Array<Zombie> zombies = new Array<>();

	public static Array<Bullet> bullets = new Array<>();
	
	public static int count = 0;
	

	public static void update(float dt)
	{
		try {
		
		//player.update(dt);
		
		for (Unit c : allUnits())
		{
			
			c.update(dt);
			
			for (Bullet b : bullets)
			{
				if (Intersector.overlaps(c.mask, b.mask) && c != b.sender)
				{
					c.hurt();
				}
			}
		}

		for (Bullet b : bullets)
		{
			b.update(dt);
		}
		
		} catch(Exception e){
			System.out.println("Sprites: "+e.toString());
		}

	}
	
	public static Array<Unit> allUnits(){
		
		Array<Unit> all = new Array<>();
		
		for(Unit u : zombies){
			all.add(u);
		}
		
		for(Unit c : commands){
			all.add(c);
		}
		
		for(Unit p : players){
			all.add(p);
		}
		
		return all;
	}
	
	public static Player getPlayerById(int id){
		for(Player p : players){
			if(p.getId()==id){
				return p;
			}
		}
		return null;
	}
}
