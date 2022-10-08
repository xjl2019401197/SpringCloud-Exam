<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Jc</title>
  <link rel="stylesheet" href="/static/font-awesome-4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="/static/index.css">
  <script src="/static/tool/jquery/jquery.js"></script>
  <style>
    .main{
      display: flex;
      height: 30px;
      width: 250px;
      margin-bottom: 10px;
      margin-top: 10px;
    }
    .main .item{
      flex: 1;
      text-align: center;
    }
    .main .item input{
      width: 20px !important;
      box-shadow:none;/*去除input阴影边框*/
    }
  </style>
  <script>
    $(function (){
      var hidden = $("#message").val();
      if (hidden !== "" && hidden != null) {
        alert(hidden)
      }
    })

  </script>
</head>

<body>
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
    <path fill="#0099ff" fill-opacity="1"
      d="M0,192L48,197.3C96,203,192,213,288,229.3C384,245,480,267,576,250.7C672,235,768,181,864,181.3C960,181,1056,235,1152,234.7C1248,235,1344,181,1392,154.7L1440,128L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z">
    </path>
  </svg>
  <input type="hidden" id="message" value="${message}">
  <div class="user">
    <i class="fa fa-user"></i>
    <div class="head">account login</div>
  </div>
  <div class="container">
    <div class="box login">
      <div class="form-content">
        <div class="avtar">
          <div class="pic"><img src="static/img/1.jpg" alt=""></div>
        </div>
        <h1>Welcome back</h1>
        <form action="/tologin" class="form">
          <div>
            <i class="fa fa-user-o"></i>
            <input type="text" placeholder="username"  name="username" >
          </div>
          <div>
            <i class="fa fa-key"></i>
            <input type="password" placeholder="password"  name="Password">
          </div>

          <div class="main">
            <div class="item">
              学生<input type="radio" name="type" class="student" checked="checked"  value="student">
            </div>

            <div class="item">
              教师<input type="radio" name="type" class="teacher"  value="teacher">
            </div>

            <div class="item">
              管理员<input type="radio" name="type" class="admin"  value="admin">
            </div>

          </div>

          <div class="btn">
            <button>login</button>
          </div>
        </form>
        <p class="btn-something">
          Don't have an account ? <span class="signupbtn">signup</span>
        </p>
      </div>
    </div>
    <div class="box signup">
      <div class="form-content">
        <div class="avtar">
          <div class="pic"><img src="/static/img/2.jpg" alt=""></div>
        </div>
        <h1>Let's get started</h1>
        <form action="/register" class="form" method="post">
          <div>
            <i class="fa fa-user-o"></i>
            <input type="text" placeholder="username" name="username">
          </div>
         <%-- <div>
            <i class="fa fa-envelope-o"></i>
            <input type="email" placeholder="email">
          </div>--%>
          <div>
            <i class="fa fa-key"></i>
            <input type="password" placeholder="password" name="Password">
          </div>
          <div class="main">
            <div class="item">
              学生<input type="radio" name="type" class="student" checked="checked" value="student">
            </div>

            <div class="item">
              教师<input type="radio" name="type" class="teacher"  value="teacher">
            </div>

            <div class="item">
              管理员<input type="radio" name="type" class="admin"  value="admin">
            </div>

          </div>
          <div class="btn">
            <button>signup</button>
          </div>
        </form>
        <p class="btn-something">
          Already have an account ? <span class="loginbtn">login</span>
        </p>
      </div>
    </div>
  </div>
</body>
<script>
  let login = document.querySelector(".login");
  let signup = document.querySelector(".signup");

  let loginbtn = document.querySelector(".loginbtn");
  let siginupbtn = document.querySelector(".signupbtn");

  let user = document.querySelector(".head");

  siginupbtn.addEventListener("click", () => {
    login.style.transform = "rotateY(180deg)"
    signup.style.transform = "rotateY(0deg)";

    user.innerHTML = "create account"
  })

  loginbtn.addEventListener("click", () => {
    login.style.transform = "rotateY(0deg)"
    signup.style.transform = "rotateY(-180deg)";

    user.innerHTML = "account login"
  })

</script>

</html>
