<?php
include '../database_helper.php';
/**
 * Deletes a message (a message in the sense of an exchange between players)
 * Created by PhpStorm.
 * User: johan
 * Date: 02.11.2017
 * Time: 18:43
 */

$messageID = $_GET["messageID"];
$toID = $_GET["toID"];
$username = $_GET["username"];
$password = $_GET["password"];



$conn = connectToDB();


//check if its correct user that asks for it.
$sql = "Select p.PlayerID from Player p WHERE
        p.username = \"$username\" and p.password =\"$password\";";

$result = $conn->query($sql);

if($result->num_rows>0){
    $row = $result->fetch_assoc();
    if($toID != $row["PlayerID"]){
        //incorrect user.
        echo "ERROR : wrong user";

    }else{
        //we have the correct user we can delete messages safely.
        $sql = "delete from Messages where messageID = $messageID";

        if($conn->query($sql)){
            echo "Success";
        }else{
            echo "ERROR";
        }
    }
}else{
    //not a user
    echo "ERROR : not a user";
}

$conn->close();