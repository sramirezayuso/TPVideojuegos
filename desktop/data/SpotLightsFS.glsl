varying vec4 v_color; 
varying vec2 v_texCoords;
uniform sampler2D u_texture;

//iluminacion
varying vec4 v2f_normalW; 
varying vec4 v2f_positionW;
uniform vec3 EyePosW3;   // Eye position in world space.
uniform vec3 LightPosW_3; // Light's position in world space.
uniform vec3 LightColor_3; // Light's diffuse and specular contribution.
uniform vec3 spotlightDirection_3;
uniform vec3 l_spotCutOff; 

uniform vec3 MaterialEmissive_3;
uniform vec3 MaterialDiffuse_3;
uniform vec3 MaterialSpecular_3;
uniform float MaterialShininess; 

void main() {


vec4 LightPosW=vec4( LightPosW_3,1);
vec4 LightColor=vec4 (LightColor_3,1);

vec4 EyePosW4=vec4(EyePosW3,1);
vec4 MaterialEmissive=vec4(MaterialEmissive_3,1);
vec4 MaterialDiffuse= vec4(MaterialDiffuse_3,1);
vec4 MaterialSpecular=vec4(MaterialSpecular_3,1);
	
	
//http://www.lighthouse3d.com/tutorials/glsl-tutorial/spotlights/

vec3 lightDir_3 = vec3(l_pos - pos);//vector desde la luz hasta el vertice en cuestion


float intensity = 0.0;
    vec4 spec = vec4(0.0);
 
    vec3 ld = normalize(lightDir_3);
    vec3 sd = normalize(vec3(-spotlightDirection_3));  
 
    // inside the cone?
    if (dot(sd,ld) > l_spotCutOff) {
 
        vec3 n = normalize(v2f_normalW.xyz);
        intensity = max(dot(n,ld), 0.0f);
 
        if (intensity > 0.0f) {
            vec3 eye = normalize(EyePosW3);
            vec3 h = normalize(ld + eye);
            float intSpec = max(dot(h,n), 0.0);
            spec = MaterialSpecular * pow(intSpec, MaterialShininess);
        }
    }
 
    
    
    gl_FragColor = (intensity * MaterialDiffuse_3 + MaterialSpecular_3) * texture2D(u_texture, v_texCoords);
}


