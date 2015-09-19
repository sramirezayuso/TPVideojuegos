package com.mygdx.game.models;

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
import com.mygdx.game.Cam;

public class Ship {
	static Mesh spaceshipMesh;
	static Texture img;
	public static String dataFolder = null;
	private Vector3 position;
	public Ship(String dataFolder,Vector3 position) {
		this.dataFolder = dataFolder;
		loadImg();
		loadMesh();
		this.position=position;
	}

	private static void loadMesh() {
		if (spaceshipMesh == null) {
			ModelLoader<?> loader = new ObjLoader();
			ModelData data = loader.loadModelData(Gdx.files.internal(dataFolder
					+ "ship.obj"));
			spaceshipMesh = new Mesh(true, data.meshes.get(0).vertices.length,
					data.meshes.get(0).parts[0].indices.length,
					VertexAttribute.Position(), VertexAttribute.Normal(),
					VertexAttribute.TexCoords(0));
			spaceshipMesh.setVertices(data.meshes.get(0).vertices);
			spaceshipMesh.setIndices(data.meshes.get(0).parts[0].indices);
		}
	}

	private static void loadImg(){
		if(img==null)
			img = new Texture(dataFolder + "ship.png");
			
	}
	
	public void render(ShaderProgram shader, Cam camera, int primitiveType) {
		
		Matrix4 modelMatrix = new Matrix4().translate(position);	
		Matrix4 viewProjection = camera.getVP();
		
		Matrix4 res = new Matrix4(modelMatrix);
		res.mul(viewProjection);
		
		//modelMatrix.mul(viewProjection);
		
		

		shader.setUniformMatrix("u_mvp", res);
		shader.setUniformi("u_texture", 0);
		
		spaceshipMesh.render(shader, primitiveType);

		// usar aca el bind de la textura, no esta bien que este suelto
		this.bind();
	}

	void bind() {
		img.bind();

	}
}
