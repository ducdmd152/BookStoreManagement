# Documentation Writing

Be completed: December 12, 2022
Done: No
Order: 7

## 🥪 Book Store Management 1.0
#javawebapplicationdevelopment

## **🏷️ Resources:**

| # | Resource | Description |
| --- | --- | --- |
| 1 | [Book Store Management 1.0](https://github.com/ducdmd152/BookStoreManagement_1.0) | The documentation about my web app for Book Store’s context. |
| 2 | [MVC2 Notebook](https://github.com/ducdmd152/BookStoreManagement_1.0/tree/main/MVC2) | My self-notes on the learning path for the flow and something related MVC2 model. |
| 3 | [SourceCode](https://github.com/ducdmd152/BookStoreManagement_1.0/tree/main/SourceCode) | The folder contains the source code of the project. |

## Table of Contents

- [Description](#description)
- [Live Demo](#live-demo--here)
- [Technology](#technology)
- [How can get started](#how-can-get-started)
- [License & Copyright](sqljdbc42.jarr)

## Description

- A bookstore web application for buying books
- Users are divided into 2 main roles: Admin & customers.
- Both roles are authenticated through the Login feature;
In addition, customers also support the Register feature.
- As an admin, we can do some operations to manage accounts like search, delete or update.
- As a customer, we can do some operations to order books like:
    - View book in the store.
    - Cart’s actions:
        - Add books to the cart.
        - View books in the cart.
        - Remove books from the cart.
    - Checkout:
        - Fill in the delivery information.
    - Note*: *You can operate as a customer and buy the book without login.*
- This web is tested for screens and features on desktops; not yet for mobile.

## Live Demo: 👉 [here]()

## Technology

**1. Frontend**

- HTML, CSS
- JSP - Java Server Pages
- JSTL: JSP Standard Tag Library

**2. Backend**

- Servlets: as Controllers
- JDBC - Java Database Connectivity.

**3. Database**

- Microsoft SQL Server.

**4. Tools**

- Netbeans 8.2
- Java JDK 8
- Apache Tomcat 8.0.27

**5. Bonus**
- SHA256 for password's hashing 

## How can Get Started

- Source/Project:
    - Download the [Source Code here](https://github.com/ducdmd152/BookStoreManagement_1.0/tree/main/SourceCode).
    - Open the downloaded project on Netbeans:
        - Import JSTL 1.2.1 library that supports by IDE
        - Import [sqlJDBC42.jar]() which you can download here into the lib folder of Tomcat,
        maybe it looks like C:\Program Files\Apache Software Foundation\Apache Tomcat 8.0.27\lib.
- Database:
    - Execute the [BOOK_STORE.sql script](https://github.com/ducdmd152/BookStoreManagement_1.0/blob/main/BOOK_STORE.sql)
    - Customize the content in `[context.xml](https://github.com/ducdmd152/BookStoreManagement_1.0/blob/main/SourceCode/web/META-INF/context.xml)` for adapting to your machine.
   

## License & Copyright
&copy; 2022 Duc Dao ducdmd152 Licensed under the [MIT LICENSE](https://github.com/ducdmd152/BookStoreManagement_1.0/blob/main/LICENSE).

> 🤟 Feel free to use my repository and star it if you find something interesting 🤟
