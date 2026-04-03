# Site Can't Be Reached - Troubleshooting Guide

## ❌ Common Issues & Solutions

### **Issue 1: MongoDB Not Running**
Your application requires MongoDB at `localhost:27017`

#### ✅ **Solution:**

**Windows - Using MongoDB Community Server:**

1. **Download & Install MongoDB**
   - Visit: https://www.mongodb.com/try/download/community
   - Download for Windows
   - Run installer and follow setup

2. **Start MongoDB Service**
   ```bash
   # If you installed as a service, it should auto-start
   # Check Services: Press Win + R, type "services.msc"
   # Look for "MongoDB Server"
   ```

3. **Or Start Manually (if not using service)**
   ```bash
   # Navigate to MongoDB bin directory (typically)
   cd "C:\Program Files\MongoDB\Server\7.0\bin"
   
   # Start mongod
   mongod.exe
   ```

4. **Verify MongoDB is Running**
   ```bash
   # In a new terminal, connect to MongoDB
   cd "C:\Program Files\MongoDB\Server\7.0\bin"
   mongo.exe
   
   # Or if you have mongosh (newer versions)
   mongosh.exe
   ```
   You should see a MongoDB shell prompt.

---

### **Issue 2: Port Already in Use**
If port 27017 is already in use for MongoDB or 8080 for the app

#### ✅ **Solution A: Find Process Using Port**
```bash
# Check port 27017 (MongoDB)
netstat -ano | findstr :27017

# Check port 8080 (Application)
netstat -ano | findstr :8080

# Kill process (replace PID with the number from above)
taskkill /PID <PID> /F
```

#### ✅ **Solution B: Change Application Port**
Edit `application.properties`:
```properties
server.port=8081
```

---

### **Issue 3: Java Not Installed or Not in PATH**

#### ✅ **Solution:**

1. **Check Java Installation**
   ```bash
   java -version
   javac -version
   ```

2. **If Not Installed:**
   - Download JDK 17 from: https://www.oracle.com/java/technologies/downloads/
   - Install and add to PATH

3. **Verify Installation**
   ```bash
   java -version
   javac -version
   ```

---

### **Issue 4: Maven Not Found**

#### ✅ **Solution:**

1. **Check Maven**
   ```bash
   mvn --version
   ```

2. **If Not Installed:**
   - Download from: https://maven.apache.org/download.cgi
   - Extract to a folder
   - Add to PATH

3. **Verify**
   ```bash
   mvn --version
   ```

---

## 🚀 **STEP-BY-STEP: How to Run Your Application**

### **Step 1: Start MongoDB**
```bash
# Terminal 1: Start MongoDB
mongod.exe
# You should see: "message":"Waiting for connections"
```

### **Step 2: Navigate to Project**
```bash
# Terminal 2: Go to project directory
cd c:\MyWokspace\Java\Student_management-System
```

### **Step 3: Build the Application**
```bash
# Build with Maven
mvn clean install
```

### **Step 4: Run the Application**
```bash
# Run the Spring Boot application
mvn spring-boot:run

# OR use Java directly after building
java -jar target/student-management-system-0.0.1-SNAPSHOT.jar
```

### **Step 5: Access the Application**
Open your browser and go to:
```
http://localhost:8080/login
```

---

## ✅ **Successful Startup Signs**

When the application starts successfully, you should see:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_|\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.1.0)

2024-04-03 10:30:00.000  INFO 12345 --- [main] c.e.s.StudentManagementApplication : Started StudentManagementApplication
2024-04-03 10:30:00.100  INFO 12345 --- [main] o.s.b.w.e.t.TomcatWebServer : Tomcat started on port(s): 8080
```

---

## 🔐 **Login Credentials**

Once the application is running:

**URL:** `http://localhost:8080/login`

**Default Admin Account:**
- **Username:** `omkarpatange77@gmail.com`
- **Password:** `Omkar@3483`

Or register a new student account.

---

## 🛠️ **Troubleshooting Checklist**

- [ ] MongoDB is installed
- [ ] MongoDB is running (`mongod.exe` is executing)
- [ ] Java 17+ is installed
- [ ] Maven is installed
- [ ] Port 8080 is available
- [ ] Port 27017 (MongoDB) is available
- [ ] `mvn clean install` completed successfully
- [ ] Application started without errors
- [ ] Can access `http://localhost:8080/login`

---

## 🆘 **If Still Having Issues**

### **1. Check Application Logs**
```bash
# When running: mvn spring-boot:run
# Look for any error messages in the console
# Common errors:
# - "Unable to connect to MongoDB"
# - "Connection refused"
# - "Port already in use"
```

### **2. Try Alternative: Docker**
If MongoDB installation is problematic, use Docker:
```bash
# Install Docker from https://www.docker.com/products/docker-desktop

# Run MongoDB in Docker
docker run -d -p 27017:27017 --name mongodb mongo:latest

# Now run your application
mvn spring-boot:run
```

### **3. Update application.properties**
If MongoDB is on a different machine:
```properties
spring.data.mongodb.uri=mongodb://YOUR_IP:27017/student_management
```

### **4. Check Firewall**
Make sure your firewall isn't blocking ports 8080 or 27017.

---

## 📋 **Quick Reference**

| Issue | Solution |
|-------|----------|
| MongoDB connection failed | Start `mongod.exe` in terminal |
| Port 8080 in use | Change `server.port` in properties |
| Java not found | Install JDK 17 and add to PATH |
| Maven not found | Install Maven and add to PATH |
| Can't connect to app | Verify all services running and logs |

---

## ✅ **Success!**

Once you can access `http://localhost:8080/login`, your site is up and running!

All pages should display with the professional styling we just set up.

---

**Need More Help?**
1. Check the application console for error messages
2. Verify MongoDB is running: `mongosh` should connect
3. Check if ports are in use: `netstat -ano | findstr :PORT`

Good luck! 🎓
