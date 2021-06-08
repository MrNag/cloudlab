<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Default.aspx.cs" Inherits="DBAPP.Default" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
    <center>
        <div>
        ID: <asp:TextBox ID="txtID" runat="server"></asp:TextBox>
        <br />
        Name: <asp:TextBox ID="txtName" runat="server"></asp:TextBox>
        <br />
        Email: <asp:TextBox ID="txtEmail" runat="server"></asp:TextBox>
        <br />
        Age: <asp:TextBox ID="txtAge" runat="server"></asp:TextBox>
        <br />
        <asp:Button ID="Button1" runat="server" OnClick="Button1_Click" Text="Insert" />
    </center>
        <asp:GridView ID="GridView1" HorizontalAlign="Center" runat="server" BackColor="White" BorderColor="#DEDFDE" BorderStyle="None" BorderWidth="1px" CellPadding="4" DataSourceID="AWSSQLServer" ForeColor="Black" GridLines="Vertical" AutoGenerateColumns="False">
            <AlternatingRowStyle BackColor="White" />
            <Columns>
                <asp:CommandField ShowDeleteButton="True" ShowEditButton="True" />
                <asp:BoundField HeaderText="ID" DataField="ID"/>
                <asp:BoundField HeaderText="Name" DataField="Name"/>
                <asp:BoundField HeaderText="Email" DataField="Email"/>
                <asp:BoundField HeaderText="Age" Datafield="age"/>
            </Columns>
            <FooterStyle BackColor="#CCCC99" />
            <HeaderStyle BackColor="#6B696B" Font-Bold="True" ForeColor="White" />
            <PagerStyle BackColor="#F7F7DE" ForeColor="Black" HorizontalAlign="Right" />
            <RowStyle BackColor="#F7F7DE" />
            <SelectedRowStyle BackColor="#CE5D5A" Font-Bold="True" ForeColor="White" />
            <SortedAscendingCellStyle BackColor="#FBFBF2" />
            <SortedAscendingHeaderStyle BackColor="#848384" />
            <SortedDescendingCellStyle BackColor="#EAEAD3" />
            <SortedDescendingHeaderStyle BackColor="#575357" />
        </asp:GridView>
        <asp:SqlDataSource ID="AWSSQLServer" runat="server" ConnectionString="<%$ ConnectionStrings:testConnectionString %>" DeleteCommand="DELETE FROM [person] WHERE [ID] = @ID" InsertCommand="INSERT INTO [person] ([ID], [Name], [Email], [age]) VALUES (@ID, @Name, @Email, @age)" SelectCommand="SELECT * FROM [person]" UpdateCommand="UPDATE [person] SET [Name] = @Name, [Email] = @Email, [age] = @age WHERE [ID] = @ID">
            <DeleteParameters>
                <asp:Parameter Name="ID" Type="Int32" />
            </DeleteParameters>
            <InsertParameters>
                <asp:Parameter Name="ID" Type="Int32" />
                <asp:Parameter Name="Name" Type="String" />
                <asp:Parameter Name="Email" Type="String" />
                <asp:Parameter Name="age" Type="Int32" />
            </InsertParameters>
            <UpdateParameters>
                <asp:Parameter Name="Name" Type="String" />
                <asp:Parameter Name="Email" Type="String" />
                <asp:Parameter Name="age" Type="Int32" />
                <asp:Parameter Name="ID" Type="Int32" />
            </UpdateParameters>
        </asp:SqlDataSource>
    </div>
    </form>
</body>
</html>
