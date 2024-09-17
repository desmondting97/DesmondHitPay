# DesmondHitPay
ToDoList for HitPay

There are two fragments, first for loading the list and second for adding a ToDo item to the list.
You may delete the item in the list by holding the item until the 'Delete' text appears.

There are two buttons below to prepopulate the list with 2,000 items and delete the data in the database respectively.

You may need to download .idea files on your own to build the project.

You may be prompted to change gradle jdk to your own existing jdk which is either jbr-17 or JAVA_HOME if you are using an older version of Android Studio.

As for the reasoning behind the usage of Room database, it is preferred over the original requirement of SQLite due to its faster data loading times and
and less prone to runtime errors. It also allows for more seamless integration with MVVM which enables an efficient data synchronization with UI which is important
for displaying the 2,000 items with optimum performance. Writing unit tests is also easier with the use of Room Database as it offers in memory database options which 
creates isolated environments for testing.