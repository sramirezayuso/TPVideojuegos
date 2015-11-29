package com.mygdx.game.cameras;

import com.badlogic.gdx.math.Matrix4;

public class OrthographicProjection {

	
	public static Matrix4 getProjection(float width,float height,float z_far,float z_near){
		Matrix4 ans = new Matrix4(new float[] { 2 / width, 0, 0, 0,
											0,2 / height, 0, 0,
											0, 0, -2 / (z_far - z_near),0, 
											0, 0,-( z_near) / (z_far - z_near), 1 });

		
		return new Matrix4().setToOrtho(-2f, 2f, -2f, 2f, -0.1f, 100f);
	}
	
	
		
	
}
