
'------------------------------------------------------------
'-                File Name: Kokaly_Assignment4             - 
'-   Part of Project: Assignment #4 (Turbo Auto Service)    -
'------------------------------------------------------------
'-                Written By: Spencer Kokaly                -
'-                Written On: 10/27/14                      -
'------------------------------------------------------------
'- File Purpose:                                            -
'- This file contains a module that holds many of the basic -
'- subroutines of this project. These functions deal with   -
'- creating the Database, clearing contents, adding         -
'- contents, and reading based on file.                     -
'------------------------------------------------------------
'- Global Variable Dictionary:                              -
'- blnFirstRun - A Boolean to see if this is the first run  -
'- of the system.                                           -
'- gstrConnString - A String that holds the connection      -
'- string to the Database.                                  -
'- gstrDBName - A String that holds the Database name.      -
'------------------------------------------------------------

'Imports
Imports System.Data.OleDb
Imports System.IO

Module modDatabase

    'Global Variable to Check for First Run
    Public blnFirstRun = True

    'DB Name and Connection String
    Public Const gstrDBName As String = "TurboAutoService.accdb"
    Public Const gstrConnString As String = "Provider=Microsoft.ACE.OLEDB.12.0;Data Source=" & gstrDBName & ";"

    '------------------------------------------------------------
    '-               Subprogram Name: MakeDatabase              -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/7/14                       -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- To create, clean out, and populate the database for      - 
    '- Turbo Auto Service.                                      -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- None.                                                    -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- strConn - A String that holds the gstrConnString value.  -
    '- Which that value holds the connection to the database.   -
    '------------------------------------------------------------
    Sub MakeDatabase()

        'Variable for Connection String, Setting Equal to the Connection String
        Dim strConn As String = gstrConnString

        'Building the database
        CreateDatabase(strConn)

        'Clearing Contents of the Tables
        WipeTableContents(strConn, "Customers")
        WipeTableContents(strConn, "Vehicles")
        WipeTableContents(strConn, "Bays")
        WipeTableContents(strConn, "Services")
        WipeTableContents(strConn, "Mechanics")
        WipeTableContents(strConn, "Mechanic_To_Bay")
        WipeTableContents(strConn, "Customer_To_Vehicle")
        WipeTableContents(strConn, "Schedule")

        'Populating the Tables with Default Information
        PopulateBaysTable(strConn)
        PopulateServicesTable(strConn)
        PopulateMechanicsTable(strConn)
        PopulateMechanicToBayTable(strConn)

        'Populate the Tables with Initial Information from Text File
        PopulateBasedOnFile(strConn)

    End Sub

    '------------------------------------------------------------
    '-              Subprogram Name: CreateDatabase             -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/7/14                       -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- To create the database for Turbo Auto Service.           -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- strConn - The connection string that connects to the     -
    '- database.                                                -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- DBCat - The ADOX.Catalog() which is used to create the   -
    '- database.                                                -
    '- DBCmd - The OleDbCommand that is used to execute the     -
    '- queries.                                                 -
    '- DBConn - The OleDbConnection that is used to connect to  -
    '- the database.                                            -
    '------------------------------------------------------------
    Sub CreateDatabase(ByVal strConn As String)

        'Setting Up DB Catalog, Command and Connection
        Dim DBCat As New ADOX.Catalog()
        Dim DBConn As OleDbConnection
        Dim DBCmd As OleDbCommand = New OleDbCommand()

        'Try Catch to see if Database Exists
        Try
            DBCat.Create(strConn)
            MessageBox.Show("Created Database!")
        Catch ex As Exception
            MessageBox.Show("Database Already Exists!")
        End Try

        'Setting Up DB Connection
        DBConn = New OleDbConnection(strConn)
        DBConn.Open()

        'Creating the Customers Table
        DBCmd.CommandText = "CREATE TABLE Customers (" & _
                            "TUID int, " & _
                            "FirstInitial varchar(2), " & _
                            "LastName varchar(50))"

        DBCmd.Connection = DBConn

        'Seeing If the Table Was Created
        Try
            DBCmd.ExecuteNonQuery()
            MessageBox.Show("Created Customers Table!")
        Catch ex As Exception
            MessageBox.Show("Customers Table Already Exists!")
        End Try

        'Creating the Vehicles Table
        DBCmd.CommandText = "CREATE TABLE Vehicles (" & _
                            "TUID int, " & _
                            "Manufacturer varchar(50), " & _
                            "Model varchar(50))"

        DBCmd.Connection = DBConn

        'Seeing If the Table Was Created
        Try
            DBCmd.ExecuteNonQuery()
            MessageBox.Show("Created Vehicles Table!")
        Catch ex As Exception
            MessageBox.Show("Vehicles Table Already Exists!")
        End Try

        'Creating the Bays Table
        DBCmd.CommandText = "CREATE TABLE Bays (" & _
                            "TUID int, " & _
                            "BayNumber int)"

        DBCmd.Connection = DBConn

        'Seeing If the Table Was Created
        Try
            DBCmd.ExecuteNonQuery()
            MessageBox.Show("Created Bays Table!")
        Catch ex As Exception
            MessageBox.Show("Bays Table Already Exists!")
        End Try

        'Creating the Services Table
        DBCmd.CommandText = "CREATE TABLE Services (" & _
                            "TUID int, " & _
                            "ServiceName varchar(50), " & _
                            "Minutes int)"

        DBCmd.Connection = DBConn

        'Seeing If the Table Was Created
        Try
            DBCmd.ExecuteNonQuery()
            MessageBox.Show("Created Services Table!")
        Catch ex As Exception
            MessageBox.Show("Services Table Already Exists!")
        End Try

        'Creating the Mechanics Table
        DBCmd.CommandText = "CREATE TABLE Mechanics (" & _
                            "TUID int, " & _
                            "Name varchar(50), " & _
                            "Wage numeric (4,2))"

        DBCmd.Connection = DBConn

        'Seeing If the Table Was Created
        Try
            DBCmd.ExecuteNonQuery()
            MessageBox.Show("Created Mechanics Table!")
        Catch ex As Exception
            MessageBox.Show("Mechanics Table Already Exists!")
        End Try

        'Creating the Mechanic_To_Bay Table
        DBCmd.CommandText = "CREATE TABLE Mechanic_To_Bay (" & _
                            "TUID int, " & _
                            "MechanicTUID int, " & _
                            "BayTUID int)"

        DBCmd.Connection = DBConn

        'Seeing If the Table Was Created
        Try
            DBCmd.ExecuteNonQuery()
            MessageBox.Show("Created Mechanic_To_Bay Table!")
        Catch ex As Exception
            MessageBox.Show("Mechanic_To_Bay Table Already Exists!")
        End Try

        'Creating the Customer_To_Vehicle Table
        DBCmd.CommandText = "CREATE TABLE Customer_To_Vehicle (" & _
                            "TUID int, " & _
                            "CustomerTUID int, " & _
                            "VehicleTUID int)"

        DBCmd.Connection = DBConn

        'Seeing If the Table Was Created
        Try
            DBCmd.ExecuteNonQuery()
            MessageBox.Show("Created Customer_To_Vehicle Table!")
        Catch ex As Exception
            MessageBox.Show("Customer_To_Vehicle Table Already Exists!")
        End Try

        'Creating the Schedule Table
        DBCmd.CommandText = "CREATE TABLE Schedule (" & _
                            "TUID int, " & _
                            "CustToVehicleTUID int, " & _
                            "ServiceTUID int, " & _
                            "ScheduledTime date, " & _
                            "MechToBayTUID int)"

        DBCmd.Connection = DBConn

        'Seeing If the Table Was Created
        Try
            DBCmd.ExecuteNonQuery()
            MessageBox.Show("Created Schedule Table!")
        Catch ex As Exception
            MessageBox.Show("Schedule Table Already Exists!")
        End Try

        'Closing DB Connection
        DBConn.Close()

        'Closing Active Connection
        DBCat.ActiveConnection.Close()

    End Sub

    '------------------------------------------------------------
    '-          Subprogram Name: PopulateBasedOnFile            -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/8/14                       -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- To populate the database with the starting text file.    -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- strConn - The connection string that connects to the     -
    '- database.                                                -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- DBCmd - The OleDbCommand that is used to execute the     -
    '- queries.                                                 -
    '- DBConn - The OleDbConnection that is used to connect to  -
    '- the database.                                            -
    '- intCustTUID - An Integer that holds the current          -
    '- customer TUID.                                           -
    '- intCust_To_VehicleTUID - An Integer that holds the       -
    '- Customer_To_Vehicle TUID.                                -                      
    '- intVehicleTUID - An Integer that holds the current       -
    '- vehicle TUID.                                            -
    '- intLocInFile - An Interger to see what line the program  -
    '- is currently at in the file.                             -
    '- intScheduleTUID - An Integer to see hold the current     -
    '- schedule TUID.                                           -
    '- intServiceTUID - An Integer to see what the service TUID -
    '- is for the corresponding service name.                   -
    '- strFileLine - A String to get the text from one line of  -
    '- the text file.                                           -
    '- strGetData() - A String that holds the pieces of data    -
    '- from strFileLine.                                        -
    '- strFileName - A String that holds the file name for the  -
    '- text file where the data is being read from.             -
    '- strInitial - A String that holds the first initial of    -
    '- the customer.                                            -
    '- strLastName - A String that holds the last name of the   -
    '- customer.                                                -
    '- strName - A String that holds the first initial and last -
    '- name of the customer.                                    -
    '- strReadData() - A String that holds all of the text from -
    '- the text file.                                           -
    '- strServiceName - A String that holds the service name.   -
    '------------------------------------------------------------
    Sub PopulateBasedOnFile(ByVal strConn As String)

        'Local Variables
        Dim intLocInFile As Integer
        Dim strFileLine As String
        Dim strGetData() As String
        Dim strReadData() As String
        Dim strName As String
        Dim strInitial As String
        Dim strServiceName As String
        Dim strLastName As String
        Dim intCustTUID As Integer
        Dim intVehicleTUID As Integer
        Dim intCust_To_VehicleTUID As Integer
        Dim intScheduleTUID As Integer
        Dim intServiceTUID As Integer

        'Getting File Name
        frmTurboAutoService.ofdFilePicker.ShowDialog()
        Dim strFileName = frmTurboAutoService.ofdFilePicker.FileName

        'Setting Up DB Command and Connection
        Dim DBConn As OleDbConnection
        Dim DBCmd As OleDbCommand = New OleDbCommand()

        'Opening Up Connection
        DBConn = New OleDbConnection(strConn)
        DBConn.Open()

        'Seeing if File Exists
        If File.Exists(strFileName) Then
            'Opening and Reading File
            strReadData = File.ReadAllLines(strFileName)

            'Parsing Information and Putting Into Database
            For intLocInFile = 0 To strReadData.Length - 1
                strFileLine = strReadData(intLocInFile)
                strGetData = strFileLine.Split(vbTab)
                strName = strGetData(0)
                strInitial = strName.Substring(0, 2)
                strLastName = strName.Substring(3, strName.Length - 3)

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

                Else

                    'Getting Customer TUID
                    DBCmd.CommandText = "SELECT TUID FROM Customers WHERE LastName = '" & strLastName & "' AND FirstInitial = '" & strInitial & "'"

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    'Returning Customer TUID
                    intCustTUID = DBCmd.ExecuteScalar
                    intCustTUID -= 1

                End If

                'Adding Vehicle

                'Seeing if Vehicle Exists
                DBCmd.CommandText = "SELECT COUNT (*) FROM Vehicles WHERE Manufacturer = '" & strGetData(1) & "' AND Model = '" & strGetData(2) & "'"

                DBCmd.Connection = DBConn
                DBCmd.ExecuteNonQuery()

                'Seeing What the Count Results Was
                If (DBCmd.ExecuteScalar = 0) Then

                    'Getting Last TUID Number
                    DBCmd.CommandText = "SELECT COUNT(*) FROM Vehicles"

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    'Seeing What the Count Result Was
                    intVehicleTUID = DBCmd.ExecuteScalar

                    'Adding Vehicle to Vehicles Table
                    DBCmd.CommandText = "INSERT INTO Vehicles (TUID, Manufacturer, Model) " & _
                                        "VALUES ('" & intVehicleTUID + 1 & "', '" & strGetData(1) & _
                                        "', '" & strGetData(2) & "')"

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    'Seeing if Customer Already Owns Vehicle
                    DBCmd.CommandText = "SELECT COUNT(*) FROM Customer_To_Vehicle WHERE Customer_To_Vehicle.VehicleTUID = " & _
                                        intVehicleTUID + 1 & " AND Customer_To_Vehicle.CustomerTUID = " & intCustTUID + 1 & ""

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

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

                Else 'Relating Customer to Vehicle

                    'Getting Vehicle TUID
                    DBCmd.CommandText = "SELECT TUID FROM Vehicles WHERE Manufacturer = '" & strGetData(1) & "' AND Model = '" & strGetData(2) & "'"

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    intVehicleTUID = DBCmd.ExecuteScalar

                    'Seeing if Customer Already Owns Vehicle
                    DBCmd.CommandText = "SELECT COUNT(*) FROM Customer_To_Vehicle WHERE Customer_To_Vehicle.VehicleTUID = " & _
                                        intVehicleTUID & " AND Customer_To_Vehicle.CustomerTUID = " & intCustTUID + 1 & ""

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    'Seeing What the Count Results Was
                    If (DBCmd.ExecuteScalar = 0) Then

                        'Getting Cust_To_Vehicle TUID
                        DBCmd.CommandText = "SELECT COUNT(*) FROM Customer_To_Vehicle "

                        DBCmd.Connection = DBConn
                        DBCmd.ExecuteNonQuery()

                        intCust_To_VehicleTUID = DBCmd.ExecuteScalar

                        'Adding 'Customer to Vehicle' to Customer_To_Vehicle Table
                        DBCmd.CommandText = "INSERT INTO Customer_To_Vehicle (TUID, CustomerTUID, VehicleTUID) " & _
                                            "VALUES ('" & intCust_To_VehicleTUID + 1 & "', '" & intCustTUID + 1 & "', '" & intVehicleTUID & "')"

                        DBCmd.Connection = DBConn
                        DBCmd.ExecuteNonQuery()

                    End If

                End If

                If (blnFirstRun = False) Then

                    'Getting Service Name
                    strServiceName = strGetData(3)

                    'Getting Cust_To_Vehicle TUID
                    DBCmd.CommandText = "SELECT TUID From Customer_To_Vehicle WHERE CustomerTUID = " & intCustTUID + 1 & _
                        " AND VehicleTUID = " & intVehicleTUID & ""

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    'Seeing What the TUID is
                    intCust_To_VehicleTUID = DBCmd.ExecuteScalar


                    'Getting Service TUID
                    DBCmd.CommandText = "SELECT TUID From Services WHERE ServiceName = '" & strServiceName & "'"

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    'Seeing What the TUID is
                    intServiceTUID = DBCmd.ExecuteScalar

                    'Adding Customer to Schedule Table

                    'Getting Last TUID Number
                    DBCmd.CommandText = "SELECT COUNT(*) FROM Schedule"

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                    'Seeing What the Count Results Was
                    intScheduleTUID = DBCmd.ExecuteScalar

                    'Adding Customer to Schedule Table Query
                    DBCmd.CommandText = "INSERT INTO Schedule (TUID, CustToVehicleTUID, ServiceTUID) " & _
                                        "VALUES ('" & intScheduleTUID + 1 & "', '" & _
                                        intCust_To_VehicleTUID & "', '" & intServiceTUID & "')"

                    DBCmd.Connection = DBConn
                    DBCmd.ExecuteNonQuery()

                End If

            Next
        Else
            MessageBox.Show(strFileName & " Does Not Exist! Can't Load In Starting Data!")
        End If

        blnFirstRun = False

        'Closing Connection
        DBConn.Close()

    End Sub

    '------------------------------------------------------------
    '-            Subprogram Name: PopulateBaysTable            -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/8/14                       -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- To populate the data for the Bays table.                 -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- strConn - The connection string that connects to the     -
    '- database.                                                -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- DBCmd - The OleDbCommand that is used to execute the     -
    '- queries.                                                 -
    '- DBConn - The OleDbConnection that is used to connect to  -
    '- the database.                                            -
    '------------------------------------------------------------
    Sub PopulateBaysTable(ByVal strConn As String)

        'Setting Up DB Command and Connection
        Dim DBConn As OleDbConnection
        Dim DBCmd As OleDbCommand = New OleDbCommand()

        'Opening Up Connection
        DBConn = New OleDbConnection(strConn)
        DBConn.Open()

        'Adding Bays to Database
        'Adding Bay 1
        DBCmd.CommandText = "INSERT INTO Bays (TUID, BayNumber) " & _
                            "VALUES ('1','1')"
        DBCmd.Connection = DBConn
        DBCmd.ExecuteNonQuery()

        'Adding Bay 2
        DBCmd.CommandText = "INSERT INTO Bays (TUID, BayNumber) " & _
                            "VALUES ('2','2')"
        DBCmd.Connection = DBConn
        DBCmd.ExecuteNonQuery()

        'Closing Connection
        DBConn.Close()
        MessageBox.Show("All Bays Added To Bays Table!")

    End Sub

    '------------------------------------------------------------
    '-         Subprogram Name: PopulateMechanicsTable          -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/8/14                       -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- To populate the data for the Mechanics table.            -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- strConn - The connection string that connects to the     -
    '- database.                                                -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- DBCmd - The OleDbCommand that is used to execute the     -
    '- queries.                                                 -
    '- DBConn - The OleDbConnection that is used to connect to  -
    '- the database.                                            -
    '------------------------------------------------------------
    Sub PopulateMechanicsTable(ByVal strConn As String)

        'Setting Up DB Command and Connection
        Dim DBConn As OleDbConnection
        Dim DBCmd As OleDbCommand = New OleDbCommand()

        'Opening Up Connection
        DBConn = New OleDbConnection(strConn)
        DBConn.Open()

        'Adding Mechanices to Database
        'Adding Steve
        DBCmd.CommandText = "INSERT INTO Mechanics (TUID, Name, Wage) " & _
                            "VALUES ('1','Steve','9')"
        DBCmd.Connection = DBConn
        DBCmd.ExecuteNonQuery()

        'Adding Sue
        DBCmd.CommandText = "INSERT INTO Mechanics (TUID, Name, Wage) " & _
                            "VALUES ('2','Sue','10')"
        DBCmd.Connection = DBConn
        DBCmd.ExecuteNonQuery()

        'Closing Connection
        DBConn.Close()
        MessageBox.Show("All Mechanics Added To Mechanics Table!")

    End Sub

    '------------------------------------------------------------
    '-      Subprogram Name: PopulateMechanicToBayTable         -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/8/14                       -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- To populate the data for the Mechanic_To_Bay table.      -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- strConn - The connection string that connects to the     -
    '- database.                                                -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- DBCmd - The OleDbCommand that is used to execute the     -
    '- queries.                                                 -
    '- DBConn - The OleDbConnection that is used to connect to  -
    '- the database.                                            -
    '------------------------------------------------------------
    Sub PopulateMechanicToBayTable(ByVal strConn As String)

        'Setting Up DB Command and Connection
        Dim DBConn As OleDbConnection
        Dim DBCmd As OleDbCommand = New OleDbCommand()

        'Opening Up Connection
        DBConn = New OleDbConnection(strConn)
        DBConn.Open()

        'Adding Mechanic To Bay to Database
        'Adding Mechanic 1 (Steve) to Bay 1
        DBCmd.CommandText = "INSERT INTO Mechanic_To_Bay (TUID, MechanicTUID, BayTUID) " & _
                            "VALUES ('1','1','1')"
        DBCmd.Connection = DBConn
        DBCmd.ExecuteNonQuery()

        'Adding Mechanic 2 (Sue) to Bay 2
        DBCmd.CommandText = "INSERT INTO Mechanic_To_Bay (TUID, MechanicTUID, BayTUID) " & _
                            "VALUES ('2','2','2')"
        DBCmd.Connection = DBConn
        DBCmd.ExecuteNonQuery()

        'Closing Connection
        DBConn.Close()
        MessageBox.Show("All 'Mechanic To Bay' Added To Mechanic_To_Bay Table!")

    End Sub

    '------------------------------------------------------------
    '-          Subprogram Name: PopulateServicesTable          -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/8/14                       -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- To populate the data for the Services table.             -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- strConn - The connection string that connects to the     -
    '- database.                                                -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- DBCmd - The OleDbCommand that is used to execute the     -
    '- queries.                                                 -
    '- DBConn - The OleDbConnection that is used to connect to  -
    '- the database.                                            -
    '------------------------------------------------------------
    Sub PopulateServicesTable(ByVal strConn As String)

        'Setting Up DB Command and Connection
        Dim DBConn As OleDbConnection
        Dim DBCmd As OleDbCommand = New OleDbCommand()

        'Opening Up Connection
        DBConn = New OleDbConnection(strConn)
        DBConn.Open()

        'Adding Services to Database
        'Adding Oil Change
        DBCmd.CommandText = "INSERT INTO Services (TUID, ServiceName, Minutes) " & _
                            "VALUES ('1','Oil','30')"
        DBCmd.Connection = DBConn
        DBCmd.ExecuteNonQuery()

        'Adding Tire Replacement
        DBCmd.CommandText = "INSERT INTO Services (TUID, ServiceName, Minutes) " & _
                            "VALUES ('2','Tires','60')"
        DBCmd.Connection = DBConn
        DBCmd.ExecuteNonQuery()

        'Adding Brakes
        DBCmd.CommandText = "INSERT INTO Services (TUID, ServiceName, Minutes) " & _
                            "VALUES ('3','Brakes','180')"
        DBCmd.Connection = DBConn
        DBCmd.ExecuteNonQuery()

        'Adding Transmission
        DBCmd.CommandText = "INSERT INTO Services (TUID, ServiceName, Minutes) " & _
                            "VALUES ('4','Transmission','120')"
        DBCmd.Connection = DBConn
        DBCmd.ExecuteNonQuery()

        'Adding Cooling System
        DBCmd.CommandText = "INSERT INTO Services (TUID, ServiceName, Minutes) " & _
                            "VALUES ('5','Cooling','240')"
        DBCmd.Connection = DBConn
        DBCmd.ExecuteNonQuery()

        'Closing Connection
        DBConn.Close()
        MessageBox.Show("All Services Added To Services Table!")

    End Sub

    '------------------------------------------------------------
    '-             Subprogram Name: WipeTableContents           -
    '------------------------------------------------------------
    '-                Written By: Spencer Kokaly                -
    '-                Written On: 10/7/14                       -
    '------------------------------------------------------------
    '- Subprogram Purpose:                                      -
    '- To delete the contents from the selected table in the    -
    '- database.                                                -
    '------------------------------------------------------------
    '- Parameter Dictionary:                                    -
    '- strConn - The connection string that connects to the     -
    '- database.                                                -
    '- strTableName - The table name that will have its         -
    '- contents deleted.                                        -
    '------------------------------------------------------------
    '- Local Variable Dictionary:                               -
    '- DBCmd - The OleDbCommand that is used to execute the     -
    '- query.                                                   -
    '- DBConn - The OleDbConnection that is used to connect to  -
    '- the database.                                            -
    '------------------------------------------------------------
    Sub WipeTableContents(ByVal strConn As String, ByVal strTableName As String)

        'Setting Up DB Command and Connection
        Dim DBConn As OleDbConnection
        Dim DBCmd As OleDbCommand = New OleDbCommand()

        'Opening Up Connection to the DB
        DBConn = New OleDbConnection(strConn)
        DBConn.Open()

        'Deleting Contents From Table
        DBCmd.CommandText = "DELETE * FROM " & strTableName
        DBCmd.Connection = DBConn
        DBCmd.ExecuteNonQuery()

        'Displaying Messagebox That Everything Was Deleted From Table
        MessageBox.Show("Deleted Everything In " & strTableName & "!")

        'Closing Connection
        DBConn.Close()

    End Sub

End Module
