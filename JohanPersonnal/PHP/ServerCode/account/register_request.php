<?php
include '../json_helper/Player.php';
include '../database_helper.php';

/**
 * Register form.
 * Basically like login with an insertion.
 * Created by PhpStorm.
 * User: johan
 * Date: 01.10.2017
 * Time: 16:36
 */


//access the parameters of the request
$username = $_GET['username'];
$password = $_GET['password'];
if(empty($username)||empty($password)){
    die("Please enter a valid username and password");
}

$conn = connectToDB();

$sql = "insert into Player(username,password,experience, 
numberWins , 
numberLoss , 
numberReports ,imageID,
message, fastestTime,Admin)
values(\"$username\",\"$password\",0,0,0,0,1,\"hello world ! \",0,0); ";

//when a player register he automatically gets added to the table of friends to record his friends later on
if($conn->query($sql)){
    //now we go back into the database to see the result of insertion.
    $sql = "Select PlayerID, username,message from Player p
    where p.username = \"$username\" and p.password = \"$password\";";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        //ouput data.
        $row = $result->fetch_assoc();
        $player = new Player();
        $player->set_player( $row["PlayerID"],$row["username"], $row["message"]);

        $playerJson = json_encode($player);
        //update the Friends list
        $sql = "insert into Friends(PlayerID, friends) values ($player->PlayerID,\"\" );";
        if($conn->query($sql)){
            echo $playerJson;
        }else{

            echo "ERROR";
        }



    } else {
        echo "ERROR";
    }
}else{
    echo "ERROR";
}


$conn->close();






