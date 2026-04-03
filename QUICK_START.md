# 🚀 Quick Start Guide - Automated Setup

## ✅ **EASIEST WAY - Using START.bat**

Your project now has an **automated startup script** that handles everything!

### **How to Use:**

1. **Open File Explorer**
   - Navigate to: `c:\MyWokspace\Java\Student_management-System`

2. **Double-click: `START.bat`**
   - The script will:
     ✓ Check Java installation
     ✓ Check Maven installation
     ✓ Find and start MongoDB (if installed)
     ✓ Download all dependencies
     ✓ Build your project
     ✓ Start the application

3. **Wait for the Message:**
   ```
   *** APPLICATION STARTING ***
   
   Access the application at: http://localhost:8080/login
   ```

4. **Open Browser**
   - Go to: `http://localhost:8080/login`

5. **Login**
   - Email: `omkarpatange77@gmail.com`
   - Password: `Omkar@3483`

---

## ⚙️ **What The Script Automatically Does**

### **Step 1: Java Check**
- Verifies Java 17+ is installed
- If missing, shows download link

### **Step 2: Maven Check**
- Verifies Maven is installed
- If missing, shows how to install

### **Step 3: MongoDB Check**
- Checks if MongoDB is running
- **Automatically starts MongoDB** if found installed
- If not installed, shows setup instructions

### **Step 4: Database Connection**
- Confirms MongoDB is accessible

### **Step 5: Clean Build**
- Removes old build files
- Clears the cache

### **Step 6: Full Build**
- Downloads all dependencies (first time may take 2-3 minutes)
- Compiles all code
- Skips tests for faster setup

### **Step 7: Run Application**
- Starts Spring Boot server
- Opens on: `http://localhost:8080`

---

## 🔧 **Pre-requisites (One-Time Setup)**

### **Option A: Automated (Recommended)**
The script will tell you exactly what's needed!

### **Option B: Manual Setup**

**1. Install Java 17+**
```
https://www.oracle.com/java/technologies/downloads/
```

**2. Install Maven**
```
https://maven.apache.org/download.cgi

After downloading:
1. Extract to C:\Maven
2. Add C:\Maven\bin to PATH
3. Restart Windows
```

**3. Install MongoDB**
```
https://www.mongodb.com/try/download/community

Windows installer will auto-setup
```

---

## 📋 **Troubleshooting**

### **Problem: "Java not found"**
```
Solution:
1. Download Java 17+ from Oracle
2. Install with default settings
3. Restart command prompt
4. Run START.bat again
```

### **Problem: "Maven not found"**
```
Solution:
1. Download Maven
2. Extract to C:\Maven
3. Add C:\Maven\bin to Windows PATH
4. Restart command prompt
5. Run START.bat again
```

### **Problem: "MongoDB not running"**
```
Solutions:
A) Install MongoDB Community Server
   - Download from mongodb.com
   - Use default installation
   - Script will auto-start it

B) Start manually:
   - Open Command Prompt
   - Run: mongod.exe
   - Keep that window open
   - Run START.bat in new window
```

### **Problem: "Port 8080 already in use"**
```
Solution:
1. Find what's using port 8080:
   netstat -ano | findstr :8080

2. Kill the process (replace XXXX with PID):
   taskkill /PID XXXX /F

3. Run START.bat again
```

### **Problem: "Build failed"**
```
Solutions:
1. Check internet connection
2. Delete C:\Users\YOUR_USER\.m2\repository
3. Run START.bat again (will re-download dependencies)
4. Check if antivirus is blocking Maven
```

---

## 📱 **Accessing the Application**

Once running, open your browser:

```
URL: http://localhost:8080/login

DEFAULT ADMIN:
  Email: omkarpatange77@gmail.com
  Password: Omkar@3483

OR REGISTER:
  Click: "Don't have an account? Register"
  Create your student account
```

---

## 🎓 **Pages Available**

### **Admin Dashboard**
- Statistics & overview
- Manage students
- Add new students
- Edit student records
- Delete students

### **Student Dashboard**
- View your profile
- Edit profile
- Update personal information

---

## ⏹️ **To Stop the Application**

Press: **`Ctrl + C`** in the command prompt window

---

## 🔄 **To Run Again**

Simply double-click **`START.bat`** again!

---

## 💡 **Pro Tips**

1. **First Run Takes Longer**: Maven downloads 500MB+ of dependencies. Subsequent runs are much faster.

2. **Keep MongoDB Running**: MongoDB needs to keep running for the app to work. The script auto-starts it.

3. **Browser Issues**: 
   - Try incognito mode if cache is a problem
   - Clear browser cookies
   - Try a different browser

4. **Port Already in Use**:
   - Edit `application.properties`
   - Change `server.port=8080` to `server.port=8081`

5. **Database Issues**:
   - MongoDB data is stored in `C:\data\db`
   - Make sure this folder exists and is writable

---

## 📞 **Still Having Issues?**

Check these files for detailed help:
- `TROUBLESHOOTING.md` - Detailed troubleshooting guide
- `PROJECT_ORGANIZATION.md` - Project structure
- `CHANGES_CHECKLIST.md` - All changes made

---

**That's it! Your application should now be running! 🎉**

Questions? Check the TROUBLESHOOTING.md file or review the START.bat output.
