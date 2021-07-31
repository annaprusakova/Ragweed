
<?php

if(isset($_POST['email'], $_POST['password'])){
    $email=$_POST['email'];
    $password=$_POST['password'];
   
//an array of response
$output = array();

//requires database connection
require_once('connect.php');

//checking if email exit
$conn=$dbh->prepare("SELECT * FROM users_table WHERE email=? and password=?");
$pass=md5($password);
$conn->bindParam(1,$email);
$conn->bindParam(2,$pass);
$conn->execute();
if($conn->rowCount() == 0){
$output['isSuccess'] = 0;
$output['message'] = "Невірна пошта чи пароль";
}

//get the username
if($conn->rowCount() !==0){
$results=$conn->fetch(PDO::FETCH_ASSOC);
//we get both the username and password

$output['isSuccess'] = 1;
$output['message'] = "login sucessful";
$output['name'] = $results['name'];
$output['email'] = $results['email'];
$output['password'] = $password;
$output['user_photo'] = "http:/" .$results['user_photo'];
$output['id'] = $results['id'];
}
echo json_encode($output);
}
?>
