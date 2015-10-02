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

vec4 EyePosW4=vec4(EyePosW3,1);
vec4 MaterialEmissive=vec4(MaterialEmissive_3,1);
vec4 MaterialDiffuse= vec4(MaterialDiffuse_3,1);
vec4 MaterialSpecular=vec4(MaterialSpecular_3,1);
	
	
	// Compute the emissive term.
    vec4 Emissive = MaterialEmissive;
 
    // Compute the diffuse term.
    vec4 N = normalize( v2f_normalW );
    vec4 L = normalize( LightPosW - v2f_positionW );
    float NdotL = max( dot( N, L ), 0 );
    vec4 Diffuse =  NdotL * LightColor * MaterialDiffuse;
     
    // Compute the specular term.
    vec4 V = normalize( EyePosW4 - v2f_positionW );
    vec4 H = normalize( L + V );
    vec4 R = reflect( -L, N );
    float RdotV = max( dot( R, V ), 0 );
    float NdotH = max( dot( N, H ), 0 );
    vec4 Specular = pow( RdotV, MaterialShininess ) * LightColor * MaterialSpecular;


// de los glcolor, el primero es con luces, el segund sin luces, el tercero es prueba  
//gl_FragColor = ( Emissive + Diffuse + Specular ) * texture2D(u_texture, v_texCoords);
//gl_FragColor =v_color * texture2D(u_texture, v_texCoords);
gl_FragColor = ( Emissive + Diffuse + Specular ) * texture2D(u_texture, v_texCoords);
}


