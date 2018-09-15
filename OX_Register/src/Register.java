import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.*;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class Register {
    private JTextField txtName;
    private JTextField txtUserName;
    private JPasswordField pwdPassword;
    private JPasswordField pwdConPassword;
    private JButton btnOk;
    private JButton btnCancel;
    private JLabel lblName;
    private JLabel lblUserName;
    private JLabel lblPassword;
    private JLabel lblConPassword;
    private JPanel mainPanel;
    MongoCollection<Document> user;
    MongoClientURI uri;
    MongoClient client;
    MongoDatabase db;
    Document checkData;
    List<Document> insertData;
    private boolean checkUser;
    private boolean checkPass;
    private boolean isNull;

    public Register() {

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkNull();
                checkUsername();
                checkPassword();
                if (isNull==true) {
                    JLabel mesage = new JLabel("กรอกข้อมูลให้ครบ");
                    mesage.setFont(new Font("Angsana New", Font.BOLD, 20));
                    JOptionPane.showMessageDialog(null, mesage, "Error", JOptionPane.ERROR_MESSAGE);
                }  else if (checkPass==false) {
                    JLabel mesage = new JLabel("Password ไม่ถูกต้อง");
                    mesage.setFont(new Font("Angsana New", Font.BOLD, 20));
                    JOptionPane.showMessageDialog(null, mesage, "Error", JOptionPane.ERROR_MESSAGE);
                    setPass();
                } else if (checkUser==false) {
                    JLabel mesage = new JLabel("Username นี้มีผู้ใช้แล้ว");
                    mesage.setFont(new Font("Angsana New", Font.BOLD, 20));
                    JOptionPane.showMessageDialog(null, mesage, "Error", JOptionPane.ERROR_MESSAGE);
                    setUser();
                }else {
                    submitData();
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public JPanel getMainPanel(){
        return mainPanel;
   }

   public void checkUsername() {
       uri  = new MongoClientURI("mongodb://Pongsiri:oxgame61@ds257372.mlab.com:57372/oxgame");
       client = new MongoClient(uri);
       db = client.getDatabase("oxgame");
       user = db.getCollection("user");

       checkData = user.find(eq("username", txtUserName.getText())).first();

       if (checkData == null) {
           checkUser = true;
       } else {
           checkUser = false;
       }
   }

   public void checkPassword() {
        if (new String(pwdPassword.getPassword()).equals(new String(pwdConPassword.getPassword()))) {
            checkPass = true;
        } else {
            checkPass = false;
        }
   }

   public void checkNull() {
        if (txtName.getText().isEmpty()||txtUserName.getText().isEmpty()||
                new String(pwdPassword.getPassword()).equals("")||new String(pwdConPassword.getPassword()).equals("")) {

            isNull = true;
        } else {
            isNull = false;
        }
   }

   public void submitData() {
        insertData = new ArrayList<Document>();
        insertData.add(new Document("name",txtName.getText()).append("username",txtUserName.getText()).append("password",new String(pwdPassword.getPassword())));
        user.insertMany(insertData);

        JLabel mesage = new JLabel("การสมัครสมาชิกสำเร็จ");
        mesage.setFont(new Font("Angsana New", Font.BOLD, 20));
        JOptionPane.showMessageDialog(null, mesage);

        client.close();
        System.exit(0);
   }

   public void setNull() {
        txtUserName.setText("");
        txtName.setText("");
        pwdConPassword.setText("");
        pwdPassword.setText("");
   }

   public void setUser() {
        txtUserName.setText("");
   }

   public void setPass() {
        pwdPassword.setText("");
        pwdConPassword.setText("");
   }
}