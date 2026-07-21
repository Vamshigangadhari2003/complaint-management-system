# 🛡️ Smart Complaint Management System

A full-stack **enterprise-grade complaint management system** built with Spring Boot, JWT authentication, Role-Based Access Control (RBAC), and a modern dark-themed frontend.

## 🚀 Live Demo
>https://complaint-management-system-bv7i.onrender.com

## 📸 Screenshots

### Login Page
![Login Page](screenshots/login.png)

### Admin Dashboard
![Admin Dashboard](screenshots/admin.png)

### User Dashboard
![User Dashboard](screenshots/user.png)

### Agent Dashboard
![Agent Dashboard](screenshots/agent.png)

---

## 🎯 Features

### 🔐 Security
- JWT-based stateless authentication
- BCrypt password hashing
- Role-Based Access Control (USER / AGENT / ADMIN)
- Login/logout audit trail with IP tracking

### 👥 Three Role System
| Role | Capabilities |
|------|-------------|
| **USER** | Register, raise complaints, track status, close resolved complaints |
| **AGENT** | View assigned complaints, update status, add resolution notes |
| **ADMIN** | Full system control — manage users, assign agents, view dashboard stats, login history |

### 📋 Complaint Lifecycle

OPEN → ASSIGNED → IN_PROGRESS → RESOLVED → CLOSED

### 📊 Admin Dashboard
- Real-time complaint statistics
- Complaints by category and priority breakdown
- Agent workload monitoring
- Complete login history audit

### 📧 Email Notifications
- Auto email on complaint creation
- Auto email on status updates
- Auto email when complaint is assigned to agent

### 🎨 Modern Frontend
- Dark glassmorphism UI
- Color-coded status badges
- Responsive design
- Toast notifications
- Role-based page routing

---

## 🛠️ Tech Stack

### Backend
| Technology | Purpose |
|-----------|---------|
| Spring Boot 3.5 | Backend framework |
| Spring Security 6 | Authentication & Authorization |
| JWT (jjwt 0.11.5) | Stateless token authentication |
| Spring Data JPA | Database ORM |
| Hibernate | JPA implementation |
| MySQL | Relational database |
| JavaMailSender | Email notifications |
| Lombok | Boilerplate reduction |
| Maven | Build tool |

### Frontend
| Technology | Purpose |
|-----------|---------|
| HTML5 / CSS3 | Structure & Styling |
| Vanilla JavaScript | Frontend logic |
| Google Fonts (Inter) | Typography |
| Fetch API | REST API calls |

---

## 📁 Project Structure

complaint-management/
├── src/main/java/com/vamshi/complaint_management/
│ ├── config/ # Security config, UserDetails
│ ├── controller/ # REST API controllers
│ ├── dto/ # Request/Response DTOs
│ ├── entity/ # JPA entities
│ ├── enums/ # Role, Status, Priority, Category
│ ├── exception/ # Global exception handler
│ ├── filter/ # JWT auth filter
│ ├── repository/ # JPA repositories
│ ├── service/ # Business logic
│ └── util/ # JWT utility
└── src/main/resources/
├── static/
│ ├── index.html # Login page
│ ├── css/ # Stylesheets
│ ├── js/ # JavaScript utilities
│ └── pages/ # Dashboard pages
└── application.properties

---

## 🔌 API Endpoints

### Auth
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/api/auth/register` | Public | Register new user |
| POST | `/api/auth/login` | Public | Login and get JWT |
| POST | `/api/auth/logout` | Authenticated | Logout |

### Complaints
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/api/complaints` | USER | Raise complaint |
| GET | `/api/complaints/my` | USER | View my complaints |
| PUT | `/api/complaints/{id}/close` | USER | Close resolved complaint |
| GET | `/api/complaints/assigned` | AGENT | View assigned complaints |
| PUT | `/api/complaints/{id}/status` | AGENT | Update complaint status |
| GET | `/api/complaints/all` | ADMIN | View all complaints |
| PUT | `/api/complaints/{id}/assign/{agentId}` | ADMIN | Assign agent |

### Admin
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/admin/users` | ADMIN | Get all users |
| POST | `/api/admin/create-agent` | ADMIN | Create agent account |
| GET | `/api/admin/login-history` | ADMIN | View login history |

### Dashboard
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/dashboard` | ADMIN | Get dashboard stats |
| GET | `/api/dashboard/search` | ADMIN | Search/filter complaints |

---

## ⚙️ Local Setup

### Prerequisites
- Java 21
- MySQL 8.0+
- Maven

### Steps

1. **Clone the repository**
```bash
git clone https://github.com/Vamshigangadhari2003/complaint-management-system.git
cd complaint-management-system/complaint-management
```

2. **Create MySQL database**
```sql
CREATE DATABASE complaint_db;
```

3. **Configure application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/complaint_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.mail.username=your_gmail@gmail.com
spring.mail.password=your_app_password
jwt.secret=your_secret_key
jwt.expiration=86400000
```

4. **Run the application**
```bash
./mvnw spring-boot:run
```

5. **Access the application**

http://localhost:8080

### Default Admin Setup
After running, insert admin user via MySQL:
```sql
INSERT INTO users (name, email, password, role, enabled, created_at)
VALUES ('Admin', 'admin@example.com', '$2a$10$...bcrypt_hash...', 'ADMIN', 1, NOW());
```

---

## 👨‍💻 Developer

**Vamshi Gangadhari**
- B.Tech CSE | Pallavi Engineering College, Hyderabad
- GitHub: [@Vamshigangadhari2003](https://github.com/Vamshigangadhari2003)

---

## 📄 License
This project is open source and available under the MIT License.