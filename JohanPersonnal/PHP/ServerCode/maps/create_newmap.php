<?php
include "../database_helper.php";

/**
 * this maps creates a new map.
 * In case of success resturn the map created. in case of failure. return ERROR.
 * Created by PhpStorm.
 * User: johan
 * Date: 12.11.2017
 * Time: 15:06
 */

//grab the attributes..
$username = $_GET["username"];
$playerID = $_GET["playerID"];
$mapdata = $_GET["mapdata"];
$mapName = $_GET["mapName"];
$description = $_GET["description"];

echo $mapdata;


$conn = connectToDB();

$sql = "insert into Map(mapName, mapDescription, Author, Data,NumOffensiveRep,NumUnfairRep) values (\"$mapName\",\"$description\",$playerID,\"$mapdata\", 0,0)";
echo $sql;
if($conn->query($sql)){
    //success..
    //get it now.
    $sql = "Select m.mapID from Map m where m.data = \"$mapdata\" and m.mapName = \"$mapName\"";
    $result = $conn->query($sql);
    if($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        $mapID = $row["mapID"];
        header("Location:mapInfo_request.php?mapID=$mapID");
    }else{
        echo "ERROR";
    }
}else{
    echo "ERROR";
}

$conn->close();
