# Contacts App
Add contacts to your list and decide what contact information to see. <br/>
Signup/Login is required initially. <br/> 
<img src = "README images/app_icon.png" height="300"> <br/>

## Usage:
1. In order to save your contacts, first Signup and a space in the DB will be created. <br/>
   (Default user: admin, 123) <br/>
   After you Login, the Contacts screen will be launched first always, even after the app is closed. <br/>
   Multiple users can use the app in the same device(Logout old user -> Login new user). <br/>
   If you forgot your password, you can change it at the Login screen. <br/><br/>
   <img src = "README images/login_signup_changePass_screens.png"> <br/>
2. In the Contacts screen, you can add new contacts and also change the settings of the contacts list and choose what contact information to see. <br/><br/>
   <img src = "README images/contacts_addNew_settings_screens.png"> <br/>
3. Pressing on a contact leads to a full screen with his information and other actions. <br/><br/>
   <img src = "README images/contact_info_edit_screens.png"> <br/>

## Includes:
* Main Activity with Fragments, Binding, Navigation graph <br/>
* RecyclerView <br/>
* Shared preferences, Jackson's ObjectMapper <br/>
* Menu <br/>
* Data base: ROOM(LiveData) <br/>
* Design pattern: MVVM <br/>

## Created with:
* Language: Java
* Android Studio: 2022.1.1 Patch 2.
* SDK min version: 23
* SDK target version: 33
