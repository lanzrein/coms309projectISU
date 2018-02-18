<?php
include "../database_helper.php";

/**
 * File used for when a user asks to edit his personnal message
 * Created by PhpStorm.
 * User: johan
 * Date: 01.11.2017
 * Time: 18:25
 */

$username = $_GET['username'];
$password = $_GET['password'];
$message = $_GET['newMessage'];

$conn = connectToDB();

$sql = "update Player p set p.message = \"$message\"
      where p.username = \"$username\" and p.password = \"$password\";";
if($conn->query($sql)){
    echo "Update successful";
}else{
    echo "ERROR";
}

$conn->close();