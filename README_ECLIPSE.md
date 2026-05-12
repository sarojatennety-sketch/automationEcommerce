# Eclipse Setup Guide for automationEcommerce

This project is already configured as an Eclipse Maven project. The repository includes:

- `.project`
- `.classpath`
- `.settings/org.eclipse.jdt.core.prefs`
- `.settings/org.eclipse.m2e.core.prefs`

These files allow Eclipse to import the project with the correct Java 11 settings and Maven integration.

## Prerequisites

1. Install Java JDK 11 or higher.
2. Install Eclipse IDE for Java Developers or Eclipse IDE for Enterprise Java Developers.
3. Install Apache Maven if you prefer command-line builds.
4. Install Git for Windows if you will clone the repository from GitHub.
5. Install Google Chrome.
6. Download ChromeDriver matching your Chrome version and place it in a stable folder, for example:
   - `C:\WebDriver\chromedriver.exe`

## 1. Prepare the new Windows machine

If you want the machine to be fully prepared first, use the provided script:

```powershell
cd C:\Users\Saroja\automationEcommerce
.\setup-automationEcommerce.ps1
```

That script checks for:

- Java
- Maven
- Git
- Chrome

It also sets session environment variables and runs `mvn test`.

> If you are using Eclipse, run the script first to ensure the system software is installed and the repository is available.

## 2. Clone or copy the project

If the repository is not already available locally, clone it:

```powershell
cd C:\Users\Saroja\Documents
git clone https://github.com/sarojatennety-sketch/automationEcommerce.git
```

If the project is already on the machine, simply use that folder.

## 3. Import into Eclipse

1. Open Eclipse.
2. Choose a workspace location.
3. Select `File > Import...`.
4. Choose `Maven > Existing Maven Projects`.
5. Click `Next`.
6. Select the root folder of the cloned repository (`automationEcommerce`).
7. Ensure `pom.xml` is selected and click `Finish`.

Eclipse should import the project and configure it automatically using the included `.project`, `.classpath`, and `.settings` files.

## 4. Verify Eclipse project settings

In Eclipse, verify the following:

- `Project > Properties > Java Build Path`
  - JavaSE-11 should be selected.
- `Project > Properties > Java Compiler`
  - Compiler compliance level should be `11`.
- `Project > Properties > Maven`
  - `Resolve workspace projects` should be enabled by default.

## 5. Run the project in Eclipse

The project is a Maven test project. To run it in Eclipse:

1. Right-click the project in the Project Explorer.
2. Choose `Run As > Maven test`.
3. Alternatively, use `Run As > JUnit Test` if appropriate.

## 6. Run from the command line after Eclipse install

If Eclipse is installed and you want to run tests from PowerShell:

```powershell
cd C:\Users\Saroja\automationEcommerce
mvn test
```

If ChromeDriver is needed explicitly:

```powershell
$env:CHROME_DRIVER_PATH = 'C:\WebDriver\chromedriver.exe'
mvn test
```

## 7. Notes

- The Eclipse project files are already in version control, so import should work without additional project generation.
- Use Java 11 as defined by `.settings/org.eclipse.jdt.core.prefs`.
- If Maven dependencies are missing, right-click the project and choose `Maven > Update Project...`.
- If the project still fails, confirm the workspace is using the same JDK version as the project settings.
