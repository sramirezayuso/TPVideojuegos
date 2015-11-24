package com.mygdx.game.lights;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.cameras.Cam;
import com.mygdx.game.cameras.OrthographicProjection;

public class DirectionalLight extends Light {

	float z_near=0.1f;
	float z_far=100f;
	public DirectionalLight(ShaderProgram shader, Vector3 m_Position,
			Vector3 m_Direction, Vector3 light_color) {
		super(shader,GL_LIGHT0
		         , new Vector3(new float[]{ 0.0f, 0.0f, 0.0f })
		         , new Vector3(new float[]{ 1.0f, 1.0f, 1.0f })
		         , new Vector3(new float[]{ 1.0f, 1.0f, 1.0f})
		         , m_Position
		         , new Vector3(new float[]{ 0.0f, 0.0f, 1.0f})
		         ,  0.0f
		         , 180.0f
		         , 1.0f
		         , 0.0f
		         ,  0.0f ,
		         light_color
		         );
	}
	@Override
	public void setParameters() {
		// TODO Auto-generated method stub
		super.setParameters();
		shader.setUniformf("spotlightDirection_3",m_SpotDirection );
	}
	public void setShadowParameters(ShaderProgram shader,Cam cam) {
	
		shader.setUniformf("TRS",m_SpotDirection );
		Matrix4 VP=new Matrix4(cam.getVP());
		Matrix4 MVP=
		shader.setUniformf("u_mvp",cam.getVP());
		
	}
	public Vector3 getPosition() {
		return m_Position;
	}
	public Matrix4 getVP() {
		Matrix4 V=getV();
		Matrix4 P=OrthographicProjection.getProjection(2, 2, z_far, z_near);
		return P.mul(V);
	}
	
	
	public Matrix4 getV(){
		return this.getTR().inv();
	}
	public Matrix4 getTR() {
		return getT().mul(getR());
	}
		
	private Matrix4 getR() {
		return new Matrix4();
	}
	public Matrix4 getT() {
		return new Matrix4(new float[] { 1, 0, 0, m_Position.x, 0, 1, 0,
				m_Position.y, 0, 0, 1, m_Position.z, 0, 0, 0, 1 }).tra();
	}
	public float getFar() {
		return z_far;
	}
}
