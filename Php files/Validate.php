<?php
$username = "s2088960";
$password = "Joker_366";
$database = "d2088960";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

$Name = $_REQUEST["name"];
$Table = strtoupper($_REQUEST["table"]);
//get first three chars from table to use for query.Also decides which table and columns to query
$Col_Pass = strtoupper(substr($Table,0,3))."_PASSWORD";
$Col_User=strtoupper(substr($Table,0,3))."_USERNAME";

$query="SELECT $Col_Pass from $Table where $Col_User = ?";
$stat=mysqli_prepare($link,$query);
$stat->bind_param("s",$Name);
$stat->execute();
$result=$stat->get_result();
$row=mysqli_fetch_assoc($result);//since there will be a unique user only a single row is needed

mysqli_close($link);
echo $row["$Col_Pass"];
?>
