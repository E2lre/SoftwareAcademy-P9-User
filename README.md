# SoftwareAcademy-P9-User-V2
Micro-service User manage users authentification

## Installation

### Database installation
* execute CreateDatabase.sql file


###Docker image construction in project directory : ==> TODO

docker build --build-arg JAR_FILE=target/*.jar -t p9-user .

### Docker execution :==> TODO

docker run -p 8083:8083 --name USER p9-user

#### Docker execution for all project
* On user project directory : 

execute command line to start all components: docker-compose up -d

### divers
* paramétrage du proxy pour node js


### lancement de zipkin 
* depuis le répertoire de zipkin : java -jar zipkin-server-2.6.1-exec.jar
* lancer : http://localhost:9411 
