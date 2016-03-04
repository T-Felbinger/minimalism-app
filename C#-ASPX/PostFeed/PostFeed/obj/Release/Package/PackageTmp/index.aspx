<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="index.aspx.cs" Inherits="PostFeed.index" %>

<!DOCTYPE html>


<html>
	<head>
		<link href='https://fonts.googleapis.com/css?family=Raleway' rel='stylesheet' type='text/css'>
		<style>
			body, html {
				background-color:#ececec;
				font-family: 'Raleway', sans-serif;
			}
			.card{
				box-shadow: 0 1px 1.5px 0 rgba(0,0,0,.12), 0 1px 1px 0 rgba(0,0,0,.24);
				border-radius: 5px;
				background-color:white;
				text-align:center;
				margin: 10%;
				overflow: hidden;
				font-size: 40px;
			}
		</style>
		
		<!--js for pulling sql database info -->
		<script src="http://code.jquery.com/jquery-2.2.1.min.js"></script>
		<script>

		    var setMax = 1;




			var content = [
				{

					img: "https://genesistransformation.files.wordpress.com/2014/11/coffee.jpg", 
					desc: "coffee is coffee.", 
					cat: "drink"
				},
				{
					img: "https://genesistransformation.files.wordpress.com/2014/11/coffee.jpg", 
					desc: "coffee is not coffee.", 
					cat: "drink"
				}

			]







	//		var Footer = 
	//			'<div class="w3-container w3-grey">' +
	//		  	'<h5 id="footer">'+Footer+'</h5>' +
	//		  	'<p id="footer_info">'+Info+'</p>' +
	//			'</div>';

			window.onload = function () {


			    /*for (var i = 0; i < setMax; i++)
			    {
			        
			        PageMethods.GrabImg(i);
			        content[i + 1].img = document.getElementById("TextBox").value;

			        PageMethods.GrabDesc(i);
			        content[i + 1].desc = document.getElementById("TextBox").value;
			        
			        PageMethods.GrabCat(i);
			        content[i + 1].cat = document.getElementById("TextBox").value;
			        
			       
			    }
                */



			/*	for (var i = 0; i < content.length; i++)
					$("body").append("<div class='card'>  <img src='" + content[i].img + "'style='width: 100%'>  <h2>Description</h2>  <p>" + content[i].desc + "</p>  <h2>Catagory</h2>  <p>" + content[i].cat + "</p></div>");
			};
            */

		</script>		
	
	</head>

	<body>
		<form id="form1" runat="server">
    <asp:ScriptManager ID='ScriptManager1' runat='server' EnablePageMethods='true' />
    <div>  

        <asp:DropDownList ID="DDL" OnTextChanged="ShowCharity" runat="server">
            <asp:ListItem Text="Clothing" Value="1" Selected="True"></asp:ListItem>
                <asp:ListItem Text="Books" Value="2"></asp:ListItem>
                    <asp:ListItem Text="Toys" Value="3"></asp:ListItem>
                        <asp:ListItem Text="School" Value="4"></asp:ListItem>
                            <asp:ListItem Text="Electronics" Value="5"></asp:ListItem>
                                <asp:ListItem Text="Kitchen" Value="6"></asp:ListItem>
                                    <asp:ListItem Text="Furniture" Value="7"></asp:ListItem>


        </asp:DropDownList><asp:Button ID="Button" onClick="ShowCharity" runat="server" Text="Go" />


        <asp:GridView ID="Charity" runat="server"></asp:GridView>

        <asp:GridView ID="GridView" runat="server"></asp:GridView>

        
    </div>
    </form>
		<div style="margin:1.5%"></div>





	</body>
</html>