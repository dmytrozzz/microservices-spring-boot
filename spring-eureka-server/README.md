# spring-eureka-server
# spring-logging-setup
* Build an image - `docker build -t imageName:optional .`
* To run container based on image - `docker run -it -p 8761:8761 imageName`
* To retag - `docker tag imageName:optionalTag repoURL:optTag`
* To retag - `docker tag imageName ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/repoName`
* To push - `docker push repoUrl:optTag`
* To clean up - `docker system prune -a`

