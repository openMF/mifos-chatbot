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
5. Slack/Telegram/Facebook account

And for NLU, you are required to have
1. Python 3.6.1 (as NOW Rasa NLU is having dependency issues with Python 3.7)
2. Microsoft VC++ Compiler
3. Rasa (for offline training and testing models)

```
# install Rasa NLU using these commands

pip install rasa[spacy]
python -m spacy download en_core_web_md
python -m spacy link en_core_web_md en
```

## Getting started

### Setting up
##### Java:
There are two options. 
1. Download Java 1.8 from [official website](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), install it and configure it on class path
2. If you have `brew` installed in your system, you can also download from homebrew. `brew cask install java8` to install Java 8 on Mac OS. 

##### MySQL:
1. Download from [official website](https://dev.mysql.com/downloads/mysql/), install it and configure it. During installation, if the user does not set the initial password, then the initial password should be set in the System Preferences -> MySQL -> set initial password. Then configure mysql in the classpath. 
2. If you want to use Cloud Service for database, you are free to do that. Remember to update `application.properties` file. 

##### Gradle:
Download Gradle from [official website](https://gradle.org/install/). 

Now we need a platform to run your chatbot, we have integration with Slack, Telegram and Facebook messenger (atleast one is required to setup).
##### Slack:
1. Deploy your own Slack bot, apply from [here](https://api.slack.com/apps?new_app=1).
2. Get `Bot User OAuth Access Token` from OAuth & Permission.
3. Set token to environment variable with name `MIFOS_SLACK_API_TOKEN`.

Now you are all setup to test bot on Slack.
##### Telegram:
1. Search for the “botfather” telegram bot on either [web.telegram.org](https://web.telegram.org/) or on telegram app.
2. Create bot using `/newbot` command.
3. Set username of bot to name `TELEGRAMBOT_USERNAME` and token to `TELEGRAMBOT_TOKEN` environment variables.

Now you are all setup to test bot on telegram.
##### Facebook Messenger:
1. Create a facebook page (here we will deploy our bot) and set page access token to `MESSENGER_PAGE_ACCESS_TOKEN` environment variable.
2. Create facebook app from [here](https://developers.facebook.com/).
3. Set messenger app token to `MESSENGER_APP_SECRET` and verify token (will be used to setup callback) to `MESSENGER_VERIFY_TOKEN` environment variables.
4. Set callback url to endpoint where chatbot is deployed and messenger verify token on Webhooks section (by clicking on "Edit Subscription" button).

Now you are all setup to test bot on Facebook Messenger.
### Training your NLU own model
Use the following command to train Rasa NLU model. Make sure you have prerequisites for Rasa and Rasa is installed on your local system. 
```
rasa train nlu
```
Remember to use following structure for Rasa, else provide training data and other configuration files manually.
###### Rasa structure
```
mifos-chatbot\nlp\src\main\resources\rasa/
|-- data/
|    |
|    |-- nlu.md
|
|-- models
|    |
|    |-- nlu-XXXXXXXX-XXXXXX.tar.gz
|
|-- config.yml
``` 

### Deploying and testing your own model
Use the following command to run Rasa NLU model locally. 
```
# Start a server with your NLU model as web service
rasa run --enable-api -m models/nlu-XXXXXXXX-XXXXXX.tar.gz
# Test your model on shell 
rasa shell -m models/nlu-XXXXXXXX-XXXXXX.tar.gz
```

### Playing with Fineract

As you may know, the Chatbot project will query information from Fineract. Hence, we are supposed to have a public instance or a local instance of Fineract running on Tomcat server.

For deploying your own instance of Fineract to local Tomcat server, follow the steps of [installing Mifos on local machine] to generate the war file and put it under the webapp folder of Tomcat.

Set up database using mysql-first-time.sql to populate sample data into local database

For the public instance, [open demo org] is available for use. One minor issue about this instance is that the authority level is that everyone involved in Fineract related project can change it. Hence, if someone push the code with bugs to the server, then it may cause errors.

### Playing with chatbot

Fork and clone chatbot project, do a gradle import and run the unit tests in those modules to understand the function of each module.<br>

Run chatbot and type your queries on respective chat platform. For example, you can type `Check my arrear day`, `Check my due interest`, `Check my next due date`. Then you will receive response from the Mifos chatbot, like "478 days" as the response of `arrear day`, "1150.0" as the response of `due interest` and `Sat Apr 29 00:00:00 SGT 2017` as the response of `next due date`. 

Remember to configure application.properties in server package.

The sample application properties file is like that:
```
server.port="port where chatbot will run"
mifos.apiUrl="fineract endpoint"
mifos.slackApiToken=${MIFOS_SLACK_API_TOKEN}
mifos.username=${MIFOS_USERNAME}
mifos.password=${MIFOS_PASSWORD}
messenger4j.appSecret=${MESSENGER_APP_SECRET}
messenger4j.verifyToken=${MESSENGER_VERIFY_TOKEN}
messenger4j.pageAccessToken=${MESSENGER_PAGE_ACCESS_TOKEN}
telegram.botUsername=${TELEGRAMBOT_USERNAME}
telegram.botToken=${TELEGRAMBOT_TOKEN}
developer.metadata="developer defined Metadata"
# configured for mysql
spring.jpa.hibernate.ddl-auto=none
spring.datasource.driverClassName = com.mysql.jdbc.Driver
spring.datasource.url = "database url"
spring.datasource.username = "database username"
spring.datasource.password = "database password"
rasa.nlu.service.url = "Rasa NLU model endpoint"
logging.level.org.springframework=DEBUG
```

Run `./gradlew server:bootRun` to startup the application and input text in the Slack interface.

## Authors
- [Zhao Dingfan](https://github.com/ZhaoDingfan) (GSoC 2018)
- [Anshul Singh](https://github/com/iosdev747) (GSoC 2019)

## Mentor
- Aleksandar

## License
This project is licensed under Apache License Version 2.0.

## Acknowledgements
Sincere appreciation to Mr Aleksandar Vidakovic's and Sanyam's help
