# mifos-chatbot
Chatbot and adapter to Apache Fineract

## Objectives
This project is about to build a Chatbot by implementing Artificial Intelligence technology, specifically Natural Language Processing, to make users understand their financial conditions conveniently through Chatbot interface.

## Prerequisites
If you want to contribute to this project, there are several things that you have to prepare on your local environment.
1. Access to Internet, which is necessary to see this README
2. Exactly Java 1.8 
3. MySQL & Tomcat
4. Gradle
5. Slack account

## Getting started

### Installing on Mac OS
Java: <br>
There are two options. 
1. Download Java 1.8 from [official website](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), install it and configure it on class path
2. If you have `brew` installed in your system, you can also download from homebrew. `brew cask install java8` to install Java 8 on Mac OS. 

MySQL:
1. Download from [official website](https://dev.mysql.com/downloads/mysql/), install it and configure it. During installation, if the user does not set the initial password, then the initial password should be set in the System Preferences -> MySQL -> set initial password. Then configure mysql in the classpath. 

2. If you want to use Cloud Service instead of local installation of database, you can use Docker to create a virtual database. 

Gradle <br>
Download Gradle from [official website](https://gradle.org/install/). 

Slack
1. Join Mifos workspace and search for `chatbot` in the application page to access service. 
2. If you want to have your own Slack bot, apply from [here](https://api.slack.com/apps?new_app=1). 

### Playing with chatbot
After forking and cloning chatbot project , do a gradle import run the unit tests in those modules to understand the function of each module.<br>

Key in text in the chatbot and read the response to understand more.

For example, you can type `Check my arrear day`, `Check my due interest`, `Check my next due date` in the Slack interface. Then you will receive response from the Slack bot, like "478 days" as the response of `arrear day`, "1150.0" as the response of `due interest` and `Sat Apr 29 00:00:00 SGT 2017` as the response of `next due date`. 

### Playing with Fineract and Chatbot

As you may know, the Chatbot project will query information from Fineract. Hence, we are supposed to have a public instance or a local instance of Fineract running on Tomcat server.

For deploying your own instance of Fineract to local Tomcat server, follow the steps of [installing Mifos on local machine] to generate the war file and put it under the webapp folder of Tomcat.

Set up database using mysql-first-time.sql to populate sample data into local database

For the public instance, [open demo org] is available for use. One minor issue about this instance is that the authority level is that everyone involved in Fineract related project can change it. Hence, if someone push the code with bugs to the server, then it may cause errors.

After settling down issues about Fineract, configure mifos.url in application.properties in server package. Apply for your own bot user in the application page of Slack and put the token in application.properties file also.

The sample application properties file is like that:
```
server.port=10000

mifos.apiUrl=https://demo.openmf.org/fineract-provider/api/v1

#Slack
mifos.slackApiToken=xoxb-15186592304-405342609110-7PHj7JTsvcQ3wlGrGb8XLMjW

mifos.username=mifos
mifos.password=password
```

Run `./gradlew server:bootRun` to startup the application and input text in the Slack interface.

## Authors
[Zhao Dingfan](https://github.com/ZhaoDingfan)

## Mentor
Aleksandar

## License
This project is licensed under Apache License Version 2.0.

## Acknowledgements
Sincere appreciation to Mr Aleksandar Vidakovic's and Sanyam's help
