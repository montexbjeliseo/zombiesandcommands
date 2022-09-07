package com.montex.zombiesandcommands.items;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.montex.zombiesandcommands.unit.*;

import static com.montex.zombiesandcommands.manager.LevelManager.isFree;
import com.montex.zombiesandcommands.manager.*;

public class Bullet
{
	Sprite sprite;

	Vector2 dir;

	public Circle mask;

	public Unit sender;

	public float angle;

	public Bullet(Unit sender, Texture tex, Vector2 pos, Vector2 dir){
		this.sender = sender;
		this.sprite = new Sprite(tex);
		this.sprite.setSize(4, 4);
		this.sprite.setOrigin(2f, 2f);

		this.mask = new Circle(pos.x, pos.y, 2f);

		this.dir = dir;

		angle = MathUtils.radiansToDegrees * MathUtils.atan2(dir.x, dir.y);

		if (angle < 0)
		{
			angle += 360;
		}

		angle = -angle;

		sprite.setRotation(angle);

	}

	public void update(float dt){

		if (isFree(mask.x + dir.x * 500 * dt, mask.y + dir.y * 500 * dt))
		{
			mask.x += dir.x * 500 * dt;
			mask.y += dir.y * 500 * dt;
		}
		else
		{
			dir.x = 0;
			dir.y = 0;
		}

		sprite.setPosition(mask.x, mask.y);

	}
	public void render(SpriteBatch batch){
		if (dir.x == 0 && dir.y == 0)
		{return;}
		sprite.draw(batch);
	}
}
