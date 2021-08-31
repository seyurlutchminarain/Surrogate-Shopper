<?php
$username = "s2088960";
$password = "Joker_366";
$database = "d2088960";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();

$MSG_ID = $_REQUEST["msg_id"];

$query="DELETE FROM MESSAGES WHERE MSG_ID = ? ";
$stat=mysqli_prepare($link,$query);
$stat->bind_param("s",$MSG_ID);
$stat->execute();

mysqli_close($link);

?>

