package com.mygdx.game;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

//es una camara ortografica
public abstract class Cam {
	Vector3 camPosition;
	Matrix4 view = new Matrix4();
	private static final Vector3 up =new Vector3(new float[] { 0f, 1f, 0f });
	
	private Vector3 lookAtVector;
	
	public Cam(){
		view=new Matrix4();
		lookAtVector=new Vector3(new float[]{0f,0f,0f});
	}
	
	
	public void setPosition(Vector3 vec) {
//		Vector3 translationVec = new Vector3(new float[] { -vec.x, -vec.y,
//				-vec.z });
//		this.camPosition = translationVec;
//		view.set(view.setToTranslation(translationVec));
	
		camPosition=vec;
	}

	public void lookAt(Vector3 lookVector) {
		
		//view.setToLookAt(camPosition, lookVector, new Vector3(up));
		
		this.lookAtVector=lookVector;
	}

	
	/*
	 * mvp representa la combinación (multiplicación) de
P*V*T*R*S
	 */
	public Matrix4 getMVP() {
		Matrix4 p=this.getProjection();
		Matrix4 v= LookAtRH(camPosition	, lookAtVector, up);
		
		//return p.mul(v);
		return new Matrix4();
	}

	/*
	 * Basado en el código de
	 * http://www.3dgep.com/understanding-the-view-matrix/
	 */
	public Matrix4 LookAtRH( Vector3 eye, Vector3 target,Vector3 up ){
		Vector3 zaxis = eye.sub(target).nor();    // The "forward" vector.
		Vector3 xaxis = (up.crs(zaxis)).nor();// The "right" vector.
		Vector3 yaxis = zaxis.crs( xaxis);     // The "up" vector.
	 
	    // Create a 4x4 orientation matrix from the right, up, and forward vectors
	    // This is transposed which is equivalent to performing an inverse 
	    // if the matrix is orthonormalized (in this case, it is).
		
	    Matrix4 orientation = new Matrix4(
	    		new float[] { xaxis.x, yaxis.x, zaxis.x, 0 ,
	    		 xaxis.y, yaxis.y, zaxis.y, 0 ,
	    		 xaxis.z, yaxis.z, zaxis.z, 0 ,
	    		   0,       0,       0,     1} 
	    );
	     
	    // Create a 4x4 translation matrix.
	    // The eye position is negated which is equivalent
	    // to the inverse of the translation matrix. 
	    // T(v)^-1 == T(-v)
	   Matrix4 translation =new Matrix4(new float[] {
	           1,      0,      0,   0 ,
	           0,      1,      0,   0 , 
	           0,      0,      1,   0 ,
	        -eye.x, -eye.y, -eye.z, 1 }
	    );
	 
	    // Combine the orientation and translation to compute 
	    // the final view matrix
	    return  orientation.mul(translation );
	}
	
	protected abstract Matrix4 getProjection();
	
}
