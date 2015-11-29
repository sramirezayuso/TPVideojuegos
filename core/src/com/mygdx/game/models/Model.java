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

	
	//render que se emplea para crear el shadow map
	public void renderShadowMapping(int primitiveType,ShaderProgram shader,DirectionalLight light){
		shader.begin();
		
		getTexture().bind(0);
		Matrix4 modelMatrix = new Matrix4(getModelMatrix());
		Matrix4 viewProjection = light.getVP();

		//calcular mvp
		Matrix4 mvp=modelMatrix.mul(viewProjection);
		shader.setUniformMatrix("u_mvp", mvp);
		
		//Texture texture = getTexture();
		//texture.bind();//SHADOW MAP

		shader.setUniformf("u_cameraFar", light.getFar());
		
		
		shader.setUniformf("u_lightPosition", light.getPosition());
		
		renderMesh(shader, primitiveType);
		shader.end();
	}
	private void renderMesh(ShaderProgram shader, int primitiveType){

		Mesh mesh = getMesh();
		mesh.render(shader, primitiveType);
	}
	
	
	//render que se emplea para dibujar las sombras
	public void renderShadow(ShaderProgram shader,DirectionalLight dir_light,Cam cam,int primitiveType){
		shader.begin();
		
		setShadowParameters(shader, cam);
		
		dir_light.setShadowParameters(shader, cam,getModelMatrix());
		renderMesh(shader, primitiveType);
		shader.end();
	}
	
	private void setShadowParameters(ShaderProgram shader,Cam cam){
		shader.setUniformi("u_shadowmap",1);
		Matrix4 mvp_cam = getMVP(cam);
		
		shader.setUniformMatrix("u_mvp_cam", mvp_cam);
	}
	
	private Matrix4 getMVP(Cam camera){
		Matrix4 modelMatrix = getModelMatrix();
		Matrix4 viewProjection = camera.getVP();

		Matrix4 res = new Matrix4(modelMatrix);
		res.mul(viewProjection);
		return res;
	}
	
	public void render(List<Light> lights, Cam camera, int primitiveType) {

		// //iluminacion, se hace una pasada por cada luz
		for (Light light : lights) {
			ShaderProgram shader = light.getShader();
			shader.begin();
			Matrix4 modelMatrix=getModelMatrix();
			light.setParameters(modelMatrix,camera);
			
			
			Texture texture = getTexture();
			
			Matrix4 res = getMVP(camera);

			camera.setParameters(shader);
			// modelMatrix.mul(viewProjection);
			if (texture != null)
				texture.bind(0);
			shader.setUniformMatrix("u_m", modelMatrix);
			
			
			
			shader.setUniformMatrix("u_mvp", res);
			shader.setUniformi("u_texture", 0);

			// iluminacion
			// shader.setUniformf("in_normal",normal);
			material.setParameters(shader);
			camera.setParameters(shader);

			renderMesh(shader, primitiveType);
			shader.end();
		}
	}

	protected Matrix4 getModelMatrix(){
		return new Matrix4().translate(position);
		
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
