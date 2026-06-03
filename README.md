# QA Brains Automation — Java + Playwright + Cucumber

![Java](https://img.shields.io/badge/Java-11+-orange?logo=java)
![Playwright](https://img.shields.io/badge/Playwright-1.53-green?logo=playwright)
![Cucumber](https://img.shields.io/badge/Cucumber-BDD-brightgreen?logo=cucumber)
![TestNG](https://img.shields.io/badge/TestNG-Runner-red)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apache-maven)
![Jenkins](https://img.shields.io/badge/Jenkins-CI%2FCD-D24939?logo=jenkins)
![Allure](https://img.shields.io/badge/Allure-Reports-yellow)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

**Author:** Ranajit Baran Chowdhury — Software Developer & QA Automation Engineer
**Email:** chyranajit@gmail.com
**GitHub:** [@ranajitchowdhury](https://github.com/ranajitchowdhury)

An enterprise-grade **BDD test automation framework** built with **Java**, **Playwright**, and **Cucumber** — following industry-standard design patterns including Page Object Model, Hooks-based lifecycle management, and CI/CD-ready pipeline integration.

---

## 📋 Tech Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| **Language** | Java 11+ | Core automation logic |
| **Browser Automation** | Playwright for Java | Fast, reliable cross-browser control |
| **BDD Framework** | Cucumber (Gherkin) | Human-readable test scenarios |
| **Test Runner** | TestNG | Execution, grouping, parallel runs |
| **Build Tool** | Maven | Dependency management |
| **CI/CD** | Jenkins / GitHub Actions / AWS Pipeline | Automated build and execution |
| **Reporting** | Allure Reports + Cucumber HTML | Rich, interactive test reports |
| **Design Pattern** | Page Object Model (POM) | Maintainable UI abstraction layer |

---

## 📁 Project Structure

```
qaBrainsAutomation-Java-Playwright/
│
├── src/test/java/
│   ├── hooks/                     ← @Before / @After lifecycle management
│   │   └── Hooks.java
│   ├── pages/                     ← Page Object Model classes
│   │   ├── LoginPage.java
│   │   ├── LogoutPage.java
│   │   ├── CartPage.java
│   │   ├── RegistrationPage.java
│   │   ├── ForgotPasswordPage.java
│   │   ├── HomePage.java
│   │   └── SearchPage.java
│   ├── stepdefinitions/           ← Cucumber step definitions
│   │   ├── CommonSteps.java
│   │   ├── LoginSteps.java
│   │   ├── LogoutSteps.java
│   │   ├── CartSteps.java
│   │   ├── RegistrationSteps.java
│   │   ├── ForgotPasswordSteps.java
│   │   ├── SmokeSteps.java
│   │   └── RegressionSteps.java
│   ├── runners/                   ← TestNG test runner
│   │   └── TestRunner.java
│   └── utils/                     ← Core utilities
│       ├── BaseTest.java          ← Browser lifecycle management
│       ├── DriverFactory.java     ← Thread-safe WebDriver factory
│       ├── DriverHelper.java      ← Shared driver utilities
│       └── ConfigReader.java      ← config.properties reader
│
├── src/test/resources/
│   ├── features/                  ← Gherkin BDD feature files
│   │   ├── Login.feature
│   │   ├── Logout.feature
│   │   ├── CartCheckout.feature
│   │   ├── Registration.feature
│   │   ├── ForgotPassword.feature
│   │   ├── SmokeTests.feature
│   │   └── RegressionTests.feature
│   └── config/
│       └── config.properties      ← URL, credentials, browser settings
│
├── reports/                       ← Generated test reports & screenshots
│   ├── allure-results/
│   ├── cucumber-reports/
│   └── screenshots/
│
├── pom.xml                        ← Maven dependencies & plugins
├── testng.xml                     ← TestNG suite configuration
├── Jenkinsfile                    ← CI/CD pipeline definition
└── README.md
```

---

## ✨ Framework Features

| Feature | Implementation |
|---------|---------------|
| ✅ BDD with Cucumber | Gherkin scenarios — readable by developers, QA, and business stakeholders |
| ✅ Page Object Model | Each page encapsulates its own locators and actions |
| ✅ Thread-safe Driver | `DriverFactory` uses `ThreadLocal<Page>` for parallel-safe execution |
| ✅ Screenshot on Failure | Auto-captured in `@After` hook for every failed scenario |
| ✅ Configurable | `config.properties` controls URL, browser, headless mode, timeouts |
| ✅ CI/CD Ready | Jenkins + GitHub Actions + AWS Pipeline support |
| ✅ Allure Reports | Rich HTML reports with steps, screenshots, and trend history |
| ✅ Known Issue Handling | `@known_issue` tag skips broken site components gracefully |
| ✅ Detailed Logging | Console output with step-level success/failure indicators |

---

## 🎯 Test Coverage

### 🔐 Authentication
- Valid login with correct credentials
- Invalid login — error message validation
- Session verification post-login

### 🛒 Cart & Checkout
- Add product to cart
- Remove product from cart
- Cart empty state verification
- Checkout flow navigation

### 📝 User Registration
- Successful registration with valid unique email
- Email format validation (HTML5 + inline errors)
- Duplicate email handling

### 🔑 Forgot Password
- Password reset with registered email
- Error handling for unregistered email

### 🚀 Smoke Tests
- Home page load verification
- Navigation link validation (Catalog, About, Blog)
- Wishlist and Refer-a-Friend page access

### 🔄 Regression Tests
- Cart quantity updates
- Invalid search — no result display
- Multi-product cart operations

---

## ✅ Prerequisites

- ✅ **JDK 11 or higher** — configured in PATH
- ✅ **Maven 3.8+** — installed and configured
- ✅ **IDE** — IntelliJ IDEA or Eclipse
- ✅ **Allure CLI** — for generating Allure reports (`brew install allure` on Mac)
- ✅ **Git** — for version control

---

## ⚙️ Installation & Setup

**1. Clone the repository:**
```bash
git clone https://github.com/ranajitchowdhury/qaBrainsAutomation-Java-Playwright.git
cd qaBrainsAutomation-Java-Playwright
```

**2. Install dependencies:**
```bash
mvn clean install -DskipTests
```

**3. Install Playwright browsers:**
```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chromium"
```

**4. Configure test settings in `src/test/resources/config/config.properties`:**
```properties
url=https://practice.qabrains.com/
browser=chromium
headless=false
timeout=30000
valid_email=qa_testers@qabrains.com
valid_password=Password123
```

---

## ▶️ Running Tests

### Run all tests
```bash
mvn clean test
```

### Run with specific TestNG suite
```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Run by Cucumber tag
```bash
# Smoke tests
mvn test -Dcucumber.filter.tags="@smoke"

# Regression tests
mvn test -Dcucumber.filter.tags="@regression"

# Login only
mvn test -Dcucumber.filter.tags="@Login"

# Exclude known issues
mvn test -Dcucumber.filter.tags="not @known_issue"
```

### Run in headless mode (for CI/CD)
```bash
mvn test -Dheadless=true
```

---

## 📊 Reports

### Allure Report
```bash
# Generate and open
mvn allure:serve

# Or generate static
mvn allure:report
open target/allure-report/index.html
```

### Cucumber HTML Report
```
target/cucumber-reports/cucumber.html
```

### TestNG Report
```
target/surefire-reports/index.html
```

---

## 🔄 CI/CD Integration

### Jenkins Pipeline

```groovy
pipeline {
    agent any

    tools {
        maven 'Maven 3.8'
        jdk 'JDK 11'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/ranajitchowdhury/qaBrainsAutomation-Java-Playwright.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Execute Tests') {
            steps {
                sh 'mvn test -Dheadless=true -Dcucumber.filter.tags="@smoke or @regression"'
            }
        }

        stage('Publish Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }

    post {
        always {
            cucumber buildStatus: 'UNSTABLE',
                     fileIncludePattern: '**/cucumber.json',
                     reportTitle: 'QA Brains Test Report'
        }
        failure {
            emailext(
                subject: "❌ Build FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Test execution failed. Check Allure report: ${env.BUILD_URL}allure",
                to: 'chyranajit@gmail.com'
            )
        }
        success {
            emailext(
                subject: "✅ Build PASSED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "All tests passed. Allure report: ${env.BUILD_URL}allure",
                to: 'chyranajit@gmail.com'
            )
        }
    }
}
```

### GitHub Actions

```yaml
name: QA Brains Automation

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]
  schedule:
    - cron: '0 9 * * 1-5'   # Mon–Fri 9 AM UTC

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3

      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'

      - name: Install dependencies
        run: mvn clean install -DskipTests

      - name: Install Playwright browsers
        run: mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chromium"

      - name: Run Smoke Tests
        run: mvn test -Dheadless=true -Dcucumber.filter.tags="@smoke"

      - name: Run Regression Tests
        run: mvn test -Dheadless=true -Dcucumber.filter.tags="@regression"

      - name: Generate Allure Report
        run: mvn allure:report

      - name: Upload Allure Results
        uses: actions/upload-artifact@v3
        if: always()
        with:
          name: allure-report
          path: target/allure-report/
```

### AWS Pipeline
```
CodeCommit → CodeBuild → Execute Tests → Allure S3 Report → SNS Email Notification
```

---

## 📸 Screenshot & Failure Handling

The framework automatically handles failures with the following:

```java
// Hooks.java — auto screenshot on failure
@After
public void tearDown(Scenario scenario) {
    if (scenario.isFailed()) {
        String name = scenario.getName().replaceAll("\\s+", "_");
        byte[] screenshot = ((Page) BaseTest.getPage())
            .screenshot(new Page.ScreenshotOptions().setFullPage(true));
        scenario.attach(screenshot, "image/png", "FAILED_" + name);
    }
    BaseTest.quitBrowser();
}
```

- 📸 Full-page screenshot captured on every failure
- 📋 Screenshot embedded in Cucumber HTML and Allure reports
- 🔍 Detailed step-level logging with ✅ / ❌ indicators
- 🗂️ Screenshots saved to `reports/screenshots/FAILED_<ScenarioName>.png`

---

## 🏷️ Tags

| Tag | Description |
|-----|-------------|
| `@smoke` | Critical path — fast build verification |
| `@regression` | Full regression suite |
| `@Login` | Login scenarios only |
| `@Logout` | Logout scenarios only |
| `@Registration` | Registration flow |
| `@ForgotPassword` | Password reset flow |
| `@CartCheckout` | Cart and checkout flow |
| `@known_issue` | Known site defects — skipped gracefully as Pending |

---

## 🏗️ Framework Architecture

```
Feature Files (Gherkin)
        ↓
Step Definitions (stepdefinitions/)
        ↓
Page Object Model (pages/)
        ↓
BaseTest / DriverFactory (utils/)
        ↓
Playwright → Chromium Browser
        ↓
Application: practice.qabrains.com
        ↓
Allure + Cucumber Reports
        ↓
Jenkins / GitHub Actions / AWS Pipeline
```

### Senior QA Design Decisions

- **`ThreadLocal<Page>`** in `DriverFactory` ensures thread-safe parallel execution
- **`@known_issue` tag** skips broken site components as Pending — no false failures in CI
- **`ConfigReader`** externalizes all config from code — environment-independent execution
- **Hooks centralised** — single `@Before`/`@After` source of truth prevents duplicate browser opens
- **Soft assertions** used where appropriate — full scenario execution even on partial failures

---

## 📈 Future Enhancements

- [ ] Docker containerization — `docker-compose` for isolated test execution
- [ ] Parallel execution — TestNG `parallel="scenarios"` configuration
- [ ] BrowserStack integration — mobile and cross-browser cloud testing
- [ ] Database validation — JDBC-based backend assertion layer
- [ ] REST Assured API testing — backend contract validation alongside UI tests
- [ ] AI-assisted test generation — auto-generate step definitions from feature files

---

## 🤝 Contributing

1. Fork the repository
2. Create your branch: `git checkout -b feature/improvement`
3. Write your tests following the existing POM structure
4. Run `mvn test` to verify nothing breaks
5. Commit: `git commit -m 'Add improvement'`
6. Push and open a Pull Request

---

## 📄 License

This project is open-source under the **MIT License**.

---

## 🌟 Why This Framework?

| Strength | Detail |
|----------|--------|
| ✅ Industry-standard architecture | POM + BDD + Hooks — aligned with enterprise QA standards |
| ✅ Easy maintenance | Page classes isolate locators — change one place, fix everywhere |
| ✅ Scalable design | Add new features by adding one Page class + one Steps class |
| ✅ Readable test cases | Gherkin scenarios understood by developers, QA, and product managers |
| ✅ Fast execution | Playwright is 2–3x faster than Selenium for most test scenarios |
| ✅ CI/CD compatible | Jenkins, GitHub Actions, and AWS Pipeline examples included |
| ✅ Enterprise-ready | Thread-safe, configurable, reportable, and cloud-executable |

---

## 👤 Author

**Ranajit Baran Chowdhury**
Software Developer & QA Automation Engineer
- 📧 Email: chyranajit@gmail.com
- 🐙 GitHub: [@ranajitchowdhury](https://github.com/ranajitchowdhury)
- 🌐 Portfolio: [rbc6543.wixsite.com/rbc-portfolio](https://rbc6543.wixsite.com/rbc-portfolio)
- 💼 LinkedIn: [linkedin.com/in/ranajitchowdhury](https://linkedin.com/in/ranajitchowdhury)

---

**Version:** 1.0.0 | **Last Updated:** May 2026
