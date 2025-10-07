# CineFlix — Movie Discovery App

CineFlix is a modern Android application that allows users to discover, browse, and explore trending, popular, and upcoming movies using data from **The Movie Database (TMDb)** API.  
It provides a visually engaging interface, Firebase user authentication, and detailed movie information screens.

---

## Features

###  Authentication
- Firebase **Sign Up & Sign In** — secure user login and registration.  
- Input validation for emails and passwords.  
- User-friendly feedback for incorrect credentials.

###  Movie Browsing
- Displays **Trending**, **Popular**, and **Upcoming** movie lists.  
- Fetches live movie data using **Retrofit** from TMDb.  
- Search and voice search functionality.  
- Each movie displays: Poster, Title, Genre tags.

###  Movie Details
- Tap any movie to view:
  - Full poster image  
  - Overview / synopsis  
  - Genre chips  
  - Share button to share movie info  
  - “More Like This” section with similar movies  

###  Navigation
- **Welcome Page** — entry point with Sign Up and Login options.  
- **MainActivity** — displays featured, trending, and popular movies.  
- **MovieDetailActivity** — detailed view of a selected movie.  
- **Bottom Navigation Bar** — quick access to Home, Browse, and Account pages.

---

##  Tech Stack

| Component | Technology |
|------------|-------------|
| **Language** | Kotlin |
| **UI Design** | XML, Material Design |
| **Backend / Auth** | Firebase Authentication & Firestore |
| **API Integration** | Retrofit + Gson |
| **Image Loading** | Glide |
| **Async Operations** | Kotlin Coroutines |
| **Architecture** | MVVM (Model–View–ViewModel) |
| **Data Source** | TMDb REST API |

---

##  Screens

- **Welcome Screen** — entry page with sign-up and login options.  
- **Sign Up / Sign In** — Firebase-based user authentication.  
- **Main Screen** — shows Trending, Popular, and Upcoming movies.  
- **Browse Movies** — search and filter by genre or release date.  
- **Movie Details** — full details, genres, and similar movie suggestions.

---

##  Setup Instructions

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/yourusername/CineFlix.git
cd CineFlix
```

### 2️⃣ Add Your TMDb API Key
Open `RetrofitClient.kt` and replace the placeholder:
```kotlin
private const val API_KEY = "YOUR_API_KEY_HERE"
```

### 3️⃣ Connect Firebase
1. Go to [Firebase Console](https://console.firebase.google.com/).  
2. Create a project named **CineFlix**.  
3. Download your `google-services.json` file and place it in:  
   ```
   app/google-services.json
   ```  
4. Enable **Authentication (Email/Password)** under the Firebase Authentication tab.

### 4️⃣ Build and Run
1. Open the project in **Android Studio**.  
2. Select an emulator or connect a device.  
3. Click ▶️ **Run** to build and launch the app.

---

##  Project Structure

```
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
```

---

## 🔮 Future Enhancements

- 🎞️ User watchlist & favorites  
- 🤖 Personalized recommendations  
- ▶️ In-app trailer playback  
- 🌙 Dark mode support  
- 📦 Offline caching  
