<?php
/**
 * Created by PhpStorm.
 * User: JeremyC
 * Date: 11/4/2017
 * Time: 12:30 PM
 *
 * This php file retrieve the list of players' friend in-game information that has a record of the number of game they played
 * by adding the value of both the numberWin and numberLoss column of the Player table together.
 * The information for the friend of the player is sorted in descending order based on the total game played.
 */

include '../json_helper/playerL.php';
//access the parameters of the request
$username = $_GET['username'];
$password = $_GET['password'];
if(empty($username)||empty($password)){
    die("Please enter a valid username and password");
}

$servername = "mysql.cs.iastate.edu";
$usernameDb = "dbu309vcb4";
$passwordDb = "qCaVEG0Z";
$dbname = "db309vcb4";
$store = array(); //array to stored data.
$index = 0; // index of an array

//create connection
$conn = new mysqli($servername,$usernameDb,$passwordDb,$dbname);

//check connection
if($conn -> connect_error)
{
    die("Connection failed!".$conn->connect_error);
}

/**
 * compare two values of total game played value and see which one of the two is larger
 * @param $a the first total game played value
 * @param $b the second total game played value
 * @return int  return the comparison value
 */
function cmp($a, $b)
{
    if($a["numberWin"]+$a["numberLoss"] == $b["numberWin"]+$b["numberLoss"])
    {
        return 0;
    }

    return ($a["numberWin"]+$a["numberLoss"] > $b["numberWin"]+$b["numberLoss"]) ? -1 : 1;
}

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
        $sql= "SELECT PlayerID, username,message,numberWins, numberLoss ,imageID, fastestTime
                  FROM Player p WHERE p.PlayerID = $friendID;";
        $result = $conn->query($sql);
        //compile each friend info into an array
        $row = $result->fetch_assoc();
        $store[$index] = $row;
        $index++;
    }

    //sort the array based on most game played.
    usort($store, "cmp");

    //echo every element in the array.
    for($i = 0; $i < count($store); $i++)
    {
        if($store[$i] != null) {
            echo json_encode($store[$i]);
            echo "#";
        }
    }

} else {
    echo "Error : wrong username or password combination.\n";
}