# Windows Installation Guide for automationEcommerce

This document explains how to set up a new Windows machine to run the `automationEcommerce` Selenium test project.

Open Powershell and run - cd C:\Users\Saroja\automationEcommerce
.\setup-automationEcommerce.ps1

## 0. Quick install sequence

If you want to run the setup directly on a new machine, follow these steps in order:

1. Install Java JDK 11 or higher
2. Install Apache Maven 3.6+
3. Install Git for Windows
4. Install Google Chrome
5. Install ChromeDriver that matches your Chrome version
6. Set the required environment variables
7. Clone the GitHub repository
8. Run the Maven test command

Use this PowerShell command sequence after installing the required software:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21.0.10'
$env:MAVEN_HOME = 'C:\Program Files\apache-maven-3.9.15'
$env:PATH = "$env:JAVA_HOME\bin;$env:MAVEN_HOME\bin;$env:PATH"
$env:CHROME_DRIVER_PATH = 'C:\WebDriver\chromedriver.exe'

java -version
mvn -version
git --version

cd C:\Users\Saroja\Documents
git clone https://github.com/sarojatennety-sketch/automationEcommerce.git
cd automationEcommerce
mvn test
```

If the repository is already present on the machine, skip the `git clone` step and run:

```powershell
cd C:\Users\Saroja\automationEcommerce
mvn test
```

## 1. Required software

Install the following in this order:

1. Java JDK
   - Download and install Java 11 or higher. Java 17 or Java 21 is recommended.
   - Example install path: `C:\Program Files\Java\jdk-21.0.10`

2. Apache Maven
   - Download and install Maven 3.6+.
   - Example install path: `C:\Program Files\apache-maven-3.9.15`

3. Git
   - Install Git for Windows if you need to clone or copy the repository from another machine.

4. Google Chrome
   - Install the latest Google Chrome browser.
   - Make sure the Chrome version matches the ChromeDriver version used by your tests.

5. Browser driver (ChromeDriver)
   - This project uses Selenium with Chrome.
   - If your network or policy blocks automatic download, download a matching ChromeDriver binary manually from https://chromedriver.chromium.org/downloads.
   - Place the driver executable in a permanent folder, e.g. `C:\WebDriver\chromedriver.exe`.

## 2. Copy the project to the new machine

You can copy the project in one of these ways:

- Use Git clone if the repository is hosted remotely.
- Use a shared network folder, USB drive, or file copy from the current machine.

Example using Git:

```powershell
cd C:\Users\Saroja\Documents
git clone <repository-url> automationEcommerce
cd automationEcommerce
```

If copying directly, ensure the full project folder structure is preserved:

- `pom.xml`
- `README.md`
- `src/main/java/...`
- `src/test/java/...`
- `src/test/resources`

## 3. Set environment variables

### 3.1 JAVA_HOME

1. Open Windows Settings > System > About > Advanced system settings.
2. Click `Environment Variables...`.
3. Under `System variables`, click `New...` or `Edit...`.
4. Set:
   - Name: `JAVA_HOME`
   - Value: `C:\Program Files\Java\jdk-21.0.10`

5. Add the JDK bin folder to `Path` if not already present:
   - `%JAVA_HOME%\bin`

### 3.2 MAVEN_HOME and PATH

1. In `Environment Variables`, under `System variables`:
   - Name: `MAVEN_HOME`
   - Value: `C:\Program Files\apache-maven-3.9.15`

2. Add Maven `bin` to `Path`:
   - `%MAVEN_HOME%\bin`

### 3.3 Optional: CHROME_DRIVER_PATH

If your environment requires an explicit ChromeDriver path, add:

- Name: `CHROME_DRIVER_PATH`
- Value: `C:\WebDriver\chromedriver.exe`

If not using `CHROME_DRIVER_PATH`, the project uses `WebDriverManager` to download the correct driver automatically at runtime.

## 4. Verify installation

Open PowerShell and run:

```powershell
java -version
mvn -version
git --version
```

Expected results:

- Java version 11 or higher
- Maven 3.6+
- Git command available

## 5. Configure project-specific settings

1. Open the project folder in VS Code or your Java IDE.
2. Confirm `pom.xml` includes the Selenium, TestNG, and WebDriverManager dependencies.
3. In the test code, update Amazon login credentials if required by the project.

## 6. Running the tests

Open PowerShell in the project root folder and run:

```powershell
cd C:\Users\Saroja\automationEcommerce
mvn test
```

If you set `CHROME_DRIVER_PATH`, run:

```powershell
$env:CHROME_DRIVER_PATH = 'C:\WebDriver\chromedriver.exe'
mvn test
```

## 7. Notes for a new Windows machine

- If the project fails because ChromeDriver cannot be downloaded, use the manually downloaded driver and set `CHROME_DRIVER_PATH`.
- Ensure the installed Chrome browser version matches the ChromeDriver version.
- If network policies block `WebDriverManager`, the explicit `webdriver.chrome.driver` path can be supplied in code or environment variables.
- Keep the project folder path simple and avoid spaces if using older build tools.

## 8. Troubleshooting

- `java` not found: Verify `JAVA_HOME` and `Path` are correctly configured.
- `mvn` not found: Verify Maven `bin` is on `Path`.
- ChromeDriver mismatch: Download the ChromeDriver version matching installed Chrome.
- Build errors: Run `mvn clean test` to refresh dependencies.

## 9. Optional convenience commands

Use this PowerShell snippet to export the required variables for the current session:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21.0.10'
$env:PATH = "$env:JAVA_HOME\bin;C:\Program Files\apache-maven-3.9.15\bin;$env:PATH"
$env:CHROME_DRIVER_PATH = 'C:\WebDriver\chromedriver.exe'
```

Then run:

```powershell
mvn test
```

## 10. Run the provided setup script

A ready-to-run script is available in the repository root:

- `setup-automationEcommerce.ps1`

Run it from PowerShell like this:

```powershell
cd C:\Users\Saroja\automationEcommerce
.\setup-automationEcommerce.ps1
```

If you want to use the script with a different clone location:

```powershell
.\setup-automationEcommerce.ps1 -ClonePath "C:\Users\Saroja\automationEcommerce"
```

---

This guide is written specifically for the current Windows Selenium Maven project and includes the full setup needed to migrate it to a new Windows machine.