<?php
//import the files..
include '../json_helper/playerL.php';
/**
 * Created by PhpStorm.
 * User: JeremyC
 * Date: 10/7/2017
 * Time: 3:31 PM
 *
 * This php file retrieve the list of players information that has a record of their win ratio
 * The win ratio is obtained by dividing the numberWin column with the numberLoss column of the Player table.
 * The list of players' is sorted in descending order based on the win ratio.
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
        order by p.numberWins/p.numberLoss desc;";



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
