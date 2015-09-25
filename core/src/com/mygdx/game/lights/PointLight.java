package com.mygdx.game.lights;

import com.badlogic.gdx.math.Vector3;

public class PointLight extends Light {

	public PointLight() {
		super(GL_LIGHT0
         , new Vector3(new float[]{ 0.0f, 0.0f, 0.0f })
         , new Vector3(new float[]{ 1.0f, 1.0f, 1.0f })
         , new Vector3(new float[]{ 1.0f, 1.0f, 1.0f})
         , new Vector3(new float[]{ 0.0f, 0.0f, 1.0f})
         , new Vector3(new float[]{ 0.0f, 0.0f, 1.0f})
         ,  0.0f
         , 180.0f
         , 1.0f
         , 0.0f
         ,  0.0f );
	}

}
