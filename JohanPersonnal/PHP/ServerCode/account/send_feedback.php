<?php
/**
 * Sends a feedback to an email address.
 * Created by PhpStorm.
 * User: johan
 * Date: 01.11.2017
 * Time: 18:33
 */
//TODO if working on it later on pls dont let my email.
//using my swiss address because @iastate takes like 5min to arrive....
$email = "johan.lanzrein@epfl.ch";
$username = $_GET['username'];
$playerID = $_GET['playerID'];
$feedback = $_GET['feedback'];

$message = wordwrap("This is a feedback from $username ID $playerID. Here is what he has to say : \n $feedback",
                70,"\n");
$headers = 'To: Johan Per Alexandre Lanzrein<'.$email.'>'."\n".
            'From: Server <webmaster@proj-309-vc-b-4.cs.iastate.edu>' . "\n"
            .'Reply-To: Server<webmaster@proj-309-vc-b-4.cs.iastate.edu>'."\n".
                'X-Mailer: PHP/'.phpversion();
$try = mail($email,"[TOWERDEFENSE]",$message,$headers);
if($try){
    echo "Email sent!";
}else{
    echo "ERROR";
}


