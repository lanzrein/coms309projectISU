<?php
include '../json_helper/Map.php';
include '../database_helper.php';
/**
 * Get the data for all the maps
 * Created by PhpStorm.
 * User: johan
 * Date: 17.10.2017
 * Time: 16:25
 */

$conn = connectToDB();

$sql = "Select m.mapID, m.mapName,m.mapDescription,m.Author from Map m;";
$result = $conn->query($sql);

if($result->num_rows> 0){
    //echo all the rows.

    while($row = $result->fetch_assoc()){

        $map = new Map();
        $map->set_map($row["mapID"],$row["mapName"],$row["mapDescription"],
            $row["Author"]);
        echo json_encode($map)."$";

    }
}else{
    echo 'ERR';
}

$conn->close();