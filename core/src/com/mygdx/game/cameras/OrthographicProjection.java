package com.mygdx.game.cameras;

import com.badlogic.gdx.math.Matrix4;

public class OrthographicProjection {

	
	public static Matrix4 getProjection(float width,float height,float z_far,float z_near){
//		Matrix4 ans = new Matrix4(new float[] { 2 / width, 0, 0, 0,
//											0,2 / height, 0, 0,
//											0, 0, -2 / (z_far - z_near),0, 
//											0, 0,-( z_near) / (z_far - z_near), 1 });
		float left=-width;
		float right=width;
		float top=height;
		float bottom=-height;
		float x_orth = 2 / (right - left);
		float y_orth = 2 / (top - bottom);
		float z_orth = -2 / (z_far - z_near);

		float tx = -(right + left) / (right - left);
		float ty = -(top + bottom) / (top - bottom);
		float tz = -(z_far + z_near) / (z_far - z_near);

		Matrix4 ans = new Matrix4(new float[] { x_orth, 0, 0, 0,
				0,y_orth, 0, 0,
				0, 0, z_orth,0, 
				tx, ty,tz, 1 });

		
		//return new Matrix4().setToOrtho(-width, width, -height, height, z_near, z_far);
		return ans;
	}
	
	
		
	
}
