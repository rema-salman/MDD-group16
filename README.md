# MDD-group16

This repository provides the source code for the ROVU system implemented by group 16.

## Table of Contents
* [Introduction](#introduction)
* [Organization](#organization)
* [Getting Started](#getting-started)
* [REQUIREMENTS](#requirements)
* [Cloning](#cloning)
* [Running](#running)

## Introduction
The ROVU system contains rovers, robots, that autonomously move within a designated environment. An interface allows an operator to monitor the environmental status that rovers perform their missions in, and display the positions of the robots and reward points. The missions performed by the rovers may change over time according to the environment, where each mission is composed of strategies that follow sequential points. The rovers use their proximity sensors to avoid environmental obstacles or running into each other. Furthermore, they communicate through a central station or with each other, as a possible part of the obstacle avoidance.

## Organization
This repository is divided into three main folders as the followed archituctural style:

**1. Model:** Where the code for the systems components(objects) are stored. 

**2. View:** On this folder all the code used for the graphical user interfaces can be found.

**3. Controller:** Where all the logical source code is available, including links to other repositories can be found in the classes headers if any is used.

## Getting Started
These instructions will get you a copy of the project up and run on your local machine for development and testing purposes.
             
## REQUIREMENTS
needs to be added 

- `Java Development Kit 1.8` (or newer) should be correctly installed in your machine
- You need to convert the project's configuration into a `maven project`.
- You'll also need the Simbad simulator dependencies that exists in the `pom.xml file`.

### Cloning          
   The project can be cloned using the URL provided in the GitHub webpage of the project or you can clone the project by adding the following command on your terminal/bash:
   
    ```
    git clone --recurse-submodules https://github.com/remasalm/MDD-group16.git
                     
    ```

 ### Running# MDD-group16
You can run the project as a normal java project from the `Main.java` class
