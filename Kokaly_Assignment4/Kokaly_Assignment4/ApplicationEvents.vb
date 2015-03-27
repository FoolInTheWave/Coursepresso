Namespace My

    ' The following events are available for MyApplication:
    ' 
    ' Startup: Raised when the application starts, before the startup form is created.
    ' Shutdown: Raised after all application forms are closed.  This event is not raised if the application terminates abnormally.
    ' UnhandledException: Raised if the application encounters an unhandled exception.
    ' StartupNextInstance: Raised when launching a single-instance application and the application is already active. 
    ' NetworkAvailabilityChanged: Raised when the network connection is connected or disconnected.
    Partial Friend Class MyApplication

        'DB Name and Connection String
        Public Const gstrDBName As String = "TurboAutoService.accdb"
        Public Const gstrConnString As String = "Provider=Microsoft.ACE.OLEDB.12.0;Data Source=" & gstrDBName & ";Persist Security Info=False"

        '------------------------------------------------------------
        '-          Subprogram Name: MyApplication_Startup          -
        '------------------------------------------------------------
        '-                Written By: Spencer Kokaly                -
        '-                Written On: 10/7/14                       -
        '------------------------------------------------------------
        '- Subprogram Purpose:                                      -
        '- To see if the database has been created or not, and if   -
        '- it hasn't, a new database is created.                    -
        '------------------------------------------------------------
        '- Parameter Dictionary:                                    -
        '- sender – Identifies which particular control raised the  -
        '-          event                                           - 
        '- e – Holds the ApplicationServices.StartUpEventArgs       -
        '-     object sent to the subroutine                        -
        '------------------------------------------------------------
        '- Local Variable Dictionary:                               -
        '- blnFIleExists - A Boolean used to check and see if the   -
        '- database exists.                                         -
        '------------------------------------------------------------
        Private Sub MyApplication_Startup(sender As Object, e As ApplicationServices.StartupEventArgs) Handles Me.Startup

            'Boolean to See if DB Exists
            Dim blnFileExists As Boolean = False

            'Checking for DB
            blnFileExists = My.Computer.FileSystem.FileExists(gstrDBName)
            If Not blnFileExists Then
                MakeDatabase()
            End If
        End Sub

    End Class


End Namespace

