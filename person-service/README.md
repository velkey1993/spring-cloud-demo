## Spring-Doc-Open-Api:
* /v3/api-docs
* /swagger-ui.html

## Actuator
* /actuator

## Docker-Containers:
* `docker run -d --name elasticsearch --net micro -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.6.2`
* `docker run -d --name logstash --net micro -p 5000:5000 docker.elastic.co/logstash/logstash:7.6.2`
* `docker cp logstash.conf logstash:/usr/share/logstash/pipeline/logstash.conf`
* `docker restart logstash`
* `docker run -d --name kibana --net micro -e "ELASTICSEARCH_URL=http://elasticsearch:9200" -p 5601:5601 docker.elastic.co/kibana/kibana:7.6.2`
* `docker run -d --name person-service -p 8080:8080 --net micro velkey1993epam/person-service`
* `docker network inspect micro`
