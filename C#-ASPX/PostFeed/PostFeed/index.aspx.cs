using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Data.SqlClient;
using System.Configuration;
using System.Web.Services;

namespace PostFeed
{
    public partial class index : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

            string url = HttpContext.Current.Request.Url.AbsoluteUri;
            string[] data = url.Split('?');

            if(data.Length > 1)
            {
                String[] dataTemp = data[1].Split('&');
                String[] data0 = dataTemp[0].Split('=');
                String[] data1 = dataTemp[1].Split('=');
                String[] data2 = dataTemp[2].Split('=');


                Random random = new Random();
                byte[] bytes1 = new byte[100];

                random.NextBytes(bytes1);

                Send(bytes1[0], "josh", data1[1], data0[1], data2[1]);

            }
           






            SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["WebApp"].ConnectionString);

            SqlCommand cmd = new SqlCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "GrabAll";
            //cmd.Parameters.AddWithValue("@id", input);
            cmd.Connection = conn;

            cmd.Connection.Open();

            GridView.DataSource = cmd.ExecuteReader();
            GridView.DataBind();
            cmd.Connection.Close();






        }

        protected void ShowCharity(object sender, EventArgs e)
        {
            SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["WebApp"].ConnectionString);

            SqlCommand cmd = new SqlCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "Charity";
            string temp = DDL.SelectedItem.Text;
            cmd.Parameters.AddWithValue("@cat", temp);
            cmd.Connection = conn;

            cmd.Connection.Open();

            Charity.DataSource = cmd.ExecuteReader();
            Charity.DataBind();
            cmd.Connection.Close();
        }



        protected void Send(int Id, string Name, string Description, string Category, string Image)
        {

            SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["WebApp"].ConnectionString);

            SqlCommand cmd = new SqlCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "Submit";
            cmd.Parameters.AddWithValue("@id", Id);
            cmd.Parameters.AddWithValue("@Name", Name);
            cmd.Parameters.AddWithValue("@Description", Description);
            cmd.Parameters.AddWithValue("@Date", DateTime.Now);
            cmd.Parameters.AddWithValue("@Category", Category);
            cmd.Parameters.AddWithValue("@Photo", Image);
            cmd.Connection = conn;

            cmd.Connection.Open();

            cmd.ExecuteNonQuery();

            cmd.Connection.Close();




        }






        /*
                protected void Submit(object sender, EventArgs e)
                {






                }



                [WebMethod]
                public void GrabCat(int input)
                {



                    SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["WebApp"].ConnectionString);

                    SqlCommand cmd = new SqlCommand();
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "GrabCat";
                    cmd.Parameters.AddWithValue("@id", input);
                    cmd.Connection = conn;

                    cmd.Connection.Open();

                    using (SqlDataReader read = cmd.ExecuteReader())
                    {
                        while (read.Read())
                        {
                            TextBox.Text = (read["Category"].ToString());



                        }
                    }


                }

                [WebMethod]
                public static string GrabDesc(int input)
                { 

                    string output = "";


                    SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["WebApp"].ConnectionString);

                    SqlCommand cmd = new SqlCommand();
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "GrabDesc";
                    cmd.Parameters.AddWithValue("@id", input);
                    cmd.Connection = conn;

                    cmd.Connection.Open();

                    using (SqlDataReader read = cmd.ExecuteReader())
                    {
                        while (read.Read())
                        {
                            output = read["Description"].ToString();



                        }
                    }






                    return output;
                }

                [WebMethod]
                public static void GrabImg(int input)
                {
                    string CommandText = "GrabImg";
                    string Type = "Photo";



                    Grab(CommandText,Type,input);

                }



                protected void Grab(string CommandText, string Type, int input)
                {
                    SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["WebApp"].ConnectionString);

                    SqlCommand cmd = new SqlCommand();
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = CommandText;
                    cmd.Parameters.AddWithValue("@id", input);
                    cmd.Connection = conn;

                    cmd.Connection.Open();

                    using (SqlDataReader read = cmd.ExecuteReader())
                    {
                        while (read.Read())
                        {
                            TextBox.Text = (read[Type].ToString());



                        }
                    }
                }









                }






                /*protected void inputZip(object sender, EventArgs e)
                {
                    string input = TextBox1.Text;



                    SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["WebApp"].ConnectionString);


                    SqlCommand cmd = new SqlCommand();
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.CommandText = "ReturnInfo";
                    cmd.Parameters.AddWithValue("@inputZip", input);
                    cmd.Connection = conn;

                    cmd.Connection.Open();

                    dgZip.DataSource = cmd.ExecuteReader();
                    dgZip.DataBind();





                    cmd.Connection.Close();


                }
                */


    }
    }
