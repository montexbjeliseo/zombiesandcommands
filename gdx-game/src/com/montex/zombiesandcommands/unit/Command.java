package com.montex.zombiesandcommands.unit;

import static com.montex.zombiesandcommands.util.Util.distance;
import com.badlogic.gdx.math.*;

public class Command extends Unit
{
	
	public Controller ai;
	
	public Command(){
		super();
		ai = new Controller(this);
		this.key = "nasha komanda";
	}
	
	public void update(float dt){
		if(!isDead()){
			ai.tick();
		}
		super.update(dt);
	}
	
}
