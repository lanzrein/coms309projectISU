<?php
include '../database_helper.php';
include '../json_helper/User.php';
/**
 * Get all the friends of a user
 * Created by PhpStorm.
 * User: johan
 * Date: 05.10.2017
 * Time: 17:49
 */



//access the parameters of the request
$username = $_GET['username'];
$password = $_GET['password'];

if(empty($username)||empty($password)){
    die("Please enter a valid username and password");
}

$conn = connectToDB();

$sql = "select f.Friends
        from Player p join Friends f
        on p.PlayerID = f.PlayerID
        where p.username = \"$username\" and p.password = \"$password\";";


$result = $conn->query($sql);


if ($result->num_rows > 0) {
    //request for each friend his info.
    $row = $result->fetch_assoc();

    $array = preg_split("/[,]+/",$row["Friends"],NULL,PREG_SPLIT_NO_EMPTY);

    foreach ($array as $friendID){
            $sql= "SELECT PlayerID, username,message,numberWins, numberLoss , imageID
                  FROM Player p
                  WHERE p.PlayerID = $friendID;";
            $result = $conn->query($sql);
            if($result->num_rows>0){
                $row = $result->fetch_assoc();
                $user = new User();
                //($playerID,$username,$message,$noWins,$noLoss,$imageID)
                $user->set_user($row["PlayerID"],$row["username"],$row["message"],$row["numberWins"],$row["numberLoss"], $row["imageID"]);
                echo json_encode($user);
                echo "$";
//                echo "hello".$row1["PlayerID"].$row1["username"];
            }
    }

} else {
    echo "Error : wrong username or password combination.\n";
}

$conn->close();