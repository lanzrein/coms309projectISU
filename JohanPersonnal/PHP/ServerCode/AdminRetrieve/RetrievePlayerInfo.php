<?php
/**
 * Created by PhpStorm.
 * User: JeremyC
 * Date: 11/18/2017
 * Time: 10:17 PM
 *
 * Retrieve a list of players with its respective information. This activity will only retrieve the player whose
 * Number of reports is higher than 0.
 */


include '../admin/RepPlayers.php';

$servername = "mysql.cs.iastate.edu";
$usernameDb = "dbu309vcb4";
$passwordDb = "qCaVEG0Z";
$dbname = "db309vcb4";
$RepPlayers = new RepPlayers();

//create connection
$conn = new mysqli($servername,$usernameDb,$passwordDb,$dbname);

//check connection
if($conn -> connect_error)
{
    die("Connection failed!".$conn->connect_error);
}

$sql = "select r.PlayerID, r.username, r.numberWins, r.numberLoss, r.numberReports, r.imageID, r.message from Player r
        where r.numberReports
        order by r.numberReports desc;";


$result = $conn->query($sql);

if($result->num_rows > 0)
{

    while($row = $result->fetch_assoc()) {
        $RepPlayers->set_playerR($row["PlayerID"],$row["username"],$row["numberWins"],$row["numberLoss"], $row["numberReports"], $row["imageID"], $row["message"]);
        echo json_encode($RepPlayers);
        echo "#";
    }

}

else{
    echo "Error: Unable to allocate any players' information.";
}

$conn->close();