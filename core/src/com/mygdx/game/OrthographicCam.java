package com.mygdx.game;

import com.badlogic.gdx.math.Matrix4;

public class OrthographicCam extends Cam {

	@Override
	protected Matrix4 getProjection() {
		float width=2;
		float height=2;
		float z_far=-1;
		float z_near=1f;
		
		
		Matrix4 ans=new Matrix4(new float[]{
											1/width,0,0,0,
											0,1/height,0,0,
											0,0,-2/(z_far-z_near),0,
											0,0,-(z_far+z_near)/(z_far-z_near),1
											});
		
		
		//retorna matrix de prueba, en realidad deberia devolver ans;
		Matrix4 aux= new Matrix4(new float[]{ 1,0,0,0,//primer col
										0,1,0,0,
										0,0,0,0,
										0,0,0,1});
					
		return ans;
	}

}
