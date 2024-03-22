<?php
  session_start();

  // m'kay
  $drugsarebad = true;
  
  // get the database connection
  require_once("../mysql.php");
  // override $drugsarebad and $dead if necessary
  require_once("../whoami.php");

  // logged in?
  if(!isset($_SESSION['user'])) {
    header("Location: ../");
    exit();
  }

  // successful save?
  $saved = false;

  // POST?
  if($_SERVER["REQUEST_METHOD"] == "POST") {
    // confirm user password
    if($_SESSION['user']['password'] == $_POST['current']
        && $_POST['confirm'] == $_POST['password']) {
      $clauses = array(); // what are we updating
      $ignore = array("confirm", "current", "id"); // what are we not updating
      if($_POST['password'] == '')
        $ignore[] = 'password';
      // this is where we get posh!
      foreach($_POST as $k => $v) {
        if(!in_array($k, $ignore))
          $clause[] = sprintf("$k='%s'", mysqli_real_escape_string($db, $v));
      }
	  
	  $sql = "UPDATE users SET ".implode(", ", $clause)." WHERE id=".mysqli_real_escape_string($db, $_POST['id']).";";

      $result = mysqli_query($db, $sql);
      if($result) {
        // update the password in the session (probably shouldn't be there)
        if($_POST['id'] == $_SESSION['user']['id'] && $_POST['password'] != '') {
          $_SESSION['user']['password'] = $_POST['password'];
        }
        $saved = true; // success
      }
    }
  }

   // get me from the database
   $sql = "SELECT * FROM users WHERE id=".mysqli_real_escape_string($db, $_SESSION['user']['id']).";";

  $result = mysqli_query($db, $sql);
  if(!$result)
    die("Query failed: " . mysqli_error($db));

  // put me into the session
  $user = $_SESSION['user'] = mysqli_fetch_assoc($result);

  mysqli_close($db);
?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Account Settings</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="/css/bootstrap<?php if(!$drugsarebad) echo ".ch"; ?>.min.css" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
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
    <?php require_once("../headnav.php"); ?>
    <div class="container">
      <?php
        if($_SERVER["REQUEST_METHOD"] == "POST") {
          if($saved) {
        ?>
          <div class="alert alert-success alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            Your account has been updated successfully.
          </div>
        <?php
          } else {
        ?>
          <div class="alert alert-error alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            Your details couldn't be updated. Please try again later.
          </div>
        <?php
  
          }
        }
      ?>
      <h1>My Account Settings</h1>
      <form action="settings.php" method="post" id="form">
        <fieldset>
          <legend>Contact Form</legend>
          <label>Username</label>
          <input name="name" type="text" placeholder="Your username..." value="<?php echo(htmlentities($user['name'])); ?>" disabled="disabled" />
          <label>Full Name</label>
          <input name="full" type="text" placeholder="Your full name..." value="<?php echo(htmlentities($user['full'])); ?>" />
          <label>Email</label>
          <input name="email" type="text" placeholder="Email Address (bham.ac.uk)" value="<?php echo(htmlentities($user['email'])); ?>" id="email"/>
          <label>Password</label>
          <input name="password" type="password" placeholder="New password..." />
          <label>Confirm Password</label>
          <input name="confirm" type="password" placeholder="Confirm password..." />
          <input name="id" type="hidden" value="<?php echo(htmlentities($user['id'])); ?>" />
          <div class="form-actions">
            <label>Current Password</label>
            <input name="current" type="password" placeholder="Current password..." value="<?php echo($user['password']); ?>" /><br />
            <button type="submit" class="btn btn-primary">Send</button>
            <button type="reset" class="btn">Clear</button>
          </div>
        </fieldset>
      </form>
    </div> <!-- /container -->
    <script type="text/javascript" charset="utf8" src="/js/jquery-2.1.2.min.js"></script>
    <script type="text/javascript">
      $(document).ready(function() {
        $("#form").submit(function(e) {
          if($("#email").val().indexOf('bham.ac.uk', $("#email").val().length - 'bham.ac.uk'.length) === -1) {
            alert("Email addresses must end in bham.ac.uk");
            $("#email").focus();
            return false;
          }
        });
      });
    </script>
  </body>
</html>
