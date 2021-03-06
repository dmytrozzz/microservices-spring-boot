# Microservices with Spring Boot on AWS
Added AWS capability to original repository
0. Place your github personal access token into pipeline.yml instead of "SECRET_TOKEN_HERE"  
1. Deploy ECR and S3 resources: `aws cloudformation create-stack --stack-name cicd-resources --template-body file://retain.yml --capabilities CAPABILITY_IAM`
2. To deploy out of the box CI/CD: `aws cloudformation create-stack --stack-name cicd --template-body file://cicd.yml --capabilities CAPABILITY_IAM`
3. Trigger cicd pipeline from AWS console (if not yet automatically) or by pushing to the repo.
4. That's it - now it will have fully available infrastructure and cicd pipelines
CICD deploys ECS stack and services and output the LoadBalancer URL and DB url

### What does it do
* dockerization of spring boot service
* pushing to aws ecr
* running on ecs
* vpc, subnets, all services are private 
* all functionality available through load balancer

###Endpoints
* LoadBalancerURL/api/auth/auth - POST to get token
  `{
  "username": "admin",
  "password": "12345"
  }`
* LoadBalancerURL/api/gallery - 
  `GET 
  Authorization: Bearer token from previous request`
* LoadBalancerURL/eurekaui

# Microservices with Spring Boot
The source code for medium series of articles on Microservices with Spring Boot. 

The series of articles can be found below:
- [Microservices with Spring Boot — Intro to Microservices (Part 1)](https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-intro-to-microservices-part-1-c0d24cd422c3)
- [Microservices with Spring Boot — Creating our Microserivces & Gateway (Part 2)](https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-creating-our-microserivces-gateway-part-2-31f8aa6b215b)
- [Microservices with Spring Boot — Authentication with JWT (Part 3)](https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-authentication-with-jwt-part-3-fafc9d7187e8)
- [Microservices with Spring Boot — Circuit Breaker & Log Tracing (Part 4)](https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-circuit-breaker-log-tracing-part-4-9cdf5e898988)

I've written this script in my free time during my work. If you find it useful, please support the project by spreading the word.

Contribute by creating new issues, sending pull requests on Github or you can send an email at: omar.elgabry.93@gmail.com

Thanks.
