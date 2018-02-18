<?php
include '../database_helper.php';
include '../json_helper/User.php';
/**
 * This files provides a script to add a friend to your friends list.
 * need to provide : username and the user id of the player you want to add.
 */

$username = $_GET['username'];
$password = $_GET['password'];

$friendID = $_GET['friendID'];

if(empty($username) || empty($password) || empty($friendID)){
    die('ERR : provide a correct combination.');
}
$conn = connectToDB();

//got a connection we need to retrieve the id of the player.
//then from the id go ahead and update the friends data.
$sql = "update Friends f
set f.Friends = concat(f.Friends,\"$friendID,\")
where f.PlayerID in (
Select p.PlayerID from Player p 
where p.username = \"$username\" and p.password = \"$password\"
);";

if($conn->query($sql)){
    echo "Add successful ! ";
}else{
    echo "ERROR";
}

$conn->close();