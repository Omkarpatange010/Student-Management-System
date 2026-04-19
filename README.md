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
- [Design & UI](#-design--ui)
- [Changelog](#-changelog)
- [Troubleshooting](#-troubleshooting)
- [Documentation](#-documentation)
- [Status](#-status)

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

**✨ New Features:** Fully mobile responsive design, enhanced security with CSRF protection

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
- 📱 **Fully Mobile Responsive** - Works perfectly on all devices

### **Student Dashboard**
- 👤 View personal profile
- ✏️ Update profile information
- 📚 View course and department details
- 🔐 Secure account management
- 📱 **Fully Mobile Responsive** - Optimized for mobile devices

### **Authentication**
- 🔐 Secure login/register system with CSRF protection
- 🛡️ Spring Security integration with proper token handling
- 👨‍💼 Role-based access control (Admin/Student)
- 🔑 Password encryption with BCrypt

### **Mobile Responsiveness**
- 📱 **25 Fully Responsive Templates** - All pages adapt to mobile, tablet, and desktop
- 🎨 **Consistent Dark Theme** - Professional cyan accent (#00d4ff) across all devices
- 📊 **Responsive Tables** - Horizontal scrolling and optimized layouts for mobile
- 🎯 **Touch-Friendly UI** - Optimized buttons and navigation for mobile devices
- 📐 **Flexible Grid System** - Bootstrap 5 responsive grid with custom breakpoints

---

## 🆕 Recent Updates (April 2026)

### **🔒 Security Enhancement**
- ✅ **Fixed 403 Forbidden Error** - Added CSRF tokens to login forms
- ✅ **Enhanced Security** - Proper CSRF protection for all POST requests
- ✅ **AuthController Updates** - Added `@ModelAttribute` for CSRF token injection

### **📱 Mobile Responsiveness Overhaul**
- ✅ **25 Templates Made Responsive** - All HTML templates now mobile-friendly
- ✅ **Responsive Breakpoints** - 768px (tablets) and 480px (phones) breakpoints
- ✅ **Dark Theme Consistency** - Unified cyan accent theme across all pages
- ✅ **Mobile-Optimized Forms** - Touch-friendly inputs and buttons
- ✅ **Responsive Navigation** - Collapsible menus and mobile navigation
- ✅ **Table Responsiveness** - Horizontal scrolling for data tables
- ✅ **Flexible Layouts** - Dashboard layouts adapt to screen size

### **🎨 UI/UX Improvements**
- ✅ **Professional Styling** - Consistent gradient backgrounds and effects
- ✅ **Font Awesome Icons** - Enhanced visual elements throughout
- ✅ **Bootstrap 5 Integration** - Modern responsive components
- ✅ **Smooth Animations** - Hover effects and transitions
- ✅ **Accessibility** - Semantic HTML and proper contrast ratios

---

## 💻 Technology Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Java** | 17+ | Backend Language |
| **Spring Boot** | 3.1.0 | Framework |
| **Spring Security** | Latest | Authentication & CSRF Protection |
| **Thymeleaf** | Latest | Template Engine |
| **MongoDB** | Latest | Database |
| **Bootstrap** | 5.3.0 | Responsive UI Framework |
| **Font Awesome** | 6.0.0 | Icons & Visual Elements |
| **Maven** | 3.6+ | Build Tool |
| **CSS3** | Latest | Custom Responsive Styling |
| **HTML5** | Latest | Semantic Markup |

### **Key Libraries & Features**
- 🔐 **CSRF Protection** - Spring Security with token validation
- 📱 **Mobile-First Design** - Responsive CSS with media queries
- 🎨 **Dark Theme** - Custom CSS with cyan accent (#00d4ff)
- 📊 **Bootstrap Components** - Cards, tables, forms, navigation
- 🔄 **Thymeleaf Integration** - Server-side templating
- 🛡️ **BCrypt Encryption** - Secure password hashing

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
│   │           ├── login.html *(responsive)*
│   │           ├── register.html *(responsive)*
│   │           ├── error.html *(responsive)*
│   │           ├── admin/ *(11 responsive templates)*
│   │           │   ├── dashboard.html
│   │           │   ├── students.html
│   │           │   ├── add-student.html
│   │           │   └── edit-student.html
│   │           └── student/ *(10 responsive templates)*
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

- **🌙 Professional Dark Theme**: Purple & Blue gradients with cyan accent (#00d4ff)
- **📱 Fully Responsive Design**: Mobile, Tablet, Desktop optimized
- **🎭 Modern Animations**: Smooth transitions & hover effects
- **📐 Bootstrap 5 Grid**: Professional responsive layout system
- **🎨 Font Awesome Icons**: Beautiful icons throughout the application
- **♿ Semantic HTML**: Accessible markup with proper ARIA labels
- **📊 Responsive Tables**: Horizontal scrolling and mobile-optimized data display
- **🎯 Touch-Friendly UI**: Optimized buttons and navigation for mobile devices
- **🔄 Consistent Styling**: Unified theme across all 25 templates

### **Mobile Responsiveness Features**
- ✅ **25 Responsive Templates** - All pages adapt to screen size
- ✅ **Flexible Breakpoints** - 768px (tablets) and 480px (phones)
- ✅ **Mobile Navigation** - Collapsible menus and touch-friendly controls
- ✅ **Responsive Forms** - Optimized input fields and buttons for mobile
- ✅ **Adaptive Layouts** - Dashboard layouts that stack on mobile devices

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

## � Changelog

### **Version 2.0.0 - April 2026**
#### **🔒 Security Fixes**
- Fixed 403 Forbidden error on `/login` URL
- Added CSRF tokens to all login forms (student and admin)
- Enhanced `AuthController.java` with `@ModelAttribute` for CSRF token injection
- Improved Spring Security configuration

#### **📱 Mobile Responsiveness Overhaul**
- Made all **25 HTML templates** fully responsive
- Added responsive CSS media queries (768px and 480px breakpoints)
- Implemented consistent dark theme with cyan accent (#00d4ff)
- Optimized navigation for mobile devices
- Added horizontal scrolling for data tables
- Made forms touch-friendly for mobile users

#### **🎨 UI/UX Enhancements**
- Unified dark gradient theme across all pages
- Enhanced Bootstrap 5 integration
- Improved Font Awesome icon usage
- Added smooth hover animations and transitions
- Optimized typography and spacing for mobile
- Improved accessibility with semantic HTML

#### **📋 Templates Updated (25 total)**
**Authentication:** `login.html`, `register.html`, `pending.html`, `error.html`
**Student Pages:** `dashboard.html`, `profile.html`, `attendance.html`, `marks.html`, `subjects.html`, `id-card.html`, `exam-pending-approval.html`, `exam-status.html`, `payment-gateway.html`, `payment-success.html`
**Admin Pages:** `dashboard.html`, `students.html`, `add-student.html`, `edit-student.html`, `add-subject.html`, `subjects.html`, `attendance.html`, `marks.html`, `change-password.html`, `exam-registrations.html`, `pending-registrations.html`

#### **🛠️ Technical Improvements**
- Updated CSS with responsive design patterns
- Enhanced template organization
- Improved build process validation
- Added comprehensive documentation

---

## �📚 Documentation

| File | Purpose |
|------|---------|
| `QUICK_START.md` | Quick start guide |
| `TROUBLESHOOTING.md` | Detailed troubleshooting |
| `PROJECT_ORGANIZATION.md` | Project structure & design |
| `CHANGES_CHECKLIST.md` | All improvements made |

---

## ✅ Status

**✅ COMPLETE & PRODUCTION READY (April 2026)**

### **Latest Features:**
- 🔒 **Security Enhanced** - CSRF protection implemented
- 📱 **Fully Mobile Responsive** - All 25 templates optimized for mobile
- 🎨 **Professional UI** - Consistent dark theme with cyan accents
- 🛡️ **Secure Authentication** - Proper token handling and validation

### **Quality Assurance:**
- ✅ **Build Status**: All Maven builds successful
- ✅ **Security**: CSRF tokens properly implemented
- ✅ **Responsiveness**: Tested on mobile, tablet, and desktop
- ✅ **Cross-browser**: Compatible with modern browsers
- ✅ **Performance**: Optimized CSS and efficient loading

**Ready for deployment and production use! 🚀**

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
