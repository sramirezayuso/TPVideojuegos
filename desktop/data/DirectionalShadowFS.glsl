//http://www.microbasic.net/tutorials/shadow-mapping/ShadowMapping.pdf

varying vec4 v_color; 
varying vec2 v_texCoords;
uniform sampler2D u_shadowMap;
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
 
varying vec4 v_position;
varying vec4 w_position;


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

float getVisibility(){
	float ans=1.0;
	
//  tenes que comparar f_position.z contra shadowmap distance
 	vec4 LightPosW=vec4(LightPosW_3,1);
	
	
	float dist=-w_position.z;//distancia entre fragmento y la luz(da negativo para la escena)
	
	
	vec3 shadow_cord = (w_position.xyz ) * 0.5+ vec3(0.5,0.5,0.5); 
	
	float shadowmap_distance=unpack_depth(texture2D(u_shadowMap, shadow_cord.xy));
	
	
	
	float difference=(shadowmap_distance-dist);
		
	
	
	
	
	
	if(shadow_cord.x>0.0 
		&& shadow_cord.x<1.0 
		&& shadow_cord.y>0.0 
		&& shadow_cord.y<1.0
		&& !(difference>0.0 && difference<0.1)){ //esta en la sombra
		ans=0.1;
		}
	else{
		ans=1.0;
		}
	
	return ans;

}

void main()
{
vec4 LightPosW=vec4( LightPosW_3,1);
vec4 LightColor=vec4 (LightColor_3,1);

vec4 MaterialSpecular=vec4(MaterialSpecular_3,1);
vec4 MaterialDiffuse= vec4(MaterialDiffuse_3,1);	


//http://www.lighthouse3d.com/tutorials/glsl-tutorial/directional-lights-per-pixel/

// set the specular term to black
    vec4 spec = vec4(0.0);
 
    // normalize both input vectors
    vec3 n = v2f_normalW.xyz;
    vec3 e = normalize(vec3(EyePosW3));
 	vec3 l_dir= vec3(LightPosW_3 - v2f_positionW.xyz);
 	l_dir=normalize(l_dir);
    float intensity = max(dot(n,l_dir), 0.0);
 
    // if the vertex is lit compute the specular color
    if (intensity > 0.0) {
        // compute the half vector
        vec3 h = normalize(l_dir + e);  
        // compute the specular term into spec
        float intSpec = max(dot(h,n), 0.0);
        spec =MaterialSpecular * pow(intSpec,MaterialShininess);
    }
    
    float visibility=getVisibility();
   gl_FragColor = (visibility*intensity *  MaterialDiffuse + spec)* texture2D(u_texture, v_texCoords);


	
}
	


