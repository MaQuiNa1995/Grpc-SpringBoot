# Que es gRPC

Es un protocolo por el cual se permite ejecutar un procedimiento de otro programa alojado en otro sistema

La mejor parte de gRPC es que los desarrolladores no tienen que codificar explicitamente los detalles de la interacción, este código se autogenera con el propio framework

De tal manera que si por ejemplo tenemos una aplicación Golang y una Java a traves de este protocolo podrían llamarse entre si

Uno actúa de servidor y otro de cliente (Luego veremos las diferentes formas que hay)

# Que tipos hay

1. Unaria – Cliente manda 1 mensaje y el servidor responde con otro mensaje

2. Streaming del lado de cliente – El cliente manda n mensajes y espera que el servidor mande 1 sola respuesta

3. streaming del lado del servidor – El cliente manda 1 mensaje y espera que el servidor mande N mensajes

4. bi-direccional – el cliente y el servidor se mandan multiples mensajes en paralelo y un orden arbitrario, este tipo es flexible y no bloqueante que se refiere a que ninguna de las 2 partes necesita esperar para mandar informacion a la otra

# Como Funciona

El cliente tiene un "stub" que provee el mismo método o función que tiene el servidor , este "stub" es autogenerado por el framework a traves de un compilador externo o en el caso de java a traves de un plugin que definimo en el pom

Este "stub" llamara a gRPC por detrás para intercambiar información con el servidor por la red

Gracias al "stub" el cliente y el servidor solo se tienen que preocupar de implementar su logica de negocio

# Porque gRPC

Es una manera de comunicar diferentes lenguajes de programación que hoy en día existen en una aplicación productiva, por ejemplo podrías tener java o golang como backend y Javascript como frontend

Para la comunicación entre ellos debe haber algo para hacerlo de manera sencilla y rápida, eso es gRPC 

Es necesario cumplir una serie de reglas para esa comunicación:
- the communication channel
- authentication
- payload format
- data model
- error handling

# Generación de código

Esta es la característica mas importante ya que genera el código necesario para la comunicación enter cliente y servidor

Lo primero que tendremos que hacer es crear el contrato en un archivo .proto que en el caso de java alojaremos en `src/main/proto/`

```
// Aqui definimos la sintaxis que usará nuestro archivo, es conveniente usar la versión 3 de este
// Ya que aglutina mas lenguajes de programación compatibles 
syntax = "proto3";

// Con esto indicamos que cada clase que se cree se haga en un fichero independiente
option java_multiple_files = true;

// Aqui indicaremos el paquete que tienen todos las clases de este archivo
package com.github.maquina1995.grpc.model;

// Aqui definimos un pojo que albergará la información de una Persona
message Person {
	// Aqui definimos los atributos indicando la posición de los mismos
	string first_name = 1;
	string last_name = 2;
}

// Lo mismo que lo de antes pero aqui solo tenemos 1 atributo
message Greeting {
	string message = 1;
}

// Aquí definimos un servicio que usa nuestros pojos 
service HelloWorldService {
  rpc sayHello (Person) returns (Greeting);
}
```

## Java

Para poder generar los objetos asociados necesitamos:

1. Tener un plugin de maven
2. Ejecutar un `mvn clean compile`

```
<plugin>
	<groupId>org.xolstice.maven.plugins</groupId>
	<artifactId>protobuf-maven-plugin</artifactId>
	<version>${protobuf-maven-plugin.version}</version>
	<configuration>
	<protocArtifact>com.google.protobuf:protoc:3.5.1-1:exe:${os.detected.classifier}</protocArtifact>
	<pluginId>grpc-java</pluginId>
	<pluginArtifact>io.grpc:protoc-gen-grpc-java:1.16.1:exe:${os.detected.classifier}</pluginArtifact>
	</configuration>
	<executions>
		<execution>
			<goals>
				<goal>compile</goal>
				<goal>compile-custom</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```

Al hacer el `mvn clean compile` podremos ver en la siguiente ruta: `target\generated-sources\protobuf\java` los archivos generados

## Para cualquier lenguaje (Windows)

Según la respuesta de Yuvaraj en: https://stackoverflow.com/questions/13616033/install-protocol-buffers-on-windows

Solo tienes que descargar: https://github.com/protocolbuffers/protobuf/releases/download/v3.19.1/protoc-3.19.1-win64.zip

- Una vez que la tengas debes descomprimirla en una ruta
- Esa ruta la tienes que meter como PROTOC_HOME en las variables de entorno
- A continuación añadiremos %PROTOC_HOME%\bin a la variable path
- Abriremos una nueva terminal (no vale las que tengamos abiertas ya que has modificado las variables de entorno)
- Para finalizar comprobaremos la correcta instalación y configuración con el comando: `protoc`

# Diferencias REST vs gRPC

En el rest la comunicación solo se puede hacer en un sentido `cliente -> servidor` mientras que en gRPC en todos los sentidos.

Rest está ampliamente soportado por los navegadores mientras que gRPC está limitado.

# Documentación oficial
1. Java: [Documentacion gRPC Java](https://grpc.io/docs/languages/java/basics/) 
2. Golang: [Documentacion gRPC Golang](https://grpc.io/docs/languages/go/basics/) 
