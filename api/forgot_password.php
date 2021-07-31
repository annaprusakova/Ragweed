<?php 

$conn=mysqli_connect("localhost","root","091040tardis","users");

if(isset($_POST['email'])){
    $email = $_POST['email'];
    $query = "SELECT email FROM users_table WHERE email='$email'";
      
    if (mysqli_query($conn, $query)){
    $query = "SELECT email,password FROM users_table WHERE email='$email'";

    $result = mysqli_query($conn, $query);
    $data = array();

    if(mysqli_num_rows($result) > 0){
        $row = mysqli_fetch_assoc($result);
        $password = $row['password'];
        $to      = $row['email'];
        $subject = 'Пароль';
        $message = "Ваш пароль від облікового запису у Ragweed:  ".md5($password);
        $headers = 'From: ragweedapp@gmail.com';

    mail($to, $subject, $message, $headers);

                $response["value"] = "1";
                $response["message"] = "Success";
    
                echo json_encode($response);
               
        
    }

    }else{
        $response['isSuccess'] = 0;
        $response['message'] = "No such email";
        echo json_encode($response);
        mysqli_close($conn);
    }
    

}


?>