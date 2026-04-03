# =====================================================
# Student Management System - PowerShell Startup Script
# =====================================================
# This script automatically sets up and runs your application
# Usage: Right-click this file > Run with PowerShell
# Or: Open PowerShell and run: .\START.ps1

param(
    [switch]$SkipMongoDB = $false,
    [switch]$SkipBuild = $false
)

Write-Host "`n" -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Green
Write-Host "Student Management System - Auto Setup" -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Green
Write-Host "`n"

# Set error action preference
$ErrorActionPreference = "Continue"

# Get script directory
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
Set-Location $ScriptDir

# Functions for output formatting
function Write-Success {
    param([string]$Message)
    Write-Host "[OK] $Message" -ForegroundColor Green
}

function Write-Error {
    param([string]$Message)
    Write-Host "[FAIL] $Message" -ForegroundColor Red
}

function Write-Warning {
    param([string]$Message)
    Write-Host "[WARN] $Message" -ForegroundColor Yellow
}

function Write-Info {
    param([string]$Message)
    Write-Host "[INFO] $Message" -ForegroundColor Cyan
}

# Step 1: Check Java
Write-Host "`nStep 1: Checking Java Installation..." -ForegroundColor Yellow
Write-Host "=========================================`n"

try {
    $javaVersion = & java -version 2>&1
    if ($LASTEXITCODE -eq 0) {
        Write-Success "Java is installed"
        Write-Info ($javaVersion[0])
    } else {
        throw "Java not found"
    }
} catch {
    Write-Error "Java is NOT installed or not in PATH"
    Write-Host "`nSOLUTION: Download Java 17+ from:" -ForegroundColor Cyan
    Write-Host "https://www.oracle.com/java/technologies/downloads/" -ForegroundColor Blue
    Write-Host "`nThen restart this script.`n" -ForegroundColor Cyan
    Read-Host "Press Enter to exit"
    exit 1
}

# Step 2: Check Maven
Write-Host "`nStep 2: Checking Maven Installation..." -ForegroundColor Yellow
Write-Host "=========================================`n"

try {
    $mvnVersion = & mvn --version 2>&1
    if ($LASTEXITCODE -eq 0) {
        Write-Success "Maven is installed"
    } else {
        throw "Maven not found"
    }
} catch {
    Write-Error "Maven is NOT installed or not in PATH"
    Write-Host "`nSOLUTION: Download Maven from:" -ForegroundColor Cyan
    Write-Host "https://maven.apache.org/download.cgi`n" -ForegroundColor Blue
    Write-Host "1. Extract to C:\Maven" -ForegroundColor Cyan
    Write-Host "2. Press Win+R, type: sysdm.cpl" -ForegroundColor Cyan
    Write-Host "3. Go to Environment Variables" -ForegroundColor Cyan
    Write-Host "4. Add C:\Maven\bin to PATH" -ForegroundColor Cyan
    Write-Host "5. Restart PowerShell and this script`n" -ForegroundColor Cyan
    Read-Host "Press Enter to exit"
    exit 1
}

# Step 3: Check MongoDB (unless skipped)
if (-not $SkipMongoDB) {
    Write-Host "`nStep 3: Checking MongoDB..." -ForegroundColor Yellow
    Write-Host "=========================================`n"

    $mongoRunning = Test-NetConnection -ComputerName localhost -Port 27017 -WarningAction SilentlyContinue | Where-Object {$_.TcpTestSucceeded}

    if ($mongoRunning) {
        Write-Success "MongoDB is running on port 27017"
    } else {
        Write-Warning "MongoDB is NOT running on port 27017"
        Write-Host "`nAttempting to start MongoDB...`n" -ForegroundColor Yellow

        $mongoPaths = @(
            "C:\Program Files\MongoDB\Server\7.0\bin\mongod.exe",
            "C:\Program Files\MongoDB\Server\6.0\bin\mongod.exe",
            "C:\Program Files\MongoDB\Server\5.0\bin\mongod.exe"
        )

        $mongoFound = $false
        foreach ($path in $mongoPaths) {
            if (Test-Path $path) {
                Write-Info "Starting MongoDB from: $path"
                Start-Process -FilePath $path -WindowStyle Minimized
                Start-Sleep -Seconds 3
                $mongoFound = $true
                break
            }
        }

        if ($mongoFound) {
            Write-Success "MongoDB started"
        } else {
            Write-Error "MongoDB not found in Program Files"
            Write-Host "`nSOLUTION: Install MongoDB Community Server from:" -ForegroundColor Cyan
            Write-Host "https://www.mongodb.com/try/download/community`n" -ForegroundColor Blue
            Write-Host "Or start MongoDB manually:" -ForegroundColor Cyan
            Write-Host "1. Open Command Prompt" -ForegroundColor Cyan
            Write-Host "2. Run: mongod.exe" -ForegroundColor Cyan
            Write-Host "3. Keep that window open" -ForegroundColor Cyan
            Write-Host "4. Run this script in a new PowerShell window`n" -ForegroundColor Cyan
            Write-Warning "Continuing without MongoDB..."
        }
    }
}

# Step 4: Clean Build
Write-Host "`nStep 5: Cleaning Previous Builds..." -ForegroundColor Yellow
Write-Host "=========================================`n"

if (Test-Path "target") {
    Write-Info "Removing old build artifacts..."
    Remove-Item "target" -Recurse -Force -ErrorAction SilentlyContinue
    Write-Success "Clean completed"
} else {
    Write-Info "No previous builds to clean"
}

# Step 5: Build (unless skipped)
if (-not $SkipBuild) {
    Write-Host "`nStep 6: Downloading Dependencies and Building..." -ForegroundColor Yellow
    Write-Host "=========================================`n"
    Write-Host "This may take several minutes on first run...`n" -ForegroundColor Yellow

    & mvn clean install -DskipTests

    if ($LASTEXITCODE -ne 0) {
        Write-Error "Build FAILED"
        Write-Host "`nTroubleshooting:" -ForegroundColor Cyan
        Write-Host "1. Check internet connection" -ForegroundColor Cyan
        Write-Host "2. Try again - Maven may need to download dependencies" -ForegroundColor Cyan
        Write-Host "3. Delete: $env:USERPROFILE\.m2\repository" -ForegroundColor Cyan
        Read-Host "`nPress Enter to exit"
        exit 1
    }

    Write-Success "Build completed successfully"
}

# Step 6: Start Application
Write-Host "`nStep 7: Starting Application..." -ForegroundColor Yellow
Write-Host "========================================="`n

Write-Host "*** APPLICATION STARTING ***`n" -ForegroundColor Green
Write-Host "Access the application at: http://localhost:8080/login" -ForegroundColor Cyan
Write-Host "`nLogin Credentials:" -ForegroundColor Yellow
Write-Host "  Email: omkarpatange77@gmail.com" -ForegroundColor Cyan
Write-Host "  Password: Omkar@3483`n" -ForegroundColor Cyan
Write-Host "To Stop: Press Ctrl+C`n" -ForegroundColor Yellow
Write-Host "=========================================`n" -ForegroundColor Green

& mvn spring-boot:run

Read-Host "Application stopped. Press Enter to exit"
