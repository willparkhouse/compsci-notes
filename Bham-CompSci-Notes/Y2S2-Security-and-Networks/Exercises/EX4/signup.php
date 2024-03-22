<?php
  session_start();

  // m'kay
  $drugsarebad = true;
  
  // get the database connection
  require_once("mysql.php");
  // override $drugsarebad and $dead if necessary
  require_once("whoami.php");

  $match = false; // password == confirmed password
  $existing = false; // username already exists

  // POST?
  if($_SERVER["REQUEST_METHOD"] == "POST") {
    // validate new and confirmed password
    $match = $_POST['password'] == $_POST['confirm'];

    if($match) { // ok?
      // add the user into the database
      $sql = "INSERT INTO users VALUES(default, '" . $_POST['username'] . "', '" .
		    mysqli_real_escape_string($db, $_POST['fullname']) . "', '".
        mysqli_real_escape_string($db, $_POST['password']) . "', default, '" .
        mysqli_real_escape_string($db, $_POST['email']) . "', default, default);";

      $result = mysqli_query($db, $sql);
      if(!$result) {
        // something happened
        if(mysqli_errno($db) == 1062) // already exists
          $existing = true;
        else // no idea.
          die("Query failed: " . mysqli_error($db));
      }

      mysqli_close($db);

      if(!$existing)
        header("Location: /signup-success.php");
        die();
    }
  }
?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Signup</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="/css/bootstrap<?php if(!$drugsarebad) echo(".ch"); ?>.min.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

    </style>
    <style>
      .container, .navbar-fixed-top .container {
        width: 800px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="/ico/apple-touch-icon-57-precomposed.png">
    <link rel="shortcut icon" href="/ico/favicon.png">
  </head>

  <body>
    <?php require_once("headnav.php"); ?>
    <div class="container">
      <form action="signup.php" method="post" class="form-signin" id="form">
        <h2 class="form-signin-heading">Registration</h2>
        <?php if($_SERVER["REQUEST_METHOD"] == "POST" && (!$match || $existing)) { ?>
          <div class="alert alert-error alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            <?php if($existing) { ?>
              Username is in use.
            <?php } ?>
            <?php if(!$match) { ?>
              Your passwords do not match.
            <?php } ?>
          </div>
        <?php } ?>
        <?php if($_SERVER["REQUEST_METHOD"] == "POST" && $existing) { ?>
          <div class="control-group error">
        <?php } ?>
        <input name="username" type="text" class="input-block-level" placeholder="Username">
        <input name="fullname" type="text" class="input-block-level" placeholder="Full name">
        <input name="email" type="text" class="input-block-level" placeholder="Email Address (bham.ac.uk)" id="email">
        <?php if($_SERVER["REQUEST_METHOD"] == "POST" && $existing) { ?>
          </div>
        <?php } ?>
        <?php if($_SERVER["REQUEST_METHOD"] == "POST" && !$match) { ?>
          <div class="control-group error">
        <?php } ?>
            <input name="password" type="password" class="input-block-level" placeholder="Password">
            <input name="confirm" type="password" class="input-block-level" placeholder="Confirm Password">
        <?php if($_SERVER["REQUEST_METHOD"] == "POST" && !$match) { ?>
          </div>
        <?php } ?>
        <button class="btn btn-large btn-primary" type="submit">Register</button>
      </form>
    </div> <!-- /container -->
    <script type="text/javascript" charset="utf8" src="/js/jquery-2.1.2.min.js"></script>
    <script type="text/javascript" charset="utf8" src="/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript">
      $(document).ready(function() {
        $("#form").submit(function(e) {
          if($("#email").val().indexOf('bham.ac.uk', $("#email").val().length - 'bham.ac.uk'.length) === -1) {
            $("#email").val("");
            $("#email").focus();
            return false;
          }
        });
      });
    </script>
  </body>
</html>
