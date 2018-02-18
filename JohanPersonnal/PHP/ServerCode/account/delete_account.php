<?php
include "../database_helper.php";
/**
 * This file is used when the player wants to delete his account.
 * Created by PhpStorm.
 * User: johan
 * Date: 01.11.2017
 * Time: 18:29
 */

$username = $_GET['username'];
$password = $_GET['password'];

$conn = connectToDB();

$sql = "delete from Player where username = \"$username\" and password = \"$password\";";
if($conn->query($sql)){
    echo "Delete successful";
}else{
    echo "ERROR";
}

$conn->close();