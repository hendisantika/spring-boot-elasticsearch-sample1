# spring-boot-elasticsearch-sample1

[![Java CI with Maven](https://github.com/hendisantika/spring-boot-elasticsearch-sample1/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/spring-boot-elasticsearch-sample1/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-brightgreen?logo=springboot)
![Elasticsearch](https://img.shields.io/badge/Elasticsearch-9.4.8-005571?logo=elasticsearch)
![Testcontainers](https://img.shields.io/badge/Testcontainers-2.x-2496ED?logo=docker)

Sample Spring Boot application that stores and searches `Employee` documents in Elasticsearch using
Spring Data Elasticsearch (both `ElasticsearchOperations` and repository style).

### Tech Stack

| Component                   | Version                           |
|-----------------------------|-----------------------------------|
| Java (JDK)                  | 25                                |
| Spring Boot                 | 4.1.2                             |
| Spring Data Elasticsearch   | managed by Spring Boot            |
| Elasticsearch Java client   | 9.x (managed by Spring Boot)      |
| Elasticsearch server        | 9.4.8 (Docker)                    |
| Testcontainers              | 2.x (managed by Spring Boot)      |
| Build tool                  | Maven (wrapper included: `mvnw`)  |

### Prerequisites

* JDK 25
* Docker (used by Docker Compose when running the app and by Testcontainers when running tests)

### Quick Start

```shell
git clone https://github.com/hendisantika/spring-boot-elasticsearch-sample1.git
cd spring-boot-elasticsearch-sample1

# Spring Boot Docker Compose support starts Elasticsearch from compose.yaml automatically
./mvnw spring-boot:run
```

### Running Tests

Integration tests use [Testcontainers](https://testcontainers.com/) to spin up a real Elasticsearch `9.4.8`
container, so Docker must be running:

```shell
./mvnw clean verify
```

### CI/CD & Dependency Updates

* **GitHub Actions** (`.github/workflows/maven.yml`) builds and tests the project on JDK 25 (Temurin) for every push
  and pull request to `main`. Testcontainers uses the Docker engine pre-installed on the `ubuntu-latest` runner.
  On push, the Maven dependency graph is submitted to GitHub to improve Dependabot alerts.
* **Dependabot** (`.github/dependabot.yml`) keeps dependencies up to date:
    * `maven` &mdash; daily at 05:00 Asia/Jakarta, grouped into `spring-boot`, `testcontainers`, `logging` and
      `maven-plugins`
    * `github-actions` &mdash; daily, all actions grouped into one PR
    * `docker-compose` &mdash; weekly, keeps the Elasticsearch image in `compose.yaml` up to date

> **Note:** Spring Boot 4.x ships the Elasticsearch Java client 9.x, which requires an Elasticsearch **9.x** server.
> Using an 8.x server results in `Accept version must be either version 8 or 7, but found 9`.

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
docker run --name elasticsearch-container -d -p 9200:9200 -p 9300:9300 \
  -e "discovery.type=single-node" -e "xpack.security.enabled=false" \
  docker.elastic.co/elasticsearch/elasticsearch:9.4.8
```

For demo purposes, we will install Elasticsearch using Docker. The following `compose.yaml` creates a single-node
Elasticsearch server. We expose the server at port ‘9200‘ so that our application can connect to it.

```yaml
services:
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:9.4.8
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
    "number": "9.4.8",
    "build_flavor": "default",
    "build_type": "docker",
    "build_hash": "...",
    "build_date": "...",
    "build_snapshot": false,
    "lucene_version": "10.x",
    "minimum_wire_compatibility_version": "8.19.0",
    "minimum_index_compatibility_version": "8.0.0"
  },
  "tagline": "You Know, for Search"
}
```
