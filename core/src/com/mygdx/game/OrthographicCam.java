package com.mygdx.game;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class OrthographicCam extends Cam {
	private static final int LEFT_KEY = 21;
	private static final int RIGHT_KEY = 22;
	private static final int UP_KEY = 19;
	private static final int DOWN_KEY = 20;
	private static final float DELTA_KEY_PRESSED = 0.2F;

	@Override
	protected Matrix4 getProjection() {
		float width = 2;
		float height = 2;
		float z_far = 100;
		float z_near = 0.1f;

		Matrix4 ans = new Matrix4(new float[] { 1 / width, 0, 0, 0, 0,
				1 / height, 0, 0, 0, 0, -2 / (z_far - z_near), 0, 0, 0,
				-(z_far + z_near) / (z_far - z_near), 1 });

		// retorna matrix de prueba, en realidad deberia devolver ans;
		Matrix4 aux = new Matrix4(new float[] { 1, 0, 0, 0,// primer col
				0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });

		return ans;
	}

	// MOVIMIENTO CON TECLADO,¿por que no me deja ponerlo en la superclase?
	@Override
	public boolean keyDown(int keycode) {
		System.out.println("tecla presionada:" + keycode);

		float x_movement = 0;
		float y_movement = 0;
		float z_movement = 0;

		Vector3 forwardVector = new Vector3(new Vector3(camPosition)	.sub(lookAtVector)).nor(); // The "forward" vector
		Vector3 rightVector=new Vector3(new Vector3(up).crs(forwardVector));//normal(cross(up, zaxis));// The "right" vector.
		System.out.println(forwardVector);
		
		Vector3 movementVector;
		int currentDelta;
		switch (keycode) {
		case DOWN_KEY: {
			//z_movement=DELTA_KEY_PRESSED*forwardVector.mulAdd(vec, scalar);
			//movementVector=forwardVector;
			z_movement=DELTA_KEY_PRESSED*forwardVector.z;
			break;
		}
		case UP_KEY: {
			z_movement=-DELTA_KEY_PRESSED*forwardVector.z;
			break;
		}
		case LEFT_KEY: {
			x_movement=-DELTA_KEY_PRESSED*rightVector.x;
			break;
		}
		case RIGHT_KEY: {
			x_movement=DELTA_KEY_PRESSED*rightVector.x;
			break;
		}

		}
		
		Vector3 movement=new Vector3(new float[]{x_movement,y_movement,z_movement});
		getPosition().add(movement);
		

		return false;
	}

	
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
