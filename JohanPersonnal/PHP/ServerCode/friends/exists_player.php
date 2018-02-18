<?php
include "../database_helper.php";
/**
 * Check if a player exists
 * Created by PhpStorm.
 * User: johan
 * Date: 31.10.2017
 * Time: 14:44
 */

$username = $_GET["username"];
$playerID = $_GET["playerID"];

$conn = connectToDB();

$sql = "Select * from Player p 
where p.PlayerID = $playerID and p.username = \"$username\";";

$result = $conn->query($sql);
if($result->num_rows > 0){
    echo "Success";
}else{
    echo "ERROR";
}

$conn->close();