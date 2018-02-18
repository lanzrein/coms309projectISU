<?php
include '../database_helper.php';
include '../json_helper/SetupGame.php';
/**
 * poll the game lobby to see if the other player is ready.
 * Created by PhpStorm.
 * User: johan
 * Date: 05.11.2017
 * Time: 18:46
 */


//polling the game lobby to see if other player is ready.
//only done by leader...
$gameID = $_GET["GameID"];
$playerID = $_GET["PlayerID"];

$conn = connectToDB();


$sql = "Select * from GameLobby where GameID = $gameID";
$result = $conn->query($sql);
if($result->num_rows>0){

    $row = $result->fetch_assoc();
    if($row["race2"] > 0 && $row["race1"] > 0) {
        $setup = new SetupGame();
        $setup->set_setup($row["race1"], $row["race2"], $row["mapID"], $row["leaderID"], $row["oppID"], $row["GameID"], -1);

        echo json_encode($setup);
    }else{
        echo "Waiting for other player. ";
    }

}else{
    echo "ERROR";
}

$conn->close();