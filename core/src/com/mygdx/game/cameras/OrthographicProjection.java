package com.mygdx.game.cameras;

import com.badlogic.gdx.math.Matrix4;

public class OrthographicProjection {

	
	public static Matrix4 getProjection(float width,float height,float z_far,float z_near){
		Matrix4 ans = new Matrix4(new float[] { 1 / width, 0, 0, 0,
				0,1 / height, 0, 0,
				0, 0, -2 / (z_far - z_near),0, 
				0, 0,-(z_far + z_near) / (z_far - z_near), 1 });

		return ans;
	}
	
}
