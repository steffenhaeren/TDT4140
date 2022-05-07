# Running and building

The server requires Java 17, along with Node.js to build the frontend. The frontend can be built separately, but if any API-dependent functionality is to be tested as well, it's recommended that you don't. The API-dependent parts of the code rely on the API being hosted on the same port.

You don't have to install node yourself. Maven will install a project-local variant for you regardless of whether you have it installed. We currently run 17.4.0 in the Maven configuration. Other versions may work, but we guarantee whichever version the Maven configuration uses.

The node maven installs can be used manually as well, regardless of whether Node is installed on the system. Finally, the project requires Maven 3.8.0 or newer. If you don't have 3.8.0, you can use replace all `mvn` commands with `./mvnw` (`mvnw.cmd` on Windows) when you're in the eventmarket subdirectory (NOT the root directory).

The server can then be started with `mvn spring-boot:run`. Tests and other checks can be run with `mvn verify`.
