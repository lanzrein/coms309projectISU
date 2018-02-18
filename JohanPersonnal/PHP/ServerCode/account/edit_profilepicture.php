<?php
include '../database_helper.php';
/**
 * Used in case of changing your profile picture
 * Created by PhpStorm.
 * User: johan
 * Date: 02.11.2017
 * Time: 13:35
 */

$username = $_GET['username'];
$password = $_GET['password'];
$newImageID = $_GET['imageID'];

$conn = connectToDB();

$sql = "update Player p set p.imageID = $newImageID
        where p.username=\"$username\" and p.password = \"$password\";";

if($conn->query($sql)){
    echo "New image set !";

}else{
    echo "ERROR";
}

$conn->close();