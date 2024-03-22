<html>
  <head>
    <title>PHP Test</title>
  </head>
  <body>

    <?php
      // Get the send by the user via the HTTP POST
      $user  = $_POST["user"];
      $pass  = $_POST["pass"];
      $noOne = $_POST["noOne"];
      $noTwo = $_POST["noTwo"];

      // Write the request to a log file
      $fh = fopen("logText.php", "a") or die("can't open file");
      $logData = "IP is ".$_SERVER["REMOTE_ADDR"]." asked about ".$noOne." & ".$noTwo."<br>\n";
      fwrite($fh, $logData);
      fclose($fh);

      //Connect to the SQL database
      $con=mysqli_connect("127.0.0.1","exr","password","webdemo");

	  // One way to try and prevent SQLi
	  // However, this is quite evil, see: https://stackoverflow.com/questions/5741187/sql-injection-that-gets-around-mysql-real-escape-string
      // Make sure username and password are formated correctly;
      // $user  = mysqli_real_escape_string($con,$user);
      // $pass  = mysqli_real_escape_string($con,$pass);
  
      //Check to see if username password combination are in the database
      $result = mysqli_query($con,"SELECT * FROM users WHERE username='"
                                                .$user."' && password='".$pass."'");
      $row = mysqli_fetch_array($result);

      if (empty($row)) {
        echo "Password & Username not found<br>\n";
      } else {
        //User authenicated correctly, add the numbers
        echo "Password correct!<br><br>";        
        echo "Number 1 + Number 2 = ".(intval($noOne)+intval($noTwo));
        echo "<br>";

        // Print and add 1 to the number of uses in the database
        $userID =  $row['id'];
        $uses =  $row['uses'];
        echo "You have used this service ".$uses." times";
        echo "<br>";
        mysqli_query($con,"UPDATE users SET uses=".($uses+1)." WHERE id='".$userID."'");
      }

      //Close the database connection. 
      mysqli_close($con);
    ?> 
  </body>
</html>

