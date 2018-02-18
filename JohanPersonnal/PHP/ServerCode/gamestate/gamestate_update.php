<?php
include '../database_helper.php';
/**
 * Created by PhpStorm.
 * This class handles lobby request by adding them to the database.
 * If the request was successfully added return 0
 * else return -1
 *
 * User: johan
 * Date: 07.10.2017
 * Time: 13:23
 */

$playerID = $_GET["playerID"];
$enemy = $_GET["adversaryID"];

//get it as a JSON String
$gamestateString =$_GET["gamestate"];





//add the request to the database.
$conn = connectToDB();


//we need to check if there is already a request for the player in the database !
$sql = "insert into RequestsTable(PlayerID, AdversaryID, gamestate,flag)values(
 $playerID, $enemy, '$gamestateString',1
) on DUPLICATE  KEY UPDATE gamestate = '$gamestateString', flag = 1;";

if($conn->query($sql)){
    //the request was successfully added to the db
    echo "SUCCESS";

}else{
    echo "ERR";
}


$conn->close();




