# Talent Catalog API #

## Overview ##

This service implements the 
[Talent Catalog API Specification](https://github.com/Talent-Catalog/tc-api-spec)

## How do I get set up? ##

### Install the tools ###

> IMPORTANT NOTE:
>
> These instructions are tailored for Mac users using Intellij, as this is what we use for
> development.
>
> On a Mac, installing with Homebrew usually works well. eg "brew install xxx".
>
> It is also probably easier to install Java directly (or from your development IDE - see below) 
> rather than using brew.

Download and install the latest of the following tools.

- Homebrew - [Homebrew website](https://brew.sh)

- IntelliJ IDEA - [Intellij website](https://www.jetbrains.com/idea/download/)
    - Import standard settings and run configurations from another developer
    - In development, it is best to build using Intellij rather than gradle. Change the Intellij
      setting for "Build, Execution & Deployment" > "Build Tools" > "Gradle" to build with Intellij.

- Java 17
    - The current version of Java supported is Java 17. We use the Temurin release (however there
      should be no issues using other releases). **One way** (but you can choose whichever method
      you like) to manage Java versions is with **sdkman**. A .sdkmanrc file exists when you check 
      out the repository. You can get **sdkman** by running the following:

      ```
      curl -s "https://get.sdkman.io" | bash
      source "$HOME/.sdkman/bin/sdkman-init.sh"
      sdk install 17.0.11-tem
      ```

    - Intellij will load the JDK through the .sdkmanrc file.
    - Update the Project SDK:
        - Go to File / Project Structure / Project and set the SDK to your chosen JDK.
        - On the same page, ensure the language level matches your chosen SDK version.
    - IntelliJ Settings:
        - Go to IntelliJ / Settings / Build,Execution,Deployment / Compiler / Java Compiler
            - Add `-parameters` to the`Additional command line parameters` textbox.
            - Set the `Project bytecode version` to match the JDK chosen (e.g. **17**).
        - Go to IntelliJ / Settings / Build,Execution,Deployment / Build Tools / Gradle
            - Set the **GradleJVM** from the drop list to use the Project SDK.


- Code Style
    - Download the intellij-java-google-style.xml file from the google/styleguide repository
      [here](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml).
    - Launch IntelliJ and go to the **IntelliJ > Settings...** menu and expand the **Code Style**
      sub-menu underneath Editor. Here, you will see a list of supported languages. Select **Java**.
    - Next to the Scheme drop-down menu select the gear icon then **Import Scheme > IntelliJ IDEA
      code
      style XML** then select the intellij-java-google-style.xml file you downloaded from GitHub.
    - Give the schema a name (or use the default GoogeStyle name from the import). Click **OK** or
      **Apply** for the settings to take effect.


- Gradle [https://gradle.org/install/](https://gradle.org/install/)
  ```
  brew install gradle
  ```

- Git - [see Git website](https://git-scm.com/downloads) - Not really necessary now with Intellij
  which will prompt you to install Git if needed.


- Docker and docker-compose
    - Install Docker Desktop for Mac -
      see [docker website](https://hub.docker.com/editions/community/docker-ce-desktop-mac/)
    - Note for Mac Silicon users. The current Docker doc (link above) implies that installing
      Rosetta is optional.
      But if you don't do it you won't be able to install Docker.
      You need to execute softwareupdate --install-rosetta just to run Docker for the first time
      after installing it.
    - When you install Docker Desktop for Mac, Docker Compose is bundled with it. You can verify the
      installation by running:
      ```shell
        docker-compose --version
      ```

### Clone the TC API and TC API Specification repositories from Git ###

- Clone [the service](https://github.com/Talent-Catalog/tc-api.git) to your local system
```shell
git clone https://github.com/Talent-Catalog/tc-api.git
```
- Open the root folder in IntelliJ IDEA (it should auto detect gradle and self-configure)

- Clone [the OpenAPI specification](https://github.com/Talent-Catalog/tc-api-spec.git) 
to your local system
```shell
git clone https://github.com/Talent-Catalog/tc-api-spec.git
```
- Open the root folder in IntelliJ IDEA as a Module - IntelliJ > File > New > Module from Existing 
Sources

### Using Docker-Compose to Start Services ###

With Docker and Docker Compose installed, you can now use docker-compose to set up the required
services: Aurora-Postgres DB, Mongo DB, and optionally, Mongo Express.

- The API service git repository includes a docker-compose.yml file in the docker-compose 
  folder, with preconfigured services for Aurora, Mongo and Mongo Express. This file is ready for 
  you to use.
- To start the services, navigate to the docker-compose folder and run the following command:
```shell
cd talentcatalog/docker-compose
docker-compose up -d
```
- The -d flag runs the services in detached mode.
- To stop the services, run the following command:
```shell
docker-compose down
```

### Using IntelliJ’s Docker-Compose Integration to Start Services ###

IntelliJ IDEA provides built-in support for Docker Compose, allowing you to start and stop services
directly from the IDE, either from the Services tool window or directly from the docker-compose.yml
file itself.

- In the Project tool window, navigate to and open the docker-compose.yml file.
- IntelliJ adds green Run/Debug triangles in the gutter (left margin) next to each service in the
  docker-compose.yml file.
- Click on the green Run triangle next to a service (e.g., mongo) to start that specific service.
- You can also click the Run triangle next to the services block at the top of the file to start all
  services at once.

### Verify Services ###

The following services will all run from the Docker container:

- **Aurora-Postgres** (listening on the non-default port 5433 to prevent conflict with the core TC 
service Postgres DB)
- **Mongo** (listening on port 27017)
- **Mongo Express** (8081)

Verify with the following terminal command:
```shell
docker ps
```

### Set up your local database ###

The TC API service is designed to run in a cloud environment alongside the TC core 
service. You should have the TC core service and database running locally before proceeding. See
the [talentcatalog](https://github.com/Talent-Catalog/talentcatalog) repository for further details.

Once you have a local TC core service and database installed and running, you can run the 
TcApiServiceApplication from IntelliJ - see 'Run the server' below for further details. 

### Configure the API search ###

To enable the TC API service to fetch candidates for anonymisation, you must configure the service 
with valid credentials and an associated saved search in the Talent Catalog (TC) application. This 
is done via the following configuration properties, typically supplied via environment variables or 
in your local application.yaml:

```yaml
tc-service:
  apiUrl: ${TC_API_URL:http://localhost:8080/api/admin}
  search-id: ${TC_SEARCH_ID:2679}
  username: ${TC_USERNAME:appAnonDatabaseService}
  password: ${TC_PASSWORD:xxxxxxxx}
```

#### Create a service user ####

Before running the API service, you must:

1. Launch the Talent Catalog locally (see the 
   [talentcatalog](https://github.com/Talent-Catalog/talentcatalog) repository for setup 
   instructions).
2. Log in as an admin and create a new admin user that will act as the API service user (e.g., 
   appAnonDatabaseService).

#### Create a saved search ####

Once the service user is created:

1. Log into the Talent Catalog as the new service user. 
2. Use the candidate search interface to configure a search query that identifies the candidates 
   you want the API service to process. 
3. Click "Save search" and take note of the saved search's ID
4. Set the search-id property to match this ID (e.g., 2679 in the example above).

> The API service will use this saved search when retrieving candidates to be anonymised and 
> migrated to Aurora and MongoDB.

You can now run the API service and it will authenticate with the TC core application and retrieve 
candidates using this configured search.

### Run the server ###

The generated Open API models and controllers are provided in a published Maven artefact 
(tc-api-spec) that is referenced as a project dependency in the tc-api-service build.gradle file. 
There is no need to build the OpenAPI-generated code locally for standard development.

> Developer Note:
> 
> For developers who need to iterate quickly on API spec changes (for example, when working 
> simultaneously on the spec and the service), please consult the 
> [Working With the OpenAPI Wiki](https://github.com/Talent-Catalog/tc-api/wiki/Working-with-the-OpenAPI) 
> for instructions on using local iterative development with `publishToMavenLocal`.

With the Maven artefact in place, simply run the TcApiServiceApplication from IntelliJ as normal. 
There is no need to generate the Mapstruct mapper classes from Gradle. The IntelliJ build will do 
this. 

You can now start the TC API service:

- Create a new Run Profile for `org.tctalent.anonymization.TcApiServiceApplication`.
  In the Environment Variables section of Intellij, check the "Include system environment variables" 
  checkbox.
- Run the new profile, you should see something similar to this in the logs:

```
Started TcApiServiceApplication in 3.179 seconds (process running for 3.424)
```

- Your server will be running on port 8082 (as defined in server.port in application.yml).
- You can test out the server with a tool such as Postman by issuing a GET to the following 
  endpoint: 
  [http://localhost:8082/v1/candidates](http://localhost:8082/v1/candidates)

### Run and Manage Anonymisation Jobs ###

The TC API service exposes several batch management endpoints for running and monitoring 
anonymisation jobs. These endpoints can be triggered either via:

- Postman (or any REST client)
- The Talent Catalog Admin API interface (Settings → System Admin API)

#### API Authentication ####

All batch management endpoints require authentication using a Partner API key.

To set this up:

1. Log into the Talent Catalog admin portal. 
2. Go to Settings → Partners. 
3. Select the desired partner and click Edit. 
4. Enable Public API Access. 
5. Assign from the following API Authorities:
   - READ_CANDIDATE_DATA: Allows reading candidate records. 
   - SUBMIT_JOB_MATCHES: Allows submitting job-candidate matches (not used by anonymisation, but 
     required by job matching API partners). 
   - OFFER_CANDIDATE_SERVICES: Enables offering services to candidates (required for OTA 
     interactions). 
   - REGISTER_CANDIDATES: Allows registering new candidates. 
   - ADMIN: Grants full access to batch endpoints (most API partners will not need this).

> The x-api-key header must be included in all HTTP requests to authorize access. You can find the 
> API key in the Partner settings screen. It is displayed once when the API access is granted. You
> must take note of it when it is shown. For security, it will not be visible again.

#### API Migration Commands ####

Below are the available batch job commands. These may be entered in the TC Admin API command box or 
used in tools like Postman.

| Command                               | Description                                                    |
| ------------------------------------- | -------------------------------------------------------------- |
| `run_api_migration`                   | Run the full anonymisation job (Aurora + Mongo)                |
| `run_api_migration/aurora`            | Run only the Aurora migration step                             |
| `run_api_migration/mongo`             | Run only the Mongo migration step                              |
| `run_api_migration/list/{listId}`     | Run anonymisation using candidates from a specified saved list |
| `list_api_migrations`                 | View recent job executions (ID, status, start/end time)        |
| `stop_api_migration/{executionId}`    | Stop a currently running job                                   |
| `restart_api_migration/{executionId}` | Restart a previously failed job                                |


#### Using Postman ####

1. Open Postman and create a new request. 
2. Choose the method (GET or POST) based on the command. 
3. Set the URL to your API server, e.g.:
   ```shell
   POST http://localhost:8082/api/admin/run_api_migration
   ```
4. Add the x-api-key header with your partner's API key:
   ```shell
   x-api-key: your-partner-api-key
   ```
5. Send the request. You should receive a response confirming job submission or an error message.

#### Using the TC Admin API ####

In the Talent Catalog frontend (admin portal):

1. Navigate to Settings → Admin API. 
2. In the API call input box, type one of the supported commands (e.g. run_api_migration). 
3. Click Send. 
4. The response will appear below, showing success messages or error output.

> The list of available commands is displayed beneath the input field for convenience.

### Connect IntelliJ to your database ###

- File > New > Data Source > MongoDB > MongoDB
- Give the DB a name that clearly identifies it as your local development version.
- Populate the other setup parameters with the default values in the `mongo` configuration of the 
  project file `docker-compose.yml`.
- Repeat these steps to add Aurora DB as PostgreSQL data source: File > New > Data Source > 
  PostgreSQL > PostgreSQL

## Version Control ##

We use GitHub. Our repository is called tc-api -
[https://github.com/Talent-Catalog/tc-api](https://github.com/Talent-Catalog/tc-api)

See the [GitHub wiki](https://github.com/Talent-Catalog/tc-api/wiki) for additional documentation.

### Master branch ###

The main branch is "master". We only merge and push into "master" when we are ready to deploy to 
production (rebuild and upload of build artifacts to the production environment is automatic, 
triggered by any push to "master". See Deployment section below).

Master should only be accessed directly when staging is merged into it, triggering deployment to 
production. You should not do normal development in Master.

### Staging branch ###

The "staging" branch is used for code which is potentially ready to go into production. Code is 
pushed into production by merging staging into master and then pushing master. See Deployment 
section below.

Staging is a shared resource so you should only push changes there when you have finished changes 
which you are confident will build without error and should not break other parts of the code.

As a shared resource, staging is the best way to share your code with other team members to allow 
them to merge your code into their own branches and also to allow them to review your code and help 
with testing.

Rebuild and upload of build artifacts to the AWS testing environment is automatic when any push is 
made to "staging".

### Personal branches ###

New development should be done in branches.

Typically, you should branch from the staging branch, and merge regularly (eg daily) from staging 
so that your code does not get too far away from what everyone else is doing.

When you are ready to share your code for others to take a look at and for final joint testing and 
eventual deployment, merge your branch into staging.

On your branch you should commit often - doing separate commits for specific functionality, rather 
than lumping different kinds of functionality into a single big commit. That makes commits simpler 
to review and understand. It also makes it easier to revert specific functionality when you have got
something wrong and decide to start again, doing it differently.

You should feel comfortable pushing regularly - often doing Commit and Push at the same time. 
Pushing is effectively saving your work into the "cloud" rather than having changes just saved on 
your computer.

## Deployment and Monitoring ##

See the Deployment and Monitoring pages on the
[GitHub wiki](https://github.com/Talent-Catalog/tc-api/wiki).

## License

[GNU AGPLv3](https://choosealicense.com/licenses/agpl-3.0/)

