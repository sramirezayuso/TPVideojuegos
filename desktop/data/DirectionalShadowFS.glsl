//http://www.microbasic.net/tutorials/shadow-mapping/ShadowMapping.pdf

varying vec4 v_color; 
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform sampler2D u_shadowMap;
uniform mat4 u_shadowVP;
uniform mat4 TRS;
//iluminacion
uniform vec3 LightPosW_3; // Light's position in world space.

varying vec4 v_position;
varying vec4 w_position;


const vec4 bit_shift = vec4(256.0*256.0*256.0, 256.0*256.0, 256.0, 1.0);
    const vec4 bit_mask  = vec4(0.0, 1.0/256.0, 1.0/256.0, 1.0/256.0);
   
vec4 pack_depth(const in float depth)
{
     vec4 res = fract(depth * bit_shift);
    res -= res.xxyz * bit_mask;
    return res;
}

float unpack_depth(const in vec4 rgba_depth)
{
     float depth = dot(rgba_depth, bit_shift);
    return depth;
}

void main()
{// JORGE DICE : tenes que comparar f_position.z contra shadowmap distance
 	vec4 LightPosW=vec4(LightPosW_3,1);
	vec4 f_position=TRS*u_shadowVP*w_position;//posicion del fragmento  respecto de la luz (MVP)
	float dist=-f_position.z;//distancia entre fragmento y la luz(da negativo para la escena)
	
	vec4 shadowProj =TRS* u_shadowVP * w_position;
	vec3 asdf = (shadowProj.xyz + vec3(1,1,1)) * 0.5; 
	
	float shadowmap_distance=unpack_depth(texture2D(u_shadowMap, asdf.xy));
	
	//gl_FragColor=vec4(1.0,1.0,1.0,1.0);
	//if(dist>shadowmap_distance)
	//	gl_FragColor=vec4(0.0,0.0,0.0,0.0);
	
	//probando
	float difference=(dist-shadowmap_distance)/10.0;
		
	
	
	gl_FragColor=vec4(0.0,0.0,0.0,0.0);
	if(dist<(shadowmap_distance))
		gl_FragColor=vec4(1.0,1.0,1.0,1.0);;
	
	//gl_FragColor=(dist*10.0)*vec4(1.0,1.0,1.0,1.0);
	//gl_FragColor=(shadowmap_distance/5.0)*vec4(1.0,1.0,1.0,1.0);
	//gl_FragColor=vec4(0.0,0.0,0.0,0.0);
	//if(difference>0.0)
		//gl_FragColor=difference*vec4(1.0,1.0,1.0,1.0);;
	
		//gl_FragColor=(dist)*vec4(1.0,1.0,1.0,1.0);
	
		
	
	
}
	


