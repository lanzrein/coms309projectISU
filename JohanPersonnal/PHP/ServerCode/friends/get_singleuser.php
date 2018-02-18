<?php
include '../database_helper.php';
include  '../json_helper/User.php';
/**
 * Get a single user according to his id and possibly username
 * Created by PhpStorm.
 * User: johan
 * Date: 02.11.2017
 * Time: 16:51
 */

$username = $_GET["username"];
$playerID = $_GET["playerID"];


$conn = connectToDB();
$sql = "";
if(empty($username)){
    $sql = "Select * from Player p 
where p.PlayerID = $playerID ;";

}else{
    $sql = "Select * from Player p 
where p.PlayerID = $playerID and p.username = \"$username\";";

}

$result = $conn->query($sql);
if($result->num_rows > 0){
    //return the user that we have..
    $row = $result->fetch_assoc();
    $user = new User();
    //($playerID,$username,$message,$noWins,$noLoss,$imageID)
    $user->set_user($row["PlayerID"],$row["username"],$row["message"],$row["numberWins"],$row["numberLoss"], $row["imageID"]);
    echo json_encode($user);

}else{
    echo "ERROR";
}

$conn->close();