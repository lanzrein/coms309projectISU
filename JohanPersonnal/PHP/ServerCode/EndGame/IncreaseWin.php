<?php
/**
 * Created by PhpStorm.
 * User: JeremyC
 * Date: 11/30/2017
 * Time: 11:44 PM
 */

include '../database_helper.php';

$PlayerID = $_GET['PlayerID'];

if(empty($PlayerID))
{
    die('ERR : ID does not exist.');
}

//create connection
$conn = connectToDB();


$sql = "UPDATE Player 
        SET numberWins = numberWins + 1
        where PlayerID = $PlayerID";

if($conn->query($sql) == TRUE)
{
    echo "Successfully update account";
}

else{
    echo "Error in updating account".$conn->error;
}

$conn->close();
?>