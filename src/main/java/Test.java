import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test {

    private JButton okButton;
    private JPanel rootPanel;


    public Test() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.toString());
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

}
