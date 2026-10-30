# BelAirLine Backend

- **Student:** Nizeyimana Kevine
- **Student ID:** 26859
- **Project:** BelAirLine
- **Course:** INSY 8322 Web Technology and Internet, Mid-Term Examination

BelAirLine is the backend of an online ticket purchase and booking platform for an airline, built with Spring Boot, Spring Data JPA and PostgreSQL.

## Requirements

- Java 17 or newer
- PostgreSQL running on `localhost:5432`
- No Maven install is needed; the project includes the Maven wrapper

## Steps to run

1. Create the database:

   ```
   psql -U postgres -c "CREATE DATABASE bel_air_line"
   ```

2. Load the seed data:

   ```
   psql -U postgres -d bel_air_line -f seed/seed-data.sql
   ```

3. Start the API. It uses the `postgres` user and the password `postgres`. If your password is different, set the `DB_PASSWORD` environment variable first.

   ```
   ./mvnw spring-boot:run
   ```

   On Windows use `mvnw.cmd spring-boot:run`.

4. The API runs at `http://localhost:8081`. Import `bel-air-line-midterm.postman_collection.json` into Postman.

## Postman collection

There is one request per requirement, named by its ID (`A2`, `B1`, `C4`, `D2`, ...). Failing cases and extra checks sit next to it, for example `D2 - error 400 not allowed`.

The collection variables hold the ids of the seed data, so it works only with `seed/seed-data.sql` loaded. Running the whole folder in order passes on a fresh seed. Some requests change the data, so a second run needs the seed loaded again. Reloading `seed/seed-data.sql` resets the database.

## Seed data

Step 0 of the paper was entered through the API's own save endpoints with `seed/seed.py`, and three ancillary purchases were added with `seed/seed_purchases.py`. `seed/seed-data.sql` is a dump of that result.

The paper's seed needs bookings on dates such as 2026-10-20, with all four statuses. Rule D1 refuses past dates and starts every new booking as RESERVED, so `seed.py` only works against the Part A commit (tag `part-a`), which has no D1 yet. On any later commit, load the dump instead.
