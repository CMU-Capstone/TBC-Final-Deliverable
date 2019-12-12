# TBC-Final-Deliverable
This Repository contains the final deliverables for TBC's Capstone project.

## Getting Started
These instructions will get the services deployed on a single server in 3 seperate docker containers.
### Prerequisites
#### Install Docker
For Windows
* [Getting Started with Docker Windows](https://docs.docker.com/docker-for-windows/) - Minium docker requirement
For Mac
* [Getting Started with Docker Mac](https://docs.docker.com/docker-for-mac/) - Minium docker requirement
For Linux
* [Installing Docker Engine on Ubuntu](https://docs.docker.com/install/linux/docker-ce/ubuntu/) - Minium docker requirement

#### Install Docker-Compose
Docker-Compose
* [Installing Docker Compose](https://docs.docker.com/compose/install/) - For all OS

### Server Setup
#### Setting up server files
1. Pull project repository on local computer
2. Upload deployable.zip onto server
3. Unzip deployable.zip
4. Ensure that the following files are present:
```
 deployable/
    + docker-compose.yaml
    |
    + demo/
    |   + Dockerfile
    |   |
    |   + target/
    |       + demo-0.0.1-DEPLOY.jar
    |
    + github_scanner/
    |   + Dockerfile
    |   |
    |   + target/
    |       + sri-0.0.1-SNAPSHOT.jar
    |
    + slack_analyser_springboot/
    |   + Dockerfile
    |   |
    |   + target/
    |       + slackanalyser-0.0.1-SNAPSHOT.jar
    |
    + frontend/
    |   + Dockerfile
    |   |
    |   + src/ <all frontend resources>
    |   |
    |   + package.json
```

### Pre-running instructions
Due to the inclusion of the React based frontend code please follow the following instruction to ensure that the frontend will run correctly:

#### Edit the docker-compose.yaml file
![docker-compose](https://raw.githubusercontent.com/CMU-Capstone/TBC-Final-Deliverable/master/Documents/images/docker-compose.PNG)
Within the docker-compose.yaml file, you will have to change 3 urls to ensure that frontend works.
```
REACT_APP_GIT_BASE=http://<server ip>:8082
REACT_APP_SLACK_BASE=http://<server ip>:8081
REACT_APP_DEMO_BASE=http://<server ip>:8080
```
Replace the `localhost` in the current file with the IP address of the deployment server.

### Running Services
Ensure that all prerequisites are in place before moving to the following steps.

1. Enter the deployable folder<br>
`cd deployable`
2. Prune your docker system<br>
`docker system prune -f`
3. Build the docker images - you may skip this step if it is your first time deploying<br>
`docker-compose build`
4. Run the containers<br>
`docker-compose up`

## Test Services
The quickest way to tell if the services are running is to go to their respective ports:
* Git Scanner: `8082`
* Slack Scanner: `8081`
* Demo: `8080`

All services above will redirect you to their respective Swagger UI documentation pages

* Frontend: `8083`
This is the demo frontend - built off a open source template [material-dashboard-react](https://github.com/creativetimofficial/material-dashboard-react)

## Services
Refer to the all service documentation in the `Documents/API Manuals` folder of this project

## Raw Code
The raw code are also available within the project. All projects are <b>Spring Boot Projects</b> with <b>Maven</b> dependency management.

### Github_Scanner & Slack_Analyser_Springboot & Demo - Spring Boot Projects
We advise that you use the following IDEs to view the project:
1. Intellij
2. Eclipse

### Demo Frontend - Nodejs & React

