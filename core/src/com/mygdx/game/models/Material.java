package com.mygdx.game.models;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;

public class Material {
	Vector3 m_Ambient;
	Vector3 m_Diffuse;
	Vector3 m_Specular;
	Vector3 m_Emission;
	float m_Shininess;

	public Material(Vector3 m_Ambient, Vector3 m_Diffuse, Vector3 m_Specular,
			Vector3 m_Emission, float m_Shininess) {
		super();
		this.m_Ambient = m_Ambient;
		this.m_Diffuse = m_Diffuse;
		this.m_Specular = m_Specular;
		this.m_Emission = m_Emission;
		this.m_Shininess = m_Shininess;
	}

	public void setParameters(ShaderProgram shader) {
		shader.setUniformf("MaterialEmissive",m_Emission);
		shader.setUniformf("MaterialDiffuse",m_Diffuse);
		shader.setUniformf("MaterialSpecular",m_Specular);
		shader.setUniformf("MaterialShininess", m_Shininess);;
	}

}
