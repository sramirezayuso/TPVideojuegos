package com.mygdx.game.lights;

import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;

public  class PointLight extends Light {

	public PointLight(ShaderProgram shader,Vector3 position,Vector3 light_color) {
		super(shader,
			GL_LIGHT0
         , new Vector3(new float[]{ 0.0f, 0.0f, 0.0f })
         , new Vector3(new float[]{ 1.0f, 1.0f, 1.0f })
         , new Vector3(new float[]{ 1.0f, 1.0f, 1.0f})
         , position
         , new Vector3(new float[]{ 0.0f, 0.0f, 1.0f})
         ,  0.0f
         , 180.0f
         , 1.0f
         , 0.0f
         ,  0.0f ,
         light_color
         );
	}

	public Vector3 getPosition() {
		return m_Position;
	}

}
