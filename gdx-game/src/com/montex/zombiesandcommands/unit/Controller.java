package com.montex.zombiesandcommands.unit;

import com.montex.zombiesandcommands.manager.SpriteManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import static com.montex.zombiesandcommands.util.Util.distance;
import com.badlogic.gdx.utils.Array;

public class Controller
{
	Unit self, persecuted;
	
	Array<Circle> zones = new Array<>();
	
	enum Status {
		watchOut,
		explore,
		attack
	}
	
	Circle lastPoint;
	
	public Status currentStatus = Status.watchOut;
	
	public Controller(Unit u){
		this.self = u;
		lastPoint = new Circle(u.position);
	}
	
	public void tick(){
		
		System.out.println(currentStatus.toString());
		
		if(currentStatus==Status.watchOut){
			watchOut();
		} else if(currentStatus==Status.attack){
			attack();
		} else if(currentStatus==Status.explore){
			explore();
		}
	}
	
	public void watchOut(){
		if(self.lvision==null){
			return;
		}
		
		boolean newZone = true;
		
		for(Circle z : zones){
			if(Intersector.overlaps(self.lvision, z)){
				newZone = false;
			}
		}
		
		if(newZone){
			zones.add(self.lvision);
		}
		
		for(Unit u : SpriteManager.allUnits()){
			if(Intersector.overlaps(u.position, self.lvision)&&
			u.getId()!=self.getId()&&
			self.key!=u.key&&
			!u.isDead()){
				observe(u);
				System.out.println("Encontrado: " + u.getId() + ", " + u.key + ", ");
				return;
			}
		}
		currentStatus = Status.explore;
	}
	
	public void explore(){
		if(!Intersector.overlaps(self.aim(), self.position)){
			lastPoint = new Circle(self.position);
			self.walk();
		} else {
			
		}
		watchOut();
		//currentStatus = Status.watchOut;
	}
	
	public void attack(){
		if(persecuted==null||persecuted.isDead()){
			persecuted = null;
			currentStatus = Status.watchOut;
			return;
		}
		if(!persecuted.isDead()&&Intersector.overlaps(persecuted.position, self.aim())){
			self.fire();
		} else {
			currentStatus = Status.watchOut;
		}
	}
	
	public void observe(Unit u){
		if(persecuted!=null){
			persecuted = 
				distance(
				new Vector2(persecuted.position.x, persecuted.position.y), 
				new Vector2(self.position.x, self.position.y))
				<
				distance(
				new Vector2(u.position.x, u.position.y), 
				new Vector2(self.position.x, self.position.y))
				? persecuted : u;

		} else {
			persecuted = u;
		}
		
		if(persecuted!=null&&Intersector.overlaps(self.lvision, persecuted.position)){
			self.aim(new Circle(persecuted.position.x, persecuted.position.y, 5));
			currentStatus = Status.attack;
			System.out.println("Aim");
		}
	}
}
