package com.mygdx.game.cameras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;

//es una camara ortografica
public class Cam implements InputProcessor {
	protected Vector3 camPosition;
	
	
	protected final static float z_far = 100;
	
	protected final static float z_near = 0.1f;
	//protected Vector3 up = new Vector3(new float[] { 0f, 1f, 0f });

	protected Vector3 lookAtVector;

	// mouse
	protected int last_x = Gdx.graphics.getWidth() / 2;
	protected int last_y = Gdx.graphics.getHeight() / 2;

	protected float x_rot = 0;
	protected float y_rot = 0;

	protected float pitch = 0;
	protected float yaw = 0;

	// teclado
	private static final int LEFT_KEY = 21;
	private static final int RIGHT_KEY = 22;
	private static final int UP_KEY = 19;
	private static final int DOWN_KEY = 20;
	private static final float DELTA_KEY_PRESSED = 0.25F;

	//

	// cambio de camara
	protected MyGdxGame application;

	private static final int CHANGE_CAMARA_KEY = 31;// se cambia de camara con
													// la tecla "c"

	public Cam(MyGdxGame app) {
		lookAtVector = new Vector3(new float[] { 0f, 0f, -1f });
		camPosition = new Vector3(new float[] { 0f, 0f, 0f });
		application = app;
	}

	public void setPosition(Vector3 vec) {
		// Vector3 translationVec = new Vector3(new float[] { -vec.x, -vec.y,
		// -vec.z });
		// this.camPosition = translationVec;
		// view.set(view.setToTranslation(translationVec));

		camPosition = vec;
	}

	public Vector3 getPosition() {
		return camPosition;
	}

	public void lookAt(Vector3 lookVector) {

		// view.setToLookAt(camPosition, lookVector, new Vector3(up));

		this.lookAtVector = lookVector;
	}

	/*
	 * mvp representa la combinación (multiplicación) de P*V*T*R*S
	 */
	public Matrix4 getVP() {
		Matrix4 p = new Matrix4(getProjection());
		// Matrix4 v = LookAtRH(camPosition, lookAtVector, up);
		// float pitch = x_rot / Gdx.graphics.getWidth();
		// float yaw = y_rot / Gdx.graphics.getHeight();

		Matrix4 v = getV();

		// FPSViewRH(camPosition, pitch, yaw);

		Matrix4 ans = p.mul(v);

		return ans;
	}
	
	public Matrix4 getV(){
		return this.getTR().inv();
	}

	/*
	 * Basado en el código de
	 * http://www.3dgep.com/understanding-the-view-matrix/
	 */
	public Matrix4 LookAtRH(Vector3 eye, Vector3 target, Vector3 up) {
		Vector3 zaxis = new Vector3(eye).sub(target).nor(); // The "forward"
															// vector.
		Vector3 xaxis = (new Vector3(up).crs(zaxis)).nor();// The "right"
															// vector.
		Vector3 yaxis = new Vector3(zaxis).crs(xaxis).nor(); // The "up" vector.

		// Create a 4x4 orientation matrix from the right, up, and forward
		// vectors
		// This is transposed which is equivalent to performing an inverse
		// if the matrix is orthonormalized (in this case, it is).

		Matrix4 orientation = new Matrix4(new float[] { xaxis.x, yaxis.x,
				zaxis.x, 0, xaxis.y, yaxis.y, zaxis.y, 0, xaxis.z, yaxis.z,
				zaxis.z, 0, 0, 0, 0, 1 });

		// Create a 4x4 translation matrix.
		// The eye position is negated which is equivalent
		// to the inverse of the translation matrix.
		// T(v)^-1 == T(-v)
		Matrix4 translation = new Matrix4(new float[] { 1, 0, 0, 0, 0, 1, 0, 0,
				0, 0, 1, 0, -eye.x, -eye.y, -eye.z, 1 });

		// Combine the orientation and translation to compute
		// the final view matrix
		return orientation.mul(translation);
	}

	protected Matrix4 getProjection() {
		return new Matrix4();
	}

	// MOVIMIENTO CON TECLADO,
	@Override
	public boolean keyDown(int keycode) {
		System.out.println("tecla presionada:" + keycode);
		//se presiono la tecla de cambio de camara
		if (keycode == CHANGE_CAMARA_KEY) {
			this.changeCamera();
			System.out.println("---Cambio de camara---");
			return false;
		}

		// Vector3 forwardVector = new Vector3(
		// new Vector3(camPosition).add(lookAtVector));
		// System.out.println("--Forward antes"+ forwardVector);
		//
		// forwardVector.rotate(new Vector3(new float[] { 0f, 1f, 0f }), yaw)
		// .rotate(new Vector3(new float[] { 1f, 0f, 0f }), pitch).nor(); // The
		// // "forward"
		// System.out.println("--Forward despues de rotar "+forwardVector); //
		// vector

		//
		//
		// Vector3 rightVector = new Vector3(new
		// Vector3(forwardVector).crs(up)).nor();

		Vector3 forwardVector = getForward();
		Vector3 rightVector = getRight();
		// normal(cross(up,
		// zaxis));//
		// The
		// "right"
		// vector.
		System.out.println("///forward vector:" + forwardVector);
		System.err.println("---right vector" + rightVector);

		Vector3 movementVector = new Vector3();

		int currentDelta;

		switch (keycode) {

		case DOWN_KEY: {
			// z_movement = -DELTA_KEY_PRESSED * forwardVector.z;
			movementVector = forwardVector;
			break;
		}
		case UP_KEY: {
			// z_movement = DELTA_KEY_PRESSED * forwardVector.z;
			movementVector = new Vector3().mulAdd(forwardVector, -1);

			break;
		}
		case LEFT_KEY: {
			// x_movement = -DELTA_KEY_PRESSED * rightVector.x;
			movementVector = new Vector3().mulAdd(rightVector, -1);
			break;
		}
		case RIGHT_KEY: {
			// x_movement = DELTA_KEY_PRESSED * rightVector.x;

			movementVector = rightVector;
			break;
		}

		}
		float x_movement = movementVector.x * DELTA_KEY_PRESSED;
		float y_movement = movementVector.y * DELTA_KEY_PRESSED;
		float z_movement = movementVector.z * DELTA_KEY_PRESSED;
		System.out.println("posicion inicial cam:" + this.camPosition);
		Vector3 movement = new Vector3(new float[] { x_movement, y_movement,
				z_movement });
		getPosition().add(movement);
		System.out.println("posicion final camara" + this.camPosition);
		return false;
	}

	protected void changeCamera() {
		
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

	/*
	 * http://www.swiftless.com/tutorials/opengl/camera2.html
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// System.out.println("mouse x:" + screenX + " y: " + screenY);

		x_rot = last_x - screenX;
		y_rot = last_y - screenY;
		yaw += (x_rot / Gdx.graphics.getWidth()) * 180;
		pitch += (y_rot / Gdx.graphics.getHeight()) * 180;
		last_x = screenX;
		last_y = screenY;

//		if (pitch > 0)
//			up.rotate(new Vector3(new float[] { 1, 0, 0 }), pitch);
//		//

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	// Pitch should be in the range of [-90 ... 90] degrees and yaw
	// should be in the range of [0 ... 360] degrees.
	Matrix4 FPSViewRH(Vector3 eye, float pitch, float yaw) {
		// System.out.println("pitch:"+pitch+". yaw:"+yaw);
		// If the pitch and yaw angles are in degrees,
		// they need to be converted to radians. Here
		// I assume the values are already converted to radians.
		float cosPitch = (float) Math.cos(Math.toRadians(pitch));
		float sinPitch = (float) Math.sin(Math.toRadians(pitch));
		float cosYaw = (float) Math.cos(Math.toRadians(yaw));
		float sinYaw = (float) Math.sin(Math.toRadians(yaw));

		Vector3 xaxis = new Vector3(new float[] { cosYaw, 0, -sinYaw });
		Vector3 yaxis = new Vector3(new float[] { sinYaw * sinPitch, cosPitch,
				cosYaw * sinPitch });
		Vector3 zaxis = new Vector3(new float[] { sinYaw * cosPitch, -sinPitch,
				cosPitch * cosYaw });

		// Create a 4x4 view matrix from the right, up, forward and eye position
		// vectors
		Matrix4 viewMatrix = new Matrix4(new float[] { xaxis.x, yaxis.x,
				zaxis.x, 0, xaxis.y, yaxis.y, zaxis.y, 0, xaxis.z, yaxis.z,
				zaxis.z, 0, -(xaxis.dot(eye)), -(yaxis.dot(eye)),
				-(zaxis.dot(eye)), 1 });

		return viewMatrix;
	}

	public Matrix4 getT() {
		return new Matrix4(new float[] { 1, 0, 0, camPosition.x, 0, 1, 0,
				camPosition.y, 0, 0, 1, camPosition.z, 0, 0, 0, 1 }).tra();
	}

	public Matrix4 getR() {
		Matrix4 rx;
		Matrix4 ry;
		Matrix4 rz;

		rx = new Matrix4(new float[] { 1, 0, 0, 0, 0,
				(float) Math.cos(Math.toRadians(pitch)),
				(float) -Math.sin(Math.toRadians(pitch)), 0, 0,
				(float) Math.sin(Math.toRadians(pitch)),
				(float) Math.cos(Math.toRadians(pitch)), 0, 0, 0, 0, 1 }).tra();

		ry = new Matrix4(new float[] { (float) Math.cos(Math.toRadians(yaw)),
				0, (float) Math.sin(Math.toRadians(yaw)), 0, 0, 1, 0, 0,
				(float) -Math.sin(Math.toRadians(yaw)), 0,
				(float) Math.cos(Math.toRadians(yaw)), 0, 0, 0, 0, 1 }).tra();
		rz = new Matrix4();
		return rx.mul(ry).mul(rz);

	}

	public Matrix4 getTR() {
		return getT().mul(getR());
	}

	public Vector3 getForward() {
		return new Vector3(new float[] { 0, 0, 1 }).mul(getR());
	}

	public Vector3 getRight() {
		return new Vector3(new float[] { 1, 0, 0 }).mul(getR());
	}

	
	//setea los parametros necesarios para renderear iluminacion
	public void setParameters(ShaderProgram shader) {
		shader.setUniform3fv("EyePosW3", 
				vect3TofloatArray(this.getPosition()), 0, 3);
		//shader.setUniformf("EyePosW3", this.getPosition());
		
	}
	
	
	private float[] vect3TofloatArray(Vector3 vect){
		return new float[]{vect.x,vect.y,vect.z};
	}

	public float getFar() {
		return z_far;	}
}
