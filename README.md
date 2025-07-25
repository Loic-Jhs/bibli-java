# 📚 bibli-java

**bibli-java** is a desktop library management application built with Java, JavaFX, and SQLite. It allows users to manage books, users, and library operations with a modern graphical interface.

---

## Features

- Add, edit, and delete books
- Register, login, and manage users (with password reset)
- Search and filter books
- Export book lists to Word documents
- Data stored locally in SQLite database
- Modern UI with JavaFX and BootstrapFX

---

## Installation

### Prerequisites

- Java 11 or newer
- Maven

### Build & Run

1. Clone the repository:
   ```sh
   git clone https://github.com/Loic-Jhs/bibli-java.git
   cd bibli-java
   ```
2. Build the project:
   ```sh
   mvn clean install
   ```
3. Run the application:
   ```sh
   mvn javafx:run
   ```

---

## Usage

1. Launch the app and log in or register a new user.
2. Add new books or manage existing ones.
3. Use the export feature to save book lists as Word documents.
4. Reset your password if needed from the login screen.

---

## Project Structure

- `src/main/java/org/example/biblijava/` - Main application code
- `src/main/resources/` - FXML views and styles
- `bibliotheque.db` - SQLite database file

---

## Technologies

- Java 11
- JavaFX
- SQLite
- Apache POI (Word export)
- BootstrapFX

---

## Authors

- Loic Jouhans
- Benjamin Ribou

---
