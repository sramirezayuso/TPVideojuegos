package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.models.Ship;

public class MyGdxGame extends ApplicationAdapter {
	public static String dataFolder = "./data/";
	SpriteBatch batch;
	ShaderProgram shaderProgram;

	private Cam camera;
	private Ship spaceShip1;
	private Ship spaceShip2;
	private List<Ship> objects=new ArrayList<Ship>();
	@Override
	public void create() {
		String vs = Gdx.files.internal(dataFolder + "LightsVS.glsl")
				.readString();
		String fs = Gdx.files.internal(dataFolder + "LightsFS.glsl")
				.readString();
		shaderProgram = new ShaderProgram(vs, fs);
		System.out.println(shaderProgram.getLog());
//		ModelLoader<?> loader = new ObjLoader();

		spaceShip1 = new Ship(dataFolder,new Vector3(new float[]{0f,0f,0f}));
		spaceShip2 = new Ship(dataFolder,new Vector3(new float[]{0f,0.5f,0f}));
		objects.add(spaceShip1);
		objects.add(spaceShip2);
		// camara
		camera = new OrthographicCam();
		Gdx.input.setInputProcessor(camera);
		Vector3 cam_pos = new Vector3(new float[] { 0f, 0f, 0.1f });
		camera.setPosition(cam_pos);

	}

	@Override
	public void render() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		Gdx.gl.glDepthFunc(GL20.GL_LESS);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		
		shaderProgram.begin();
		// original
		// shaderProgram.setUniformMatrix("u_worldView", new Matrix4()); // aca
		// trabajar
		// Intento de agregado de la camara aca abajo

		// camera.lookAt(new Vector3(new float[]{1f,-1f,0f}));
		

		//spaceshipMesh.render(shaderProgram, GL20.GL_TRIANGLES);
		//spaceShip.render(shaderProgram,  GL20.GL_TRIANGLES);
		for(Ship ship:objects){
			ship.render(shaderProgram, camera, GL20.GL_TRIANGLES);
		}
		
		shaderProgram.end();
	}

}
