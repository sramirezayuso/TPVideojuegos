package com.mygdx.game.cameras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.mygdx.game.MyGdxGame;
//Gdx.graphics.getWidth()
public class Perspective extends Cam {
	
	private static final float FOVy = 60.0f;
	private static float FOVx = FOVy * Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
	
	public Perspective(MyGdxGame app) {
		super(app);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected Matrix4 getProjection() {
		
		
		
		Matrix4 ans = new Matrix4(new float[] {
				1f/((float) Math.tan(Math.toRadians(FOVx) * 0.5f)),0,0,0,
				0,1f/((float) Math.tan(Math.toRadians(FOVy) * 0.5f	)),0,0,
				0,0,-(z_far+z_near)/(z_far-z_near),-1,
				0,0,-(2*z_far*z_near)/(z_far-z_near),0
		});
		return ans;
	}

	
	@Override
	protected void changeCamera() {
		Cam newCamera=new OrthographicCam(this.application);
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
}