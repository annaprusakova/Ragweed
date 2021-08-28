
<?php


if(isset($_POST['name'], $_POST['email'], $_POST['password'])){
    $name=$_POST['name'];
    $email=$_POST['email'];
    $password=$_POST['password'];

$output=array();


require_once('connect.php');

$conn=$dbh->prepare('SELECT email FROM users_table WHERE email=?');
$conn->bindParam(1,$email);
$conn->execute();


if($conn->rowCount() !==0){
$output['isSuccess'] = 0;
$output['message'] = "Електронна адреса вже існує";
}else{

$conn=$dbh->prepare('INSERT INTO users_table(name,email,password) VALUES (?,?,?)');

$pass=md5($password);
$conn->bindParam(1,$name);
$conn->bindParam(2,$email);
$conn->bindParam(3,$pass);

$conn->execute();
if($conn->rowCount() == 0)
{
$output['isSuccess'] = 0;
$output['message'] = "Не вдалося зареєструвати, спробуйте ще раз";
}
elseif($conn->rowCount() !==0){
$output['isSuccess'] = 1;
$output['message'] = "Ви успішно зареєстровані";
$output['username']=$name;
}
}
echo json_encode($output);

}

?>
