# =====================================================
# Student Management System - Quick Diagnostic Script
# =====================================================
# Run this to diagnose "Site can't be reached" issue

Write-Host "`n=== STUDENT MANAGEMENT SYSTEM - DIAGNOSTIC ===`n" -ForegroundColor Cyan

# 1. Check MongoDB
Write-Host "1. Checking MongoDB (port 27017)..." -ForegroundColor Yellow
$mongo = netstat -ano 2>$null | findstr ":27017"
if ($mongo) {
    Write-Host "[OK] MongoDB is running!" -ForegroundColor Green
    Write-Host $mongo
} else {
    Write-Host "[FAIL] MongoDB is NOT running" -ForegroundColor Red
    Write-Host "SOLUTION: Open Command Prompt and run: mongod.exe" -ForegroundColor Yellow
    Write-Host "THEN run this diagnostic again.`n" -ForegroundColor Yellow
}

# 2. Check Application
Write-Host "`n2. Checking Application (port 8080)..." -ForegroundColor Yellow
$app = netstat -ano 2>$null | findstr ":8080"
if ($app) {
    Write-Host "[OK] Application is running!" -ForegroundColor Green
    Write-Host $app
    Write-Host "`n[SUCCESS] Try accessing: http://localhost:8080/login" -ForegroundColor Green
} else {
    Write-Host "[FAIL] Application is NOT running on port 8080" -ForegroundColor Red
    Write-Host "SOLUTION: Run START.bat or run: mvn spring-boot:run" -ForegroundColor Yellow
}

# 3. Check Java
Write-Host "`n3. Checking Java..." -ForegroundColor Yellow
$java = java -version 2>&1
if ($LASTEXITCODE -eq 0) {
    Write-Host "[OK] Java is installed" -ForegroundColor Green
    Write-Host $java[0]
} else {
    Write-Host "[FAIL] Java is NOT installed or not in PATH" -ForegroundColor Red
}

# 4. Check Maven
Write-Host "`n4. Checking Maven..." -ForegroundColor Yellow
$mvn = mvn --version 2>&1 | select -first 1
if ($LASTEXITCODE -eq 0) {
    Write-Host "[OK] Maven is installed" -ForegroundColor Green
    Write-Host $mvn
} else {
    Write-Host "[FAIL] Maven is NOT installed or not in PATH" -ForegroundColor Red
}

# 5. Try to connect
Write-Host "`n5. Testing connection to http://localhost:8080/login..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest http://localhost:8080/login -ErrorAction Stop
    Write-Host "[OK] Connection successful!" -ForegroundColor Green
    Write-Host "Status: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "`nSite is WORKING! Open: http://localhost:8080/login" -ForegroundColor Green
} catch {
    Write-Host "[FAIL] Cannot reach the application" -ForegroundColor Red
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n`n=== WHAT TO DO NEXT ===`n" -ForegroundColor Cyan

# Recommendations
if (-not $mongo) {
    Write-Host "1. START MONGODB:" -ForegroundColor Yellow
    Write-Host "   - Open Command Prompt" -ForegroundColor Gray
    Write-Host "   - Run: mongod.exe" -ForegroundColor Gray
    Write-Host "   - Keep this window open`n" -ForegroundColor Gray
}

if (-not $app) {
    Write-Host "1. START APPLICATION:" -ForegroundColor Yellow
    Write-Host "   Option A: Double-click START.bat`n" -ForegroundColor Gray
    Write-Host "   Option B: Run these commands:" -ForegroundColor Gray
    Write-Host "   - cd c:\MyWokspace\Java\Student_management-System" -ForegroundColor Gray
    Write-Host "   - mvn spring-boot:run`n" -ForegroundColor Gray
}

Write-Host "2. WAIT FOR MESSAGE: 'Tomcat started on port(s): 8080'" -ForegroundColor Yellow
Write-Host "`n3. OPEN BROWSER: http://localhost:8080/login" -ForegroundColor Yellow
Write-Host "`n4. LOGIN WITH:" -ForegroundColor Yellow
Write-Host "   Email: omkarpatange77@gmail.com" -ForegroundColor Cyan
Write-Host "   Password: Omkar@3483`n" -ForegroundColor Cyan

Write-Host "For more help, see: DIAGNOSTIC.md`n" -ForegroundColor Gray

Read-Host "Press Enter to close this window"
