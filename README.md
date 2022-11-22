# CHM-Test-App
Simple Compose MVVM aplication that downloads data from the Livesport test api.

### Design descisions

* App uses Retrofit for network comunication together with Moshi codegen for improved performance
* The search text is stored in the view model so that on the fly search or some custom validation can be added.
* The App contains Room that stores the last search data. It has two main reasons - It convenient for the user as well as
it simplfies passing around the search data because there was no endpoint for the search entity detail.
* For styling basic Material3 theme was used
