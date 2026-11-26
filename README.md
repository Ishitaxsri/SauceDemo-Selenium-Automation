# SauceDemo Selenium Automation

## Project Overview
This project automates the login functionality of the SauceDemo web application using Selenium WebDriver with Java.

It tests different login scenarios to verify that the application handles valid and invalid login credentials correctly.

## Technologies Used
- Java 17
- Selenium WebDriver 4.27.0
- JUnit 5
- Maven
- Google Chrome

## Test Scenarios

| Test Case | Description |
|---|---|
| Valid Login | Verifies login with valid credentials |
| Incorrect Password | Checks login with an incorrect password |
| Empty Credentials | Checks login with both fields empty |
| Locked Out User | Verifies locked-out user handling |
| Missing Password | Checks login without a password |

## Project Structure

```text
SauceDemo-Selenium-Automation/
├── pom.xml
├── .gitignore
├── screenshots/
└── src/
    └── test/
        └── java/
            └── SauceDemoTest.java
```

## How to Run

1. Install Java 17 or later.
2. Install Maven.
3. Clone this repository.
4. Open the project folder in a terminal.
5. Run:

```bash
mvn clean test
```

## Application Under Test
https://www.saucedemo.com/

## Author
Ishita Srivastava

Chandigarh University | B.E. Computer Science and Engineering
