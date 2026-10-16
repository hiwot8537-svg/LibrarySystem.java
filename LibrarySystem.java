
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LibrarySystem extends JFrame {

    private JTextField idField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField searchField;
    private JTable table;
    private DefaultTableModel model;

    public LibrarySystem() {

        setTitle("Library Management System");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JLabel heading = new JLabel(
            "LIBRARY MANAGEMENT SYSTEM",
            SwingConstants.CENTER
        );
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(heading, BorderLayout.NORTH);

        idField = new JTextField();
        titleField = new JTextField();
        authorField = new JTextField();
        searchField = new JTextField();

        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.add(new JLabel("Book ID:"));
        form.add(idField);
        form.add(new JLabel("Book Title:"));
        form.add(titleField);
        form.add(new JLabel("Author:"));
        form.add(authorField);

        String[] columns = {"Book ID", "Title", "Author"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JPanel buttons = new JPanel();

        JButton addButton = new JButton("Add Book");
        JButton deleteButton = new JButton("Delete Book");
        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");
        JButton clearButton = new JButton("Clear");

        buttons.add(addButton);
        buttons.add(deleteButton);
        buttons.add(searchButton);
        buttons.add(showAllButton);
        buttons.add(clearButton);

        JPanel searchPanel = new JPanel(new BorderLayout(8, 8));
        searchPanel.add(new JLabel("Search ID or Title:"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(form, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(buttons, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(table), BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();

            if (id.isEmpty() || title.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this, "Please fill in all fields."
                );
                return;
            }

            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).toString().equalsIgnoreCase(id)) {
                    JOptionPane.showMessageDialog(
                        this, "This Book ID already exists."
                    );
                    return;
                }
            }

            model.addRow(new Object[]{id, title, author});
            clearFields();
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row >= 0) {
                model.removeRow(table.convertRowIndexToModel(row));
            } else {
                JOptionPane.showMessageDialog(
                    this, "Select a book from the table first."
                );
            }
        });

        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            boolean found = false;

            for (int i = 0; i < model.getRowCount(); i++) {
                String id = model.getValueAt(i, 0).toString();
                String title = model.getValueAt(i, 1).toString();

                if (id.equalsIgnoreCase(query)
                        || title.toLowerCase().contains(query.toLowerCase())) {
                    table.setRowSelectionInterval(i, i);
                    table.scrollRectToVisible(table.getCellRect(i, 0, true));
                    found = true;
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Book not found.");
            }
        });

        showAllButton.addActionListener(e -> {
            table.clearSelection();
            searchField.setText("");
        });

        clearButton.addActionListener(e -> clearFields());

        setContentPane(mainPanel);
        setVisible(true);
    }

    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        authorField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarySystem());
    }
}
