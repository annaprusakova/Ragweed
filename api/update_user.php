<?php 
$conn=mysqli_connect("localhost","root","091040tardis","users");


$key = $_POST['key'];

if ( $key == "update" ){
$id         = $_POST['id'];
$name      = $_POST['name'];
$email    = $_POST['email'];
$user_gender      = $_POST['user_gender'];
$user_photo     = $_POST['user_photo'];

$query = "UPDATE users_table SET 
name='$name', 
email='$email', 
user_gender='$user_gender' 
WHERE id='$id' ";

if ( mysqli_query($conn, $query) ){

    if ($user_photo == null) {

        $result["value"] = "1";
        $result["message"] = "Success";

        echo json_encode($result);
        mysqli_close($conn);

    } else {

        $path = "Users/$id.jpeg";
        $finalPath = "/192.168.0.105/api/".$path;

        $insert_picture = "UPDATE users_table SET user_photo='$finalPath' WHERE id='$id' ";
    
        if (mysqli_query($conn, $insert_picture)) {
    
            if ( file_put_contents( $path, base64_decode($user_photo) ) ) {
                
                $result["value"] = "1";
                $result["message"] = "Success!";
    
                echo json_encode($result);
                mysqli_close($conn);
    
            } else {
                
                $response["value"] = "0";
                $response["message"] = "Error! ".mysqli_error($conn);
                echo json_encode($response);

                mysqli_close($conn);
            }

        }
    }

} 
else {
    $response["value"] = "0";
    $response["message"] = "Error! ".mysqli_error($conn);
    echo json_encode($response);

    mysqli_close($conn);
}
}


?>