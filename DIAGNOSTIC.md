# 🔍 Diagnostic Guide - "Site Can't Be Reached"

## ⚡ Quick Diagnosis Checklist

Follow these steps to identify the problem:

---

## **Step 1: Is MongoDB Running?** 🗄️

### Check MongoDB Status:
```powershell
# In PowerShell, check if MongoDB is listening on port 27017
netstat -ano | findstr :27017
```

**If you see a line with `:27017` → MongoDB is running ✓**

**If nothing appears → MongoDB is NOT running ✗**

### ✅ Solution: Start MongoDB
```powershell
# Find and start MongoDB
cd "C:\Program Files\MongoDB\Server\7.0\bin"
.\mongod.exe
```

**You should see:** `"Waiting for connections"`

Keep this window open and continue to Step 2.

---

## **Step 2: Is the Application Running?** 🚀

### Check Application Status:
```powershell
# Check if port 8080 is listening
netstat -ano | findstr :8080
```

**If you see a line with `:8080` → Application is running ✓**

**If nothing appears → Application is NOT running ✗**

### ✅ Solution: Start the Application

**Option A: Use START.bat**
```batch
Double-click: START.bat
```

**Option B: Manual Start**
```powershell
cd c:\MyWokspace\Java\Student_management-System
mvn spring-boot:run
```

---

## **Step 3: Check Application Logs** 📋

If the application is running but site still can't be reached, check for errors:

**Look for these messages in the console:**

✅ **Good Sign:**
```
Tomcat started on port(s): 8080 (http)
Started StudentManagementApplication
```

❌ **Bad Signs (Look For These):**
```
ERROR - Failed to connect to MongoDB
ERROR - Connection refused
ERROR - Port 8080 already in use
```

---

## **Step 4: Port Already in Use?** 🔌

### If port 8080 is already used:

**Find what's using port 8080:**
```powershell
netstat -ano | findstr :8080
```

**Kill the process** (replace XXXX with PID from above):
```powershell
taskkill /PID XXXX /F
```

**Or change application port:**

Edit `application.properties`:
```properties
server.port=8081
```

Then access: `http://localhost:8081/login`

---

## **Step 5: Try This Complete Flow** 📝

Follow exactly in order:

### **Terminal 1: Start MongoDB**
```powershell
cd "C:\Program Files\MongoDB\Server\7.0\bin"
.\mongod.exe
# Wait until you see: "Waiting for connections"
# KEEP THIS TERMINAL OPEN
```

### **Terminal 2: Build & Run Application**
```powershell
cd c:\MyWokspace\Java\Student_management-System

# Clean build
mvn clean

# Build project
mvn install

# Run application
mvn spring-boot:run
```

### **Wait For:**
```
Tomcat started on port(s): 8080 (http)
```

### **Terminal 3: Test URL**
```powershell
# Test if application is responding
Invoke-WebRequest http://localhost:8080/login
```

If successful, you'll see: `StatusCode : 200`

---

## **COMMON ISSUES & SOLUTIONS** 🔧

### **Issue #1: "MongoDB connection refused"**
```
❌ java.net.ConnectException: Connection refused
```
**Solution:**
- Start MongoDB first! (Terminal 1)
- Verify MongoDB is running: `netstat -ano | findstr :27017`
- Wait 3-5 seconds for MongoDB to fully start

---

### **Issue #2: "Address already in use"**
```
❌ Address already in use: bind
```
**Solution A: Kill existing process**
```powershell
# Kill Spring Boot on 8080
netstat -ano | findstr :8080
taskkill /PID XXXX /F
```

**Solution B: Use different port**
Edit `application.properties`:
```properties
server.port=8081
```
Access: `http://localhost:8081/login`

---

### **Issue #3: "Build failed" or dependencies**
```powershell
# Clear Maven cache and try again
rmdir %USERPROFILE%\.m2\repository -r
mvn clean install -DskipTests
```

---

### **Issue #4: "Application starts but won't connect"**

Check firewall:
1. Press `Win + R`
2. Type: `wf.msc`
3. Look for Java/Tomcat rules
4. Allow on Port 8080

---

## **STEP-BY-STEP DEBUG** 🔬

Run this exact sequence:

### **1. Check Java:**
```powershell
java -version
```
Should show Java 17+

### **2. Check Maven:**
```powershell
mvn --version
```
Should show Maven 3.6+

### **3. Check MongoDB:**
```powershell
netstat -ano | findstr :27017
```
Should see MongoDB listening

### **4. Build Project:**
```powershell
cd c:\MyWokspace\Java\Student_management-System
mvn clean install -DskipTests
```
Should show: `BUILD SUCCESS`

### **5. Run Application:**
```powershell
mvn spring-boot:run
```
Should show: `Tomcat started on port(s): 8080`

### **6. Test URL:**
```powershell
Invoke-WebRequest http://localhost:8080/login
```
Should show: `StatusCode : 200`

---

## **IF STILL NOT WORKING** ⚠️

Provide these details:

1. **What error message do you see?**
   - Take a screenshot of the console

2. **What's in the browser?**
   - Screenshot of the error page

3. **Check these:**
   ```powershell
   # Is MongoDB running?
   netstat -ano | findstr :27017
   
   # Is app running?
   netstat -ano | findstr :8080
   
   # Show me errors
   # Copy error messages from console
   ```

---

## **NUCLEAR OPTION - Reset Everything** 💣

If nothing works, try this:

```powershell
# Stop MongoDB (close its window)
# Stop application (Ctrl+C)

# Clear everything
cd c:\MyWokspace\Java\Student_management-System
rmdir target -r -force -ea 0
rmdir "%USERPROFILE%\.m2" -r -force -ea 0

# Start fresh
mvn clean install -DskipTests

# Then try again
mvn spring-boot:run
```

---

## **QUICK REFERENCE** 📋

| Problem | Command | Expected Result |
|---------|---------|-----------------|
| Test MongoDB | `netstat -ano \| findstr :27017` | See TCP line with 27017 |
| Test App | `netstat -ano \| findstr :8080` | See TCP line with 8080 |
| Check Java | `java -version` | Shows Java 17+ |
| Check Maven | `mvn --version` | Shows Maven 3.6+ |
| Test URL | `Invoke-WebRequest http://localhost:8080/login` | StatusCode 200 |
| Kill Port 8080 | `taskkill /PID XXXX /F` | Process killed |
| Clear Maven | `rmdir $env:USERPROFILE\.m2\repository -r` | Cache cleared |

---

**Now let's fix this! Tell me:**

1. ✋ Run this command and tell me the output:
   ```powershell
   netstat -ano | findstr :8080
   ```

2. ✋ Tell me what's in the browser when you try to access the site

3. ✋ Any error messages in the terminal?

**I'll help you fix it! 🚀**
