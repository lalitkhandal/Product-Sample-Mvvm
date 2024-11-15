# Product-Sample-Mvvm

This project demonstrates a **Product Sample Application** built using the **MVVM** (Model-View-ViewModel) architecture, implemented with modern Android development tools and best practices. The app leverages **Jetpack Compose** for building the UI, **Kotlin Flow** for handling asynchronous data streams, **Retrofit** for API communication, and **Room Database** for local data storage. It follows **SOLID principles** and **Clean Code** practices to ensure a maintainable, scalable, and testable codebase.

## Features

- **MVVM Architecture:** Clean separation of concerns using Model, View, and ViewModel.
- **Jetpack Compose:** A modern UI toolkit for building native UIs.
- **Kotlin Flow:** Asynchronous programming with a declarative stream API for handling data.
- **Retrofit:** Simplified HTTP communication for consuming public APIs.
- **Room Database:** Local database for storing product data persistently.
- **SOLID Principles:** Follows the SOLID principles to maintain clean, modular, and scalable code.
- **Unit Testing & UI Testing:** Comprehensive test coverage using JUnit and Compose Testing APIs.

### Key Components:

- **Data Layer:** Handles the communication with the network (via Retrofit) and local storage (via Room).
- **Domain Layer:** Contains business logic and use cases that interact with repositories to fetch or store data.
- **Presentation Layer:** Comprises ViewModels and UI components written using Jetpack Compose. The ViewModel interacts with the domain layer to provide data to the View.
- **Dependency Injection:** Managed via Hilt to inject dependencies into different layers.


