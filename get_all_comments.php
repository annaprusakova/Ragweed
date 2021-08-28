<?php 

$conn=mysqli_connect("localhost","root","091040tardis","users");

if (isset($_GET['key'],$_GET['table'])) {
    $table = $_GET["table"];
    $key = $_GET["key"];
        $query = "SELECT c.comment_date, c.comment_text, u.name, u.user_photo FROM comment as c, users_table as u, topic_comment as tc WHERE c.comment_user_id = u.id AND c.comment_topic_id = tc.topic_comment_id AND c.comment_topic_id = (SELECT topic_comment_id FROM topic_comment WHERE topic_id = $key AND topic_name ='$table')";
        $result = mysqli_query($conn, $query);
        $response = array();
 
        while($row = mysqli_fetch_assoc($result) ){

    array_push($response, 
    array(
        'comment_date'   =>date($row['comment_date']), 
        'comment_text'      =>$row['comment_text'], 
        'name'      =>$row['name'],
        'user_photo'     =>"http:/" . $row['user_photo'])
    );
}
        
echo json_encode($response); 
mysqli_close($conn); 
    
        
}


?>