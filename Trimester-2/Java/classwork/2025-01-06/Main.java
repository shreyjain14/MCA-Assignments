import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Feedback {
    private String userName;
    private String movieName;
    private int rating;
    private String comments;

    public Feedback(String userName, String movieName, int rating, String comments) {
        this.userName = userName;
        this.movieName = movieName;
        this.rating = rating;
        this.comments = comments;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "User: " + userName + ", Movie: " + movieName + ", Rating: " + rating + ", Comments: " + comments;
    }
}

public class Main {
    private static List<Feedback> feedbackList = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Movie Feedback");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel userLabel = new JLabel("User Name:");
        JTextField userField = new JTextField();
        JLabel movieLabel = new JLabel("Movie Name:");
        JTextField movieField = new JTextField();
        JLabel ratingLabel = new JLabel("Rating (1-5):");
        JTextField ratingField = new JTextField();
        JLabel commentsLabel = new JLabel("Comments:");
        JTextField commentsField = new JTextField();

        JButton submitButton = new JButton("Submit");
        JButton averageButton = new JButton("Show Average Rating");

        panel.add(userLabel);
        panel.add(userField);
        panel.add(movieLabel);
        panel.add(movieField);
        panel.add(ratingLabel);
        panel.add(ratingField);
        panel.add(commentsLabel);
        panel.add(commentsField);
        panel.add(submitButton);
        panel.add(averageButton);

        frame.add(panel);
        frame.setVisible(true);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userField.getText();
                String movieName = movieField.getText();
                int rating = Integer.parseInt(ratingField.getText());
                String comments = commentsField.getText();

                Feedback feedback = new Feedback(userName, movieName, rating, comments);
                feedbackList.add(feedback);

                JOptionPane.showMessageDialog(frame, "Feedback submitted!");
                userField.setText("");
                movieField.setText("");
                ratingField.setText("");
                commentsField.setText("");
            }
        });

        averageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double averageRating = feedbackList.stream().mapToInt(Feedback::getRating).average().orElse(0.0);
                JOptionPane.showMessageDialog(frame, "Average Rating: " + averageRating);
            }
        });
    }
}