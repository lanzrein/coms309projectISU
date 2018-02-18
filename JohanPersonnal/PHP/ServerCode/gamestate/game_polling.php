<?php
include '../database_helper.php';
/**
 * Created by PhpStorm.
 * This script is executed whenever the client polls during a game
 * It goes into the database to try and find any request the client as sent.
 * and sends it back
 * Sends null string if not request .
 *
 * Output : gamestate if updated, 0 else.
 * User: johan
 * Date: 07.10.2017
 * Time: 13:58
 */

$playerID = $_GET['playerID'];
$enemy = $_GET['adversaryID'];


//add the request to the database.

$conn = connectToDB();


//we need to check if there is already a request for the player in the database !
$sql = "select r.gamestate,  r.flag from RequestsTable r where 
 r.PlayerID = $enemy and r.AdversaryID = $playerID;";


$result = $conn->query($sql);



if($result->num_rows > 0){
    //there is a request for our client.
    $row = $result->fetch_assoc();
    $flag = $row["flag"];

    if($flag != 0) {
        clear_flag($conn,$playerID,$enemy);
        $gs = $row["gamestate"];

        echo $gs;
    }else{
        echo "$";
    }
}else{
    echo "ERROR";
}


$conn->close();

function clear_flag($conn,$playerID,$enemy){
~~    $conn->query($sql);

    return;
}
