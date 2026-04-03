@echo off
REM =====================================================
REM Student Management System - Full Auto Setup & Run
REM =====================================================
setlocal enabledelayedexpansion

echo.
echo ========================================
echo Student Management System - Auto Setup
echo ========================================
echo.

REM Get current directory
set SCRIPT_DIR=%~dp0
cd /d "%SCRIPT_DIR%"

REM Colors for output (using simple methods for Windows)
set SUCCESS=[OK]
set ERROR=[FAIL]
set WARNING=[WARN]

echo.
echo Step 1: Checking Java Installation...
echo =========================================
java -version >nul 2>&1
if errorlevel 1 (
    echo %ERROR% Java is NOT installed or not in PATH
    echo.
    echo Attempting to locate Java...
    where java >nul 2>&1
    if errorlevel 1 (
        echo %ERROR% Could not find Java
        echo.
        echo SOLUTION: Download and install Java 17+ from:
        echo https://www.oracle.com/java/technologies/downloads/
        echo.
        echo Then restart this script.
        pause
        exit /b 1
    )
) else (
    for /f "tokens=3" %%i in ('java -version 2^>^&1 ^| findstr /R "version"') do set JAVA_VER=%%i
    echo %SUCCESS% Java is installed: !JAVA_VER!
)

echo.
echo Step 2: Checking Maven Installation...
echo =========================================
mvn --version >nul 2>&1
if errorlevel 1 (
    echo %ERROR% Maven is NOT installed or not in PATH
    echo.
    echo SOLUTION: Download Maven from:
    echo https://maven.apache.org/download.cgi
    echo.
    echo 1. Extract to a folder (e.g., C:\Maven)
    echo 2. Add to PATH:
    echo    - Press Win+R, type: sysdm.cpl
    echo    - Go to: Environment Variables
    echo    - Add: C:\Maven\bin to PATH
    echo 3. Restart Command Prompt and this script
    echo.
    pause
    exit /b 1
) else (
    for /f "tokens=3" %%i in ('mvn --version 2^>^&1 ^| findstr /R "Apache Maven"') do set MAVEN_VER=%%i
    echo %SUCCESS% Maven is installed
)

echo.
echo Step 3: Checking MongoDB...
echo =========================================
netstat -ano 2>nul | findstr :27017 >nul 2>&1
if errorlevel 1 (
    echo %WARNING% MongoDB is NOT running on port 27017
    echo.
    echo Attempting to start MongoDB...
    
    REM Try to find MongoDB installation
    if exist "C:\Program Files\MongoDB\Server\7.0\bin\mongod.exe" (
        echo Starting MongoDB from Program Files...
        start /B "" "C:\Program Files\MongoDB\Server\7.0\bin\mongod.exe"
        timeout /t 3 /nobreak
        echo %SUCCESS% MongoDB started
    ) else if exist "C:\Program Files\MongoDB\Server\6.0\bin\mongod.exe" (
        echo Starting MongoDB from Program Files...
        start /B "" "C:\Program Files\MongoDB\Server\6.0\bin\mongod.exe"
        timeout /t 3 /nobreak
        echo %SUCCESS% MongoDB started
    ) else (
        echo %ERROR% MongoDB not found in Program Files
        echo.
        echo SOLUTION: Install MongoDB Community Server from:
        echo https://www.mongodb.com/try/download/community
        echo.
        echo Or start MongoDB manually:
        echo 1. Open Command Prompt
        echo 2. Run: mongod.exe
        echo 3. Keep that window open
        echo 4. Run this script in a new Command Prompt window
        echo.
        timeout /t 2 /nobreak
    )
) else (
    echo %SUCCESS% MongoDB is running on port 27017
)

echo.
echo Step 4: Checking Database Connection...
echo =========================================
timeout /t 2 /nobreak
netstat -ano 2>nul | findstr :27017 >nul 2>&1
if errorlevel 1 (
    echo %ERROR% MongoDB still not accessible
    echo Please ensure MongoDB is running before proceeding.
    pause
) else (
    echo %SUCCESS% MongoDB is accessible
)

echo.
echo Step 5: Cleaning Previous Builds...
echo =========================================
if exist "target" (
    echo Removing old build artifacts...
    rmdir /s /q target >nul 2>&1
)
echo %SUCCESS% Clean completed

echo.
echo Step 6: Downloading Dependencies and Building...
echo =========================================
echo This may take several minutes on first run...
echo.
call mvn clean install -DskipTests
if errorlevel 1 (
    echo.
    echo %ERROR% Build FAILED
    echo.
    echo Troubleshooting:
    echo 1. Check internet connection
    echo 2. Try again - Maven may need to download dependencies
    echo 3. Check Maven settings at: %%USERPROFILE%%\.m2\settings.xml
    echo.
    pause
    exit /b 1
)
echo %SUCCESS% Build completed successfully

echo.
echo Step 7: Starting Application...
echo =========================================
echo.
echo *** APPLICATION STARTING ***
echo.
echo Access the application at: http://localhost:8080/login
echo.
echo Login Credentials:
echo   Email: omkarpatange77@gmail.com
echo   Password: Omkar@3483
echo.
echo To Stop: Press Ctrl+C
echo.
echo =========================================
echo.

REM Start the application
call mvn spring-boot:run

pause

