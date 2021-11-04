package kyh.textgame;

import javax.swing.*;

public class ShowInput {
    public static String text;

    public void welcomeText() {
        String[] options = {"OK"};
        JPanel panel = new JPanel();
        JLabel lbl = new JLabel("Enter Your name: ");
        JTextField txt = new JTextField(10);
        panel.add(lbl);
        panel.add(txt);
        int selectedOption = JOptionPane.showOptionDialog(null, panel, "Welcome to Escape the mansion", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (selectedOption == 0) {
            text = txt.getText();
        }
    }

    public void gameEndingText() {
        JOptionPane.showMessageDialog(null,"Good choice " + text + "! You are now free to roam the seven seas again. Thanks for playing!");
    }
}
