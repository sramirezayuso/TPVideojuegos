package com.mygdx.game.models;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.cameras.Cam;
import com.mygdx.game.lights.DirectionalLight;
import com.mygdx.game.lights.Light;

public abstract class Model {
	protected Vector3 position;
	protected Material material;

	public Model(Material material, Vector3 position) {
		this.position = position;
		this.material = material;
	}

	public void setLightParameters(Shader shader) {

	}

	public void renderShadow(Cam camera,int primitiveType,ShaderProgram shader,DirectionalLight light){
		shader.begin();
		Matrix4 modelMatrix = new Matrix4().translate(position);
		Matrix4 viewProjection = camera.getVP();

		Matrix4 res = new Matrix4(modelMatrix);
		res.mul(viewProjection);
		shader.setUniformMatrix("u_m", modelMatrix);
		float u_cameraFar=camera.getFar();
		
		
		//texture.bind()//SHADOW MAP
		shader.setUniformf("u_cameraFar",u_cameraFar);
		shader.setUniformf("u_lightPosition", light.getPosition());
		
		shader.setUniformMatrix("u_vp", viewProjection);
	}
	
	public void render(List<Light> lights, Cam camera, int primitiveType) {

		// //iluminacion, se hace una pasada por cada luz
		for (Light light : lights) {
			ShaderProgram shader = light.getShader();
			shader.begin();
			light.setParameters();
			
			Mesh mesh = getMesh();
			Texture texture = getTexture();

			Matrix4 modelMatrix = new Matrix4().translate(position);
			Matrix4 viewProjection = camera.getVP();

			Matrix4 res = new Matrix4(modelMatrix);
			res.mul(viewProjection);
			camera.setParameters(shader);
			// modelMatrix.mul(viewProjection);
			if (texture != null)
				texture.bind();
			shader.setUniformMatrix("u_m", modelMatrix);
			
			//texture.bind()//SHADOW MAP
			
			
			shader.setUniformMatrix("u_mvp", res);
			shader.setUniformi("u_texture", 0);

			// iluminacion
			// shader.setUniformf("in_normal",normal);
			material.setParameters(shader);
			camera.setParameters(shader);

			mesh.render(shader, primitiveType);
			shader.end();
		}
	}

	protected abstract Texture getTexture();

	protected abstract Mesh getMesh();

	static protected Mesh readMesh(String filename, String dataFolder) {

		ModelLoader<?> loader = new ObjLoader();
		ModelData data = loader.loadModelData(Gdx.files.internal(dataFolder
				+ filename));
		Mesh mesh = new Mesh(true, data.meshes.get(0).vertices.length,
				data.meshes.get(0).parts[0].indices.length,
				VertexAttribute.Position(), VertexAttribute.Normal(),
				VertexAttribute.TexCoords(0));
		mesh.setVertices(data.meshes.get(0).vertices);
		mesh.setIndices(data.meshes.get(0).parts[0].indices);
		return mesh;
	}

	static protected Texture readTexture(String filename, String dataFolder) {
		Texture texture = new Texture(dataFolder + filename);
		return texture;
	}
}
