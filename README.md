# bicycle-service
This project is a simple realization of "bicycle service" system. User can log in as a user (or register). The following features are available after authentication:

- Add new bicycle, user, manufacturer.
- Add user to bicycle
- Show list of all:
- bicycles with information about each bicycle and list of user assigned to the bicycle.
- registered users with information about each user.
- bicycle's manufacturers with information about each manufacturer.
- Delete:
- bicycle, all users will be unassigned from deleted bicycle.
- user, user will be unassigned from previously assigned bicycles.
- manufacturer.

---
##Implementation details
The project is designed according to the principles of SOLID, Dependency injection and Inversion of control. Project based on famous 3-layered architecture:

1. Data access layer (DAO)
2. Application layer (service)
3. Presentation layer (controllers)

---
##Technologies
- Apache Tomcat
- MySQL
- JDBC
- Servlet
- JSP
- JSTL
- HTML, CSS
- Maven Checkstyle Plugin

---
##Setup
1. Install:
- IntelliJ IDEA Ultimate
- ApacheTomcat
- MySQL and MySQL Workbench
2. Clone this project into your IDE
3. In MySQL Workbench run the script from resources/init_db.sql
    - Pay attention: if you have a schema with the name "bicycle" it will be replaced
4. In bicycle/util/ConnectionUtil change constants:
```
private static final String URL = "YOUR_URL";
private static final String USERNAME = "YOUR_USERNAME";
private static final String PASSWORD = "YOUR_PASSWORD";
private static final String JDBC_DRIVER = "YOUR_JDBC_DRIVER";
```
5. Configure TomCat Local server (Add New Configuration -> TomCat -> Local -> Fix -> bicycle_app:war_exploded -> OK)
