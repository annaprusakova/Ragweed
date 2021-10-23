<?php 

$conn=mysqli_connect("localhost","root","","ragweed");


if (isset($_GET['id'])) {
    $id = $_GET['id'];
        $query = "SELECT * FROM users_table WHERE id=$id";
        $result = mysqli_query($conn, $query);
        $response = array();
 

        while( $row = mysqli_fetch_assoc($result) ){

    array_push($response, 
    array(
        'id'   =>$row['id'], 
        'name'      =>$row['name'], 
        'email'      =>$row['email'],
        'password'     =>md5($row['password']),
        'user_photo'     =>"http:/" . $row['user_photo'],
        'user_gender'=>$row['user_gender'])
    );
}
        
echo json_encode($response); 
mysqli_close($conn); 
}



?>