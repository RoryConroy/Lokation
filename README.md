# Lokation;

## Lokation

Lokation is an application which allows users to create a mood rating based on their current location and look back on it historically allowing the user to update/delete the placemarks as they wish and view their favourites using the google maps API.


## Development quick overview
This app has been developed by Rory Conroy(20080846) for the Mobile App Development 2 module.
This app is originally based off the Donation labs. Lokation app was built on APK version 29.
All references can be found below.


## Technical report
This app incorporates use of the firebase real time database, firebase authentication, firebase storage, FirebaseUI and the google maps API for the users favourite moods.


### Functionality

When the user launches the app for the first time, they will have to sign up. The user has 3 choices:
1)  Use their email address
2)  Sign in with their phone number
3)  Sign in using google

This sign in process is managed using the firebase authentication development tools available. 

After the user logs in, they are able to create a mood straight away. The user must enter a title and a rating from 1-10 with an optional favourite button and short description. When the user presses upload this goes straight to the firebase real time database and is saved under the user's id. The user can then view all of their location memories on a card view, editing and deleting as they please.
The user also has an option to view every user on the apps mood rating and title by pressing on 'all lokations'.

When the user makes any changes to their lokation this happens instantly across the app and will be removed from the card views accordingly. The user also has an option to view their favourite lokations via the Google Maps API. When the user presses 'favourites' they will be brought to a map showing all of their lokations which then shoes the lokation logo, title, rating and short descriptions pinned across where they have uploaded.

In lokation, firebase storage was used to store the users profile image. When a user logs in through google, the app will fetch the users google display images and set this to their image. The user also has an option to change this image to something else if they'd like, and then this will save to the firebase storage too. 

### UX / DX Approach adopted

For the UX and DX approach, the first thing i focused my time on was the colour scheme. The colour scheme is a vital piece of the design process and will determine the users initial impressions of the application which they are using. After some research, i decided to use a monochromatic colour scheme. I decided to use this because the colours are all in the same range and are guaranteed to suit eachother.

For the design experience i focused on app usability. I only added features which actually benefitted the users expierence and removed a lot of unnecessary segments. The app was developed at every stage with the simplest usability possible for the user.


 ### Git approach

For the git approach i used 2 branches. I used my master branch as my development branch with an early tagged release. I then had a release branch so i could create a final tagged release which was the full final release version.


### Personal Statement
Overall, i am happy with how this app turned out. It was a very interesting project to work on throughout the semester - with plenty of new skills acquired. It was great to be able to tie so many different types of functionality under one app such as the authentication/database and storage. 











## References

donation labs:
https://tutors-design.netlify.app/course/mobile-app-dev-2-2020.netlify.app

Monochromatic colour scheme:
https://www.w3schools.com/colors/colors_monochromatic.asp

Best android development practices:
https://www.netguru.com/blog/best-practices-in-android-app-development
