# DemoBlaze test automation

UI tests for [demoblaze.com](https://www.demoblaze.com/) written with Selenium WebDriver, Java and TestNG. Tests run locally, headless, or on a Selenium Grid in Docker (Chrome, Firefox, Edge). Preconditions such as creating a user or filling the cart go through the DemoBlaze API with REST Assured, so each UI test only checks the thing it's about.

## Tech stack

| Tool | Version |
|------|---------|
| Java | 25 |
| Selenium | 4.51 |
| TestNG | 7.12 |
| REST Assured | 6.3.0 |
| Allure | 3.0.0 (report CLI 2.46.1) |
| Log4j2 | |
| Maven Surefire | 3.5.4 |
| Docker / Selenium Grid | 4.51.0 |

## Project structure

```text
DemoBlaze/
├── .github/workflows/tests.yml CI on GitHub Actions
├── docker-compose.yml          Selenium Grid: hub + chrome, firefox, edge nodes
├── pom.xml
├── testng.xml                  suite, groups, parallel settings
└── src/
    ├── main/java/com/demoblaze/
    │   ├── drivers/            driver factories (local, headless, remote) and GUIDriver
    │   ├── utils/              element, browser, alert and frame actions, waits,
    │   │                       API client, logs, Allure reports, data readers
    │   ├── validations/        hard assertions (Validation) and soft checks (Verification)
    │   ├── listeners/          TestNG listener: logs, screenshots, report
    │   ├── pages/              page objects and shared components (NavBar, modals)
    │   ├── flows/              multi-page flows, e.g. CheckoutFlow
    │   ├── apis/               UserApi, CartApi for test preconditions
    │   └── models/             data objects such as OrderData
    ├── main/resources/         webApp.properties, environment.properties, log4j2, allure
    └── test/
        ├── java/com/demoblaze/tests/   BaseTest and one class per feature
        └── resources/test-data/        users.json, products.json
```

The code is split in three layers. Tests talk to pages and flows only. Pages and flows use the core (drivers, actions, waits). The core knows nothing about DemoBlaze.

## Prerequisites

- JDK 25
- Maven 3.9+
- Chrome, Firefox or Edge for local runs
- Docker Desktop for remote runs

Browser drivers are downloaded by Selenium Manager, so there is nothing to install for them.

## Configuration

`src/main/resources/webApp.properties`:

| Key | Values | Default |
|-----|--------|---------|
| `browserType` | `chrome`, `edge`, `firefox` | `chrome` |
| `executionType` | `local`, `localHeadless`, `remote` | `local` |
| `remoteUrl` | Grid address | `http://localhost:4444` |
| `gridReadySeconds` | seconds to wait for the Grid before the first remote test | `30` |
| `fluentWaitSeconds` | max wait for an element or page change | `10` |
| `openReportAfterExecution` | open the Allure report after the run | `true` |

`src/main/resources/environment.properties` holds the site and API URLs.

Every key can be overridden from the command line with `-D`, for example `-DbrowserType=firefox`.

## Running the tests

Full suite on the local browser:

```bash
mvn test
```

Headless:

```bash
mvn test -DexecutionType=localHeadless
```

Only the smoke group:

```bash
mvn test -Dgroups=smoke
```

One class:

```bash
mvn test -Dtest=LoginTest
```

## Running on Selenium Grid

1. Start the Grid (the first time pulls the images and takes a few minutes):

```bash
docker compose up -d
```

2. Check that `http://localhost:4444` shows three nodes.

3. Run the suite on the browser you want:

```bash
mvn test -DexecutionType=remote -DbrowserType=chrome -DopenReportAfterExecution=false
```

4. Stop the Grid when you're done:

```bash
docker compose down
```

Each Grid session shows the test name, so you can tell which test is running at `localhost:4444`. To watch a browser live, open VNC at `localhost:7900` (chrome), `7901` (firefox) or `7902` (edge). The password is `secret`.

If the Grid is down, the first remote test fails after `gridReadySeconds` with a message that says so, not a connection stack trace.

## Parallel runs

`testng.xml` runs test classes in parallel with 2 threads, and the driver lives in a `ThreadLocal`. To change the thread count for one run:

```bash
mvn test -DexecutionType=remote -DthreadCount=4
```

Each Grid node accepts 2 sessions (`SE_NODE_MAX_SESSIONS` in `docker-compose.yml`). With 4 threads on one browser, raise it to 4, or the extra sessions wait in the Grid queue.

## CI (GitHub Actions)

`.github/workflows/tests.yml` runs the tests on a GitHub runner, on Chrome in the Selenium Grid from `docker-compose.yml`:

| Trigger | Group |
|---------|-------|
| push to `main` | `regression` |
| pull request | `smoke` |
| manual run from the Actions tab | `smoke` or `regression`, you pick |

The Allure report, screenshots and logs are uploaded as an artifact on every run, failed or not, and kept for 14 days.

## Test groups

| Group | What's in it |
|-------|-------------|
| `smoke` | 5 main flows: valid login, sign up with a new user, add to cart from the product page, product shows in cart, purchase |
| `regression` | all 19 tests (23 runs, since some use a DataProvider) |
| `quarantine` | flaky tests waiting for a fix; excluded in `testng.xml` |

## Reports and logs

After a run, everything goes under `test-output/`:

- `reports/AllureReport_<date>.html`: single-file Allure report
- `full-report/`: full Allure report with history
- `screenshots/`: screenshots of failed tests
- `logs/logs.log`: the run log

Each test's log lines are tagged with the test name, and the Allure report attaches only that test's lines to it.

## Test coverage

| Class | Covers |
|-------|--------|
| `LoginTest` | valid login, invalid password, invalid user, logout |
| `SignUpTest` | new user, existing user, empty fields |
| `HomePageTest` | product shows in its category, next page shows other products |
| `ProductTest` | product page shows the right price, add to cart shows "Product added" |
| `CartTest` | added product shows in cart, total is the sum of prices, delete removes only that product |
| `CheckoutTest` | purchase shows order details, empty form shows an error, cart is empty after purchase |
| `ContactTest` | sending a message shows a thank-you alert |
| `AboutUsTest` | About us shows the video |
