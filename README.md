# 🎓 Student Management System

A professional Spring Boot application for managing students with admin and student dashboards.

---

## 📋 Table of Contents

- [Quick Start](#-quick-start)
- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Setup Instructions](#-setup-instructions)
- [Running the Application](#-running-the-application)
- [Login Credentials](#-login-credentials)
- [Troubleshooting](#-troubleshooting)
- [Documentation](#-documentation)

---

## 🚀 Quick Start

### **Option 1: Automatic Setup (Easiest)**

Just run one of these startup scripts:

**Windows (Batch):**
```batch
Double-click: START.bat
```

**Windows (PowerShell):**
```powershell
Right-click START.ps1 > Run with PowerShell
```

The script will automatically:
- ✓ Check Java installation
- ✓ Check Maven installation
- ✓ Start MongoDB (if installed)
- ✓ Download dependencies
- ✓ Build the project
- ✓ Start the application

Then open: `http://localhost:8080/login`

---

### **Option 2: Manual Setup**

1. **Ensure you have installed:**
   - Java 17+: https://www.oracle.com/java/technologies/downloads/
   - Maven: https://maven.apache.org/download.cgi
   - MongoDB: https://www.mongodb.com/try/download/community

2. **Start MongoDB** (in a terminal):
   ```bash
   mongod.exe
   ```
   Keep this window open!

3. **Build and run the application** (in another terminal):
   ```bash
   cd c:\MyWokspace\Java\Student_management-System
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the application:**
   Open browser → `http://localhost:8080/login`

---

## ✨ Features

### **Admin Dashboard**
- 📊 View statistics (total students, active users, login records)
- 👥 Manage all students (view, add, edit, delete)
- 🔑 Change password
- 📋 Student database with search/filter

### **Student Dashboard**
- 👤 View personal profile
- ✏️ Update profile information
- 📚 View course and department details
- 🔐 Secure account management

### **Authentication**
- 🔐 Secure login/register system
- 🛡️ Spring Security integration
- 👨‍💼 Role-based access control (Admin/Student)
- 🔑 Password encryption

---

## 💻 Technology Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Java** | 17+ | Backend Language |
| **Spring Boot** | 3.1.0 | Framework |
| **Spring Security** | Latest | Authentication |
| **Thymeleaf** | Latest | Template Engine |
| **MongoDB** | Latest | Database |
| **Bootstrap** | 5.3.0 | UI Framework |
| **Maven** | 3.6+ | Build Tool |

---

## 📁 Project Structure

```
Student_management-System/
├── src/
│   ├── main/
│   │   ├── java/com/example/studentmanagement/
│   │   │   ├── StudentManagementApplication.java (Main)
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── PasswordConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── StudentController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   └── AppErrorController.java
│   │   │   ├── model/
│   │   │   │   ├── Student.java
│   │   │   │   └── User.java
│   │   │   ├── repository/
│   │   │   │   ├── StudentRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   └── service/
│   │   │       ├── StudentService.java
│   │   │       ├── UserService.java
│   │   │       └── CustomUserDetailsService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/css/
│   │       │   └── style.css (Professional styling)
│   │       └── templates/
│   │           ├── login.html
│   │           ├── register.html
│   │           ├── error.html
│   │           ├── admin/
│   │           │   ├── dashboard.html
│   │           │   ├── students.html
│   │           │   ├── add-student.html
│   │           │   └── edit-student.html
│   │           └── student/
│   │               ├── dashboard.html
│   │               └── profile.html
│   └── test/
├── pom.xml (Dependencies)
├── START.bat (Automatic startup - Windows Batch)
├── START.ps1 (Automatic startup - PowerShell)
├── README.md (This file)
├── QUICK_START.md (Quick start guide)
├── TROUBLESHOOTING.md (Detailed troubleshooting)
├── PROJECT_ORGANIZATION.md (Project details)
└── CHANGES_CHECKLIST.md (All changes made)
```

---

## ⚙️ Setup Instructions

### **Prerequisites**

1. **Java Development Kit (JDK) 17+**
   ```bash
   # Check installation
   java -version
   ```
   If not installed: https://www.oracle.com/java/technologies/downloads/

2. **Apache Maven 3.6+**
   ```bash
   # Check installation
   mvn --version
   ```
   If not installed: https://maven.apache.org/download.cgi

3. **MongoDB Community Server**
   ```bash
   # Install from
   https://www.mongodb.com/try/download/community
   ```

### **Configuration**

Edit `application.properties`:
```properties
# Database
spring.data.mongodb.uri=mongodb://localhost:27017/student_management

# Web
server.port=8080

# Thymeleaf
spring.thymeleaf.cache=false

# Security
spring.security.user.name=example@email.com
spring.security.user.password=YourPassword
spring.security.user.roles=ADMIN
```

### **Environment Variables (recommended)**

1. Copy `.env.example` to `.env` and set your real secrets.
2. Never commit `.env` or credentials files.

Example values:
```env
SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/student_management
SERVER_PORT=8080
SPRING_SECURITY_USER_NAME=admin@example.com
SPRING_SECURITY_USER_PASSWORD=YourSuperSecretPassword
SPRING_SECURITY_USER_ROLES=ADMIN
APP_JWT_SECRET=ChangeThisToAStrongRandomKey
SPRING_PROFILES_ACTIVE=dev
```

> `application.properties` supports `${ENV_VAR}` style placeholders:
> `spring.data.mongodb.uri=${SPRING_DATA_MONGODB_URI}`

---

## 🎯 Running the Application

### **Method 1: Using Startup Scripts (Recommended)**

**Windows (Batch):**
```bash
Double-click: START.bat
```

**Windows (PowerShell):**
```powershell
PowerShell -ExecutionPolicy Bypass -File "START.ps1"
```

### **Method 2: Command Line**

**Terminal 1: Start MongoDB**
```bash
mongod.exe
```

**Terminal 2: Build and Run**
```bash
cd c:\MyWokspace\Java\Student_management-System
mvn clean install
mvn spring-boot:run
```

### **Verify It's Running**

You should see:
```
Tomcat started on port(s): 8080 (http)
```

Then open: `http://localhost:8080/login`

---

## 🔐 Login Credentials

### **Default Admin Account**
```
Email: omkarpatange77@gmail.com
Password: Omkar@3483
```

### **Create New Account**
- Click "Don't have an account? Register"
- Fill in student details
- Login with your new credentials

---

## 📱 Available Pages

### **Admin Routes**
- `/login` - Login page
- `/admin/dashboard` - Admin dashboard
- `/admin/students` - Students list
- `/admin/add-student` - Add new student
- `/admin/edit-student/{id}` - Edit student
- `/admin/change-password` - Change password
- `/logout` - Logout

### **Student Routes**
- `/student/dashboard` - Student dashboard
- `/student/profile` - Student profile
- `/student/update-profile` - Update profile
- `/logout` - Logout

---

## 🎨 Design & UI

- **Professional Gradient**: Purple & Blue theme
- **Responsive Design**: Mobile, Tablet, Desktop
- **Modern Animations**: Smooth transitions & effects
- **Bootstrap 5**: Professional UI components
- **Font Awesome**: Beautiful icons throughout
- **Semantic HTML**: Accessible markup

---

## 🐛 Troubleshooting

### **Common Issues**

**"Site can't be reached"**
- Verify MongoDB is running
- Check if port 8080 is available
- Check console for error messages

**"MongoDB connection refused"**
- Start MongoDB: `mongod.exe`
- Ensure port 27017 is available

**"Port 8080 already in use"**
- Change port in `application.properties`
- Or kill the process using the port

See `TROUBLESHOOTING.md` for detailed solutions.

---

## 📚 Documentation

| File | Purpose |
|------|---------|
| `QUICK_START.md` | Quick start guide |
| `TROUBLESHOOTING.md` | Detailed troubleshooting |
| `PROJECT_ORGANIZATION.md` | Project structure & design |
| `CHANGES_CHECKLIST.md` | All improvements made |

---

## ✅ Status

**✅ COMPLETE & READY TO USE**

All pages organized, professionally styled, and fully functional!

---

**Happy Learning! 📚**

- Java 17 or higher
- Maven 3.6+
- MongoDB running on localhost:27017

## Installation and Setup

1. Clone or download the project.
2. Ensure MongoDB is installed and running.
3. Navigate to the project directory.
4. Run `mvn clean install` to build the project.
5. Run `mvn spring-boot:run` to start the application.

## Usage

- Access the application at `http://localhost:8080`
- **Admin Login**: Username: `admin`, Password: `admin123`
- Students can register at `/register` and login.

## Project Structure

- `src/main/java/com/example/studentmanagement/`
  - `model/`: Entity classes (User, Student)
  - `repository/`: MongoDB repositories
  - `service/`: Business logic services
  - `controller/`: Web controllers
  - `config/`: Configuration classes
- `src/main/resources/templates/`: Thymeleaf templates
- `src/main/resources/static/`: CSS and static files

## Default Admin Credentials

- Username: admin
- Password: admin123

You can change the admin password from the admin dashboard.

## Database

Data is stored in MongoDB collections:
- `users`: User accounts
- `students`: Student details

## Security

- Passwords are encrypted using BCrypt.
- Role-based access control (ADMIN, STUDENT).

## Contributing

This is a college project. Feel free to improve and extend."# Student-Management-System" 
