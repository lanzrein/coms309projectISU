<?php
include '../json_helper/Map.php';
include '../database_helper.php';
/**
 * Return meta data about a map.
 * Created by PhpStorm.
 * User: johan
 * Date: 11.10.2017
 * Time: 10:27
 */


$mapID = $_GET["mapID"];
if(empty($mapID)){
    die("Please enter valid map ID");
}

$conn = connectToDB();

$sql = "Select m.mapName, m.mapDescription, m.Author from Map m where m.mapID = $mapID;";
$result = $conn->query($sql);
$map = new Map();
if($result->num_rows > 0){

    $row = $result->fetch_assoc();
    $map->set_map($mapID, $row["mapName"], $row["mapDescription"],$row["Author"]);
    $res = json_encode($map);

    echo $res;
}

$conn->close();