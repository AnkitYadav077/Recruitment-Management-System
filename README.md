# 🚀 Recruitment Management System

A **Spring Boot–based backend** for managing the entire recruitment process.  
This system provides secure user authentication, automated resume parsing, and powerful admin tools to handle job postings and applicant data efficiently.

---

## 🧩 Features Overview

### 👥 User Management
- 🔐 **JWT-based authentication** using Spring Security
- 👤 Two user roles: **Admin** and **Applicant**
- 📝 Manage personal profiles and update information
- 🔒 Role-based access control for secure operations

---

### 📄 Resume Processing
- 📤 Upload resumes in **PDF** or **DOCX** format
- 🤖 Automatic **resume parsing** via third-party API
- 🧠 Extract key information like:
    - Skills
    - Education
    - Experience
    - Contact details

---

### 💼 Job Management
- 🧑‍💼 **Admins** can create, update, and delete job openings
- 👨‍💻 **Applicants** can browse and apply for available jobs
- 📬 Track applications and view job-related details

---

### 🛠️ Admin Dashboard
- 📋 View all applicants and parsed details
- 🔍 Access comprehensive applicant profiles
- 📂 See job details along with applicant lists

---

## 🧠 Tech Stack

| Category | Technology |
|-----------|-------------|
| **Backend Framework** | Spring Boot 3.x |
| **Security** | Spring Security + JWT |
| **Database** | MySQL + Spring Data JPA |
| **File Upload** | Spring Multipart File |
| **HTTP Client** | RestTemplate / WebClient |
| **Build Tool** | Maven |

---

## ⚙️ System Architecture

```plaintext
[ Applicant ] ---> [ Spring Boot API ] ---> [ MySQL Database ]
         |                   |
         |                   ---> [ Resume Parsing API ]
         |
[ Admin ] ---> Manage Jobs / Applicants
