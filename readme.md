### Project Bio
This project serves as a sandbox for exploring various Android development concepts. It's a collection of code snippets, methods, classes, and packages that can be reused and adapted for future projects.

### Cover Story
This project is built as a **Weather App** that demonstrates the use of the layered architecture while experimenting with data access and processing techniques. The app retrieves temperature data from multiple sources and calculates the average to provide a more comprehensive picture of the current conditions.

### ToDo list
- [x] Migrate to Koin DI
- [x] Convert project into Kotlin Multiplatform (Android + iOS)
- [ ] Share logic between platforms
- [ ] Cover UseCases with unit tests (1/3)
- [ ] Implement native iOS app UI
- [ ] Implement navigation between screens for Android and iOS
- [ ] Add preference screen to configure weather sources (for android and iOS)
- [ ] Add offline support
 
### Project Structure
I'm gonna try to use following hierarchy as a project structure template.
```
- app
- data
  - repository // data access logic, interacts with remote/local sources
  - service
    - local
      - location
        - LocationService.class
        - FusedLocationService.class
      - database
    - remote
      - openmeteo
        - request // optional
        - response // optional
        - OpenMeteoRemoteService.class
        - OpenMeteoHttpRemoteService.class
- domain
  - usecase
  - model
- presentation
  - navigation
  - composable
  - screens
    - feature1
      - Feature1ViewModel.class
      - Feature1Screen.class
  - widgets
  - theme
- utils
- di
- service  // services directly tied to app functionality
  - MyBackgroundService.kt
```

### Setup
Before building the project, obtain your API keys and save it in the `apikeys.properties` file using the following template.
```properties  
# https://home.openweathermap.org/api_keys  
openweathermap.apikey=YOUR_OpenWeatherMap_API_KEY_HERE
```