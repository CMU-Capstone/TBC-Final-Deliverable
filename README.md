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
        + Dockerfile
        |
        + target/
            + slackanalyser-0.0.1-SNAPSHOT.jar
    
```

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

All services will redirect you to their respective Swagger UI documentation pages

## Services
Refer to the all service documentation in the documents folder of this project
