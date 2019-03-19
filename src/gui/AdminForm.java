package gui;

import dal.ActionDAO;
import dal.ActionSetDAO;
import dal.ContainerDAO;
import dal.ObjectDAO;
import dal.PermissionDAO;
import dal.RoleDAO;
import dal.UserDAO;
import java.awt.Color;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import model.Action;
import model.ActionSet;
import model.ContainerModel;
import model.ObjectModel;
import model.Permission;
import model.RoleModel;
import model.User;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nam
 */
public class AdminForm extends javax.swing.JFrame {

    /**
     * Creates new form AdminForm
     */
    private LoginForm parent;
    private DefaultTableModel dtm1, dtm2, dtm3, dtmRolesAndPermissions;
    private JPanel activePanel;
    private JPanel activeBg;
    
    
    public AdminForm() {
        initComponents();
        initCustomComps();
    }

    public AdminForm(LoginForm p) {
        initComponents();
        initCustomComps();
        parent = p;
    }

    private void initCustomComps() {
        this.setTitle("Admin_page");
        initDayPicker();
        initTimePicker();
        initCreateActionTab();
        initCategorizeObject();
        initActionSet();
        initCreatePermission();
        initRolesAndPermissionsTab();
    }
    
    
    private void initCategorizeObject() {
        cbAllContainer_createContainer.setVisible(false);
        listShow_createContainer.setVisible(false);
        btnList_createContainer.setVisible(false);
        dtm1 = (DefaultTableModel) tableContainerCreate_createContainer.getModel();
        ObjectDAO objDAO = new ObjectDAO();
        ArrayList<ObjectModel> objects = objDAO.all();
        for (ObjectModel object : objects) {
            dtm1.addRow(new Object[]{object.getObjectName(), false});
        }
    }
    
    private void reloadObject() {
        dtm1 = (DefaultTableModel) tableContainerCreate_createContainer.getModel();
        while(dtm1.getRowCount() > 0)
            dtm1.removeRow(0);
        ObjectDAO objDAO = new ObjectDAO();
        ArrayList<ObjectModel> objects = objDAO.all();
        for (ObjectModel object : objects) {
            dtm1.addRow(new Object[]{object.getObjectName(), false});
        }
    }
    
    private void reloadAction() {
        dtm2 = (DefaultTableModel) tableActionSetCreate_createActionSet.getModel();
        while(dtm2.getRowCount() > 0)
            dtm2.removeRow(0);
        ActionDAO actionDAO = new ActionDAO();
        ArrayList<Action> actions = actionDAO.all();
        for (Action action : actions) {
            dtm2.addRow(new Object[]{action.getActionName(), false});
        }
    }
    
    private void initActionSet() {
        cbAllActionSet_createActionSet.setVisible(false);
        listShow_createActionSet.setVisible(false);
        btnList_createActionSet.setVisible(false);
        dtm2 = (DefaultTableModel) tableActionSetCreate_createActionSet.getModel();
        while(dtm2.getRowCount() > 0)
            dtm2.removeRow(0);
        ActionDAO actionDAO = new ActionDAO();
        ArrayList<Action> actions = actionDAO.all();
        for (Action action : actions) {
            dtm2.addRow(new Object[]{action.getActionName(), false});
        }
    }

    private void initCreateActionTab() {
        cbConflictedAction_createAction.removeAllItems();
        cbConflictedAction_createAction.addItem("None");
        ActionDAO actionDB = new ActionDAO();
        ArrayList<Action> actions = actionDB.all();
        for (Action action : actions) {
            cbConflictedAction_createAction.addItem(action.getActionName());
        }
    }
    
    private void initRolesAndPermissionsTab() {
        RoleDAO roleDAO = new RoleDAO();
        cbRoleName_rolesAndPermissions.removeAllItems();
        cbPermission_rolesAndPermissions.removeAllItems();
        ArrayList<RoleModel> roles = roleDAO.all();
        for (RoleModel role : roles) {
            cbRoleName_rolesAndPermissions.addItem(role.getRoleName());
        }
        // load permissions
        PermissionDAO permissionDAO = new  PermissionDAO();
        ObjectDAO objectDAO = new ObjectDAO();
        ActionDAO actionDAO = new ActionDAO();
        ArrayList<Permission> permissions = permissionDAO.all();
        
        for (Permission permission : permissions) {
            ObjectModel obj = objectDAO.getByID(permission.getObjectID());
            Action action = actionDAO.getByID(permission.getActionID());
            cbPermission_rolesAndPermissions.addItem(obj.getObjectName() + " -- " + action.getActionName());
        }
    }
    
    private void loadPermission() {
        PermissionDAO permissionDAO = new PermissionDAO();
        ObjectDAO objectDAO = new ObjectDAO();
        ActionDAO actionDAO = new ActionDAO();
        while(dtm3.getRowCount() > 0) {
            dtm3.removeRow(0);
        }
        ArrayList<Permission> permissions = permissionDAO.all();
        for (Permission permission : permissions) {
            dtm3.addRow(new String[]{ objectDAO.getByID(permission.getObjectID()).getObjectName(),
                                      actionDAO.getByID(permission.getActionID()).getActionName()});
        }
        
        
    }
    
    private void initCreatePermission() {
        dtm3 = (DefaultTableModel) tablePermissionShow_createPermission.getModel();
        loadPermission();
        ContainerDAO containerDAO = new ContainerDAO();
        ActionSetDAO setDAO = new ActionSetDAO();
        ActionDAO actionDAO = new ActionDAO();
        ObjectDAO objectDAO = new ObjectDAO();
        ArrayList<ContainerModel> containers = containerDAO.all();
        ArrayList<ActionSet> sets = setDAO.all();
        ArrayList<Action> actions = actionDAO.all();
        ArrayList<ObjectModel> objects = objectDAO.all();
        cbContainer_createPermissionM1.removeAllItems();
        cbContainer_createPermissionM2.removeAllItems();
        cbSet_createPermissionM1.removeAllItems();
        cbSet_createPermissionM3.removeAllItems();
        cbObject_createPermissionM3.removeAllItems();
        cbObject_createPermissionM4.removeAllItems();
        cbAction_createPermissionM2.removeAllItems();
        cbAction_createPermissionM4   .removeAllItems();     
        for (ContainerModel container : containers) {
            cbContainer_createPermissionM1.addItem(container.getContainerName());
            cbContainer_createPermissionM2.addItem(container.getContainerName());
        }
        for (ActionSet set : sets) {
            cbSet_createPermissionM1.addItem(set.getActionSetName());
            cbSet_createPermissionM3.addItem(set.getActionSetName());
        }
        for (ObjectModel object : objects) {
            cbObject_createPermissionM3.addItem(object.getObjectName());
            cbObject_createPermissionM4.addItem(object.getObjectName());
        }
        for (Action action : actions) {
            cbAction_createPermissionM2.addItem(action.getActionName());
            cbAction_createPermissionM4.addItem(action.getActionName());
        }

    }

    private void initTimePicker() {

        Date date = new Date();
        SpinnerDateModel sdmFrom_createUser = new SpinnerDateModel(date, null, null, Calendar.MINUTE);
        SpinnerDateModel sdmTo_createUser = new SpinnerDateModel(date, null, null, Calendar.MINUTE);

        // initilize time picker for create user tab
        spinnerFromTime__createUser.setModel(sdmFrom_createUser);
        spinnerToTime_createUser.setModel(sdmTo_createUser);
        JSpinner.DateEditor deFrom_createUser = new JSpinner.DateEditor(spinnerFromTime__createUser, "hh:mm:ss a");
        JSpinner.DateEditor deTo_createUser = new JSpinner.DateEditor(spinnerToTime_createUser, "hh:mm:ss a");
        deFrom_createUser.getTextField().setEditable(false);
        deTo_createUser.getTextField().setEditable(false);
        spinnerFromTime__createUser.setEditor(deFrom_createUser);
        spinnerToTime_createUser.setEditor(deTo_createUser);

        
        SpinnerDateModel sdmFrom_createObject = new SpinnerDateModel(date, null, null, Calendar.MINUTE);
        SpinnerDateModel sdmTo_createObject = new SpinnerDateModel(date, null, null, Calendar.MINUTE);
        
        // initilize time picker for create object tab
        spinnerFromTime_createObject.setModel(sdmFrom_createObject);
        spinnerToTime_createObject.setModel(sdmTo_createObject);
        JSpinner.DateEditor deFrom_createObject = new JSpinner.DateEditor(spinnerFromTime_createObject, "hh:mm:ss a");
        JSpinner.DateEditor deTo_createObject = new JSpinner.DateEditor(spinnerToTime_createObject, "hh:mm:ss a");
        deFrom_createObject.getTextField().setEditable(false);
        deTo_createObject.getTextField().setEditable(false);
        spinnerFromTime_createObject.setEditor(deFrom_createObject);
        spinnerToTime_createObject.setEditor(deTo_createObject);

        SpinnerDateModel sdmFrom_createRole = new SpinnerDateModel(date, null, null, Calendar.MINUTE);
        SpinnerDateModel sdmTo_createRole = new SpinnerDateModel(date, null, null, Calendar.MINUTE);        
        
        // initilize time picker for create role tab
        spinnerFromTime_createRole.setModel(sdmFrom_createRole);
        spinnerToTime_createRole.setModel(sdmTo_createRole);
        JSpinner.DateEditor deFrom_createRole = new JSpinner.DateEditor(spinnerFromTime_createRole, "hh:mm:ss a");
        JSpinner.DateEditor deTo_createRole = new JSpinner.DateEditor(spinnerToTime_createRole, "hh:mm:ss a");
        deFrom_createRole.getTextField().setEditable(false);
        deTo_createRole.getTextField().setEditable(false);
        spinnerFromTime_createRole.setEditor(deFrom_createRole);
        spinnerToTime_createRole.setEditor(deTo_createRole);

    }

    private void initDayPicker() {
        DateFormatSymbols dfs = new DateFormatSymbols();
        cbFromDay__createUser.removeAllItems();
        cbToDay_createUser.removeAllItems();
        cbFromDay_createObject.removeAllItems();
        cbToDay_createObject.removeAllItems();
        cbFromDay_createRole.removeAllItems();
        cbToDay_createRole.removeAllItems();
        for (String day : dfs.getWeekdays()) {
            if (day.equals("")) {
                continue;
            }
            String upperDay = day.toUpperCase();
            cbFromDay__createUser.addItem(upperDay);
            cbToDay_createUser.addItem(upperDay);
            cbFromDay_createObject.addItem(upperDay);
            cbToDay_createObject.addItem(upperDay);
            cbFromDay_createRole.addItem(upperDay);
            cbToDay_createRole.addItem(upperDay);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bg0 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        bg1 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        bg2 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        bg3 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        bg4 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        bg5 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        bg6 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        bg7 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        tabbed = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUsername_createUser = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPassword__createUser = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        cbFromDay__createUser = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cbToDay_createUser = new javax.swing.JComboBox<>();
        spinnerToTime_createUser = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        spinnerFromTime__createUser = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtIpAddress__createUser = new javax.swing.JTextField();
        btnCreate__createUser = new javax.swing.JButton();
        btnRefresh__createUser = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtObjName_createObject = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        cbFromDay_createObject = new javax.swing.JComboBox<>();
        cbToDay_createObject = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        spinnerFromTime_createObject = new javax.swing.JSpinner();
        spinnerToTime_createObject = new javax.swing.JSpinner();
        jLabel19 = new javax.swing.JLabel();
        txtIpAddress_createObject = new javax.swing.JTextField();
        btnCreate_createObject = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtActionName_createAction = new javax.swing.JTextField();
        btnCreate_createAction = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        cbConflictedAction_createAction = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtRoleName_createRole = new javax.swing.JTextField();
        cbFromDay_createRole = new javax.swing.JComboBox<>();
        cbToDay_createRole = new javax.swing.JComboBox<>();
        spinnerFromTime_createRole = new javax.swing.JSpinner();
        spinnerToTime_createRole = new javax.swing.JSpinner();
        txtIpAddress_createRole = new javax.swing.JTextField();
        btnCreate_createRole = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtContainerName_createContainer = new javax.swing.JTextField();
        btnCreate_createContainer = new javax.swing.JButton();
        chkShowList_createContainer = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableContainerCreate_createContainer = new javax.swing.JTable();
        cbAllContainer_createContainer = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        listShow_createContainer = new javax.swing.JList<>();
        btnList_createContainer = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtActionSetName_createActionSet = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        btnCreate_createContainer1 = new javax.swing.JButton();
        cbAllActionSet_createActionSet = new javax.swing.JComboBox<>();
        chkShowList_createActionSet = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableActionSetCreate_createActionSet = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        listShow_createActionSet = new javax.swing.JList<>();
        btnList_createActionSet = new javax.swing.JButton();
        fadsfsd = new javax.swing.JSeparator();
        jPanel8 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        cbContainer_createPermissionM1 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbSet_createPermissionM1 = new javax.swing.JComboBox<>();
        btnCreate_createPermissionM1 = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        cbContainer_createPermissionM2 = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        cbAction_createPermissionM2 = new javax.swing.JComboBox<>();
        btnCreate_createPermissionM2 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        cbObject_createPermissionM3 = new javax.swing.JComboBox<>();
        jLabel42 = new javax.swing.JLabel();
        cbSet_createPermissionM3 = new javax.swing.JComboBox<>();
        btnCreate_createPermissionM3 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        cbObject_createPermissionM4 = new javax.swing.JComboBox<>();
        jLabel45 = new javax.swing.JLabel();
        cbAction_createPermissionM4 = new javax.swing.JComboBox<>();
        btnCreate_createPermissionM4 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablePermissionShow_createPermission = new javax.swing.JTable();
        fadsfsd1 = new javax.swing.JSeparator();
        jPanel9 = new javax.swing.JPanel();
        cbRoleName_rolesAndPermissions = new javax.swing.JComboBox<>();
        jLabel48ff = new javax.swing.JLabel();
        jLabel49ff = new javax.swing.JLabel();
        cbPermission_rolesAndPermissions = new javax.swing.JComboBox<>();
        jLabel51 = new javax.swing.JLabel();
        fadsfsd2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(76, 41, 211));
        jPanel14.setPreferredSize(new java.awt.Dimension(966, 50));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("<html> Permission Based SOD <br/> in Dynamic RBAC </html>");

        bg0.setBackground(new java.awt.Color(76, 41, 211));
        bg0.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 24)); // NOI18N
        bg0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bg0MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bg0MousePressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 204, 204));
        jLabel16.setText("User creation");

        javax.swing.GroupLayout bg0Layout = new javax.swing.GroupLayout(bg0);
        bg0.setLayout(bg0Layout);
        bg0Layout.setHorizontalGroup(
            bg0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bg0Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel16))
        );
        bg0Layout.setVerticalGroup(
            bg0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bg0Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 204, 204));
        jLabel11.setText("Admin Page");

        jButton1.setBackground(new java.awt.Color(76, 41, 211));
        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(204, 204, 204));
        jButton1.setText("Logout");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        bg1.setBackground(new java.awt.Color(76, 41, 211));
        bg1.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 24)); // NOI18N
        bg1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bg1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bg1MousePressed(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(204, 204, 204));
        jLabel48.setText("Object Creation");

        javax.swing.GroupLayout bg1Layout = new javax.swing.GroupLayout(bg1);
        bg1.setLayout(bg1Layout);
        bg1Layout.setHorizontalGroup(
            bg1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bg1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel48)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bg1Layout.setVerticalGroup(
            bg1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bg1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel48)
                .addContainerGap())
        );

        bg2.setBackground(new java.awt.Color(76, 41, 211));
        bg2.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 24)); // NOI18N
        bg2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bg2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bg2MousePressed(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(204, 204, 204));
        jLabel49.setText("Action Creation");

        javax.swing.GroupLayout bg2Layout = new javax.swing.GroupLayout(bg2);
        bg2.setLayout(bg2Layout);
        bg2Layout.setHorizontalGroup(
            bg2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bg2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel49)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bg2Layout.setVerticalGroup(
            bg2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bg2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel49)
                .addContainerGap())
        );

        bg3.setBackground(new java.awt.Color(76, 41, 211));
        bg3.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 24)); // NOI18N
        bg3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bg3MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bg3MousePressed(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(204, 204, 204));
        jLabel52.setText("Role Creation");

        javax.swing.GroupLayout bg3Layout = new javax.swing.GroupLayout(bg3);
        bg3.setLayout(bg3Layout);
        bg3Layout.setHorizontalGroup(
            bg3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bg3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel52)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bg3Layout.setVerticalGroup(
            bg3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bg3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel52)
                .addContainerGap())
        );

        bg4.setBackground(new java.awt.Color(76, 41, 211));
        bg4.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 24)); // NOI18N
        bg4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bg4MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bg4MousePressed(evt);
            }
        });

        jLabel53.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(204, 204, 204));
        jLabel53.setText("Container Creation");

        javax.swing.GroupLayout bg4Layout = new javax.swing.GroupLayout(bg4);
        bg4.setLayout(bg4Layout);
        bg4Layout.setHorizontalGroup(
            bg4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bg4Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel53)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bg4Layout.setVerticalGroup(
            bg4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bg4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel53)
                .addContainerGap())
        );

        bg5.setBackground(new java.awt.Color(76, 41, 211));
        bg5.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 24)); // NOI18N
        bg5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bg5MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bg5MousePressed(evt);
            }
        });

        jLabel54.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(204, 204, 204));
        jLabel54.setText("Action Set Creation");

        javax.swing.GroupLayout bg5Layout = new javax.swing.GroupLayout(bg5);
        bg5.setLayout(bg5Layout);
        bg5Layout.setHorizontalGroup(
            bg5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bg5Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel54)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bg5Layout.setVerticalGroup(
            bg5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bg5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel54)
                .addContainerGap())
        );

        bg6.setBackground(new java.awt.Color(76, 41, 211));
        bg6.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 24)); // NOI18N
        bg6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bg6MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bg6MousePressed(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(204, 204, 204));
        jLabel56.setText("Permission Creation");

        javax.swing.GroupLayout bg6Layout = new javax.swing.GroupLayout(bg6);
        bg6.setLayout(bg6Layout);
        bg6Layout.setHorizontalGroup(
            bg6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bg6Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel56)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bg6Layout.setVerticalGroup(
            bg6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bg6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel56)
                .addContainerGap())
        );

        bg7.setBackground(new java.awt.Color(76, 41, 211));
        bg7.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 24)); // NOI18N
        bg7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bg7MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bg7MousePressed(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(204, 204, 204));
        jLabel57.setText("Roles And Permissions");

        javax.swing.GroupLayout bg7Layout = new javax.swing.GroupLayout(bg7);
        bg7.setLayout(bg7Layout);
        bg7Layout.setHorizontalGroup(
            bg7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bg7Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel57)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bg7Layout.setVerticalGroup(
            bg7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bg7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel57)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bg7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bg1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(bg3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bg2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bg4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bg5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bg6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bg0, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(695, 695, 695))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jButton1))
                .addGap(34, 34, 34)
                .addComponent(bg0, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bg1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bg2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bg3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bg4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bg5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bg6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bg7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabbed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabbedFocusGained(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("User Creation Panel");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("User Name");

        txtUsername_createUser.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Password");

        txtPassword__createUser.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("From Day");

        cbFromDay__createUser.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N
        cbFromDay__createUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("To");

        cbToDay_createUser.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N
        cbToDay_createUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        spinnerToTime_createUser.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("To");

        spinnerFromTime__createUser.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N
        spinnerFromTime__createUser.setToolTipText("");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("From Time");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("IP Address");

        txtIpAddress__createUser.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        btnCreate__createUser.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btnCreate__createUser.setForeground(new java.awt.Color(102, 102, 102));
        btnCreate__createUser.setText("Create");
        btnCreate__createUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreate__createUserActionPerformed(evt);
            }
        });

        btnRefresh__createUser.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btnRefresh__createUser.setForeground(new java.awt.Color(102, 102, 102));
        btnRefresh__createUser.setText("Refresh");
        btnRefresh__createUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefresh__createUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbFromDay__createUser, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(spinnerFromTime__createUser, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbToDay_createUser, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinnerToTime_createUser, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtPassword__createUser)
                            .addComponent(txtUsername_createUser)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(txtIpAddress__createUser, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel8))
                                .addGap(30, 30, 30)
                                .addComponent(btnCreate__createUser, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnRefresh__createUser, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(168, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtUsername_createUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPassword__createUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbToDay_createUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(cbFromDay__createUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtIpAddress__createUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(spinnerFromTime__createUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(spinnerToTime_createUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate__createUser)
                    .addComponent(btnRefresh__createUser))
                .addContainerGap(193, Short.MAX_VALUE))
        );

        tabbed.addTab("", jPanel1);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setText("Object Creation Panel");

        jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 102));
        jLabel15.setText("Object Name");

        txtObjName_createObject.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("From Day");

        cbFromDay_createObject.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N
        cbFromDay_createObject.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbToDay_createObject.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N
        cbToDay_createObject.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel18.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setText("From Time");

        spinnerFromTime_createObject.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N
        spinnerFromTime_createObject.setToolTipText("");

        spinnerToTime_createObject.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 102, 102));
        jLabel19.setText("IP Address");

        txtIpAddress_createObject.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        btnCreate_createObject.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btnCreate_createObject.setForeground(new java.awt.Color(102, 102, 102));
        btnCreate_createObject.setText("Create");
        btnCreate_createObject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreate_createObjectActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(102, 102, 102));
        jLabel34.setText("To");

        jLabel35.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(102, 102, 102));
        jLabel35.setText("To");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel18)
                                        .addComponent(jLabel15)
                                        .addComponent(jLabel17))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addComponent(jLabel19)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIpAddress_createObject, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtObjName_createObject, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(cbFromDay_createObject, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(spinnerFromTime_createObject, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel34)
                                            .addComponent(jLabel35))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cbToDay_createObject, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(spinnerToTime_createObject))))
                                    .addComponent(btnCreate_createObject, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtObjName_createObject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(cbFromDay_createObject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbToDay_createObject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(spinnerFromTime_createObject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(spinnerToTime_createObject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel35))
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtIpAddress_createObject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(btnCreate_createObject)
                .addContainerGap(260, Short.MAX_VALUE))
        );

        tabbed.addTab("", jPanel2);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel3FocusGained(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Action Creation Panel");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Action Name");

        txtActionName_createAction.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        btnCreate_createAction.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btnCreate_createAction.setForeground(new java.awt.Color(102, 102, 102));
        btnCreate_createAction.setText("Create");
        btnCreate_createAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreate_createActionActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(102, 102, 102));
        jLabel27.setText("Conflicted Action");

        cbConflictedAction_createAction.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N
        cbConflictedAction_createAction.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCreate_createAction, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtActionName_createAction)
                                .addComponent(cbConflictedAction_createAction, 0, 160, Short.MAX_VALUE)))))
                .addContainerGap(168, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtActionName_createAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(cbConflictedAction_createAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(btnCreate_createAction)
                .addContainerGap(345, Short.MAX_VALUE))
        );

        tabbed.addTab("", jPanel3);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        jLabel21.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(102, 102, 102));
        jLabel21.setText("Role Creation Panel");

        jLabel22.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 102, 102));
        jLabel22.setText("Role Name");

        jLabel23.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(102, 102, 102));
        jLabel23.setText("From Day");

        jLabel24.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(102, 102, 102));
        jLabel24.setText("From Time");

        jLabel25.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 102, 102));
        jLabel25.setText("IP Address");

        txtRoleName_createRole.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        cbFromDay_createRole.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N
        cbFromDay_createRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbToDay_createRole.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N
        cbToDay_createRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        spinnerFromTime_createRole.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N
        spinnerFromTime_createRole.setToolTipText("");

        spinnerToTime_createRole.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        txtIpAddress_createRole.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        btnCreate_createRole.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btnCreate_createRole.setForeground(new java.awt.Color(102, 102, 102));
        btnCreate_createRole.setText("Create");
        btnCreate_createRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreate_createRoleActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(102, 102, 102));
        jLabel46.setText("To");

        jLabel47.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(102, 102, 102));
        jLabel47.setText("To");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtRoleName_createRole)
                            .addComponent(txtIpAddress_createRole)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbFromDay_createRole, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinnerFromTime_createRole, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel46)
                                    .addComponent(jLabel47))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbToDay_createRole, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinnerToTime_createRole, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(btnCreate_createRole, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(168, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtRoleName_createRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(cbFromDay_createRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbToDay_createRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(spinnerFromTime_createRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(spinnerToTime_createRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel47)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtIpAddress_createRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(btnCreate_createRole)
                .addContainerGap(297, Short.MAX_VALUE))
        );

        tabbed.addTab("", jPanel4);

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel6FocusGained(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(102, 102, 102));
        jLabel28.setText("Categorize Container Panel");

        jLabel29.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(102, 102, 102));
        jLabel29.setText("Object Container Name");

        txtContainerName_createContainer.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        btnCreate_createContainer.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btnCreate_createContainer.setForeground(new java.awt.Color(102, 102, 102));
        btnCreate_createContainer.setText("Create");
        btnCreate_createContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreate_createContainerActionPerformed(evt);
            }
        });

        chkShowList_createContainer.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        chkShowList_createContainer.setForeground(new java.awt.Color(102, 102, 102));
        chkShowList_createContainer.setText("Show List");
        chkShowList_createContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowList_createContainerActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setText("Objects");

        tableContainerCreate_createContainer.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N
        tableContainerCreate_createContainer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ObjectName", "Add to container"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableContainerCreate_createContainer);

        cbAllContainer_createContainer.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        listShow_createContainer.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N
        jScrollPane5.setViewportView(listShow_createContainer);

        btnList_createContainer.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btnList_createContainer.setForeground(new java.awt.Color(102, 102, 102));
        btnList_createContainer.setText("List");
        btnList_createContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnList_createContainerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnCreate_createContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(chkShowList_createContainer))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtContainerName_createContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(btnList_createContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbAllContainer_createContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                        .addGap(22, 22, 22))))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(jLabel28)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(chkShowList_createContainer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtContainerName_createContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbAllContainer_createContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnList_createContainer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane5)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(btnCreate_createContainer)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        tabbed.addTab("", jPanel6);

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel7FocusGained(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(102, 102, 102));
        jLabel30.setText("Generic Action Set Panel");

        jLabel31.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(102, 102, 102));
        jLabel31.setText("Action Level Name");

        txtActionSetName_createActionSet.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(102, 102, 102));
        jLabel33.setText("Actions");

        btnCreate_createContainer1.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btnCreate_createContainer1.setForeground(new java.awt.Color(102, 102, 102));
        btnCreate_createContainer1.setText("Create");
        btnCreate_createContainer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreate_createContainer1ActionPerformed(evt);
            }
        });

        cbAllActionSet_createActionSet.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        chkShowList_createActionSet.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        chkShowList_createActionSet.setForeground(new java.awt.Color(102, 102, 102));
        chkShowList_createActionSet.setText("Show List");
        chkShowList_createActionSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowList_createActionSetActionPerformed(evt);
            }
        });

        tableActionSetCreate_createActionSet.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N
        tableActionSetCreate_createActionSet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Action Name", "Add to set"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableActionSetCreate_createActionSet);

        listShow_createActionSet.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N
        jScrollPane6.setViewportView(listShow_createActionSet);

        btnList_createActionSet.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btnList_createActionSet.setForeground(new java.awt.Color(102, 102, 102));
        btnList_createActionSet.setText("List");
        btnList_createActionSet.setPreferredSize(new java.awt.Dimension(60, 23));
        btnList_createActionSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnList_createActionSetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(txtActionSetName_createActionSet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCreate_createContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkShowList_createActionSet)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(btnList_createActionSet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbAllActionSet_createActionSet, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(fadsfsd, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fadsfsd, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(0, 0, 0)
                        .addComponent(txtActionSetName_createActionSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(chkShowList_createActionSet)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnList_createActionSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbAllActionSet_createActionSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(btnCreate_createContainer1)
                .addGap(21, 21, 21))
        );

        tabbed.addTab("", jPanel7);

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel8FocusGained(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(102, 102, 102));
        jLabel32.setText("<html> Conflicted and Non-Conflicted Permissions <br/>with Attributes</html");

        jTabbedPane1.setForeground(new java.awt.Color(102, 102, 102));
        jTabbedPane1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        cbContainer_createPermissionM1.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(102, 102, 102));
        jLabel26.setText("Categorize Container");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Generic Action Set");

        cbSet_createPermissionM1.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        btnCreate_createPermissionM1.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btnCreate_createPermissionM1.setForeground(new java.awt.Color(102, 102, 102));
        btnCreate_createPermissionM1.setText("Create");
        btnCreate_createPermissionM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreate_createPermissionM1ActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(102, 102, 102));
        jLabel36.setText("Multiple objects to multiple actions");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel26))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCreate_createPermissionM1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbContainer_createPermissionM1, 0, 160, Short.MAX_VALUE)
                            .addComponent(cbSet_createPermissionM1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel36)
                .addGap(58, 58, 58)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbContainer_createPermissionM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbSet_createPermissionM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCreate_createPermissionM1)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Method 1", jPanel5);

        jLabel37.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(102, 102, 102));
        jLabel37.setText("Multiple objects to single action");

        jLabel38.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(102, 102, 102));
        jLabel38.setText("Categorize Container");

        cbContainer_createPermissionM2.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        jLabel39.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(102, 102, 102));
        jLabel39.setText("Action");

        cbAction_createPermissionM2.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        btnCreate_createPermissionM2.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btnCreate_createPermissionM2.setForeground(new java.awt.Color(102, 102, 102));
        btnCreate_createPermissionM2.setText("Create");
        btnCreate_createPermissionM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreate_createPermissionM2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel38)
                            .addComponent(jLabel39))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCreate_createPermissionM2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbContainer_createPermissionM2, 0, 160, Short.MAX_VALUE)
                            .addComponent(cbAction_createPermissionM2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel37)
                .addGap(58, 58, 58)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbContainer_createPermissionM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(cbAction_createPermissionM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCreate_createPermissionM2)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Method 2", jPanel10);

        jLabel40.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(102, 102, 102));
        jLabel40.setText("Single object to multiple actions");

        jLabel41.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(102, 102, 102));
        jLabel41.setText("Object");

        cbObject_createPermissionM3.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(102, 102, 102));
        jLabel42.setText("Action Set");

        cbSet_createPermissionM3.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        btnCreate_createPermissionM3.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btnCreate_createPermissionM3.setForeground(new java.awt.Color(102, 102, 102));
        btnCreate_createPermissionM3.setText("Create");
        btnCreate_createPermissionM3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreate_createPermissionM3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel40)
                .addContainerGap(78, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel42)
                    .addComponent(jLabel41))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCreate_createPermissionM3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSet_createPermissionM3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbObject_createPermissionM3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel40)
                .addGap(58, 58, 58)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbObject_createPermissionM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(cbSet_createPermissionM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCreate_createPermissionM3)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Method 3", jPanel11);

        jLabel43.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(102, 102, 102));
        jLabel43.setText("Single object to single action");

        jLabel44.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(102, 102, 102));
        jLabel44.setText("Object");

        cbObject_createPermissionM4.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        jLabel45.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(102, 102, 102));
        jLabel45.setText("Action");

        cbAction_createPermissionM4.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        btnCreate_createPermissionM4.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btnCreate_createPermissionM4.setForeground(new java.awt.Color(102, 102, 102));
        btnCreate_createPermissionM4.setText("Create");
        btnCreate_createPermissionM4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreate_createPermissionM4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel43)
                .addContainerGap(106, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel45)
                    .addComponent(jLabel44))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCreate_createPermissionM4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbObject_createPermissionM4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbAction_createPermissionM4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel43)
                .addGap(58, 58, 58)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbObject_createPermissionM4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(cbAction_createPermissionM4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCreate_createPermissionM4)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Method 4", jPanel12);

        tablePermissionShow_createPermission.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N
        tablePermissionShow_createPermission.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Action Name", "Object Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tablePermissionShow_createPermission);
        if (tablePermissionShow_createPermission.getColumnModel().getColumnCount() > 0) {
            tablePermissionShow_createPermission.getColumnModel().getColumn(0).setResizable(false);
            tablePermissionShow_createPermission.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fadsfsd1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fadsfsd1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane1)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(63, 63, 63))
        );

        tabbed.addTab("", jPanel8);

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));

        cbRoleName_rolesAndPermissions.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        jLabel48ff.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel48ff.setForeground(new java.awt.Color(102, 102, 102));
        jLabel48ff.setText("Role Name");

        jLabel49ff.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel49ff.setForeground(new java.awt.Color(102, 102, 102));
        jLabel49ff.setText("Permission");

        cbPermission_rolesAndPermissions.setFont(new java.awt.Font("Segoe UI Historic", 0, 13)); // NOI18N

        jLabel51.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(102, 102, 102));
        jLabel51.setText("Permission Execution");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel49ff)
                    .addComponent(jLabel48ff))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbRoleName_rolesAndPermissions, 0, 160, Short.MAX_VALUE)
                    .addComponent(cbPermission_rolesAndPermissions, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(324, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fadsfsd2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel51)
                .addGap(15, 15, 15)
                .addComponent(fadsfsd2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48ff)
                    .addComponent(cbRoleName_rolesAndPermissions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49ff)
                    .addComponent(cbPermission_rolesAndPermissions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(404, Short.MAX_VALUE))
        );

        tabbed.addTab("", jPanel9);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tabbed, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbed)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        this.dispose();
        parent.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnCreate_createActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreate_createActionActionPerformed
        if (isValidString(txtActionName_createAction.getText())) {
            ActionDAO actionDAO = new ActionDAO();
            Action action = new Action();
            action.setActionName(txtActionName_createAction.getText());
            String conflictedAction = (String) cbConflictedAction_createAction.getSelectedItem();

            if (!conflictedAction.equals("None")) {
                action.setConflictedAction(actionDAO.getByName(conflictedAction));
            }
            int status = actionDAO.insert(action);
            action.setActionID(actionDAO.getCurrentID());

            if (status == 1) {
                initCreateActionTab();
                initActionSet();
                JOptionPane.showMessageDialog(this,
                        "Action has been successfully added.",
                        "Action creation result",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Action has not been added.",
                        "Action creation result",
                        JOptionPane.INFORMATION_MESSAGE);
                reloadAction();
                initCreatePermission();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "You need to validate all fields.",
                    "Action fiels validation",
                    JOptionPane.INFORMATION_MESSAGE);

        }
    }//GEN-LAST:event_btnCreate_createActionActionPerformed

    private void btnCreate__createUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreate__createUserActionPerformed
        if (isValidString(txtUsername_createUser.getText())
                && isValidIpAddress(txtIpAddress__createUser.getText())) {
            User user = new User();
            user.setUsername(txtUsername_createUser.getText());
            user.setPassword(txtPassword__createUser.getText());
            user.setFromDay(cbFromDay__createUser.getSelectedItem().toString());
            user.setToDay(cbToDay_createUser.getSelectedItem().toString());
            user.setFromTime(toDateString((Date) spinnerFromTime__createUser.getValue()));
            user.setToTime(toDateString((Date) spinnerToTime_createUser.getValue()));
            user.setIpAddress(txtIpAddress__createUser.getText());
            UserDAO userDB = new UserDAO();
            int status = userDB.insert(user);
            if (status == 1) {
                JOptionPane.showMessageDialog(this,
                        "User has been successfully added.",
                        "User creation result",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "User has not been added.",
                        "User creation result",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this,
                    "You need to validate all fields.",
                    "User fiels validation",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnCreate__createUserActionPerformed

    private void btnRefresh__createUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefresh__createUserActionPerformed
        txtUsername_createUser.setText("");
        txtPassword__createUser.setText("");
        txtIpAddress__createUser.setText("");
        cbFromDay__createUser.setSelectedIndex(0);
        cbToDay_createUser.setSelectedIndex(0);
    }//GEN-LAST:event_btnRefresh__createUserActionPerformed

    private void btnCreate_createObjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreate_createObjectActionPerformed
        if (isValidString(txtObjName_createObject.getText())
                && isValidIpAddress(txtIpAddress_createObject.getText())) {

            ObjectDAO objDB = new ObjectDAO();
            ObjectModel obj = new ObjectModel();
            obj.setObjectID(objDB.getCurrentID() + 1);
            obj.setObjectName(txtObjName_createObject.getText());
            obj.setFromDay((String) cbFromDay_createObject.getSelectedItem());
            obj.setToDay((String) cbToDay_createObject.getSelectedItem());
            obj.setFromTime(toDateString((Date) spinnerFromTime_createObject.getValue()));
            obj.setToTime(toDateString((Date) spinnerToTime_createObject.getValue()));
            obj.setIpAddress(txtIpAddress_createObject.getText());

            int status = objDB.insert(obj);
            obj.setObjectID(objDB.getCurrentID());
            if (status == 1) {
                JOptionPane.showMessageDialog(this,
                        "Object has been successfully added.",
                        "Object creation result",
                        JOptionPane.INFORMATION_MESSAGE);
                reloadObject();
                initCreatePermission();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Object has not been added.",
                        "Object creation result",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "You need to validate all fields.",
                    "Object fiels validation",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnCreate_createObjectActionPerformed

    private void btnCreate_createRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreate_createRoleActionPerformed
        if (isValidString(txtRoleName_createRole.getText())
                    && isValidIpAddress(txtIpAddress_createRole.getText())) {
            RoleDAO roleDB = new RoleDAO();
            RoleModel role = new RoleModel();
            role.setRoleID(roleDB.getCurrentID() + 1);
            role.setRoleName(txtRoleName_createRole.getText());
            role.setFromDay((String) cbFromDay_createRole.getSelectedItem());
            role.setToDay((String) cbToDay_createRole.getSelectedItem());
            role.setFromTime(toDateString((Date) spinnerFromTime_createRole.getValue()));
            role.setToTime(toDateString((Date) spinnerToTime_createRole.getValue()));
            
            role.setIpAddress(txtIpAddress_createObject.getText());

            System.out.println("-------" + role.getFromTime());
            
            int status = roleDB.insert(role);
            role.setRoleID(roleDB.getCurrentID());
            if (status == 1) {
                JOptionPane.showMessageDialog(this,
                        "Role has been successfully added.",
                        "Role creation result",
                        JOptionPane.INFORMATION_MESSAGE);
                initRolesAndPermissionsTab();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Role has not been added.",
                        "Role creation result",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "You need to validate all fields.",
                    "Role fiels validation",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnCreate_createRoleActionPerformed

    private void btnCreate_createContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreate_createContainerActionPerformed
        if (isValidString(txtContainerName_createContainer.getText())) {
            ArrayList<ObjectModel> selectedObjects = new ArrayList<>();
            ObjectDAO objDAO = new ObjectDAO();
//            System.out.println("total row: " + dtm.getRowCount() + " column: " + dtm.getColumnCount());
            for (int i = 0; i < dtm1.getRowCount(); i++) {
                if ((boolean) dtm1.getValueAt(i, 1) == true) {
                    selectedObjects.add(objDAO.getByName((String) dtm1.getValueAt(i, 0)));
                }
            }

            if (selectedObjects.size() == 0) {
                JOptionPane.showMessageDialog(this,
                        "You need to add at least one object to this container.",
                        "Container Information",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                ContainerDAO containerDAO = new ContainerDAO();
                ContainerModel container = new ContainerModel();

                container.setContainerName(txtContainerName_createContainer.getText());
                container.setObjects(selectedObjects);

                int status = containerDAO.insert(container);
                container.setContainerID(containerDAO.getCurrentID());

                if (status == 1) {
//                    initCreateActionTab();
                    JOptionPane.showMessageDialog(this,
                            "Container has been successfully added.",
                            "Container creation result",
                            JOptionPane.INFORMATION_MESSAGE);
                    initCreatePermission();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Container has not been added.",
                            "Container creation result",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "You need to validate all fields.",
                    "Container fiels validation",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnCreate_createContainerActionPerformed

    private void chkShowList_createContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowList_createContainerActionPerformed
        if (chkShowList_createContainer.isSelected()) {
            cbAllContainer_createContainer.setVisible(true);
            listShow_createContainer.setVisible(true);
            btnList_createContainer.setVisible(true);

            // reset data
            listShow_createContainer.setModel(new DefaultListModel());
            cbAllContainer_createContainer.removeAllItems();
            loadContainerShowList();
        } else {
            cbAllContainer_createContainer.setVisible(false);
            listShow_createContainer.setVisible(false);
            btnList_createContainer.setVisible(false);
        }
    }//GEN-LAST:event_chkShowList_createContainerActionPerformed

    private void btnList_createContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnList_createContainerActionPerformed
        ContainerDAO containerDAO = new ContainerDAO();
        ArrayList<String> listObject = containerDAO.getAllObjectNameInContainer((String) cbAllContainer_createContainer.getSelectedItem());
        System.out.println("selected: " + (String) cbAllContainer_createContainer.getSelectedItem());
        DefaultListModel dlm_createContainer = new DefaultListModel();
        for (String objName : listObject) {
            System.out.println("in " + (String) cbAllContainer_createContainer.getSelectedItem() + ":" + objName);
            dlm_createContainer.addElement(objName);
        }
        listShow_createContainer.setModel(dlm_createContainer);
    }//GEN-LAST:event_btnList_createContainerActionPerformed

    private void jPanel3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel3FocusGained
        initCreateActionTab();
    }//GEN-LAST:event_jPanel3FocusGained

    private void tabbedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabbedFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tabbedFocusGained

    private void jPanel6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel6FocusGained
        initCategorizeObject();
    }//GEN-LAST:event_jPanel6FocusGained

    private void setColor(JPanel panel){
        panel.setBackground(new Color(135, 112, 225));
    }
    private void resetColor(JPanel panel){
        panel.setBackground(new Color(76, 41, 211));
    }
    
    private void setPanel(JPanel panel) {
        panel.setVisible(true);
    }
    
    private void hidePanel(JPanel panel) {
        panel.setVisible(false);
    }
    
    private void bg0MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg0MouseClicked
        tabbed.setSelectedIndex(0);
        setColor(bg0);
        resetColor(bg1);
        resetColor(bg2);
        resetColor(bg3);
        resetColor(bg4);
        resetColor(bg5);
        resetColor(bg6);
        resetColor(bg7);
    }//GEN-LAST:event_bg0MouseClicked

    private void bg1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg1MouseClicked
        tabbed.setSelectedIndex(1);
        resetColor(bg0);
        setColor(bg1);
        resetColor(bg2);
        resetColor(bg3);
        resetColor(bg4);
        resetColor(bg5);
        resetColor(bg6);
        resetColor(bg7);
    }//GEN-LAST:event_bg1MouseClicked

    private void bg2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg2MouseClicked
        tabbed.setSelectedIndex(2);
        resetColor(bg0);
        resetColor(bg1);
        setColor(bg2);
        resetColor(bg3);
        resetColor(bg4);
        resetColor(bg5);
        resetColor(bg6);
        resetColor(bg7);
    }//GEN-LAST:event_bg2MouseClicked

    private void bg3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg3MouseClicked
        tabbed.setSelectedIndex(3);
        resetColor(bg0);
        resetColor(bg1);
        resetColor(bg2);
        setColor(bg3);
        resetColor(bg4);
        resetColor(bg5);
        resetColor(bg6);
        resetColor(bg7);
    }//GEN-LAST:event_bg3MouseClicked

    private void bg4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg4MouseClicked
        tabbed.setSelectedIndex(4);
        resetColor(bg0);
        resetColor(bg1);
        resetColor(bg2);
        resetColor(bg3);
        setColor(bg4);
        resetColor(bg5);
        resetColor(bg6);
        resetColor(bg7);
    }//GEN-LAST:event_bg4MouseClicked

    private void bg5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg5MouseClicked
        tabbed.setSelectedIndex(5);
        resetColor(bg0);
        resetColor(bg1);
        resetColor(bg2);
        resetColor(bg3);
        resetColor(bg4);
        setColor(bg5);
        resetColor(bg6);
        resetColor(bg7);
    }//GEN-LAST:event_bg5MouseClicked

    private void bg6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg6MouseClicked
        tabbed.setSelectedIndex(6);
        resetColor(bg0);
        resetColor(bg1);
        resetColor(bg2);
        resetColor(bg3);
        resetColor(bg4);
        resetColor(bg5);
        setColor(bg6);
        resetColor(bg7);
    }//GEN-LAST:event_bg6MouseClicked

    private void bg7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg7MouseClicked
        tabbed.setSelectedIndex(7);
        resetColor(bg0);
        resetColor(bg1);
        resetColor(bg2);
        resetColor(bg3);
        resetColor(bg4);
        resetColor(bg5);
        resetColor(bg6);
        setColor(bg7);
    }//GEN-LAST:event_bg7MouseClicked

    private void bg0MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg0MousePressed
        tabbed.setSelectedIndex(0);
        setColor(bg0);
        resetColor(bg1);
        resetColor(bg2);
        resetColor(bg3);
        resetColor(bg4);
        resetColor(bg5);
        resetColor(bg6);
        resetColor(bg7);
    }//GEN-LAST:event_bg0MousePressed

    private void bg1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg1MousePressed
        tabbed.setSelectedIndex(1);
        resetColor(bg0);
        setColor(bg1);
        resetColor(bg2);
        resetColor(bg3);
        resetColor(bg4);
        resetColor(bg5);
        resetColor(bg6);
        resetColor(bg7);
    }//GEN-LAST:event_bg1MousePressed

    private void bg2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg2MousePressed
        tabbed.setSelectedIndex(2);
        resetColor(bg0);
        resetColor(bg1);
        setColor(bg2);
        resetColor(bg3);
        resetColor(bg4);
        resetColor(bg5);
        resetColor(bg6);
        resetColor(bg7);
    }//GEN-LAST:event_bg2MousePressed

    private void bg3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg3MousePressed
        tabbed.setSelectedIndex(3);
        resetColor(bg0);
        resetColor(bg1);
        resetColor(bg2);
        setColor(bg3);
        resetColor(bg4);
        resetColor(bg5);
        resetColor(bg6);
        resetColor(bg7);
    }//GEN-LAST:event_bg3MousePressed

    private void bg4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg4MousePressed
        tabbed.setSelectedIndex(4);
        resetColor(bg0);
        resetColor(bg1);
        resetColor(bg2);
        resetColor(bg3);
        setColor(bg4);
        resetColor(bg5);
        resetColor(bg6);
        resetColor(bg7);
    }//GEN-LAST:event_bg4MousePressed

    private void bg5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg5MousePressed
        tabbed.setSelectedIndex(5);
        resetColor(bg0);
        resetColor(bg1);
        resetColor(bg2);
        resetColor(bg3);
        resetColor(bg4);
        setColor(bg5);
        resetColor(bg6);
        resetColor(bg7);
    }//GEN-LAST:event_bg5MousePressed

    private void bg6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg6MousePressed
        tabbed.setSelectedIndex(6);
        resetColor(bg0);
        resetColor(bg1);
        resetColor(bg2);
        resetColor(bg3);
        resetColor(bg4);
        resetColor(bg5);
        setColor(bg6);
        resetColor(bg7);
    }//GEN-LAST:event_bg6MousePressed

    private void bg7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg7MousePressed
        tabbed.setSelectedIndex(7);
        resetColor(bg0);
        resetColor(bg1);
        resetColor(bg2);
        resetColor(bg3);
        resetColor(bg4);
        resetColor(bg5);
        resetColor(bg6);
        setColor(bg7);
    }//GEN-LAST:event_bg7MousePressed

    private void jPanel8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel8FocusGained
        initCreatePermission();
    }//GEN-LAST:event_jPanel8FocusGained

    private void btnCreate_createPermissionM4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreate_createPermissionM4ActionPerformed
        ObjectDAO objectDAO = new ObjectDAO();
        ActionDAO actionDAO = new ActionDAO();
        ObjectModel obj = objectDAO.getByName((String) cbObject_createPermissionM4.getSelectedItem());
        Action action = actionDAO.getByName((String) cbAction_createPermissionM4.getSelectedItem());

        Permission permission = new Permission();
        permission.setActionID(action.getActionID());
        permission.setObjectID(obj.getObjectID());
        PermissionDAO permissionDAO = new PermissionDAO();
        int status = permissionDAO.insert(permission);
        permission.setPermissionID(permissionDAO.getCurrentID());
        if (status == 1) {
            loadPermission();
            JOptionPane.showMessageDialog(this,
                "Permission has been successfully added.",
                "Permission creation result",
                JOptionPane.INFORMATION_MESSAGE);
            initRolesAndPermissionsTab();
        } else {
            JOptionPane.showMessageDialog(this,
                "Permission has not been added.",
                "Permission creation result",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnCreate_createPermissionM4ActionPerformed

    private void btnCreate_createPermissionM3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreate_createPermissionM3ActionPerformed
        ObjectDAO objectDAO = new ObjectDAO();
        ActionSetDAO setDAO = new ActionSetDAO();
        ActionDAO actionDAO = new ActionDAO();
        ObjectModel obj = objectDAO.getByName((String) cbObject_createPermissionM3.getSelectedItem());
        ArrayList<String> actionName = setDAO.getAllActionNameInSet((String) cbSet_createPermissionM3.getSelectedItem());
        int status = 0;
        for (String action : actionName) {
            Permission permission = new Permission();
            permission.setActionID(actionDAO.getByName(action).getActionID());
            permission.setObjectID(obj.getObjectID());
            PermissionDAO permissionDAO = new PermissionDAO();
            status = permissionDAO.insert(permission);
            permission.setPermissionID(permissionDAO.getCurrentID());
        }
        if (status == 1) {
            loadPermission();
            JOptionPane.showMessageDialog(this,
                "Permission has been successfully added.",
                "Permission creation result",
                JOptionPane.INFORMATION_MESSAGE);
            initRolesAndPermissionsTab();
        } else {
            JOptionPane.showMessageDialog(this,
                "Permission has not been added.",
                "Permission creation result",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnCreate_createPermissionM3ActionPerformed

    private void btnCreate_createPermissionM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreate_createPermissionM2ActionPerformed
        ObjectDAO objectDAO = new ObjectDAO();
        ActionDAO actionDAO = new ActionDAO();
        ContainerDAO containerDAO = new ContainerDAO();
        Action action = actionDAO.getByName((String)cbAction_createPermissionM2.getSelectedItem());
        ArrayList<String> objName = containerDAO.getAllObjectNameInContainer((String) cbContainer_createPermissionM2.getSelectedItem());
        int status = 0;
        for (String obj : objName) {
            Permission permission = new Permission();
            permission.setActionID(action.getActionID());
            permission.setObjectID(objectDAO.getByName(obj).getObjectID());
            PermissionDAO permissionDAO = new PermissionDAO();
            status = permissionDAO.insert(permission);
            permission.setPermissionID(permissionDAO.getCurrentID());
        }
        if (status == 1) {
            loadPermission();
            JOptionPane.showMessageDialog(this,
                "Permission has been successfully added.",
                "Permission creation result",
                JOptionPane.INFORMATION_MESSAGE);
            initRolesAndPermissionsTab();
        } else {
            JOptionPane.showMessageDialog(this,
                "Permission has not been added.",
                "Permission creation result",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnCreate_createPermissionM2ActionPerformed

    private void btnCreate_createPermissionM1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreate_createPermissionM1ActionPerformed
        ContainerDAO containerDAO = new ContainerDAO();
        ActionSetDAO setDAO = new ActionSetDAO();
        ObjectDAO objectDAO = new ObjectDAO();
        ActionDAO actionDAO = new ActionDAO();

        ArrayList<String> containerName = containerDAO.getAllObjectNameInContainer((String)cbContainer_createPermissionM1.getSelectedItem());
        ArrayList<String> setName = setDAO.getAllActionNameInSet((String)cbSet_createPermissionM1.getSelectedItem());
        int status = 0;
        for (String actionName : setName) {
            for (String objectName : containerName) {
                Permission permission = new Permission();
                permission.setActionID(actionDAO.getByName(actionName).getActionID());
                permission.setObjectID(objectDAO.getByName(objectName).getObjectID());
                PermissionDAO permissionDAO = new PermissionDAO();
                status = permissionDAO.insert(permission);
                permission.setPermissionID(permissionDAO.getCurrentID());
            }
        }
        if (status == 1) {
            loadPermission();
            JOptionPane.showMessageDialog(this,
                "Permission has been successfully added.",
                "Permission creation result",
                JOptionPane.INFORMATION_MESSAGE);
            initRolesAndPermissionsTab();
        } else {
            JOptionPane.showMessageDialog(this,
                "Permission has not been added.",
                "Permission creation result",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnCreate_createPermissionM1ActionPerformed

    private void jPanel7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel7FocusGained
        initActionSet();
    }//GEN-LAST:event_jPanel7FocusGained

    private void btnList_createActionSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnList_createActionSetActionPerformed
        ActionSetDAO setDAO = new ActionSetDAO();
        ArrayList<String> listAction = setDAO.getAllActionNameInSet((String) cbAllActionSet_createActionSet.getSelectedItem());
        System.out.println("selected: " + (String) cbAllActionSet_createActionSet.getSelectedItem());
        DefaultListModel dlm_createActionSet = new DefaultListModel();
        for (String actionName : listAction) {
            //            System.out.println("in " +(String)cbAllContainer_createContainer.getSelectedItem() + ":" + objName );
            dlm_createActionSet.addElement(actionName);
        }
        listShow_createActionSet.setModel(dlm_createActionSet);
    }//GEN-LAST:event_btnList_createActionSetActionPerformed

    private void chkShowList_createActionSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowList_createActionSetActionPerformed
        if (chkShowList_createActionSet.isSelected()) {
            cbAllActionSet_createActionSet.setVisible(true);
            listShow_createActionSet.setVisible(true);
            btnList_createActionSet.setVisible(true);

            // reset data
            listShow_createActionSet.setModel(new DefaultListModel());
            cbAllActionSet_createActionSet.removeAllItems();
            loadActionSetShowList();
        } else {
            cbAllActionSet_createActionSet.setVisible(false);
            listShow_createActionSet.setVisible(false);
            btnList_createActionSet.setVisible(false);
        }
    }//GEN-LAST:event_chkShowList_createActionSetActionPerformed

    private void btnCreate_createContainer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreate_createContainer1ActionPerformed
        if (isValidString(txtActionSetName_createActionSet.getText())) {
            ArrayList<Action> selectedActions = new ArrayList<>();
            ActionDAO actionDAO = new ActionDAO();
            for (int i = 0; i < dtm2.getRowCount(); i++) {
                if ((boolean) dtm2.getValueAt(i, 1) == true) {
                    selectedActions.add(actionDAO.getByName((String) dtm2.getValueAt(i, 0)));
                }
            }

            if (selectedActions.size() == 0) {
                JOptionPane.showMessageDialog(this,
                    "You need to add at least one object to this set.",
                    "Action Set Information",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                ActionSetDAO setDAO = new ActionSetDAO();
                ActionSet set = new ActionSet();

                set.setActionSetName(txtActionSetName_createActionSet.getText());
                set.setActions(selectedActions);

                int status = setDAO.insert(set);
                set.setActionSetID(setDAO.getCurrentID());

                if (status == 1) {
                    //                    initCreateActionTab();
                    JOptionPane.showMessageDialog(this,
                        "Set has been successfully added.",
                        "Set creation result",
                        JOptionPane.INFORMATION_MESSAGE);
                    initCreatePermission();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Set has not been added.",
                        "Set creation result",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "You need to validate all fields.",
                "Set fiels validation",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnCreate_createContainer1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg0;
    private javax.swing.JPanel bg1;
    private javax.swing.JPanel bg2;
    private javax.swing.JPanel bg3;
    private javax.swing.JPanel bg4;
    private javax.swing.JPanel bg5;
    private javax.swing.JPanel bg6;
    private javax.swing.JPanel bg7;
    private javax.swing.JButton btnCreate__createUser;
    private javax.swing.JButton btnCreate_createAction;
    private javax.swing.JButton btnCreate_createContainer;
    private javax.swing.JButton btnCreate_createContainer1;
    private javax.swing.JButton btnCreate_createObject;
    private javax.swing.JButton btnCreate_createPermissionM1;
    private javax.swing.JButton btnCreate_createPermissionM2;
    private javax.swing.JButton btnCreate_createPermissionM3;
    private javax.swing.JButton btnCreate_createPermissionM4;
    private javax.swing.JButton btnCreate_createRole;
    private javax.swing.JButton btnList_createActionSet;
    private javax.swing.JButton btnList_createContainer;
    private javax.swing.JButton btnRefresh__createUser;
    private javax.swing.JComboBox<String> cbAction_createPermissionM2;
    private javax.swing.JComboBox<String> cbAction_createPermissionM4;
    private javax.swing.JComboBox<String> cbAllActionSet_createActionSet;
    private javax.swing.JComboBox<String> cbAllContainer_createContainer;
    private javax.swing.JComboBox<String> cbConflictedAction_createAction;
    private javax.swing.JComboBox<String> cbContainer_createPermissionM1;
    private javax.swing.JComboBox<String> cbContainer_createPermissionM2;
    private javax.swing.JComboBox<String> cbFromDay__createUser;
    private javax.swing.JComboBox<String> cbFromDay_createObject;
    private javax.swing.JComboBox<String> cbFromDay_createRole;
    private javax.swing.JComboBox<String> cbObject_createPermissionM3;
    private javax.swing.JComboBox<String> cbObject_createPermissionM4;
    private javax.swing.JComboBox<String> cbPermission_rolesAndPermissions;
    private javax.swing.JComboBox<String> cbRoleName_rolesAndPermissions;
    private javax.swing.JComboBox<String> cbSet_createPermissionM1;
    private javax.swing.JComboBox<String> cbSet_createPermissionM3;
    private javax.swing.JComboBox<String> cbToDay_createObject;
    private javax.swing.JComboBox<String> cbToDay_createRole;
    private javax.swing.JComboBox<String> cbToDay_createUser;
    private javax.swing.JCheckBox chkShowList_createActionSet;
    private javax.swing.JCheckBox chkShowList_createContainer;
    private javax.swing.JSeparator fadsfsd;
    private javax.swing.JSeparator fadsfsd1;
    private javax.swing.JSeparator fadsfsd2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel48ff;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel49ff;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList<String> listShow_createActionSet;
    private javax.swing.JList<String> listShow_createContainer;
    private javax.swing.JSpinner spinnerFromTime__createUser;
    private javax.swing.JSpinner spinnerFromTime_createObject;
    private javax.swing.JSpinner spinnerFromTime_createRole;
    private javax.swing.JSpinner spinnerToTime_createObject;
    private javax.swing.JSpinner spinnerToTime_createRole;
    private javax.swing.JSpinner spinnerToTime_createUser;
    private javax.swing.JTabbedPane tabbed;
    private javax.swing.JTable tableActionSetCreate_createActionSet;
    private javax.swing.JTable tableContainerCreate_createContainer;
    private javax.swing.JTable tablePermissionShow_createPermission;
    private javax.swing.JTextField txtActionName_createAction;
    private javax.swing.JTextField txtActionSetName_createActionSet;
    private javax.swing.JTextField txtContainerName_createContainer;
    private javax.swing.JTextField txtIpAddress__createUser;
    private javax.swing.JTextField txtIpAddress_createObject;
    private javax.swing.JTextField txtIpAddress_createRole;
    private javax.swing.JTextField txtObjName_createObject;
    private javax.swing.JPasswordField txtPassword__createUser;
    private javax.swing.JTextField txtRoleName_createRole;
    private javax.swing.JTextField txtUsername_createUser;
    // End of variables declaration//GEN-END:variables

    private boolean isValidString(String str) {
        String pattern = "^(?=.{4,100}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        if (!m.find()) {
            System.out.println("Wrong format!");
            return false;
        }
        return true;
    }

    private boolean isValidIpAddress(String str) {
        String pattern = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        if (!m.find()) {
            System.out.println("Wrong IP format!");
            return false;
        }
        return true;
    }

    private String toDateString(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat("hh:mm:ss a");
        return formater.format(date);
    }

    private void loadContainerShowList() {
        ContainerDAO containerDB = new ContainerDAO();
        ArrayList<ContainerModel> containers = containerDB.all();
        for (ContainerModel container : containers) {
            cbAllContainer_createContainer.addItem(container.getContainerName());
        }
    }

    private void loadActionSetShowList() {
        ActionSetDAO setDB = new ActionSetDAO();
        ArrayList<ActionSet> sets = setDB.all();
        for (ActionSet set : sets) {
            cbAllActionSet_createActionSet.addItem(set.getActionSetName());
        }
    }
}
