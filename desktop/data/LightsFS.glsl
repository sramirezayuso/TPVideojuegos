varying vec4 v_color; 
varying vec2 v_texCoords;
uniform sampler2D u_texture;

//iluminacion
varying vec4 v2f_normalW; 
varying vec4 v2f_positionW;
uniform vec4 EyePosW;   // Eye position in world space.
uniform vec4 LightPosW; // Light's position in world space.
uniform vec4 LightColor; // Light's diffuse and specular contribution.
 
 
uniform vec4 MaterialEmissive;
uniform vec4 MaterialDiffuse;
uniform vec4 MaterialSpecular;
uniform float MaterialShininess; 

void main() {
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
}


