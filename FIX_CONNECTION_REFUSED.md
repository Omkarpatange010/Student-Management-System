# 🚨 ERR_CONNECTION_REFUSED - COMPLETE FIX GUIDE

## The Problem
`ERR_CONNECTION_REFUSED` means the application crashed or never started on port 8080.

---

## ✅ IMMEDIATE FIX (Follow Step by Step)

### **Step 1: Open PowerShell (As Administrator)**
1. Press `Win` key
2. Type: `PowerShell`
3. Right-click → **Run as Administrator**

---

### **Step 2: Check If MongoDB Is Running**

```powershell
netstat -ano | findstr :27017
```

**Expected Output:**
```
TCP    127.0.0.1:27017        0.0.0.0:0              LISTENING       1234
```

**If you see nothing:** ❌ MongoDB is NOT running

**FIX: Start MongoDB in new PowerShell window:**
```powershell
cd "C:\Program Files\MongoDB\Server\7.0\bin"
.\mongod.exe
```

Wait 3-5 seconds until you see: `"Waiting for connections"`

Keep this window open!

---

### **Step 3: Navigate to Project**

```powershell
cd c:\MyWokspace\Java\Student_management-System
```

---

### **Step 4: Clean Previous Builds**

```powershell
# Remove old build files
rmdir target -r -force -ea 0
```

---

### **Step 5: Full Rebuild (Latest Dependencies)**

```powershell
mvn clean install -DskipTests
```

**Watch for:** `BUILD SUCCESS`

If you see `BUILD FAILURE`, scroll up to find the error. Common issues:
- No internet connection
- Maven repository corrupt
- Java version mismatch

**If BUILD FAILURE occurs:**
```powershell
# Clear Maven cache completely
rmdir $env:USERPROFILE\.m2\repository -r -force -ea 0

# Try again
mvn clean install -DskipTests
```

---

### **Step 6: Start the Application**

```powershell
mvn spring-boot:run
```

**Watch the output carefully for:**

✅ **Good Signs (Look for these):**
```
2026-04-03 10:30:00 INFO Started StudentManagementApplication
2026-04-03 10:30:00 INFO o.s.b.w.e.t.TomcatWebServer : Tomcat started on port(s): 8080 (http)
```

❌ **Bad Signs (If you see these):**
```
ERROR - Could not connect to MongoDB
ERROR - Connection refused
ERROR - Address already in use
```

---

### **Step 7: Verify It's Running**

Open **NEW PowerShell window** and run:

```powershell
netstat -ano | findstr :8080
```

**Expected Output:**
```
TCP    127.0.0.1:8080         0.0.0.0:0              LISTENING       5678
```

If you see this → Application is running ✓

---

### **Step 8: Open Browser**

```
http://localhost:8080/login
```

**Should see:** Login page with "Welcome Back" header

---

## 🔧 TROUBLESHOOTING BY ERROR MESSAGE

### **Error: "Could not connect to MongoDB"**

```
ERROR - com.mongodb.MongoSocketOpenException: 
  Exception opening socket to host
```

**SOLUTION:**
1. Ensure MongoDB is running: `netstat -ano | findstr :27017`
2. If not running, start it: `mongod.exe`
3. Wait 3-5 seconds for MongoDB to fully start
4. Try running application again

---

### **Error: "Address already in use"**

```
ERROR - java.net.BindException: Address already in use
```

**SOLUTION A: Kill port 8080**
```powershell
# Find what's using port 8080
netstat -ano | findstr :8080

# Kill it (replace XXXXX with the PID number)
taskkill /PID XXXXX /F
```

**SOLUTION B: Use different port**

Edit: `application.properties`

```properties
server.port=8081
```

Then access: `http://localhost:8081/login`

---

### **Error: "Could not resolve placeholder"**

This means configuration issue.

**SOLUTION:**
```powershell
# Rebuild with fresh configuration
mvn clean install -DskipTests
mvn spring-boot:run
```

---

### **Error: "Failed to load ApplicationContext"**

```
ERROR - java.lang.IllegalStateException: 
  Failed to load ApplicationContext
```

This is usually MongoDB or Spring configuration issue.

**SOLUTION:**
1. Check MongoDB is running: `netstat -ano | findstr :27017`
2. Check `application.properties` is correct:
   - Should have: `spring.data.mongodb.uri=mongodb://localhost:27017/student_management`
3. Clean and rebuild:
   ```powershell
   mvn clean install -DskipTests
   mvn spring-boot:run
   ```

---

## 🧪 COMPLETE DIAGNOSTIC SCRIPT

Copy and run this in PowerShell:

```powershell
# 1. Check MongoDB
Write-Host "`n=== CHECKING SERVICES ===" -ForegroundColor Cyan
Write-Host "`n1. MongoDB (port 27017):" -ForegroundColor Yellow
if (netstat -ano | findstr :27017) {
    Write-Host "[OK] Running" -ForegroundColor Green
} else {
    Write-Host "[FAIL] NOT running" -ForegroundColor Red
    Write-Host "Start with: mongod.exe" -ForegroundColor Yellow
}

# 2. Check if port 8080 is available
Write-Host "`n2. Port 8080 availability:" -ForegroundColor Yellow
if (netstat -ano | findstr :8080) {
    Write-Host "[FAIL] Port 8080 already in use!" -ForegroundColor Red
    netstat -ano | findstr :8080 | ForEach-Object {
        $fields = $_ -split "\s+"
        $pid = $fields[-1]
        Write-Host "Process ID: $pid" -ForegroundColor Red
        Write-Host "Command: taskkill /PID $pid /F" -ForegroundColor Yellow
    }
} else {
    Write-Host "[OK] Port 8080 is available" -ForegroundColor Green
}

# 3. Check Java
Write-Host "`n3. Java:" -ForegroundColor Yellow
if ((java -version 2>&1) -match "version") {
    Write-Host "[OK] Installed" -ForegroundColor Green
    java -version 2>&1 | select -first 1
} else {
    Write-Host "[FAIL] NOT installed" -ForegroundColor Red
}

# 4. Check Maven
Write-Host "`n4. Maven:" -ForegroundColor Yellow
if ((mvn --version 2>&1) -match "Apache Maven") {
    Write-Host "[OK] Installed" -ForegroundColor Green
    mvn --version | select -first 1
} else {
    Write-Host "[FAIL] NOT installed" -ForegroundColor Red
}

Write-Host "`n================================" -ForegroundColor Cyan
```

---

## 🎯 THE COMPLETE 5-MINUTE FIX

Do this EXACTLY in order:

### **Terminal 1: Start MongoDB**
```powershell
cd "C:\Program Files\MongoDB\Server\7.0\bin"
.\mongod.exe
# Wait for: "Waiting for connections"
# KEEP OPEN
```

### **Terminal 2: Rebuild & Run**
```powershell
cd c:\MyWokspace\Java\Student_management-System
rmdir target -r -force -ea 0
mvn clean install -DskipTests
mvn spring-boot:run
# Wait for: "Tomcat started on port(s): 8080"
```

### **Browser: Access Application**
```
http://localhost:8080/login
```

---

## ✋ CHECK THESE BEFORE ASKING FOR HELP

1. **Is MongoDB running?**
   ```powershell
   netstat -ano | findstr :27017
   ```
   Should show a line with LISTENING

2. **Are there any errors in Terminal 2?**
   - Copy the error message
   - Scroll up to see full error

3. **Can you reach MongoDB?**
   ```powershell
   # Try connecting directly
   cd "C:\Program Files\MongoDB\Server\7.0\bin"
   .\mongosh.exe
   ```
   Should connect to MongoDB shell

---

## 📋 TELL ME THIS INFORMATION

If still not working, provide:

1. What does this show?
   ```powershell
   netstat -ano | findstr :8080
   ```

2. What does this show?
   ```powershell
   netstat -ano | findstr :27017
   ```

3. Any errors in the terminal when running `mvn spring-boot:run`?
   (Copy the full error message)

4. What's in `application.properties`?

---

**Follow the 5-minute fix above. It should work! 🚀**

If you get stuck, run the diagnostic script and tell me the output.
