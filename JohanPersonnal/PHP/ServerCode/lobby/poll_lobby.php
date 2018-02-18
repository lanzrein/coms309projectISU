<?php

include '../database_helper.php';
include '../json_helper/Lobby.php';
include '../json_helper/SetupGame.php';
/**
 * Poll the game to see if there is a game available
 * Created by PhpStorm.
 * User: johan
 * Date: 05.11.2017
 * Time: 14:37
 */

$playerID = $_GET["PlayerID"];
$gamemode = $_GET["Gamemode"];
$username = $_GET["username"];


$conn = connectToDB();

$sql = "Select GameID, leaderID, leaderUsername , oppID, oppUsername from Games where gamemode = $gamemode;";


$result = $conn->query($sql);

if($result->num_rows){
    //there is a game !
    $found = false;
    while(($row = $result->fetch_assoc()) && !$found) {

        $gameID = $row["GameID"];
        $leaderID = $row["leaderID"];
        $leaderUsername = $row["leaderUsername"];

        if ($leaderID != $playerID) {
            //we update it in the db and then return it to the first player.
            $sql = "Update Games set oppID = $playerID, oppUsername = \"$username\" where gameID= $gameID;";
            if ($conn->query($sql)) {
                //output the username and the gamemode. we can then go to lobby.
                $found = true;
                $lobby = new Lobby();

                $lobby->set_lobby($leaderID, $leaderUsername,$gamemode,$gameID, $playerID, $username);


                //prepare a gamelobby.
                $gamelobby = new SetupGame();
                $gamelobby->set_fromlobby($lobby);



                $sql = "Insert into GameLobby(GameID, gamemode, leaderID, oppID, race1, race2, mapID)
                              values ($gamelobby->gameID,$gamemode, $gamelobby->leaderID, $playerID,
                      -1,-1,-1);";


                $conn->query($sql);



                echo json_encode($lobby);

            } else {

                echo "ERROR";
            }

        }else{
            //check if there is an opponent or not.
            $oppID = $row["oppID"];
            $oppUsername = $row["oppUsername"];
            if($oppID != -1){
                //found a player...

                $lobby = new Lobby();
                $lobby->set_lobby($leaderID, $leaderUsername,$gamemode,$gameID, $oppID, $oppUsername);
                echo json_encode($lobby);


                //delete the entry because they both have the info now.
//                $sql = "delete from Games where GameID = $gameID";
                $found = true;
///                $conn->query($sql);

            }
        }
    }

    if(!$found){
        echo "Waiting for a game";
    }

}else{
    //create a game
    $sql = "insert into Games(leaderID, leaderUsername, gamemode, oppID, oppUsername) values ( $playerID, \"$username\", $gamemode, -1, \"\");";
    if($conn->query($sql)){
        echo "Waiting for a game";
    }else{
        echo "ERROR";
    }

}

$conn->close();

