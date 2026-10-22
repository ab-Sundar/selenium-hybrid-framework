# Selenium Java Hybrid Automation Framework

An interview-friendly, small **hybrid** UI automation project using **Java 21 + Maven + Selenium WebDriver + TestNG + Cucumber BDD + Page Object Model + CSV data-driven testing**. Demo site: https://www.saucedemo.com/.

## What makes it hybrid?

- **POM:** `pages/` centralizes locators and browser actions (no Selenium locators in step definitions/tests).
- **BDD:** `login.feature` + step definitions + Cucumber TestNG runner.
- **Data-driven:** TestNG `@DataProvider` reads `testdata/login.csv`.
- **TestNG:** One suite runs both Cucumber scenarios and ordinary TestNG tests.
- **Configuration:** resource `config.properties` defaults overridden by JVM `-D` properties.
- **Reporting:** Cucumber HTML/JSON, TestNG/Surefire reports, failure screenshots.
- **CI:** GitHub Actions and Jenkins pipeline. Optional Selenium Grid support.

## Architecture

```text
Feature (.feature) -> Steps -> Page Objects -> Selenium WebDriver -> Browser
                          ^                       ^
TestNG DataProvider -> TestNG Test ---------------|
                       ^
                CSV test data

TestNG suite -> Cucumber runner + TestNG tests -> reports / screenshots
Git push -> GitHub Actions or Jenkins -> mvn test -> artifacts
```

## Setup and run (IntelliJ / Windows / macOS / Linux)

Requirements: **JDK 21, Maven, Chrome** (or Firefox). Selenium Manager downloads matching drivers automatically when possible; the browser and Maven dependencies need network access on first run.

1. Extract ZIP; open its **pom.xml** as a Maven project in IntelliJ.
2. In IntelliJ, wait for Maven import to complete and select JDK 21.
3. In a terminal from the root:

```bash
mvn clean test
```

Run without displaying browser (ideal for CI):

```bash
mvn clean test -Dheadless=true
```

Run Firefox (requires Firefox installed):

```bash
mvn clean test -Dbrowser=firefox
```

Run with a Selenium Grid already listening on port 4444:

```bash
mvn clean test -Dheadless=true -Dgrid.url=http://localhost:4444/wd/hub
```

## Where are the reports?

| Output | Location |
|---|---|
| Cucumber HTML | `target/cucumber-report.html` |
| Cucumber JSON | `target/cucumber-report.json` |
| Maven/TestNG XML | `target/surefire-reports/` |
| Regular TestNG failure screenshots | `target/screenshots/` |
| BDD failure screenshots | embedded in Cucumber HTML |

## Run in GitHub Actions

Create a GitHub repository, then from this project folder:

```bash
git init
git add .
git commit -m "Initial hybrid Selenium framework"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/YOUR-REPO.git
git push -u origin main
```

On push, `.github/workflows/selenium.yml` runs the entire suite in headless Chrome on Ubuntu. Inspect results under **Actions > workflow run > Artifacts**. Replace `YOUR-USERNAME` / `YOUR-REPO` with your actual values, and create an empty remote repository first.

## Run in Jenkins

1. Install Jenkins and ensure a Linux agent with **Chrome installed**, JDK 21 and Maven available.
2. Configure global Jenkins tool names **JDK21** (JDK) and **Maven3** (Maven).
3. Create a **Pipeline** job > **Pipeline script from SCM** > Git > repository URL > `Jenkinsfile`.
4. Build Now. Jenkins checks out code, runs `mvn -B clean test -Dheadless=true`, publishes JUnit XML and archives report artifacts.
5. For a Windows Jenkins agent, replace `sh` with `bat` in `Jenkinsfile` and ensure Chrome is installed.

## Execution sequence explained

1. Maven Surefire reads `testng.xml`.
2. TestNG launches the Cucumber runner and the data-driven test class.
3. Cucumber reads a scenario, opens a browser in the `@Before` hook, invokes Java steps, attaches screenshot on failure, then quits browser in `@After`.
4. Separately, TestNG creates one browser per `@Test` method invocation, reads CSV through `@DataProvider`, verifies the inventory title and quits in `@AfterMethod`.
5. Reports are written beneath `target/`. CI can publish them.

## Interview talking points

- The **framework is hybrid** because it mixes BDD, data-driven testing, POM, TestNG, config-driven execution and CI workflows rather than using a single automation design pattern.
- **ThreadLocal WebDriver** helps isolate browser sessions if concurrency is introduced later; this sample suite deliberately runs sequentially for simplicity.
- **Explicit waits** in `BasePage` reduce flaky element interactions. No `Thread.sleep()` calls.
- **Screenshot-on-failure**, controlled driver cleanup and pipeline reports aid debugging.
- **Grid** via `-Dgrid.url` enables remote execution without changing test code.
- **Secrets**: CSV includes public demo credentials only. For real systems, inject test credentials using environment variables or CI secret storage, not committed files.

## Possible enhancements (not needed for this small learning project)

Allure/Extent advanced dashboard, retry analysis, logging (SLF4J), environment profiles, Docker Grid, API tests, and controlled parallel execution. Add these when there is a real requirement—not merely to increase framework complexity.

**Note:** These tests depend on the third-party public demo site. UI changes, downtime, bot restrictions, or incompatible local browser/driver installations can affect runs. CI and local execution have not been verified in this package-building environment.
