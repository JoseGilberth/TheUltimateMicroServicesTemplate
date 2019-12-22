# INTERFACES

En esta sección colocaras las interfaces que TODOS los microservicios usaran,ademas de las clases Abstractas.

Tenemos una clase abstracta "ACRUDEndPoints" que lo que hace es agregar un endpoint de heartbeat , para ver la salud del endpoint en general , si te das cuenta los demas endpoint de los demas microservicios extienden de esta clase.

Tenemos una clase abstracta "ACrud" que los servicios de los demas microservicios extienden para que tengan todo el crud definido con la respuesta que tenemos en el submodulo "DTO"

Tambien tenemos una interface "ICRUD" de la que implementan la mayoria de los servicios de los microservicios , con el fin de que esos servicios tengan definido el crud desde esta interface
 

