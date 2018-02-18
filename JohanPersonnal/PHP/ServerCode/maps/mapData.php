<?php
include '../json_helper/Map.php';
include '../database_helper.php';
/**
 * Get the data for a map
 * Created by PhpStorm.
 * User: johan
 * Date: 12.10.2017
 * Time: 16:53
 */

$mapID = $_GET["mapID"];
if(empty($mapID)){
    die("ERROR");
}


$conn = connectToDB();

$sql = "Select m.Data from Map m where m.mapID = $mapID";
$result = $conn->query($sql);
$map = new Map();
if($result->num_rows > 0){
    $row = $result->fetch_assoc();
    $map = $row["Data"];
    echo $map;
}
$conn->close();