# Chess
A chess game with the user login, game history and simple ranking system. A match is played on the same device. At the end of a game an arduino board will mark it's presence, just make sure you plug one. Created with my friends for a university project. Code written in Java (using JavaFX).  
As you can guess not everything here is my work - my part will be descriped below.  
UI made in polish.

### How to run
1. Add the jar files from the /lib folder as libraries.
2. Connect a database. The needed sql file named "szachy.sql" is in the main folder. The class responsible for the database is named "BazaDanych". If you use other tool than MySQL or want to change port etc., take a look at the fields named DB_URL, DB_USER and DB_PASSWORD.
3. Compile the "HelloApplication.java" file. Log in with accounts (the left/right panel for each player). You can enter logins and passwords of the already created users or register ones yourself. 

### Additional description
For now when check occurs and a king has no way of rescuing himself, it can be captured. Afterwards a game ends. A checkmate detector will be added later.

### My part of work
I created a logic behind the game. Like settting up the board, showing possible movements. Here are the details:  
1. Majority of the Board.java (except last function named "zwyciezca").
2. Every class for each figure (Rook, Queen, Pawn etc.).
3. The Square class
