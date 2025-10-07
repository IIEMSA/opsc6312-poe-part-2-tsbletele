CineFlix — Movie Discovery App

CineFlix is a modern Android application that allows users to discover, browse, and explore trending, popular, and upcoming movies using data from The Movie Database (TMDb) API.
It provides a visually engaging interface, Firebase user authentication, and detailed movie information screens.

Features
Authentication

Firebase Sign Up & Sign In — Secure login and registration for users.

Input validation for emails and passwords.

Error feedback for incorrect credentials.

Movie Browsing

Displays Trending, Popular, and Upcoming movie lists.

Fetches live data from TMDb using Retrofit.

Search and voice search support.

Each movie shows:

Poster

Title

Genre tags

Movie Details

Tap any movie to view:

Full poster

Overview / synopsis

Genre chips

Share button (send movie info via apps)

“More Like This” section with similar movies

Navigation

Welcome Page with buttons for Sign Up / Login.

MainActivity displays featured and trending movies.

MovieDetailActivity shows details for selected movies.

Bottom Navigation Bar for quick navigation (Home, Browse, Account, etc.).

Tech Stack
Component	Technology
Language	Kotlin
UI Design	XML, Material Design
Backend / Auth	Firebase Authentication & Firestore
API Integration	Retrofit + Gson
Image Loading	Glide
Async Operations	Kotlin Coroutines
Architecture	MVVM Pattern (Model–View–ViewModel)
Data Source	TMDb REST API
Screens

Welcome Screen — Entry point with Sign Up and Login.

Sign Up / Sign In — Firebase-based authentication.

Main Screen — Shows movie categories (Trending, Popular, Upcoming).

Browse Movies — Search and filter by genre or release date.

Movie Details — Complete details and similar movie suggestions.

⚙️ Setup Instructions
1️⃣ Clone the Repository
git clone https://github.com/yourusername/CineFlix.git
cd CineFlix

2️⃣ Add Your TMDb API Key

Open RetrofitClient.kt and replace the placeholder:

private const val API_KEY = "YOUR_API_KEY_HERE"

3️⃣ Connect Firebase

Go to Firebase Console

Create a project named CineFlix

Download google-services.json
→ Place it in:

app/google-services.json


Enable Authentication (Email/Password) in Firebase.

4️⃣ Build and Run

Open the project in Android Studio, then:

Select an emulator or connected device

Click ▶️ Run

Project Structure
CineFlix/
│
├── app/
│   ├── java/com/example/cineflix/
│   │   ├── ui/ → Activities (Main, Browse, MovieDetail)
│   │   ├── adapters/ → RecyclerView adapters
│   │   ├── data/network/ → Retrofit client & API service
│   │   ├── model/ → Data classes (Movie, Genre)
│   │   └── viewmodel/ → Optional ViewModels
│   │
│   └── res/
│       ├── layout/ → XML UI files
│       ├── drawable/ → Icons, shapes, buttons
│       ├── values/ → Colors, strings, styles
│       └── navigation/ → Bottom nav menus
│
├── build.gradle (app)
├── build.gradle (project)
└── README.md

Future Enhancements

User watchlist & favorites

Personalized movie recommendations

In-app trailer playback

Dark mode

Offline caching
