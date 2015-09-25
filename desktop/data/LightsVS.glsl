//estos atributos vienen del archivo .OBJattribute vec4 a_position;attribute vec4 a_color;attribute vec2 a_texCoord0;//estos uniforms son seteados por mi aplicacionuniform mat4 u_mvp;//estos varying son el OUTPUT de mi vertex shader//el OUTPUT del vertex shader es efectivamente el INPUT del //fragment shadervarying vec4 v_color;varying vec2 v_texCoords;//iluminacionuniform vec4 in_normal;varying vec4 v2f_normalW;varying vec4 v2f_positionW;varying mat4 ModelMatrix;//este es mi Vertex Shader void main(){    v_color = vec4(1, 1, 1, 1);    v_texCoords = a_texCoord0;        //esta linea transforma la posicion del vertice (en coordenadas locales)    //a coordenadas de mundo, luego a coordenadas de vista    //y por ultimo a coordenadas NDC (proyectadas)    gl_Position =  u_mvp * a_position;            //para poder dibujar distintos objetos (o distintas naves)    //es necesario introducir el valor (distinto) de mvp antes de     //dibujar cada nave            //parametros de iluminación al fragment    v2f_normalW = ModelMatrix * in_normal;    v2f_positionW = ModelMatrix * a_position;             }