# SauceLab Automation Framework — Sauce Labs Cloud Testing

![Java](https://img.shields.io/badge/Java-11+-orange?logo=java)
![Selenium](https://img.shields.io/badge/Selenium-WebDriver-43B02A?logo=selenium)
![Cucumber](https://img.shields.io/badge/Cucumber-BDD-brightgreen?logo=cucumber)
![TestNG](https://img.shields.io/badge/TestNG-Runner-red)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apache-maven)
![SauceLabs](https://img.shields.io/badge/Sauce_Labs-Cloud-E2231A?logo=saucelabs)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

**Author:** Ranajit Baran Chowdhury — Software Programmer & QA Automation Engineer
**Email:** chyranajit@gmail.com
**GitHub:** [@ranajitchowdhury](https://github.com/ranajitchowdhury)

A robust, production-grade test automation framework built with **Selenium + Java + Cucumber + TestNG**, integrated with **Sauce Labs** for cross-browser cloud execution. This project demonstrates real-world QA automation practices including BDD, Page Object Model, parallel execution, and CI/CD-ready pipeline structure.

---

## 📋 Tech Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| **Language** | Java 11+ | Core programming language |
| **Automation** | Selenium WebDriver | Browser UI interaction |
| **BDD Framework** | Cucumber (Gherkin) | Human-readable test scenarios |
| **Test Runner** | TestNG | Execution, grouping, parallel runs |
| **Build Tool** | Maven | Dependency management |
| **Cloud Platform** | Sauce Labs | Cross-browser cloud execution |
| **Design Pattern** | Page Object Model | Maintainable UI abstraction |
| **Reporting** | Cucumber HTML + Sauce Labs Dashboard | Test results and video logs |

---

## 📁 Project Structure

```
qaBrainsAutomation/
│
├── src/test/java/
│   ├── stepdefinitions/        ← Cucumber step definition classes
│   │   ├── LoginSteps.java
│   │   ├── CartSteps.java
│   │   ├── RegistrationSteps.java
│   │   └── CommonSteps.java
│   ├── pages/                  ← Page Object Model (POM) classes
│   │   ├── LoginPage.java
│   │   ├── CartPage.java
│   │   ├── RegistrationPage.java
│   │   └── ForgotPasswordPage.java
│   ├── hooks/                  ← Browser setup & teardown
│   │   └── Hooks.java
│   └── runners/                ← TestNG runner configuration
│       └── TestRunner.java
│
├── src/test/resources/
│   ├── features/               ← Gherkin feature files
│   │   ├── Login.feature
│   │   ├── CartCheckout.feature
│   │   ├── Registration.feature
│   │   ├── ForgotPassword.feature
│   │   ├── SmokeTests.feature
│   │   └── RegressionTests.feature
│   └── config/                 ← Configuration files
│       └── config.properties
│
├── pom.xml                     ← Maven dependencies & plugins
├── testng.xml                  ← TestNG suite configuration
└── README.md
```

---

## ✨ Key Features

| Feature | Description |
|---------|-------------|
| ✅ BDD with Cucumber | Gherkin syntax — plain English test scenarios readable by stakeholders |
| ✅ Page Object Model | Clean separation of UI locators and test logic per page |
| ✅ Sauce Labs Cloud | Execute on 700+ browser/OS combinations without local setup |
| ✅ Parallel Execution | Multi-threaded test runs via TestNG for faster feedback |
| ✅ Cross-Browser | Chrome, Firefox, Edge, Safari via Sauce Labs grid |
| ✅ Screenshot on Failure | Auto-captured failure screenshots in Sauce Labs dashboard |
| ✅ Video Recording | Every test run recorded and stored in Sauce Labs |
| ✅ CI/CD Ready | Plug directly into Jenkins or GitHub Actions |

---

## 🎯 Test Coverage

### 🔐 Authentication
- Valid and invalid login validation
- Session management and logout verification
- Error message display for incorrect credentials

### 🛒 Cart & Checkout
- Add product to cart
- Remove product from cart
- Quantity update verification
- Full checkout flow validation

### 📝 Registration
- Successful registration with valid data
- Email format validation
- Duplicate registration handling

### 🔑 Forgot Password
- Reset link request with registered email
- Error handling for unregistered email

### 🚀 Smoke & Regression
- Critical path verification (Smoke)
- Full application regression suite

---

## ✅ Prerequisites

Ensure the following are installed before setup:

- ✅ **JDK 11 or higher** — configured in system PATH
- ✅ **Maven** — latest stable version
- ✅ **IDE** — Eclipse or IntelliJ IDEA
- ✅ **Sauce Labs Account** — with Username and Access Key
- ✅ **Git** — for cloning the repository

---

## ☁️ Sauce Labs Setup

**Step 1 — Create a Sauce Labs account:**
Visit [saucelabs.com](https://saucelabs.com) and sign up for a free trial.

**Step 2 — Get your credentials:**
Go to **Account → User Settings** and copy your:
- `SAUCE_USERNAME`
- `SAUCE_ACCESS_KEY`

**Step 3 — Configure in your framework:**

```java
String USERNAME    = System.getenv("SAUCE_USERNAME");
String ACCESS_KEY  = System.getenv("SAUCE_ACCESS_KEY");
String SAUCE_URL   = "https://" + USERNAME + ":" + ACCESS_KEY
                   + "@ondemand.us-west-1.saucelabs.com:443/wd/hub";
```

> **Best Practice:** Store credentials as environment variables, never hardcode them.

**Step 4 — Set environment variables (Mac/Linux):**
```bash
export SAUCE_USERNAME="your_username"
export SAUCE_ACCESS_KEY="your_access_key"
```

---

## ⚙️ Installation & Setup

**1. Clone the repository:**
```bash
git clone https://github.com/ranajitchowdhury/QaBrainsSauceLabAPI_Automation.git
cd QaBrainsSauceLabAPI_Automation
```

**2. Install dependencies:**
```bash
mvn clean install -DskipTests
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
# Smoke tests only
mvn test -Dcucumber.filter.tags="@smoke"

# Regression tests only
mvn test -Dcucumber.filter.tags="@regression"

# Login tests only
mvn test -Dcucumber.filter.tags="@Login"
```

### Run in parallel
```bash
mvn test -DsuiteXmlFile=testng-parallel.xml
```

---

## 📊 Reports & Dashboards

| Report Type | Location |
|-------------|---------|
| **Cucumber HTML** | `target/cucumber-reports/cucumber.html` |
| **TestNG Report** | `target/surefire-reports/index.html` |
| **Sauce Labs Dashboard** | [app.saucelabs.com](https://app.saucelabs.com) — video, logs, screenshots |

Open Cucumber report after test run:
```bash
open target/cucumber-reports/cucumber.html
```

---

## 🔧 Common Issues & Fixes

### ❌ `NoSuchElementException`
```
✔ Use explicit WebDriverWait instead of Thread.sleep
✔ Verify XPath/CSS selector in browser DevTools
✔ Check if element is inside an iframe
```

### ❌ `DuplicateStepDefinition`
```
✔ Ensure each Gherkin step is defined in only one class
✔ Separate step definition classes by feature domain
✔ Check glue path in TestRunner — avoid duplicate packages
```

### ❌ `Driver NullPointerException`
```
✔ Initialize WebDriver in @Before hook, not in constructor
✔ Use ThreadLocal<WebDriver> for parallel execution safety
✔ Verify Hooks.java is included in the glue path
```

### ❌ `Element not found in Sauce Labs`
```
✔ Add explicit waits for dynamic elements
✔ Check for async DOM updates (AJAX)
✔ Increase implicit wait timeout in config
✔ Review Sauce Labs video recording for visual diagnosis
```

---

## 🔄 CI/CD Integration

### Jenkins Pipeline
```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test -DsuiteXmlFile=testng.xml'
            }
        }
    }
    post {
        always {
            cucumber buildStatus: 'UNSTABLE',
                     reportTitle: 'QA Brains Report',
                     fileIncludePattern: '**/cucumber.json'
        }
    }
}
```

### GitHub Actions
```yaml
name: QA Automation

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
      - name: Run Tests
        env:
          SAUCE_USERNAME: ${{ secrets.SAUCE_USERNAME }}
          SAUCE_ACCESS_KEY: ${{ secrets.SAUCE_ACCESS_KEY }}
        run: mvn clean test
```

---

## 🏗️ Framework Architecture

```
Feature Files (Gherkin)
        ↓
Step Definitions (stepdefinitions/)
        ↓
Page Object Model (pages/)
        ↓
Selenium WebDriver
        ↓
Sauce Labs Cloud Grid
        ↓
Chrome / Firefox / Edge / Safari

```

---

## 🌟 Why This Project Stands Out

| Highlight | Detail |
|-----------|--------|
| ☁️ Cloud Testing | 700+ browser/OS combos via Sauce Labs |
| 🎥 Video Replay | Every test run recorded for defect analysis |
| ♟️ BDD | Plain English scenarios aligned with business requirements |
| 🔁 Parallel Execution | Faster feedback with TestNG parallel configuration |
| 🔒 Secure Credentials | Environment variable-based auth, no hardcoded secrets |
| 📦 CI/CD Ready | Jenkins + GitHub Actions examples included |
| 🧩 Scalable Design | POM makes adding new pages and scenarios effortless |

---

## 📈 Future Enhancements

- [ ] REST API Testing — REST Assured integration
- [ ] Docker support — containerized test execution
- [ ] Allure Reports — richer test reporting with trends
- [ ] Parallel execution optimization — TestNG data providers
- [ ] Database validation — JDBC-based backend assertions
- [ ] Performance testing — JMeter integration

---

## 🤝 Contributing

1. Fork the repository
2. Create your branch: `git checkout -b feature/improvement`
3. Commit your changes: `git commit -m 'Add improvement'`
4. Push: `git push origin feature/improvement`
5. Open a Pull Request

---

## 📄 License

This project is open-source under the **MIT License**.

---

## 👤 Author

**Ranajit Baran Chowdhury**
Software Programmer & QA Automation Engineer
- 📧 Email: chyranajit@gmail.com
- 🐙 GitHub: [@ranajitchowdhury](https://github.com/ranajitchowdhury)

---

**Version:** 1.0.0
**Last Updated:** May 2026
