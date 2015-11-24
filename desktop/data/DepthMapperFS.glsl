//http://www.microbasic.net/tutorials/shadow-mapping/ShadowMapping.pdf

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

varying vec4 v_Position;
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
{
	//gl_FragColor = pack(gl_Position.z);//de jorge
	
	
	//float intensity=1.0-((v_Position.z)/u_cameraFar);
	//vec4 packed1=pack_depth(intensity);
	//float unpacked1=unpack_depth(packed1);
	
	//gl_FragColor = unpacked1*vec4(1.0,1.0,1.0,1.0);
	
	gl_FragColor = pack_depth(v_Position.z);
}


