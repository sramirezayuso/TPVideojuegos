package com.mygdx.game.models;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.cameras.Cam;
import com.mygdx.game.lights.Light;

public class Ship extends Model {
	static Mesh spaceshipMesh;
	static Texture img;
	private static String dataFolder = null;
	private static final String OBJ_FILE="ship.obj";
	private static final String IMG_FILE="ship.png";
	

	public Ship(String dataFolder, Vector3 position) {
		super(new MetallicMaterial(),position);
		this.dataFolder = dataFolder;
		loadImg();
		loadMesh();
	}
	
	private static void loadMesh() {
		if (spaceshipMesh == null) {
			spaceshipMesh=readMesh(OBJ_FILE, dataFolder);
		}
	}

	private static void loadImg() {
		if (img == null)
			img = readTexture(IMG_FILE, dataFolder);

	}
	
	

	@Override
	protected Texture getTexture() {
		return img;
	}

	@Override
	protected Mesh getMesh() {
		return spaceshipMesh;
	}


}
