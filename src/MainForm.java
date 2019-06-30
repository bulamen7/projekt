import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainForm {
    private JTextField filterTextField;
    private JButton filterButton;
    private JTable table;
    private JPanel rootPanel;
    private JLabel imageLabel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private BookDb db;
    private List<Book> books;

    public MainForm() throws IOException {
        db = JsonBookDb.fromFile("media/books.json");
        books = db.getBooks("");
        table.setModel(new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return books.size();
            }

            @Override
            public int getColumnCount() {
                return 8;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Book book = books.get(rowIndex);
                switch (columnIndex) {
                    case 0:
                        return book.getAuthor();
                    case 1:
                        return book.getCountry();
                    case 2:
                        return book.getImageLink();
                    case 3:
                        return book.getLanguage();
                    case 4:
                        return book.getLink();
                    case 5:
                        return book.getPages();
                    case 6:
                        return book.getTitle();
                    case 7:
                        return book.getYear();
                }
                return null;
            }

            @Override
            public String getColumnName(int column) {
                switch (column) {
                    case 0:
                        return "Author";
                    case 1:
                        return "Country";
                    case 2:
                        return "ImageLink";
                    case 3:
                        return "Language";
                    case 4:
                        return "Link";
                    case 5:
                        return "Pages";
                    case 6:
                        return "Title";
                    case 7:
                        return "Year";
                }
                return null;
            }
        });
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                books = db.getBooks(filterTextField.getText());
                table.repaint();
            }
        });
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = e.getFirstIndex();
                if (index != -1) {
                    String path = "media/" + books.get(index).getImageLink();
                    try {
                        BufferedImage image = ImageIO.read(new File(path));
                        imageLabel.setIcon(new ImageIcon(image));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ;
                }
            }
        });


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InsertUpdateDialog insertUpdateDialog = new InsertUpdateDialog();
                insertUpdateDialog.present();

                if (insertUpdateDialog.isOkClick()) {
                    db.add(insertUpdateDialog.getBook());
                    refreshData();
                }
            }
        });


        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book book = getSelectedBook();
                if (book != null) {
                    InsertUpdateDialog insertUpdateDialog = new InsertUpdateDialog(book);
                    insertUpdateDialog.present();
                    if (insertUpdateDialog.isOkClick()) {
                        refreshData();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "select row");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book book = getSelectedBook();
                if (book != null) {
                    db.delete(book);
                    refreshData();
                } else {
                    JOptionPane.showMessageDialog(null, "select row");
                }
            }
        });
    }

    private Book getSelectedBook() {
        int index = table.getSelectedRow();
        if (index != -1) {
            return books.get(index);
        }
        return null;
    }

    private void refreshData() {
        books = db.getBooks("");
        table.repaint();
    }

    public void present() {
        JFrame frame = new JFrame("Library");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().add(rootPanel);
        frame.pack();
        frame.setVisible(true);

    }
}
