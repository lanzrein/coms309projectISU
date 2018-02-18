<?php
include 'database_helper.php';
/**
 * Created by PhpStorm.
 * User: johan
 * Date: 13.10.2017
 * Time: 17:02
 */
$playerID = $_GET["playerID"];
$newMessage = $_GET["newMessage"];
if(empty($newMessage)){
    die("ERR");
}

$conn = connectToDB();

$sql = "update Player p set p.Message = $newMessage 
        where p.playerID = $playerID";

if($conn->query($sql)){
    echo $newMessage;
}else{
    echo "ERR";
}

$conn->close();