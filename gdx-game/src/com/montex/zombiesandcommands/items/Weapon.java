package com.montex.zombiesandcommands.items;

public class Weapon
{
	private float 
		delayTime = 0.3f,
		delay = 0;
	
	private int 
		maxCharge = 12,
		charge = 12;
	
	public Weapon(){
		
	}
	
	public void fire(){
		charge--;
		charge = Math.max(charge, 0);
	}
	public void reload(){
		charge = 12;
		delay = 0.7f;
	}
}
