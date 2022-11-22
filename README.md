# CHM-Test-App
Simple Compose MVVM application that downloads data from the Livesport test API.

### Design decisions

* App uses Retrofit for network communication together with Moshi code generation for better performance than reflection approach.
* The search text is stored in the view model so that on the fly search or some custom validation can be easily added.
* The app also contains Room library that stores the last searched data. It  has two main reasons - It is convenient for the user as well as it  simplifies passing around the search data because there was no endpoint  for the search entity detail.
* For styling basic Material3 theme was used and I focused on the code itself instead of UI design.
* I chose TOML build system as it brings an advantage with libraries updates via one gradle command.
* I also used ktlint as a standard for Kotlin coding conventions.
