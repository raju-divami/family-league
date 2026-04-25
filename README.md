# Family League — Backend API

A **Spring Boot** backend for a family & friends cricket prediction league platform.  
Users predict match outcomes and final standings; points are awarded automatically after each result is published.

---

## Table of Contents

- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Project Setup](#project-setup)
- [Running the Application](#running-the-application)
  - [Option 1 — VS Code (Recommended, zero IDE config)](#option-1--vs-code-recommended)
  - [Option 2 — IntelliJ IDEA](#option-2--intellij-idea)
  - [Option 3 — Eclipse](#option-3--eclipse)
  - [Option 4 — Maven CLI (works everywhere)](#option-4--maven-cli)
- [IDE Lombok Troubleshooting](#ide-lombok-troubleshooting)
- [API Documentation](#api-documentation)
- [Database Seed](#database-seed)
- [Project Documentation](#project-documentation)

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.0.6 |
| Security | Spring Security + JWT (JJWT 0.12.6) |
| Persistence | Spring Data JPA + Hibernate + PostgreSQL |
| Code generation | Lombok 1.18.36, MapStruct 1.6.3 |
| API docs | SpringDoc OpenAPI 2.8.4 (Swagger UI) |
| Build | Maven |

---

## Prerequisites

- **Java 17** — verify with `java -version`
- **Maven 3.8+** — verify with `mvn -version`
- **PostgreSQL 13+** — running locally or via Docker
- A `.env` file in the project root (see below)

---

## Project Setup

### 1. Clone the repository

```bash
git clone <repo-url>
cd family-league
```

### 2. Create the database

```sql
CREATE DATABASE family_league;
```

### 3. Configure environment variables

```bash
cp .env.example .env
```

Open `.env` and fill in your values:

```properties
DB_URL=jdbc:postgresql://localhost:5432/family_league
DB_USERNAME=postgres
DB_PASSWORD=your_password

HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
HIBERNATE_DDL_AUTO=update

JWT_SECRET=change-me-to-a-secure-random-256-bit-secret-key

MAIL_USERNAME=your_gmail@gmail.com
MAIL_PASSWORD=your_gmail_app_password
```

> **Gmail App Password:** In your Google account go to  
> Security → 2-Step Verification → App passwords → generate one for "Mail".

### 4. Seed required roles

After the app starts for the first time (Hibernate creates the schema), run this once in PostgreSQL:

```sql
INSERT INTO roles (code, name) VALUES ('USER',  'Regular User');
INSERT INTO roles (code, name) VALUES ('ADMIN', 'Administrator');
```

See [POSTMAN_TESTING_GUIDE.md](POSTMAN_TESTING_GUIDE.md) for full seed instructions.

---

## Running the Application

### Option 1 — VS Code (Recommended)

VS Code uses the **Maven compiler plugin** directly, which runs all annotation processors (Lombok, MapStruct) correctly — no extra IDE plugin needed.

#### Steps

1. Install the **Extension Pack for Java** from the VS Code marketplace  
   (Publisher: Microsoft — includes Language Support for Java, Maven, Debugger)

2. Open the project folder in VS Code:
   ```
   File → Open Folder → family-league/
   ```

3. VS Code detects `pom.xml` automatically and imports the Maven project.

4. Run via any of these methods:
   - **Run button** — open `FamilyLeagueApplication.java`, click the ▶ Run link above `main()`
   - **Maven sidebar** — `family-league → Plugins → spring-boot → spring-boot:run`
   - **Terminal inside VS Code:**
     ```bash
     mvn spring-boot:run
     ```

5. The app starts at `http://localhost:8080`

> VS Code does **not** require the Lombok Eclipse agent — it delegates compilation to Maven, so `@Slf4j`, `@Getter`, `@RequiredArgsConstructor` etc. all work out of the box.

---

### Option 2 — IntelliJ IDEA

1. **Open** the project: `File → Open → family-league/` (select the `pom.xml` when prompted to trust)
2. IntelliJ imports Maven dependencies automatically.
3. Enable annotation processing:
   - `Settings → Build, Execution, Deployment → Compiler → Annotation Processors`
   - Check **Enable annotation processing**
4. Install the Lombok plugin if not already present:
   - `Settings → Plugins → Marketplace → search "Lombok" → Install`
5. Run `FamilyLeagueApplication` via the green ▶ button.

> If you still see Lombok errors after enabling annotation processing, restart IntelliJ and rebuild: `Build → Rebuild Project`.

---

### Option 3 — Eclipse

Eclipse requires the Lombok agent to be installed **once** into the IDE itself.

1. Find the Lombok jar in your Maven local repository:
   ```
   ~/.m2/repository/org/projectlombok/lombok/1.18.36/lombok-1.18.36.jar
   ```
   On Windows: `C:\Users\<you>\.m2\repository\org\projectlombok\lombok\1.18.36\lombok-1.18.36.jar`

2. Run the Lombok installer:
   ```bash
   java -jar lombok-1.18.36.jar
   ```
   Click **Install / Update**, point it at your Eclipse installation, then **Quit Installer**.

3. Restart Eclipse.

4. Enable annotation processing in the project:
   - Right-click project → `Properties → Java Compiler → Annotation Processing`
   - Check **Enable project specific settings** and **Enable annotation processing**

5. Do a clean build: `Project → Clean → Clean all projects`

6. Run `FamilyLeagueApplication` as a Java Application.

> **If Eclipse still fails after the above steps**, use VS Code or the Maven CLI instead — they have zero IDE configuration requirements.

---

### Option 4 — Maven CLI

Works on any machine with Java 17 and Maven installed — no IDE needed.

```bash
# From the project root
mvn spring-boot:run
```

Or build a JAR and run it:

```bash
mvn clean package -DskipTests
java -jar target/family-league-0.0.1-SNAPSHOT.jar
```

---

## IDE Lombok Troubleshooting

Lombok generates code (constructors, getters, loggers) at **compile time** via annotation processing. If your IDE does not run annotation processors, you will see errors like:

```
The blank final field X may not have been initialized
The method getX() is undefined for the type Y
log cannot be resolved
```

These errors mean the IDE compiled the class **without** Lombok running.

| IDE | Fix |
|---|---|
| **VS Code** | No fix needed — uses Maven compiler, works automatically |
| **IntelliJ IDEA** | Enable annotation processing + install Lombok plugin (see above) |
| **Eclipse** | Install Lombok agent into Eclipse (see above) |
| **Any IDE** | Fall back to `mvn spring-boot:run` in the terminal — always works |

---

## API Documentation

Once the app is running, open Swagger UI in your browser:

```
http://localhost:8080/swagger-ui.html
```

Raw OpenAPI JSON:

```
http://localhost:8080/v3/api-docs
```

The Swagger UI covers all 13 API groups with request/response schemas and the ability to call endpoints directly from the browser.

---

## Database Seed

After the app starts for the first time, execute these SQL statements in your PostgreSQL database before using the API:

```sql
-- Required roles
INSERT INTO roles (code, name) VALUES ('USER',  'Regular User');
INSERT INTO roles (code, name) VALUES ('ADMIN', 'Administrator');
```

Then register your first user via `POST /api/auth/register` and manually promote them to ADMIN:

```sql
-- Replace 1 with the user's actual ID
INSERT INTO user_roles (user_id, role_id)
SELECT 1, id FROM roles WHERE code = 'ADMIN';
```

---

## Project Documentation

| Document | Description |
|---|---|
| [requirements.md](requirements.md) | Full business and technical requirements |
| [schema.dbml](schema.dbml) | Database schema (DBML format) |
| [ASYNC_EMAIL_CONFIG.md](ASYNC_EMAIL_CONFIG.md) | Async leaderboard recalculation and email setup |
| [AUDIT_LOGGING.md](AUDIT_LOGGING.md) | Audit trail implementation details |
| [POSTMAN_TESTING_GUIDE.md](POSTMAN_TESTING_GUIDE.md) | Step-by-step API testing guide |
| [postman/](postman/) | Postman collection and environment files |
| [.env.example](.env.example) | Environment variable reference |
