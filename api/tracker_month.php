<?php 
$conn=mysqli_connect("localhost","root","091040tardis","users");
if(isset($_POST['tracker_date'])){
    $tracker_date  = $_POST['tracker_date'];
    $query = "call getMonth('$tracker_date');";
    $result = mysqli_query($conn, $query);
    echo json_encode ($result);

}

?>