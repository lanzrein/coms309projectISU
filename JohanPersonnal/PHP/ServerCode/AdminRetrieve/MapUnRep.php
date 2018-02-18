<?php
/**
 * Created by PhpStorm.
 * User: JeremyC
 * Date: 11/28/2017
 * Time: 11:41 PM
 *
 * The map that shares similar ID with the provided ID will have its NumUnfairRep column's value increase by one in the Player table.
 */

include '../database_helper.php';

$mapID = $_GET['mapID'];

if(empty($mapID))
{
    die('ERR : ID does not exist.');
}

//create connection
$conn = connectToDB();


$sql = "UPDATE Map 
        SET NumUnfairRep = NumUnfairRep + 1
        where mapID = $mapID";

if($conn->query($sql) == TRUE)
{
    echo "Successfully update map";
}

else{
    echo "Error in updating map".$conn->error;
}

$conn->close();
?>