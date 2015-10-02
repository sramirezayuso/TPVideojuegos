package com.mygdx.game.models;

import java.util.List;

import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.lights.Light;

public class Model {
	protected Material material;
	public Model(Material material) {
		this.material=material;
	}
	
	public void setLightParameters(Shader shader){
		
	}
	
}
