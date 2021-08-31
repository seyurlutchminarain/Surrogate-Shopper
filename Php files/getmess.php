<?php
$username = "s2088960";
$password = "Joker_366";
$database = "d2088960";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();

$VOL_ID = $_REQUEST["vol_id"];


$query="SELECT * FROM  MESSAGES WHERE VOL_ID = ? ";
$stat=mysqli_prepare($link,$query);
$stat->bind_param("s",$VOL_ID);
$stat->execute();
$result=$stat->get_result();

if($result){

while ($row=$result->fetch_assoc()){
$output[]=$row;
}

}


mysqli_close($link);
echo json_encode($output);
?>

