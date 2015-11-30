package com.mygdx.game.cameras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;

public class OrthographicCam extends Cam {
	private static float width = 2;
	private static float height = 2;

	public OrthographicCam(MyGdxGame app) {
		super(app);
	}

	private Matrix4 projection=null;
	@Override
	protected void changeCamera() {
		Cam newCamera=new Perspective(this.application);
		newCamera.camPosition=this.camPosition;
		newCamera.lookAtVector=this.lookAtVector;
		newCamera.last_x=this.last_x;
		newCamera.last_y=this.last_y;
		newCamera.x_rot=this.x_rot;
		newCamera.y_rot=this.y_rot;
		newCamera.pitch=this.pitch;
		newCamera.yaw=this.yaw;
		
		
		application.changeCamera(newCamera);
		
		
	};
	@Override
	protected Matrix4 getProjection() {
		
		

//		Matrix4 ans = new Matrix4(new float[] { 1 / width, 0, 0, 0,
//												0,1 / height, 0, 0,
//												0, 0, -2 / (z_far - z_near),0, 
//												0, 0,-(z_far + z_near) / (z_far - z_near), 1 });
//
//		
		if(projection==null)
			projection=OrthographicProjection.getProjection(width, height, z_far, z_near);
		
		return projection;
	}

	
}
