//http://www.microbasic.net/tutorials/shadow-mapping/ShadowMapping.pdf//estos atributos vienen del archivo .OBJattribute vec4 a_position;attribute vec4 a_color;attribute vec2 a_texCoord0;attribute vec4 a_normal;//estos uniforms son seteados por mi aplicacionuniform mat4 u_mvp;varying vec4 v_position;void main(){	v_position= u_mvp *a_position;	gl_Position = v_position;}