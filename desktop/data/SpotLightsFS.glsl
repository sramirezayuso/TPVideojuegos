varying vec4 v_color; 
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_mvp;

//iluminacion
varying vec4 v2f_normalW; 
varying vec4 v2f_positionW;
uniform vec3 EyePosW3;   // Eye position in world space.
uniform vec3 LightPosW_3; // Light's position in world space.
uniform vec3 LightColor_3; // Light's diffuse and specular contribution.
uniform vec3 spotlightDirection_3;
uniform float l_spotCutOff; 

uniform vec3 MaterialEmissive_3;
uniform vec3 MaterialDiffuse_3;
uniform vec3 MaterialSpecular_3;
uniform float MaterialShininess; 


varying vec3 lightDir_3;
void main() {


vec4 LightPosW=vec4( LightPosW_3,1);
vec4 LightColor=vec4 (LightColor_3,1);

vec4 EyePosW4=vec4(EyePosW3,1);
vec4 MaterialEmissive=vec4(MaterialEmissive_3,1);
vec4 MaterialDiffuse= vec4(MaterialDiffuse_3,1);
vec4 MaterialSpecular=vec4(MaterialSpecular_3,1);
	
	
//http://www.lighthouse3d.com/tutorials/glsl-tutorial/spotlights/

	vec3 lightDir_3 = vec3(LightPosW.xyz - v2f_positionW.xyz);//vector desde la luz hasta el fragmento en cuestion


	float intensity = 0.0;
    vec4 spec = vec4(0.0);
 
    vec3 ld = normalize(lightDir_3);
    vec3 sd = normalize(vec3(-spotlightDirection_3));  
 
    // inside the cone?
    if ( dot(sd,ld) > l_spotCutOff) {
  	//if ( dot(sd,ld) == 0.0) {
  	
  	
  	
        vec3 n = normalize(v2f_normalW.xyz);
        intensity = max(dot(n,ld), 0.0);
 
        if (intensity > 0.0) {
            vec3 eye = normalize(EyePosW3);
            vec3 h = normalize(ld + eye);
            float intSpec = max(dot(h,n), 0.0);
            spec = MaterialSpecular * pow(intSpec, MaterialShininess);
        }
        //intensity=1.0;
    }
 
 	// Compute the diffuse term.
    vec4 N = normalize( v2f_normalW );
    vec4 L = normalize( LightPosW - v2f_positionW );
    float NdotL = max( dot( N, L ), 0 );
    vec4 Diffuse =  NdotL * LightColor * MaterialDiffuse;
     
 
 
 
    
    //el primero es el correcto, el de abajo es de prueba
    gl_FragColor = (intensity * Diffuse + MaterialSpecular) * texture2D(u_texture, v_texCoords);
    //gl_FragColor = intensity * texture2D(u_texture, v_texCoords);
    
   
    
    //gl_FragColor = vec4(sd, 1.0);
}


