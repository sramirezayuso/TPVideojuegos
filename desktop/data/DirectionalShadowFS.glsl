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
varying vec4 v_position;
uniform vec3 u_lightPosition;


void main()
{
gl_FragColor = vec4(length(v_position.xyz-u_lightPosition)/u_cameraFar);
}


