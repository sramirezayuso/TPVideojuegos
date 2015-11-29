
varying vec4 v_color; 
varying vec2 v_texCoords;
uniform sampler2D u_texture;

//iluminacion
varying vec4 v2f_normalW; 
varying vec4 v2f_positionW;
uniform vec3 EyePosW3;   // Eye position in world space.
uniform vec3 LightPosW_3; // Light's position in world space.
uniform vec3 LightColor_3; // Light's diffuse and specular contribution.
 
 
uniform vec3 MaterialEmissive_3;
uniform vec3 MaterialDiffuse_3;
uniform vec3 MaterialSpecular_3;
uniform float MaterialShininess; 

uniform float u_cameraFar;
varying vec4 v_position;//coordenadas mundo
uniform vec3 u_lightPosition;//coordenadas mundo

 
 //fuente de funciones de pack:http://www.ozone3d.net/blogs/lab/20080604/glsl-float-to-rgba8-encoder/
 
 vec4 pack_depth(const float value)
{
  const vec4 bitSh = vec4(256.0*256.0*256.0, 256.0*256.0, 256.0, 1.0);
  const vec4 bitMsk = vec4(0.0, 1.0/256.0, 1.0/256.0, 1.0/256.0);
  vec4 res = fract(value * bitSh);
  res -= res.xxyz * bitMsk;
  return res;
}
 
 
 float unpack_depth(const vec4 value)
{
  const vec4 bitSh = vec4(1.0/(256.0*256.0*256.0), 1.0/(256.0*256.0), 1.0/256.0, 1.0);
  return(dot(value, bitSh));
}

void main()
{
	//gl_FragColor = pack(gl_Position.z);//de jorge
	
	//vec4 normalized_position=(v_position ) * 0.5+ vec4(0.5,0.5,0.5,5.0);
	
	
	
	vec4 packed1=pack_depth((-1.0)* v_position.z);
	float unpacked1=unpack_depth(packed1);
	
	//gl_FragColor = unpacked1*vec4(1.0,1.0,1.0,1.0);
	//gl_FragColor = pack_depth(normalizedDistance);
	
	//gl_FragColor = vec4(0.0,0.0,0.0,0.0);
	
	//gl_FragColor = pack_depth(v_position.z);
	//gl_FragColor = ( v_position.z)*vec4(1.0,1.0,1.0,1.0);
	
	
	gl_FragColor = packed1;
	
}


