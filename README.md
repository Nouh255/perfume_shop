# Luxury Perfume Shop Android App

An advanced Android application for browsing and managing luxury perfumes. This app demonstrates a hybrid approach, combining real-time data from a public API with a local SQLite database for personal inventory management.

## 📱 Features

### 1. User Authentication
*   **Secure Registration:** Create an account with name, email, password, and phone number.
*   **Login System:** Secure authentication against a local SQLite user database.
*   **Validation:** Real-time input validation for all forms (email format, password length, etc.).

### 2. Dual-Mode Dashboard
After logging in, users can choose between two distinct modes:

*   **🌐 Public Perfume API (Read-Only)**
    *   Browse a vast catalog of real perfumes fetched from the **Fragella API**.
    *   View high-quality images, prices, brands, and descriptions.
    *   Network handling with Retrofit and error management.

*   **✏️ My Collection (Local CRUD)**
    *   **Create:** Add your own custom perfumes to the database.
    *   **Read:** View your personal inventory in a beautiful list format.
    *   **Update:** Edit details (price, description, name) of existing perfumes.
    *   **Delete:** Remove items from your collection with confirmation.
    *   **Offline Capable:** All personal data is stored locally using SQLite.

### 3. Modern UI/UX Design
*   **Material Design 3:** Uses modern components like MaterialButtons, Cards, and TextInputs.
*   **Custom Headers:** Unique, non-overlapping header design with status bar integrity.
*   **Responsive Layouts:** Optimized for various screen sizes.
*   **Visual Feedback:** Loading spinners, empty states, and toast notifications.

## 🛠️ Technology Stack

*   **Language:** Java
*   **Minimum SDK:** Android 24 (Nougat)
*   **Architecture:** MVC (Model-View-Controller) pattern

### Libraries & Dependencies
*   **Retrofit2 & OkHttp3:** For robust network requests and API integration.
*   **Glide:** For efficient image loading and caching.
*   **Material Components:** For modern UI elements.
*   **SQLite:** For local database management of users and custom perfumes.

## 🚀 Getting Started

### Prerequisites
*   Android Studio Iguana or newer.
*   JDK 17 or higher.

### Installation
1.  Clone the repository:
    ```bash
    git clone https://github.com/Nouh255/perfume_shop.git
    ```
2.  Open the project in Android Studio.
3.  Sync Gradle files to download dependencies.
4.  Run the application on an emulator or physical device.

## 📂 Project Structure

```
com.example.perfume_shop
├── adapters/           # RecyclerView adapters for lists
├── api/                # Retrofit client and API interfaces
├── database/           # SQLiteOpenHelper and CRUD logic
├── models/             # POJO classes for User and Perfume
├── utils/              # Helper classes (Validation, constants)
├── Activities          # (MainActivity, Login, Dashboard, etc.)
└── res/layout/         # XML UI definitions
```

## 📸 Screenshots
*(Add screenshots of your Login, Dashboard Selection, and Inventory screens here)*

## 🤝 Contributing
1.  Fork the repository.
2.  Create your feature branch (`git checkout -b feature/AmazingFeature`).
3.  Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4.  Push to the branch (`git push origin feature/AmazingFeature`).
5.  Open a Pull Request.

## 📄 License
This project is licensed under the MIT License - see the LICENSE file for details.
