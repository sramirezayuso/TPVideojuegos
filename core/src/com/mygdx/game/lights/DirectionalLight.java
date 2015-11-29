package com.mygdx.game.lights;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.cameras.Cam;
import com.mygdx.game.cameras.OrthographicProjection;

public class DirectionalLight extends Light {

	float z_near = -0.01f;
	float z_far = 100f;

	public DirectionalLight(ShaderProgram shader, Vector3 m_Position, Vector3 m_Direction, Vector3 light_color) {
		super(shader, GL_LIGHT0, new Vector3(new float[] { 0.0f, 0.0f, 0.0f }), new Vector3(new float[] { 1.0f, 1.0f,
				1.0f }), new Vector3(new float[] { 1.0f, 1.0f, 1.0f }), m_Position, m_Direction, 0.0f, 180.0f, 1.0f,
				0.0f, 0.0f, light_color);
	}

	@Override
	public void setParameters(Matrix4 modelMatrix,Cam cam) {
		// TODO Auto-generated method stub
		super.setParameters(modelMatrix,cam);
		shader.setUniformf("spotlightDirection_3", m_SpotDirection);
		setShadowParameters(shader, cam, modelMatrix);
	}

	public void setShadowParameters(ShaderProgram shader, Cam cam,Matrix4 modelMatrix) {

		// shader.setUniformf("TRS",m_SpotDirection );
		Matrix4 VP = getVP();
		Matrix4 mvp_light = new Matrix4(modelMatrix);
		
		mvp_light.mul(VP);
		
		
		shader.setUniformMatrix("u_mvp_light", mvp_light);

		shader.setUniformf("LightPosW_3", m_Position);
		// FIX
		// Matrix4 MVP=
		// shader.setUniformf("u_mvp",cam.getVP());

	}

	private Vector3 getUpVector() {
		Vector3 right = this.getRightVector();
		Vector3 direction = this.getDirection();

		Vector3 up = right.crs(direction);
		return up;
	}

	private Vector3 getDirection() {
		Vector3 light_pos = new Vector3(getPosition());
		Vector3 lookAtpos = m_SpotDirection;

		return light_pos.sub(lookAtpos);
	}

	private Vector3 getRightVector() {
		// TODO Auto-generated method stub
		return null;
	}

	public Vector3 getPosition() {
		return m_Position;
	}

	public Matrix4 getVP() {
		Matrix4 V = getV();
		Matrix4 P = OrthographicProjection.getProjection(2, 2, z_far, z_near);
		
		//System.out.println(P);
		Camera aux=new OrthographicCamera();
		aux.translate(m_Position);
		aux.lookAt(m_SpotDirection);
		aux.far=z_far;
		aux.near=z_near;
		aux.update();
		Matrix4 paux=aux.projection;
				
				
		return P.mul(V);
	}
	//revisado
	public Matrix4 getV() {
		// return this.getTR().inv();
		Vector3 up = new Vector3(new float[] { 0, 1, 0 });
		Matrix4 m1= LookAtRH(m_Position, m_SpotDirection, up);
		
		
		
		
		
		return m1;
	}

	public Matrix4 getTR() {
		return getT().mul(getR());
	}

	private Matrix4 getR() {
		return new Matrix4();
	}

	public Matrix4 getT() {
		return new Matrix4(new float[] { 1, 0, 0, m_Position.x, 0, 1, 0, m_Position.y, 0, 0, 1, m_Position.z, 0, 0, 0,
				1 }).tra();
	}

	public float getFar() {
		return z_far;
	}

	// view matrix
	public Matrix4 LookAtRH(Vector3 eye, Vector3 target, Vector3 up) {
		
		
		//version original
		Vector3 zaxis = new Vector3(eye).sub(target).nor(); // The "forward"
		// vector.
		Vector3 xaxis = (new Vector3(up).crs(zaxis)).nor();// The "right"
		// vector.
		if(xaxis.x==0 && xaxis.y==0 && xaxis.z==0)
			 xaxis=new Vector3(new float[]{1,0,0});
		
		Vector3 yaxis = new Vector3(zaxis).crs(xaxis).nor(); // The "up" vector.

		// the final view matrix

		Matrix4 W = new Matrix4(new float[] { xaxis.x, xaxis.y, xaxis.z, 0, 
									yaxis.x, yaxis.y, yaxis.z, 0, 
									zaxis.x,zaxis.y, zaxis.z, 0, 
									eye.x, eye.y, eye.z, 1 });
		return W.inv();
	}
	
	
		

}
