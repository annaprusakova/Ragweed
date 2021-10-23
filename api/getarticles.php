<?php 
if(isset($_GET['article_id'])){
	$article_id = $_GET['article_id'];
	
$con=mysqli_connect("localhost","root","","ragweed");

$sql="SELECT * FROM article WHERE article_id='$article_id'";
$result=mysqli_query($con,$sql);

$data=array();
while($row=mysqli_fetch_assoc($result)){
$data[]=$row;

}
			
	echo json_encode($data);
	
	
}
?>