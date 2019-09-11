Big<3 - README
===

# Big<3

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Social media platform for sharing acts of kindness & promoting communities that give back to each other! 

### App Evaluation
- **Category:** Social Networking , Lifestyle
- **Mobile:** 
    - How uniquely mobile is the product experience?
        - Embedded map, Interactivity with camera & Connects like users in community
- **Story:**
    - How compelling is the story around this app once completed?
        -  In a society that sometimes prioritizes personal endeavors over bonding between individuals, this app promotes overall kindness in communities and neighborhoods——builds friendships and relationships through good hearted deeds. 
- **Market:**
    - What's the size and scale of your potential user base?
        - Potentially very large - events all over the world, could also be strong in building up smallers communities
    - Does this app provide huge value to a niche group of people?
        - Those who need help can find more volunteers simply by posting an event
        - Those that wish to share their kind acts with thier friends 
        - Those that wish to "track" the good deeds they put in this world
    - Do you have a well-defined audience of people for this app?
        - For anyone who wishes to do their part in society if they have any means to do so. Even those who are not financially able can do kind acts/deeds.
- **Habit:**
    - How frequently would an average user open and use this app?
        - The average user would open the app at least once per day.
    - Does an average user just consume your app or do they create?
        - Both - can create events or see and go to events; also can see events that other people go to
        - Post good deeds done, request help for deeds to be done, report good deeds user has done
- **Scope:**
    - How technically challenging will it be to complete this app by the end of the program?
        - Not too challenging for the main flow - stretch stories might be more difficult
    - Is a stripped-down version of this app still interesting to build?
        - Making our app with basic features and referencing potential features would still be interesting
    - How clearly defined is the product you want to build?
        - There is a clear goal for our MVP and some optional stories we would like to get to

## Product Spec

### 1. User Stories 

* User can login/register 
* User can post their good deeds done, request help to perform good deeds in the form of events, post about what others helped them
* User can see feed of user good deeds in "Home" tab
    * mix of events and posts on main timeline
    * Posts:
        * location
        * date posted
        * description
        * like button
        * (optional) image
    * Events:
        * location
        * title
        * date posted
        * description
        * like button
        * bookmark button
        * (optional) image
* User can access "Profile" tab 
    * swap between viewing own posts and viewing bookmarked posts
* User can see just events in "Events" tab
    * automatically filtered to events within 10 miles of the user
    * can apply own filters to events
* User can see their "Circle of Influence" in the "Maps" tab
    * displays pins showing location of all events posted
* User can log out




### 2. Screen Archetypes
* Login/Register
   * User can register an account
   * User can log into an existing account
* Profile
   * User can see their past deeds
   * User can see their bookmarked events
* Timeline
    * User can see deeds from other users
* Create Post 
    * User can write a caption about their deed
    * User can upload photos, use camera to snap photos
    * User can drop a pin/location tag 
* Good Deed Tracker (Using maps)
    * User map drops pins at site of posted good deeds 
    * User can see past pins
* Events Tab
    * User can see all events posted and apply filters


### 3. Navigation

**Tab Navigation** (Tab to Screen)

* "Profile"
* "Timeline"
* "Map"
* "Search Events"
* "Create Post" - Taskbar button

**Flow Navigation** (Screen to Screen)

* Login/Register
   * Can register for account - takes to timeline
   * Can login to account - takes to timeline
* Timeline Screen
   * Can switch to other screens through tabs/buttons (listed above)

## Wireframes 
<img src="" width=600>


## Schema 

### Models
#### User

| Column | Type | 
| -------- | -------- |
| First Name     | String     | 
| Last Name     | String     | 
| Username    | String     | 
| Password     | String     | 
| Email     | String     | 
| Profile Photo    | File     | 
| Bookmarks    | Array     | 

#### Post

| Column | Type |
| ------ | --------|
| User | Pointer |
| Image* | File | 
| Title* | String | 
| Description | String |
| Likes | Array |
| Location | GeoPoint |
| Date* | String | 
| Time* | String | 
| IsEvent | Boolean |

*can be null


### Networking

#### Timeline Fragment
* Read/GET
    * get posts from database to display
* Update/PUT    
    * add current user to array of likes when a post is liked
    * add current user and string to array of comments when a comment is created
* Delete
    * remove current user from array of likes when a post is unliked
    * remove current user and string from array of comments when comment is deleted

#### Profile Fragment
* Read/GET
    * get user info from database
    * get all user's posts from database
* Delete
    * delete a post from database

#### Compose Fragment
* Create/POST
    * create a new post and add it to the database
* Read/GET
    * get the current user to attach to post

#### Map Fragment
* Read/GET
    * get all posts from current user
    * get the locations attached to the current user's posts
    * get images linked to each post
    * get description linked to each post
    
#### Events Fragment
* Read/GET
    * get all events nearby
    * get the locations attached to each event
    * get images linked to each event
    * get description linked to each event
    
#### Log In Activity
* Read/GET
    * get user based off of log in information
    
#### Register Activity
* Create/POST
    * create a new user and add them to the database
