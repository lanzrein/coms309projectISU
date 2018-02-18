<?php
include '../json_helper/Player.php';
include '../database_helper.php';
/**
 * Handles all the login request from different users.
 * Created by PhpStorm.
 * User: johan
 * Date: 01.10.2017
 * Time: 14:50
 */


//access the parameters of the request
$username = $_GET['username'];
$password = $_GET['password'];
if(empty($username)||empty($password)){
    die("ERROR");
}


$conn = connectToDB();

$sql = "Select PlayerID, username,message from Player p
where p.username = \"$username\" and p.password = \"$password\";";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    //ouput data.
    $row = $result->fetch_assoc();
    $player = new Player();
    $player->set_player( $row["PlayerID"],$row["username"], $row["message"]);

    echo json_encode($player);
} else {
    echo "ERROR";
}

$conn->close();









