# Sample JEE Application

In order to run the application, it is necessary to:
1. Download Wildfly from https://download.jboss.org/wildfly/17.0.1.Final/wildfly-17.0.1.Final.zip
2. Unzip it
3. Create the following directory: wildfly-17.0.1.Final/modules/system/layers/base/org/postgresql/main/
4. Create a file named module.xml there with the following content:
```xml
<?xml version="1.0" encoding="UTF-8"?>
 
<module xmlns="urn:jboss:module:1.0" name="org.postgresql">
  <resources>
    <resource-root path="postgresql-42.2.6.jar"/>
  </resources>
  <dependencies>
    <module name="javax.api"/>
	<module name="javax.transaction.api"/>
  </dependencies>
</module>
```
5. Download PostgreSQL JDBC Driver from https://repo1.maven.org/maven2/org/postgresql/postgresql/42.2.6/postgresql-42.2.6.jar
6. Move postgresql-42.2.6.jar to the same directory that you have just created
7. Edit wildfly-17.0.1.Final/standalone/configuration/standalone.xml with the following content:
```xml
<subsystem xmlns="urn:jboss:domain:datasources:5.0">
            <datasources>
                <datasource jndi-name="java:jboss/datasources/ExampleDS" pool-name="ExampleDS" enabled="true" use-java-context="true" statistics-enabled="${wildfly.datasources.statistics-enabled:${wildfly.statistics-enabled:false}}">
                    <connection-url>jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
                    <driver>h2</driver>
                    <security>
                        <user-name>sa</user-name>
                        <password>sa</password>
                    </security>
                </datasource>
                <datasource jndi-name="java:/EnterpriseDS" pool-name="EnterpriseDS" enabled="true" use-java-context="true">
                    <connection-url>jdbc:postgresql://localhost:5432/enterprise</connection-url>
                    <driver>org.postgresql</driver>
                    <pool>
                        <min-pool-size>10</min-pool-size>
                        <max-pool-size>100</max-pool-size>
                        <prefill>true</prefill>
                    </pool>
                    <security>
                        <user-name>postgres</user-name>
                        <password>postgres</password>
                    </security>
                </datasource>
                <drivers>
                    <driver name="h2" module="com.h2database.h2">
                        <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
                    </driver>
                    <driver name="org.postgresql" module="org.postgresql">
                        <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
                    </driver>
                </drivers>
            </datasources>
        </subsystem>
```
8. Build the application .war with $ mvn clean install
9. Move the generated .war to wildfly-17.0.1.Final/standalone/deployments/
10. Make sure you have Postgres installed
11. Create a Postgres database named "enterprise"
    1. Run $ sudo -u postgres psql
    2. Run $ CREATE DATABASE enterprise;
12. Run this script: wildfly-17.0.1.Final/bin/standalone.sh

After Wildfly deploys the application, it should be listening on port 8080, and you should be able to call the following endpoints:

- GET /enterprise/api/homework-assignment
- GET /enterprise/api/homework-assignment/{id}
- DELETE /enterprise/api/homework-assignment/{id}
- POST /enterprise/api/homework-assignment

Sample request body for POST:
```json
{
  "name": "",
  "description": "",
  "estimatedDeadlineInDays": 1
}
```
