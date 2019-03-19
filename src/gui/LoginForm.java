package gui;

import dal.UserDAO;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
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
public class LoginForm extends javax.swing.JFrame {

    /**
     * Creates new form LoginForm
     */
    public LoginForm() {
        initComponents();
        this.setTitle("Login Form");
//        String time1 = "11:59:00 AM";
//        String time2 = "12:01:00 PM";
//
//        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a");
//        Date date1 = null;
//        try {
//            date1 = format.parse(time1);
//        } catch (ParseException ex) {
//            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Date date2 = null;
//        try {
//            date2 = format.parse(time2);
//        } catch (ParseException ex) {
//            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        long difference = date2.getTime() - date1.getTime();
//        System.out.println(difference / 1000);
//        System.out.println("---- " + isSooner2("05:02:04 AM", "01:02:03 PM"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        cbUserType = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 0));
        setForeground(new java.awt.Color(204, 153, 0));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(54, 33, 89));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("<html> Permission Based SOD in Dynamic RBAC <br/></html");

        txtUsername.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        txtUsername.setText("Admin");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("Username");

        txtPassword.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtPassword.setText("admin");
        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordFocusGained(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Password");

        cbUserType.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        cbUserType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "User" }));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("User Type");

        btnLogin.setBackground(new java.awt.Color(54, 33, 89));
        btnLogin.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");
        btnLogin.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnExit.setBackground(new java.awt.Color(54, 33, 89));
        btnExit.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("Exit");
        btnExit.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(64, 58, 74));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText(" Login Page");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel5)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnLogin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExit))
                            .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbUserType, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 60, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbUserType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnExit))
                .addContainerGap(44, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.getAccessibleContext().setAccessibleParent(null);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // get the system ip
        String ip = null;
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
            System.out.println(ip);
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (((String) cbUserType.getSelectedItem()).equals("Admin")) {
            if (IsLoginToAdmin(txtUsername.getText(), txtPassword.getText(), ip)) {
                AdminForm adForm = new AdminForm(this);
                this.setVisible(false);
                adForm.setVisible(true);
            }
        } else {
            if (IsLoginToUser(txtUsername.getText(), txtPassword.getText(), ip)) {
                UserDAO userDAO = new UserDAO();
                UserForm userForm = new UserForm(this, userDAO.getByUsername(txtUsername.getText()));
                this.setVisible(false);
                userForm.setVisible(true);
            }
        }

    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusGained
        txtPassword.setText("");
    }//GEN-LAST:event_txtPasswordFocusGained

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
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLogin;
    private javax.swing.JComboBox<String> cbUserType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

    private boolean IsLoginToAdmin(String username, String password, String ip) {
        if (!username.equals("Admin")) {
            JOptionPane.showConfirmDialog(this, "Wrong user name",
                    "Login failure", JOptionPane.CLOSED_OPTION);
            return false;
        }
        UserDAO userDAO = new UserDAO();
        User tmp = userDAO.getByUsername(username);
        if (tmp == null) {
            JOptionPane.showConfirmDialog(this, "Wrong user name or password",
                    "Login failure", JOptionPane.CLOSED_OPTION);
            return false;
        }
        if (!tmp.getPassword().equals(password) || !tmp.getIpAddress().equals(ip)) {

            JOptionPane.showConfirmDialog(this, "Wrong password or IP",
                    "Login failure", JOptionPane.CLOSED_OPTION);
            return false;
        }

        if (!isRightTime(tmp.getFromDay(), tmp.getToDay(), tmp.getFromTime(), tmp.getToTime())) {

            JOptionPane.showConfirmDialog(this, "Wrong time login",
                    "Login failure", JOptionPane.CLOSED_OPTION);
            return false;
        }

        return true;
    }

    private boolean isValidString(String str) {
        String pattern = "^(?=.{5,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        if (!m.find()) {
            System.out.println("Wrong format!");
            return false;
        }
        return true;
    }

    private boolean IsLoginToUser(String username, String password, String ip) {
        if (username.equals("Admin")) {
            JOptionPane.showMessageDialog(this,
                    "Users can't login with account Admin",
                    "Login failed",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        UserDAO userDAO = new UserDAO();
        User tmp = userDAO.getByUsername(username);
        if (tmp == null) {
            JOptionPane.showMessageDialog(this,
                    "Wrong username or password",
                    "Login failed",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (!tmp.getPassword().equals(password) || !tmp.getIpAddress().equals(ip)) {
            JOptionPane.showMessageDialog(this,
                    "Wrong username or password or IpAddress not match",
                    "Login failed",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        if (!isRightTime(tmp.getFromDay(), tmp.getToDay(), tmp.getFromTime(), tmp.getToTime())) {
            JOptionPane.showMessageDialog(this,
                    "Not match login time",
                    "Login failed",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean isRightTime(String fromDay, String toDay, String fromTime, String toTime) {
        ArrayList<String> weekdays = new ArrayList<>();
        weekdays.add("MONDAY");
        weekdays.add("TUESDAY");
        weekdays.add("WEDNESDAY");
        weekdays.add("THURSDAY");
        weekdays.add("FRIDAY");
        weekdays.add("SATURDAY");
        weekdays.add("SUNDAY");

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String currentDay = null;
        switch (day) {
            case Calendar.SUNDAY:
                currentDay = "SUNDAY";
                break;
            case Calendar.MONDAY:
                currentDay = "MONDAY";
                break;
            case Calendar.TUESDAY:
                currentDay = "TUESDAY";
                break;
            case Calendar.WEDNESDAY:
                currentDay = "WEDNESDAY";
                break;
            case Calendar.THURSDAY:
                currentDay = "THURSDAY";
                break;
            case Calendar.FRIDAY:
                currentDay = "FRIDAY";
                break;
            case Calendar.SATURDAY:
                currentDay = "SATURDAY";
                break;
        }

        int indFromDay = weekdays.indexOf(fromDay);
        int indToDay = weekdays.indexOf(toDay);
        int indCurrentDay = weekdays.indexOf(currentDay);
        if (indFromDay > indToDay) {
            if (indFromDay <= indCurrentDay && indCurrentDay <= indToDay) {
                return false;
            }
        } else {
            if (indCurrentDay > indToDay || indCurrentDay < indFromDay) {
                return false;
            }
        }

        LocalDateTime ldt = LocalDateTime.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String curTime = formatter.format(ldt);

        return isBetween(fromTime, curTime, toTime);
    }

    public boolean isSooner(String p1, String p2) {
        System.out.println("p1: " + p1 + " p2: " + p2);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(p1);
            date2 = sdf.parse(p2);
        } catch (ParseException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (date1 == null || date2 == null) {
            return false;
        }

        long difference = date2.getTime() - date1.getTime();
        System.out.println("date2:" + date2.getTime() + " date1: " + date1.getTime() + " difference: " + difference);
        return difference >= 0;
    }

    public boolean isBetween(String range1, String time, String range2) {
        try {
            Date time1 = new SimpleDateFormat("HH:mm:ss a").parse(range1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            Date time2 = new SimpleDateFormat("HH:mm:ss a").parse(range2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            if (calendar1.after(calendar2)) {
                calendar2.add(Calendar.DATE, 1);
            }

            Date d = new SimpleDateFormat("HH:mm:ss a").parse(time);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            if (calendar1.after(calendar3))
                calendar3.add(Calendar.DATE, 1);
            
            Date current_time = calendar3.getTime();
            
            return current_time.after(calendar1.getTime()) && current_time.before(calendar2.getTime());
        } catch (ParseException e) {
        }
        return false;
    }
}
