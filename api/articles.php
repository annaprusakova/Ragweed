<?php 

$conn=mysqli_connect("localhost","root","091040tardis","users");

$type = $_GET['item_type'];

if (isset($_GET['key'])) {
    $key = $_GET["key"];
    if ($type == 'article') {
        $query = "SELECT * FROM article WHERE article_name LIKE '%$key%'";
        $result = mysqli_query($conn, $query);
        $data = array();
        while( $row = mysqli_fetch_assoc($result) ){
            $data[]=$row;
        }
        echo json_encode( $data);   
    }
} else {
    if ($type == 'article') {
        $query = "SELECT * FROM article";
        $result = mysqli_query($conn, $query);
        $data = array();
        while( $row = mysqli_fetch_assoc($result) ){
            $data[]=$row;
        }
        echo json_encode($data);   
    }
}


?>