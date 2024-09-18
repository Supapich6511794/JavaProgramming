import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.lang.String;
import java.sql.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class Project {
    private StringProperty ssn;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty mi;
    private StringProperty birthDate;
    private StringProperty street;
    private StringProperty phone;
    private StringProperty zipCode;
    private StringProperty deptId;
    //ตามteble
    public Project(String ssn, String firstName, String lastName, String mi, String birthDate, String street, String phone, String zipCode, String deptId) {
        setSSN(ssn);
        setFirstName(firstName);
        setLastName(lastName);
        setMi(mi);
        setBirthDate(birthDate);
        setStreet(street);
        setPhone(phone);
        setZipCode(zipCode);
        setDeptId(deptId);
    }
   //ให้user input input=ในตาราง
    public void setSSN(String value) { ssnProperty().set(value); }
    public String getSSN() { return ssnProperty().get(); }
    public StringProperty ssnProperty() {
        if (ssn == null) ssn = new SimpleStringProperty(this, "ssn");
        return ssn;
    }

    public void setFirstName(String value) { firstNameProperty().set(value); }
    public String getFirstName() { return firstNameProperty().get(); }
    public StringProperty firstNameProperty() {
        if (firstName == null) firstName = new SimpleStringProperty(this, "firstName");
        return firstName;
    }

    public void setLastName(String value) { lastNameProperty().set(value); }
    public String getLastName() { return lastNameProperty().get(); }
    public StringProperty lastNameProperty() {
        if (lastName == null) lastName = new SimpleStringProperty(this, "lastName");
        return lastName;
    }

    public void setMi(String value) { miProperty().set(value); }
    public String getMi() { return miProperty().get(); }
    public StringProperty miProperty() {
        if (mi == null) mi = new SimpleStringProperty(this, "mi");
        return mi;
    }

    public void setBirthDate(String value) { birthDateProperty().set(value); }
    public String getBirthDate() { return birthDateProperty().get(); }
    public StringProperty birthDateProperty() {
        if (birthDate == null) birthDate = new SimpleStringProperty(this, "birthDate");
        return birthDate;
    }

    public void setStreet(String value) { streetProperty().set(value); }
    public String getStreet() { return streetProperty().get(); }
    public StringProperty streetProperty() {
        if (street == null) street = new SimpleStringProperty(this, "street");
        return street;
    }

    public void setPhone(String value) { phoneProperty().set(value); }
    public String getPhone() { return phoneProperty().get(); }
    public StringProperty phoneProperty() {
        if (phone == null) phone = new SimpleStringProperty(this, "phone");
        return phone;
    }

    public void setZipCode(String value) { zipCodeProperty().set(value); }
    public String getZipCode() { return zipCodeProperty().get(); }
    public StringProperty zipCodeProperty() {
        if (zipCode == null) zipCode = new SimpleStringProperty(this, "zipCode");
        return zipCode;
    }

    public void setDeptId(String value) { deptIdProperty().set(value); }
    public String getDeptId() { return deptIdProperty().get(); }
    public StringProperty deptIdProperty() {
        if (deptId == null) deptId = new SimpleStringProperty(this, "deptId");
        return deptId;
    }
    // คลาส ใช้แสดงแผนภูมิ
    public static class Main extends Application {
        private Statement stmt;
        private final TextField tfSSN = new TextField();
        private final TextField tfFN = new TextField();
        private final TextField tfMI = new TextField();
        private final TextField tfLN = new TextField();
        private final TextField tfdID = new TextField();
        private final TextField tfST = new TextField();
        private final TextField tfTEL = new TextField();
        private final TextField tfZC = new TextField();
        private final TextField tfBD = new TextField();
        private final Label lblStatus = new Label();
        private final TableView<Project> list = new TableView<Project>();
        private final TextArea dbConnectionTextArea = new TextArea();

        @Override
        public void start(Stage primaryStage) {
            //เริ่มการทำงานของdatabase
            initializeDB();

            // Scene
            BorderPane borderPane = new BorderPane();
            BorderPane borderPane1 = new BorderPane();

            dbConnectionTextArea.setEditable(false);
            borderPane1.setTop(dbConnectionTextArea);
            borderPane1.setPrefSize(250,100);

            Pane pane = new HBox(10);
            pane.setPadding(new Insets(5, 20, 5, 5));
            Image image = new Image("File:image/Student.jpg");
            pane.getChildren().add(new ImageView(image));

            HBox hBox4 =new HBox();
            hBox4.getChildren().add(pane);
            hBox4.setAlignment(Pos.CENTER);
            HBox hBox3 =new HBox();
            hBox3.getChildren().addAll(borderPane1,borderPane);
            VBox vBox3 = new VBox();
            Label statusLabel = new Label("Status");
            statusLabel.setStyle("-fx-font-size: 30px;");

            vBox3.getChildren().addAll(hBox4,statusLabel, hBox3);
            Scene scene = new Scene(vBox3 ,1600, 900);
            // BarChart
            CategoryAxis xAxis = new CategoryAxis();  //CategoryAxis ใช้ในแผนภูมิ แกนx
            xAxis.setCategories(FXCollections.observableArrayList(Arrays.asList("BIOL", "CHEM", "CS", "MATH")));
            NumberAxis yAxis = new NumberAxis(); //แกนy
            yAxis.setLabel("Amount");
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Bar Chart");
            XYChart.Series<String, Number> barChartSeries = new XYChart.Series<>();
            barChartSeries.setName("deptId");
            barChart.setLegendVisible(false);
            barChart.setAnimated(false);
            barChart.getData().add(barChartSeries);

            Group root1 = new Group(barChart);
            // PieChart
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("BIOL", numBIOL),
                    new PieChart.Data("CHEM", numCHEM),
                    new PieChart.Data("CS", numCS),
                    new PieChart.Data("MATH", numMATH));
            PieChart pieChart = new PieChart(pieChartData);
            pieChart.setTitle("Pie Chart");
            pieChart.setClockwise(true);
            pieChart.setLabelLineLength(50);
            pieChart.setLabelsVisible(true);
            pieChart.setStartAngle(180);
            pieChart.setLegendVisible(false);
            pieChart.setAnimated(true);
            // Refresh At Init
            refreshData(pieChart, barChartSeries);

            Group root2 = new Group(pieChart);

            root1.setVisible(false);
            root2.setVisible(false);

            //Insert record and Refresh
            AtomicBoolean isProgramVisible = new AtomicBoolean(false);
            Button showBtn = new Button("Show");
            showBtn.setOnAction(event -> {
                if(showBtn.getText() == "Hide") {
                    showBtn.setText("Show");
                    root1.setVisible(false);
                    root2.setVisible(false);
                } else {
                    showBtn.setText("Hide");
                    root1.setVisible(true);
                    root2.setVisible(true);
                }
            });

            // Button Insert New Data to Chart
            Button insertButton = new Button("Insert new record");
            insertButton.setOnAction(e -> {
                insertData();
                refreshTableView();
                refreshData(pieChart, barChartSeries);
            });

            // Button Update New Data to Chart
            Button updateButton = new Button("Update record");
            updateButton.setOnAction(e -> {
                try {
                    updateData();
                } catch (SQLException ex) { }
                refreshTableView();
                refreshData(pieChart, barChartSeries);
            });

            // Button Delete Data from Chart
            Button deleteButton = new Button("Delete record");
            deleteButton.setOnAction(e -> {
                deleteData();
                refreshTableView();
                refreshData(pieChart, barChartSeries);
            });

            // Button Refresh Data Chart
            Button refreshBtn = new Button("Refresh");
            refreshBtn.setOnAction(event -> {
                refreshData(pieChart, barChartSeries);
                refreshTableView();
            });
            Button btClear = new Button("Clear");
            btClear.setOnAction(event -> {
                tfSSN.clear();
                tfFN.clear();
                tfLN.clear();
                tfMI.clear();
                tfBD.clear();
                tfST.clear();
                tfTEL.clear();
                tfdID.clear();
                tfZC.clear();
            });


            HBox hBox1 = new HBox(5);
            hBox1.getChildren().addAll(new Label("SSN"), tfSSN,
                    new Label("First Name"), tfFN,
                    new Label("Middle Name"), tfMI,
                    new Label("Last Name"), tfLN,
                    new Label("Phone"), tfTEL,
                    new Label("Birth Date (YYYY-MM-DD)"), tfBD);

            HBox hBox2 = new HBox(5);
            hBox2.getChildren().addAll(
                    new Label("Street"), tfST,
                    new Label("Zipcode"), tfZC,
                    new Label("deptID"), tfdID, (insertButton),(updateButton),(deleteButton)/*,(refreshBtn)*/,(showBtn),(btClear));

            VBox vBox = new VBox(10);
            vBox.getChildren().addAll(hBox1, hBox2, lblStatus);

            tfSSN.setPrefColumnCount(6);
            tfFN.setPrefColumnCount(6);
            tfMI.setPrefColumnCount(6);
            tfLN.setPrefColumnCount(6);
            tfST.setPrefColumnCount(6);
            tfTEL.setPrefColumnCount(6);
            tfZC.setPrefColumnCount(6);
            tfdID.setPrefColumnCount(6);

            // List View
            list.setMaxSize(800, 227);
            TableColumn ssnCol = new TableColumn("SSN");
            ssnCol.setCellValueFactory(new PropertyValueFactory<Project, String>("ssn"));
            TableColumn firstNameCol = new TableColumn("First Name");
            firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            TableColumn lastNameCol = new TableColumn("Last Name");
            lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            TableColumn miCol = new TableColumn("MI");
            miCol.setCellValueFactory(new PropertyValueFactory<>("mi"));
            TableColumn birthDateCol = new TableColumn("Birth Date");
            birthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
            TableColumn streetCol = new TableColumn("Street");
            streetCol.setCellValueFactory(new PropertyValueFactory<>("street"));
            TableColumn phoneCol = new TableColumn("Phone");
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            TableColumn zipCodeCol = new TableColumn("Zip Code");
            zipCodeCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
            TableColumn deptCol = new TableColumn("Dept Id");
            deptCol.setCellValueFactory(new PropertyValueFactory<>("deptId"));

            list.getColumns().addAll(ssnCol, firstNameCol, lastNameCol, miCol, birthDateCol, streetCol, phoneCol, zipCodeCol, deptCol);
            list.setRowFactory( tv -> {
                TableRow<Project> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (!row.isEmpty()) {
                        Project rowData = row.getItem();
                        tfSSN.setText(rowData.getSSN());
                        tfFN.setText(rowData.getFirstName());
                        tfMI.setText(rowData.getMi());
                        tfLN.setText(rowData.getLastName());
                        tfST.setText(rowData.getStreet());
                        tfTEL.setText(rowData.getPhone());
                        tfBD.setText(rowData.getBirthDate());
                        tfZC.setText(rowData.getZipCode());
                        tfdID.setText(rowData.getDeptId());
                    }
                });
                return row ;
            });

            // Refresh View Data
            refreshTableView();

            //Display
            borderPane.setRight(root1);
            borderPane.setLeft(root2);
            borderPane.setBottom(vBox);
            borderPane.setTop(list);

            primaryStage.setTitle("GUI DESIGN Students Database");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        private void initializeDB() {
            try {
                // Load the JDBC driver
                Class.forName("com.mysql.jdbc.Driver");

                System.out.println("Driver loaded");

                // Establish a connection
                Connection connection = DriverManager.getConnection("jdbc:mysql://18.140.131.225/javabook", "ce2104", "ce2104");

                System.out.println("Database connected");

                // Create a statement
                stmt = connection.createStatement();
                String connectionDetails = "Database connected";
                dbConnectionTextArea.setText(connectionDetails);
            }
            catch (Exception ex) {
                String connectionDetails = "Fail";
                dbConnectionTextArea.setText(connectionDetails);
                ex.printStackTrace();

            }
        }
        private int numBIOL = 0;
        private int numCHEM = 0;
        private int numCS = 0;
        private int numMATH = 0;
        private void numCount() {
            try {
                String queryString = "SELECT deptID, count(deptID) FROM Student GROUP by deptID";
                ResultSet rset = stmt.executeQuery(queryString);

                while (rset.next()) {
                    String deptID = rset.getString(1);
                    int count = rset.getInt(2);
                    if ("BIOL".equals(deptID)) {
                        numBIOL = count;
                    } else if ("CHEM".equals(deptID)) {
                        numCHEM = count;
                    } else if ("CS".equals(deptID)) {
                        numCS = count;
                    } else if ("MATH".equals(deptID)) {
                        numMATH = count;
                    }
                }
            }
            catch (SQLException ex) {
                //ex.printStackTrace();
            }
        }
        private void insertData() {
            String ssn = tfSSN.getText();
            String fn = tfFN.getText();
            String mi = tfMI.getText();
            String ln = tfLN.getText();
            String st = tfST.getText();
            String tel = tfTEL.getText();
            String zc = tfZC.getText();
            String bd = tfBD.getText();
            String deptId = tfdID.getText();

            // Validate Input
            if (ssn == null || fn == null || mi == null || ln == null || st == null || bd == null || tel == null || zc == null || deptId == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill data.");
                alert.show();
                return;
            }

            if (ssn.isEmpty() || fn.isEmpty() || mi.isEmpty() || ln.isEmpty() || st.isEmpty() || tel.isEmpty() || bd.isEmpty() || zc.isEmpty() || deptId.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill data.");
                alert.show();
                return;
            }

            // Validate birth date
            String[] birthDateSplit = bd.split("-");
            if(birthDateSplit[0] == null || birthDateSplit[1] == null || birthDateSplit[2] == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Birth Date format should be YYYY-MM-DD.");
                alert.show();
                return;
            }
            if(birthDateSplit[0].length() != 4 || birthDateSplit[1].length() != 2 || birthDateSplit[2].length() != 2) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Birth Date format should be YYYY-MM-DD.");
                alert.show();
                return;
            }

            String queryExistString = "SELECT * FROM Student WHERE ssn = ?";
            try {
                PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(queryExistString);
                preparedStatement.setString(1, ssn);
                ResultSet rSetExist = preparedStatement.executeQuery();
                if(rSetExist.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("SSN already have in database, change it.");
                    alert.show();
                    return;
                }
            } catch (SQLException e) {}

            try {
                String queryString = "INSERT INTO Student (ssn, firstName, mi, lastName, street, phone, zipCode, deptId, birthDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(queryString);

                preparedStatement.setString(1, ssn);
                preparedStatement.setString(2, fn);
                preparedStatement.setString(3, mi);
                preparedStatement.setString(4, ln);
                preparedStatement.setString(5, tel); // Setting phone
                preparedStatement.setString(6, st); // Setting street
                preparedStatement.setString(7, zc); // Setting zip code
                preparedStatement.setString(8, deptId);
                preparedStatement.setString(9, bd);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    lblStatus.setText("Record inserted successfully");
                } else {
                    lblStatus.setText("Failed to insert record");
                }
            } catch (SQLException ex) {
                System.out.println(ex);
                //ex.printStackTrace();
            }
        }

        private void updateData() throws SQLException {
            String ssn = tfSSN.getText();
            String fn = tfFN.getText();
            String mi = tfMI.getText();
            String ln = tfLN.getText();
            String st = tfST.getText();
            String tel = tfTEL.getText();
            String zc = tfZC.getText();
            String bd = tfBD.getText();
            String deptId = tfdID.getText();

            // Validate Input
            if (ssn == null || fn == null || mi == null || ln == null || st == null || bd == null || tel == null || zc == null || deptId == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill data.");
                alert.show();
                return;
            }

            if (ssn.isEmpty() || fn.isEmpty() || mi.isEmpty() || ln.isEmpty() || st.isEmpty() || bd.isEmpty() || tel.isEmpty() || zc.isEmpty() || deptId.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill data.");
                alert.show();
                return;
            }

            // Validate birth date
            String[] birthDateSplit = bd.split("-");
            if(birthDateSplit[0] == null || birthDateSplit[1] == null || birthDateSplit[2] == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Birth Date format should be YYYY-MM-DD .");
                alert.show();
                return;
            }
            if(birthDateSplit[0].length() != 4 || birthDateSplit[1].length() != 2 || birthDateSplit[2].length() != 2) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Birth Date format should be YYYY-MM-DD.");
                alert.show();
                return;
            }

            String queryExistString = "SELECT * FROM Student WHERE ssn = ?";
            try {
                PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(queryExistString);
                preparedStatement.setString(1, ssn);
                ResultSet rSetExist = preparedStatement.executeQuery();
                if(!rSetExist.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("This SSN don't exist, please enter correct.");
                    alert.show();
                    return;
                }
            } catch (SQLException e) {}

            try {
                String queryUpdateString = "UPDATE Student SET firstName = ?, lastName = ?, mi = ?, birthDate = ?, street = ?, phone = ?, zipCode = ?, deptId = ? WHERE ssn = ?";
                PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(queryUpdateString);
                preparedStatement.setString(1, fn);
                preparedStatement.setString(2, ln);
                preparedStatement.setString(3, mi);
                preparedStatement.setString(4, bd);
                preparedStatement.setString(5, st);
                preparedStatement.setString(6, tel);
                preparedStatement.setString(7, zc);
                preparedStatement.setString(8, deptId);
                preparedStatement.setString(9, ssn);
                int updateRow = preparedStatement.executeUpdate();
                if(updateRow > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Update success.");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Update failed for some reason.");
                    alert.show();
                }
                refreshTableView();
            } catch (SQLException e) {}
        }

        private void deleteData() {
            String ssn = tfSSN.getText();

            // Validate Input
            if (ssn == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill data.");
                alert.show();
                return;
            }

            if (ssn.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill data.");
                alert.show();
                return;
            }

            String queryExistString = "SELECT * FROM Student WHERE ssn = ?";
            try {
                PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(queryExistString);
                preparedStatement.setString(1, ssn);
                ResultSet rSetExist = preparedStatement.executeQuery();
                if(!rSetExist.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("This SSN don't exist, please enter correct.");
                    alert.show();
                    return;
                }
            } catch (SQLException e) {}

            try {
                String queryUpdateString = "DELETE FROM Student WHERE ssn = ?";
                PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(queryUpdateString);
                preparedStatement.setString(1, ssn);
                int updateRow = preparedStatement.executeUpdate();
                if(updateRow > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Delete success.");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Delete failed for some reason.");
                    alert.show();
                }
                refreshTableView();
            } catch (SQLException e) {}
        }

        private void refreshData(PieChart pieChart, XYChart.Series<String, Number> seriesBarChart) {
            try {
                // Get New Count from MySQL
                numCount();

                // Clear existing data
                seriesBarChart.getData().clear();
                pieChart.getData().clear();

                // Set New Data to Bar Chart
                seriesBarChart.getData().add(new XYChart.Data<>("BIOL", numBIOL));
                seriesBarChart.getData().add(new XYChart.Data<>("CHEM", numCHEM));
                seriesBarChart.getData().add(new XYChart.Data<>("CS", numCS));
                seriesBarChart.getData().add(new XYChart.Data<>("MATH", numMATH));

                for (int i = 0; i < seriesBarChart.getData().size(); i++) {
                    String color = "";
                    switch (i) {
                        case 0:
                            color = "-fx-bar-fill: #afc8ff;";
                            break;
                        case 1:
                            color = "-fx-bar-fill: #6c9afc;";
                            break;
                        case 2:
                            color = "-fx-bar-fill: #4d7fea;";
                            break;
                        case 3:
                            color = "-fx-bar-fill: #5959ea;";
                            break;
                    }
                    seriesBarChart.getData().get(i).getNode().setStyle(color);
                }

                for (XYChart.Data<String, Number> data : seriesBarChart.getData()) {
                    StackPane stackPane = (StackPane) data.getNode();
                    Label label = new Label(data.getYValue().toString());
                    stackPane.getChildren().add(label);
                    StackPane.setAlignment(label, javafx.geometry.Pos.TOP_CENTER);
                }

                // Set New Data to Pie Chart
                pieChart.getData().add(new PieChart.Data("BIOL", numBIOL));
                pieChart.getData().add(new PieChart.Data("CHEM", numCHEM));
                pieChart.getData().add(new PieChart.Data("CS", numCS));
                pieChart.getData().add(new PieChart.Data("MATH", numMATH));

                for (int i = 0; i < pieChart.getData().size(); i++) {
                    String color = "";
                    switch (i) {
                        case 0:
                            color = "-fx-pie-color: #afc8ff;";
                            break;
                        case 1:
                            color = "-fx-pie-color: #6c9afc;";
                            break;
                        case 2:
                            color = "-fx-pie-color: #4d7fea;";
                            break;
                        case 3:
                            color = "-fx-pie-color: #5959ea;";
                            break;
                    }

                    PieChart.Data pieDataAtIndex = pieChart.getData().get(i);
                    pieDataAtIndex.getNode().setStyle(color);
                    pieDataAtIndex.nameProperty().setValue(pieDataAtIndex.getName() + " : " + (int) pieDataAtIndex.getPieValue());
                }
            } catch (Exception ignored) {}
        }

        private void refreshTableView() {
            list.getItems().clear();
            try {
                String queryString = "SELECT * FROM Student";
                ResultSet rset = stmt.executeQuery(queryString);

                while (rset.next()) {
                    list.getItems().add(new Project(rset.getString(1), rset.getString(2), rset.getString(4), rset.getString(3), rset.getString(5), rset.getString(6), rset.getString(7), rset.getString(8), rset.getString(9)));
                }
            }
            catch (SQLException ex) {
                //ex.printStackTrace();
            }
        }

        public static void main(String[] args){
            launch(args);
        }
    }

    public static class Pro3Wel extends Application {

        @Override
        public void start(Stage primaryStage) {
            // Create welcome message
            Image backgroundImage = new Image("file:image/Welcome.jpg");

            // Create the background image
            BackgroundImage background = new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

            // Create welcome message
            VBox welcomeLayout = new VBox();
            welcomeLayout.setStyle("-fx-alignment: center; -fx-spacing: 10;");
            javafx.scene.control.Label welcomeLabel = new javafx.scene.control.Label("Welcome to my GUI!");
            welcomeLabel.setStyle("-fx-font-size: 50px; -fx-text-fill: #0D1291;"); // Set font size and color
            welcomeLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 50)); // Set font family and weight
            Button startButton = new Button("Start");
            startButton.setStyle("-fx-font-size: 25px; -fx-min-width: 100px; -fx-min-height: 50px;"); // Set font size and minimum size

            welcomeLayout.getChildren().addAll(welcomeLabel, startButton);
            welcomeLayout.setBackground(new Background(background));

            welcomeLayout.setBackground(new Background(background));

            // Set the welcome scene
            Scene welcomeScene = new Scene(welcomeLayout, 1600, 800);



            // Set action for the button
            ((Button) welcomeLayout.getChildren().get(1)).setOnAction(event -> {

                Main main = new Main(); // Create an instance of  Main class
                main.start(primaryStage); // Call the start method
            });

            primaryStage.setTitle("Welcome");
            primaryStage.setScene(welcomeScene);
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
}
