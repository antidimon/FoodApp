# Task Tracker API

## Описание

API для слежения за КБЖУ.


## Технологии

* Java
* Spring Boot
* Spring REST
* Spring Data JPA
* PostgreSQL
* Flyway
* Docker
* JUnit+Mockito

## Запуск

1. Склонировать репозиторий
    ```bash
    git clone https://github.com/antidimon/FoodApp
    ```
2. Запустить скрипт run.sh, выполнится автоматическая сборка и запуск докер контейнеров.

   ```bash
    ./run.sh
   ```
   Или выполнить вручную сборку JAR файла и запуск докер контейнеров:
   ```
   mvn clean install dependency:copy-dependencies
   ```
   После чего:
   ```
   docker-compose up --build -d
   ```
3. Swagger запустится по этим url

   ```
   http://localhost:8080/swagger-ui.html
   http://localhost:8080/swagger-ui/index.html
   ```
