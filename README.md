#  DelightRoom Music Player Assignment 

멀티 모듈 기반으로 구현된 간단한 음악 플레이어 앱입니다.  
디바이스 내부의 실제 오디오 파일을 불러와 리스트로 표시하고,  
`androidx.media3` 기반의 `ExoPlayer` + `MediaSessionService`로  
백그라운드 재생이 가능한 구조로 설계했습니다. 


## 🚀 Tech Stack

### ✔ Android
- **Kotlin**
- **Jetpack Compose**
- **AndroidX Media3 (ExoPlayer, MediaSessionService)**
- **Coroutines / Flow**
- **ViewModel + StateFlow**
- **Multi Module Architecture**

---

## 🧩 Architecture
🎨 Block-Feature
 
 └── Feature-List     
 └── Feature-Player   

🎧 Block-Core

 └── Core-Media  

🔧 Block-Common

 └── Common-Log
 └── Common-Extension
 └── Common-Utils
 └── Common-Bom

📱 App Layer

 └── MainActivity + Global Navigation


