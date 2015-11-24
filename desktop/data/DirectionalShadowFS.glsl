//http://www.microbasic.net/tutorials/shadow-mapping/ShadowMapping.pdf

varying vec4 v_color; 
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform sampler2D u_shadowMap;
uniform mat4 u_shadowVP;

//iluminacion
uniform vec3 LightPosW_3; // Light's position in world space.
 
 

varying vec4 v_Position;
varying w_position;
uniform vec4 UP; 

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
 vec4 LightPosW=vec4(LightPosW_3,1);
	vec4 f_position=w_position*UP;//posicion del fragmento  respecto de la luz
	float dist=distance(f_position,LightPosW)//distancia entre fragmento y la luz
	
	vec4 shadowProj = u_shadowVP * w_position;
	vec3 asdf = (shadowProj.xyz + vec3(1,1,1)) * 0.5; 
	
	float shadowmap_distance=unpack(texture2D(u_shadowMap, asdf.xy));
	if(dist>shadowmap_distance)
		gl_FragColor=vec4(1.0,1.0,1.0,1.0);
	
	gl_FragColor=vec4(0.0,0.0,0.0,0.0);
	
	//gl_FragColor = pack(gl_Position.z);
}


