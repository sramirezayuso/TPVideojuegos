package com.mygdx.game.lights;

import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;

public class DirectionalLight extends Light {

	public DirectionalLight(ShaderProgram shader,int m_LightID, Vector3 m_Ambient,
			Vector3 m_Diffuse, Vector3 m_Specular, Vector3 m_Position,
			Vector3 m_SpotDirection, float m_SpotExponent, float m_SpotCutoff,
			float m_ConstantAttenuation, float m_LinearAttenuation,
			float m_QuadraticAttenuation, Vector3 m_light_color) {
		super(shader,m_LightID, m_Ambient, m_Diffuse, m_Specular, m_Position, m_SpotDirection,
				m_SpotExponent, m_SpotCutoff, m_ConstantAttenuation,
				m_LinearAttenuation, m_QuadraticAttenuation, m_light_color);
		// TODO Auto-generated constructor stub
	}
	

}
