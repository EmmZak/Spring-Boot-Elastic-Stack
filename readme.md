## Run PostGres instance

docker-compose up db -d

## check indexes

http://localhost:9200/_cat/indices?v

http://localhost:9200/_all/_mapping?pretty

## search

http://localhost:9200/book/_search?scroll=10m&size=50