<#
.SYNOPSIS
Sets up environment variables, clones the repository if needed, and runs Maven tests.

.DESCRIPTION
This script checks for Java, Maven, Git, and Chrome. If winget is available, it can install missing packages.
It then clones the GitHub repository and runs `mvn test` in the project root.
#>

param(
    [string]$JavaInstallPath = "C:\Program Files\Java\jdk-21.0.10",
    [string]$MavenInstallPath = "C:\Program Files\apache-maven-3.9.15",
    [string]$ChromeDriverPath = "C:\WebDriver\chromedriver.exe",
    [string]$RepoUrl = "https://github.com/sarojatennety-sketch/automationEcommerce.git",
    [string]$ClonePath = "$env:USERPROFILE\\automationEcommerce"
)

function Write-Section {
    param([string]$Text)
    Write-Host "`n=== $Text ===" -ForegroundColor Cyan
}

function Ensure-Command {
    param([string]$Name, [string]$InstallHint)
    if (-not (Get-Command $Name -ErrorAction SilentlyContinue)) {
        Write-Host "WARNING: '$Name' is not available. $InstallHint" -ForegroundColor Yellow
        return $false
    }
    return $true
}

Write-Section "Environment check"
$winget = Get-Command winget -ErrorAction SilentlyContinue
if ($winget) {
    Write-Host "winget is available. Missing packages may be installed automatically."
} else {
    Write-Host "winget is not available. Please install required software manually if missing." -ForegroundColor Yellow
}

$javaOk = Ensure-Command java "Install Java JDK 11+ and set JAVA_HOME."
$mavenOk = Ensure-Command mvn "Install Maven 3.6+ and add it to PATH."
$gitOk = Ensure-Command git "Install Git for Windows and add it to PATH."
$chromeOk = Ensure-Command chrome "Install Google Chrome."

if ($winget) {
    if (-not $javaOk) {
        Write-Host "Installing Java 21 via winget..."
        winget install --id Eclipse.Adoptium.Temurin.21.JDK -e --accept-package-agreements --accept-source-agreements
    }
    if (-not $mavenOk) {
        Write-Host "Installing Maven via winget..."
        winget install --id Apache.Maven -e --accept-package-agreements --accept-source-agreements
    }
    if (-not $gitOk) {
        Write-Host "Installing Git via winget..."
        winget install --id Git.Git -e --accept-package-agreements --accept-source-agreements
    }
    if (-not $chromeOk) {
        Write-Host "Installing Google Chrome via winget..."
        winget install --id Google.Chrome -e --accept-package-agreements --accept-source-agreements
    }
}

Write-Section "Set environment variables for this session"
$env:JAVA_HOME = $JavaInstallPath
$env:MAVEN_HOME = $MavenInstallPath
$env:PATH = "$env:JAVA_HOME\\bin;$env:MAVEN_HOME\\bin;$env:PATH"
if (Test-Path $ChromeDriverPath) {
    $env:CHROME_DRIVER_PATH = $ChromeDriverPath
    Write-Host "Set CHROME_DRIVER_PATH=$ChromeDriverPath"
} else {
    Write-Host "ChromeDriver not found at $ChromeDriverPath. If needed, download a matching ChromeDriver and update the script path." -ForegroundColor Yellow
}

Write-Section "Verify installed tools"
java -version
mvn -version
git --version

Write-Section "Clone or update repository"
if (-not (Test-Path $ClonePath)) {
    Write-Host "Cloning repository to $ClonePath"
    git clone $RepoUrl $ClonePath
} else {
    Write-Host "Repository folder already exists at $ClonePath"
}

Set-Location $ClonePath

if (-not (Test-Path ".git")) {
    Write-Host "ERROR: The clone path is not a git repository." -ForegroundColor Red
    exit 1
}

Write-Section "Run Maven tests"
mvn test

Write-Section "Finished"
Write-Host "If the tests succeed, your project is installed and running." -ForegroundColor Green
