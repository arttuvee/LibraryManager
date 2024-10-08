# LibraryManager Application

This is the repository for the **LibraryManager** application. The application allows users to reserve books, movies, and games from a library. Users can also manage their loans, view return policies, and receive invoices for late returns.

To use the application, users must create an account. Admins have access to additional features, such as monitoring the inventory of available items. The app restricts access to certain media based on age (e.g., 18+ for certain movies or games).

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Screenshots](#screenshots)
- [Rebuild Instructions](#rebuild-instructions)

## Features
- Reserve books, movies, and games.
- View and manage loans.
- Receive invoices for late returns.
- Admins can manage the library's inventory.
- Age-based content restrictions (18+ for some media).

## Technologies Used
- **JavaFX**
- **Java**
- **Scene Builder**
- **Spring Boot**
- **CSS**
- **Git**
- **SQL**


## Installation

To get started, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/arttuvee/LibraryManager.git
   cd LibraryManager

1. Connect to Metropolia VPN: Ensure that you are connected to the Metropolia VPN to access the library's database.

2. Run the application: Use your Java IDE to run the application.
## Screenshots
![image](https://github.com/user-attachments/assets/3760bd93-0ab1-44a4-8929-df368520c2b5)
Running the application
### Log In / Sign Up
![image](https://github.com/user-attachments/assets/0fb1c892-ff1d-4fed-9b1c-de1067fca8ef)
*Log in or sign up screen.*
### Homepage
![image](https://github.com/user-attachments/assets/8c682907-4e6a-48fb-ad24-8b4779e5e478)
1. **Kirjahylly (Bookshelf)**  
   This section displays a list of books and other media in the library. It shows details such as:
   - Title
   - Year
   - Author
   - Publisher
   - Age rating
   - Type (e.g., book, movie, game)
   - Description
   - Genre

2. **Lainat (Loans)**  
   This section manages the books or items that have been borrowed. It displays information about the loaned items and due dates.

3. **Laskut (Bills)**  
   This section handles financial matters, such as any fees for late returns.

4. **Varasto (Storage/Inventory)**  
   This section shows items that are in storage or part of the library's collection but are not currently available for loan.

5. **Lainaa kirja (Borrow a Book)**  
   This button allows users to check out a selected book or item from the library's collection.

6. **Kirjaudu ulos (Log out)**  
   This button logs the user out of the system.

   ## Charts
## Architectural Design: ER Kaavio
![ER Kaavio](https://github.com/user-attachments/assets/bd04e306-5dd0-46af-9f2c-4c90410f5fa7)
**Figure 1: ER Kaavio**

## Architectural Design: Case Diagram
![Case Diagram](https://github.com/user-attachments/assets/c6a5bd07-439a-4d79-b150-dcf4ed2a7639)
**Figure 2: Case Diagram**

## Architectural Design: Activity Diagram
![Activity Diagram](https://github.com/user-attachments/assets/ae0a6da5-649e-4c24-b097-fd743a59a3c4)
**Figure 3: Activity Diagram**


   
  
   ## Rebuild Instructions

If you want to rebuild the **LibraryManager** application from scratch, follow these steps:

### 1. Clone the Repository
First, make sure you have Git installed, then clone the repository:
```bash
git clone https://github.com/arttuvee/LibraryManager.git
cd LibraryManager
```

## 2. Set Up Metropolia VPN
To connect to the library's database, you need to access it via the Metropolia VPN. If you're not connected, you won't be able to log in.

## 3. Run the Application
Once you've installed the required dependencies and are connected to the VPN, you can run the application:

## 4. Rebuild the Project
If you want to make changes and rebuild the project:

1. Edit the code or configuration as needed.
2. Compile the Java code using your IDE's build. 
3. Repackage the application using tools like Maven if necessary.
4. Test the changes locally to ensure everything works as expected.
