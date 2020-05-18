
* `docker run -d --name vault --cap-add=IPC_LOCK -e 'VAULT_DEV_ROOT_TOKEN_ID=spring-microservices-course' -p 8200:8200 vault:1.4.0`
* `docker run -d --name consul -p 8500:8500 consul`
* `docker run -d --name redis -p 6379:6379 redis:5.0.9`
