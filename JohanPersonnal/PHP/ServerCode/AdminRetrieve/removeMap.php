<?php
/**
 * Created by PhpStorm.
 * User: JeremyC
 * Date: 11/28/2017
 * Time: 11:00 PM
 *
 * Delete the entire row of the map ID that is similar to the provided ID.
 */

$servername = "mysql.cs.iastate.edu";
$usernameDb = "dbu309vcb4";
$passwordDb = "qCaVEG0Z";
$dbname = "db309vcb4";

$mapID = $_GET['mapID'];

if(empty($mapID))
{
    die('ERR : ID does not exist.');
}

//create connection
$conn = new mysqli($servername,$usernameDb,$passwordDb,$dbname);

if($conn -> connect_error)
{
    die("Connection failed!".$conn->connect_error);
}

$sql = "DELETE FROM Map WHERE mapID = $mapID";

if($conn->query($sql) == TRUE)
{
    echo "Successfully deleted map";
}

else{
    echo "Error in deleting map".$conn->error;
}

$conn->close();
?>