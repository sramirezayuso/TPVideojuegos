package com.mygdx.game.models;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class Cube extends Model {
	static Mesh mesh;
	static Texture img;
	private static String dataFolder = null;
	private static final String OBJ_FILE="roadTile_001.obj";
	
	private static final String IMG_FILE="tile.png";
	public Cube(String dataFolder, Vector3 position) {
		super(new MetallicMaterial(), position);
		this.dataFolder=dataFolder;
		loadMesh();
		loadImg();
		
	}
	private static void loadMesh() {
		if (mesh == null) {
			mesh=readMesh(OBJ_FILE, dataFolder);
		}
	}
	@Override
	protected Texture getTexture() {
		return img;
	}

	@Override
	protected Mesh getMesh() {
		return mesh;
	}
	private static void loadImg() {
		if (img == null)
			img = readTexture(IMG_FILE, dataFolder);

	}

}
