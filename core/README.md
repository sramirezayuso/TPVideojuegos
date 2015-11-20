NOTAS
------
*¿estan bien los valores de far, near y height en orthografic cam?
poner mas, depende de la aplicacion

*¿Por que no me toma los metodos que implementan InputProcessor en la superclase abstracta de camara? 
Me obliga a ponerlo en las clases que heredan.
Ahora lo permite, ponelo en la superclase

*La camara se mueve bien si fijo la direccion en la que mira y cambio la coordenada z. ¿que hacer cuando hay
 otro lookAtVector 
para la camara?

moverse en la direccion forward. Este vector depende de donde estas y hacia donde miras, lo calcula en  lookAtRh
Vector3 zaxis = new Vector3(eye).sub(target).nor(); // The "forward"
															// vector.
															
El forward vector sirve para adelante y para atras.
Para adelante: posInicial+ delta* forward vector

Para los lados necesitas el vector Right.


Ambos vectores los normalizas antes de usarlo.

															
															

*linea 79 de Cam.java, en la ultima columna de la matriz de traslacion, los cambios de signo complican el desplazamiento.
Es la formula copiada de la pagina recomendada(http://www.3dgep.com/understanding-the-view-matrix/) 
¿esta esto bien?

 se
 
*para el movimiento del mouse,¿esta bien modificar la posición de lookAt? ¿o usar movimiento de camara yaw, pitch,head?

cuando moves mouse, tocas yaw pitch roll. Se podifica si o si a donde miras en funcion de esos angulos


__________________________________
4/8/2015

Manejo de luces
--------------



Cada objeto que se esta representando, debe tener : geometria (el mesh), y una clase Material


//clase Material
diffuse_mat (Vec4)
specular_mat (vec4)
shine_mat(float)

material(ShaderProgram p)
	{this.p=p;}
	
	
setParametersToShaders(ShaderProgram p){
p.setUniform( //y aca pasarle los vectores del comienzo

//


El shader program es el vertex shader y el fragment shader.

// clase GameObject, que contiene el mesh y la parte del material

class GameObject{

mesh;
material material;
 	


draw(){



}

}


//


Basicamente, lo que tenes es un shader, un objeto(que contiene el material), y una luz.
En el programa principal, cuando se ejecuta render:
// en programa principal
		render(){
		...
		object.material.setParametersToShader();
		light.setParametersToShader(o.materialShader);
		object.material.shader.draw(mesh, ...);
		}
		
		


//
Las variables que "tiene" el vertex shader se las pasas cuando haces : program.setUniform("nombre variable", variable);

En el vertex shader, hay variables "Uniform", variables "attribute" para el archivo de puntos ".obj", y variables "varying" para materiales.
Para las luces solo te importa "varying", no el otro.
 
 --------------------
 25/9
 
 *MetallicMaterial no esta realmente configurado para metal, REVISAR
 *¿Vector normal al vertice???
 
 -------------------
 
 2/10
 *¿Como levantar propiedades de material del archivo *.mtl?no hace falta
 *¿Que ocurre cuando hay múltiples texturas? ¿Se cargan igual que como el caso de una unica imagen de la nave? no lo hagas, hace cosas con una sola textura
 *animaciones: no en obj, exportarla en 3dg (ver libgdx animations and skinning).
 *gdbugger: para debugger.
 
 
Animacion 
----
http://www.gamefromscratch.com/post/2014/02/14/No-bones-about-it-Bones-in-LibGDX-and-Blender.aspx
http://mmmovania.blogspot.com.ar/2012/11/skeletal-animation-and-gpu-skinning.html
http://dagger.se/?p=71


Para comprobar la spotlight, poner un plano para ver el circulo. Segun jorge, hay algo raro.

6/11
----
En la pointlight hay como luz ambiente, la luz en si no se ve del color que deberia.
ARREGLADO!

---------


20/11
-----
*En el shadow map las posiciones aparecen al reves.
*En el shadow map desaparece la segunda nave.
*En el shadow map las cosas mas cercas estan marcadas como mas lejos y viceversa!!!!.ARREGLADO


