# Chess
A chess game with user login, game history and simple ranking system. Match is played on the same device. At the end of a game arduino board will mark it's presence, just make sure you plug one. Created with my friends for a university project. Code written in Java (using JavaFX).  
As you can guess not everything here is my work - my part will be descriped below.  
UI made in polish.

### How to run
1. Add jar files from the /lib folder as libraries.
2. Connect a database. A needed sql file named "szachy.sql" is in the main folder. Class responsible for database named "BazaDanych". If you use other tool than MySQL, want to change port etc. take a look at the fields named DB_URL, DB_USER and DB_PASSWORD.
3. Compile a "HelloApplication.java" file. Log in with accounts (left and right panel for each player). You can enter logins and passwords of already created users or register ones yourself. 

### Additional description
For now, when check happens and a king has no way of rescuing himself it can be captured. Afterwards game ends. Checkmate detector will be added later.

### My part of work
I created a logic behind the game. Like settting up the board, showing possible movements. Here are the details:  
1. Majority of the Board.java (except last function named "zwyciezca").
2. Initiated ChessboardController.java to make it able to control game (only that). Elements of database (name of players, ranking), surrender/draw buttons and timer were appended later by my friends.
3. Every class for each figure (Rook, Queen, Pawn etc.).
4. Square class
