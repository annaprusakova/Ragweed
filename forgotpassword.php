
<?php


if(isset($_POST['email'])){
    $email=$_POST['email'];


$output=array();


require_once('connect.php');

$conn=$dbh->prepare('SELECT email FROM users_table WHERE email=?');
$conn->bindParam(1,$email);
$conn->execute();


if($conn->rowCount() !==0){
    $conn=$dbh->prepare('SELECT email,password FROM users_table WHERE email=?');

$conn->bindParam(1,$email);

$conn->execute();
if($conn->rowCount() == 0)
{
$output['isSuccess'] = 0;
$output['message'] = "Error";
}
elseif($conn->rowCount() !==0){
    $results=$conn->fetch(PDO::FETCH_ASSOC);
$output['isSuccess'] = 1;
$output['message'] = "sent";
$to      = $email;
$password = md5($results['password']);
$subject = 'Пароль';
        $message = "Ваш пароль від облікового запису у Ragweed:  ".$password;
        $headers = 'From: ragweedapp@gmail.com';

    mail($to, $subject, $message, $headers);

}else{

    $output['isSuccess'] = 0;
    $output['message'] = "already exist";
}
}
echo json_encode($output);

}

?>
