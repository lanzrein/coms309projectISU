-- This file is just for testing purpose. 

insert into Player(username,password,experience, 
numberWins , 
numberLoss , 
numberReports ,imageID,
message )
values("Johan","1234",1200,10,10,20,1,"hello");
select * from Player;

insert into Player(username,password,experience, 
numberWins , 
numberLoss , 
numberReports , imageID,
message )
values("Alex","abcd",1500,15,12,24,1,"goodbye");


select * from Player; -- where username = "johan" and password = "1234";
insert into Friends(PlayerID, Friends) values(2,"");

Select * from Player;
Select * from Friends;
delete from Player
where PlayerID > 2;

select p.PlayerID, f.Friends
from Player p join Friends f
on p.PlayerID = f.PlayerID;

select p.numberWins, p.username
from Player p
order by numberWins desc;


select * from RequestsTable;
select r.gamestate from RequestsTable r where r.PlayerID = 1 and r.AdversaryID = 2;
delete from RequestsTable where PlayerID < 100;

update RequestsTable
set gamestate = '{"ready":false,"x":800,"y":800}'
where playerID = 1;
insert into RequestsTable(PlayerID, AdversaryID, gamestate,flag)values(
 2, 1, '{"x":200,"y":300}',1
) on DUPLICATE  KEY UPDATE gamestate = '{"x":200,"y":300}', flag = 1;

select * from Player;


update Map 
set NumUnfairRep = 0, NumOffensiveRep = 0
where mapID > 3;


-- This are dummy values insert into Map...
insert into Map(mapName,mapDescription,Author,Data) values (
"Death valley", "This map is very deadly", 1,
"0,0,0,0,1,0,0,0,0,0
,1,1,1,1,1,0,0,0,0,2
,0,0,0,0,0,0,0,0,0,0
,1,1,1,1,1,1,0,0,0,0
,0,0,0,0,0,1,0,0,0,0
,0,0,0,3,3,1,3,3,0,0
,0,0,0,0,3,1,3,0,0,0
,0,0,0,0,0,1,0,0,0,0
,0,0,0,0,0,1,1,1,0,0
,0,0,0,0,0,0,0,1,1,1");

insert into Map(mapName,mapDescription,Author,Data) values (
"Canyon of Dwarf", "Dwarfs like it there", 2,
"0,0,0,0,1,0,0,0,0,0
,1,1,1,1,1,0,0,0,0,2
,0,0,0,0,1,0,0,0,0,0
,1,1,1,1,1,1,0,0,0,0
,0,0,0,0,0,1,0,0,0,0 
,0,0,0,0,0,1,0,0,0,0
,4,4,4,0,3,1,3,0,0,0
,4,4,4,0,0,1,0,0,0,0
,4,4,4,0,0,1,1,1,0,0
,0,0,0,0,0,0,0,1,1,1");

insert into Map(mapName,mapDescription,Author,Data) values (
"Simple road", "This map is for noobs", 3,
"0,0,0,0,1,0,0,0,0,0
,0,0,0,0,1,0,0,0,0,0
,0,0,0,0,1,0,0,0,0,0
,0,0,0,0,1,0,0,0,0,0
,0,0,0,0,1,0,0,0,0,0
,0,0,0,0,1,0,0,0,0,0
,0,0,0,0,1,0,0,0,0,0
,0,0,0,0,1,0,0,0,0,0
,0,0,0,0,1,0,0,0,0,0
,0,0,0,0,1,0,0,0,0,0");

select * from Map;
Select m.Data from Map m where m.mapID = 1;


/* Test for friends...*/
select * from Player p join Friends f
on p.PlayerID = f.PlayerID;
-- insert into Friends(PlayerID, Friends) values
-- (4,"");insert into Friends(PlayerID, Friends) values
-- (5,"");insert into Friends(PlayerID, Friends) values
-- (3,"");

Select * from Player;

Select * from Friends;


select f.Friends 
from Friends f join Player p 
on p.PlayerID = f.PlayerID;

update Friends f
set f.Friends = concat(f.Friends,"2,")
where f.PlayerID in 
(select p.PlayerID from Player p 
where p.username = "" and p.password = "";

select f.Friends from Player p join Friends f 
on p.PlayerID = f.PlayerID 
where p.username = "test" and p.password = "1234";


SELECT PlayerID, username,imageID, numberWins, numberLoss FROM Player; 



select f.Friends
        from Player p join Friends f
        on p.PlayerID = f.PlayerID
        where p.username = "hallo";
        


Select * from Player p 
where p.PlayerID = 4 and p.username = "Alex";




/***THIS IS DUMMY TEST PLAYER****/
insert into Player(username,password,experience, 
numberWins , 
numberLoss , 
numberReports ,imageID,
message )
values("test","1234",0,10,8,0,1,"hello");
select * from Player;

delete from Player where username = "test" and password = "dsadsa";



Select p.PlayerID from Player p WHERE p.username = "test" and p.password = "1234";
select Data from Map;



insert into Messages(fromID, toID, message) values (4,9,"Hey man how are you doing. ");

Select * from Messages;
select * from Player;


update Player p 
set p.password = "598d4c200461b81522a3328565c25f7c"
where p.PlayerID = 9;



insert into Map(mapName, mapDescription, Author, Data) values ("Hallo","This rocks",9,"[1, 0, 1, -3, 0, 1, -3, 0, 1, 0, 1, 1, -1, 0, 1, -5, 0, 1, 1, 0, 1, 0, 1, 1, -4, 0, 1, 0, 0, 1, -9, 0, 1, -16, 0, 1, 1, 0, 0, 1, 1, 0, 0, -4, 1, 0, -1, 1, 0, 1, -1, 0, 1, 0, 0, 1, 1, 0, 0, 1, -2, 0, 1, 1, -10, 0, 1, -3, 0, -1, 1, -3, 0, 1, -8, 0, 1, 0, 1, -1, 0, -1, 1, 0, 1, 0, 1, -1, 0, 1, 1, 0, 1, -2, 0, 1, -2, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, -2, 1, 0, 1, -2, 0, 1, -1, 0, 1, 1, 0, 1, -1, 0, 1, 0, 1, 0, 0, 1, -2, 0, 1, 1, 0, -1, 1, -1, 0, 1, -9, 0, -1, 1, 0, 0, 1, 0, 1, 1, 0, 1, -11, 0, 1, 0, 0, -1, 1, -1, 0, -2, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, -2, 0, -1, 1, -1, 0, 1, -4, 0, 1, 0, 0, 1, -1, 0, 1, -1, 0, 1, -10, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, -6, 0, 1, 0, 0, 1, -2, 0, 1, -3, 0, 1, -1, 0, 1, 0, 0, -1, 1, 0, -1, 1, -4, 0, 1, -2, 0, 1, 1, 0, 1, -2, 0, 1, 1, 0, -1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, -7, 0, 1, 1, -1, 0, 1, 1, -5, 0, 1, -1, 0, 1, 1, 0, 0, 1, -6, 0, 1, 0, 1, -1, 0, 1, 0, 1, 0, 1, 0, 0, 1, -3, 0, 1, -9, 0, 1, -1, 0, 1, 1, -1, 0, 1, -9, 0, 1, 0, 1, 0, 1, 0, 0, 1, -1, 0, 1, -2, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, -13, 0, 1, 0, 1, 0, 0]")


;insert into Games(leaderID, leaderUsername, gamemode, oppID, oppUsername) values ( 10, "hallo", 1, -1, "")