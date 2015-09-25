package com.mygdx.game.lights;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;

public class Light {
	/* LightParameter */
	protected static final int GL_AMBIENT = 0x1200;
	protected static final int GL_DIFFUSE = 0x1201;
	protected static final int GL_SPECULAR = 0x1202;
	protected static final int GL_POSITION = 0x1203;
	protected static final int GL_SPOT_DIRECTION = 0x1204;
	protected static final int GL_SPOT_EXPONENT = 0x1205;
	protected static final int GL_SPOT_CUTOFF = 0x1206;
	protected static final int GL_CONSTANT_ATTENUATION = 0x1207;
	protected static final int GL_LINEAR_ATTENUATION = 0x1208;
	protected static final int GL_QUADRATIC_ATTENUATION = 0x1209;

	/* LightName */
	protected static final int GL_LIGHT0 = 0x4000;
	protected static final int GL_LIGHT1 = 0x4001;
	protected static final int GL_LIGHT2 = 0x4002;
	protected static final int GL_LIGHT3 = 0x4003;
	protected static final int GL_LIGHT4 = 0x4004;
	protected static final int GL_LIGHT5 = 0x4005;
	protected static final int GL_LIGHT6 = 0x4006;
	protected static final int GL_LIGHT7 = 0x4007;

	int m_LightID;
	Vector3 m_Ambient;
	Vector3 m_Diffuse;
	Vector3 m_Specular;

	Vector3 m_Position;
	Vector3 m_SpotDirection;
	float m_SpotExponent;
	float m_SpotCutoff;
	float m_ConstantAttenuation;
	float m_LinearAttenuation;
	float m_QuadraticAttenuation;

	public Light(int m_LightID, Vector3 m_Ambient, Vector3 m_Diffuse,
			Vector3 m_Specular, Vector3 m_Position, Vector3 m_SpotDirection,
			float m_SpotExponent, float m_SpotCutoff,
			float m_ConstantAttenuation, float m_LinearAttenuation,
			float m_QuadraticAttenuation) {
		super();
		this.m_LightID = m_LightID;
		this.m_Ambient = m_Ambient;
		this.m_Diffuse = m_Diffuse;
		this.m_Specular = m_Specular;
		this.m_Position = m_Position;
		this.m_SpotDirection = m_SpotDirection;
		this.m_SpotExponent = m_SpotExponent;
		this.m_SpotCutoff = m_SpotCutoff;
		this.m_ConstantAttenuation = m_ConstantAttenuation;
		this.m_LinearAttenuation = m_LinearAttenuation;
		this.m_QuadraticAttenuation = m_QuadraticAttenuation;
	}

//	public void activate() {
//		Gdx.gl20.glEnable(m_LightID);
//		Gdx.gl10.glLightfv(m_LightID, GL_AMBIENT, m_Ambient.x);
//		glLightfv(m_LightID, GL_DIFFUSE, m_Diffuse.x);
//		glLightfv(m_LightID, GL_SPECULAR, m_Specular.x);
//		glLightfv(m_LightID, GL_POSITION, m_Position.x);
//		glLightfv(m_LightID, GL_SPOT_DIRECTION, m_SpotDirection.x);
//		glLightf(m_LightID, GL_SPOT_EXPONENT, m_SpotExponent);
//		glLightf(m_LightID, GL_SPOT_CUTOFF, m_SpotCutoff);
//		glLightf(m_LightID, GL_CONSTANT_ATTENUATION, m_ConstantAttenuation);
//		glLightf(m_LightID, GL_LINEAR_ATTENUATION, m_LinearAttenuation);
//		glLightf(m_LightID, GL_QUADRATIC_ATTENUATION, m_QuadraticAttenuation);
//	}

}
