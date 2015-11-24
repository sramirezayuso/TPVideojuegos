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

const vec4 bitSh = vec4(256. * 256. * 256., 256. * 256., 256., 1.);
const vec4 bitMsk = vec4(0.,vec3(1./256.0));
const vec4 bitShifts = vec4(1.) / bitSh;

vec4 pack (float depth) {
    vec4 comp = fract(depth * bitSh);
    comp -= comp.xxyz * bitMsk;
    return comp;
}
float unpack (vec4 color) {
    return dot(color , bitShifts);
}

void main()
{
	//gl_FragColor = pack(gl_Position.z);//de jorge
	//float depth=1.0-((v_Position.z)/u_cameraFar);
	
	//gl_FragColor = depth*vec4(1.0,1.0,1.0,1.0);
	
	gl_FragColor = pack(gl_Position.z);
}


