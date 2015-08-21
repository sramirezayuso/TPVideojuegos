package com.mygdx.game;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
//es una camara ortografica
public  class Cam {
	Vector3 camPosition;
	Matrix4 view=new Matrix4();
	
	
	public void setPosition(Vector3 vec){
		Vector3 translationVec=new Vector3(new float[]{-vec.x,-vec.y,-vec.z});
		this.camPosition=translationVec;
		view.set(view.setToTranslation(translationVec));
	}
	
	public void lookAt(Vector3 lookVector){
		float[] up={0f,1f,0f};
		view.setToLookAt(camPosition, lookVector, new Vector3(up));
	}
	
	public Matrix4 getView(){
		
		return view;
		
	}
}
