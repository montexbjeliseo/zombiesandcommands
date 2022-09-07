package com.montex.zombiesandcommands.manager;

import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.audio.*;
import com.montex.zombiesandcommands.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.loaders.*;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class ResourceManager
{

	public static AssetManager assets = new AssetManager();

	public static BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/console.fnt"));

	public static Skin skin;
	
	public static Texture background = new Texture(Gdx.files.internal("fonts/background.png"));

	public static void load(){

		loadTextures();

		loadSounds();

		loadSkin();

		loadLevels();
	}

	public static void loadTextures(){
		assets.load("tiles/leaves.png", Texture.class);

		assets.load("units/01.png", Texture.class);

		assets.load("units/01_0.png", Texture.class);

		assets.load("units/02.png", Texture.class);

		assets.load("units/03.png", Texture.class);

		assets.load("units/04.png", Texture.class);

		assets.load("bullet.png", Texture.class);
	}

	public static void loadSounds(){

		assets.load("sounds/shoot.wav", Sound.class);

		assets.load("sounds/step.wav", Sound.class);

		assets.load("sounds/hurt.wav", Sound.class);

		assets.load("sounds/pain.wav", Sound.class);

		assets.load("ambience/windy.mp3", Sound.class);

	}

	public static void loadLevels(){
		try
		{
			assets.setLoader(TiledMap.class, new TmxMapLoader());
			FileHandle[] files = Gdx.files.internal("levels").list();

			for (FileHandle f : files)
			{
				if (!f.isDirectory())
					continue;
				FileHandle level = f.child(f.name() + ".tmx");
				if (level == null)
					continue;

				assets.load(level.path(), TiledMap.class);

			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

	public static Array<String> getLevels(){
		Array<String> list = new Array<>();
		for (String s : assets.getAssetNames())
		{
			if (s.endsWith("tmx"))
			{
				list.add(s.split("/")[2].replace(".tmx", ""));
			}
		}

		return list;
	}
	
	public static TiledMap getLevel(String name){
		return assets.get("levels/"+name+"/"+name+".tmx", TiledMap.class);
	}

	public static void loadSkin(){

		skin = new Skin(); 
		
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		skin.add("white", new Texture(pixmap)); 
		skin.add("default", new BitmapFont(Gdx.files.internal("fonts/console.fnt"))); 

		//Button Style
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.font = skin.getFont("default");
		tbs.up = skin.newDrawable("white", new Color(0.33f, 0.33f, 0, 1));
		tbs.down = skin.newDrawable("white", new Color(0.67f, 0.67f, 0, 1));
		skin.add("default", tbs); 


		//TextField Style
		TextFieldStyle tfs = new TextFieldStyle();
		tfs.font = skin.getFont("default");
		tfs.background = skin.newDrawable("white", new Color(0.097f, 0.097f, 0, 1));
		tfs.fontColor = Color.WHITE;
		skin.add("default", tfs);


		//Label Style
		LabelStyle ls = new LabelStyle();
		ls.font = skin.getFont("default");
		skin.add("default", ls);

		//List Style
		ListStyle lis = new ListStyle();
		lis.background = skin.newDrawable("white", Color.LIGHT_GRAY);
		lis.font = skin.getFont("default");
		lis.fontColorUnselected = Color.WHITE;
		lis.fontColorSelected = Color.YELLOW;
		lis.selection = skin.newDrawable("white", Color.WHITE);
		skin.add("default", lis);

		//ScrollPane Style
		ScrollPaneStyle sps = new ScrollPaneStyle();
		sps.background = skin.newDrawable("white", Color.LIGHT_GRAY);
		skin.add("default", sps);

	}

	public static void play(String name){
		assets.get("sounds/" + name + ".wav", Sound.class).play();
	}

	public static void ambience(String name){
		assets.get("ambience/" + name, Sound.class).loop();
	}

	public static int count(){
		return assets.getLoadedAssets();
	}

	public static Array<String> list(){
		return assets.getAssetNames();
	}

	public static Texture getTexture(String name){
		return assets.get(name, Texture.class);
	}

	public static float getProgress(){
		return assets.getProgress();
	}

	public static TextureRegion[] getTextureRegions(String name, int cols, int rows, int count){

		TextureRegion[] frames = new TextureRegion[cols * rows];

		Texture tex_tmp = assets.get(name, Texture.class);

		TextureRegion[][] tmp = TextureRegion.split(tex_tmp, tex_tmp.getWidth() / cols, tex_tmp.getHeight() / rows);

		TextureRegion[] filtered = new TextureRegion[count];

		int index = 0;

		for (int i = 0; i < cols; i ++)
		{
			for (int j = 0; j < rows; j++)
			{
				frames[index] = tmp[i][j];
				index++;
			}
		}

		for (int i = 0; i < count; i++)
		{
			filtered[i] = frames[i];
		}

		return filtered;
	}

	public static Skin skin(){
		return skin;
	}

}
