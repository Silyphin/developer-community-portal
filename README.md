# 🌟 Developer Community Portal
A comprehensive Spring Boot web application that serves as a professional networking platform for software developers. Built for connecting developers, sharing knowledge, and discovering career opportunities within the tech community. For school Project

## 🚀 Key Features

### 👥 **User Management System**
- **User Registration & Authentication**: Secure account creation with email verification
- **Comprehensive User Profiles**: Professional information including skills, experience, and bio
- **Privacy Controls**: Public/private profile visibility settings
- **Role-Based Access Control**: Separate admin and user permissions

### 🔍 **Developer Networking**
- **Advanced Search**: Find developers by name, company, location, skills, or experience
- **Profile Discovery**: Browse and connect with other developers
- **Public Profile Viewing**: View detailed developer profiles and professional information
- **Professional Networking**: Connect based on expertise, location, and interests

### 🛠️ **Admin Dashboard**
- **User Management**: Complete CRUD operations for all user accounts
- **Bulk Email System**: Send personalized invitations and announcements to multiple users
- **Community Analytics**: Monitor user registrations, growth, and engagement
- **Content Administration**: Manage user-generated content and profiles

### 💻 **Technical Highlights**
- **Responsive Design**: Mobile-first approach using Bootstrap framework
- **Email Integration**: Gmail SMTP integration with mail personalization
- **Rich Text Editor**: TinyMCE integration for enhanced content creation
- **Form Validation**: Comprehensive client-side and server-side validation
- **Session Security**: Secure HTTP session-based authentication
- **Database Management**: Efficient MySQL integration with JPA/Hibernate

## 🏗️ Technology Stack

### **Backend Technologies**
- **Java 17+**: Modern Java development with latest features
- **Spring Boot 3.2+**: Rapid application development framework
- **Spring Data JPA**: Simplified data access and persistence
- **Spring Web MVC**: Robust web layer implementation
- **Hibernate ORM**: Object-relational mapping for database operations
- **MySQL 8.0+**: Reliable relational database management
- **JavaMailSender**: Built-in email functionality

### **Frontend Technologies**
- **Thymeleaf**: Server-side Java templating engine
- **Bootstrap 4.5**: Responsive CSS framework
- **JavaScript & jQuery**: Dynamic client-side functionality
- **TinyMCE Editor**: WYSIWYG rich text editing
- **Custom CSS**: Application-specific styling and themes

### **Development Tools**
- **Maven**: Dependency management and build automation
- **IntelliJ IDEA**: Recommended IDE for development
- **Git**: Version control and collaboration

## 📋 Prerequisites

Before running this application, ensure you have:

- **Java Development Kit (JDK) 17 or higher**
- **Apache Maven 3.6+**
- **MySQL Server 8.0+**
- **Gmail account** (for email functionality)
- **Git** (for version control)
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)

## 🛠️ Installation & Setup Guide

### **Step 1: Clone the Repository**
```bash
git clone https://github.com/Silyphin/developer-community-portal.git
cd developer-community-portal
```

### **Step 2: Database Configuration**

#### **Create MySQL Database:**
```sql
-- Log into MySQL as root
mysql -u root -p

-- Create the application database
CREATE DATABASE DeveloperCommunityDB;

-- Optional: Create a dedicated user for the application
CREATE USER 'devuser'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON DeveloperCommunityDB.* TO 'devuser'@'localhost';
FLUSH PRIVILEGES;

-- Exit MySQL
EXIT;
```

#### **Verify Database Connection:**
```bash
# Test connection to your database
mysql -u root -p DeveloperCommunityDB
```

### **Step 3: Application Configuration**

#### **Copy Configuration Template:**
```bash
# Copy the example configuration file
cp application-example.properties application.properties
```

#### **Update Database Settings:**
Edit `application.properties` and update the database section:
```properties
# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/DeveloperCommunityDB?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### **Step 4: Gmail SMTP Setup (Email Functionality)**

The application uses Gmail SMTP for sending emails. Follow these steps to set up email functionality:

#### **4.1: Enable 2-Factor Authentication**
1. **Go to your Gmail account**: [gmail.com](https://gmail.com)
2. **Access Google Account settings**: [myaccount.google.com](https://myaccount.google.com)
3. **Navigate to Security**: Click on "Security" in the left sidebar
4. **Enable 2-Step Verification**: 
   - Click "2-Step Verification"
   - Follow the setup process (requires phone number)
   - Complete the verification setup

#### **4.2: Generate App Password**
1. **Return to Security settings**: [myaccount.google.com/security](https://myaccount.google.com/security)
2. **Find App passwords**: Under "2-Step Verification" section
3. **Create new App password**:
   - Click "App passwords"
   - Select app: "Mail"
   - Select device: "Other (custom name)"
   - Enter name: "Developer Community Portal"
   - Click "Generate"
4. **Copy the 16-character password**: Save this securely (format: `abcd efgh ijkl mnop`)

#### **4.3: Configure Email Settings**
Update the email section in your `application.properties`:
```properties
# Gmail SMTP configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-16-character-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.ssl.trust=smtp.gmail.com
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000

# Application-specific email settings
app.mail.from=your-email@gmail.com
```

**Important Notes:**
- **Use the same Gmail address** for both `spring.mail.username` and `app.mail.from`
- **Use the 16-character App Password**, not your regular Gmail password
- **Keep your App Password secure** and never share it publicly

#### **4.4: Test Email Configuration**
After setup, you can test the email functionality:
1. **Start your application**
2. **Login as admin**
3. **Navigate to Admin → Send Bulk Email**
4. **Send a test email to yourself**

### **Step 5: Build and Run**

#### **Build the Project:**
```bash
# Clean and install dependencies
mvn clean install
```

#### **Run the Application:**
```bash
# Start the Spring Boot application
mvn spring-boot:run
```

#### **Alternative: Run from IDE**
- **IntelliJ IDEA**: Right-click `Main.java` → Run
- **Eclipse**: Right-click project → Run As → Spring Boot App

### **Step 6: Access the Application**

Once the application starts successfully:

- **Application URL**: `http://localhost:8080`
- **Admin Login**: Create an admin account or use the registration system
- **Database Tables**: Automatically created by Hibernate DDL

**Success Indicators:**
- Console shows: `Tomcat started on port(s): 8080`
- No error messages in the startup logs
- Application accessible in web browser

## 📁 Project Structure

```
developer-community-portal/
├── src/
│   ├── main/
│   │   ├── java/org/example/DSE105_Assignment_2/
│   │   │   ├── Controller/              # Web controllers (MVC)
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── SearchController.java
│   │   │   │   └── UserController.java
│   │   │   ├── Model/                   # Entity classes and forms
│   │   │   │   ├── User.java
│   │   │   │   ├── LoginForm.java
│   │   │   │   ├── RegistrationForm.java
│   │   │   │   ├── SearchForm.java
│   │   │   │   └── PasswordResetForm.java
│   │   │   ├── Repository/              # Data access layer
│   │   │   │   └── UserRepository.java
│   │   │   ├── Service/                 # Business logic layer
│   │   │   │   └── EmailService.java
│   │   │   └── Main.java               # Application entry point
│   │   └── resources/
│   │       ├── static/                  # Static web assets
│   │       │   ├── css/                # Custom stylesheets
│   │       │   └── js/                 # JavaScript files
│   │       ├── templates/              # Thymeleaf templates
│   │       │   ├── admin/              # Admin panel templates
│   │       │   ├── fragments/          # Reusable template fragments
│   │       │   └── *.html             # Main application templates
│   │       └── application-example.properties
├── target/                             # Maven build output
├── pom.xml                            # Maven configuration
├── .gitignore                         # Git ignore rules
├── README.md                          # This documentation
└── LICENSE                            # MIT license
```

## 🎯 Usage Guide

### **For Regular Users**

#### **Getting Started:**
1. **Register Account**: Visit `/register` to create your developer profile
2. **Complete Profile**: Add professional information, skills, and experience
3. **Set Privacy**: Choose public or private profile visibility
4. **Search Developers**: Use advanced search to find other developers
5. **View Profiles**: Browse public profiles and connect with peers

#### **Profile Management:**
- **Update Information**: Keep your skills and experience current
- **Privacy Settings**: Control who can see your profile
- **Professional Details**: Showcase your expertise and projects

### **For Administrators**

#### **Admin Access:**
1. **Login**: Use admin credentials to access admin panel
2. **Dashboard**: Monitor user statistics and community growth
3. **User Management**: View, edit, or remove user accounts
4. **Bulk Communications**: Send announcements to user groups

#### **Admin Features:**
- **User Statistics**: Track registrations and engagement
- **Content Moderation**: Manage user-generated content
- **Email Campaigns**: Send personalized bulk emails
- **Account Management**: Full CRUD operations on user accounts

## 🔐 Security Features

### **Authentication & Authorization**
- **Session-based Authentication**: Secure HTTP session management
- **Role-based Access Control**: Separate user and admin permissions
- **Password Security**: Encrypted password storage (BCrypt recommended)
- **Login Protection**: Session timeout and security measures

### **Data Protection**
- **Input Validation**: Comprehensive form validation
- **SQL Injection Prevention**: JPA/Hibernate query protection
- **XSS Protection**: Template engine security measures
- **Privacy Controls**: User-controlled profile visibility

### **Email Security**
- **App Password Usage**: Secure Gmail integration
- **Environment Variables**: Sensitive data protection
- **Rate Limiting**: Email sending throttling

## 🚀 Deployment Options

### **Local Development**
```bash
# Development mode with hot reload
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### **Production Deployment**

#### **JAR Deployment:**
```bash
# Build production JAR
mvn clean package -DskipTests

# Run production JAR
java -jar target/developer-community-portal.jar
```

#### **Environment Variables (Production):**
```bash
# Set production environment variables
export DB_HOST=your-production-db-host
export DB_PASSWORD=your-production-db-password
export EMAIL_USERNAME=your-production-email@domain.com
export EMAIL_PASSWORD=your-production-app-password
```

#### **Docker Deployment (Optional):**
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/developer-community-portal.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🔧 Configuration Options

### **Database Configuration**
- **Connection Pool**: HikariCP for connection management
- **DDL Auto**: Automatic table creation and updates
- **SQL Logging**: Debug SQL queries in development

### **Email Configuration**
- **SMTP Settings**: Configurable email server settings
- **Email Templates**: Customizable email content
- **Bulk Email**: Rate-limited bulk sending capabilities

### **Application Profiles**
- **Development**: Debug logging and hot reload
- **Production**: Optimized logging and performance
- **Testing**: Mock services and test data


## 🐛 Troubleshooting

### **Common Issues & Solutions**

#### **Database Connection Issues:**
```bash
# Check MySQL service status
sudo systemctl status mysql

# Verify database exists
mysql -u root -p -e "SHOW DATABASES;"

# Test connection
mysql -u root -p DeveloperCommunityDB
```

#### **Email Configuration Issues:**
- **Authentication Failed**: Verify App Password is correct and 2FA is enabled
- **Connection Timeout**: Check firewall settings and network connectivity
- **Invalid Email Format**: Ensure email addresses follow proper format

#### **Build Issues:**
```bash
# Clear Maven cache and rebuild
mvn clean
mvn install -U

# Check Java version
java -version
javac -version
```

#### **Port Conflicts:**
```bash
# Check if port 8080 is in use
netstat -tulpn | grep :8080

# Use different port
mvn spring-boot:run -Dserver.port=8081
```


## 🙏 Acknowledgments

Special thanks to the following technologies and communities:

- **[Spring Boot](https://spring.io/projects/spring-boot)** - For providing an excellent framework for rapid application development
- **[Bootstrap](https://getbootstrap.com/)** - For the responsive and modern UI components
- **[TinyMCE](https://www.tiny.cloud/)** - For rich text editing capabilities
- **[MySQL](https://www.mysql.com/)** - For reliable database management
- **[Thymeleaf](https://www.thymeleaf.org/)** - For powerful server-side templating
- **Open Source Community** - For continuous inspiration and support

