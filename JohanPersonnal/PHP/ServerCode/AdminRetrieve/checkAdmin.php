<?php
/**
 * Created by PhpStorm.
 * User: JeremyC
 * Date: 11/29/2017
 * Time: 9:42 PM
 *
 * Check whether the player is an admin by checking the Admin column
 * in the Player table. The query will use the username and password to pinpoint which
 * player they should check. If the player is an admin, it will echo "Success". If not, it
 * will echo "ERROR"
 */

include "../database_helper.php";

$username = $_GET["username"];
$password = $_GET["password"];

$conn = connectToDB();

$sql = "Select p.Admin from Player p 
where p.username = \"$username\" and p.password = \"$password\" and p.Admin = 1;";

$result = $conn->query($sql);


if($result->num_rows > 0){
    echo "Success";
}else{
    echo "ERROR";
}

$conn->close();