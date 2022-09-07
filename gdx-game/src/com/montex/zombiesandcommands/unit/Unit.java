package com.montex.zombiesandcommands.unit;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.audio.*;
import com.montex.zombiesandcommands.manager.*;
import com.montex.zombiesandcommands.items.*;

import static com.montex.zombiesandcommands.manager.LevelManager.isFree;

import static com.montex.zombiesandcommands.util.Util.dir;

public class Unit
{
	private int id;
	
	public Circle lvision;

	public int 
	currentFrameIndex = 0,
	health = 100, 
	shield = 0;

	private float 
		DELAY_SHOOT = 0, 
		DELAY_STEP = 0, 
		DELAY_HURT;

	Animation currentAnimation;

	public Animation walkAnimation;

	public Animation dyingAnimation;

	public Animation idleAnimation;

	public float 
	time = 0f, 
	speed = 100,
	scope = 1000;

	public Circle position, mask;
	
	private Circle aim;
	
	public Vector2 dir;

	public Sprite sprite;
	
	private float angle;
	
	public String key = "noteam";

	enum UnitStatus
	{
		walking,
		dead,
		idle,
		firing
	}

	public UnitStatus currentStatus = UnitStatus.idle;

	public Unit(){
		position = new Circle(0, 0, 50);
		mask = new Circle(position);
		aim = new Circle(0, 0, 1);
		id = SpriteManager.count;
		SpriteManager.count++;
	}
	
    public int getId(){
		return id;
	}
	
	public boolean isDead(){
		return currentStatus==UnitStatus.dead;
	}

	public boolean canShoot(){
		return DELAY_SHOOT < 0;
	}

	public void render(Batch batch){
		sprite.draw(batch);
	}

	public void update(float dt){
		//Time para la animacion
		time += dt;
		
		lvision = new Circle(position.x, position.y, position.radius * 10);
		
		currentStatus = health <= 0 ? UnitStatus.dead : currentStatus;
		
		//Sprite para el fotograma
		if (currentStatus == UnitStatus.walking)
		{
			step(dt);

			sprite = new Sprite(walkAnimation.getKeyFrame(time, true));
		} else {
			sprite = new Sprite(idleAnimation.getKeyFrame(time, true));
		}

		sprite.setPosition(position.x - position.radius, position.y - position.radius);

		sprite.setSize(position.radius * 2, position.radius * 2);

		

		if(currentStatus != UnitStatus.dead){
			sprite.setRotation(angle);
		}

		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);


		DELAY_SHOOT -= dt;
		
		DELAY_HURT -= dt;
		
		DELAY_STEP -= dt;

	}

	private void step(float dt){
		if (!Intersector.overlaps(position, new Circle(aim.x, aim.y, 1)))
		{
			dir = dir(position, aim);
			
			if(isFree(position.x + dir.x * speed * dt, position.y )){
				position.x += dir.x * speed * dt;
			}
			
			if(isFree(position.x, position.y + dir.y * speed * dt)){
				position.y += dir.y * speed * dt;
			}
			
			mask = new Circle(position);
			
			mask.radius/=2;
			
			if(DELAY_STEP < 0){
				ResourceManager.play("step");
				DELAY_STEP = 0.4f;
			}
			
		}
		else
		{
			stop();
		}
	}

	public void walk(){
		if(canShoot())
			currentStatus = UnitStatus.walking;
	}

	public void stop(){
		currentStatus = UnitStatus.idle;
	}

	public void fire(){
		if (canShoot())
		{

			dir = dir(position, aim);

			SpriteManager.bullets.add(
				new Bullet(
					this,
					ResourceManager.getTexture("bullet.png"), 
					new Vector2(
						this.position.x,
						this.position.y), 
					new Vector2(dir)));
			ResourceManager.play("shoot");
			DELAY_SHOOT = 0.3f;
		}
	}

	public void die(){
		
		if(!isDead()){
			ResourceManager.play("pain");
		}
		
		currentStatus = UnitStatus.dead;

		aim = position;
	}

	public void doSomething(){
		//Do something
	}
	
	public void observe(Unit u){
		
	}

	public void hurt(){
		health--;
		health = Math.max(0, health);
		if(DELAY_HURT < 0 && !isDead()){
			DELAY_HURT = 0.3f;
			if(health <= 0){
				die();
			} else {
				ResourceManager.play("hurt");
			}
		}
		
	}
	
	public Circle aim(){
		return this.aim;
	}
	
	public void aim(Circle aim){
		this.aim = aim;
		
		angle = MathUtils.radiansToDegrees * MathUtils.atan2(aim.x - position.x + position.radius / 2, aim.y - position.y + position.radius / 2);

		if (angle < 0)
		{
			angle += 360;
		}
		
		angle = -angle;
		
	}
}
