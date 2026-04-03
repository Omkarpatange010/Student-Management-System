#!/usr/bin/env pwsh
# AUTO-FIX: Connection Refused Error
# This script automatically fixes ERR_CONNECTION_REFUSED issues

# Run as Administrator check
$isAdmin = [bool]([Security.Principal.WindowsIdentity]::GetCurrent().Groups -match "S-1-5-32-544")
if (-not $isAdmin) {
    Write-Host "`n⚠️  ADMIN REQUIRED: Restarting PowerShell as Administrator..." -ForegroundColor Yellow
    Start-Process PowerShell "-NoProfile -ExecutionPolicy Bypass -Command `"cd '$pwd'; & '$PSCommandPath'`"" -Verb RunAs
    exit
}

Clear-Host
Write-Host "🔧 AUTO-FIX: Connection Refused Error" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan

# Colors
$ok = @{ForegroundColor="Green"}
$fail = @{ForegroundColor="Red"}
$info = @{ForegroundColor="Yellow"}
$header = @{ForegroundColor="Cyan"}

# Step 1: Check MongoDB
Write-Host "`n📍 STEP 1: Checking MongoDB..." @header
$mongoRunning = netstat -ano 2>$null | findstr :27017
if ($mongoRunning) {
    Write-Host "[✅ OK] MongoDB is running on port 27017" @ok
} else {
    Write-Host "[❌ FAIL] MongoDB is NOT running" @fail
    Write-Host "`nAttempting to START MongoDB..." @info
    
    $mongoPath = "C:\Program Files\MongoDB\Server\7.0\bin\mongod.exe"
    if (Test-Path $mongoPath) {
        Write-Host "Starting: $mongoPath" @info
        Start-Process -FilePath $mongoPath -WindowStyle Normal
        Write-Host "✓ MongoDB starting in new window..." @ok
        Write-Host "  Wait 5-10 seconds for 'Waiting for connections' message" @info
        Write-Host "  Keep that window open!" @info
        Start-Sleep -Seconds 3
    } else {
        Write-Host "❌ MongoDB not found at: $mongoPath" @fail
        Write-Host "Please install MongoDB 7.0 first" @fail
    }
}

# Step 2: Check if port 8080 is in use
Write-Host "`n📍 STEP 2: Checking port 8080..." @header
$port8080 = netstat -ano 2>$null | findstr :8080
if ($port8080) {
    Write-Host "[❌ FAIL] Port 8080 is ALREADY IN USE!" @fail
    Write-Host "Process using port 8080:" @fail
    $port8080 | ForEach-Object {
        $parts = $_ -split '\s+'
        $pid = $parts[-1]
        Write-Host "  PID: $pid" @fail
        Write-Host "  Run this to kill it: taskkill /PID $pid /F" @info
    }
} else {
    Write-Host "[✅ OK] Port 8080 is available" @ok
}

# Step 3: Verify Java
Write-Host "`n📍 STEP 3: Checking Java..." @header
$javaCheck = java -version 2>&1
if ($javaCheck -match "17|21|20|19|18") {
    Write-Host "[✅ OK] Java installed" @ok
    $javaVersion = ($javaCheck | select -first 1) -replace "openjdk version ", ""
    Write-Host "  Version: $javaVersion" @info
} else {
    Write-Host "[❌ FAIL] Java 17+ not found" @fail
}

# Step 4: Verify Maven
Write-Host "`n📍 STEP 4: Checking Maven..." @header
$mvnCheck = mvn --version 2>&1
if ($mvnCheck -match "Apache Maven") {
    Write-Host "[✅ OK] Maven installed" @ok
    Write-Host "  $($mvnCheck | select -first 1)" @info
} else {
    Write-Host "[❌ FAIL] Maven not found" @fail
}

# Step 5: Rebuild project
Write-Host "`n📍 STEP 5: Rebuilding project..." @header
$projectPath = "c:\MyWokspace\Java\Student_management-System"
if (Test-Path "$projectPath\pom.xml") {
    Write-Host "Location: $projectPath" @info
    
    # Remove old build
    Write-Host "Cleaning old builds..." @info
    if (Test-Path "$projectPath\target") {
        Remove-Item "$projectPath\target" -Recurse -Force -ErrorAction SilentlyContinue
        Write-Host "✓ Old build removed" @ok
    }
    
    # Maven clean install
    Write-Host "Running: mvn clean install -DskipTests" @info
    cd $projectPath
    $buildOutput = mvn clean install -DskipTests 2>&1
    
    if ($buildOutput -match "BUILD SUCCESS") {
        Write-Host "[✅ OK] Build successful" @ok
    } else {
        Write-Host "[❌ FAIL] Build failed" @fail
        Write-Host "Last 10 lines of output:" @fail
        $buildOutput | select -last 10 | ForEach-Object {
            Write-Host "  $_" @fail
        }
    }
} else {
    Write-Host "[❌ FAIL] pom.xml not found at $projectPath" @fail
}

# Step 6: Start application
Write-Host "`n📍 STEP 6: Starting application..." @header
Write-Host "Command: mvn spring-boot:run" @info
Write-Host "Waiting for 'Tomcat started on port(s): 8080'..." @info
Write-Host "`nPress CTRL+C in the application window to stop it" @info

cd $projectPath
Write-Host "`n" @header
mvn spring-boot:run

