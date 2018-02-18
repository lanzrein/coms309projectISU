<?php
include '../database_helper.php';
include '../json_helper/SetupGame.php';
/**
 * get info about the current game lobby
 * Created by PhpStorm.
 * User: johan
 * Date: 05.11.2017
 * Time: 18:45
 */

//check if its leader or not. if leader he can set the map/race else just race.
$gameID = $_GET["GameID"];
$playerID = $_GET["PlayerID"];
$race = $_GET["race"];
$mapID = $_GET["MapID"];


$conn = connectToDB();


$sql = "Select * from GameLobby where leaderID = $playerID and GameID = $gameID";
$result = $conn->query($sql);


if ($result->num_rows > 0) {
    //leader
    $row = $result->fetch_assoc();
    if($row["race2"] >= 0){
        //other player already updated.
        $setup = new SetupGame();
        $setup->set_setup($race, $row["race2"], $mapID, $playerID, $row["oppID"], $row["GameID"], -1);

        $sql = "Update GameLobby set race1 = $race , mapID = $mapID where gameID = $gameID";

        if ($conn->query($sql)) {
            echo json_encode($setup);
        } else {
            echo "ERROR";
        }

    }else {
        //other player didnt update.
        $sql = "Update GameLobby set race1 = $race , mapID = $mapID where gameID = $gameID";

        if ($conn->query($sql)) {
            echo "Wait";
        } else {
            echo "ERROR";
        }
    }


} else {
    //not leader
    $sql = "Select * from GameLobby where GameID = $gameID;";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        if ($row["mapID"] > 0) {
            //leader already updated.

            $sql = "Update GameLobby set race2 = $race where GameID = $gameID";

            if ($conn->query($sql)) {
                //encode and send the game..
                $setup = new SetupGame();
                $setup->set_setup($row["race1"], $race, $row["mapID"], $row["leaderID"], $playerID, $row["GameID"], -1);
                echo json_encode($setup);
            } else {
                echo "ERROR";
            }
        }else{
            $sql = "Update GameLobby set race2 = $race, oppID = $playerID where GameID = $gameID";
            $conn->query($sql);
            echo "Wait";
        }

    }else{
        echo "ERROR";
    }

}



$conn->close();
