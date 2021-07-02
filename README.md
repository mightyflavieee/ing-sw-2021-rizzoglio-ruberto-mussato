# Software Engineering Project
### *Masters Of Renaissance*
##### Flavio Rizzoglio, Gianluca Ruberto, Leonardo Mussato
###### Anno 2020/2021

## 1. Functions implemented
The following functionalities have been implemented:

![alt text](https://github.com/mightyflavieee/ing-sw-2021-rizzoglio-ruberto-mussato/blob/master/deliverables/readme_images/implemented_functions.PNG)

### 1.1 Additional functionalities
The additional functionalities chosen and implemented are:
1. Multiple games;
2. Persistence;
3. Resilience to disconnections.

## 2. Instructions for running the Client
### 2.1 CLI
To run the CLI version, open the terminal and type:
`java -jar MastersOfRenaissance.jar cli`

Once you have run the command, select your nickname. Now, indicate whether you want to create or join a game, using one of these two commands:
1. Creation: `create`;
2. Join: `join`.

If you selected the first option, select the number of players you wish to play with. Otherwise, if you selected the second option, enter the id of the game in which you wish to play. 
Wait for the other players (if any) and start playing!

### 2.2 GUI
There are two ways to run the GUI version:
1. Through the CLI: enter the following command `java -jar MastersOfRenaissance.jar gui`;
2. Through the .jar file: click on the file!

## 3. Server
The server's .jar file is hosted on a Linux machine (EC2 of Amazon AWS) and is permanently active. Anyone in possession of the client-side .jar file can automatically connect to the server.

The server's .jar file has however been included in the folder `/deliverables`. 

## 4. Coverage
The coverage of the Model is about 80%. The untested methods are the various getters, setters and toString functions.

![alt text](https://github.com/mightyflavieee/ing-sw-2021-rizzoglio-ruberto-mussato/blob/master/deliverables/readme_images/coverage_pt1.PNG)
![alt text](https://github.com/mightyflavieee/ing-sw-2021-rizzoglio-ruberto-mussato/blob/master/deliverables/readme_images/coverage_pt2.PNG)
