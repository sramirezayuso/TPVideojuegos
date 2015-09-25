package com.mygdx.game.models;

import com.badlogic.gdx.math.Vector3;

public class MetallicMaterial extends Material {

	public MetallicMaterial() {
		super(new Vector3(new float[]{ 0.2f, 0.2f, 0.2f})
	            , new Vector3(new float[]{0.8f, 0.8f, 0.8f})
	            , new Vector3(new float[]{0.0f, 0.0f, 0.0f})
	            , new Vector3(new float[]{0.0f, 0.0f, 0.0f})
	            ,  0f);
	}

}
