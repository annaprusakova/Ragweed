<?php 
$conn=mysqli_connect("localhost","root","091040tardis","users");


$key = $_POST['key'];

$comment_user_id    = $_POST['comment_user_id'];
$comment_date     = $_POST['comment_date'];
$comment_text      = $_POST['comment_text'];
$comment_topic_id    = $_POST['comment_topic_id'];


if ( $key == "insert" ){

    $date_newformat = date('Y-m-d', strtotime($comment_date));

    $query = "INSERT INTO comment (comment_date,comment_text,comment_user_id,comment_topic_id)
    VALUES ('$date_newformat', '$comment_text', '$comment_user_id', '$comment_topic_id') ";

        if ( mysqli_query($conn, $query) ){
            $result["value"] = "1";
            $result["message"] = "Success";
                echo json_encode($result);
                mysqli_close($conn);

            } 
          
             else { 
                        $response["value"] = "0";
                        $response["message"] = "Error! ".mysqli_error($conn);
                        echo json_encode($response);

                        mysqli_close($conn);
             }
   
}


?>