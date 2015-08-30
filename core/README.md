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