package com.mygdx.game.models;

import com.badlogic.gdx.math.Vector3;

public class MetallicMaterial extends Material {

	public MetallicMaterial() {
		super(new Vector3(new float[]{ 0f, 0f, 0f})
	            , new Vector3(new float[]{1f, 1f, 1f})
	            , new Vector3(new float[]{0.0f, 0.0f, 0.0f})
	            , new Vector3(new float[]{0.5f, 0.5f, 0.5f})
	            ,  1f);
	}

}
