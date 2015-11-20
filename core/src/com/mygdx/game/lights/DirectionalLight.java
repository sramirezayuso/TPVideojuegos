package com.mygdx.game.lights;

import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;

public class DirectionalLight extends Light {

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
	

}
