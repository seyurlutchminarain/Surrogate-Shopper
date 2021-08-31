<?php
$username = "s2088960";
$password = "Joker_366";
$database = "d2088960";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();

$Name = $_REQUEST["name"];
$Table = strtoupper($_REQUEST["table"]);
$Col_User=strtoupper(substr($Table,0,3))."_USERNAME";

$query="SELECT * from $Table WHERE $Col_User = ? ";
$stat=mysqli_prepare($link,$query);
$stat->bind_param("s",$Name);
$stat->execute();
$result=$stat->get_result();

if ($result) {
while ($row=$result->fetch_assoc()){
$output[]=$row;
}
}
mysqli_close($link);
echo json_encode($output);
?>

