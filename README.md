# spring-boot-elasticsearch-sample1

### Introduction to Elasticsearch

Elasticsearch is a powerful and widely-used open-source, Lucene-based search and analytics engine. It is designed for
storing, searching, and analyzing large volumes of data quickly and in near real-time.

Below are some of the key features of Elasticsearch:

* Full-Text Search: Elasticsearch excels at full-text search. It’s capable of indexing and searching through large
  amounts of unstructured or semi-structured text data efficiently.
* JSON Documents: Data in Elasticsearch is stored in the form of JSON documents. Each document is stored in an index,
  which is conceptually similar to a database table in the relational database world. This makes Elasticsearch
  schema-less, allowing us to index and search data without a predefined structure.
* Querying: Elasticsearch offers a rich and flexible query language that allows us to perform simple to complex
  searches. We can filter, aggregate, and sort data using various query types, such as term queries, match queries,
  range queries, and more.
* Analyzers and Tokenizers: Elasticsearch includes powerful text analysis features like analyzers and tokenizers that
  can break down text into tokens for efficient search and indexing. It supports multiple languages and custom
  analyzers.

### Using Docker

```shell
docker run --name elasticsearch-container -d -p 9200:9200 -p 9300:9300 /
-e "discovery.type=single-node" -e "xpack.security.enabled=false" /
docker.elastic.co/elasticsearch/elasticsearch:8.16.1
```

For demo purposes, we will install Elasticsearch using Docker. The following docker-compose.yml creates a single-node
Elasticsearch server. We expose the server at port ‘9200‘ so that our application can connect to it.

```shell
services:
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:8.16.1
    container_name: elasticsearch-container
    ports:
      - "9200:9200"
    environment:
      - discovery.type=single-node
      - xpack.security.enabled=false
```

Elasticsearch 8 comes with SSL/TLS enabled by default, to disable the security we use the environment variable
“xpack.security.enabled=false”. If security remains enabled, configuring the Elasticsearch client will require setting
up an SSL connection.

Once the server is up, hit the URL – http://localhost:9200/, we should see something like the below –

```json
{
  "name": "992e6b8bf7a5",
  "cluster_name": "docker-cluster",
  "cluster_uuid": "RxXotwWrTd2lzQBJmQ5gqA",
  "version": {
    "number": "8.16.1",
    "build_flavor": "default",
    "build_type": "docker",
    "build_hash": "f8edfccba429b6477927a7c1ce1bc6729521305e",
    "build_date": "2023-06-05T21:32:25.188464208Z",
    "build_snapshot": false,
    "lucene_version": "9.6.0",
    "minimum_wire_compatibility_version": "7.17.0",
    "minimum_index_compatibility_version": "7.0.0"
  },
  "tagline": "You Know, for Search"
}
```
