<?php
include '../database_helper.php';

/**
 * Send a message to the user with toID
 * Created by PhpStorm.
 * User: johan
 * Date: 02.11.2017
 * Time: 17:41
 */

$message = $_GET["message"];
$fromID = $_GET["fromID"];
$toID = $_GET["toID"];

$conn = connectToDB();

$sql = "insert into Messages(fromID, toID, message) 
values($fromID, $toID,\"$message\");";

if($conn->query($sql)){
    echo "Success";
}else{
    echo "ERROR";
}

$conn->close();