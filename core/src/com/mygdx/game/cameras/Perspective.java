package com.mygdx.game.cameras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.mygdx.game.MyGdxGame;

//Gdx.graphics.getWidth()
public class Perspective extends Cam {
	private static float aspectRatio=Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
	private static final float FOVy = 60.0f;
	
	private static float FOVx = FOVy * aspectRatio;

	private static Matrix4 projection = null;

	public Perspective(MyGdxGame app) {
		super(app);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Matrix4 getProjection() {

		if (projection == null) {
			
			projection = new Matrix4(new float[] { 
					1f / ((float) Math.tan((FOVy*(Math.PI/180))/2.0)*aspectRatio), 0, 0,0
					, 0, 1f / ((float) Math.tan(Math.toRadians(FOVy) * 0.5f)), 0, 0
					, 0, 0,	-(z_far + z_near) / (z_far - z_near), -1
					, 0, 0, -(2 * z_far * z_near) / (z_far - z_near), 0 });
			System.out.println(projection);

			
		}

		
		
		return projection;
	}

	@Override
	protected void changeCamera() {
		Cam newCamera = new OrthographicCam(this.application);
		newCamera.camPosition = this.camPosition;
		newCamera.lookAtVector = this.lookAtVector;
		newCamera.last_x = this.last_x;
		newCamera.last_y = this.last_y;
		newCamera.x_rot = this.x_rot;
		newCamera.y_rot = this.y_rot;
		newCamera.pitch = this.pitch;
		newCamera.yaw = this.yaw;

		application.changeCamera(newCamera);

	};
}
