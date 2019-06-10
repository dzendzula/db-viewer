DB viewer application.

REST API Application for managing database connection settings.
Supported functions
- saving, updating, listing and deleting connection details for PostgreSQL relational database.
    User should persist at least the following properties:
       name - custom name of the database instance
       hostname - hostname of the database
       port - port where the database runs
       databaseName - name of the database
       username - username for connecting to the database
       password - password for connecting to the database

- browsing structure and data using your stored database connections
   Listing schemas (if your selected database supports them)
   Listing tables
   Listing columns
   Data preview of the table


===============================================
Run prerequisites
===============================================
PostrgeSQL server installed required.

- create DB user for app administration
CREATE ROLE db_viewer_admin LOGIN PASSWORD 'passwd';

- create DB for app admionistration
CREATE DATABASE db_viewer
    WITH OWNER = db_viewer_admin
    ENCODING = 'UTF8';

Run flyway migration with maven plugin.
Run as Spring Boot Application.

================================================
API description
================================================
REST API of application is descripted with Swagger
SwaggerUI page cam be found at URL.

/your-app-server-adress-with-contextpath/swagger-ui.html