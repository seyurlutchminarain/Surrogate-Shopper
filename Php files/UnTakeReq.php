<?php
$username = "s2088960";
$password = "Joker_366";
$database = "d2088960";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();

$R_ID = $_REQUEST["r_id"];

$update="UPDATE REQUESTS SET REQ_TAKEN = 0,VOL_ID=NULL WHERE R_ID = ?";
$Q=mysqli_prepare($link,$update);
$Q->bind_param("s",$R_ID);
$Q->execute();


mysqli_close($link);

?>

