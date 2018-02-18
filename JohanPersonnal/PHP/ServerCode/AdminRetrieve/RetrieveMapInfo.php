<?php
/**
 * Created by PhpStorm.
 * User: JeremyC
 * Date: 11/18/2017
 * Time: 9:32 PM
 *
 * Retrieve a list of maps with its respective information. This activity will only retrieve the map whose
 * Number of offensive or unfairness report is higher than 0.
 */


include '../admin/RepMap.php';

$servername = "mysql.cs.iastate.edu";
$usernameDb = "dbu309vcb4";
$passwordDb = "qCaVEG0Z";
$dbname = "db309vcb4";
$RepMap = new RepMap();

//create connection
$conn = new mysqli($servername,$usernameDb,$passwordDb,$dbname);

//check connection
if($conn -> connect_error)
{
    die("Connection failed!".$conn->connect_error);
}

$sql = "select m.mapID, m.mapName, m.mapDescription, m.NumOffensiveRep, m.NumUnfairRep
        from Map m where m.NumOffensiveRep > 0 || m.NumUnfairRep > 0
        order by m.NumOffensiveRep + m.NumUnfairRep desc;";

$result = $conn->query($sql);

if($result->num_rows >0) {
    while ($row = $result->fetch_assoc()) {
        $RepMap->set_mapR($row["mapID"], $row["mapName"], $row["mapDescription"], $row["NumOffensiveRep"], $row["NumUnfairRep"]);
        echo json_encode($RepMap);
        echo "#";
    }
}

else{
    echo "Error: Unable to allocate any players' information.";
}




    $conn->close();