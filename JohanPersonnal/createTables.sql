create table Player(
PlayerID int not null auto_increment primary key,
username char(10),
password char(64),
experience float, 
numberWins int, 
numberLoss int, 
numberReports int,
imageID int, 
message char(255));

create table Map(
mapID int not null auto_increment primary key, 
mapName char(30),
mapDescription char(255) , 
Author int references Player(PlayerID),
Data varchar(8000)
);



create table Friends(
PlayerID int primary key references Player(PlayerID),
Friends varchar(6000));


create table RequestsTable(
PlayerID int not null primary key references Player(PlayerID) ,
AdversaryID int not null references Player(PlayerID),
gamestate varchar(6000),
flag int);


create table Messages(
messageID int not null primary key auto_increment,
fromID int not null references Player(PlayerID),
toID int not null references Player(PlayerID),
message varchar(300));


create table Games
(GameID int not null primary key auto_increment,
gamemode int,
leaderID int not null references Player(PlayerID),
leaderUsername char(10) not null references Player(Username),
oppID int references Player(PlayerID),
oppUsername char(10) not null references Player(Username));


create table GameLobby(
GameID int not null primary key, 
gamemode int, 
race1 int, race2 int, 
leaderID int, oppID int, 
mapID int);

-- DO NOT EXECUTE THOSE 2 LINES ONLY FOR TESTING PURPOSE
/*drop tables Player;
drop tables Map;*/
-- drop tables Player;

select * from Messages;
select * from Friends;
select * from Player;
select * from Games;
select * from GameLobby;
select * from Map;
select * from RequestsTable;

