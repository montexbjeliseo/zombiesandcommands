package com.montex.zombiesandcommands.util;
import com.badlogic.gdx.math.*;
import com.montex.zombiesandcommands.manager.*;

public class Util
{
	public static float distance(Vector2 a, Vector2 b){

		return (float)(Math.pow((double)(a.x - b.x), 2) + Math.pow((double)(a.x - b.x), 2));

	}

	public static Vector2 absolute(Vector2 p){
		return new Vector2(
			p.x - (CameraManager.camera.viewportWidth / 2 - CameraManager.camera.position.x),
			p.y - (CameraManager.camera.viewportHeight / 2 - CameraManager.camera.position.y));
	}
	
	public static Vector2 dir(Vector2 from, Vector2 to){
		return new Vector2(to.x - from.x, to.y - from.y).nor();
	}
	
	public static Vector2 dir(Circle from, Circle to){
		return new Vector2(to.x - from.x, to.y - from.y).nor();
	}

}
