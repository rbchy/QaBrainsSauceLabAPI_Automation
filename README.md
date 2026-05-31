🚀 QA Brains Automation Framework (Sauce Labs)
A robust test automation framework built using Selenium + Java + Cucumber + TestNG, integrated with Sauce Labs for cross-browser cloud execution.

This project demonstrates real-world QA automation practices including BDD (Behavior Driven Development), Page Object Model (POM), CI-ready structure, and cloud testing.# QaBrainsSauceLabAPI_Automation

📌 Project Overview
This framework automates web application testing using modern QA tools and best practices.

It supports:

UI Automation Testing

Cross-browser testing using Sauce Labs

BDD with Cucumber

Parallel execution with TestNG

Scalable and maintainable test structure

| Layer           | Technology              |
| --------------- | ----------------------- |
| Language        | Java                    |
| Automation Tool | Selenium WebDriver      |
| BDD Framework   | Cucumber                |
| Test Runner     | TestNG                  |
| Build Tool      | Maven                   |
| Cloud Platform  | Sauce Labs              |
| Design Pattern  | Page Object Model (POM) |


qaBrainsAutomation/
│
├── src/test/java/
│   ├── stepdefinitions/     # Step definition classes
│   ├── pages/               # Page Object classes
│   ├── hooks/               # Hooks (setup/teardown)
│   └── runners/             # Test runner classes
│
├── src/test/resources/
│   ├── features/            # Cucumber feature files
│   └── config/              # Config files (if any)
│
├── pom.xml                  # Maven dependencies
└── README.md

🔑 Key Features
✅ BDD Framework (Cucumber)
Write test cases in plain English using Gherkin.

✅ Page Object Model (POM)
Clean separation between test logic and UI elements.

✅ Sauce Labs Integration
Run tests on multiple browsers and OS combinations.

✅ Reusable Components
Common utilities and hooks for driver management.

✅ Cross-Browser Testing
Supports Chrome, Firefox, Edge via Sauce Labs.

⚙️ Prerequisites
Make sure you have installed:

Java JDK 11 or higher

Maven

IDE (Eclipse / IntelliJ)

Sauce Labs Account

🔐 Sauce Labs Setup
Create account on Sauce Labs

Get your credentials:

Username

Access Key

Add in your framework (Example):

String USERNAME = "your_username";
String ACCESS_KEY = "your_access_key";

String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";

▶️ How to Run Tests
Run via Maven
mvn clean test

Run specific tags
mvn test -Dcucumber.filter.tags="@smoke"
🧪 Sample Test Scenarios
✅ Login functionality

✅ Add to cart

✅ Remove from cart

✅ Checkout flow

✅ Forgot password

✅ Registration

📊 Reporting
TestNG Reports

Cucumber HTML Reports

Sauce Labs Dashboard (Video + Logs)

⚠️ Common Issues & Fixes
❌ NoSuchElementException
✔ Fix:

Use explicit wait

Verify locator (XPath/CSS)

Check if element is inside iframe
❌ DuplicateStepDefinition
✔ Fix:

Ensure each step is defined only once

Separate step classes properly

❌ Driver NullPointerException
✔ Fix:

Initialize driver in Hooks class

Use Singleton pattern for driver

❌ Element not found in Sauce Labs
✔ Fix:

Add wait conditions

Check dynamic DOM changes

🔄 CI/CD Ready
This framework can be integrated with:

Jenkins

GitHub Actions

Example:

mvn clean test -DsuiteXmlFile=testng.xml
🎯 Why This Project Stands Out
✔ Real-world automation framework
✔ Cloud testing with Sauce Labs
✔ Scalable design using POM
✔ BDD implementation for readability
✔ CI/CD ready structure

👨‍💻 Author
Ranajit Baran Chowdhury
Software Programmer & QA Automation Engineer

📌 Future Enhancements
✅ API Testing integration (Rest Assured)

✅ Jenkins pipeline setup

✅ Parallel execution optimization

✅ Docker support
