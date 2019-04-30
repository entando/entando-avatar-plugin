# avatarPlugin

This application was generated using JHipster 5.8.2, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v5.8.2](https://www.jhipster.tech/documentation-archive/v5.8.2).

This is a "microservice" application intended to be part of a microservice architecture, please refer to the [Doing microservices with JHipster][] page of the documentation for more information.

This application is configured for Service Discovery and Configuration with . On launch, it will refuse to start if it is not able to connect to .

## Development

To start your application in the dev profile, simply run:

    ./mvnw

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

## Building for production

To optimize the avatarPlugin application for production, run:

    ./mvnw -Pprod clean package

To ensure everything worked, run:

    java -jar target/*.war

Refer to [Using JHipster in production][] for more details.

## Testing

To launch your application's tests, run:

    ./mvnw clean test

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Then, run a Sonar analysis:

```
./mvnw -Pprod clean test sonar:sonar
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

    docker-compose -f src/main/docker/postgresql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/postgresql.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw package -Pprod verify jib:dockerBuild

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 5.8.2 archive]: https://www.jhipster.tech/documentation-archive/v5.8.2
[doing microservices with jhipster]: https://www.jhipster.tech/documentation-archive/v5.8.2/microservices-architecture/
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v5.8.2/development/
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v5.8.2/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v5.8.2/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v5.8.2/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v5.8.2/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v5.8.2/setting-up-ci/

## Keycloak Entando Integration

To make this application work with your instance of keycloak you will need to configure Keycloak properly.

What is required is a `cliend-id` and a `client-secret` for this application (required to verify the token that is used to access the service) as well as you will need
a client scope which will be able to map user and client permissions/roles into the token.
Here the steps to achieve this:
- In keycloak, create a new client scope with the name that you want
- Once created, you need to create two mappers:
    - The first mapper will map the User Client Role to a claim named **roles** of type string - make sure this claim is added to the ID token, the access token and the user info endpoint
    - The second mapper will map the User Realm Role also to a claim named **roles** of type string - again make sure this claim is added to the ID token, the access token and the user info endpoint
- Now you need to add the Client Scope to the client
- Make sure the client has the `Service Accont` protocol active, so that you can give "roles" even to the client itself

Now you should be able to generate a new token and use it to get access to the service
