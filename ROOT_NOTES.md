# Root Notes for New Machine Setup

This file is the root note you can use to ask Copilot to install the project directly on a new Windows machine.

## What to run on the new machine

1. Open PowerShell as Administrator.
2. Install the required system software:
   - Java JDK 11 or higher
   - Apache Maven 3.6+
   - Git for Windows
   - Google Chrome
   - ChromeDriver matching the installed Chrome version

3. Clone the repository or copy the project folder to the new machine.

4. Run the setup script from the project root:

```powershell
cd C:\Users\Saroja\automationEcommerce
.\setup-automationEcommerce.ps1git

```

## Copilot prompt example

Use this prompt with Copilot:

> "Please install the project on this Windows machine using the existing `setup-automationEcommerce.ps1` script and verify it can run `mvn test`."

## Additional notes

- If `winget` is available, the script may install missing tools automatically.
- If the script cannot install software, install Java, Maven, Git, and Chrome manually first.
- If you already cloned the repo, skip cloning and just run the script.
- For Eclipse-specific import, see `README_ECLIPSE.md`.

## Repository URL

- `https://github.com/sarojatennety-sketch/automationEcommerce`
