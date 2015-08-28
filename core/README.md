NOTAS
------
*¿estan bien los valores de far, near y height en orthografic cam?


*¿Por que no me toma los metodos que implementan InputProcessor en la superclase abstracta de camara? 
Me obliga a ponerlo en las clases que heredan.

*La camara se mueve bien si fijo la direccion en la que mira y cambio la coordenada z. ¿que hacer cuando hay otro lookAtVector 
para la camara?

*linea 79 de Cam.java, en la ultima columna de la matriz de traslacion, los cambios de signo complican el desplazamiento.
Es la formula copiada de la pagina recomendada(http://www.3dgep.com/understanding-the-view-matrix/) 
¿esta esto bien?

*para el movimiento del mouse,¿esta bien modificar la posición de lookAt? ¿o usar movimiento de camara yaw, pitch,head?