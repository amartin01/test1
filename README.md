Mate1 Hyperqueue test
=====

The broker is made of servlets meant to be deployed in the latest tomcat.

There are 2 servlets:
brokerServlet mapped to "/messages": This is for handling posting and reading the message queues
topicServlet mapped to "/topics": Tis is for listing and adding topics

The producer and consumer are jquery-based


Install
==

Everything installs as 1 webapp in tomcat: use your favorite method to deploy "broker.war" to a recent tomcat.
Tested with tomcat 7.0.52

The default url to the app will be http://localhost:8080/broker

producer.html and consumer.html are available from the homepage


