<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Area</title>
    <link rel="stylesheet" href="css/bootstrap-337.min.css">
    <link rel="stylesheet" href="font-awsome/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top"><!-- navbar navbar-inverse navbar-fixed-top Begin -->
    <div class="navbar-header"><!-- navbar-header Begin -->

        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse"><!-- navbar-toggle Begin -->

            <span class="sr-only">Toggle Navigation</span>

            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>

        </button><!-- navbar-toggle Finish -->

        <a href="index.php?dashboard" class="navbar-brand">Admin Area</a>
        

    </div><!-- navbar-header Finish -->

    <ul class="nav navbar-right top-nav"><!-- nav navbar-right top-nav Begin -->

        <li class="dropdown"><!-- dropdown Begin -->

            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><!-- dropdown-toggle Begin -->

                <i class="fa fa-user"></i> <?php echo $admin_name; ?> <b class="caret"></b>

            </a><!-- dropdown-toggle Finish -->

            <ul class="dropdown-menu"><!-- dropdown-menu Begin -->
                <li><!-- li Begin -->
                    <a href="index.php?user_profile=<?php echo $admin_id; ?>"><!-- a href Begin -->

                        <i class="fa fa-fw fa-user"></i> Profile

                    </a><!-- a href Finish -->
                </li><!-- li Finish -->



                <li class="divider"></li>

                <li><!-- li Begin -->
                    <a href="logout.php"><!-- a href Begin -->

                        <i class="fa fa-fw fa-power-off"></i> Log Out

                    </a><!-- a href Finish -->
                </li><!-- li Finish -->

            </ul><!-- dropdown-menu Finish -->

        </li><!-- dropdown Finish -->

    </ul><!-- nav navbar-right top-nav Finish -->

    <div class="collapse navbar-collapse navbar-ex1-collapse"><!-- collapse navbar-collapse navbar-ex1-collapse Begin -->
        <ul class="nav navbar-nav side-nav"><!-- nav navbar-nav side-nav Begin -->
            <li><!-- li Begin -->
                <a href="index.php?dashboard"><!-- a href Begin -->

                    <i class="fa fa-fw fa-dashboard"></i> Dashboard

                </a><!-- a href Finish -->
            </li><!-- li Finish -->

            <li><!-- li Begin -->
              <a href="#" data-toggle="collapse" data-target="#slides"><!-- a href Begin -->

                  <i class="fa fa-fw fa-gear"></i> Berita
                  <i class="fa fa-fw fa-caret-down"></i>

              </a><!-- a href Finish -->

              <ul id="slides" class="collapse"><!-- collapse Begin -->
                  <li><!-- li Begin -->
                      <a href="index.php?insert_slide"> Tambah Berita </a>
                  </li><!-- li Finish -->
                  <li><!-- li Begin -->
                      <a href="index.php?view_slides"> Lihat Berita </a>
                  </li><!-- li Finish -->
              </ul>  <!-- collapse Finish -->

            </li><!-- li Finish -->

            <li><!-- li Begin -->
              <a href="#" data-toggle="collapse" data-target="#category"><!-- a href Begin -->

                  <i class="fa fa-fw fa-book"></i> Kehadiran
                  <i class="fa fa-fw fa-caret-down"></i>

              </a><!-- a href Finish -->

              <ul id="category" class="collapse"><!-- collapse Begin -->
                  <li><!-- li Begin -->
                      <a href="index.php?insert_category"> Lihat Kehadiran </a>
                  </li><!-- li Finish -->
                  <li><!-- li Begin -->
                      <a href="index.php?view_categories"> Statistika Kehadiran </a>
                  </li><!-- li Finish -->
              </ul>  <!-- collapse Finish -->

            </li><!-- li Finish -->
            

            <li><!-- li Begin -->
              <a href="#" data-toggle="collapse" data-target="#users"><!-- a href Begin -->

                  <i class="fa fa-fw fa-users"></i> Pegawai
                  <i class="fa fa-fw fa-caret-down"></i>

              </a><!-- a href Finish -->

              <ul id="users" class="collapse"><!-- collapse Begin -->
              <li><!-- li Begin -->
                      <a href="index.php?view_users" > Lihat Pegawai </a>
                  </li><!-- li Finish -->
                  <li><!-- li Begin -->
                      <a href="index.php?insert_user"> Tambah Data Pegawai </a>
                  </li><!-- li Finish -->
                  <li><!-- li Begin -->
                      <a href="index.php?user_profile=<?php echo $admin_id; ?>"> Edit User Profile </a>
                  </li><!-- li Finish -->
              </ul>  <!-- collapse Finish -->

            </li><!-- li Finish -->

            <li><!-- li Begin -->
                <a href="index.php?view_orders"><!-- a href Begin -->
                    <i class="fa fa-fw fa-book"></i> Perizinan
                </a><!-- a href Finish -->
            </li><!-- li Finish -->



            <li><!-- li Begin -->
                <a href="logout.php"><!-- a href Begin -->
                    <i class="fa fa-fw fa-power-off"></i> Log Out
                </a><!-- a href Finish -->
            </li><!-- li Finish -->

        </ul><!-- nav navbar-nav side-nav Finish -->
    </div><!-- collapse navbar-collapse navbar-ex1-collapse Finish -->

</nav><!-- navbar navbar-inverse navbar-fixed-top Finish -->

</body>
</html>