<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()> _
Partial Class frmTurboAutoService
    Inherits System.Windows.Forms.Form

    'Form overrides dispose to clean up the component list.
    <System.Diagnostics.DebuggerNonUserCode()> _
    Protected Overrides Sub Dispose(ByVal disposing As Boolean)
        Try
            If disposing AndAlso components IsNot Nothing Then
                components.Dispose()
            End If
        Finally
            MyBase.Dispose(disposing)
        End Try
    End Sub

    'Required by the Windows Form Designer
    Private components As System.ComponentModel.IContainer

    'NOTE: The following procedure is required by the Windows Form Designer
    'It can be modified using the Windows Form Designer.  
    'Do not modify it using the code editor.
    <System.Diagnostics.DebuggerStepThrough()> _
    Private Sub InitializeComponent()
        Me.dgvCustomers = New System.Windows.Forms.DataGridView()
        Me.dgvVehicles = New System.Windows.Forms.DataGridView()
        Me.cmdProcessFile = New System.Windows.Forms.Button()
        Me.lblCustomers = New System.Windows.Forms.Label()
        Me.lblVehicles = New System.Windows.Forms.Label()
        Me.cmdAddCustomer = New System.Windows.Forms.Button()
        Me.cmdAddVehicle = New System.Windows.Forms.Button()
        Me.cmdScheduleService = New System.Windows.Forms.Button()
        Me.dgvServices = New System.Windows.Forms.DataGridView()
        Me.lblServices = New System.Windows.Forms.Label()
        Me.ofdFilePicker = New System.Windows.Forms.OpenFileDialog()
        Me.dgvSchedule = New System.Windows.Forms.DataGridView()
        Me.lstSchedule = New System.Windows.Forms.ListBox()
        CType(Me.dgvCustomers, System.ComponentModel.ISupportInitialize).BeginInit()
        CType(Me.dgvVehicles, System.ComponentModel.ISupportInitialize).BeginInit()
        CType(Me.dgvServices, System.ComponentModel.ISupportInitialize).BeginInit()
        CType(Me.dgvSchedule, System.ComponentModel.ISupportInitialize).BeginInit()
        Me.SuspendLayout()
        '
        'dgvCustomers
        '
        Me.dgvCustomers.AllowUserToAddRows = False
        Me.dgvCustomers.AllowUserToDeleteRows = False
        Me.dgvCustomers.AllowUserToResizeColumns = False
        Me.dgvCustomers.AllowUserToResizeRows = False
        Me.dgvCustomers.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize
        Me.dgvCustomers.Location = New System.Drawing.Point(12, 12)
        Me.dgvCustomers.Name = "dgvCustomers"
        Me.dgvCustomers.ReadOnly = True
        Me.dgvCustomers.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect
        Me.dgvCustomers.Size = New System.Drawing.Size(360, 186)
        Me.dgvCustomers.TabIndex = 0
        '
        'dgvVehicles
        '
        Me.dgvVehicles.AllowUserToAddRows = False
        Me.dgvVehicles.AllowUserToDeleteRows = False
        Me.dgvVehicles.AllowUserToResizeColumns = False
        Me.dgvVehicles.AllowUserToResizeRows = False
        Me.dgvVehicles.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize
        Me.dgvVehicles.Location = New System.Drawing.Point(615, 12)
        Me.dgvVehicles.Name = "dgvVehicles"
        Me.dgvVehicles.ReadOnly = True
        Me.dgvVehicles.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect
        Me.dgvVehicles.Size = New System.Drawing.Size(335, 186)
        Me.dgvVehicles.TabIndex = 1
        '
        'cmdProcessFile
        '
        Me.cmdProcessFile.Font = New System.Drawing.Font("Microsoft Sans Serif", 18.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.cmdProcessFile.Location = New System.Drawing.Point(399, 81)
        Me.cmdProcessFile.Name = "cmdProcessFile"
        Me.cmdProcessFile.Size = New System.Drawing.Size(187, 43)
        Me.cmdProcessFile.TabIndex = 2
        Me.cmdProcessFile.Text = "Process File"
        Me.cmdProcessFile.UseVisualStyleBackColor = True
        '
        'lblCustomers
        '
        Me.lblCustomers.AutoSize = True
        Me.lblCustomers.Font = New System.Drawing.Font("Microsoft Sans Serif", 21.75!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.lblCustomers.Location = New System.Drawing.Point(112, 201)
        Me.lblCustomers.Name = "lblCustomers"
        Me.lblCustomers.Size = New System.Drawing.Size(157, 33)
        Me.lblCustomers.TabIndex = 3
        Me.lblCustomers.Text = "Customers"
        '
        'lblVehicles
        '
        Me.lblVehicles.AutoSize = True
        Me.lblVehicles.Font = New System.Drawing.Font("Microsoft Sans Serif", 21.75!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.lblVehicles.Location = New System.Drawing.Point(720, 201)
        Me.lblVehicles.Name = "lblVehicles"
        Me.lblVehicles.Size = New System.Drawing.Size(126, 33)
        Me.lblVehicles.TabIndex = 4
        Me.lblVehicles.Text = "Vehicles"
        '
        'cmdAddCustomer
        '
        Me.cmdAddCustomer.Font = New System.Drawing.Font("Microsoft Sans Serif", 18.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.cmdAddCustomer.Location = New System.Drawing.Point(95, 237)
        Me.cmdAddCustomer.Name = "cmdAddCustomer"
        Me.cmdAddCustomer.Size = New System.Drawing.Size(186, 43)
        Me.cmdAddCustomer.TabIndex = 5
        Me.cmdAddCustomer.Text = "Add Customer"
        Me.cmdAddCustomer.UseVisualStyleBackColor = True
        '
        'cmdAddVehicle
        '
        Me.cmdAddVehicle.Font = New System.Drawing.Font("Microsoft Sans Serif", 21.75!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.cmdAddVehicle.Location = New System.Drawing.Point(669, 235)
        Me.cmdAddVehicle.Name = "cmdAddVehicle"
        Me.cmdAddVehicle.Size = New System.Drawing.Size(232, 43)
        Me.cmdAddVehicle.TabIndex = 6
        Me.cmdAddVehicle.Text = "Add Vehicle"
        Me.cmdAddVehicle.UseVisualStyleBackColor = True
        '
        'cmdScheduleService
        '
        Me.cmdScheduleService.Font = New System.Drawing.Font("Microsoft Sans Serif", 18.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.cmdScheduleService.Location = New System.Drawing.Point(73, 468)
        Me.cmdScheduleService.Name = "cmdScheduleService"
        Me.cmdScheduleService.Size = New System.Drawing.Size(232, 43)
        Me.cmdScheduleService.TabIndex = 8
        Me.cmdScheduleService.Text = "Schedule Service"
        Me.cmdScheduleService.UseVisualStyleBackColor = True
        '
        'dgvServices
        '
        Me.dgvServices.AllowUserToAddRows = False
        Me.dgvServices.AllowUserToDeleteRows = False
        Me.dgvServices.AllowUserToResizeColumns = False
        Me.dgvServices.AllowUserToResizeRows = False
        Me.dgvServices.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize
        Me.dgvServices.Location = New System.Drawing.Point(12, 298)
        Me.dgvServices.Name = "dgvServices"
        Me.dgvServices.ReadOnly = True
        Me.dgvServices.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect
        Me.dgvServices.Size = New System.Drawing.Size(360, 131)
        Me.dgvServices.TabIndex = 9
        '
        'lblServices
        '
        Me.lblServices.AutoSize = True
        Me.lblServices.Font = New System.Drawing.Font("Microsoft Sans Serif", 21.75!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.lblServices.Location = New System.Drawing.Point(131, 432)
        Me.lblServices.Name = "lblServices"
        Me.lblServices.Size = New System.Drawing.Size(128, 33)
        Me.lblServices.TabIndex = 10
        Me.lblServices.Text = "Services"
        '
        'ofdFilePicker
        '
        Me.ofdFilePicker.FileName = "OpenFileDialog1"
        '
        'dgvSchedule
        '
        Me.dgvSchedule.AllowUserToAddRows = False
        Me.dgvSchedule.AllowUserToDeleteRows = False
        Me.dgvSchedule.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize
        Me.dgvSchedule.Location = New System.Drawing.Point(399, 298)
        Me.dgvSchedule.Name = "dgvSchedule"
        Me.dgvSchedule.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect
        Me.dgvSchedule.Size = New System.Drawing.Size(551, 276)
        Me.dgvSchedule.TabIndex = 11
        '
        'lstSchedule
        '
        Me.lstSchedule.Font = New System.Drawing.Font("Courier New", 11.25!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.lstSchedule.FormattingEnabled = True
        Me.lstSchedule.ItemHeight = 17
        Me.lstSchedule.Location = New System.Drawing.Point(1001, 13)
        Me.lstSchedule.Name = "lstSchedule"
        Me.lstSchedule.Size = New System.Drawing.Size(824, 548)
        Me.lstSchedule.TabIndex = 12
        '
        'frmTurboAutoService
        '
        Me.AutoScaleDimensions = New System.Drawing.SizeF(6.0!, 13.0!)
        Me.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font
        Me.ClientSize = New System.Drawing.Size(1835, 586)
        Me.Controls.Add(Me.lstSchedule)
        Me.Controls.Add(Me.dgvSchedule)
        Me.Controls.Add(Me.lblServices)
        Me.Controls.Add(Me.dgvServices)
        Me.Controls.Add(Me.cmdScheduleService)
        Me.Controls.Add(Me.cmdAddVehicle)
        Me.Controls.Add(Me.cmdAddCustomer)
        Me.Controls.Add(Me.lblVehicles)
        Me.Controls.Add(Me.lblCustomers)
        Me.Controls.Add(Me.cmdProcessFile)
        Me.Controls.Add(Me.dgvVehicles)
        Me.Controls.Add(Me.dgvCustomers)
        Me.Name = "frmTurboAutoService"
        Me.Text = "Turbo Auto Service"
        CType(Me.dgvCustomers, System.ComponentModel.ISupportInitialize).EndInit()
        CType(Me.dgvVehicles, System.ComponentModel.ISupportInitialize).EndInit()
        CType(Me.dgvServices, System.ComponentModel.ISupportInitialize).EndInit()
        CType(Me.dgvSchedule, System.ComponentModel.ISupportInitialize).EndInit()
        Me.ResumeLayout(False)
        Me.PerformLayout()

    End Sub
    Friend WithEvents dgvCustomers As System.Windows.Forms.DataGridView
    Friend WithEvents dgvVehicles As System.Windows.Forms.DataGridView
    Friend WithEvents cmdProcessFile As System.Windows.Forms.Button
    Friend WithEvents lblCustomers As System.Windows.Forms.Label
    Friend WithEvents lblVehicles As System.Windows.Forms.Label
    Friend WithEvents cmdAddCustomer As System.Windows.Forms.Button
    Friend WithEvents cmdAddVehicle As System.Windows.Forms.Button
    Friend WithEvents cmdScheduleService As System.Windows.Forms.Button
    Friend WithEvents dgvServices As System.Windows.Forms.DataGridView
    Friend WithEvents lblServices As System.Windows.Forms.Label
    Friend WithEvents ofdFilePicker As System.Windows.Forms.OpenFileDialog
    Friend WithEvents dgvSchedule As System.Windows.Forms.DataGridView
    Friend WithEvents lstSchedule As System.Windows.Forms.ListBox

End Class
