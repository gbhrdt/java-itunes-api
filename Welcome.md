# Introduction #

This project enables iTunes control from Java.

# Goal #

The goal is to provide a jar that you can include in your project. Using the jar classes should be the only requirement to get up and running and start programming against iTunes.

# Implementation #

The library uses Java scripting engine support. iTunes can be controlled via sripting languages on OSX. For Java there is the Jasconn project, which enables you to control OSX applications that support scripting access.

This projects is an abstraction layer, so you can use objects and methods to access iTunes instead of using applescript.

The Jasconn project is created by Thomas Künneth and can be found here https://jasconn.dev.java.net/
Thomas has given good support when I ran into troubles.

# How to get and build the source code #

Download Eclipse (I used 3.4.2 Ganymede for Mac carbon)
Extract Eclipse

Start Eclipse

Use your farourite svn client, if you have none I suggest:
Install Subclipse see http://subclipse.tigris.org/install.html

Configure svn to check out from:

svn checkout http://java-itunes-api.googlecode.com/svn/trunk/ java-itunes-api-read-only

OSX Currently supports JRE 1.6. You have to add it in Eclipse.
Add a new JRE in Eclipse preferences:

Mac OSX VM:
Name: JVM 1.6
/System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home