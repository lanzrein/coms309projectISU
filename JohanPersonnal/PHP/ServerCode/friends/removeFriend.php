<?php
/**
 * This files provides a script to remove a friend from your friends list.
 */
include '../database_helper.php';
include '../json_helper/User.php';

$username = $_GET['username'];
$password = $_GET['password'];

$friendID = $_GET['friendID'];

if(empty($username) || empty($password) || empty($friendID)){
    die('ERR : provide a correct combination.');
}
$conn = connectToDB();


$sql = "Select f.friends from Friends f where f.PlayerID in 
( Select p.PlayerID from Player p 
where p.username = \"$username\" and p.password = \"$password\"
)";

$result = $conn->query($sql);

if($result->num_rows > 0){
    //we now try to remove it -
    $row = $result->fetch_assoc();
    $friends = $row["friends"];
    //its a string so we need to split it where the id is and then concat those two parts.
    $array = preg_split("/[,]+/", $friends, 0, PREG_SPLIT_NO_EMPTY);
    $str = "";
    for($i = 0; $i < count($array); $i++){

        if($array[$i] != $friendID){
            $str.=$array[$i];
            $str.=",";
        }

    }
    //now we finally got the string that will be the new friends list..
    //then from the id go ahead and update the friends data.
    $sql = "update Friends f
    set f.Friends = \"$str\"
    where f.PlayerID in (
    Select p.PlayerID from Player p 
    where p.username = \"$username\" and p.password = \"$password\"
    );";

    if($conn->query($sql)){
        echo "Remove successful";

    }else{
        echo "Error";
    }
}else{
    echo "ERROR";
}

$conn->close();