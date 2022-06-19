# Budget navigator

## Table of contents

* [General info](#general-info)
* [Status](#status)
* [Technologies](#technologies)
* [How to run](#setup)
* [Requirements](#requirements)


## General info

This project is REST API designed to help people manage their resources and expenses. It supports user login,
associating assets and expenses with specific user and accessing them in ownership based model. The application offers
the functionality of generating files reporting current financial status.

## Status

The application is continuously developed. The next challenge is to add a service that sends emails with periodic
reports of financial status

## Technologies

Project is created with:

* Java 11
* Spring Boot v 2.6.6 (Data, Validation, Security)
* MySQL
* Liquibase
* H2 (Used for testing)
* Testing (AssertJ, JUnit, Mockito)
* Apache Commons CSV
* Lombok
* Docker (Container for database)
* MapStruct


## How to run

To run this project, you can use maven wrapper:

```
$ ./mvnw clean spring-boot:run
```

Alternatively using your installed maven version type this:

```
$ mvn clean spring-boot:run
```

## Requirements

TODO
