# 🗓️ **Day 11 – Centralized Configuration using Spring Cloud Config Server (Local Setup)**

## 🎯 **Goal**
To centralize and manage configuration files of multiple microservices (like **product-service** and **order-service**) using **Spring Cloud Config Server**, with a **local file-based config repository**.

---

## 🧩 **Why Centralized Configuration?**
When you have multiple microservices, each one usually maintains its own `application.yml` file.  
Updating common settings (like database URLs, API keys, or ports) becomes time-consuming.  

**Spring Cloud Config Server** provides:
- A single source of truth for configuration (central repo)
- Easy environment management (`dev`, `test`, `prod`)
- Version control and consistency across all services

---

## ⚙️ **Project Structure**

Actual structure looks like this 👇

```
springboot-microservices-training/
├── api-gateway/
├── config-server/
│   └── src/main/resources/application.yml
│
├── discovery-server/
├── order-service/
│   └── src/main/resources/application.yml
│
├── product-service/
│   └── src/main/resources/application.yml
│
└── notes/
```

**Local Config Repo (stored outside the project):**
```
C:\Users\kalee\Documents\config-repo
├── product-service-dev.yml
└── order-service-dev.yml
```

---

## 🧱 **1️⃣ Config Server Setup**

### 🔹 Dependency (pom.xml)
Add this in `config-server/pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

---

### 🔹 Enable Config Server
In your main class (`ConfigServerApplication.java`):

```java
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```

---

### 🔹 Config Server Configuration

**File:** `config-server/src/main/resources/application.yml`

```yaml
server:
  port: 8888

spring:
  application:
    name: config-server

  cloud:
    config:
      server:
        git:
          uri: file:///C:/Users/kalee/Documents/config-repo
```

> ✅ The `file:///` prefix tells Spring Cloud Config Server to read configuration files from your **local folder** instead of a remote Git repository.

---

## 🧱 **2️⃣ Config Repo Setup (Local Folder)**

Path:  
```
C:\Users\kalee\Documents\config-repo
```

Inside it, create config files for each service and environment.

---

### **product-service-dev.yml**

```yaml
server:
  port: 8081

spring:
  application:
    name: product-service

app:
  message: "This is Product Service (from Config Server)"
```

---

### **order-service-dev.yml**

```yaml
server:
  port: 8082

spring:
  application:
    name: order-service

app:
  message: "This is Order Service (from Config Server)"
```

---

> 💡 Naming convention:  
> `service-name-profile.yml` → e.g. `product-service-dev.yml`  
> When a service runs with profile `dev`, the config server will automatically pick this file.

---

## 🧱 **3️⃣ Product Service Setup**

### 🔹 Dependency
Add this to `product-service/pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

---

### 🔹 Configuration

**File:** `product-service/src/main/resources/application.yml`

```yaml
spring:
  application:
    name: product-service
  profiles:
    active: dev
  config:
    import: "optional:configserver:http://localhost:8888"
```

---

## 🧱 **4️⃣ Order Service Setup**

### 🔹 Dependency
Add this in `order-service/pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

---

### 🔹 Configuration

**File:** `order-service/src/main/resources/application.yml`

```yaml
spring:
  application:
    name: order-service
  profiles:
    active: dev
  config:
    import: "optional:configserver:http://localhost:8888"
```

---


---

## 🚀 **5️⃣ Running and Testing the Setup**

### 🔹 Step 1: Start the Config Server
- Run the `config-server` application.
- It will read configuration files from your local folder:  
  `C:\Users\kalee\Documents\config-repo`

You can verify by opening in browser:
- 👉 [http://localhost:8888/product-service/dev](http://localhost:8888/product-service/dev)
- 👉 [http://localhost:8888/order-service/dev](http://localhost:8888/order-service/dev)

You’ll see JSON output of config values.

---

### 🔹 Step 2: Start Product Service
- Run `product-service`.
- Test URL: 
- Expected : product-service should pick its config from config-server and will run

---

### 🔹 Step 3: Start Order Service
- Run `order-service`.
- Test URL: [http://localhost:8082/order/message](http://localhost:8082/order/message)
- Expected - Expected : order-service should pick its config from config-server and will run

---

## 🔁 **6️⃣ Changing Configs**

If you change any value in the local config files (e.g., update message),  
then restart the microservice → new values will be fetched automatically from Config Server.

> 💡 Later you can add `@RefreshScope` and `/actuator/refresh` endpoint to refresh configs at runtime without restarting.

---

## 🧠 **7️⃣ How the Flow Works**

```
+---------------------------+
| Local Config Repo (YAMLs) |
| product-service-dev.yml   |
| order-service-dev.yml     |
+-------------+-------------+
              |
              v
+---------------------------+
| Config Server (port 8888) |
| Reads from local folder   |
+-------------+-------------+
              |
              v
+---------------------------+
| Microservices             |
| (Product, Order, etc.)    |
| Fetch configs dynamically |
+---------------------------+
```

---

## 🔒 **8️⃣ Best Practices**

✅ Keep config repo **separate** from code (as you’re doing).  
✅ Don’t store secrets in plain text — use environment variables or Vault.  
✅ Maintain naming conventions (`service-profile.yml`).  
✅ For production → host config-repo on GitHub or GitLab.  

---

## 🌐 **9️⃣ (Optional) Move to GitHub Later**

When ready to push your config-repo online:
1. Create a new GitHub repo named `config-repo`.
2. In terminal:
   ```bash
   cd C:\Users\kalee\Documents\config-repo
   git init
   git remote add origin https://github.com/<your-username>/config-repo.git
   git add .
   git commit -m "Initial config files"
   git push -u origin master
   ```
3. Update `config-server/application.yml`:
   ```yaml
   spring:
     cloud:
       config:
         server:
           git:
             uri: https://github.com/<your-username>/config-repo.git
             default-label: master
   ```

---

## ✅ **Summary**

✔️ Config Server centralizes microservice configurations.  
✔️ Configs are stored locally for now, easy to edit and test.  
✔️ Each service loads its configuration from Config Server at startup.  
✔️ The setup can easily be upgraded later to use a remote GitHub repo.

---

## 🏁 **End of Day 11**
🎉 I have successfully:
- Built a **Config Server**  
- Created a **central config repository**  
- Connected multiple microservices to it  
- Verified configuration loading dynamically from a single source  
