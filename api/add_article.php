<?php 


$conn=mysqli_connect("localhost","root","091040tardis","users");



if(isset($_POST['article_name'], $_POST['article_link'],$_POST['article_image'],$_POST['article_text'], $_POST['user_id_article'])){
$article_name  = $_POST['article_name'];
$article_link    = $_POST['article_link'];
$article_image   = $_POST['article_image'];
$article_text    = $_POST['article_text'];
$user_id_article = $_POST['user_id_article'];
  

    $query = "INSERT INTO article (article_name,article_link,article_image,article_text,user_id_article)
    VALUES ('$article_name', '$article_link','$article_image','$article_text','$user_id_article')";

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