<?php
$username = "s2088960";
$password = "Joker_366";
$database = "d2088960";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();

$R_ID = $_REQUEST["r_id"];
$VOL_ID = $_REQUEST["vol_id"];



$update="UPDATE REQUESTS SET REQ_TAKEN = 1,VOL_ID=? WHERE R_ID = ?";
$Q=mysqli_prepare($link,$update);
$Q->bind_param("ss",$VOL_ID,$R_ID);
$Q->execute();


mysqli_close($link);

?>

