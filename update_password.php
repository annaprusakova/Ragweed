<?php 
$conn=mysqli_connect("localhost","root","091040tardis","users");


if(isset($_POST['id'], $_POST['password'])){
$id         = $_POST['id'];
$password    = $_POST['password'];
$new_password = $_POST['new_password'];


$old_pass = md5($password);
$new_pass = md5($new_password);


$query = "SELECT password FROM users_table WHERE id = $id";
$result = mysqli_query($conn, $query);
$row = mysqli_fetch_assoc($result);
$check_password=$row['password'];


if($old_pass == $check_password){
    $query = "UPDATE users_table SET password='$new_pass' WHERE id=$id ";
    if ( mysqli_query($conn, $query) ){
        
            $response["value"] = "1";
            $response["message"] = "Success";
    
            echo json_encode($response);
            mysqli_close($conn);
    
        } else {
                    
                    $response["value"] = "0";
                    $response["message"] = "Error! ".mysqli_error($conn);
                    echo json_encode($response);
    
                    mysqli_close($conn);
                }
} else{
    $response["value"] = "0";
    $response["message"] = "Старий пароль не вірний!".mysqli_error($conn);
    echo json_encode($response);

    mysqli_close($conn);
}

        }
?>