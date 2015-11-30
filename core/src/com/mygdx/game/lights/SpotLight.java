package com.mygdx.game.lights;

import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.cameras.Cam;

public class SpotLight extends Light {
	static float cutoff=(float) Math.toRadians(45.0);
	
    	public SpotLight(ShaderProgram shader,Vector3 postion,Vector3 ligh_color,Vector3 direction) {
    		
    		super(shader
    				,GL_LIGHT0
    		         , new Vector3(new float[]{ 0.0f, 0.0f, 0.0f })
    		         , new Vector3(new float[]{ 1.0f, 1.0f, 1.0f })
    		         , new Vector3(new float[]{ 1.0f, 1.0f, 1.0f})
    		         , postion
    		         , direction
    		         ,  0.0f
    		         , cutoff
    		         , 1.0f
    		         , 0.0f
    		         ,  0.0f ,
    		         ligh_color
    		         );
    			}
	
	@Override
	public void setParameters(Matrix4 modelMatrix,Cam cam,FrameBuffer shadowbuBuffer) {
		super.setParameters(modelMatrix,cam,shadowbuBuffer);
		
		
		shader.setUniformf("l_spotCutOff",m_SpotCutoff);
		shader.setUniformf("spotlightDirection_3",m_SpotDirection );
		
	}

	public Vector3 getPosition() {
		return m_Position;
	}

}
