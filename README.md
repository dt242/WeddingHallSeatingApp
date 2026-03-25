# Wedding Hall Seating App 🥂

**Wedding Hall Seating App** is an intelligent system for managing and visualizing guest seating arrangements during events. The project is focused on demonstrating advanced **Object-Oriented Programming (OOP)** concepts and the practical application of **Design Patterns** within real-world business logic.

The application allows for flexible modeling of a wedding hall's hierarchical structure—from individual guests to families and tables—while simultaneously enforcing specific seating constraints.

## Architecture & Design Patterns

The primary focus of this project is clean architectural organization and the implementation of classic software design patterns:

* **Composite Pattern**: Used to model the hall's hierarchy. The `Seatable` interface allows individual guests (`Leaf`) and groups such as families and tables (`Composite`) to be treated uniformly by the system.
* **Iterator Pattern**: Implemented for flexible traversal of the complex table structure. The system supports various iteration modes—by tables, by families, or by individual guests—without exposing their internal representation.
* **Singleton Pattern**: The `WeddingRules` class ensures centralized and global management of system constraints (e.g., banned family pairs).
* **MVC Architecture**: Clean separation of Model (business objects), Service (business logic), and Controller/View (user interface via Thymeleaf).

## Core Features

* **Dynamic Seating**: Add and remove tables, families, and guests in real-time.
* **Smart Constraints**: Automatically enforces limits such as a maximum number of guests per table (up to 10) or families per table (up to 2).
* **Ban System**: Ability to define rules that prevent specific families from sitting at the same table.
* **Dynamic Search & Statistics**: View all participants with JavaScript-powered filtering and track overall hall capacity.

## Tech Stack

* **Language**: Java 21
* **Framework**: Spring Boot
* **Front-end**: Thymeleaf, HTML5, CSS3
* **Data Management**: In-memory data structures (focusing on OOP logic)
* **Build Tool**: Maven

## Getting Started

1.  **Clone the repository**:
    ```bash
    git clone [https://github.com/dt242/WeddingHallSeatingApp.git](https://github.com/dt242/WeddingHallSeatingApp.git)
    ```
2.  **Install dependencies**:
    ```bash
    mvn clean install
    ```
3.  **Run the application**:
    ```bash
    mvn spring-boot:run
    ```
4.  Access the web interface at: `http://localhost:8080`
