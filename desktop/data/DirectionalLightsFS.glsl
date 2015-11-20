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

void main() {


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
    //gl_FragColor = (intensity *  MaterialDiffuse + spec)* texture2D(u_texture, v_texCoords);
   gl_FragColor = (intensity *  MaterialDiffuse + spec)* texture2D(u_texture, v_texCoords);
}


