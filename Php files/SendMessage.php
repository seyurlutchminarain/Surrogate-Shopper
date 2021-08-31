<?php
$username = "s2088960";
$password = "Joker_366";
$database = "d2088960";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();

$Message = $_REQUEST["message"];
$VOL_ID = $_REQUEST["vol_id"];
$REQ_ID = $_REQUEST["req_id"];
$REQ_USERNAME = $_REQUEST["name"];


$query="INSERT INTO MESSAGES(MESSAGE,VOL_ID,REQ_ID,REQ_USERNAME) VALUES(?,?,?,?) ";
$stat=mysqli_prepare($link,$query);
$stat->bind_param("ssss",$Message,$VOL_ID,$REQ_ID,$REQ_USERNAME);
$stat->execute();

mysqli_close($link);

?>

