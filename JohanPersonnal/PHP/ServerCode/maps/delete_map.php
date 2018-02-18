<?php
include '../database_helper.php';
include  '../json_helper/Map.php';
/**
 * Deletes a map if the user wants to
 * Created by PhpStorm.
 * User: johan
 * Date: 28.11.2017
 * Time: 17:44
 */

$authorID = $_GET["authorID"];
$mapID = $_GET["mapID"];

$conn = connectToDB();

$sql = "delete from Map where Author = $authorID and mapID = $mapID";

if($conn->query($sql)){
    echo "SUCCESS";
}else{
    echo "ERR";
}