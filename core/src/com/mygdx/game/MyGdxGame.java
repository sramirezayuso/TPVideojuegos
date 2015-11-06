package com.mygdx.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.cameras.Cam;
import com.mygdx.game.cameras.OrthographicCam;
import com.mygdx.game.lights.Light;
import com.mygdx.game.lights.PointLight;
import com.mygdx.game.lights.SpotLight;
import com.mygdx.game.models.Cube;
import com.mygdx.game.models.Model;
import com.mygdx.game.models.Ship;

public class MyGdxGame extends ApplicationAdapter {
	public static String dataFolder = "./data/";
	SpriteBatch batch;
	ShaderProgram shaderProgram;

	private Cam camera;
	private InputMultiplexer multiplexer;
	private Ship spaceShip1;
	private Ship spaceShip2;
	private List<Model> objects=new ArrayList<Model>();
	private List<Light> lights=new ArrayList<Light>();
	@Override
	public void create() {
		String vs = Gdx.files.internal(dataFolder + "SpotLightVS.glsl")
				.readString();
		String fs = Gdx.files.internal(dataFolder + "SpotLightsFS.glsl")
				.readString();
		shaderProgram = new ShaderProgram(vs, fs);
		shaderProgram.pedantic = false;
		System.out.println(shaderProgram.getLog());

		
		//objetos de la escena
		spaceShip1 = new Ship(dataFolder,new Vector3(new float[]{0f,0f,0f}));
		spaceShip2 = new Ship(dataFolder,new Vector3(new float[]{1f,0f,0f}));
		objects.add(spaceShip1);
		objects.add(spaceShip2);
		
		Cube cube=new Cube(dataFolder, new Vector3(new float[]{0f,-0.1f,0f}));
		objects.add(cube);
		// camara
		
		
		camera = new OrthographicCam(this);
		multiplexer=new InputMultiplexer(camera);
		Gdx.input.setInputProcessor(multiplexer);
		Vector3 cam_pos = new Vector3(new float[] { 0f, 0f, 1f });
		camera.setPosition(cam_pos);
		//lights
//		lights.add(new PointLight(
//				new Vector3(new float[] { 0f, 1f, 0.1f }),
//				new Vector3(new float[] { 0f, 1f, 0f })
//				));
//		
		lights.add(new SpotLight(
				
				new Vector3(new float[] { 6f, 10f, 0.2f })
				, new Vector3(new float[] { 1f, 0f, 0f })
				,new Vector3(new float[]{ 0.0f, -0.1f, 0.0f})));
	}
	

	public void changeCamera(Cam cam){
		if(cam!=null){
			multiplexer.removeProcessor(cam);// se quita el listener para camara vieja
			this.camera=cam;
			multiplexer.addProcessor(cam);//agrega listener camara nueva
		
		}
	}

	@Override
	public void render() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		Gdx.gl.glDepthFunc(GL20.GL_LESS);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		
		shaderProgram.begin();
		// original
		// shaderProgram.setUniformMatrix("u_worldView", new Matrix4()); // aca
		// trabajar
		// Intento de agregado de la camara aca abajo

		// camera.lookAt(new Vector3(new float[]{1f,-1f,0f}));
		

		//spaceshipMesh.render(shaderProgram, GL20.GL_TRIANGLES);
		//spaceShip.render(shaderProgram,  GL20.GL_TRIANGLES);
		for(Model model:objects){
			model.render(shaderProgram,lights, camera, GL20.GL_TRIANGLES);
		}
		
		shaderProgram.end();
		String shader_log=shaderProgram.getLog();
		if(shader_log.length()>0)
		System.out.println(shader_log);
	}

}
