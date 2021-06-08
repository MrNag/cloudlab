using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Data.SqlClient;
using System.Configuration;

namespace DBAPP
{
    public partial class Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["testConnectionString"].ConnectionString);
            SqlCommand cmd = con.CreateCommand();
            cmd.CommandText="Insert into person (ID, Name, Email, Age) values("+Int32.Parse(txtID.Text)+",'"+txtName.Text+"','"+txtEmail.Text+"',"+Int32.Parse(txtAge.Text)+")";
            con.Open();
            cmd.ExecuteNonQuery();
            con.Close();
            Response.Redirect("~/Default.aspx");

        }
    }
}