# BookShelf

BookShelf is an Android application that allows users to sign up, log in, view a list of books, and create personal annotations.

## Features

- **User Authentication:** Sign up, log in, and log out functionality with field validations.
- **Book List Page:** Fetch and display a list of books from a remote API.
- **Favorites:** Ability to mark/unmark books as favorites.
- **Filtering:** Filter books by publish date with a responsive tab interface.
- **Book Detail Page:** View detailed information about each book and add personal annotations.

**Screenshots**
![BookDetail](https://github.com/Anvitha-Mandava/BookShelfApplication/assets/139376656/94f9e412-c79c-43b9-a81c-0be3a07526e8)
![BooksList](https://github.com/Anvitha-Mandava/BookShelfApplication/assets/139376656/07096b2f-b96f-4809-b23a-e8f7d6762d48)
![Login](https://github.com/Anvitha-Mandava/BookShelfApplication/assets/139376656/651d4fee-7151-4966-9c14-66c47e4751b7)
![SignUp](https://github.com/Anvitha-Mandava/BookShelfApplication/assets/139376656/cbc3ecdb-6581-44f9-9a03-a4a97863fd7b)


**Development Decisions and Assumptions**
MVVM Architecture: The app follows the Model-View-ViewModel (MVVM) architecture pattern, separating the business logic (ViewModel) from the UI (View).
Retrofit: The Retrofit library is used for making HTTP requests to the backend API and fetching the book data.
Image Loading: Images of the book are loaded using a library like  Picasso for efficient and optimized image loading and caching.
ROOM Database: A local SQLite database to store the user's data, annotations related to data
Persistence: The user's annotations are persisted across app sessions

### Prerequisites
- Android Studio
- Android SDK
- JDK

**Permissions**
Internet

### Installing
cd ~/ProjectLocation/BookShelfApplication
git clone git@github.com:Anvitha-Mandava/BookShelfApplication.git
Build and Run your project
