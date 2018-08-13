# Final Report of Mifos Chatbot project

## Overview
From the overview & objectives of this project, I got to know that this Chatbot will allow user and Fineract platform to interact directly. <br>
Hence, my work can be divided into these phases.

1. Basic-structure
2. OpenNLP implementation
3. RESTful API call
4. API handler building
5. Slack service integration
6. Integration & Add unit tests to guarantee stability

According to the description of the project idea:

> Main components needed are: <br>
>1. NLU (natural language understanding) /NLP (natural language processing) <br>
This component is probably a good candidate for integrating a suitable existing OS project. <br><br>
>2. Chat platform and/or protocols <br>
To establish the communication between user and the bot logic the project could either leverage (at least) one of the major chat platforms (e. g. Facebook messenger etc.) and/or use open source protocols like XMPP or IRC. It's probably best to evaluate existing chat frameworks/client libraries <br><br>
>3. Fineract adapter <br>
This is the part where most of the student's attention is needed (see use cases below). The student would have to evaluate to which extent a chatbot framework could be integrated or if there are better arguments to develop something Mifos specific.

Hence, for the final product, it has multiple modules that cover different functions of the Chatbot. Different modules integrates to build the whole workflow.

It contains the following modules:
1. adapter
2. client
3. core
4. nlp
5. protocol
6. server

## Setup

The setup process of this project is quite concise.

Dependencies needed:
1. Java 1.8 <br>
For your information, this project requires the Java version to be exactly `1.8`. If you have both Java 10 and Java 1.8 together on your operating system, you can use the bash command export JAVA_HOME=`/usr/libexec/java_home -v 1.8` to change Java version 1.8.

2. Gradle <br>
Use Gradle to solve the dependency management problem.

3. IDE ==> IntelliJ or Eclipse <br>
These two IDEs provide a lot of useful plugins to facilitate the coding process. For example, `Lombok plugin` is a kind of plugin that injects the instance of logging method into usage.

After setting up the dependencies, you are ready to play with it.

## Play with Chatbot 

If you want to play with it as a user, then you should get an account in Slack `Mifos` workspace first. Within this workspace, you search for an app called `chatbot`. Then you can type in your query and wait for response. Remember that the query should within the scope of [project requirements](https://mifosforge.jira.com/wiki/spaces/RES/pages/225411076/Google+Summer+of+Code+2018+Ideas#GoogleSummerofCode2018Ideas-Mifos/FineractChatbot&Adapter).
> Tips: Currently this project has not been deployed to a server, which means I only run it from my local machine. Hence, this application will not run all the time, the function of always responding to queries will be achieved when this project is hosted on a web server.

However, if you want to play with it as a developer.

1. You can try out the unit tests in every modules to understand more about functions of these modules.

2. You have to apply for a Slack bot user account, which means create you own bot user in Slack Application page. You Slack bot is distinguished by a token given by Slack. You have to place the token into `application.properties` and configure it properly.

3. You can run this application with Gradle commands. You navigate your terminal to server package. After that, you can execute the command `server:BootRun` to start your own Chatbot.

## Working period

#### Starting to First Evaluation
During this period, I finished the work mainly about exploration about the Chatbot and OpenNLP structure.
> It would be valuable to provide an extensible chatbot connected to Mifos/Fineract that could be used to both provide customer support as well as allow clients to directly interact with their accounts.

* At the initial period, I did some exploration about the requirements of this project. According to the [requirement page](https://mifosforge.jira.com/wiki/spaces/RES/pages/225411076/Google+Summer+of+Code+2018+Ideas#GoogleSummerofCode2018Ideas-Mifos/FineractChatbot&Adapter),
<br><br> 1. From above, we can find out that the Chatbot will allow user to interact with Fineract directly through some interface instead of visiting the mifos page, which will save much time. Hence, from technical perspective of this project, I understand that this project will build a client to receive data from Fineract and return received data to users.
<br><br> 2. On the other side, the Chatbot will receive users' input and parse user input text to get user's intent. This Chatbot is flexible enough for users to input in natural language instead of fixed commands. From there, I find out that the Chatbot is actually an adapter to build the connection between Fineract and user. Hence, from the technical perspective, it requires some Natural Language Processing techniques to parse user's input.
<br><br>3. Furthermore, this Chatbot should contain an interface for user to input their query. There are two options for me, either user pre-existing interface like Slack or Facebook messenger or build from scratch. I choose to use Slack bot user instead of building from scratch because of the maturity of technology of Slack bot and time constraints.

* After understanding the requirements of Mifos Chatbot, I start building the basic structure of this project. I worked quite long time to build the infrastructure and chose the technology stack. After consideration and caring of the requirements, I chose to build a Spring Boot application with OpenNLP, Fineract API client and Slack interface. From here, I can build the basic architecture of this project, which contains six modules. They are `adapter`, `client`, `core`, `nlp`, `protocol`, `server` modules.

* For illustration, `client` module contains the Java client of Fineract APIs, `adapter` module is used for handling the APIs of Fineract like generating the request and response of APIs, `core` module contains the data holder and primary interfaces of this project, `nlp` module contains the OpenNLP engine, `protocol` is used as the Slack interface and `server` module is the module to start the Spring application. Further illustration is provided below.

* The first module that I worked on is `nlp` module. I use some models provided by OpenNLP official website and also models that I trained by myself. Basically, the module will has a class of `OpenNLPService` that will provide a series of functions that parse user's raw input to specific entities.

#### First Evaluation to Second Evaluation

During this period, what I did was mainly modifying the overall architecture including separating different modules, polishing the unit tests of `nlp` module to guarantee the stability of functions and generating the Java clients of Fineract.

* The first thing done in this period is separating different modules and finalise the six-module structure. This structure ensures that `core` module take control of the interfaces and other modules conduct the implementation. In this time, `core` module contains an interface of OpenNLP. If other modules would like to use service of `nlp` module, they only need to query the interface from `core` module and Spring will help them handle the injection of dependency.

* Other things done in this period are unit tests of `nlp` modules and generating the Java clients of Fineract. From the unit tests of `nlp` modules, I found some minor bugs of my OpenNLP service because the model trained by me is not stable enough to care about all the corner cases. According to the [requirements page](https://mifosforge.jira.com/wiki/spaces/RES/pages/225411076/Google+Summer+of+Code+2018+Ideas#GoogleSummerofCode2018Ideas-Mifos/FineractChatbot&Adapter) and [Fineract API page](https://app.swaggerhub.com/apis/mifosChatbot/apache-fineract_api_documentation/1.0.0), the clients of those APIs are needed. Thanks for [Sanyam]'s help, he provided me a live API documentation of Fineract written in Swagger OpenAPI specs and I used that documentation to generate my Java client code. After that, I did some modification of my client to adapt the package of client to `client` package and update the dependency information.

* At the end of this period, I can make use of the client code and test them in the unit tests to see whether they can connect to the [demo instance of Fineract](https://demo.openmf.org/) and fetch the data back. Another attempt was to build my own local instance of Fineract. From this attempt I learnt a lot about the Spring configuration issues.

#### Second Evaluation to Final Evaluation
This is the last period of my Google Summer of Code internship. After completing the first two periods' work, the basic workflow of Chatbot can be achieved by imitating the user's input. Hence, my work focused on completing the whole workflow and building the handlers of APIs.

* The handlers of Fineract APIs will be put in the `handler` module. For every specific request in the project idea page, we have to build a handler to handle the request and wrap the response of the API to fit it into the data structure of my project. I also implemented a filter to filter out the corresponding handler by checking whether the user intents contains specific keywords.

* Another thing I build is the Slack interface used for user input. In this interface, user can have conversations with Chatbot to query different kinds of data like the due of the interest and loan status. For the Slack bot, it will require user to input their username and keyword to authenticate user for further operations.

* Another task is to build the integration of all modules after completing all the implementation of functions. The basic workflow can be illustrated in steps:
<br><br> 1. User inputs the request that they want to know about Fineract financial products like loans, savings or loan products through Slack interface. Slack bot will pass user input to OpenNLP engine for further operations
<br><br> 2. OpenNLP engine will parse the user input to extract user intents to find out what entity they want to query from Fineract. Then they will pass the intents to API handlers to get the response back.
<br><br> 3. After getting the response from API handlers, the controller will put the response back to Slack interface to send back to users. Users may put several requests into one Slack message.
<br><br> After completing one cycle of the workflow, user will get the information they want. If they don't get the correct information, they will receive the error message instead.

* Then another work is to add the unit tests and the documentation of this project to let other developers who would like to contribute to this project give their contribution to this project.  

## Function Illustration

#### Protocol -- Slack service

This is the interface module -- `protocol`. `SlackChatService` class checks the authentication of users who want to use this service. There is a pair of username & password stored in the configuration file of this project, which is the `application.properties` in `server` module. This class will check whether username & password typed by user fits that in configuration file. If not authenticated correctly, it will return error message.

The Slack service implements `Callback` interface, which handles the function of responding results get from `adapter` to users through the same Slack interface.

#### NLP engine -- OpenNLP

This is a very important module of Chatbot which distinguishes this Chatbot with other normal chatbots that require user to input specific command to trigger the response. Instead, it will can accept natural language as input. This implementation will make it convenient for users to interact with Chatbot because they do not need to memorise all the complicated commands.

This function stays in `nlp` module. It has series of sub-functions to achieve the purpose of extracting the user's intent from user raw input. Basically, the sub-functions include:  
1. sentence detector
2. tokeniser
3. name finder / other entity finders

The first two functions are achieved by provided models from [OpenNLP official website](http://opennlp.sourceforge.net/models-1.5/), the third function is achieved by self-trained model. Sentence detector will divide raw sentence to several sentences. For every sentence it will process it and parse it to several tokens. After that, name finder will utilise the model to divide tokens to several spans which represent several entities. For example, if the user inputs "Check my loan status. Check my due interest". The processing procedure will be like that:
1. Sentence detector detects that there are two sentences.
<br> "Check my loan status. Check my due interest" ==> ["Check my loan status", "Check my due interest"]

2. For every sentence, tokenizer will parse it to tokens.
<br> "Check my loan status" ==> ["Check", "my", "loan", "status"]
<br> "Check my due interest" ==> ["Check", "my", "due", "interest"]

3. The name finder will find the entities from the tokens.
<br> ["Check", "my", "loan", "status"] ==> "loan status"
<br> ["Check", "my", "due", "interest"] ==> "due interest"

Then the OpenNLP engine will pass the results to API handlers.

#### Adapter -- Swagger code generation

The `client` module contains the Java client of Fineract APIs that will be queried in this Chatbot. Then the `handler` module contains the handler of those Java client of APIs. Specifically speaking, the `client` module contains APIs like `LoansApi`, `CurrencyApi` and `SavingsApi`. Those Java clients of APIs can function like live API of Fineract. Those APIs are generated by the Swagger code generation.

The `handler` module contains adapter between Fineract API executors and `nlp` module. For every handler of the Fineract request, it will contain a function to check which intent it should handle and another function which executes the corresponding Fineract API and returns the selected field from the JSON response of the API request.

For example, if the user want to query his/her due interest, after the processing of OpenNLP engine, the input has been processed as a series of spans, then Intent ["due interest"] is available for the processing in the `handler` module.

1. In the `MifosChatbotAdapterService` class, the handler will implement the stream filter to locate the corresponding handler to handle the request. During the selection proess, `DueInterestHandler` is selected to be the handler.

2. In the handler, it will execute the `handle` function, which executes the `LoansApi` to query information about Loans. In the technical aspect, it executes the `retrieveLoan` method to retrieve information.

3. After that, the function will select the field named `InterestOverDue` and pass it back to Slack interface.

#### Controller - Server Module

The controller of this project stays in `server` module. This module controls the whole logic of this project. The workflow mentioned above is in the `server` module and the controller will care about the configuration details and dependency injection.

The basic workflow is `Slack` service ==> `NLP` ==> `AdapterService` ==> `Slack`. After completing one work cycle, the user will get the response back from this Chatbot.

There is another module inside the system called `core`. This module controls the interfaces of this project. For example, if `server` module want to use service from other modules, it will call the service from `core` module to let Spring help it handle the dependency injection to avoid over coupling.

## Future Improvements

There are still some future improvements that you can make if you are interested about this project. 

1. Authentication part: Currently the authentication is quite basic, it only checks user input with pre-defined username & password. Two factor authentication is preferable for financial information application. 

2. Notification: Currently the project can only send response back to users after they ask questions. The ideal state is that the Chatbot can push notifications to users even they are not focusing on Chatbot and asking questions to Chatbot. 

3. The coding style can be more concise.  

## Contribution later

You can refer to developer part of section #Play with Chatbot, you can make some changes and test your changes by running the application. 

## Appreciation

I would like to express my thanks to those who helped me during the project. Thanks Mr Ed Cable for answering my questions patiently and giving me useful advice during weekly check-ins. Thanks for Sanyam for providing me the complete version of Fineract API documentation and helping me through the process of discovering the JSON file of Fineract API. Without his help, I cannot find the updated version of Fineract API.

Then I would like to express my especial thankfulness to my mentor Mr Aleks. I received a lot of help during the whole process of Google Summer of Code. Whenever I got into trouble, He would always provide useful help to me.

