package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame extends ApplicationAdapter {
	public static String dataFolder = "./data/";
	SpriteBatch batch;
	Texture img;

	Mesh spaceshipMesh;
	ShaderProgram shaderProgram;

	 private Cam camera;

	@Override
	public void create() {
		img = new Texture(dataFolder+"ship.png");
		String vs = Gdx.files.internal(dataFolder + "defaultVS.glsl")
				.readString();
		String fs = Gdx.files.internal(dataFolder + "defaultFS.glsl")
				.readString();
		shaderProgram = new ShaderProgram(vs, fs);
		System.out.println(shaderProgram.getLog());
		ModelLoader<?> loader = new ObjLoader();
		ModelData data = loader.loadModelData(Gdx.files.internal(dataFolder
				+ "ship.obj"));

		spaceshipMesh = new Mesh(true, data.meshes.get(0).vertices.length,
				data.meshes.get(0).parts[0].indices.length,
				VertexAttribute.Position(),
				VertexAttribute.Normal(), VertexAttribute.TexCoords(0));
		spaceshipMesh.setVertices(data.meshes.get(0).vertices);
		spaceshipMesh.setIndices(data.meshes.get(0).parts[0].indices);
		
		//camara
		camera=new OrthographicCam();
		Gdx.input.setInputProcessor(camera);
		Vector3 pos=new Vector3(new float[]{0f,0.3f,-0.5f});
		camera.setPosition(pos);
		
	}

	@Override
	public void render() {
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		Gdx.gl.glDepthFunc(GL20.GL_LESS);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		
		img.bind();
		shaderProgram.begin();
		//original 
		//shaderProgram.setUniformMatrix("u_worldView", new Matrix4()); // aca
																		// trabajar
		//Intento de agregado de la camara aca abajo
		
		
		//camera.lookAt(new Vector3(new float[]{1f,-1f,0f}));
		Matrix4 worldView=camera.getMVP();
		
		
		
		
		shaderProgram.setUniformMatrix("u_worldView",worldView);
			shaderProgram.setUniformi("u_texture", 0);
		spaceshipMesh.render(shaderProgram, GL20.GL_TRIANGLES);
		shaderProgram.end();
	}
	
	
}
