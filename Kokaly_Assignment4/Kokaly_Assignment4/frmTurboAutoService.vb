'------------------------------------------------------------
'-                File Name: Kokaly_Assignment4             - 
'-   Part of Project: Assignment #4 (Turbo Auto Service)    -
'------------------------------------------------------------
'-                Written By: Spencer Kokaly                -
'-                Written On: 10/27/14                      -
'------------------------------------------------------------
'- File Purpose:                                            -
'- This file holds the main part of the program and houses  -
'- the form that is used in the program. This uses the      -
'- subroutines that are in the module to help fuel the      -
'- tasks that this file performs.                           -
'------------------------------------------------------------
'- Program Purpose:                                         -
'- This program allows the user to enter files that give    -
'- information about their customers. The program then      -
'- schedules all of the jobs in FCFS, unless there is a     -
'- time opening before. The user can add customers,         -
'- vehicles, and schedule individual jobs. The program also -
'- says how much the mechanics should be paid.              -
'------------------------------------------------------------
'- Global Variable Dictionary:                              -
'- gstrConnString - A String that holds the connection      -
'- string to the Database.                                  -
'- gstrDBName - A String that holds the Database name.      -
'- DBCmd - The OleDbCommand that is used to execute the     -
'- queries.                                                 -
'- DBConn - The OleDbConnection that is used to connect to  -
'- the database.                                            -
'- DBAdapterCustomers - The Adapter for the Customers.      -
'- DBAdapterServices - The Adapter for the Services.        -
'- DBAdapterVehicles - The Adapter for the Vehicles.        -
'- dsCustomers - The DataSet for Customers.                 -
'- dsSchedule - The DataSet for Schedule.                   -
'- dsServices - The DataSet for Services.                   -
'- dsVehicles - The DataSet for Vehicles.                   -
'- intScheduleTUID - An Integer to hold the Schedule TUID.  -
'------------------------------------------------------------

'Import(s)
Imports System.Data.OleDb

Public Class frmTurboAutoService

    'DB Name and Connection String
    Public Const gstrDBName As String = "TurboAutoService.accdb"
    Public Const gstrConnString As String = "Provider=Microsoft.ACE.OLEDB.12.0;Data Source=" & gstrDBName & ";"

    'String For All of the SQL Commands
    Dim strSQLcmd As String = Nothing

    'Connection to the Database to Run Queries
    Dim DBConn As New OleDbConnection(gstrConnString)

    'Command Builder
    Dim DBCmd As OleDbCommand = New OleDbCommand()

    'Holding the Information from the Database
    Dim DBAdapterCustomers As New OleDbDataAdapter
    Dim DBAdapterVehicles As New OleDbDataAdapter
    Dim DBAdapterServices As New OleDbDataAdapter
    Dim dsCustomers As New DataSet()
    Dim dsVehicles As New DataSet()
    Dim dsServices As New DataSet()
    Dim dsSchedule As New DataSet()

    'Schedule TUID Locater
    Dim intScheduleTUID As Integer = 1


    '------------------------------------------------------------
    '-          Subprogram Name: cmdAddCustomer_Click           -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/21/14                      -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- Adding a customer to the database. Checks to make sure   -
    '- the customer doesn't already exist.                      -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- sender – Identifies which particular control raised the  -
    '-          event                                           - 
    '- e – Holds the EventArgs object sent to the subroutine    -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- intCustTUID - An Integer that holds the current          -
    '- customer TUID.                                           -
    '- strInitial - A String that holds the first initial of    -
    '- the custoemr.                                            -
    '- strLastName - A String that holds the last name of the   -
    '- customer.                                                -
    '------------------------------------------------------------
    Private Sub cmdAddCustomer_Click(sender As Object, e As EventArgs) Handles cmdAddCustomer.Click

        'Local Variables
        Dim strInitial As String
        Dim strLastName As String
        Dim intCustTUID As Integer

        'Getting Information and Checking Blank Entries
        strInitial = InputBox("What Is The Customer's First Initial?", "Customer Information")
        strInitial = strInitial.Replace(" ", "")

        strLastName = InputBox("What Is The Customer's Last Name?", "Customer Information")
        strLastName = strLastName.Replace(" ", "")

        'Checking for Entered Values
        If (strInitial = "" Or strLastName = "") Then
            MessageBox.Show("Invalid Customer", "Customer Not Added")
        Else

            'Checking Initial
            strInitial = strInitial.Substring(0, 1)
            strInitial = strInitial.ToUpper + "."

            'Cleaning Up Last Name
            strLastName = strLastName.Substring(0, 1).ToUpper + strLastName.Substring(1, strLastName.Length - 1)

            'Checking if DB is Open
            Try
                'Opening DB Connection
                DBConn.Open()
            Catch ex As Exception
            End Try

            'Seeing If Customer Exists
            DBCmd.CommandText = "SELECT COUNT(*) FROM Customers WHERE LastName = '" & strLastName & "' AND FirstInitial = '" & strInitial & "'"

            DBCmd.Connection = DBConn
            DBCmd.ExecuteNonQuery()

            'Seeing What the Count Results Was
            If (DBCmd.ExecuteScalar = 0) Then

                'Getting Last TUID Number
                DBCmd.CommandText = "SELECT COUNT(*) FROM Customers"

                DBCmd.Connection = DBConn
                DBCmd.ExecuteNonQuery()

                'Seeing What the Count Results Was
                intCustTUID = DBCmd.ExecuteScalar

                'Adding Customer to Customers Table
                DBCmd.CommandText = "INSERT INTO Customers (TUID, FirstInitial, LastName) " & _
                                    "VALUES ('" & intCustTUID + 1 & "', '" & _
                                    strInitial & "', '" & strLastName & "')"

                DBCmd.Connection = DBConn
                DBCmd.ExecuteNonQuery()

                'Closing DB Connection
                DBConn.Close()

                'Clearing Customers DataGridView
                dsCustomers.Clear()

                'Reloading the Customers
                strSQLcmd = "SELECT * FROM Customers ORDER BY TUID"
                DBAdapterCustomers = New OleDbDataAdapter(strSQLcmd, DBConn)
                DBAdapterCustomers.Fill(dsCustomers, "Customers")
                dgvCustomers.DataSource = dsCustomers.Tables("Customers")

            Else
                MessageBox.Show("Customer Exists!", "Customer Not Added")
            End If

            'Closing DB Connection
            DBConn.Close()

        End If

    End Sub

    '------------------------------------------------------------
    '-          Subprogram Name: cmdAddVehicle_Click            -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/22/14                      -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- When the Add Vehicle button is clicked this subroutine   -
    '- is called to add the vehcile to the customer             -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- sender – Identifies which particular control raised the  -
    '-          event                                           - 
    '- e – Holds the EventArgs object sent to the subroutine    -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- intCustTUID - An Integer that holds the current          -
    '- customer TUID.                                           -
    '- intCust_To_VehicleTUID - An Integer that holds the       -
    '- Customer_To_Vehicle TUID.                                - 
    '- intVehicleTUID - An Integer that holds the current       -
    '- vehicle TUID.                                            -
    '- strManuName - A String that holds the vehicle's          -
    '- manufacturer name.                                       -
    '- strManuTemp - A String that checks for only spaces for   -
    '- the manufacturer.                                        -
    '- strModelName - A String that holds the vehicle's model   -
    '- name.                                                    -
    '- strModelTemp - A String that checks for only spaces for  -
    '- the model.                                               -
    '------------------------------------------------------------
    Private Sub cmdAddVehicle_Click(sender As Object, e As EventArgs) Handles cmdAddVehicle.Click

        'Local Variables
        Dim intCustTUID As Integer
        Dim intVehicleTUID As Integer
        Dim intCust_To_VehicleTUID As Integer
        Dim strManuName As String
        Dim strModelName As String
        Dim strManuTemp As String
        Dim strModelTemp As String

        'Getting Information
        strManuName = InputBox("What Is The Vehicle's Manufacturer Name?", "Vehicle Information")
        strManuTemp = strManuName.Replace(" ", "")

        strModelName = InputBox("What Is The Vehicle's Model Name?", "Vehicle Information")
        strModelTemp = strModelName.Replace(" ", "")


        'Checking for Entered Values
        If (strManuTemp = "" Or strModelTemp = "") Then
            MessageBox.Show("Invalid Vehicle", "Vehicle Not Added")
        Else

            'Checking if DB Connection is Open
            Try
                'Opening DB Connection
                DBConn.Open()
            Catch ex As Exception
            End Try

            'Seeing if Vehicle Exists in Vehicles Table
            DBCmd.CommandText = "SELECT COUNT (*) FROM Vehicles WHERE Manufacturer = '" & strManuName & "' AND Model = '" & strModelName & "'"

            DBCmd.Connection = DBConn
            DBCmd.ExecuteNonQuery()

            'Seeing What the Count Results Was
            If (DBCmd.ExecuteScalar = 0) Then

                'Adding Vehicle to Vehicle Table

                'Getting Last TUID Number
                DBCmd.CommandText = "SELECT COUNT(*) FROM Vehicles"

                DBCmd.Connection = DBConn
                DBCmd.ExecuteNonQuery()

                'Seeing What the Count Results Was
                intVehicleTUID = DBCmd.ExecuteScalar

                'Adding Vehicle to Vehicles Table
                DBCmd.CommandText = "INSERT INTO Vehicles (TUID, Manufacturer, Model) " & _
                                    "VALUES ('" & intVehicleTUID + 1 & "', '" & strManuName & _
                                    "', '" & strModelName & "')"

                DBCmd.Connection = DBConn
                DBCmd.ExecuteNonQuery()

            Else

                'Seeing Where Vehicle Exists in Vehicles Table
                DBCmd.CommandText = "SELECT TUID FROM Vehicles WHERE Manufacturer = '" & strManuName & "' AND Model = '" & strModelName & "'"

                DBCmd.Connection = DBConn
                DBCmd.ExecuteNonQuery()

                intVehicleTUID = DBCmd.ExecuteScalar
                intVehicleTUID -= 1

            End If

            'Loading the Customer's Vehicle(s)
            intCustTUID = dgvCustomers.CurrentCell.RowIndex

            'Seeing If Vehicle for Customer Exists
            DBCmd.CommandText = "SELECT COUNT(*) FROM Customer_To_Vehicle WHERE Customer_To_Vehicle.VehicleTUID = " & _
                                intVehicleTUID + 1 & " AND Customer_To_Vehicle.CustomerTUID = " & intCustTUID + 1 & ""

            DBCmd.Connection = DBConn
            DBCmd.ExecuteNonQuery()

            'Seeing What the Count Results Was
            If (DBCmd.ExecuteScalar = 0) Then

                'Getting Last TUID Number
                DBCmd.CommandText = "SELECT COUNT(*) FROM Customer_To_Vehicle"

                DBCmd.Connection = DBConn
                DBCmd.ExecuteNonQuery()

                'Seeing What the Count Results Was
                intCust_To_VehicleTUID = DBCmd.ExecuteScalar

                'Adding 'Customer to Vehicle' to Customer_To_Vehicle Table
                DBCmd.CommandText = "INSERT INTO Customer_To_Vehicle (TUID, CustomerTUID, VehicleTUID) " & _
                                    "VALUES ('" & intCust_To_VehicleTUID + 1 & "', '" & intCustTUID + 1 & "', '" & intVehicleTUID + 1 & "')"

                DBCmd.Connection = DBConn
                DBCmd.ExecuteNonQuery()

                'Clearing Vehicles DataGridView
                dsVehicles.Clear()

                'Loading the Customer's Vehicle(s)
                intCustTUID = dgvCustomers.CurrentCell.RowIndex

                strSQLcmd = "SELECT Vehicles.TUID, Vehicles.Manufacturer, Vehicles.Model FROM Vehicles, Customer_To_Vehicle WHERE " & _
                            "Customer_To_Vehicle.CustomerTUID = " & intCustTUID + 1 & " AND Customer_To_Vehicle.VehicleTUID = Vehicles.TUID"
                DBAdapterVehicles = New OleDbDataAdapter(strSQLcmd, DBConn)
                DBAdapterVehicles.Fill(dsVehicles, "Vehicles")
                dgvVehicles.DataSource = dsVehicles.Tables("Vehicles")

            Else 'Showing that Vehicle Already Exists
                MessageBox.Show("Customer Already Has That Vehicle", "Vehicle Already Exists!")
            End If

            'Closing Connection
            DBConn.Close()

        End If

    End Sub

    '------------------------------------------------------------
    '-          Subprogram Name: cmdProcessFile_Click           -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/26/14                      -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- When the Process File button is clicked, the             -
    '- PopulateBasedOnFile and ScheduleServices Subroutines are -
    '- called and the datagridviews are updated accordingly     -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- sender – Identifies which particular control raised the  -
    '-          event                                           - 
    '- e – Holds the EventArgs object sent to the subroutine    -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- None.                                                    -
    '------------------------------------------------------------
    Private Sub cmdProcessFile_Click(sender As Object, e As EventArgs) Handles cmdProcessFile.Click

        'Calling Function to Populate Based on File
        modDatabase.PopulateBasedOnFile(gstrConnString)

        'Clearing Customers DataGridView
        dsCustomers.Clear()

        'Reloading the Customers
        strSQLcmd = "SELECT * FROM Customers ORDER BY TUID"
        DBAdapterCustomers = New OleDbDataAdapter(strSQLcmd, DBConn)
        DBAdapterCustomers.Fill(dsCustomers, "Customers")
        dgvCustomers.DataSource = dsCustomers.Tables("Customers")

        'Scheduling the Services in the File
        ScheduleServices()

    End Sub

    '------------------------------------------------------------
    '-         Subprogram Name: cmdScheduleService_Click        -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/27/14                      -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- To add an additonal job to the schedule table. Calls the -
    '- ScheduleServices sub to rerun the schedule.              -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- sender – Identifies which particular control raised the  -
    '-          event                                           - 
    '- e – Holds the FormClosingEventArgs object sent to the    -
    '- subroutine                                               -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- intCustTUID - An Integer that holds the current          -
    '- customer TUID.                                           -
    '- intCust_To_VehicleTUID - An Integer that holds the       -
    '- Customer_To_Vehicle TUID.                                - 
    '- intScheduleTUID - An Integer to see hold the current     -
    '- schedule TUID.                                           -
    '- intServiceTUID - An Integer to see what the service TUID -
    '- is for the corresponding service name.                   -
    '- intVehicleTUID - An Integer that holds the current       -
    '- vehicle TUID.                                            -
    '------------------------------------------------------------
    Private Sub cmdScheduleService_Click(sender As Object, e As EventArgs) Handles cmdScheduleService.Click

        'Local Variables
        Dim intCustTUID As Integer
        Dim intServiceTUID As Integer
        Dim intVehicleTUID As Integer
        Dim intCust_To_VehicleTUID As Integer
        Dim intScheduleTUID As Integer

        'Checking if DB Connection is Open
        Try
            'Opening DB Connection
            DBConn.Open()
        Catch ex As Exception
        End Try

        'Getting Datagridview Values
        intCustTUID = (dgvCustomers.CurrentRow.Index) + 1
        intServiceTUID = (dgvServices.CurrentRow.Index) + 1

        'Seeing if A Vehicle was Selected
        Try
            intVehicleTUID = dgvVehicles.CurrentRow.Cells(0).Value

            'Getting Cust_To_Vehicle TUID
            DBCmd.CommandText = "SELECT TUID FROM Customer_To_Vehicle WHERE CustomerTUID = " & intCustTUID & _
                                " AND VehicleTUID = " & intVehicleTUID & ""

            DBCmd.Connection = DBConn
            DBCmd.ExecuteNonQuery()

            intCust_To_VehicleTUID = DBCmd.ExecuteScalar

            'Getting Schedule TUID
            DBCmd.CommandText = "SELECT COUNT(*) FROM Schedule"

            DBCmd.Connection = DBConn
            DBCmd.ExecuteNonQuery()

            intScheduleTUID = DBCmd.ExecuteScalar

            'Inserting Into Schedule Table
            DBCmd.CommandText = "INSERT INTO Schedule (TUID, CustToVehicleTUID, ServiceTUID) " & _
                                        "VALUES ('" & intScheduleTUID + 1 & "', '" & intCust_To_VehicleTUID & "', '" & intServiceTUID & "')"

            DBCmd.Connection = DBConn
            DBCmd.ExecuteNonQuery()

            'Rescheduling
            ScheduleServices()

        Catch
            Dim ex As Exception
            MessageBox.Show("Customer Has No Vehicle!", "No Vehicle!")
        End Try

    End Sub

    '------------------------------------------------------------
    '-     Subprogram Name: dgvCustomers_SelectionChanged       -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/21/14                      -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- When the Customers DataGridView changes, a new set of    -
    '- vehicles are shown in the Vehicles DataGridView.         -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- sender – Identifies which particular control raised the  -
    '-          event                                           - 
    '- e – Holds the EventArgs object sent to the subroutine    -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- intCustTUID - An Integer that holds the current          -
    '- customer TUID.                                           -
    '------------------------------------------------------------
    Private Sub dgvCustomers_SelectionChanged(sender As Object, e As EventArgs) Handles dgvCustomers.SelectionChanged

        'Local Variables
        Dim intCustTUID As Integer

        'Clearing Vehicles DataGridView
        dsVehicles.Clear()

        'Checking for Null Reference
        Try
            'Loading the Customer's Vehicle(s)
            intCustTUID = (dgvCustomers.CurrentRow.Index) + 1
        Catch ex As Exception
            intCustTUID = 1
        End Try

        'Checking if Connection is Open
        Try
            'Opening DB Connection
            DBConn.Open()
        Catch ex As Exception
        End Try

        'Resetting Vehicles Datagridview
        strSQLcmd = "SELECT Vehicles.TUID, Vehicles.Manufacturer, Vehicles.Model FROM Vehicles, Customer_To_Vehicle WHERE " & _
                    "Customer_To_Vehicle.CustomerTUID = " & intCustTUID & " AND Customer_To_Vehicle.VehicleTUID = Vehicles.TUID"
        DBAdapterVehicles = New OleDbDataAdapter(strSQLcmd, DBConn)
        DBAdapterVehicles.Fill(dsVehicles, "Vehicles")
        dgvVehicles.DataSource = dsVehicles.Tables("Vehicles")

        'Setting Headers
        dgvVehicles.Columns(0).HeaderText = "Vehicle ID"
        dgvVehicles.Columns(1).HeaderText = "Manufacturer"
        dgvVehicles.Columns(2).HeaderText = "Model"

    End Sub

    '------------------------------------------------------------
    '-    Subprogram Name: frmTurboAutoService_FormClosing      -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/21/14                      -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- On form close, to ask the user if they want to delete    -
    '- the database.                                            -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- sender – Identifies which particular control raised the  -
    '-          event                                           - 
    '- e – Holds the FormClosingEventArgs object sent to the    -
    '- subroutine                                               -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- None.                                                    -
    '------------------------------------------------------------
    Private Sub frmTurboAutoService_FormClosing(sender As Object, e As FormClosingEventArgs) Handles Me.FormClosing

        If MessageBox.Show("Do You Want To Delete The Database?", "Delete Database", MessageBoxButtons.YesNo) = Windows.Forms.DialogResult.Yes Then
            My.Computer.FileSystem.DeleteFile(gstrDBName)
        End If

    End Sub

    '------------------------------------------------------------
    '-        Subprogram Name: frmTurboAutoService_Load         -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/21/14                      -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- When the form loads, the customers, services, vehciles   -
    '- (indirectly), and schedule get the database values       -
    '- loaded in.                                               -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- sender – Identifies which particular control raised the  -
    '-          event                                           - 
    '- e – Holds the EventArgs object sent to the subroutine    -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- None.                                                    -
    '------------------------------------------------------------
    Private Sub frmTurboAutoService_Load(sender As Object, e As EventArgs) Handles MyBase.Load

        'Setting Public Variable
        modDatabase.blnFirstRun = False

        'Loading the Customers
        strSQLcmd = "SELECT * FROM Customers ORDER BY TUID"
        DBAdapterCustomers = New OleDbDataAdapter(strSQLcmd, DBConn)
        DBAdapterCustomers.Fill(dsCustomers, "Customers")
        dgvCustomers.DataSource = dsCustomers.Tables("Customers")

        'Setting Headers
        dgvCustomers.Columns(0).HeaderText = "Customer ID"
        dgvCustomers.Columns(1).HeaderText = "First Initial"
        dgvCustomers.Columns(2).HeaderText = "Last Name"

        'Loading the Services
        strSQLcmd = "SELECT * FROM Services ORDER BY TUID"
        DBAdapterServices = New OleDbDataAdapter(strSQLcmd, DBConn)
        DBAdapterServices.Fill(dsServices, "Services")
        dgvServices.DataSource = dsServices.Tables("Services")

        'Setting Headers
        dgvServices.Columns(0).HeaderText = "Service ID"
        dgvServices.Columns(1).HeaderText = "Service Name"
        dgvServices.Columns(2).HeaderText = "Minutes"

        'Loading the Schedule
        strSQLcmd = "SELECT * FROM Schedule ORDER BY ScheduledTime"
        DBAdapterServices = New OleDbDataAdapter(strSQLcmd, DBConn)
        DBAdapterServices.Fill(dsServices, "Schedule")
        dgvSchedule.DataSource = dsServices.Tables("Schedule")

        'Setting Headers
        dgvSchedule.Columns(0).HeaderText = "Schedule ID"
        dgvSchedule.Columns(1).HeaderText = "Customer to Vehicle ID"
        dgvSchedule.Columns(2).HeaderText = "Service ID"
        dgvSchedule.Columns(3).HeaderText = "Starting Time"
        dgvSchedule.Columns(4).HeaderText = "Mechanic to Bay ID"

        'Closing DB Connection
        DBConn.Close()

    End Sub

    '------------------------------------------------------------
    '-            Subprogram Name: ScheduleServices             -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/27/14                      -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- This subroutine schedules all of the jobs that are in    -
    '- the Schedule table of the datbase. It allows gives the   -
    '- user the option to print out the information in a        -
    '- cleaner format.                                          -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- None.                                                    -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- intTotalRecords - An Integer that holds the total of     -
    '- records in the Schedule tables.                          -
    '- intServiceTUID - An Integer to see what the service TUID -
    '- is for the corresponding service name.                   -
    '- intTimeRequired - An Integer that holds the amount of    -
    '- minutes to complete the job.                             -
    '- intDayCheck - An Integer to see when it is Saturday.     -
    '- intBay1AM - An Integer that says how many minutes there  -
    '- is left for that bay and for the time period.            -
    '- intBay2AM - An Integer that says how many minutes there  -
    '- is left for that bay and for the time period.            -
    '- intBay1PM - An Integer that says how many minutes there  -
    '- is left for that bay and for the time period.            -
    '- intBay2PM - An Integer that says how many minutes there  -
    '- is left for that bay and for the time period.            -
    '- timeBay1AM - A Date that holds the starting time for Bay -
    '- 1 in the AM.                                             -
    '- timeBay2AM - A Date that holds the starting time for Bay -
    '- 2 in the AM.                                             -
    '- timeBay1PM - A Date that holds the starting time for Bay -
    '- 1 in the PM.                                             -
    '- timeBay2PM - A Date that holds the starting time for Bay -
    '- 2 in the PM.                                             -
    '------------------------------------------------------------
    Sub ScheduleServices()

        'Local Variables
        Dim intTotalRecords As Integer
        Dim intServiceTUID As Integer
        Dim intTimeRequired As Integer
        Dim intDayCheck As Integer = 0
        Dim intBay1AM As Integer = 240
        Dim intBay2AM As Integer = 240
        Dim intBay1PM As Integer = 240
        Dim intBay2PM As Integer = 240
        Dim intDayCount As Integer = 0

        'Starting Times
        Dim timeBay1AM As Date = #11/3/2014 8:00:00 AM#
        Dim timeBay2AM As Date = #11/3/2014 8:00:00 AM#
        Dim timeBay1PM As Date = #11/3/2014 1:00:00 PM#
        Dim timeBay2PM As Date = #11/3/2014 1:00:00 PM#

        'Closing DB Connections
        DBConn.Close()

        'Opening DB Connection
        DBConn.Open()

        'Getting Last TUID Number From Schedule Table
        DBCmd.CommandText = "SELECT COUNT(*) FROM Schedule"

        DBCmd.Connection = DBConn
        DBCmd.ExecuteNonQuery()

        intTotalRecords = DBCmd.ExecuteScalar

        For intPos As Integer = 1 To intTotalRecords

            'Getting the Service TUID for Scheduled Job
            DBCmd.CommandText = "SELECT ServiceTUID FROM Schedule WHERE TUID = " & intPos & ""

            DBCmd.Connection = DBConn
            DBCmd.ExecuteNonQuery()

            intServiceTUID = DBCmd.ExecuteScalar

            'Getting Minutes for Service
            DBCmd.CommandText = "SELECT Minutes FROM Services WHERE TUID = " & intServiceTUID & ""

            DBCmd.Connection = DBConn
            DBCmd.ExecuteNonQuery()

            intTimeRequired = DBCmd.ExecuteScalar

            'Finding Time Slot

            If intBay1AM >= intBay2AM And intTimeRequired <= intBay1AM Then 'Checking Bay 1 For AM

                'Updating the ScheduledTime in Schedule Table
                DBCmd.CommandText = "UPDATE Schedule SET ScheduledTime = '" & timeBay1AM & "', MechToBayTUID = 1 WHERE TUID = " & intPos & ""

                DBCmd.Connection = DBConn
                DBCmd.ExecuteNonQuery()

                'Adding how Long Job Will Take
                timeBay1AM = timeBay1AM.AddMinutes(CDbl(intTimeRequired))

                'Decreasing how Much Time is Left
                intBay1AM -= intTimeRequired

            ElseIf intBay2AM > intBay1AM And intTimeRequired <= intBay2AM Then 'Checking Bay 2 For AM

                'Updating the ScheduledTime in Schedule Table
                DBCmd.CommandText = "UPDATE Schedule SET ScheduledTime = '" & timeBay2AM & "', MechToBayTUID = 2 WHERE TUID = " & intPos & ""

                DBCmd.Connection = DBConn
                DBCmd.ExecuteNonQuery()

                'Adding how Long Job Will Take
                timeBay2AM = timeBay2AM.AddMinutes(CDbl(intTimeRequired))

                'Decreasing how Much Time is Left 
                intBay2AM -= intTimeRequired

            ElseIf intBay1PM >= intBay2PM And intTimeRequired <= intBay1PM Then 'Checking Bay 1 For PM

                'Updating the ScheduledTime in Schedule Table
                DBCmd.CommandText = "UPDATE Schedule SET ScheduledTime = '" & timeBay1PM & "', MechToBayTUID = 1 WHERE TUID = " & intPos & ""

                DBCmd.Connection = DBConn
                DBCmd.ExecuteNonQuery()

                'Adding how Long Job Will Take
                timeBay1PM = timeBay1PM.AddMinutes(CDbl(intTimeRequired))

                'Decreasing how Much Time is Left
                intBay1PM -= intTimeRequired

            ElseIf intBay2PM > intBay1PM And intTimeRequired <= intBay2PM Then 'Checking Bay 2 For PM

                'Updating the ScheduledTime in Schedule Table
                DBCmd.CommandText = "UPDATE Schedule SET ScheduledTime = '" & timeBay2PM & "', MechToBayTUID = 2 WHERE TUID = " & intPos & ""

                DBCmd.Connection = DBConn
                DBCmd.ExecuteNonQuery()

                'Adding how Long Job Will Take
                timeBay2PM = timeBay2PM.AddMinutes(CDbl(intTimeRequired))

                'Decreasing how Much Time is Left
                intBay2PM -= intTimeRequired

            Else 'No Time Left In Day (Pushing to Next Day)

                'Incrementing Day
                intDayCount += 1
                intDayCheck += 1

                'Checking for Saturday
                If (intDayCheck Mod 5 = 0) Then

                    intDayCount += 2 'Adding Two so it Skips Sunday

                End If

                'Changing Day and Setting Time to 8am and Resetting Amount of Time left for Bay1 AM 
                timeBay1AM = #11/3/2014 8:00:00 AM#
                timeBay1AM = timeBay1AM.AddDays(CDbl(intDayCount))
                intBay1AM = 240

                'Updating the ScheduledTime in Schedule Table
                DBCmd.CommandText = "UPDATE Schedule SET ScheduledTime = '" & timeBay1AM & "', MechToBayTUID = 1 WHERE TUID = " & intPos & ""

                DBCmd.Connection = DBConn
                DBCmd.ExecuteNonQuery()

                'Adding how Long Job Will Take
                timeBay1AM = timeBay1AM.AddMinutes(CDbl(intTimeRequired))

                'Decreasing how Much Time is Left
                intBay1AM -= intTimeRequired

                'Resetting All Other Bay Times
                intBay1PM = 240
                intBay2AM = 240
                intBay2PM = 240

                'Resetting Start Times
                timeBay2AM = #11/3/2014 8:00:00 AM#
                timeBay2AM = timeBay2AM.AddDays(CDbl(intDayCount))

                timeBay1PM = #11/3/2014 1:00:00 PM#
                timeBay1PM = timeBay1PM.AddDays(CDbl(intDayCount))

                timeBay2PM = #11/3/2014 1:00:00 PM#
                timeBay2PM = timeBay2PM.AddDays(CDbl(intDayCount))

            End If

        Next

        'Clearing Schedule Datagridview
        dsSchedule.Clear()

        'Repopulating the Schedule Datagridview
        strSQLcmd = "SELECT * FROM Schedule ORDER BY ScheduledTime, TUID"
        DBAdapterServices = New OleDbDataAdapter(strSQLcmd, DBConn)
        DBAdapterServices.Fill(dsSchedule, "Schedule")
        dgvSchedule.DataSource = dsSchedule.Tables("Schedule")

        'Closing DB Connection
        DBConn.Close()

        'Seeing if User Wants Cleaner Version
        If MessageBox.Show("Do You Want To See A Cleaner Version Of Schedule?", _
                           "Schedule Information", MessageBoxButtons.YesNo) = Windows.Forms.DialogResult.Yes Then

            FinalReport()

        End If

    End Sub

    '------------------------------------------------------------
    '-               Subprogram Name: FinalReport               -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/29/14                      -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- Prints off the schedule to the listbox, and adds more    -
    '- relevant information about the job. Also lists which     -
    '- mechanic has which jobs.                                 -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- None.                                                    -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- dblPay - A Double that says how much the mechanic should -
    '- be paid.                                                 -
    '- dblTime - A Double that holds how many hours the         -
    '- mechanic worked.                                         -
    '- intCustTomerUID - An Integer that holds the current      -
    '- customer TUID.                                           -
    '- intCust_To_VehicleTUID - An Integer that holds the       -
    '- Customer_To_Vehicle TUID.                                -
    '- intMechTUID - An Integer that holds the mechanic TUID.   -
    '- intNumberOfJobs - The total number of jobs in the        -
    '- schedule.                                                -
    '- intNumberOfMechs - An Integer that holds the number of   -
    '- mechanics.                                               -
    '- intServiceMinutes - An Integer that holds how many       -
    '- minutes a certain service has.                           -
    '- intServiceTUID - An Integer to see what the service TUID -
    '- is for the corresponding service name.                   -
    '- intVehicleTUID - An Integer that holds the current       -
    '- vehicle TUID.                                            -
    '- strFirstInitial - A String that holds the first initial  -
    '- of the customer.                                         -
    '- strLastName - A String that holds the last name of the   -
    '- customer.                                                -
    '- strMechName - A String that holds the mechanic name.     -
    '- strManuName - A String that holds the vehicle            -
    '- manufacturer name.                                       -
    '- strModelName - A String that holds the vehicle model     -
    '- name.                                                    -
    '- strServiceName - A String that holds the name of the     -
    '- service.                                                 -
    '- startTime - The starting time of the job.                -
    '- strFormat - A String that holds the format.              -
    '------------------------------------------------------------
    Sub FinalReport()

        'Local Variables
        Dim dblPay As Double
        Dim dblTime As Double
        Dim intCustomerTUID As Integer
        Dim intCust_To_VehicleTUID As Integer
        Dim intMechTUID As Integer
        Dim intNumberOfJobs As Integer
        Dim intNumberOfMechs As Integer
        Dim intServiceMinutes As Integer
        Dim intServiceTUID As Integer
        Dim intVehicleTUID As Integer
        Dim strFirstInitial As String
        Dim strLastName As String
        Dim strMechName As String
        Dim strManuName As String
        Dim strModelName As String
        Dim strServiceName As String
        Dim startTime As Date
        Dim strFormat As String = "{0,-5} {1,-15} {2,-25} {3,-25} {4,-15}"

        'Clearing List Box
        lstSchedule.Items.Clear()

        'Opening DB Connection
        DBConn.Open()

        'Printing Header
        lstSchedule.Items.Add(vbTab & vbTab & vbTab & vbTab & "  Turbo Auto Service")

        'Getting Number of Mechanics
        DBCmd.CommandText = "SELECT COUNT(*) FROM Mechanics"

        DBCmd.Connection = DBConn
        DBCmd.ExecuteNonQuery()

        intNumberOfMechs = DBCmd.ExecuteScalar

        'Looping Through All Jobs
        For intMechPos As Integer = 1 To intNumberOfMechs

            'Resetting Time
            dblTime = 0

            'Getting Bay Info
            DBCmd.CommandText = "SELECT MechanicTUID FROM Mechanic_To_Bay WHERE TUID = " & intMechPos & ""

            DBCmd.Connection = DBConn
            DBCmd.ExecuteNonQuery()

            intMechTUID = DBCmd.ExecuteScalar()

            DBCmd.CommandText = "SELECT Name FROM Mechanics WHERE TUID = " & intMechTUID & ""

            DBCmd.Connection = DBConn
            DBCmd.ExecuteNonQuery()

            strMechName = DBCmd.ExecuteScalar()

            'Print Bay Info
            lstSchedule.Items.Add("")
            lstSchedule.Items.Add("Bay #" & intMechTUID)
            lstSchedule.Items.Add("Mechnanic Name: " & strMechName)

            'Printing Header   
            lstSchedule.Items.Add("")
            lstSchedule.Items.Add(String.Format(strFormat, "ID", "Name", "Vehicle", "Start Time", "Service"))
            lstSchedule.Items.Add(String.Format(strFormat, "--", "----", "-------", "----------", "-------"))


            'Getting Number of Jobs
            DBCmd.CommandText = "SELECT COUNT(*) FROM Schedule"

            DBCmd.Connection = DBConn
            DBCmd.ExecuteNonQuery()

            intNumberOfJobs = DBCmd.ExecuteScalar()

            For intPos As Integer = 1 To intNumberOfJobs

                'Getting the Service TUID for Scheduled Job
                DBCmd.CommandText = "SELECT ServiceTUID FROM Schedule WHERE MechToBayTUID = " & intMechTUID & " AND TUID = " & intPos & ""

                DBCmd.Connection = DBConn
                DBCmd.ExecuteNonQuery()

                intServiceTUID = DBCmd.ExecuteScalar

                'Seeing if A Result was Found
                If intServiceTUID <> 0 Then

                    'Getting the Cust_To_Vehicle TUID
                    DBCmd.CommandText = "SELECT CustToVehicleTUID FROM Schedule WHERE TUID = " & intPos & ""

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    intCust_To_VehicleTUID = DBCmd.ExecuteScalar

                    'Getting the VehicleTUID
                    DBCmd.CommandText = "SELECT VehicleTUID FROM Customer_To_Vehicle WHERE TUID = " & intCust_To_VehicleTUID & ""

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    intVehicleTUID = DBCmd.ExecuteScalar

                    'Getting the Vehicle Model
                    DBCmd.CommandText = "SELECT Model FROM Vehicles WHERE TUID = " & intVehicleTUID & ""

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    strModelName = DBCmd.ExecuteScalar

                    'Getting the Vehicle Manufacturer
                    DBCmd.CommandText = "SELECT Manufacturer FROM Vehicles WHERE TUID = " & intVehicleTUID & ""

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    strManuName = DBCmd.ExecuteScalar

                    'Getting the CustomerTUID
                    DBCmd.CommandText = "SELECT CustomerTUID FROM Customer_To_Vehicle WHERE TUID = " & intCust_To_VehicleTUID & ""

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    intCustomerTUID = DBCmd.ExecuteScalar

                    'Getting the First Initial
                    DBCmd.CommandText = "SELECT FirstInitial FROM Customers WHERE TUID = " & intCustomerTUID & ""

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    strFirstInitial = DBCmd.ExecuteScalar

                    'Getting the Last Name
                    DBCmd.CommandText = "SELECT LastName FROM Customers WHERE TUID = " & intCustomerTUID & ""

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    strLastName = DBCmd.ExecuteScalar

                    'Getting the Service Name
                    DBCmd.CommandText = "SELECT ServiceName FROM Services WHERE TUID = " & intServiceTUID & ""

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    strServiceName = DBCmd.ExecuteScalar

                    'Getting the Start Time
                    DBCmd.CommandText = "SELECT ScheduledTime FROM Schedule WHERE TUID = " & intPos & ""

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    startTime = DBCmd.ExecuteScalar

                    'Printing Information
                    lstSchedule.Items.Add(String.Format(strFormat, intPos, strFirstInitial & " " & strLastName, _
                                          strManuName & " " & strModelName, startTime, strServiceName))

                    'Getting Minutes for Job
                    DBCmd.CommandText = "SELECT Minutes FROM Services WHERE TUID = " & intServiceTUID & ""

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    intServiceMinutes = DBCmd.ExecuteScalar

                    'Getting Total TIme
                    dblTime += intServiceMinutes
                End If

            Next

            dblTime = dblTime / 60

            'Getting Payrate
            DBCmd.CommandText = "SELECT Wage FROM Mechanics WHERE TUID = " & intMechTUID & ""

            DBCmd.Connection = DBConn
            DBCmd.ExecuteNonQuery()

            dblPay = DBCmd.ExecuteScalar

            'Finding Total
            dblPay = dblPay * dblTime

            lstSchedule.Items.Add("")
            lstSchedule.Items.Add(strMechName & " Is Owed " & FormatCurrency(dblPay) & "!")

        Next

        'Closing DB Connection
        DBConn.Close()

    End Sub

End Class
