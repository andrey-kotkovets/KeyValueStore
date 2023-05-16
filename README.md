# Transactional Key Value Store


https://github.com/andrey-kotkovets/KeyValueStore/assets/33689229/f231b142-8d05-490a-bd75-3283d6575e08


## Architecture
This Android project is written using Kotlin and built using Model - View - View Model (**MVVM**) architecture with usage of Android Jetpack components such as **Compose UI**, **View Model** and **Hilt**.

- The View layer uses a Compose screen.
- The Data layer uses a Repository and KeyValueStore.
- The Domain layer handles application business logic - parsing and executing commands.
- ViewModel calls Interactor from the Domain layer to parse a command and get a result.
- ViewModel gets the data and sends it to a Compose screen using Kotlin Flow.

## Run the app
To run the app:
1. Clone this project.
2. Open it in Android Studio.
3. Build and run it on a device or emulator.

## Test coverage
The application has examples of 2 types of tests:
1. Unit tests - created to check the provided examples on the `KeyValueStore`.
2. UI tests - created to perform basic check of the application `MainScreen`.

## Omitted in this project
1. `KeyValueStore` is not thread safe. Synchronisation part was omitted for now. If necessary, there are a few quick options to implement it:
   -- to make the full class `KeyValueStore` `synchronized` but it will available just for one operation at a time,
   -- to make `synchronized` just write-operations and read-operations will be executed without blocking the store.
2. `MainScreen` list scrolling when IME appears (currently, it will overlap list items if the list content is too big).
