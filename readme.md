# Attack and Defend tower defense
## Note
This is simply a copy of the COMS 309 project I did with a team of other persons during my ISU exchange year. The original repo can be (maybe) found at : https://git.linux.iastate.edu/309Fall2017/VC_B_4_ProjectName
Other contributors to the code are : 
- Alex Nicklaus (nicklaus@iastate.edu)
- Jeremy Chee ( jchee@iastate.edu ) 
- Joe Ward ( jsward@iastate.edu ) 

It does not work anymore as the database or server are down. 

## Information 
This is an Android application coded in Java. The server side is coded in PHP. The database was done with MySQL. 
The game is the classic genre of tower defense with the twist : you can actually play against other players and at the same time be the defender and attacker. 
The game works mostly. I do *not* recommend to use the code as is for an actual release as the security side was overlooked mainly because of time reasons. 

### Security fixes
- In login one could implement a secure DH key exchange protocol, and have the database store public keys.
- In the database, simply sanitize every input string. 
- In the game, have the server set up a secure communication between two users. Or act as proxy and require both private keys of the user to access the game. 
