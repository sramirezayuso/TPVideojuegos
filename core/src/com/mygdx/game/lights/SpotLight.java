package com.mygdx.game.lights;

import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;

public class SpotLight extends Light {
	Vector3 l_spotDir_3;
	static float cutoff=(float) Math.toRadians(45.0);
	
    	public SpotLight(ShaderProgram shader,Vector3 postion,Vector3 ligh_color,Vector3 direction) {
    		
    		super(shader,GL_LIGHT0
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
	public void setParameters(ShaderProgram shader) {
		super.setParameters(shader);
		shader.setUniformf("l_spotCutOff",m_SpotCutoff);
		shader.setUniformf("spotlightDirection_3",m_SpotDirection );
		
	}

}
