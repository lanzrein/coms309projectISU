<?php
include '../database_helper.php';
include '../json_helper/Message.php';
/**
 * Poll to see f there is any new messages.
 * Created by PhpStorm.
 * User: johan
 * Date: 02.11.2017
 * Time: 18:10
 */

$username = $_GET["username"];
$password = $_GET["password"];

$conn = connectToDB();

$sql = "select m.fromID, m.message , m.messageID from 
        Messages m where toID in (
        select p.PlayerID from Player p
        where p.username = \"$username\" and 
              p.password = \"$password\"
        );";

$result = $conn->query($sql);
if($result->num_rows>0){
    while($row = $result->fetch_assoc()) {


        $playerID = $row["fromID"];
        $sql = "Select p.username from Player p where p.PlayerID = $playerID;";
        $resUsername = $conn->query($sql);
        if ($resUsername->num_rows > 0) {

            $userRow = $resUsername->fetch_assoc();
            $message = new Message();

//        $message->set_message($row["fromID"],$userRow["username"],$row["message"]);
            $message->message = $row["message"];
            $message->fromID = $row["fromID"];
            $message->fromUsername = $userRow["username"];
            $message->messageID = $row["messageID"];
            echo json_encode($message);
            echo "$";
        }
    }



}else{
    //no messages
    echo "Empty";
}

$conn->close();