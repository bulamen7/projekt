import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

public class InsertUpdateDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField authorTextField;
    private JTextField countrytextField;
    private JTextField imageLinktextField;
    private JTextField languagetextField;
    private JTextField linktextField;
    private JTextField pagestextField;
    private JTextField titletextField;
    private JTextField yeartextField;
    private boolean isOkClick;
    private Book book;


    public InsertUpdateDialog() {
        initialize();
    }

    public InsertUpdateDialog(Book book) {
        this.book = book;
        initialize();
        authorTextField.setText(book.getAuthor());
        countrytextField.setText(book.getCountry());
        imageLinktextField.setText(book.getImageLink());
        languagetextField.setText(book.getLanguage());
        linktextField.setText(book.getLink());
        pagestextField.setText(book.getPages() + "");
        titletextField.setText(book.getTitle());
        yeartextField.setText(book.getYear() + "");
    }

    private void initialize() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        try {
            if (this.book != null) {
                fillBook(this.book);
            }
            isOkClick = true;
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void onCancel() {
        isOkClick = false;
        dispose();
    }

    public boolean isOkClick() {
        return isOkClick;
    }

    public void present() {
        this.pack();
        this.setVisible(true);
    }

    public Book getBook() {
        Book book = new Book();
        fillBook(book);
        return book;
    }

    private void fillBook(Book book) {
        book.setAuthor(authorTextField.getText());
        book.setCountry(countrytextField.getText());
        book.setImageLink(imageLinktextField.getText());
        book.setLanguage(languagetextField.getText());
        book.setLink(linktextField.getText());
        book.setPages(Integer.parseInt(pagestextField.getText()));
        book.setTitle(titletextField.getText());
        book.setYear(Integer.parseInt(yeartextField.getText()));
    }
}
