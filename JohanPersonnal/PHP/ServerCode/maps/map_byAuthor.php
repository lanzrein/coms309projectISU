<?php
include '../json_helper/Map.php';
include '../database_helper.php';
/**
 * Get all the maps of a given author
 * Created by PhpStorm.
 * User: johan
 * Date: 26.11.2017
 * Time: 13:34
 */

$authorID = $_GET["authorID"];
$conn = connectToDB();

$sql = "Select m.mapID, m.mapName,m.mapDescription from Map m where m.Author = $authorID";
$result = $conn->query($sql);

if($result->num_rows> 0){
    //echo all the rows.

    while($row = $result->fetch_assoc()){

        $map = new Map();
        $map->set_map($row["mapID"],$row["mapName"],$row["mapDescription"],
            $authorID);
        echo json_encode($map)."$";

    }
}else{
    echo 'ERR';
}

$conn->close();
