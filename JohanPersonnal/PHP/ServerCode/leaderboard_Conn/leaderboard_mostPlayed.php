<?php
include '../json_helper/playerL.php';
/**
 * Created by PhpStorm.
 * User: JeremyC
 * Date: 11/3/2017
 * Time: 3:31 PM
 *
 * This php file retrieve the list of players information that has a record of their total games played
 * by adding the numberWin and numberLoss column of the Player table together. This file will only retrieve players' information
 * The list of players' is sorted in descending order based on the total games played.
 */

$servername = "mysql.cs.iastate.edu";
$usernameDb = "dbu309vcb4";
$passwordDb = "qCaVEG0Z";
$dbname = "db309vcb4";
$playerL = new PlayerL();

//create connection
$conn = new mysqli($servername,$usernameDb,$passwordDb,$dbname);

//check connection
if($conn -> connect_error)
{
    die("Connection failed!".$conn->connect_error);
}

$sql = "select p.PlayerID, p.username, p.message, p.numberWins, p.numberLoss, p. imageID, p.fastestTime from Player p
        order by p.numberWins+p.numberLoss desc;";



$result = $conn->query($sql);

if($result->num_rows > 0)
{

    while($row = $result->fetch_assoc()) {
        $playerL->set_player($row["PlayerID"],$row["username"],$row["message"],$row["numberWins"],$row["numberLoss"],$row["imageID"],$row["fastestTime"]);
        echo json_encode($playerL);
        echo "#";
    }

}

else{
    echo "Error: Unable to allocate any players' information.";
}

$conn->close();

